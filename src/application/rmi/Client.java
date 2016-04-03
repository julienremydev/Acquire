package application.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import application.control.PlateauController;
import application.model.Case;
import application.view.ClientView;

public class Client extends UnicastRemoteObject implements ClientInterface {

	private static final long serialVersionUID = -3375116109320568837L;

	private PlateauController plateauController;
	
	private ServeurInterface serveur;
	
	private String pseudo;
	
	
	public Client () throws Exception{
	}

	/*
	 * La méthode receive est appelée par le serveur. Mise à jour du plateau de
	 * jeu du client avec le game passé en paramètre.
	 */
	public void receive(Game g) throws RemoteException {
		plateauController.setGame(g);
	}
	
	public void receiveTchat(String s) {
		plateauController.setChat(s);
	}
	public void setBEnable(boolean b) throws RemoteException{
		plateauController.setBEnable(b);
	}
	public static void main(String[] args) throws Exception {
		System.setProperty("java.security.policy","file:./security.policy");
		System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname", "127.0.0.1");
		System.setProperty("java.rmi.server.codebase","file:./bin/");
		ClientView view = new ClientView();
		view.lancer();
	}
	
	public void sendServeurAction() {
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

	public void sendCase(String text) throws RemoteException {
		this.serveur.getCasePlayed(text);
		
	}
	@Override
	public HashMap<String, Case> getMain() throws RemoteException {
		return null;
	}

}
