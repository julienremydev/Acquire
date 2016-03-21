package application.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import application.model.Plateau;

public interface ServeurInterface extends Remote{

	ClientInterface register(String p) throws Exception;

	void distribution(Game g) throws RemoteException;

	void getCasePlayed(String text) throws RemoteException;

}
