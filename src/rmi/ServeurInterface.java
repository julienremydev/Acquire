package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Plateau;

public interface ServeurInterface extends Remote{

	void register(ClientInterface c) throws RemoteException;

	void distribution(Game g) throws RemoteException;

	void getCasePlayed(String text) throws RemoteException;

	Game getCurrentGame() throws RemoteException;
}
