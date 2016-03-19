package application.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote{

	void receive(Game g) throws RemoteException;
	String getPseudo() throws RemoteException;
}
