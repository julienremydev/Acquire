package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Plateau implements Serializable {

	private ArrayList<Case> plateauArray;

	private HashMap<String, Case> plateauMap;

	private Case[][] plateauTab;
	public Plateau() {
		plateauArray = new ArrayList<Case>();
		plateauMap = new HashMap<String, Case>();
		plateauTab = new Case[9][12];
		String[] ligne = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
		String[] colonne = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
		
		
			// TopLeft
			plateauTab[0][0] = new CaseTopLeft(ligne[0] + colonne[0]);
			// BotLeft
			plateauTab[plateauTab.length - 1][0] = new CaseBotLeft(ligne[plateauTab.length - 1] + colonne[0]);
			// TopRight
			plateauTab[0][plateauTab[0].length - 1] = new CaseTopRight(
					ligne[0] + colonne[plateauTab[0].length - 1]);
			// BotRight
			plateauTab[plateauTab.length - 1][plateauTab[0].length - 1] = new CaseBotRight(
					ligne[plateauTab.length - 1] + colonne[plateauTab[0].length - 1]);

			for (int x = 0; x <= plateauTab[0].length - 1; x++) {

				// Init TOP et BOT
				if (x >= 1 && x <= plateauTab[0].length - 1 - 1) {
					plateauTab[0][x] = new CaseTop(ligne[0] + colonne[x]);
					plateauTab[plateauTab.length - 1][x] = new CaseBot(
							ligne[plateauTab.length - 1] + colonne[x]);
				}

				// Init Left et Right
				if (x >= 1 && x <= plateauTab.length - 1 - 1) {
					plateauTab[x][0] = new CaseLeft(ligne[x] + colonne[0]);

					plateauTab[x][plateauTab[0].length - 1] = new CaseRight(
							ligne[x] + colonne[plateauTab[0].length - 1]);
				}

				// Init le reste les cases de milieu
				if (x >= 1 && x <= plateauTab.length - 1 - 1) {
					for (int i = 1; i <= plateauTab[0].length - 1 - 1; i++) {
						plateauTab[x][i] = new Case(ligne[x] + colonne[i]);
					}
				}
			}

			for (int i = 0; i <= plateauTab.length - 1; i++) {
				for (int j = 0; j <= plateauTab[0].length - 1; j++) {
					if (plateauTab[i][j] instanceof CaseTopLeft) {
						plateauTab[i][j].setNeighbours(new Case(), plateauTab[i + 1][j], plateauTab[i][j + 1], new Case());
					}

					if (plateauTab[i][j] instanceof CaseTopRight) {
						plateauTab[i][j].setNeighbours(new Case(), plateauTab[i + 1][j], new Case(), plateauTab[i][j - 1]);
					}

					if (plateauTab[i][j] instanceof CaseTop) {
						plateauTab[i][j].setNeighbours(new Case(), plateauTab[i][j + 1], plateauTab[i][j + 1], plateauTab[i][j - 1]);
					}

					if (plateauTab[i][j] instanceof CaseBotLeft) {
						plateauTab[i][j].setNeighbours(plateauTab[i - 1][j], new Case(), plateauTab[i][j + 1], new Case());
					}

					if (plateauTab[i][j] instanceof CaseBotRight) {
						plateauTab[i][j].setNeighbours(plateauTab[i - 1][j], new Case(), new Case(), plateauTab[i][j - 1]);
					}

					if (plateauTab[i][j] instanceof CaseBot) {
						plateauTab[i][j].setNeighbours(plateauTab[i - 1][j], new Case(), plateauTab[i][j + 1], plateauTab[i][j - 1]);
					}

					if (plateauTab[i][j] instanceof CaseLeft) {
						plateauTab[i][j].setNeighbours(plateauTab[i - 1][j], plateauTab[i + 1][j], plateauTab[i][j + 1], new Case());
					}

					if (plateauTab[i][j] instanceof CaseRight) {
						plateauTab[i][j].setNeighbours(plateauTab[i - 1][j], plateauTab[i + 1][j], new Case(), plateauTab[i][j - 1]);
					}

					if ((i >= 1) && (j >= 1) && (i <= plateauTab.length - 1 - 1)
							&& (j <= plateauTab[0].length - 1 - 1)) {	
						plateauTab[i][j].setNeighbours(plateauTab[i - 1][j], plateauTab[i + 1][j], plateauTab[i][j + 1], plateauTab[i][j - 1]);
					}
				}
			}
		}
	
	
	public void affichePlateau() {
		String val = "";
		for (int i = 0; i <= this.plateauTab.length - 1; i++) {
			for (int j = 0; j <= this.plateauTab[0].length - 1; j++) {
				val += plateauTab[i][j].toString() + "			";
			}
			val += "\n";
		}
		System.out.println(val);
	}

	public ArrayList<Case> getPlateau() {
		return plateauArray;
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