package application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Plateau implements Serializable {

	private Case[][] plateau;

	public Plateau() {
		plateau = new Case[9][12];
		String[] ligne = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
		String[] colonne = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
		
		//initialisation tout les cases
		for (int i = 1; i < plateau.length-1; i++) {
			for (int j = 1; j < plateau[0].length-1; j++) {
				plateau[i][j] = new Case(ligne[i] + colonne[j]);
			}
		}
			
		for(int y=0;y<12;y++){
			switch (y){
			case 0:
				plateau[0][y]=new CaseTopLeft(ligne[0] + colonne[y]);
				for (int ligneTab = 1 ; ligneTab < 8 ; ligneTab++)
				{
					plateau[ligneTab][y]=new CaseLeft(ligne[ligneTab] + colonne[y]);
				}
				plateau[8][y]=new CaseBotLeft(ligne[8] + colonne[y]);
				break;
			case 11 :
				plateau[0][y]= new CaseTopRight(ligne[0] + colonne[y]);
				for (int ligneTab = 1 ; ligneTab < 8 ; ligneTab++)
				{
					plateau[ligneTab][y]=new CaseRight(ligne[ligneTab] + colonne[y]);
				}
				plateau[8][y]=new CaseBotRight(ligne[8] + colonne[y]);
				break;
			default : 
				plateau[0][y]=new CaseTop(ligne[0] + colonne[y]);
				plateau[8][y]=new CaseBot(ligne[8] + colonne[y]);
				break;
			}
			
		}
	}

	
	public void afiche(){
		for (int i=0;i<9;i++)
		{
			
			for(int j = 0;j<12;j++)
			{
				System.out.print(plateau[i][j]+"\t");
			}
			System.out.println("");
		}
	}
	public static void main(String [] args){
		Plateau p = new Plateau();
		
		p.afiche();
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

	public void updateCase(String text) {
		//retrouver la case depuis le text au formal "A2" ou "G11"
		//test v
		//plateau[2][2].setEtat(5);
	}
	
}