package application.rmi;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serveur extends UnicastRemoteObject implements ServeurInterface{

	private Hashtable<String, ClientInterface> liste_clients ;
	
	private Game game;

	protected Serveur() throws RemoteException {
		Logger.getLogger("Serveur").log(Level.INFO, "Serveur lancé");
		setListe_clients(new Hashtable<String, ClientInterface>());
		
		game = new Game();
	}


	/*
	 * Cette méthode est appelée lors de la connexion d'un client.
	 * Le client est ajouté à la liste des clients du serveur.
	 * Le client n'est pas ajouté si il a le même pseudo qu'un autre joueur.
	 */
	public synchronized boolean register(ClientInterface client, String p) throws Exception {
		if (liste_clients.containsKey(p))
			return false;
		
		getListe_clients().put(p,client);
		Logger.getLogger("Client").log(Level.INFO, "Nouveau client enregistré dans le serveur.");
		return true;
	}
	
	/*
	 * Suppression du client de l'ArrayList quand un client ferme son application.
	 */
	public synchronized void logout (String p) throws RemoteException{
		if ( liste_clients.containsKey(p)){
			liste_clients.remove(p);
			Logger.getLogger("Client").log(Level.INFO, "Le joueur "+p+" s'est déconnecté du serveur.");
		}
	}

	/*
	 * Distribution à chaque client du game , à chaque modification du plateau et des variables du jeu.
	 */
	public void distribution() throws RemoteException {
		
		//MODIFICATION DU GAME ICI
		//APPEL DE LA FONCTION
		
		
		
		//On envoie le game actualisé à chaque client.
		Enumeration<ClientInterface> e = liste_clients.elements();
	    while(e.hasMoreElements())
	    	 e.nextElement().receive(game);
	}

	public static void main (String args[]) throws RemoteException, MalformedURLException, UnknownHostException{
		System.setProperty("java.security.policy","file:./security.policy");
		System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().toString().split("/")[1]);
		System.setProperty("java.rmi.server.codebase","file:./bin/");
		LocateRegistry.createRegistry(42000);
		Serveur serveur = new Serveur();
		Naming.rebind("rmi://"+InetAddress.getLocalHost().toString().split("/")[1]+":42000/ACQUIRE", serveur);
	}


	public Hashtable<String, ClientInterface> getListe_clients() {
		return liste_clients;
	}

	public void setListe_clients(Hashtable<String, ClientInterface> hashtable) {
		this.liste_clients = hashtable;
	}


	@Override
	public void getCasePlayed(String text) throws RemoteException {
		Logger.getLogger("Serveur").log(Level.INFO, text);
		game.getPlateau().updateCase(text);
		distribution();
	}
}