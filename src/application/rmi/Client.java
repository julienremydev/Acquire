package application.rmi;

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
		System.setProperty("java.rmi.server.hostname", "127.0.0.1");
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
	
	public void receiveTchat(String s) {
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
	
	public void buyAction(HashMap<TypeChaine, Integer> actionAAcheter) throws RemoteException {
		this.serveur.achatAction(getPseudo(), actionAAcheter);
	}
	
	public void sendChoixCouleurFusionSameChaine (ArrayList<Chaine> listeChainePlateau, ArrayList<Chaine> listeChaineAModif, Chaine ChaineAbs, Case case1) throws RemoteException{
		this.serveur.choixCouleurFusion (listeChainePlateau,listeChaineAModif,ChaineAbs,case1);
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

	public void sauvegardePartie() throws RemoteException{
		serveur.clientSaveGame(getPseudo());
	}
	public void receiveGameForSave(Game game) throws RemoteException{
		this.plateauController.saveTheGame ( game );
	}
}
