package application.rmi;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.control.GameController;
import application.control.PlateauController;
import application.view.ClientView;
import application.view.JfxUtils;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Client extends UnicastRemoteObject implements ClientInterface {
	
	public Client(ServeurInterface serveur) throws Exception {
		Logger.getLogger("Client").log(Level.INFO, "Nouveau client");
	}
	
	public Client () throws Exception{
		
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
		Client client = new Client();
		ClientView view = new ClientView();
		view.lancer();
	}
	
	public void sendServeurAction() {
	}


}
