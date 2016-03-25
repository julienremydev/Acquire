package application.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


import application.control.PlateauController;
import application.model.Case;
import application.view.ClientView;

public class Client extends UnicastRemoteObject implements ClientInterface {
	
	private PlateauController plateauController;
	
	private ServeurInterface serveur;
	
	private String pseudo;
	
	
	public Client () throws Exception{
	}

	/*
	 * La m�thode receive est appel�e par le serveur. Mise � jour du plateau de
	 * jeu du client avec le game pass� en param�tre.
	 */
	public void receive(Game g) throws RemoteException {
		plateauController.setGame(g);
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
