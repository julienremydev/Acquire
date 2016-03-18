package application.model;

public class CaseBot implements CaseInterface {

	private Case north;
	private Case east;
	private Case west;
	// -1 injouable ; 0 vide ; 1 hotel ; 2-8 chaine
	private static int etat;
	
	
	public CaseBot() {
		this.etat = 0;
	}

	@Override
	public boolean surroundedByNothing() {
		// TODO Auto-generated method stub
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
