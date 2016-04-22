package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
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
	 * @param listeChaine 
	 */
	public Action updateCase(String text, ArrayList<Chaine> listeChaine) {
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
			return null;
		}

		/**
		 * Présence d'un ou plusieurs hotêls autour de la case Pas de chaînes
		 * dans ce cas création d'un object Action de type 0 (hotels)
		 */
		if (askColor && !askChain)
		{
			ArrayList<Case> tabCasesAModifier = new ArrayList<Case>();
			//tabCasesAModifier.add(caseModifiee);
			// on vérifie pour chaque cases si elle n'a pas une autre cases pareille
			tabCasesAModifier=addRecurse(tabCasesAModifier,caseModifiee);
			tabCasesAModifier.add(caseModifiee);
			Action action = new Action(tabCasesAModifier,0);
			//Vérifier s'il y a des cases autour des cases autour ...
			return action;
		}
		/**
		 * Présence d'une ou plusieures chaînes autour de la case Pas d'hôtels
		 * dans ce cas
		 */
		if (askChain && !askColor) // juste une ou plusieurs chaines, pas d'hotel
		{
			// tableau des cases non null donc dans ce cas des cases avec chaines.
			ArrayList<Case> tab = caseModifiee.tabAdjascent();
			/**
			 * Le tableau n'a qu'une taille de 1, donc simple changement de la
			 * couleur de la case
			 */
			if (tab.size() == 1)
			{
				caseModifiee.setEtat(tab.get(0).getEtat());
				listeChaine.get(tab.get(0).getEtat()).addCase(caseModifiee);				

			}

		}	

		//			/**
		//			 * Tableau taille de 2, donc deux cases avec une chaine
		//			 */
		//
		//			if (tab.size() >= 2)// regrouper le cas ou on a 2, 3 ou 4 cases
		//				// autour avec une chaine
		//			{
		//				int nbCases = tab.size();
		//				boolean sameColor = sameColorsArround(tab, nbCases);
		//				int chainePremiereCase;
		//				int chaineDeuxiemeCase;
		//				sameColor = sameColorsArround(tab, nbCases);
		//				/**
		//				 * Vérification si il s'agit de la même chaîne dans toutes les
		//				 * cases adjascentes, on change juste la couleur de la case.
		//				 */
		//				if (sameColor)
		//					this.setEtat(tab.get(0).getEtat());
		//				else {
		//					switch (nbCases) {
		//					case 2:
		//						chainePremiereCase = tab.get(0).getEtat();
		//						chaineDeuxiemeCase = tab.get(1).getEtat();
		//						/**
		//						 * Si leurs taille sont égales, on demande la couleur à
		//						 * l'utilisateur
		//						 */
		//						if (chainePremiereCase == chaineDeuxiemeCase) // avec
		//							// les
		//							// fonctions
		//							// que
		//							// yoh
		//							// va
		//							// faire
		//							this.setEtat(tab.get(0).getEtat()); // client.askColorChaineVoulue()
		//						// &&
		//						// chaine.SetChaine(int
		//						// nouvelleChaine)
		//
		//						else {
		//							if (chainePremiereCase > chaineDeuxiemeCase)
		//								// La premiere chaine est plus grande donc
		//								// changement etat case +
		//								// tab.get(1).SetChaine(tab.get(0))
		//								this.setEtat(tab.get(0).getEtat());
		//							else
		//								// pareil mais inversement avec les cases du
		//								// tableau
		//								this.setEtat(tab.get(1).getEtat());
		//						}
		//						break;
		//					case 3:
		//
		//						break;
		//					case 4:
		//						break;
		//					}
		//				}
		//				Collections.sort(tab, new Comparator<Case>() {
		//					@Override
		//					public int compare(Case tc1, Case tc2) {
		//						return tc1.getEtat().compareTo(tc2.getEtat());
		//					}
		//				});
		//			}
		//		}
		return null;


	}
	/**
	 * Methode récursive qui ajout les cases hotels adjascentes au hotels dans la liste
	 * La liste de retour contiendra donc toutes les cases hotels adjascentes uniques
	 * @return
	 */
	public ArrayList<Case> addRecurse (ArrayList<Case> casesDone, Case c) {
		ArrayList<Case> listRecurse = new ArrayList<Case>();
		casesDone.add(c);
		if (c.surroundedByHotels()) {
			ArrayList<Case> listHotels = c.tabAdjascent();
			for (Case hotel : listHotels) {
				if (!casesDone.contains(hotel)) {
					listRecurse.add(hotel);
				}
			}
			if (listRecurse.isEmpty()) {
				return null;
			}
			else {
				for (Case caseToDo : listRecurse) {
					ArrayList<Case> returnedList = addRecurse(casesDone, caseToDo);
					if (returnedList!=null) {
						casesDone.addAll(returnedList);
					}
				}
			}
		}
		return casesDone;

	}
	/**
	 * Creation d'une nouvelle chaine, Changement de l'etat des hotels en chaîne, ajout de la chaine, à la liste de chaîne.
	 * @param listeHotels
	 * @param nomChaine
	 */
	public void creationChaine(ArrayList<Case> listeHotels, TypeChaine nomChaine)
	{
		// Création de la nouvelle chaine
		Chaine nouvelleChaine = new Chaine(nomChaine);
		// Changement des états des hotels pour qu'ils appartiennent à la même chaine
		for(Case hotelToChaine : listeHotels)
		{
			nouvelleChaine.addCase(this.getCase(hotelToChaine.getNom()));
			this.getCase(hotelToChaine.getNom()).setEtat(nomChaine.getNumero());
		}
		// Ajout de la chaine à la liste de chiane ? => ou changement d'un etat dans chaine qui permet de dire qu'elle est active.
		//Game.listeChaine.add(nouvelleChaine);
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
		Random randomGenerator = new Random();
		int index;
		for (int i = 0; i < max; i++) {
			index = randomGenerator.nextInt(casesDisponible.size());
			String c = casesDisponible.get(index);
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