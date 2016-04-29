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

	private ArrayList<String> ordre_joueur;
	
	private ArrayList<String> ordre_joueur_action;

	private Game game;
	
	private Action action;

	private StringBuffer tchat;

	private String chef;
	
	private String playerTurn;

	private boolean partiecommencee;

	private boolean partiechargee;

	public Serveur() throws RemoteException {
		Logger.getLogger("Serveur").log(Level.INFO, "Serveur lancé");
		setListe_clients(new Hashtable<String, ClientInterface>());
		setOrdre_joueur(new ArrayList<String>());
		ordre_joueur_action = new ArrayList<String>();
		game = new Game();
		tchat = new StringBuffer("[Serveur] Serveur lancé.\n");
		setPartiecommencee(false);
		partiechargee = false;
	}
	
	/**
	 * Methode permettant de savoir qui est l'admin du jeu
	 * Lui attribuer la possibilité de lancer la partie
	 * @param b
	 * @throws RemoteException
	 */
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

	/**
	 * Methode recuperant la case joue par un joueur avec son pseudo
	 * Traitement du modele
	 */
	public void getCasePlayed(String text, String pseudo) throws RemoteException {
		if (isPartiecommencee()) {
			Logger.getLogger("Serveur").log(Level.INFO, text);
			action = game.getPlateau().updateCase(text, game.getListeChaine());
			
			
			
			piocheCaseFinTour(text,pseudo);
			if (action == null) {
				sendEndTurnAction();
			}else{
				nextTurnAction();
			}
			
			distribution();
		}
	}

	@Override
	public void creationChaineServeur(TypeChaine c) throws RemoteException {
		getGame().creationChaine(action.getListeDeCaseAModifier(), c);
		distribution();
		
		sendEndTurnAction ();
	}
	
	@Override
	public void achatAction(String nomJoueur, HashMap<TypeChaine, Integer> actionAAcheter) throws RemoteException{
		getGame().getTableau().achatActions(nomJoueur, actionAAcheter);
		
		distribution();
		
		nextTurn(playerTurn);
	}
	
	private void sendEndTurnAction () throws RemoteException{
		if (!game.getTableau().actionAvailableForPlayer(playerTurn)){
			nextTurn(playerTurn);
		}else{
			liste_clients.get(playerTurn).receiveBuyAction(game);
		}
	}
	private void nextTurnAction() throws RemoteException{
		//Premier appel -> On définit l'ordre des tours selon le type d'action
		if ( ordre_joueur_action.size() == 0){
			if (action.getChoix() == 0) {
				ordre_joueur_action.add(playerTurn);
			}
			if (action.getChoix() == 1) {
				//ajouter les joueurs concernés par la fusion
			}
		}
		//envoie de l'action au joueur concerné
		liste_clients.get(ordre_joueur_action.get(0)).receiveAction(action, game);
		ordre_joueur_action.remove(0);
		
	}
	
	/**
	 * methode qui permet au joueur de piocher une case à la fin de son tour
	 * @param text
	 * @param pseudo
	 */
	public void piocheCaseFinTour(String text, String pseudo){
		//Pioche d'une case a la fin du tour
		if (game.getTableau().getInfoParClient().get(pseudo).getMain().contains(text)) {
            int indice = game.getTableau().getInfoParClient().get(pseudo).getMain().indexOf(text);
            game.getTableau().getInfoParClient().get(pseudo).getMain().remove(indice);
            game.getTableau().getInfoParClient().get(pseudo).ajouteMain1fois(game.getPlateau());
        }
        else {
            // TODO throw case pas dans la main exception
            System.out.println("Case cliqué non dans la main du joueur : "+text);
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
			//ajout du joueur dans le tableau de bord si il se connecte pour la première fois
			if ( !game.getTableau().getInfoParClient().containsKey(p) ){
				game.getTableau().ajouterClient(new ClientInfo(p)); 
			}

			distributionTchat("Serveur", "Le joueur " + p + " est entré dans la partie.");
		}

		Logger.getLogger("Client").log(Level.INFO, "Nouveau client enregistré dans le serveur.");
		if ( partiechargee || isPartiecommencee()){
			distribution();
		}
		if (liste_clients.size() > 1 && !partiechargee && !partiecommencee)
			setBEnable(true);

		return null;
	}

	@Override
	public void setLancement() throws RemoteException {
		setPartiecommencee(true);
		distributionTchat("Serveur", "Le joueur " + getChef() + " a démarré la partie.");

		// initialisation des cases noirs pour chaque joueur
		//game.getPlateau().initialiseMainCaseNoir(game.getTableau().getInfoParClient().size());
		
		//INITIALISATION de lA MAIN
		initalisationMain();

		/*
		 * DISTRIBUTION de la main , du tableau des scores et du game
		 */
		
		playerTurn = ordre_joueur.get(0);
		liste_clients.get(playerTurn).turn();
		distribution();
	}

	/**
	 * Methode d'initialisation des mains de chaque joueur ainsi que la pioche de la case noire
	 * @throws RemoteException
	 */
	private void initalisationMain() throws RemoteException {
		Enumeration<String> enumKeys = liste_clients.keys();
		HashMap<String,String> listeCasesNoires = new HashMap<String,String>();
		while (enumKeys.hasMoreElements()) {
			String key = enumKeys.nextElement();
			game.getTableau().getInfoParClient().get(key).initialiseMain(game.getPlateau());
			listeCasesNoires.put(key,game.getPlateau().initialiseMainCaseNoir());
		}
		setTurn(listeCasesNoires);
		
		
	}

	/**
	 * Methode permettant d'attribuer l'ordre des joueurs
	 * @param listeCasesNoires
	 */
	public void setTurn(HashMap<String,String> listeCasesNoires) {
		HashMap<String, String> result = Globals.sortByValue(listeCasesNoires);
		Collection<String> keys = result.keySet();
		for (String key : keys) {
			ordre_joueur.add(key);
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

			if (liste_clients.size() < 2 && !partiechargee && !partiecommencee)
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

	public ArrayList<String> getOrdre_joueur() {
		return ordre_joueur;
	}

	public void setOrdre_joueur(ArrayList<String> ordre_joueur) {
		this.ordre_joueur = ordre_joueur;
	}

	public boolean isPartiecommencee() {
		return partiecommencee;
	}

	public void setPartiecommencee(boolean partiecommencee) {
		this.partiecommencee = partiecommencee;
	}
	/**
	 * Methode de calcul du prochain tour et notification du client
	 */
	public void nextTurn(String pseudo)  throws RemoteException {
		int currentIndice = this.ordre_joueur.indexOf(pseudo);
		if (this.ordre_joueur.size()==currentIndice+1) {
			playerTurn = this.ordre_joueur.get(0);
		}
		else {
			playerTurn = this.ordre_joueur.get(currentIndice+1);
		}
		distributionTchat("Serveur", "Au tour de  " + playerTurn);
		liste_clients.get(playerTurn).turn();
	}
}