package model;

public class Case {

	private Case north;
	private Case south;
	private Case east;
	private Case west;
	// -1 injouable ; 0 vide ; 1 hotel ; 2-8 chaine
	private static int etat;
	
	public Case() {
		this.etat = 0;
	}
	
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
	public static int getEtat() {
		return etat;
	}
	/**
	 * Changement de l'etat d'une case
	 * @param etat
	 */
	public static void setEtat(int etat) {
		Case.etat = etat;
	}
	/**
	 * Regarde les différents cas possible des états adjacents à la case.
	 * Appelle les fonctions qui vont bien à chaque fois.
	 */
	public void lookCase(){
		// doit d'abord regarder l'etat des 4 cases adjascentes
		int northState = this.north.getEtat();
		int southState = this.south.getEtat();
		int eastState = this.east.getEtat();
		int westState = this.west.getEtat();
		/*
		 * cas rien autour
		 * cas 1 hotel
		 * cas 2 hotels
		 * cas 3 hotels
		 * cas 4 hotels
		 * 1 hotel 1 chaine
		 * 1 h 2 c
		 * 1 h 3 c
		 */
		
		
	}
	
}
