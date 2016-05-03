package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Case implements Serializable {

	private static final long serialVersionUID = -7478757850540161294L;
	
	@JsonBackReference("case-north")
	private Case north;
	@JsonBackReference("case-south")
	private Case south;
	@JsonBackReference("case-east")
	private Case east;
	@JsonBackReference("case-west")
	private Case west;
	//-2 pas de creation de chaine possible -1 injouable ; 0 vide ; 1 hotel ; 2-8 chaine
	private int etat;
	private String nom;

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNom(){
		return this.nom;
	}

	@JsonCreator
	public Case(){
		this.etat=0;
	}

	@JsonCreator
	public Case(@JsonProperty("nom")String nom) {
		this.etat = 0;
		this.nom = nom;
		this.north = null;
		this.south = null;
		this.east = null;
		this.west = null;
	}

	public Integer getEtat() {
		return etat;
	}

	/**
	 * Changement de l'etat d'une case
	 * 
	 * @param etat
	 */
	public void setEtat(int etat) {
		this.etat = etat;
	}
	/**
	 * Méthode appelée par l'initialisation afin d'affecter les cases adjscente à notre case
	 * @param N
	 * @param S
	 * @param E
	 * @param W
	 */
	public void setNeighbours(Case N, Case S, Case E, Case W) {
		this.north = N;
		this.south = S;
		this.east = E;
		this.west = W;
	}


	/**
	 * Permet de savoir si les cases autour de la notre sont vides
	 * 
	 * @return
	 */
	public boolean surroundedByNothing() {
		if ((this.getNorth().getEtat() == 0) && (this.getEast().getEtat() == 0) && (this.getSouth().getEtat() == 0)
				&& (this.getWest().getEtat() == 0))
			return true;
		else
			return false;
	}

	/**
	 * Retourne true si on à au moins un hotel autour
	 * 
	 * @return
	 */
	public boolean surroundedByHotels() {
		if ((this.getNorth().getEtat() == 1) || (this.getEast().getEtat() == 1) || (this.getSouth().getEtat() == 1)
				|| (this.getWest().getEtat() == 1))
			return true;
		else
			return false;
	}

	/**
	 * Retourne true si on à au moins une une chaine autour
	 * 
	 * @return
	 */
	public boolean surroundedByChains() {
		if ((this.getNorth().getEtat() >= 2) || (this.getEast().getEtat() >= 2) || (this.getSouth().getEtat() >= 2)
				|| (this.getWest().getEtat() >= 2))
			return true;
		else
			return false;
	}

	/**
	 * Retourne un tableau des cases adjascentes non nulles
	 * 
	 * @param cN
	 * @param cS
	 * @param cE
	 * @param cW
	 * @return
	 */
	public ArrayList<Case> tabAdjascent() {
		ArrayList<Case> tab = new ArrayList<Case>();
		if (this.getNorth() != null && this.getNorth().getEtat() != 0)
			tab.add(this.getNorth());
		if (this.getSouth() != null && this.getSouth().getEtat() != 0)
			tab.add(this.getSouth());
		if (this.getEast() != null && this.getEast().getEtat() != 0)
			tab.add(this.getEast());
		if (this.getWest() != null && this.getWest().getEtat() != 0)
			tab.add(this.getWest());
		return tab;
	}
	
	/**
	 * Retourne vrai si les cases adjacentes (tableau) à notre cases sont de la
	 * même couleur (donc même chaîne, donc même etat)
	 * 
	 * @param tab
	 * @param taille
	 * @return
	 */
	public boolean sameColorsArround(ArrayList<Case> tab, int taille) {
		switch (taille) {
		case 2:
			if (tab.get(0).getEtat() == tab.get(1).getEtat())
				return true;
			else
				return false;
		case 3:
			if (tab.get(0).getEtat() == tab.get(1).getEtat() && tab.get(1).getEtat() == tab.get(2).getEtat())
				return true;
			else
				return false;
		case 4:
			if (tab.get(0).getEtat() == tab.get(1).getEtat() && tab.get(1).getEtat() == tab.get(2).getEtat()
			&& tab.get(2).getEtat() == tab.get(3).getEtat())
				return true;
			else
				return false;
		default:
			return true;
		}
	}

	/**
	 * Comparaison de cases en fonction de leur noms
	 * 
	 * @param c
	 * @return
	 */
	public boolean equals(Case c) {
		if(c != null)
		{
			if (this.getNom().compareTo(c.getNom()) == 0) {
				return true;
			} else {
				return false;
			}
		}
		else
			return false;
		
	}

	public String toString() {
		return this.nom;
	}

	/*
	 * Getters et setters des cases adjascents 
	 */
	public Case getNorth() {
		return north;
	}

	public void setNorth(Case north) {
		this.north = north;
	}

	public Case getSouth() {
		return south;
	}

	public void setSouth(Case south) {
		this.south = south;
	}

	public Case getEast() {
		return east;
	}

	public void setEast(Case east) {
		this.east = east;
	}

	public Case getWest() {
		return west;
	}

	public void setWest(Case west) {
		this.west = west;
	}
}
