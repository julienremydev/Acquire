package application.model;

import java.io.Serializable;
import java.util.ArrayList;

import application.globale.Globals;

public class Action implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1987155349689841351L;

	private int choix; // les choix sont dans la classe Globals
	
	private int couleur;
	
	private ArrayList<Case> listeDeCaseAModifier;
	
	private ArrayList<Chaine> listeDeChaineAProposer;
	
	private ArrayList<Chaine> listeChainesAbsorbees;
	
	private Chaine chaineAbsorbante;
	
	public int getChoix() {
		return choix;
	}

	public void setChoix(int choix) {
		this.choix = choix;
	}

	public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}
	/**
	 * Constructeur, prend en parametre la liste des cases à modifier ainsi que le type du choix à effectuer
	 * @param listeDeCase
	 * @param type
	 */
	public Action(ArrayList<Case> listeDeCase, int type) {
		this.choix = type;
		this.setListeDeCaseAModifier(listeDeCase);
	}
	
	/**
	 * Constructeur, prend en parametre la liste des cases à modifier ainsi que le type du choix à effectuer
	 * @param listeDeCase
	 * @param type
	 */
	public Action(int type,ArrayList<Chaine> listeDeChaine) {
		this.choix = type;
		if ( choix == Globals.choixActionFusionEchangeAchatVente)
			this.setListeChainesAbsorbees(listeDeChaine);
		else
			this.setListeDeChaineAProposer(listeDeChaine);
		
	}

	public ArrayList<Case> getListeDeCaseAModifier() {
		return listeDeCaseAModifier;
	}

	public void setListeDeCaseAModifier(ArrayList<Case> listeDeCaseAModifier) {
		this.listeDeCaseAModifier = listeDeCaseAModifier;
	}

	public ArrayList<Chaine> getListeDeChaineAProposer() {
		return listeDeChaineAProposer;
	}

	public void setListeDeChaineAProposer(ArrayList<Chaine> listeDeChaineAProposer) {
		this.listeDeChaineAProposer = listeDeChaineAProposer;
	}

	public ArrayList<Chaine> getListeChainesAbsorbees() {
		return listeChainesAbsorbees;
	}

	public void setListeChainesAbsorbees(ArrayList<Chaine> listeChainesAbsorbees) {
		this.listeChainesAbsorbees = listeChainesAbsorbees;
	}

	public Chaine getChaineAbsorbante() {
		return chaineAbsorbante;
	}

	public void setChaineAbsorbante(Chaine chaineAbsorbante) {
		this.chaineAbsorbante = chaineAbsorbante;
	}
	


}
