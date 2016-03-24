package application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Plateau implements Serializable {


	private ArrayList<Case> plateau ;

	public Plateau() {
		plateau = new ArrayList<Case>();
		Case[][] plateauTab = new Case[12][9];
		String[] ligne = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
		String[] colonne = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

		//initialisation tout les cases
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 7; j++) {
				plateauTab[i][j] = new Case(ligne[j] + colonne [i]);
			}
		}
		for(int y=0;y<12;y++){
			switch (y){
			case 0:
				plateauTab[0][0]=new CaseTopLeft(ligne[0] + colonne[0]);
				for (int ligneTab = 1 ; ligneTab < 8 ; ligneTab++)
				{
					plateauTab[y][ligneTab]=new CaseLeft(ligne[ligneTab] + colonne[y]);
				}
				plateauTab[y][8]=new CaseBotLeft(ligne[8] + colonne[y]);
				break;
			case 11 :
				plateauTab[y][0]= new CaseTopRight(ligne[0] + colonne[y]);
				for (int ligneTab = 1 ; ligneTab < 8 ; ligneTab++)
				{
					plateauTab[y][ligneTab]=new CaseRight(ligne[ligneTab] + colonne[y]);
				}
				plateauTab[y][8]=new CaseBotRight(ligne[8] + colonne[y]);
				break;
			default : 
				plateauTab[y][0]=new CaseTop(ligne[0] + colonne[y]);
				plateauTab[y][8]=new CaseBot(ligne[8] + colonne[y]);
				break;
			}
		}
		for (int i=0;i<12;i++) {
			for (int j=0;j<9;j++) {
				plateau.add(plateauTab[i][j]);
			}
		}
	}

	/**
	 * ajoute nbcase fois des cases noir aléatoirement dans des cases ou il n y a rien
	 * 
	 * @param nbJoueur
	 */
	
	//random dans list et pas tableau
	
	//	public void generateCaseNoir(int nbCase) {
	//		int nbNoir = 0;
	//		int random1;
	//		int random2;
	//
	//		while (nbNoir != nbCase) {
	//			random1 = (int) (Math.random() * (10 - 0)) + 0;
	//			random2 = (int) (Math.random() * (13 - 0)) + 0;
	//			if (this.plateauTab[random1][random2].getEtat() == 0) {
	//				this.plateauTab[random1][random2].setEtat(-1);
	//				nbNoir++;
	//			}
	//		}
	//	}

	public ArrayList<Case> getPlateau() {
		return plateau;
	}

	public void updateCase(String text) {
		if(plateau.contains(new Case(text)))
		{
			plateau.get(plateau.indexOf(new Case(text))).lookCase();
		}
		for(Case c : plateau)
		{
			if(c.getNom().compareTo(text)==0)
				c.setEtat(1);
		}
	}
}