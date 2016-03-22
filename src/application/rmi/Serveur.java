package application.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serveur extends UnicastRemoteObject implements ServeurInterface{

	private ArrayList<ClientInterface> liste_clients ;
	
	private Game game;

	protected Serveur() throws RemoteException {
		Logger.getLogger("Serveur").log(Level.INFO, "Serveur lancé");
		setListe_clients(new ArrayList<ClientInterface>());
		
		game = new Game();
	}


	/*
	 * Cette méthode est appelée lors de la connexion d'un client.
	 * Le client est ajouté à la liste des clients du serveur.
	 * Le client n'est pas ajouté si il a le même pseudo qu'un autre joueur.
	 */
	public synchronized boolean register(ClientInterface client, String p) throws Exception {
		for (ClientInterface c : liste_clients){
			if (c.getPseudo().equals(p))
				return false;
		}
		getListe_clients().add(client);
		return true;
	}

	/*
	 * Distribution à chaque client du game , à chaque modification du plateau et des variables du jeu.
	 */
	public void distribution() throws RemoteException {
		
		//MODIFICATION DU GAME ICI
		//APPEL DE LA FONCTION
		
		
		
		//On envoie le game actualisé à chaque client.
		for (ClientInterface c : liste_clients){
				c.receive(game);
		}
	}

	public static void main (String args[]) throws RemoteException, MalformedURLException{
		System.setProperty("java.security.policy","file:./security.policy");
		System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname", "127.0.0.1");
		System.setProperty("java.rmi.server.codebase","file:./bin/");
		LocateRegistry.createRegistry(42000);
		Serveur serveur = new Serveur();
		Naming.rebind("rmi://127.0.0.1:42000/ACQUIRE", serveur);
	}


	public ArrayList<ClientInterface> getListe_clients() {
		return liste_clients;
	}

	public void setListe_clients(ArrayList<ClientInterface> liste_clients) {
		this.liste_clients = liste_clients;
	}


	@Override
	public void getCasePlayed(String text) throws RemoteException {
		Logger.getLogger("Serveur").log(Level.INFO, text);
		//game.getPlateau().updateCase(text);
		
	}
}