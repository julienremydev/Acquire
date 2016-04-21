package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Plateau implements Serializable {

	private HashMap<String, Case> plateauMap;

	public static String[] ligne = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
	public static String[] colonne = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

	private ArrayList<String> casesDisponible;

	private Case[][] plateauTab;

	public Plateau() {
		plateauMap = new HashMap<String, Case>();
		initCasesPlateau();
		initCasesAdjascentes();
		casesDisponible = new ArrayList<String>();
		for (int x = 0; x <= ligne.length - 1; x++) {
			for (int i = 0; i <= colonne.length - 1; i++) {
				casesDisponible.add(ligne[x]+colonne[i]);
			}
		}

		/**
		 * for (Entry<String, Case> entry : plateauMap.entrySet()) {
		 * casesDisponible.add((String) entry.getKey()); }
		 **/

	}

	/**
	 * Fonction d'initalisation des cases du plateau
	 */
	public void initCasesPlateau() {
		// init des cases du milieu du plateau
		for (int x = 1; x <= ligne.length - 1 - 1; x++) {
			for (int i = 1; i <= colonne.length - 1 - 1; i++) {
				// plateauTab[x][i] = new Case(ligne[x] + colonne[i]);
				plateauMap.put(ligne[x] + colonne[i], new Case(ligne[x] + colonne[i]));
			}
		}
		// init des cases du tour plateau
		for (int y = 0; y < 12; y++) {
			// Init Left et Right
			switch (y) {
			case 0:
				plateauMap.put(ligne[0] + colonne[y], new CaseTopLeft(ligne[0] + colonne[y]));
				for (int ligneTab = 1; ligneTab < 8; ligneTab++) {

					plateauMap.put(ligne[ligneTab] + colonne[y], new CaseLeft(ligne[ligneTab] + colonne[y]));
				}
				plateauMap.put(ligne[ligne.length - 1] + colonne[y],
						new CaseBotLeft(ligne[ligne.length - 1] + colonne[y]));
				break;
			case 11:
				plateauMap.put(ligne[0] + colonne[y], new CaseTopRight(ligne[0] + colonne[y]));
				for (int ligneTab = 1; ligneTab < 8; ligneTab++) {

					plateauMap.put(ligne[ligneTab] + colonne[y], new CaseRight(ligne[ligneTab] + colonne[y]));
				}
				plateauMap.put(ligne[ligne.length - 1] + colonne[y],
						new CaseBotRight(ligne[ligne.length - 1] + colonne[y]));
				break;
			default:
				plateauMap.put(ligne[0] + colonne[y], new CaseTop(ligne[0] + colonne[y]));
				plateauMap.put(ligne[ligne.length - 1] + colonne[y], new CaseBot(ligne[ligne.length - 1] + colonne[y]));
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
				plateauMap.get(ligne[i] + colonne[j]).setNeighbours(plateauMap.get(ligne[i - 1] + colonne[j]),
						plateauMap.get(ligne[i + 1] + colonne[j]), plateauMap.get(ligne[i] + colonne[j + 1]),
						plateauMap.get(ligne[i] + colonne[j - 1]));
			}
		}
		// init des cases adjacentes pour les cases du tour du plateau
		for (int y = 0; y < 12; y++) {
			// Init Left et Right
			switch (y) {
			case 0:

				plateauMap.get(ligne[0] + colonne[y]).setNeighbours(null, plateauMap.get(ligne[1] + colonne[y]),
						plateauMap.get(ligne[0] + colonne[y + 1]), null);
				for (int ligneTab = 1; ligneTab < 8; ligneTab++) {
					plateauMap.get(ligne[ligneTab] + colonne[y]).setNeighbours(
							plateauMap.get(ligne[ligneTab - 1] + colonne[y]),
							plateauMap.get(ligne[ligneTab + 1] + colonne[y]),
							plateauMap.get(ligne[ligneTab] + colonne[y + 1]), null);
				}
				plateauMap.get(ligne[8] + colonne[y]).setNeighbours(plateauMap.get(ligne[8 - 1] + colonne[y]), null,
						plateauMap.get(ligne[8] + colonne[y + 1]), null);
				break;
			case 11:
				plateauMap.get(ligne[0] + colonne[y]).setNeighbours(null, plateauMap.get(ligne[1] + colonne[y]), null,
						plateauMap.get(ligne[0] + colonne[y - 1]));
				for (int ligneTab = 1; ligneTab < 8; ligneTab++) {

					// plateauMap.put(ligne[ligneTab] + colonne[y], new
					// CaseRight(ligne[ligneTab] + colonne[y]));
					plateauMap.get(ligne[ligneTab] + colonne[y]).setNeighbours(
							plateauMap.get(ligne[ligneTab - 1] + colonne[y]),
							plateauMap.get(ligne[ligneTab + 1] + colonne[y]), null,
							plateauMap.get(ligne[ligneTab] + colonne[y - 1]));
				}
				plateauMap.get(ligne[8] + colonne[y]).setNeighbours(plateauMap.get(ligne[8 - 1] + colonne[y]), null,
						null, plateauMap.get(ligne[8] + colonne[y - 1]));
				break;
			default:
				plateauMap.get(ligne[0] + colonne[y]).setNeighbours(null, plateauMap.get(ligne[1] + colonne[y]),
						plateauMap.get(ligne[0] + colonne[y + 1]), plateauMap.get(ligne[0] + colonne[y - 1]));
				plateauMap.get(ligne[8] + colonne[y]).setNeighbours(plateauMap.get(ligne[8 - 1] + colonne[y]), null,
						plateauMap.get(ligne[8] + colonne[y + 1]), plateauMap.get(ligne[8] + colonne[y - 1]));
				break;
			}
		}
	}

	/**
	 * Fonction de test d'affichage du plateau
	 */
	public void affichePlateau() {
		String val = "";
		for (int i = 0; i <= Plateau.ligne.length - 1; i++) {
			for (int j = 0; j <= Plateau.colonne.length - 1; j++) {
				if (plateauMap.get(ligne[i] + colonne[j]) != null) {
					val += plateauMap.get(ligne[i] + colonne[j]).toString() + "			";
				}
			}
			val += "\n";
		}
		System.out.println(val);
	}

	/**
	 * Met à jour la case du plateau passé en paramètre
	 * 
	 * @param text
	 */
	public Action updateCase(String text) {
		Case caseModifiee = plateauMap.get(text);
		// plateauMap.get(text).lookCase();

		boolean simpleCase = caseModifiee.surroundedByNothing();
		boolean askColor = caseModifiee.surroundedByHotels();
		boolean askChain = caseModifiee.surroundedByChains();

		/**
		 * Cas simple, aucune case initialisée autour, la case deviens donc un
		 * hotel modification de l'etat de cette case
		 */
		if (simpleCase) {
			caseModifiee.setEtat(1);
		}

		/**
		 * Présence d'un ou plusieurs hotêls autour de la case Pas de chaînes
		 * dans ce cas Appel de la fonction de choix de couleur de chaine coté
		 * utilisateur
		 */
		if (askColor && !askChain) {
			Action action = new Action(0);
			// ArrayList<Case> tabHotels =
			// caseModifiee.tabAdjascent(caseModifiee.getNorth(),
			// caseModifiee.getSouth(), caseModifiee.getEast(),
			// caseModifiee.getWest());
			// for(Case element : tabHotels)
			// {
			// element.setEtat(2);
			// }
			// caseModifiee.setEtat(2);
			return action;
		}
		return null;

	}

	public ArrayList<String> getCasesDisponible() {
		return casesDisponible;
	}

	public void setCasesDisponible(ArrayList<String> casesDisponible) {
		this.casesDisponible = casesDisponible;
	}

	/**
	 * Retourne la case du plateau en fonction de son nom passé en paramètre
	 * 
	 * @param text
	 * @return
	 */
	public Case getCase(String text) {
		return plateauMap.get(text);
	}

	/**
	 * Ajoute 6 cases cliquable pour le joueur
	 */

	public void initialiseMainCaseNoir(int nbClient) {
		int max = nbClient;
		for (int i = 0; i < max; i++) {
			String c = casesDisponible.get(0 + (int) (Math.random() * casesDisponible.size() - 1));
			plateauMap.get(c).setEtat(1);
			casesDisponible.remove(c);
		}
	}

	/**
	 * Ajoute 1 cases cliquable pour le joueur
	 */

	public void ajouteMain1foisCaseNoir() {
		String c = casesDisponible.get(0 + (int) (Math.random() * casesDisponible.size() - 1));
		plateauMap.get(c).setEtat(1);
		plateauMap.remove(c);
	}
	//
	//
	// public static void main(String[] Args) {
	// Plateau p = new Plateau();
	// p.affichePlateau();
	//
	// }
}