package application.rmi;

import java.io.Serializable;
import java.util.ArrayList;

import application.model.Chaine;
import application.model.Plateau;
import application.model.TableauDeBord;
import application.model.TypeChaine;

public class Game implements Serializable{

	private static final long serialVersionUID = 1964688809311788179L;
	
	public ArrayList<Chaine> listeChaine = new ArrayList<Chaine> ();
	
	private Plateau plateau;
	
	private TableauDeBord tableauDeBord;

	/**
	 * Constructeur permettant l'initialisation de la liste de chaîne accessible par plateau et tableauDeBord
	 * Initialisation du plateau et du TableauDeBord
	 */
	protected Game(){
		super();
		
		Chaine sackson = new Chaine (TypeChaine.SACKSON);
		Chaine zeta = new Chaine (TypeChaine.ZETA);
		Chaine hydra = new Chaine (TypeChaine.HYDRA);
		Chaine fusion = new Chaine (TypeChaine.FUSION);
		Chaine america = new Chaine (TypeChaine.AMERICA);
		Chaine phoenix = new Chaine (TypeChaine.PHOENIX);
		Chaine quantum= new Chaine (TypeChaine.QUANTUM);
		
		
		listeChaine.add(sackson);
		listeChaine.add(zeta);
		listeChaine.add(hydra);
		listeChaine.add(fusion);
		listeChaine.add(america);
		listeChaine.add(phoenix);
		listeChaine.add(quantum);
		
		this.plateau=new Plateau();
		this.tableauDeBord = new TableauDeBord();
	}
	
	public Plateau getPlateau() {
		return this.plateau;
	}
	
	public TableauDeBord getTableau() {
		return this.tableauDeBord;
	}
	
	public ArrayList<Chaine> getListeChaine(){
		return this.listeChaine;
	}

}
