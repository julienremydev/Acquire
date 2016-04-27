package application.model;

public class CaseBotRight extends Case {

	public CaseBotRight(String n){
		super(n);
	}
	
	

	/**
	 * Permet de savoir si les cases autour de la notre sont vides
	 * 
	 * @return
	 */
	public boolean surroundedByNothing() {
		if ((this.getNorth().getEtat() == 0) && (this.getWest().getEtat() == 0))
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
		if ((this.getNorth().getEtat() == 1) || (this.getWest().getEtat() == 1))
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
		if ((this.getNorth().getEtat() >=2) || (this.getWest().getEtat() >=2))
			return true;
		else
			return false;
	}

}
