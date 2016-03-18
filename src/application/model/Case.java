package application.model;

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
		// par exemple : si askColor true && askchain false => this.demanderCouleur
		//				 si askColor true && askchain true  => this.demanderCouleur fusion
				
		boolean simpleCase = this.surroundedByNothing();
		boolean askColor = this.surroundedByHotels();
		boolean askChain = this.surroundedByChains();
		
		if(simpleCase)
			this.setEtat(1);;
		
		
	}
	/**
	 * Permet de savoir si les cases autour de la notre sont vides
	 * @return
	 */
	public boolean surroundedByNothing(){
		if((this.getNorth().getEtat()==0) && (this.getEast().getEtat()==0) && (this.getSouth().getEtat()==0) && (this.getWest().getEtat()==0))
			return true;
		else
			return false;
	}
	/**
	 * Retourne true si on à au moins un hotel autour
	 * @return
	 */
	public boolean surroundedByHotels(){
		if((this.getNorth().getEtat()==1) || (this.getEast().getEtat()==1) || (this.getSouth().getEtat()==1) || (this.getWest().getEtat()==1))
			return true;
		else
			return false;
	}
	/**
	 * Retourne true si on à au moins une une chaine autour
	 * @return
	 */
	public boolean surroundedByChains(){
		if((this.getNorth().getEtat()>=2) || (this.getEast().getEtat()>=2) || (this.getSouth().getEtat()>=2) || (this.getWest().getEtat()>=2))
			return true;
		else
			return false;
	}
	
	
}
