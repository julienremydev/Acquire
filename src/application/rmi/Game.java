package application.rmi;

import java.io.Serializable;
import java.util.ArrayList;

import application.model.Case;
import application.model.Chaine;
import application.model.Plateau;
import application.model.TableauDeBord;
import application.model.TypeChaine;

public class Game implements Serializable{

	private static final long serialVersionUID = 1964688809311788179L;
	
	private Plateau plateau;
	
	private TableauDeBord tableauDeBord;

	/**
	 * Constructeur permettant l'initialisation de la liste de chaîne accessible par plateau et tableauDeBord
	 * Initialisation du plateau et du TableauDeBord
	 */
	public Game(){
		super();
		
		Chaine sackson = new Chaine (TypeChaine.SACKSON);
		Chaine zeta = new Chaine (TypeChaine.ZETA);
		Chaine hydra = new Chaine (TypeChaine.HYDRA);
		Chaine fusion = new Chaine (TypeChaine.FUSION);
		Chaine america = new Chaine (TypeChaine.AMERICA);
		Chaine phoenix = new Chaine (TypeChaine.PHOENIX);
		Chaine quantum= new Chaine (TypeChaine.QUANTUM);
		
		this.plateau=new Plateau();
		this.tableauDeBord = new TableauDeBord();
		
		getListeChaine().add(sackson);
		getListeChaine().add(zeta);
		getListeChaine().add(hydra);
		getListeChaine().add(fusion);
		getListeChaine().add(america);
		getListeChaine().add(phoenix);
		getListeChaine().add(quantum);
		
		
	}
	
	/**
	 * Creation d'une nouvelle chaine, Changement de l'etat des hotels en chaîne, ajout de la chaine, à la liste de chaîne.
	 * @param listeHotels
	 * @param nomChaine
	 */
	public void creationChaine(ArrayList<Case> listeHotels, TypeChaine nomChaine){
		// Changement des états des hotels pour qu'ils appartiennent à la même chaine
		for(Case hotelToChaine : listeHotels){
			getListeChaine().get(nomChaine.getNumero()-2).addCase(getPlateau().getCase(hotelToChaine.getNom()));
		}
	}
	public Plateau getPlateau() {
		return this.plateau;
	}
	
	public TableauDeBord getTableau() {
		return this.tableauDeBord;
	}
	
	public ArrayList<Chaine> getListeChaine(){
		return tableauDeBord.getListeChaine();
	}

}
