package application.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


import application.control.PlateauController;
import application.view.ClientView;

public class Client extends UnicastRemoteObject implements ClientInterface {
	
	PlateauController plateauController;
	private String pseudo;
	
	
	public Client (String pseudo) throws Exception{
		Logger.getLogger("Client").log(Level.INFO, "Nouveau client enregistr� dans le serveur.");
		this.pseudo=pseudo;
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
		this.pseudo = pseudo;
	}


}
