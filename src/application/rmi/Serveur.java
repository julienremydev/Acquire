package application.rmi;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.globale.Globals;
import application.model.Action;
import application.model.ClientInfo;
import application.model.TypeChaine;

public class Serveur extends UnicastRemoteObject implements ServeurInterface {

	private static final long serialVersionUID = 8804289039220398135L;

	private Hashtable<String, ClientInterface> liste_clients;

	private Game game;

	private StringBuffer tchat;

	private String chef;

	private boolean partiecommencee;
	
	private boolean partiechargee;

	public Serveur() throws RemoteException {
		Logger.getLogger("Serveur").log(Level.INFO, "Serveur lancé");
		setListe_clients(new Hashtable<String, ClientInterface>());

		game = new Game();
		tchat = new StringBuffer("[Serveur] Serveur lancé.\n");
		setPartiecommencee(false);
		partiechargee = false;
	}

	private void setBEnable(boolean b) throws RemoteException {
		if (liste_clients.containsKey(getChef()))
			liste_clients.get(getChef()).setBEnable(b);
	}

	public String erreurRegister(String p, boolean loadJSON) {
		//Chargement d'une partie et il y a déjà des joueurs sur le serveur
		if (loadJSON && liste_clients.size() != 0 )
			return Globals.erreurChargementJSONimpossible;
		
		// Pseudo est déjà utilisé
		else if (liste_clients.containsKey(p))
			return Globals.erreurPseudoUtilise;

		//La partie est commencée ou chargée : on vérifie les correpondances entre les pseudos
		else if ((partiechargee || isPartiecommencee()) && !game.getTableau().getInfoParClient().containsKey(p))
			return Globals.erreurForbiddenPlayer;
		
		// Il manque le chef de la partie et il reste une place, on ne connecte
		// pas le joueur
		else if (!partiechargee && liste_clients.size() == (Globals.nombre_joueurs_max - 1) && !liste_clients.containsKey(getChef())
				&& !p.equals(getChef()))
			return Globals.erreurPartieComplete2;

		// La partie est complète ( 6 joueurs ) le joueur ne peut pas se
		// connecter
		else if (liste_clients.size() == Globals.nombre_joueurs_max)
			return Globals.erreurPartieComplete;

		else
			return null;
	}

	@Override
	public void getCasePlayed(String text, String pseudo) throws RemoteException {
		if (isPartiecommencee()) {
			Logger.getLogger("Serveur").log(Level.INFO, text);
			Action action = game.getPlateau().updateCase(text, game.getListeChaine());
			// Envoi de l'action au client
			// pas d'envoi d'action car aucune action
			if (action == null) {

			} else {
				if (action.getChoix() == 0) {
					liste_clients.get(pseudo).receiveAction(action, game);
				}
			}
			distribution();
		}
	}

	/*
	 * Cette méthode est appelée lors de la connexion d'un client. Le client est
	 * ajouté à la liste des clients du serveur. Le client n'est pas ajouté si
	 * il a le même pseudo qu'un autre joueur.
	 */
	@Override
	public synchronized String register(ClientInterface client, String p, boolean loadJSON) throws Exception {

		String erreur = erreurRegister(p, loadJSON);
		if (erreur != null)
			return erreur;
			
		
		// Le premier joueur qui se connecte est le chef de la partie, seul lui peut la lancer.
		if (liste_clients.size() == 0 && getChef() == null)
			setChef(p);

		liste_clients.put(p, client);
		
		//Chargement du fichier JSON : MAJ du GAME+ClientInfo
		if ( loadJSON ){
			/*
			 * TODO :Chargement du fichier JSON : MAJ du GAME+ClientInfo
			 * 
			 */
			partiechargee = true;
			setPartiecommencee(true);
			distributionTchat("Serveur", "Le joueur " + p + " a chargé une partie.");
		}else{
			//ajout du joueur dans le tableau de bord
			game.getTableau().ajouterClient(new ClientInfo(p)); 
			distributionTchat("Serveur", "Le joueur " + p + " est entré dans la partie.");
		}
		
		Logger.getLogger("Client").log(Level.INFO, "Nouveau client enregistré dans le serveur.");
		if ( partiechargee || isPartiecommencee()){
			//distributionMain(); -> MARCHE PAAAAAAAAAAAAAAAAAAAAS
			distributionData();
			distribution();
		}
		if (liste_clients.size() > 1 && !partiechargee)
			setBEnable(true);

		return null;
	}

	@Override
	public void setLancement() throws RemoteException {
		setPartiecommencee(true);
		distributionTchat("Serveur", "Le joueur " + getChef() + " a démarré la partie.");

		// initialisation des cases noirs pour chaque joueur
		game.getPlateau().initialiseMainCaseNoir(game.getTableau().getInfoParClient().size());

		//INITIALISATION de lA MAIN ICI ???????????????????
		
		
		/*
		 * DISTRIBUTION de la main , du tableau des scores et du game
		 */
		distributionMain();
		distributionData();
		distribution();
	}

	private void distributionMain() throws RemoteException {
		Collection<ClientInterface> e = liste_clients.values();
		for (ClientInterface c : e) {
			ArrayList<String> main = new ArrayList<String>();
			main = game.getTableau().getInfoParClient().get(c.getPseudo()).initialiseMain(game.getPlateau());
			c.receiveMain(main);
			main.clear();
		}

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
			if (p.equals(getChef()) && !isPartiecommencee()) {
				distributionTchat("Serveur", "En attente de la reconnexion du joueur " + p + " pour lancer la partie.");
			}
			Logger.getLogger("Client").log(Level.INFO, "Le joueur " + p + " s'est déconnecté du serveur.");

			if (liste_clients.size() < 2)
				setBEnable(false);
			if (liste_clients.size() == 0)
				chef = "";
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
	public void distributionData() throws RemoteException {
		Enumeration<ClientInterface> e = liste_clients.elements();
		while (e.hasMoreElements())
			e.nextElement().receiveData();
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

	public Game getGame() throws RemoteException {
		return game;
	}

	@Override
	public void creationChaineServeur(Action a, TypeChaine c) throws RemoteException {
		getGame().getPlateau().creationChaine(a.getListeDeCaseAModifier(), c);
		distribution();

	}

	public boolean isPartiecommencee() {
		return partiecommencee;
	}

	public void setPartiecommencee(boolean partiecommencee) {
		this.partiecommencee = partiecommencee;
	}
}