package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.fasterxml.jackson.annotation.JsonCreator;

import application.globale.Globals;

public class Plateau implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2050984407135436647L;

	private HashMap<String, Case> plateauMap;

	public static String[] ligne = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
	public static String[] colonne = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

	private ArrayList<String> casesDisponible;

	private Case[][] plateauTab;

	
	@JsonCreator
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
		if (askColor && !askChain){
			Set<Case> setCasesAModifier = new HashSet<Case>();
			//tabCasesAModifier.add(caseModifiee);
			// on vérifie pour chaque cases si elle n'a pas une autre cases pareille
			ArrayList<Case> tabCasesAModifier = new ArrayList<Case> ();
			setCasesAModifier.addAll(addRecurse(setCasesAModifier,caseModifiee));
			tabCasesAModifier.add(caseModifiee);
			return new Action(setCasesAModifier, Globals.choixActionCreationChaine);
			//Vérifier s'il y a des cases autour des cases autour ...
		}
		/**
		 * Présence d'une ou plusieures chaînes autour de la case Pas d'hôtels
		 * dans ce cas
		 */
		if (askChain && !askColor){ // juste une ou plusieurs chaines, pas d'hotel
			// tableau des cases non null donc dans ce cas des cases avec chaines.
			ArrayList<Case> tab = caseModifiee.tabAdjascent();
			/**
			 * Le tableau n'a qu'une taille de 1, donc simple changement de la
			 * couleur de la case
			 */
			if (tab.size() == 1)
			{
				// La liste de chaine va de 0 a 7 alors que nos états vont de 2 a 8, le -2 est alors nécessaire
				// addCase ajoute la case dans la chaine, et change par la même occasion l'état de la case
				listeChaine.get(tab.get(0).getEtat()-2).addCase(caseModifiee);				

			}
			/**
			 * Tableau taille de >2, donc plusieures cases avec une chaine
			 */

			if (tab.size() >= 2){
				int nbCases = tab.size();
				boolean sameColor = caseModifiee.sameColorsArround(tab, nbCases);				
				/**
				 * Vérification si il s'agit de la même chaîne dans toutes les
				 * cases adjascentes, on change juste la couleur de la case.
				 */
				// de la même couleur donc on ajoute simplement la case à la chaine
				if (sameColor)
					listeChaine.get(tab.get(0).getEtat()-2).addCase(caseModifiee);
				// Couleur différents
				else{
					// On vérifie la taille des chianes pour savoir si elle sont différentes. 
					ArrayList<Chaine> chaineDifferente = listeChaineDifferentes(tab, listeChaine);
					Chaine grandeChaine = sameSizeChaine(chaineDifferente);

					if(grandeChaine == null){
						// il n'y a pas de chaine plus grande qu'une autre

						return new Action(Globals.choixActionFusionSameSizeChaine,listeChaine, chaineDifferente,caseModifiee);
					}
					else{
						ArrayList<Chaine> listeChaineDifferenteAvantModif = chaineDifferente;
						Chaine chaineAbsorbanteAvantFusion = grandeChaine;
						fusionChaines(listeChaine,chaineDifferente,grandeChaine,caseModifiee);

						//TODO listeChaineDifferenteAvantModif est null
						return new Action(Globals.choixActionFusionEchangeAchatVente,listeChaineDifferenteAvantModif,chaineAbsorbanteAvantFusion);
					}


				}
			}
			return null;
		}

		//		/**
		//		 * Présense d'un ou plusieurs hotêls avec une ou plusieures chaînes
		//		 */
		//
		//		if (askChain && askColor) {
		//			// Ici on a au mooins une chain et au moins 1 hotel
		//			// chercher ou on a la chaine et récupérer son etat pour avoir la
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
	 * Methode récursive qui ajout les cases hotels adjascentes au hotels dans la liste
	 * La liste de retour contiendra donc toutes les cases hotels adjascentes uniques
	 * @return
	 */
	public Set<Case> addRecurse (Set<Case> casesDone, Case c) {
		Set<Case> listRecurse = new HashSet<Case>();
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
					Set<Case> returnedList = addRecurse(casesDone, caseToDo);
					if (returnedList!=null) {
						casesDone.addAll(returnedList);
					}
				}
			}
		}
		//ArrayList<Case> casesDone1 = new ArrayList<Case> (listRecurse) ;

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
	 * Retourne la case du plateau en fonction de son nom passé en paramètre
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
	 * Retourn l'arrayList de case avec des etat différents, donc des chaines différentes
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
	 * Fonction qui regarde si les chaines sont de même taille. Si c'est le cas retourne null. Sinon retourne la plus grande chaine
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

	public ArrayList<Chaine> fusionChaines(ArrayList<Chaine> listeChaineTotale,ArrayList<Chaine> listeChaineAbsorbee, Chaine chaineAbsorbante, Case caseAAjouter)
	{

		listeChaineAbsorbee.remove(chaineAbsorbante);

		for(Chaine c : listeChaineAbsorbee)
		{
			listeChaineTotale.get(chaineAbsorbante.getTypeChaine().getNumero()-2).setListeCase(
					listeChaineTotale.get(chaineAbsorbante.getTypeChaine().getNumero()-2).modifChain(c));
			listeChaineTotale.get(c.getTypeChaine().getNumero()-2).getListeCase().clear();


		}
		listeChaineTotale.get(chaineAbsorbante.getTypeChaine().getNumero()-2).addCase(caseAAjouter);
		for (Case cas : listeChaineTotale.get(chaineAbsorbante.getTypeChaine().getNumero()-2).getListeCase()) {
			this.getCase(cas.getNom()).setEtat(cas.getEtat());
		}
		return listeChaineTotale;
	}

	/**
	 * Mise a jour des cases grises (case entouré par deux chaines sup à 11
	 * @param listChaines
	 */
	public void CasesGrises(ArrayList<Chaine> listChaines) {
		// TODO verification
		ArrayList<String> casesToRemove = new ArrayList<String>();
		ArrayList<Integer> etats = new ArrayList<Integer>();
		for (String c : casesDisponible) {
			Case cas = plateauMap.get(c);
			int nbChaineSup11=0;
			if (cas.surroundedByChains()) {
				ArrayList<Case> listCases = cas.tabAdjascent();
				for (Case cas2 : listCases) {
					if (cas2.getEtat()>1) {
						if (listChaines.get(cas2.getEtat()-2).tailleChaine()>=11) {
							if (!etats.contains(cas2.getEtat()-2)) {
								nbChaineSup11++;
								etats.add(cas2.getEtat()-2);
							}
						}
					}
				}
				if (nbChaineSup11>=2) {
					cas.setEtat(-1);
					casesToRemove.add(c);
				}
			}
			nbChaineSup11=0;
		}
		for (String cas : casesToRemove) {
			casesDisponible.remove(cas);
		}
	}

	public void uitiemechaine(ArrayList<Chaine> listChaines, Collection<ClientInfo> collection) {
		boolean isOkay=false;
		for (Chaine c : listChaines) {
			if (c.tailleChaine()==0) {
				isOkay=true;
			}
		}
		if (!isOkay) {
			for (ClientInfo c : collection) {
				for (String cas : c.getMain()) {
					Case cas2 = this.getCase(cas);
					if (cas2.surroundedByHotels()&&!cas2.surroundedByChains()) {
						cas2.setEtat(-1);
					}
				}
			}
		}
		else {
			for (ClientInfo c : collection) {
				for (String cas : c.getMain()) {
					Case cas2 = this.getCase(cas);
					cas2.setEtat(0);
				}
			}
		}
	}

	public void checkinCases(ArrayList<Chaine> listeChaine, HashMap<String, ClientInfo> infoParClient) {
		this.CasesGrises(listeChaine);
		this.uitiemechaine(listeChaine,infoParClient.values());
	}

	public static String[] getLigne() {
		return ligne;
	}

	public static void setLigne(String[] ligne) {
		Plateau.ligne = ligne;
	}

	public static String[] getColonne() {
		return colonne;
	}

	public static void setColonne(String[] colonne) {
		Plateau.colonne = colonne;
	}

	public Case[][] getPlateauTab() {
		return plateauTab;
	}

	public void setPlateauTab(Case[][] plateauTab) {
		this.plateauTab = plateauTab;
	}
}