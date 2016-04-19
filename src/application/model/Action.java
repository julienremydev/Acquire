package application.model;

public class Action {

	private int choix; // 0 = choixCouleurNouvelleChaine / 1 choixCouleurFusionChaine
	
	private int couleur;
	
	public int getChoix() {
		return choix;
	}

	public void setChoix(int choix) {
		this.choix = choix;
	}

	public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

	public Action(int type) {
		choix = type;
	}

}
