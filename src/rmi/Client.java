package rmi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Client extends UnicastRemoteObject implements ClientInterface {
	private ServeurInterface serveur;
	private Pane view;
	ControllerGAME controller;

	public Client(ServeurInterface serveur) throws IOException {
		setServeur(serveur);
		Logger.getLogger("Client").log(Level.INFO, "Nouveau client");
		showView();
	}

	public Parent getView() {
		return view;
	}
	
	/*
	 * La m�thode receive est appel�e par le serveur. Mise � jour du plateau de
	 * jeu du client avec le game pass� en param�tre.
	 */
	public void receive(Game g) throws RemoteException {
		// TODO Auto-generated method stub

	}

	
	public void showView() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Client.class.getResource("game.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		view = root;

		// Association du contr�leur � l'application.
		controller = loader.getController();
		controller.setClient(this);
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		System.setProperty("java.security.policy","file:./security.policy");
		System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname", "127.0.0.1");
		System.setProperty("java.rmi.server.codebase","file:./bin/");
		Main main = new Main();
		main.lancer();
	}

	public ServeurInterface getServeur() {
		return serveur;
	}

	public void setServeur(ServeurInterface serveur) {
		this.serveur = serveur;
	}


}
