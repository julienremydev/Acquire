package application.model;

public class CaseRight extends  Case {

	public CaseRight(String n){
		super(n);
	}
	

	/**
	 * Permet de savoir si les cases autour de la notre sont vides
	 * 
	 * @return
	 */
	public boolean surroundedByNothing() {
		if ((this.getNorth().getEtat() == 0)  && (this.getSouth().getEtat() == 0) && (this.getWest().getEtat() == 0))
			return true;
		else
			return false;
	}

	@Override
	public boolean surroundedByHotels() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean surroundedByChains() {
		// TODO Auto-generated method stub
		return false;
	}

}
