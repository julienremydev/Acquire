package application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Action implements Serializable{

	private int choix; // 0 = choixCouleurNouvelleChaine / 1 choixCouleurFusionChaine
	
	private int couleur;
	
	private ArrayList<Case> listeDeCaseAModifier;
	
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
		this.listeDeCaseAModifier = listeDeCase;
	}

}
