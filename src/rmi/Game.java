package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import model.Plateau;
import model.TableauDeBord;

public class Game extends UnicastRemoteObject{

	private String nomPartie;
	
	private Plateau plateau;
	
	private TableauDeBord tableauDeBord;

	protected Game() throws RemoteException {
		super();
		nomPartie="bim";
	}
	
	public Plateau getPlateau() {
		return this.plateau;
	}

}
