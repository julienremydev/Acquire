package application.rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import application.control.PlateauController;
import application.globale.Globals;
import application.model.Action;
import application.model.Case;
import application.model.Chaine;
import application.model.TypeChaine;
import application.view.ClientView;

public class Client extends UnicastRemoteObject implements ClientInterface {

	private static final long serialVersionUID = -3375116109320568837L;

	private PlateauController plateauController;
	
	private ServeurInterface serveur;
	
	private String pseudo;
	
	public Client () throws Exception{
	}
	
	public static void main(String[] args) throws Exception {
		System.setProperty("java.security.policy","file:./security.policy");
		System.setSecurityManager(new SecurityManager());
		//System.setProperty("java.rmi.server.hostname", "192.168.1.66");
		System.setProperty("java.rmi.server.codebase","file:./bin/");
		ClientView view = new ClientView();
		view.lancer();
	}

	/**
	 * La méthode receive est appelée par le serveur. Mise à jour du plateau de
	 * jeu du client avec le game passé en paramètre.
	 */
	public void receive(Game g) throws RemoteException {
		plateauController.setGame(g);
	}
	
	public void receiveTchat(ArrayList<String> s) {
		plateauController.setChat(s);
	}
	
	/**
	 * La méthode receiveAction est appelée par le serveur
	 * Permet au joueur de connaitre les actions possibles (fusion ou creation chaine)
	 */
	public void receiveAction(Game g) throws RemoteException{
		if ( g.getAction().getChoix() == Globals.choixActionCreationChaine )
			plateauController.setChoixCreationChaine(g);
		else if ( g.getAction().getChoix() == Globals.choixActionFusionSameSizeChaine )
			plateauController.setChoixFusionSameSizeChaine(g);
		else if ( g.getAction().getChoix() == Globals.choixActionFusionEchangeAchatVente )
			plateauController.setChoixFusionEchangeAchatVente(g);
	}
	public void receiveBuyAction(Game game) throws RemoteException {
		plateauController.setChoixAchatAction(game);
	}
	public void setBEnable(boolean b) throws RemoteException{
		plateauController.setBEnable(b);
	}

	/**
	 * Methode qui envoie la case joue par le client au serveur via son nom
	 * @param text
	 * @throws RemoteException
	 */
	public void sendCase(String text) throws RemoteException {
		this.serveur.getCasePlayed(text, pseudo);
		
	}
	/**
	 * Methode permettant l ajout d une chaine au plateau (choix de la couleur)
	 * @param a
	 * @param nomChaine
	 * @throws RemoteException
	 */
	public void pickColor(Action a, TypeChaine nomChaine) throws RemoteException {
		this.serveur.creationChaineServeur(nomChaine);
	}
	/**
	 * Achat d'actions
	 * @param actionAAcheter
	 * @throws RemoteException
	 */
	public void buyAction(HashMap<TypeChaine, Integer> actionAAcheter) throws RemoteException {
		this.serveur.achatAction(getPseudo(), actionAAcheter);
	}
	/**
	 * Permet de choisir la couleur de la chaine voulue pour la fusion de même taille
	 * @param listeChainePlateau
	 * @param listeChaineAModif
	 * @param ChaineAbs
	 * @param arrayList
	 * @throws RemoteException
	 */
	public void sendChoixCouleurFusionSameChaine (ArrayList<Chaine> listeChainePlateau, ArrayList<Chaine> listeChaineAModif, Chaine ChaineAbs, ArrayList<Case> arrayList) throws RemoteException{
		this.serveur.choixCouleurFusion (listeChainePlateau,listeChaineAModif,ChaineAbs,arrayList);
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo=pseudo;
	}

	public ServeurInterface getServeur() {
		return serveur;
	}

	public void setServeur(ServeurInterface serveur) {
		this.serveur = serveur;
	}
	
	public void setController(PlateauController plateauController) {
		this.plateauController=plateauController;
	}

	@Override
	public void turn() throws RemoteException {
		this.plateauController.setOn();
	}

	public void nextTurn() throws RemoteException {
		serveur.nextTurn(pseudo);
	}
	/**
	 * Sauvegarde la partie du client
	 * @throws IOException
	 */
	public void sauvegardePartie() throws IOException{
		serveur.clientSaveGame(getPseudo());
	}
	/**
	 * Permet de sauvegarder le game
	 */
	public void receiveGameForSave(Game game) throws IOException{
		this.plateauController.saveTheGame ( game );
	}
	/**
	 * Choix des Actions de fusions
	 * @param actions_fusions
	 * @param chaineAbsorbee
	 * @param chaineAbsorbante
	 * @param prix_action_absorbante
	 * @param prix_action_absorbee
	 * @throws RemoteException
	 */
	public void choiceFusionAction(HashMap<String, Integer> actions_fusions, Chaine chaineAbsorbee, Chaine chaineAbsorbante, int prix_action_absorbante, int prix_action_absorbee) throws RemoteException{
		serveur.choiceFusionAction(actions_fusions, chaineAbsorbee, chaineAbsorbante, pseudo, prix_action_absorbante, prix_action_absorbee);
	}

	public void isOver() throws RemoteException{
		serveur.isOver();
	}
	/**
	 * Classement des joueurs avec le gagnant
	 */
	public void receiveClassement(ArrayList<String> winner) throws RemoteException{
		plateauController.endingGame(winner);
	}

	public void sendEndGame() throws RemoteException{
		serveur.isOver();
		
	}
}
