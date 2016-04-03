package application.rmi;

import java.io.Serializable;

import application.model.Plateau;
import application.model.TableauDeBord;

public class Game implements Serializable{

	private static final long serialVersionUID = 1964688809311788179L;
	
	private Plateau plateau;
	
	private TableauDeBord tableauDeBord;

	protected Game(){
		super();
		this.plateau=new Plateau();
	}
	
	public Plateau getPlateau() {
		return this.plateau;
	}
	
	public TableauDeBord getTableau() {
		return this.tableauDeBord;
	}

}
