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
		
		//initialisation tout les cases
		for (int i = 0; i < plateau.length; i++) {
			for (int j = 0; j < plateau[0].length; j++) {
				plateau[i][j] = new Case(tab[j] + tab2[i]);
						
			}
		}
		
		//creation bot right et left,top right et left
		for(int x=0;x<12;x++){
			if(x==0){
				plateau[0][x]=CaseTopLeft(tab[0] + tab2[x]);
				plateau[8][x]=CaseBotLeft(tab[8] + tab2[x]);
			}
			if(x==11){
				plateau[0][x]=CaseTopRight(tab[0] + tab2[x]);
				plateau[8][x]=CaseBotRight(tab[8] + tab2[x]);
			}
			plateau[0][x]=CaseTop(tab[0] + tab2[x]);
			plateau[8][x]=CaseBot(tab[8] + tab2[x]);
		}
		
		//creation case left et case right
		for(int x=1;x<11;x++){
			plateau[x][0]=CaseLeft(tab[x] + tab2[0]]);
			plateau[11][x]=CaseRight(tab[11] + tab2[x]]);
		}
		for(int x=0;x<3;x++){
		this.generate1CaseNoir();
		}
	}

	/**
	 * ajoute nbcase fois des cases noir aléatoirement dans des cases ou il n y
	 * a rien
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
				this.plateau[random1][random2].setEtat(1);
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
				this.plateau[random1][random2].setEtat(1);
				nbNoir++;
			}
		}
	}

	public Case[][] getPlateau() {
		return plateau;
	}

	public void setPlateau(Case[][] plateau) {
		this.plateau = plateau;
	}

	public void updateCase(String text) {
		// retrouver la case depuis le text au formal "A2" ou "G11"
		// test v
		plateau[2][2].setEtat(5);
	}

}