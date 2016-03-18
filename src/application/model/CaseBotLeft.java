package application.model;

public class CaseBotLeft implements CaseInterface {
	private Case north;
	private Case east;
	
	private static int etat;
	
	public CaseBotLeft() {
		this.etat=0;
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
