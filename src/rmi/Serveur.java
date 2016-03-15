package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;

public class Serveur extends UnicastRemoteObject implements ServeurInterface{

	private ArrayList<ClientInterface> liste_clients ;
	
	protected Serveur() throws RemoteException {
		 setListe_clients(new ArrayList<ClientInterface>());
	}


	/*
	 * Cette m�thode est appel�e lors de la connexion d'un client.
	 * Le client est ajout� � la liste des clients du serveur.
	 */
	public void register(ClientInterface c) throws RemoteException {
		getListe_clients().add(c);
		
	}

	/*
	 * Distribution � chaque client du game , � chaque modification du plateau et des variables du jeu.
	 */
	public void distribution(Game g) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public static void main (String args[]) throws RemoteException, MalformedURLException{
		System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname", "127.0.0.1");
		LocateRegistry.createRegistry(42000);
		Serveur serveur = new Serveur();
		Naming.rebind("rmi://127.0.0.1:42000/ABC", serveur);
	}
	
	
	public ArrayList<ClientInterface> getListe_clients() {
		return liste_clients;
	}


	public void setListe_clients(ArrayList<ClientInterface> liste_clients) {
		this.liste_clients = liste_clients;
	}

}