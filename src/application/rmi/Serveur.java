package application.rmi;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.globale.Globals;
import application.model.TableauDeBord;

public class Serveur extends UnicastRemoteObject implements ServeurInterface {
	
	private static final long serialVersionUID = 8804289039220398135L;

	private Hashtable<String, ClientInterface> liste_clients;

	private Game game;
	private StringBuffer tchat;

	private String chef;
	private boolean partiecommencee;

	public Serveur() throws RemoteException {
		Logger.getLogger("Serveur").log(Level.INFO, "Serveur lancé");
		setListe_clients(new Hashtable<String, ClientInterface>());

		game = new Game();
		tchat = new StringBuffer("[Serveur] Serveur lancé.\n");
		partiecommencee = false;
	}

	private void setBEnable(boolean b) throws RemoteException {
		if (liste_clients.containsKey(getChef()))
			liste_clients.get(getChef()).setBEnable(b);
	}
	public String erreurRegister(String p) {
		// Pseudo est déjà utilisé
		if (liste_clients.containsKey(p)) 
			return Globals.erreurPseudoUtilise;
		
		// Il manque le chef de la partie et il reste une place, on ne connecte pas le joueur
		else if (liste_clients.size() == 5 && !liste_clients.containsKey(getChef()) && !p.equals(getChef())) 
			return Globals.erreurPartieComplete2;
		
		// La partie est complète ( 6 joueurs ) le joueur ne peut pas se connecter
		else if (liste_clients.size() == 6) 
			return Globals.erreurPartieComplete;
		
		 else 
			return null;
	}
	
	@Override
	public void getCasePlayed(String text) throws RemoteException {
		if (partiecommencee) {
			Logger.getLogger("Serveur").log(Level.INFO, text);
			game.getPlateau().updateCase(text);
			distribution();
		}
	}

	/*
	 * Cette méthode est appelée lors de la connexion d'un client. Le client est
	 * ajouté à la liste des clients du serveur. Le client n'est pas ajouté si
	 * il a le même pseudo qu'un autre joueur.
	 */
	@Override
	public synchronized String register(ClientInterface client, String p) throws Exception {

		String erreur = erreurRegister(p);
		if (erreur != null)
			return erreur;
		
		// Le premier joueur qui se connecte est le chef de la partie, seul lui peut la lancer.
		if (liste_clients.size() == 0 && getChef() == null)
			setChef(p);

		liste_clients.put(p, client);
		distributionTchat("Serveur", "Le joueur " + p + " est entré dans la partie.");
		Logger.getLogger("Client").log(Level.INFO, "Nouveau client enregistré dans le serveur.");
		if (liste_clients.size() > 1)
			setBEnable(true);

		return null;
	}
	@Override
	public void setLancement() throws RemoteException {
		partiecommencee = true;
		distributionTchat("Serveur", "Le joueur " + getChef() + " a démarré la partie.");
		game.getPlateau().initialiseMainCaseNoir();
		for(int i=0;i<TableauDeBord.infoParClient.size();i++){
			game.getTableau().getInfoParClient().get(i).initialiseMain(game.getPlateau());
		}
		
		/*
		 * 
		 * 
		 * Appel de la fonction de distribution des jetons ICI
		 * 
		 * DISTRIBUTIOn DU GAME
		 */
	}
	

	/*
	 * Suppression du client de la HashTable quand un client ferme son
	 * application.
	 */
	@Override
	public synchronized void logout(String p) throws RemoteException {
		if (liste_clients.containsKey(p)) {
			liste_clients.remove(p);
			distributionTchat("Serveur", "Le joueur " + p + " s'est déconnecté du serveur.");
			if (p.equals(getChef()) && !partiecommencee) {
				distributionTchat("Serveur", "En attente de la reconnexion du joueur " + p + " pour lancer la partie.");
			}
			Logger.getLogger("Client").log(Level.INFO, "Le joueur " + p + " s'est déconnecté du serveur.");

			if (liste_clients.size() < 2)
				setBEnable(false);
		}
	}

	/*
	 * Distribution à chaque client du game , à chaque modification du plateau
	 * et des variables du jeu.
	 */
	@Override
	public void distribution() throws RemoteException {

		// MODIFICATION DU GAME ICI
		// APPEL DE LA FONCTION
		// On envoie le game actualisé à chaque client.
		Enumeration<ClientInterface> e = liste_clients.elements();
		while (e.hasMoreElements())
			e.nextElement().receive(game);
	}
	
	@Override
	public void distributionTchat(String pseudo, String s) throws RemoteException {
		tchat.append("[" + pseudo + "] " + s + "\n");
		// On envoie le tchat actualisé à chaque client.
		Enumeration<ClientInterface> e = liste_clients.elements();
		while (e.hasMoreElements())
			e.nextElement().receiveTchat(tchat.toString());
	}

	public static void main(String args[]) throws RemoteException, MalformedURLException, UnknownHostException {
		System.setProperty("java.security.policy", "file:./security.policy");
		System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().toString().split("/")[1]);
		System.setProperty("java.rmi.server.codebase", "file:./bin/");
		LocateRegistry.createRegistry(42000);
		Serveur serveur = new Serveur();
		Naming.rebind("rmi://" + InetAddress.getLocalHost().toString().split("/")[1] + ":42000/ACQUIRE", serveur);
	}

	public Hashtable<String, ClientInterface> getListe_clients() {
		return liste_clients;
	}

	public void setListe_clients(Hashtable<String, ClientInterface> hashtable) {
		this.liste_clients = hashtable;
	}

	public String getChef() {
		return chef;
	}

	public void setChef(String chef) {
		this.chef = chef;
	}
}