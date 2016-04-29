package application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Action implements Serializable{

	private int choix; // 0 = choixCouleurNouvelleChaine / 1 choixCouleurFusionChaine
	
	private int couleur;
	
	private ArrayList<Case> listeDeCaseAModifier;
	
	private ArrayList<Chaine> listeDeChaineAProposer;
	
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
	 * Constructeur, prend en parametre la liste des cases � modifier ainsi que le type du choix � effectuer
	 * @param listeDeCase
	 * @param type
	 */
	public Action(ArrayList<Case> listeDeCase, int type) {
		this.choix = type;
		this.setListeDeCaseAModifier(listeDeCase);
	}
	
	/**
	 * Constructeur, prend en parametre la liste des cases � modifier ainsi que le type du choix � effectuer
	 * @param listeDeCase
	 * @param type
	 */
	public Action(int type,ArrayList<Chaine> listeDeChaine) {
		this.choix = type;
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
	


}
