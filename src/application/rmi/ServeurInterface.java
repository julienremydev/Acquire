package application.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import application.model.Plateau;

public interface ServeurInterface extends Remote{

	boolean register(ClientInterface client, String p) throws Exception;

	void distribution() throws RemoteException;
	
	void distributionTchat(String pseudo, String s) throws RemoteException;

	void getCasePlayed(String text) throws RemoteException;
	
	void logout (String p) throws RemoteException;

	void setLancement() throws RemoteException;
}
