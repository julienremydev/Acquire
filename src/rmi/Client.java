package rmi;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import control.GameController;
import control.PlateauController;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import view.ClientView;
import view.JfxUtils;

public class Client extends UnicastRemoteObject implements ClientInterface {

	private ServeurInterface serveur;

	private GameController controller;

	public Client(ServeurInterface serveur) throws Exception {
		setServeur(serveur);
		Logger.getLogger("Client").log(Level.INFO, "Nouveau client");
		controller = new GameController();
	}

	/*
	 * La méthode receive est appelée par le serveur. Mise à jour du plateau de
	 * jeu du client avec le game passé en paramètre.
	 */
	public void receive(Game g) throws RemoteException {
		// TODO Auto-generated method stub

	}
	//
	public static void main(String[] args) throws Exception {
		System.setProperty("java.security.policy","file:./security.policy");
		System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname", "127.0.0.1");
		System.setProperty("java.rmi.server.codebase","file:./bin/");
		new ClientView().lancer();
	}

	public ServeurInterface getServeur() {
		return serveur;
	}

	public void setServeur(ServeurInterface serveur) {
		this.serveur = serveur;
	}


}
