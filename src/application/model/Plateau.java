package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Plateau implements Serializable {

	
	private HashMap<String, Case> plateauMap;

	public static String[] ligne = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
	public static String[] colonne = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
	
	private Case[][] plateauTab;
	public Plateau() {
		plateauMap = new HashMap<String, Case>();
		//plateauTab = new Case[9][12];
		
		
		
			// TopLeft
			//plateauTab[0][0] = new CaseTopLeft(ligne[0] + colonne[0]);
			plateauMap.put(ligne[0] + colonne[0], new CaseTopLeft(ligne[0] + colonne[0]));
			
			
			// BotLeft
			//plateauTab[ligne.length - 1][0] = new CaseBotLeft(ligne[ligne.length - 1] + colonne[0]);
			plateauMap.put(ligne[ligne.length - 1] + colonne[0], new CaseBotLeft(ligne[ligne.length - 1] + colonne[0]));
			
			// TopRight
			//plateauTab[0][colonne.length - 1] = new CaseTopRight(ligne[0] + colonne[colonne.length - 1]);
			plateauMap.put(ligne[0] + colonne[colonne.length - 1], new CaseTopRight(ligne[0] + colonne[colonne.length - 1]));
			
			// BotRight
			//plateauTab[ligne.length - 1][colonne.length - 1] = new CaseBotRight(ligne[ligne.length - 1] + colonne[colonne.length - 1]);
			plateauMap.put(ligne[ligne.length - 1] + colonne[colonne.length - 1], new CaseBotRight(ligne[ligne.length - 1] + colonne[colonne.length - 1]));

			for (int x = 0; x <= colonne.length - 1; x++) {

				// Init TOP et BOT
				if (x >= 1 && x <= colonne.length - 1 - 1) {
					//plateauTab[0][x] = new CaseTop(ligne[0] + colonne[x]);
					plateauMap.put(ligne[0] + colonne[x], new CaseTop(ligne[0] + colonne[x]));
					
					//plateauTab[ligne.length - 1][x] = new CaseBot(ligne[ligne.length - 1] + colonne[x]);
					plateauMap.put(ligne[ligne.length - 1] + colonne[x], new CaseBot(ligne[ligne.length - 1] + colonne[x]));
					
					
				}

				// Init Left et Right
				if (x >= 1 && x <= ligne.length - 1 - 1) {
					//plateauTab[x][0] = new CaseLeft(ligne[x] + colonne[0]);
					plateauMap.put(ligne[x] + colonne[0], new CaseLeft(ligne[x] + colonne[0]));
					
					//plateauTab[x][colonne.length - 1] = new CaseRight(ligne[x] + colonne[colonne.length - 1]);
					plateauMap.put(ligne[x] + colonne[colonne.length - 1], new CaseRight(ligne[x] + colonne[colonne.length - 1]));
				}

				// Init le reste les cases de milieu
				if (x >= 1 && x <= ligne.length - 1 - 1) {
					for (int i = 1; i <= colonne.length - 1 - 1; i++) {
						//plateauTab[x][i] = new Case(ligne[x] + colonne[i]);
						plateauMap.put(ligne[x] + colonne[i], new Case(ligne[x] + colonne[i]));
					}
				}
			}

			for (int i = 0; i <= ligne.length - 1; i++) {
				
				for (int j = 0; j <= colonne.length - 1; j++) {
					if (plateauMap.get(ligne[i]+colonne[j]) instanceof CaseTopLeft) {
						
						plateauMap.get(ligne[i]+colonne[j]).setNeighbours(null, plateauMap.get(ligne[i+1]+colonne[j]), plateauMap.get(ligne[i]+colonne[j + 1]), null);
					}

					if (plateauMap.get(ligne[i]+colonne[j]) instanceof CaseTopRight) {
						
						plateauMap.get(ligne[i]+colonne[j]).setNeighbours(null, plateauMap.get(ligne[i+1]+colonne[j]), null, plateauMap.get(ligne[i]+colonne[j - 1]));
					}

					if (plateauMap.get(ligne[i]+colonne[j]) instanceof CaseTop) {
						
						plateauMap.get(ligne[i]+colonne[j]).setNeighbours(null, plateauMap.get(ligne[i]+colonne[j + 1]), plateauMap.get(ligne[i]+colonne[j + 1]), plateauMap.get(ligne[i]+colonne[j - 1]));
					}

					if (plateauMap.get(ligne[i]+colonne[j]) instanceof CaseBotLeft) {
						
						plateauMap.get(ligne[i]+colonne[j]).setNeighbours(plateauMap.get(ligne[i - 1]+colonne[j]), null, plateauMap.get(ligne[i]+colonne[j + 1]), null);
					}

					if (plateauMap.get(ligne[i]+colonne[j]) instanceof CaseBotRight) {
						
						plateauMap.get(ligne[i]+colonne[j]).setNeighbours(plateauMap.get(ligne[i - 1]+colonne[j]), null, null, plateauMap.get(ligne[i]+colonne[j - 1]));
					}

					if (plateauMap.get(ligne[i]+colonne[j]) instanceof CaseBot) {
						
						plateauMap.get(ligne[i]+colonne[j]).setNeighbours(plateauMap.get(ligne[i - 1]+colonne[j]), null, plateauMap.get(ligne[i]+colonne[j + 1]), plateauMap.get(ligne[i]+colonne[j - 1]));
					}

					if (plateauMap.get(ligne[i]+colonne[j]) instanceof CaseLeft) {
						
						plateauMap.get(ligne[i]+colonne[j]).setNeighbours(plateauMap.get(ligne[i - 1]+colonne[j]), plateauMap.get(ligne[i+1]+colonne[j]), plateauMap.get(ligne[i]+colonne[j + 1]), null);
					}

					if (plateauMap.get(ligne[i]+colonne[j]) instanceof CaseRight) {
						
						plateauMap.get(ligne[i]+colonne[j]).setNeighbours(plateauMap.get(ligne[i - 1]+colonne[j]), plateauMap.get(ligne[i+1]+colonne[j]), null, plateauMap.get(ligne[i]+colonne[j - 1]));
					}

					if ((i >= 1) && (j >= 1) && (i <= ligne.length - 1 - 1)
							&& (j <= colonne.length - 1 - 1)) {	
						plateauMap.get(ligne[i]+colonne[j]).setNeighbours(plateauMap.get(ligne[i - 1]+colonne[j]), plateauMap.get(ligne[i+1]+colonne[j]), plateauMap.get(ligne[i]+colonne[j + 1]), plateauMap.get(ligne[i]+colonne[j - 1]));
					}
				}
			}
		}
	

	public void affichePlateau() {
		String val = "";
		for (int i = 0; i <= Plateau.ligne.length - 1; i++) {
			for (int j = 0; j <= Plateau.colonne.length - 1; j++) {
				if( plateauMap.get(ligne[i]+colonne[j]) != null){
					val += plateauMap.get(ligne[i]+colonne[j]).toString() + "			";
				}
			}
			val += "\n";
		}
		System.out.println(val);
	}
	
	
	public void updateCase(String text) {
		plateauMap.get(text).setEtat(1);
	}

	public Case getCase(String text) {
		return plateauMap.get(text);
	}

	public static void main(String[] Args) {
		Plateau p = new Plateau();
		p.affichePlateau();
		
	}
}