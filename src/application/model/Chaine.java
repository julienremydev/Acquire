package application.model;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Chaine {
	private String nomChaine;
	private ArrayList<Case> listeCase;
	private static final int nbActionTotal = 25;
	private int nbActionRestante;
	
	/**
	 * Constructeur permettant de definir le nom des chaines d hotels
	 * initialisation des autres attributs a leur valeur par defaut
	 * @param nom 
	 */
	public Chaine(String nom){
		this.nomChaine = nom;
		this.nbActionRestante = 25;
		this.listeCase = new ArrayList<Case>();
	}
	
	/**
	 * fonction permettant d ajouter une case a la liste de case de la chaine
	 * @param c
	 */
	public void addCase(Case c){
		this.getListeCase().add(c);
	}

	/**
	 * fonction permettant au joueur d acheter des actions et met a jour le nombre d action restante
	 * @param nb : nombre d action voulant être acheter par le joueur
	 * @return nombre effectivement acheter
	 */
	public int achatActionJoueur(int nb){
		int res = nb;
		
		if(this.getNbActionRestante()-nb < 0){ // on ne peut pas avoir un nombre daction restante negatif
			this.setNbActionRestante(0);
			res = this.getNbActionRestante();
		} else {
			this.setNbActionRestante(this.getNbActionRestante()-nb);
		}
		
		return res;
	}
	
	/**
	 * fonction retournant la taille de la chaine d hotel
	 * @return taille de la chaine dhotel
	 */
	public int tailleChaine(){
		return this.getListeCase().size();
	}
	
	
	/*
	 * Liste des Getters et Setters des tous les attributs
	 */
	public String getNomChaine() {
		return nomChaine;
	}

	public void setNomChaine(String nomChaine) {
		this.nomChaine = nomChaine;
	}

	public ArrayList<Case> getListeCase() {
		return listeCase;
	}

	public void setListeCase(ArrayList<Case> listeCase) {
		this.listeCase = listeCase;
	}

	public int getNbActionRestante() {
		return nbActionRestante;
	}

	public void setNbActionRestante(int nbActionRestante) {
		this.nbActionRestante = nbActionRestante;
	}

	public static int getNbactiontotal() {
		return nbActionTotal;
	}
}
