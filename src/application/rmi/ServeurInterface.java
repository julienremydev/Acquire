package application.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import application.model.Action;
import application.model.TypeChaine;

public interface ServeurInterface extends Remote{

	String register(ClientInterface client, String p, boolean loadJSON) throws Exception;

	void distribution() throws RemoteException;
	
	void distributionTchat(String pseudo, String s) throws RemoteException;

	void getCasePlayed(String text, String pseudo) throws RemoteException;
	
	void creationChaineServeur(Action a,TypeChaine c) throws RemoteException;
	
	void logout (String p) throws RemoteException;

	void setLancement() throws RemoteException;
	
	Game getGame() throws RemoteException;

	void nextTurn(String pseudo) throws RemoteException;
}
