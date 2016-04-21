package application.model;

public class CaseTopRight extends Case {

	public CaseTopRight(String n){
		super(n);
	}
	
	

	/**
	 * Permet de savoir si les cases autour de la notre sont vides
	 * 
	 * @return
	 */
	public boolean surroundedByNothing() {
		if ((this.getSouth().getEtat() == 0) && (this.getWest().getEtat() == 0))
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
		if ((this.getSouth().getEtat() == 1)|| (this.getWest().getEtat() == 1))
			return true;
		else
			return false;
	}

	@Override
	public boolean surroundedByChains() {
		// TODO Auto-generated method stub
		return false;
	}

}
