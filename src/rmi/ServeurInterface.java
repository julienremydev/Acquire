package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServeurInterface extends Remote{

	void register(ClientInterface c) throws RemoteException;

	void distribution(Game g) throws RemoteException;
}
