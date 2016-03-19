package application.model;

import java.util.ArrayList;

public class Plateau {

	private Case[][] plateau;

	public Plateau() {
		plateau = new Case[12][9];

	}

	/**
	 * Initialize le plateau
	 */
	public void init() {
		String[] tab = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
		String[] tab2 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
		for (int i = 0; i < plateau.length; i++) {
			for (int j = 0; j < plateau[0].length; j++) {
				plateau[i][j] = new Case(tab[j] + tab2[i]);
			}
		}
	}

	/**
	 * ajoute nbcase fois des cases noir aléatoirement dans des cases ou il n y a rien
	 * 
	 * @param nbJoueur
	 */
	public void generateCaseNoir(int nbCase) {
		int nbNoir = 0;
		int random1;
		int random2;

		while (nbNoir != nbCase) {
			random1 = (int) (Math.random() * (10 - 0)) + 0;
			random2 = (int) (Math.random() * (13 - 0)) + 0;
			if (this.plateau[random1][random2].getEtat() == 0) {
				this.plateau[random1][random2].setEtat(-1);
				nbNoir++;
			}
		}
	}
	
	/**
	 * ajoute 1 case noir aléatoirement sur le plateau
	 * 
	 * @param nbJoueur
	 */
	public void generate1CaseNoir() {
		int nbNoir = 0;
		int random1;
		int random2;

		while (nbNoir < 1) {
			random1 = (int) (Math.random() * (10 - 0)) + 0;
			random2 = (int) (Math.random() * (13 - 0)) + 0;
			if (this.plateau[random1][random2].getEtat() == 0) {
				this.plateau[random1][random2].setEtat(-1);
				nbNoir++;
			}
		}
	}
	
	
	/**
	 * La methode retourne les cases noir
	 * @return
	 */
	public ArrayList<Case> getCaseNoir(){
		ArrayList<Case> casesNoir=new ArrayList<Case>();
		for (int i = 0; i < plateau.length; i++) {
			for (int j = 0; j < plateau[0].length; j++) {
				if(plateau[i][j].getEtat()==-1){
					casesNoir.add(plateau[i][j]);	
				}
			}
		}
		return casesNoir;
	}

	public Case[][] getPlateau() {
		return plateau;
	}

	public void setPlateau(Case[][] plateau) {
		this.plateau = plateau;
	}
	
}