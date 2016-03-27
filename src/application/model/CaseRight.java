package application.model;

public class CaseRight extends  Case {

	public CaseRight(String nn, Case n,Case s,Case e,Case w){
		super(nn,n,s,e,w);
	}
	
	public CaseRight(String n){
		super(n);
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
