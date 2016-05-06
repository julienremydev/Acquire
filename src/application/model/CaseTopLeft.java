package application.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CaseTopLeft extends Case {

	@JsonCreator
	public CaseTopLeft(@JsonProperty("nom")String nom) {
		super(nom);
	}

	/**
	 * Permet de savoir si les cases autour de la notre sont vides
	 * 
	 * @return
	 */
	public boolean surroundedByNothing() {
		if ((this.getSouth().getEtat() <= 0) && (this.getEast().getEtat() <= 0))
			return true;
		else
			return false;
	}

	/**
	 * Retourne true si on � au moins un hotel autour
	 * 
	 * @return
	 */
	public boolean surroundedByHotels() {
		if ((this.getEast().getEtat() == 1) || (this.getSouth().getEtat() == 1))
			return true;
		else
			return false;
	}

	/**
	 * Retourne true si on � au moins une une chaine autour
	 * 
	 * @return
	 */
	public boolean surroundedByChains() {
		if ((this.getEast().getEtat() >=2) || (this.getSouth().getEtat() >=2))
			return true;
		else
			return false;
	}

}
