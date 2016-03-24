package application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Plateau implements Serializable {


	private ArrayList<Case> plateau ;

	public Plateau() {
		plateau = new ArrayList<Case>();
		Case[][] plateauTab = new Case[9][12];
		String[] ligne = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
		String[] colonne = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

		//initialisation tout les cases
		for (int i = 1; i < plateauTab.length-1; i++) {
			for (int j = 1; j < plateauTab[0].length-1; j++) {
				plateauTab[i][j] = new Case(ligne[i] + colonne[j]);
			}
		}

		for(int y=0;y<12;y++){
			switch (y){
			case 0:
				plateauTab[0][y]=new CaseTopLeft(ligne[0] + colonne[y+1]);
				for (int ligneTab = 1 ; ligneTab < 8 ; ligneTab++)
				{
					plateauTab[ligneTab][y]=new CaseLeft(ligne[ligneTab] + colonne[y+1]);
				}
				plateauTab[8][y]=new CaseBotLeft(ligne[8] + colonne[y+1]);
				break;
			case 11 :
				plateauTab[0][y]= new CaseTopRight(ligne[0] + colonne[y+1]);
				for (int ligneTab = 1 ; ligneTab < 8 ; ligneTab++)
				{
					plateauTab[ligneTab][y]=new CaseRight(ligne[ligneTab] + colonne[y+1]);
				}
				plateauTab[8][y]=new CaseBotRight(ligne[8] + colonne[y+1]);
				break;
			default : 
				plateauTab[0][y]=new CaseTop(ligne[0] + colonne[y+1]);
				plateauTab[8][y]=new CaseBot(ligne[8] + colonne[y+1]);
				break;
			}
			
			for (int i = 1; i < plateauTab.length-1; i++) {
				for (int j = 0; j < plateauTab[0].length-1; j++) {
					System.out.println(plateauTab[i][j].getNom());
					
				}
			}
		}
	}





	/**
	 * ajoute nbcase fois des cases noir aléatoirement dans des cases ou il n y a rien
	 * 
	 * @param nbJoueur
	 */
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

	/**
	 * ajoute 1 case noir aléatoirement sur le plateau
	 * 
	 * @param nbJoueur
	 */
	/*
	public void generate1CaseNoir() {
		int nbNoir = 0;
		int random1;
		int random2;

		while (nbNoir < 1) {
			random1 = (int) (Math.random() * (10 - 0)) + 0;
			random2 = (int) (Math.random() * (13 - 0)) + 0;
			if (this.plateauTab[random1][random2].getEtat() == 0) {
				this.plateauTab[random1][random2].setEtat(-1);
				nbNoir++;
			}
		}
	}
	 */



	public ArrayList<Case> getPlateau() {
		return plateau;
	}



	public void updateCase(String text) {
		if(plateau.contains(new Case(text)))
		{
			System.out.println("yey");
			plateau.get(plateau.indexOf(new Case(text))).lookCase();
		}
		for(Case c : plateau)
		{
			System.out.println(plateau.size());
			if(c.getNom().compareTo(text)==0)
				c.lookCase();
		}


	}

}