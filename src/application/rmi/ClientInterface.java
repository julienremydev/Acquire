package application.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

import application.model.Action;
import application.model.Case;

public interface ClientInterface extends Remote{

	void receive(Game g) throws RemoteException;
	
	String getPseudo() throws RemoteException;
	
	HashMap<String,Case> getMain() throws RemoteException;

	void receiveTchat(String tchat) throws RemoteException;

	void setBEnable(boolean b) throws RemoteException;

	void receiveData() throws RemoteException;
	
	void receiveAction(Action a, Game g) throws RemoteException;
	
}
