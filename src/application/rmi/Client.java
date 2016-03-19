package application.rmi;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


import application.control.PlateauController;
import application.view.ClientViewConnexion;

public class Client extends UnicastRemoteObject implements ClientInterface {
	
	PlateauController plateauController;
	private String pseudo;
	
	public Client(ServeurInterface serveur) throws Exception {
		Logger.getLogger("Client").log(Level.INFO, "Nouveau client");
	}
	
	public Client (String pseudo) throws Exception{
		Logger.getLogger("Client").log(Level.INFO, "Nouveau client enregistré dans le serveur.");
		this.pseudo=pseudo;
	}

	/*
	 * La méthode receive est appelée par le serveur. Mise à jour du plateau de
	 * jeu du client avec le game passé en paramètre.
	 */
	public void receive(Game g) throws RemoteException {
		// TODO Auto-generated method stub

	}
	//public void pseudo
	public static void main(String[] args) throws Exception {
		System.setProperty("java.security.policy","file:./security.policy");
		System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname", "127.0.0.1");
		System.setProperty("java.rmi.server.codebase","file:./bin/");
		ClientViewConnexion view = new ClientViewConnexion();
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
