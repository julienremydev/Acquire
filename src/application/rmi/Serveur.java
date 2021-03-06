package application.rmi;

import java.io.File;
import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.globale.Globals;
import application.model.Action;
import application.model.Case;
import application.model.Chaine;
import application.model.ClientInfo;
import application.model.Plateau;
import application.model.TypeChaine;

public class Serveur extends UnicastRemoteObject implements ServeurInterface {

	private static final long serialVersionUID = 8804289039220398135L;

	private Hashtable<String, ClientInterface> liste_clients;

	private Game game;


	public Serveur() throws RemoteException {
		Logger.getLogger("Serveur").log(Level.INFO, "Serveur lanc�");
		setListe_clients(new Hashtable<String, ClientInterface>());
		game = new Game();
	}

	/**
	 * Le client veut sauvegarder la partie
	 * On lui envoie le Game
	 * @throws IOException 
	 */
	public void clientSaveGame(String pseudo) throws IOException{
		if ( getGame().isPartiecommencee() || getGame().isPartiechargee())
			liste_clients.get(pseudo).receiveGameForSave(getGame());
		else
			liste_clients.get(pseudo).receiveGameForSave(null);
	}
	/**
	 * Methode permettant de savoir qui est l'admin du jeu
	 * Lui attribuer la possibilit� de lancer la partie
	 * @param b
	 * @throws RemoteException
	 */
	private void setBEnable(boolean b) throws RemoteException {
		if (liste_clients.containsKey(getGame().getChef()))
			liste_clients.get(getGame().getChef()).setBEnable(b);
	}
	/**
	 * Veirification d'erreurs de registre dans le JSON
	 * @param p
	 * @param loadJSON
	 * @return
	 * @throws RemoteException
	 */
	public String erreurRegister(String p, boolean loadJSON) throws RemoteException {
		//Chargement d'une partie et il y a d�j� des joueurs sur le serveur
		if (loadJSON && liste_clients.size() != 0 )
			return Globals.erreurChargementJSONimpossible;

		// Pseudo est d�j� utilis�
		else if (liste_clients.containsKey(p))
			return Globals.erreurPseudoUtilise;

		//La partie est commenc�e ou charg�e : on v�rifie les correpondances entre les pseudos
		else if ((getGame().isPartiechargee() || getGame().isPartiecommencee()) && !game.getTableauDeBord().getInfoParClient().containsKey(p))
			return Globals.erreurForbiddenPlayer;

		// Il manque le chef de la partie et il reste une place, on ne connecte
		// pas le joueur
		else if (!getGame().isPartiechargee() && liste_clients.size() == (Globals.nombre_joueurs_max - 1) && !liste_clients.containsKey(getGame().getChef())
				&& !p.equals(getGame().getChef()))
			return Globals.erreurPartieComplete2;

		// La partie est compl�te ( 6 joueurs ) le joueur ne peut pas se
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
		if (getGame().isPartiecommencee()){
			distributionTchat("Serveur", "Le joueur "+pseudo+" a jou� la case "+text+".");
			ArrayList<String> CasesToRemove = new ArrayList<String>();
			//game.getPlateau().CasesGrises(game.getListeChaine(), game.getTableauDeBord().getClientInfo(pseudo).getMain());
			for (String s : game.getTableauDeBord().getClientInfo(pseudo).getMain()) {
				if (this.game.getPlateau().getCase(s).getEtat()==-1) {
					CasesToRemove.add(s);
				}
			}
			for (String s : CasesToRemove) {
				game.getTableauDeBord().getClientInfo(pseudo).getMain().remove(s);
				game.getTableauDeBord().getClientInfo(pseudo).ajouteMain1fois(this.game.getPlateau());
			}
			Logger.getLogger("Serveur").log(Level.INFO, text);
			getGame().setAction(game.getPlateau().updateCase(text, game.getListeChaine()));
			piocheCaseFinTour(text,pseudo);
			ArrayList<String> main = game.getPlateau().CasesGrises(game.getListeChaine(), game.getTableauDeBord().getClientInfo(pseudo).getMain());
			for (String cas : main) {
				game.getTableauDeBord().getClientInfo(pseudo).getMain().remove(cas);
				game.getTableauDeBord().getClientInfo(pseudo).ajouteMain1fois(game.getPlateau());
			}
			if (getGame().getAction() == null) {
				sendEndTurnAction();
			}else{
				if ( getGame().getAction().getChoix() == Globals.choixActionFusionEchangeAchatVente && getGame().isPartieDeuxJoueurs() ){
					setPiocheBanque();
					ArrayList<ArrayList<String>> arrayPrime = getGame().getPrime();
					sendPrimeTchat(arrayPrime);
					nextTurnAction();
					getGame().getTableauDeBord().getInfoParClient().remove("Serveur");

				}else if (getGame().getAction().getChoix() == Globals.choixActionFusionEchangeAchatVente ){
					ArrayList<ArrayList<String>> arrayPrime = getGame().getPrime();
					sendPrimeTchat(arrayPrime);
					nextTurnAction();
				}else{
					nextTurnAction();
				}
			}
			distribution();
		}
	}
	/**
	 * Envoi des primes des joueurs lors d'une fusion dans le Chat
	 * @param arrayPrime
	 * @throws RemoteException
	 */
	private void sendPrimeTchat (ArrayList<ArrayList<String>> arrayPrime) throws RemoteException{
		for (int i = 0;i<arrayPrime.size();i++){
			for (int j=0;j<arrayPrime.get(i).size();j++){
				if ( j == 0){
					distributionTchat("Serveur", "Primes pour la chaine "+arrayPrime.get(i).get(j));
				}else if (j%2 == 1 && Integer.parseInt(arrayPrime.get(i).get(j+1)) != 0){
					distributionTchat("Serveur", "Le joueur "+arrayPrime.get(i).get(j)+" re�oit "+arrayPrime.get(i).get(j+1));

				}
			}
		}
	}
	/**
	 * Lorsqu'il n'y a que deux joueur, permet a la banque de piocher une case
	 * @throws RemoteException
	 */
	private void setPiocheBanque() throws RemoteException {
		String caseNoire = getGame().getPlateau().poserJetonBanque(game.getListeChaine());
		int nb_actions_banque = Integer.parseInt(caseNoire.substring(1, caseNoire.length()));
		ClientInfo ci = new ClientInfo("Serveur");
		for (Chaine c : getGame().getAction().getListeChainesAbsorbees()) {
			ci.getActionParChaine().replace(c.getTypeChaine(), nb_actions_banque);
		}
		getGame().getTableauDeBord().getInfoParClient().put(ci.getPseudo(), ci);
		getGame().getTableauDeBord().updateActionnaire();
		if ( nb_actions_banque > 1 ){
			distributionTchat("Serveur", "La banque pioche la case:" + caseNoire + ". Elle poss�de "+nb_actions_banque+ " actions.");
		}else{
			distributionTchat("Serveur", "La banque pioche la case:" + caseNoire + ". Elle poss�de "+nb_actions_banque+ " action.");
		}
	}
	/**
	 * Cr�ation des chaines sur le serveur lors d'une creation
	 */
	@Override
	public void creationChaineServeur(TypeChaine c) throws RemoteException {
		getGame().creationChaine(getGame().getAction().getListeDeCaseAModifier(), c, getGame().getPlayerTurn());
		sendEndTurnAction();

		distribution();
	}
	/**
	 * Le joueur achete des action lors d'une fusion
	 */
	@Override
	public void achatAction(String nomJoueur, HashMap<TypeChaine, Integer> actionAAcheter) throws RemoteException{
		getGame().getTableauDeBord().achatActions(nomJoueur, actionAAcheter);
		nextTurn(getGame().getPlayerTurn());

		String actionJoueurNotif;
		if ( actionAAcheter.size() > 0 ){
			actionJoueurNotif = "Le joueur " + nomJoueur + " a achet� :"; 

			Collection<TypeChaine> keys = actionAAcheter.keySet();
			for (TypeChaine key : keys) {
				actionJoueurNotif+= "\n -"+actionAAcheter.get(key) + " " + key.toString();
			}
		}else{
			actionJoueurNotif = "Le joueur " + nomJoueur + " n'a rien achet�.";
		}

		distributionTchat("Serveur", actionJoueurNotif);
		distribution();
	}
	/**
	 * Mettre a jour infos du client apr�s vente et �change des actions
	 */
	public void choiceFusionAction(HashMap<String, Integer> actions_fusions, Chaine chaineAbsorbee, Chaine chaineAbsorbante, String pseudo,int prix_action_absorbante, int prix_action_absorbee) throws RemoteException{
		getGame().getTableauDeBord().traiteAction(actions_fusions, chaineAbsorbee, chaineAbsorbante, pseudo,prix_action_absorbante, prix_action_absorbee);
		if ( getGame().getOrdre_joueur_action().size()==0)
			getGame().getAction().getListeChainesAbsorbees().remove(0);

		nextTurnAction();

		distribution();
	}
	/**
	 * Permet de choisir la couleur de fusion lorsque 2 chaines ont la m�me taille
	 * Effectue la fusion et red�fini une action pour la vente et l'�change d'actions
	 * V�rification si la game se passe a 2 joueurs
	 */
	public void choixCouleurFusion(ArrayList<Chaine> listeChainePlateau, ArrayList<Chaine> listeChaineAModif, Chaine c, ArrayList<Case> listeCaseAbsorbee) throws RemoteException {
		ArrayList<Chaine> listeChaineDifferenteAvantModif = listeChaineAModif;
		Chaine chaineAbsorbanteAvantFusion = c;
		this.getGame().setListeChaine(getGame().getPlateau().fusionChaines(game.getListeChaine(), listeChaineAModif, c, listeCaseAbsorbee));
		Action action = new Action(Globals.choixActionFusionEchangeAchatVente,listeChaineDifferenteAvantModif,chaineAbsorbanteAvantFusion);
		getGame().setAction(action);
		if ( getGame().isPartieDeuxJoueurs() ){
			setPiocheBanque();
			ArrayList<ArrayList<String>> arrayPrime = getGame().getPrime();
			sendPrimeTchat(arrayPrime);
			nextTurnAction();
			getGame().getTableauDeBord().getInfoParClient().remove("Serveur");

		}else{
			ArrayList<ArrayList<String>> arrayPrime = getGame().getPrime();
			sendPrimeTchat(arrayPrime);
			nextTurnAction();
		}

		distribution();
	}
	/**
	 * appel�e apr�s que le client a jou� -> On lui envoir l'achat d'action si  c possible sinon on change de tour
	 * @throws RemoteException
	 */
	private void sendEndTurnAction() throws RemoteException{
		if (!getGame().getTableauDeBord().actionAvailableForPlayer(getGame().getPlayerTurn())){
			nextTurn(getGame().getPlayerTurn());
		}else{
			liste_clients.get(getGame().getPlayerTurn()).receiveBuyAction(getGame());
		}
	}
	/**
	 * D�finis l'ordre des tours celon le type d'Action
	 * @throws RemoteException
	 */
	private void nextTurnAction() throws RemoteException{
		//Premier appel-> On d�finit l'ordre des tours selon le type d'action
		if ( getGame().getOrdre_joueur_action().size() == 0){
			if (getGame().getAction().getChoix() == Globals.choixActionCreationChaine) {
				getGame().getOrdre_joueur_action().add(getGame().getPlayerTurn());
			}else if (getGame().getAction().getChoix() == Globals.choixActionFusionSameSizeChaine) {
				//ajout du joueur qui a fusionn�
				getGame().getOrdre_joueur_action().add(getGame().getPlayerTurn());
			}else if (getGame().getAction().getChoix() == Globals.choixActionFusionEchangeAchatVente) {
				//Fin du tour si tous les joueurs ont fait leurs choix
				if ( getGame().getAction().getListeChainesAbsorbees().size() == 0)
					sendEndTurnAction ();
				//ajouter les joueurs concern�s par la fusion
				else
					getGame().setOrdreFusion ();
			}
		}
		//envoie de l'action au joueur concern�
		if (getGame().getOrdre_joueur_action().size() > 0){
			liste_clients.get(getGame().getOrdre_joueur_action().get(0)).receiveAction( getGame() );
			getGame().getOrdre_joueur_action().remove(0);
		}
	}

