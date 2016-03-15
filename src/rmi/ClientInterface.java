package rmi;

import java.rmi.RemoteException;

public interface ClientInterface {

	void receive(Game g) throws RemoteException;
}
