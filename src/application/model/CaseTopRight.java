package application.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CaseTopRight extends Case {

	@JsonCreator
	public CaseTopRight(@JsonProperty("nom")String nom) {
		super(nom);
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

	/**
	 * Retourne true si on à au moins une une chaine autour
	 * 
	 * @return
	 */
	public boolean surroundedByChains() {
		if ((this.getSouth().getEtat() >=2)|| (this.getWest().getEtat() >=2))
			return true;
		else
			return false;
	}

}