	/**
	 * methode qui permet au joueur de piocher une case � la fin de son tour
	 * @param text
	 * @param pseudo
	 */
	public void piocheCaseFinTour(String text, String pseudo) throws RemoteException{
		//Pioche d'une case a la fin du tour
		if (game.getTableauDeBord().getInfoParClient().get(pseudo).getMain().contains(text) && !game.getPlateau().getCasesDisponible().isEmpty()) {
			int indice = game.getTableauDeBord().getInfoParClient().get(pseudo).getMain().indexOf(text);
			game.getTableauDeBord().getInfoParClient().get(pseudo).getMain().remove(indice);
			game.getTableauDeBord().getInfoParClient().get(pseudo).ajouteMain1fois(game.getPlateau());
		}
		else {
			int indice = game.getTableauDeBord().getInfoParClient().get(pseudo).getMain().indexOf(text);
			game.getTableauDeBord().getInfoParClient().get(pseudo).getMain().remove(indice);
			for (ClientInfo c : game.getTableauDeBord().getInfoParClient().values()) {
				if (c.getMain().isEmpty()) {
					isOver();
				}
			}
		}
	}
	/**
	 * Cette m�thode est appel�e lors de la connexion d'un client. Le client est
	 * ajout� � la liste des clients du serveur. Le client n'est pas ajout� si
	 * il a le m�me pseudo qu'un autre joueur.
	 */
	@Override
	public synchronized String register(ClientInterface client, String p, boolean loadJSON, File file) throws Exception {

		String erreur = erreurRegister(p, loadJSON);

		if (erreur != null)
			return erreur;


		// Le premier joueur qui se connecte est le chef de la partie, seul lui peut la lancer.
		if (liste_clients.size() == 0 && getGame().getChef() == null)
			getGame().setChef(p);

		liste_clients.put(p, client);

		//Chargement du fichier JSON : MAJ du GAME+ClientInfo
		if ( loadJSON ){

			this.game= chargerGame(file);
			this.game.setPlateau(Plateau.plateauRegeneration(chargerGame(file).getPlateau()));

			getGame().setPartiechargee(true);
			getGame().setPartiecommencee(true);
			liste_clients.get(p).turn();
			distributionTchat("Serveur", "Le joueur " + p + " a charg� une partie.");
		}else{
			//ajout du joueur dans le tableau de bord si il se connecte pour la premi�re fois
			if ( !game.getTableauDeBord().getInfoParClient().containsKey(p) ){
				game.getTableauDeBord().ajouterClient(new ClientInfo(p));
			}
			if (getGame().isPartiecommencee()) {
				if (getGame().getPlayerTurn().equals(p)) {
					liste_clients.get(p).turn();
				}
			}


			distributionTchat("Serveur", "Le joueur " + p + " est entr� dans la partie.");
			if ( (game.isPartiechargee() || game.isPartiechargee()) && game.getPlayerTurn().equals(p)){
				liste_clients.get(p).turn();
			}

		}

		Logger.getLogger("Client").log(Level.INFO, "Nouveau client enregistr� dans le serveur.");
		if ( getGame().isPartiechargee() || getGame().isPartiecommencee()){
			distribution();
		}
		if (liste_clients.size() == Globals.nombre_joueurs_min && !getGame().isPartiechargee() && !getGame().isPartiecommencee())
			setBEnable(true);

		return null;
	}
	/**
	 * appel�e quand un joueur lance la partie initialisation des mains
	 */
	@Override
	public void setLancement() throws RemoteException {
		getGame().setPartiecommencee(true);
		if ( liste_clients.size() == 2 )
			getGame().setPartieDeuxJoueurs(true);

		distributionTchat("Serveur", "Le joueur " + getGame().getChef() + " a d�marr� la partie.");

		// initialisation des cases noirs pour chaque joueur
		//game.getPlateau().initialiseMainCaseNoir(game.getTableau().getInfoParClient().size());

		//INITIALISATION de lA MAIN
		initalisationMain();

		/*
		 * DISTRIBUTION de la main , du tableau des scores et du game
		 */

		game.setPlayerTurn(getGame().getOrdre_joueur().get(0));
		liste_clients.get(getGame().getPlayerTurn()).turn();
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
				listeCasesNoires.put(key,game.getPlateau().initialiseMainCaseNoir());
			game.getTableauDeBord().getInfoParClient().get(key).initialiseMain(game.getPlateau());

		}
		setTurn(listeCasesNoires);


	}

	/**
	 * Methode permettant d'attribuer l'ordre des joueurs
	 * @param listeCasesNoires
	 * @throws RemoteException 
	 */
	public void setTurn(HashMap<String,String> listeCasesNoires) throws RemoteException {
		HashMap<String, String> result = Globals.sortByValue(listeCasesNoires);
		Collection<String> keys = result.keySet();
		for (String key : keys) {
			getGame().getOrdre_joueur().add(key);
		}
	}

	/**
	 * Suppression du client de la HashTable quand un client ferme son
	 * application.
	 */
	@Override
	public synchronized void logout(String p) throws RemoteException {
		if (liste_clients.containsKey(p)) {
			liste_clients.remove(p);
			distributionTchat("Serveur", "Le joueur " + p + " s'est d�connect� du serveur.");
			if (p.equals(getGame().getChef()) && !getGame().isPartiecommencee()) {
				distributionTchat("Serveur", "En attente de la reconnexion du joueur " + p + " pour lancer la partie.");
			}
			if ( !game.isPartiechargee() && !game.isPartiecommencee() && !p.equals(game.getChef()) && game.getTableauDeBord().getInfoParClient().containsKey(p) ){
				game.getTableauDeBord().getInfoParClient().remove(p); 
			}
			Logger.getLogger("Client").log(Level.INFO, "Le joueur " + p + " s'est d�connect� du serveur.");

			if (liste_clients.size() < Globals.nombre_joueurs_min && !getGame().isPartiechargee() && !getGame().isPartiecommencee())
				setBEnable(false);
			if (liste_clients.size() == 0)
				getGame().setChef(null);
		}
	}

	/**
	 * Distribution � chaque client du game , � chaque modification du plateau
	 * et des variables du jeu.
	 */
	@Override
	public void distribution() throws RemoteException {
		game.getPlateau().fullChaine(game.getListeChaine(),game.getTableauDeBord().getInfoParClient().values());
		for (ClientInfo c : this.game.getTableauDeBord().getInfoParClient().values()) {
			game.getPlateau().CasesGrises(game.getListeChaine(), c.getMain());
		}		
		this.game.getTableauDeBord().updateActionnaire();

		// MODIFICATION DU GAME ICI
		// APPEL DE LA FONCTION

		// On envoie le game actualis� � chaque client.
		Enumeration<ClientInterface> e = liste_clients.elements();
		while (e.hasMoreElements())
			e.nextElement().receive(this.game);

	}

	/**
	 * Envoi du chat actualis� � chaques clients
	 */
	@Override
	public void distributionTchat(String pseudo, String s) throws RemoteException {
		getGame().getTchat().add("[" + pseudo + "]");
		getGame().getTchat().add(s);
		// On envoie le tchat actualis� � chaque client.
		Enumeration<ClientInterface> e = liste_clients.elements();
		while (e.hasMoreElements())
			e.nextElement().receiveTchat(getGame().getTchat());
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


	public Game getGame() throws RemoteException {
		return game;
	}

	/**
	 * Methode de calcul du prochain tour et notification du client
	 */
	public void nextTurn(String pseudo)  throws RemoteException {
		game.setPlayerTurn(getGame().whoseTurn(pseudo));
		if ( liste_clients.containsKey(game.getPlayerTurn())){
			liste_clients.get(game.getPlayerTurn()).turn();

			for (ClientInfo c : game.getTableauDeBord().getInfoParClient().values()) {
				game.getPlateau().CasesGrises(game.getListeChaine(), c.getMain());
				for(String s : game.getPlateau().CasesGrises(game.getListeChaine(), c.getMain())) {
					game.getPlateau().getCase(s).setEtat(-1);
				}
			}
		}

	}
	/**
	 * Termine la partie et envoie le classement
	 */
	public void isOver() throws RemoteException{
		Collection <ClientInfo> clients = this.getGame().getTableauDeBord().getInfoParClient().values();
		HashMap<String,Integer> classement = new HashMap<String,Integer>();
		for (ClientInfo client : clients) {
			classement.put(client.getPseudo(),client.getNet());
		}
		ArrayList<String> winner = Globals.getClassement(classement);
		ArrayList<String> winnerNet = new ArrayList<String>();
		for (String pseudo : winner) {
			winnerNet.add(pseudo);
			winnerNet.add(game.getTableauDeBord().getInfoParClient().get(pseudo).getNet().toString());
		}
		Enumeration<ClientInterface> e = liste_clients.elements();
		while (e.hasMoreElements())
			e.nextElement().receiveClassement(winnerNet);

		setListe_clients(new Hashtable<String, ClientInterface>());
		game = new Game();
	}

	/**
	 * methode permets de charger l'objet game recuper� en JSON 
	 * @param file
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Game chargerGame(File file) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		// JSON from file to Object
		Game g = mapper.readValue(file, Game.class);
		// JSON from String to Object
		return g;
	}


}