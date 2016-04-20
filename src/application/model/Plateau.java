package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

public class Plateau implements Serializable {

	private HashMap<String, Case> plateauMap;
	private ArrayList<String> caseDisponible;

	public static String[] ligne = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
	public static String[] colonne = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

	private Case[][] plateauTab;

	public Plateau() {
		setPlateauMap(new HashMap<String, Case>());
		initCasesPlateau();
		initCasesAdjascentes();
		caseDisponible=new ArrayList<String>();
		caseDisponible.addAll(plateauMap.keySet());
	}

	/**
	 * Fonction d'initalisation des cases du plateau
	 */
	public void initCasesPlateau() {
		// init des cases du milieu du plateau
		for (int x = 1; x <= ligne.length - 1 - 1; x++) {
			for (int i = 1; i <= colonne.length - 1 - 1; i++) {
				// plateauTab[x][i] = new Case(ligne[x] + colonne[i]);
				getPlateauMap().put(ligne[x] + colonne[i], new Case(ligne[x] + colonne[i]));
			}
		}
		// init des cases du tour plateau
		for (int y = 0; y < 12; y++) {
			// Init Left et Right
			switch (y) {
			case 0:
				getPlateauMap().put(ligne[0] + colonne[y], new CaseTopLeft(ligne[0] + colonne[y]));
				for (int ligneTab = 1; ligneTab < 8; ligneTab++) {

					getPlateauMap().put(ligne[ligneTab] + colonne[y], new CaseLeft(ligne[ligneTab] + colonne[y]));
				}
				getPlateauMap().put(ligne[ligne.length - 1] + colonne[y],
						new CaseBotLeft(ligne[ligne.length - 1] + colonne[y]));
				break;
			case 11:
				getPlateauMap().put(ligne[0] + colonne[y], new CaseTopRight(ligne[0] + colonne[y]));
				for (int ligneTab = 1; ligneTab < 8; ligneTab++) {

					getPlateauMap().put(ligne[ligneTab] + colonne[y], new CaseRight(ligne[ligneTab] + colonne[y]));
				}
				getPlateauMap().put(ligne[ligne.length - 1] + colonne[y],
						new CaseBotRight(ligne[ligne.length - 1] + colonne[y]));
				break;
			default:
				getPlateauMap().put(ligne[0] + colonne[y], new CaseTop(ligne[0] + colonne[y]));
				getPlateauMap().put(ligne[ligne.length - 1] + colonne[y], new CaseBot(ligne[ligne.length - 1] + colonne[y]));
				break;
			}
		}
	}

