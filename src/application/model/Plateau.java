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
		Action actionReturn = null;
		//Création des boolean pour la détection des 3 cas différents
		boolean simpleCase = caseModifiee.surroundedByNothing();
		boolean askColor = caseModifiee.surroundedByHotels();
		boolean askChain = caseModifiee.surroundedByChains();
		/*
		 * Cas simple, aucune case initialisée autour, la case deviens donc un
		 * hotel modification de l'etat de cette case
		 */
		if (simpleCase) {
			caseModifiee.setEtat(1);
			return null;
		}
		/*
		 * Présence d'un ou plusieurs hotêls autour de la case Pas de chaînes
		 */
		if (askColor && !askChain){
			Set<Case> setCasesAModifier = new HashSet<Case>();
			ArrayList<Case> tabCasesAModifier = new ArrayList<Case> ();
			setCasesAModifier.addAll(addRecurse(setCasesAModifier,caseModifiee));
			tabCasesAModifier.add(caseModifiee);
			actionReturn =  new Action(setCasesAModifier, Globals.choixActionCreationChaine);
		}
		/**
		 * Présence d'une ou plusieures chaînes autour de la case Pas d'hôtels
		 * dans ce cas
		 */
		if (askChain && !askColor){ 
			actionReturn = gestionOnlyChain(listeChaine,caseModifiee);
		}
		if (askChain && askColor) {
			actionReturn = gestionChainHotel(listeChaine,caseModifiee);
		}
		return actionReturn;

	}
	private Action gestionChainHotel(ArrayList<Chaine> listeChaine, Case caseModifiee) {
		ArrayList<Case> tab = caseModifiee.tabAdjascent();
		ArrayList<Chaine> chaineDifferente = listeChaineDifferentes(tab, listeChaine);
		ArrayList<Chaine> listeGrandesChaines = new ArrayList<>();
		//Creation de la liste de Case hotels éventuelles à intégrer dans la fusion
		Set<Case> setCasesAModifier = new HashSet<Case>();
		setCasesAModifier.addAll(addRecurse(setCasesAModifier,caseModifiee));
		ArrayList<Case> tabCasesAModifier = new ArrayList<Case> (setCasesAModifier);

		if(chaineDifferente.size()==1)
			//Le nombre de chaine différente est de 1, donc la case est entourée par la même chaine, donc la grande chaine est la seule chaine
			listeGrandesChaines=chaineDifferente;
		else
			//Nous avons plusieurs chaines différentes, on regarde si elles sont toutes la même taille
			listeGrandesChaines = sameSizeChaine(chaineDifferente);

		// Si les chaines ont toutes la même taille, listeGrandeChaine est a null
		if(listeGrandesChaines == null){

			return new Action(Globals.choixActionFusionSameSizeChaine,listeChaine,chaineDifferente, chaineDifferente,tabCasesAModifier);
		}
		// Nous avons au moins une chaine plus grande qu'une autre
		else{
			// Si la taille est de 1, nous n'avons qu'une grande chaine, donc on fais la modification puis return l'Action pour les échanges d'action
			if(listeGrandesChaines.size()==1)
			{
				// Sauvegarde de l'état des chaines avant fusion pour l'échange d'actions
				ArrayList<Chaine> listeChaineDifferenteAvantModif = chaineDifferente;
				Chaine chaineAbsorbanteAvantFusion = listeGrandesChaines.get(0);
				Chaine chaineAbsorbantePourFusion = listeGrandesChaines.get(0);
				// Fusion des Chaines
				fusionChaines(listeChaine,chaineDifferente,chaineAbsorbantePourFusion,tabCasesAModifier);
				// Puis création de l'action avec les chaines précédant leur modification			
				return new Action(Globals.choixActionFusionEchangeAchatVente,listeChaineDifferenteAvantModif,chaineAbsorbanteAvantFusion);
			}
			// Sinon 2 chaines de même taille sont plus grande qu'une autre, on return donc l'Action pour le choix de la chaine Absorbante
			else
			{
				return new Action(Globals.choixActionFusionSameSizeChaine,listeChaine,listeGrandesChaines, chaineDifferente,tabCasesAModifier);
			}		
		}
	}

	private Action gestionOnlyChain(ArrayList<Chaine> listeChaine, Case caseModifiee) {
		ArrayList<Case> tab = caseModifiee.tabAdjascent();
		/*
		 * Le tableau n'a qu'une taille de 1, donc simple changement de la couleur de la case
		 */
		if (tab.size() == 1)
		{
			// La liste de chaine va de 0 a 7 alors que nos états vont de 2 a 8, le -2 est alors nécessaire
			// addCase ajoute la case dans la chaine, et change par la même occasion l'état de la case
			listeChaine.get(tab.get(0).getEtat()-2).addCase(caseModifiee);
		}
		/*
		 * Tableau taille de >2, donc plusieures cases avec une chaine
		 */
		if (tab.size() >= 2){
			boolean sameColor = caseModifiee.sameColorsArround(tab);				
			// de la même couleur donc on ajoute simplement la case à la chaine
			if (sameColor)
				listeChaine.get(tab.get(0).getEtat()-2).addCase(caseModifiee);
			else{
				// On vérifie la taille des chianes pour savoir si elle sont différentes. 
				ArrayList<Chaine> chaineDifferente = listeChaineDifferentes(tab, listeChaine);
				ArrayList<Chaine> listeGrandeChaine = new ArrayList<>();
				listeGrandeChaine = sameSizeChaine(chaineDifferente);

				Set<Case> setCasesAModifier = new HashSet<Case>();
				ArrayList<Case> tabCasesAModifier = new ArrayList<Case> ();
				setCasesAModifier.addAll(addRecurse(setCasesAModifier,caseModifiee));
				tabCasesAModifier.add(caseModifiee);

				// Toutes les chaines de la même taille
				if(listeGrandeChaine == null){
					return new Action(Globals.choixActionFusionSameSizeChaine,listeChaine,chaineDifferente, chaineDifferente,tabCasesAModifier);
				}
				else{
					if(listeGrandeChaine.size()==1)
					{
						System.out.println("grandeChaine");
						ArrayList<Chaine> listeChaineDifferenteAvantModif = chaineDifferente;
						Chaine chaineAbsorbanteAvantFusion = listeGrandeChaine.get(0);
						Chaine grandeChaineAction = listeGrandeChaine.get(0);
						ArrayList<Case> listeCaseAbsorbee1 = new ArrayList<Case>();
						listeCaseAbsorbee1.add(caseModifiee);
						fusionChaines(listeChaine,chaineDifferente,grandeChaineAction,tabCasesAModifier);
						return new Action(Globals.choixActionFusionEchangeAchatVente,listeChaineDifferenteAvantModif,chaineAbsorbanteAvantFusion);
					}
					else
					{
						System.out.println("plusieures grandesChaines");
						return new Action(Globals.choixActionFusionSameSizeChaine,listeChaine,listeGrandeChaine, chaineDifferente,tabCasesAModifier);
					}

				}


			}
		}
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
			ArrayList<Case> listHotels = c.hotelAdjascent();
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
		String c = "";
			index = randomGenerator.nextInt(casesDisponible.size());
			c = casesDisponible.get(index);
			plateauMap.get(c).setEtat(1);
			casesDisponible.remove(c);	
		return c;
	}
	
	public String poserJetonBanque(ArrayList<Chaine> listChaines) {
		boolean isOkay=false;
		Random randomGenerator = new Random();
		int index;
		String c = "";
		ArrayList<Integer> etats = new ArrayList<Integer>();
		while (!isOkay) {
			index = randomGenerator.nextInt(casesDisponible.size());
			c = casesDisponible.get(index);
			Case cas = plateauMap.get(c);
			if (cas.surroundedByChains()) {
				ArrayList<Case> list = cas.tabAdjascent();
				for (Case cas2 : list) {
					if (cas2.getEtat()>1) {
						if (!etats.contains(cas2.getEtat())) {
							etats.add(cas2.getEtat());
						}
					}
				}
			}
			if (etats.size()<2) {
				updateCase(c, listChaines);
				casesDisponible.remove(c);
				isOkay=true;
			}
		}
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
		ArrayList<Case> casesChaineIterate = new ArrayList<>();
		// Boucle qui ne prend que les cases du tab adjascent ou on a des chaines
		for(Case caseChaine : tab)
		{
			if(caseChaine.getEtat()>=2)
				casesChaineIterate.add(caseChaine);

		}
		// ensuite on regarde dans cette liste de cases appartenant à des chaines, celles qui ont des chaines différentes
		for(Case caseTest : casesChaineIterate)
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
	 * Fonction qui regarde si les chaines sont de même taille. Si c'est le cas retourne null. Sinon retourne la liste des chaines les plus grande(=)
	 * @return
	 */
	public ArrayList<Chaine> sameSizeChaine(ArrayList<Chaine> chaineDifferentes){
		ArrayList<Chaine> returnGrandesChaines = new ArrayList<>();
		ArrayList<Chaine> triCroissantTaille = chaineDifferentes;
		Collections.sort(triCroissantTaille,new Comparator<Chaine>(){
			@Override
			public int compare(Chaine c1, Chaine c2){
				return c2.tailleChaine().compareTo(c1.tailleChaine());
			}
		});
		// On prend la taille de la premiere chaine donc de la plus grande
		int taillePremiereChaine = triCroissantTaille.get(0).tailleChaine();
		// On itère sur les autres chaines, on compare leurs taille et on n'ajoute que celle qui ont la même taille
		for(Chaine ch : triCroissantTaille)
		{
			if(ch.tailleChaine()==taillePremiereChaine)
				returnGrandesChaines.add(ch);

		}
		// Si cette liste avec la grande taille à la même taille que la liste de chaine en entrée, toutes les chaines ont la même taille donc return null
		if(chaineDifferentes.size() == returnGrandesChaines.size())
		{
			return null;
		}
		else
			return returnGrandesChaines;

	}
	/**
	 * Prend en parametre la liste de chaine du plateau, afin d'ajouter la liste de chaine Absorbée à la chaine absorbante, avec la case cliquée
	 * @param listeChaineTotale
	 * @param listeChaineAbsorbee
	 * @param chaineAbsorbante
	 * @param listeCaseAbsorbee
	 * @return
	 */
	public ArrayList<Chaine> fusionChaines(ArrayList<Chaine> listeChaineTotale,ArrayList<Chaine> listeChaineAbsorbee, Chaine chaineAbsorbante, ArrayList<Case> listeCaseAbsorbee)
	{
		listeChaineAbsorbee.remove(chaineAbsorbante);

		for(Chaine c : listeChaineAbsorbee)
		{
			listeChaineTotale.get(chaineAbsorbante.getTypeChaine().getNumero()-2).setListeCase(
					listeChaineTotale.get(chaineAbsorbante.getTypeChaine().getNumero()-2).modifChain(c));
			listeChaineTotale.get(c.getTypeChaine().getNumero()-2).getListeCase().clear();


		}
		// Au lieu d'ajouter une case, faire une boucle d'ajout de la liste de case Hotels
		for(Case c : listeCaseAbsorbee)
		{
			listeChaineTotale.get(chaineAbsorbante.getTypeChaine().getNumero()-2).addCase(c);
		}

		// On refais une boucle de changement d'état des cases
		for (Case cas : listeChaineTotale.get(chaineAbsorbante.getTypeChaine().getNumero()-2).getListeCase()) {
			this.getCase(cas.getNom()).setEtat(cas.getEtat());
		}
		return listeChaineTotale;
	}

	/**
	 * Mise a jour des cases grises (case entouré par deux chaines sup à 11
	 * @param listChaines
	 * @return 
	 */
	public ArrayList<String> CasesGrises(ArrayList<Chaine> listChaines, ArrayList<String> main) {
		// TODO verification
		ArrayList<String> casesToRemove = new ArrayList<String>();
		ArrayList<Integer> etats = new ArrayList<Integer>();

		for (String c : main) {
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
					plateauMap.get(c).setEtat(-1);
					casesToRemove.add(c);
				}
			}
			nbChaineSup11=0;
			etats.clear();
		}

		return casesToRemove;
	}


	public void fullChaine(ArrayList<Chaine> listChaines, Collection<ClientInfo> listeClient) {
		boolean isOkay=false;
		for (Chaine c : listChaines) {
			if (c.tailleChaine()==0) {
				isOkay=true;
			}
		}
		if (!isOkay) {
			for (ClientInfo c : listeClient) {
				for (String cas : c.getMain()) {
					Case cas2 = this.getCase(cas);
					if (cas2.surroundedByHotels()&&!cas2.surroundedByChains()) {
						cas2.setEtat(-2);
					}
				}
			}
		}
		else {
			for (ClientInfo c : listeClient) {
				for (String cas : c.getMain()) {
					Case cas2 = this.getCase(cas);
					cas2.setEtat(0);
				}
			}
		}
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