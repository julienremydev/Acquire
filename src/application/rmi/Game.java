package application.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import application.model.Plateau;
import application.model.TableauDeBord;

public class Game implements Serializable{

	private String nomPartie;
	
	private Plateau plateau;
	
	private TableauDeBord tableauDeBord;

	protected Game(){
		super();
		this.plateau=new Plateau();
	}
	
	public Plateau getPlateau() {
		return this.plateau;
	}

}