	/**
	 * Fonction d'affectation des cases adjacentes des cases
	 */
	public void initCasesAdjascentes() {
		// init des cases adjacentes pour les cases du milieu plateau
		for (int i = 1; i <= ligne.length - 1 - 1; i++) {
			for (int j = 1; j <= colonne.length - 1 - 1; j++) {
				getPlateauMap().get(ligne[i] + colonne[j]).setNeighbours(getPlateauMap().get(ligne[i - 1] + colonne[j]),
						getPlateauMap().get(ligne[i + 1] + colonne[j]), getPlateauMap().get(ligne[i] + colonne[j + 1]),
						getPlateauMap().get(ligne[i] + colonne[j - 1]));
			}
		}
		// init des cases adjacentes pour les cases du tour du plateau
		for (int y = 0; y < 12; y++) {
			// Init Left et Right
			switch (y) {
			case 0:

				getPlateauMap().get(ligne[0] + colonne[y]).setNeighbours(null, getPlateauMap().get(ligne[1] + colonne[y]),
						getPlateauMap().get(ligne[0] + colonne[y + 1]), null);
				for (int ligneTab = 1; ligneTab < 8; ligneTab++) {
					getPlateauMap().get(ligne[ligneTab] + colonne[y]).setNeighbours(
							getPlateauMap().get(ligne[ligneTab - 1] + colonne[y]),
							getPlateauMap().get(ligne[ligneTab + 1] + colonne[y]),
							getPlateauMap().get(ligne[ligneTab] + colonne[y + 1]), null);
				}
				getPlateauMap().get(ligne[8] + colonne[y]).setNeighbours(getPlateauMap().get(ligne[8 - 1] + colonne[y]), null,
						getPlateauMap().get(ligne[8] + colonne[y + 1]), null);
				break;
			case 11:
				getPlateauMap().get(ligne[0] + colonne[y]).setNeighbours(null, getPlateauMap().get(ligne[1] + colonne[y]), null,
						getPlateauMap().get(ligne[0] + colonne[y - 1]));
				for (int ligneTab = 1; ligneTab < 8; ligneTab++) {

					getPlateauMap().put(ligne[ligneTab] + colonne[y], new CaseRight(ligne[ligneTab] + colonne[y]));
					getPlateauMap().get(ligne[ligneTab] + colonne[y]).setNeighbours(
							getPlateauMap().get(ligne[ligneTab - 1] + colonne[y]),
							getPlateauMap().get(ligne[ligneTab + 1] + colonne[y]), null,
							getPlateauMap().get(ligne[ligneTab] + colonne[y - 1]));
				}
				getPlateauMap().get(ligne[8] + colonne[y]).setNeighbours(getPlateauMap().get(ligne[8 - 1] + colonne[y]), null,
						null, getPlateauMap().get(ligne[8] + colonne[y - 1]));
				break;
			default:
				getPlateauMap().get(ligne[0] + colonne[y]).setNeighbours(null, getPlateauMap().get(ligne[1] + colonne[y]),
						getPlateauMap().get(ligne[0] + colonne[y + 1]), getPlateauMap().get(ligne[0] + colonne[y - 1]));
				getPlateauMap().get(ligne[8] + colonne[y]).setNeighbours(getPlateauMap().get(ligne[8 - 1] + colonne[y]), null,
						getPlateauMap().get(ligne[8] + colonne[y + 1]), getPlateauMap().get(ligne[8] + colonne[y - 1]));
				break;
			}
		}
	}
	
	public ArrayList<String> getCaseDisponible(){
		return this.caseDisponible;
	}

	/**
	 * Fonction de test d'affichage du plateau
	 */
	public void affichePlateau() {
		String val = "";
		for (int i = 0; i <= Plateau.ligne.length - 1; i++) {
			for (int j = 0; j <= Plateau.colonne.length - 1; j++) {
				if (getPlateauMap().get(ligne[i] + colonne[j]) != null) {
					val += getPlateauMap().get(ligne[i] + colonne[j]).toString() + "			";
				}
			}
			val += "\n";
		}
		System.out.println(val);
	}

		

	/**
	 * Ajoute 6 cases cliquable pour le joueur
	 */
	public void initialiseMainCaseNoir() {
		int max=TableauDeBord.infoParClient.size();
		for(int i=0;i<max;i++){
			Random generator = new Random();
			String c=caseDisponible.get(0 + (int)(Math.random() * caseDisponible.size()));
			plateauMap.get(c).setEtat(1);
			caseDisponible.remove(c);
		}
	}
	
	
	/**
	 * Ajoute 1 cases cliquable pour le joueur
	 */
	public void ajouteMain1foisCaseNoir() {
			Random generator = new Random();
			String c=caseDisponible.get(0 + (int)(Math.random() * caseDisponible.size()));
			plateauMap.get(c).setEtat(1);	
			plateauMap.remove(c);
	}
	

	/**
	 * Met à jour la case du plateau passé en paramètre
	 * 
	 * @param text
	 */
	public void updateCase(String text) {
		getPlateauMap().get(text).setEtat(1);
	}

	/**
	 * Retourne la case du plateau en fonction de son nom passé en paramètre
	 * 
	 * @param text
	 * @return
	 */
	public Case getCase(String text) {
		return getPlateauMap().get(text);
	}

	public static void main(String[] Args) {
		Plateau p = new Plateau();
		p.affichePlateau();

	}

	public HashMap<String, Case> getPlateauMap() {
		return plateauMap;
	}

	public void setPlateauMap(HashMap<String, Case> plateauMap) {
		this.plateauMap = plateauMap;
	}
}