package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

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
	 * Met � jour la case du plateau pass� en param�tre
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
		 * Cas simple, aucune case initialis�e autour, la case deviens donc un
		 * hotel modification de l'etat de cette case
		 */
		if (simpleCase) {
			caseModifiee.setEtat(1);
			return null;
		}

		/**
		 * Pr�sence d'un ou plusieurs hot�ls autour de la case Pas de cha�nes
		 * dans ce cas cr�ation d'un object Action de type 0 (hotels)
		 */
		if (askColor && !askChain)
		{
			ArrayList<Case> tabCasesAModifier = new ArrayList<Case>();
			//tabCasesAModifier.add(caseModifiee);
			// on v�rifie pour chaque cases si elle n'a pas une autre cases pareille
			tabCasesAModifier=addRecurse(tabCasesAModifier,caseModifiee);
			tabCasesAModifier.add(caseModifiee);
			Action action = new Action(tabCasesAModifier,0);
			//V�rifier s'il y a des cases autour des cases autour ...
			return action;
		}
		/**
		 * Pr�sence d'une ou plusieures cha�nes autour de la case Pas d'h�tels
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
				// La liste de chaine va de 0 a 7 alors que nos �tats vont de 2 a 8, le -2 est alors n�cessaire
				// addCase ajoute la case dans la chaine, et change par la m�me occasion l'�tat de la case
				listeChaine.get(tab.get(0).getEtat()-2).addCase(caseModifiee);				

			}



			/**
			 * Tableau taille de >2, donc plusieures cases avec une chaine
			 */

			if (tab.size() >= 2)
			{
				int nbCases = tab.size();
				boolean sameColor = caseModifiee.sameColorsArround(tab, nbCases);				
				/**
				 * V�rification si il s'agit de la m�me cha�ne dans toutes les
				 * cases adjascentes, on change juste la couleur de la case.
				 */
				// de la m�me couleur donc on ajoute simplement la case � la chaine
				if (sameColor)
					listeChaine.get(tab.get(0).getEtat()-2).addCase(caseModifiee);
				// Couleur diff�rents
				else
				{
					// On v�rifie la taille des chianes pour savoir si elle sont diff�rentes. 
					ArrayList<Chaine> chaineDifferente = listeChaineDifferentes(tab, listeChaine);
					Chaine grandeChaine = sameSizeChaine(chaineDifferente);
					// il n'y a pas de chaine plus grande qu'une autre
					if(grandeChaine == null)
					{
						// faire une action car il faut demander quelle chaine l'utilisateur veut choisir
						Action action = new Action(1,chaineDifferente);
					}
					else
					{
						chaineDifferente.remove(grandeChaine);
						for(Chaine c : chaineDifferente)
						{
							listeChaine.get(grandeChaine.getTypeChaine().getNumero()-2).modifChain(c);
						}
					}


				}
			}
			return null;
		}

		//		/**
		//		 * Pr�sense d'un ou plusieurs hot�ls avec une ou plusieures cha�nes
		//		 */
		//
		//		if (askChain && askColor) {
		//			// Ici on a au mooins une chain et au moins 1 hotel
		//			// chercher ou on a la chaine et r�cup�rer son etat pour avoir la
		//			// couleur
		//			// chercher les hotels ou autres chaines
		//			// regarder la taille des chaines pour savoir la quelle est la plus
		//			// grande
		//			// si taille == user.askColorForChains
		//			// si taille != on prend etat de la plus grande chaine
		//			this.setEtat(3);
		//		}
		return null;


	}
	/**
	 * Methode r�cursive qui ajout les cases hotels adjascentes au hotels dans la liste
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

	public HashMap<String, Case> getPlateauMap() {
		return plateauMap;
	}

	public void setPlateauMap(HashMap<String, Case> plateauMap) {
		this.plateauMap = plateauMap;
	}

	public ArrayList<String> getCasesDisponible() {
		return casesDisponible;
	}

	public void setCasesDisponible(ArrayList<String> casesDisponible) {
		this.casesDisponible = casesDisponible;
	}

	/**
	 * Retourne la case du plateau en fonction de son nom pass� en param�tre
	 * 
	 * @param text
	 * @return
	 */
	public Case getCase(String text) {
		return plateauMap.get(text);
	}

	public String initialiseMainCaseNoir() {
		Random randomGenerator = new Random();
		int index;
		index = randomGenerator.nextInt(casesDisponible.size());
		String c = casesDisponible.get(index);
		plateauMap.get(c).setEtat(1);
		casesDisponible.remove(c);
		return c;
	}

	/**
	 * Ajoute 1 cases cliquable pour le joueur
	 */

	public void ajouteMain1foisCaseNoir() {
		String c = casesDisponible.get(0 + (int) (Math.random() * casesDisponible.size() - 1));
		plateauMap.get(c).setEtat(1);
		casesDisponible.remove(c);
	}
	/**
	 * Retourn l'arrayList de case avec des etat diff�rents, donc des chaines diff�rentes
	 * @param tab
	 * @return
	 */
	public  ArrayList<Chaine> listeChaineDifferentes(ArrayList<Case> tab,  ArrayList<Chaine> listeChaine)
	{
		ArrayList<Chaine> tabReturn = new ArrayList<>();
		ArrayList<Integer> tabTest = new ArrayList<>();
		for(Case caseTest : tab)
		{
			if(! tabTest.contains(caseTest.getEtat()))
			{
				tabReturn.add(listeChaine.get(caseTest.getEtat()-2));
				tabTest.add(caseTest.getEtat());
			}

		}
		return tabReturn;
	}
	/**
	 * Fonction qui regarde si les chaines sont de m�me taille. Si c'est le cas retourne null. Sinon retourne la plus grande chaine
	 * @return
	 */
	public Chaine sameSizeChaine(ArrayList<Chaine> chaineDifferentes){
		ArrayList<Integer> tabTest = new ArrayList<>();
		ArrayList<Chaine> tabChaineTailleDiff = new ArrayList<>();
		for(Chaine ch : chaineDifferentes)
		{
			if(! tabTest.contains(ch.tailleChaine()))
			{
				tabTest.add(ch.tailleChaine());
				tabChaineTailleDiff.add(ch);
			}
		}
		if(tabTest.size() == 1)
		{
			return null;
		}
		else
		{

			Collections.sort(tabChaineTailleDiff,new Comparator<Chaine>(){
				@Override
				public int compare(Chaine c1, Chaine c2){
					return c2.tailleChaine().compareTo(c1.tailleChaine());
				}
			});
			return tabChaineTailleDiff.get(0);

		}
	}


}