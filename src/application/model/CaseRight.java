package application.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CaseRight extends  Case {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6730800492916075695L;

	@JsonCreator
	public CaseRight(@JsonProperty("nom")String nom) {
		super(nom);
	}
	

	/**
	 * Permet de savoir si les cases autour de la notre sont vides
	 * 
	 * @return
	 */
	public boolean surroundedByNothing() {
		if ((this.getNorth().getEtat() <= 0)  && (this.getSouth().getEtat() <= 0) && (this.getWest().getEtat() <= 0))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * Retourne true si on � au moins une une chaine autour
	 * @return
	 */
	public boolean surroundedByChains() {
		if ((this.getNorth().getEtat() >=2) || (this.getSouth().getEtat() >=2)|| (this.getWest().getEtat()>=2))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * Retourne true si on � au moins un hotel autour
	 * @return
	 */
	public boolean surroundedByHotels() {
		if ((this.getNorth().getEtat() == 1) || (this.getSouth().getEtat() == 1)|| (this.getWest().getEtat() == 1))
			return true;
		else
			return false;
	}

}
