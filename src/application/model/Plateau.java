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
import com.fasterxml.jackson.annotation.JsonProperty;

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

	// @JsonCreator
	public Plateau() {
		plateauMap = new HashMap<String, Case>();
		initCasesPlateau();
		initCasesAdjascentes();
		casesDisponible = new ArrayList<String>();
		for (int x = 0; x <= ligne.length - 1; x++) {
			for (int i = 0; i <= colonne.length - 1; i++) {
				casesDisponible.add(ligne[x] + colonne[i]);
			}
		}
	}

	@JsonCreator
	public Plateau(@JsonProperty("casesDisponible") ArrayList<String> casesDisponible) {
		plateauMap = new HashMap<String, Case>();
		initCasesPlateau();
		initCasesAdjascentes();
		this.casesDisponible = casesDisponible;
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
	 * Regarde le cas de modification lors du click Lors d'un cas simple,
	 * modification de la case Lors d'un cas de fusion, return d'une action
	 * 
	 * @param text
	 * @param listeChaine
	 * @return
	 */
	public Action updateCase(String text, ArrayList<Chaine> listeChaine) {
		Case caseModifiee = plateauMap.get(text);
		Action actionReturn = null;
		// Cr�ation des boolean pour la d�tection des 3 cas diff�rents
		boolean simpleCase = caseModifiee.surroundedByNothing();
		boolean askColor = caseModifiee.surroundedByHotels();
		boolean askChain = caseModifiee.surroundedByChains();
		/*
		 * Cas simple, aucune case initialis�e autour, la case deviens donc un
		 * hotel modification de l'etat de cette case
		 */
		if (simpleCase) {
			caseModifiee.setEtat(1);
			return null;
		}
		/*
		 * Cas de cr�ation de chaine
		 */
		if (askColor && !askChain) {
			Set<Case> setCasesAModifier = new HashSet<Case>();
			ArrayList<Case> tabCasesAModifier = new ArrayList<Case>();
			setCasesAModifier.addAll(addRecurse(setCasesAModifier, caseModifiee));
			tabCasesAModifier.add(caseModifiee);
			actionReturn = new Action(setCasesAModifier, Globals.choixActionCreationChaine);
		}
		/*
		 * Cas de fusion de chaines
		 */
		else
			actionReturn = gestionChainHotel(listeChaine, caseModifiee);
		return actionReturn;

	}

	/**
	 * Retourne une Action sp�cifique en fonction du cas de fusion
	 * 
	 * @param listeChaine
	 * @param caseModifiee
	 * @return
	 */
	private Action gestionChainHotel(ArrayList<Chaine> listeChaine, Case caseModifiee) {
		ArrayList<Case> tab = caseModifiee.tabAdjascent();
		boolean sameColor = caseModifiee.sameColorsArround(tab);
		// de la m�me couleur donc on ajoute simplement la case � la chaine
		if (sameColor) {
			listeChaine.get(tab.get(0).getEtat() - 2).addCase(caseModifiee);
			return null;
		}

		ArrayList<Chaine> chaineDifferente = listeChaineDifferentes(tab, listeChaine);
		ArrayList<Chaine> listeGrandesChaines = new ArrayList<>();
		// Creation de la liste de Case hotels �ventuelles � int�grer dans la
		// fusion
		Set<Case> setCasesAModifier = new HashSet<Case>();
		setCasesAModifier.addAll(addRecurse(setCasesAModifier, caseModifiee));
		ArrayList<Case> tabCasesAModifier = new ArrayList<Case>(setCasesAModifier);

		if (chaineDifferente.size() == 1) {
			// Le nombre de chaine diff�rente est de 1, donc la case est
			// entour�e par la m�me chaine, donc la grande chaine est la seule
			// chaine
			listeGrandesChaines = chaineDifferente;
			fusionChaines(listeChaine, chaineDifferente, listeGrandesChaines.get(0), tabCasesAModifier);
			return null;
		} else
			// Nous avons plusieurs chaines diff�rentes, on regarde si elles
			// sont toutes la m�me taille
			listeGrandesChaines = sameSizeChaine(chaineDifferente);

		// Si les chaines ont toutes la m�me taille, listeGrandeChaine est a
		// null
		if (listeGrandesChaines == null) {

			return new Action(Globals.choixActionFusionSameSizeChaine, listeChaine, chaineDifferente, chaineDifferente,
					tabCasesAModifier);
		}
		// Nous avons au moins une chaine plus grande qu'une autre
		else {
			// Si la taille est de 1, nous n'avons qu'une grande chaine, donc on
			// fais la modification puis return l'Action pour les �changes
			// d'action
			if (listeGrandesChaines.size() == 1) {
				// Sauvegarde de l'�tat des chaines avant fusion pour l'�change
				// d'actions
				ArrayList<Chaine> listeChaineDifferenteAvantModif = chaineDifferente;
				Chaine chaineAbsorbanteAvantFusion = listeGrandesChaines.get(0);
				Chaine chaineAbsorbantePourFusion = listeGrandesChaines.get(0);
				// Fusion des Chaines
				fusionChaines(listeChaine, chaineDifferente, chaineAbsorbantePourFusion, tabCasesAModifier);
				// Puis cr�ation de l'action avec les chaines pr�c�dant leur
				// modification
				return new Action(Globals.choixActionFusionEchangeAchatVente, listeChaineDifferenteAvantModif,
						chaineAbsorbanteAvantFusion);
			}
			// Sinon 2 chaines de m�me taille sont plus grande qu'une autre, on
			// return donc l'Action pour le choix de la chaine Absorbante
			else {
				return new Action(Globals.choixActionFusionSameSizeChaine, listeChaine, listeGrandesChaines,
						chaineDifferente, tabCasesAModifier);
			}
		}
	}

	/**
	 * Methode r�cursive qui ajout les cases hotels adjascentes au hotels dans
	 * la liste La liste de retour contiendra donc toutes les cases hotels
	 * adjascentes uniques
	 * 
	 * @return
	 */
	public Set<Case> addRecurse(Set<Case> casesDone, Case c) {
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
			} else {
				for (Case caseToDo : listRecurse) {
					Set<Case> returnedList = addRecurse(casesDone, caseToDo);
					if (returnedList != null) {
						casesDone.addAll(returnedList);
					}
				}
			}
		}

		return casesDone;

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
		String c = "";
		index = randomGenerator.nextInt(casesDisponible.size());
		c = casesDisponible.get(index);
		plateauMap.get(c).setEtat(1);
		casesDisponible.remove(c);
		return c;
	}

	/**
	 *  Pose un hotel pour la banque sur le plateau, lors d'une fusion avec 2
	 * joueurs
	 * @param listChaines
	 * @return
	 */
	public String poserJetonBanque(ArrayList<Chaine> listChaines) {
		boolean isOkay = false;
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
					if (cas2.getEtat() > 1) {
						if (!etats.contains(cas2.getEtat())) {
							etats.add(cas2.getEtat());
						}
					}
				}
			}
			if (etats.size() < 2) {
				updateCase(c, listChaines);
				casesDisponible.remove(c);
				isOkay = true;
			}
			etats.clear();
		}
		return c;
	}

	/**
	 * Retourn l'arrayList de case avec des etat diff�rents, donc des chaines
	 * diff�rentes
	 * 
	 * @param tab
	 * @param listeChaine
	 * @return
	 */
	public ArrayList<Chaine> listeChaineDifferentes(ArrayList<Case> tab, ArrayList<Chaine> listeChaine) {
		ArrayList<Chaine> tabReturn = new ArrayList<>();
		ArrayList<Integer> tabTest = new ArrayList<>();
		ArrayList<Case> casesChaineIterate = new ArrayList<>();
		// Boucle qui ne prend que les cases du tab adjascent ou on a des
		// chaines
		for (Case caseChaine : tab) {
			if (caseChaine.getEtat() >= 2)
				casesChaineIterate.add(caseChaine);

		}
		// ensuite on regarde dans cette liste de cases appartenant � des
		// chaines, celles qui ont des chaines diff�rentes
		for (Case caseTest : casesChaineIterate) {
			if (!tabTest.contains(caseTest.getEtat())) {
				tabReturn.add(listeChaine.get(caseTest.getEtat() - 2));
				tabTest.add(caseTest.getEtat());
			}

		}
		return tabReturn;
	}

	/**
	 * Fonction qui regarde si les chaines sont de m�me taille. Si c'est le cas
	 * retourne null. Sinon retourne la liste des chaines les plus grande(=)
	 * 
	 * @return
	 */
	public ArrayList<Chaine> sameSizeChaine(ArrayList<Chaine> chaineDifferentes) {

		ArrayList<Chaine> returnGrandesChaines = new ArrayList<>();
		ArrayList<Chaine> triCroissantTaille = chaineDifferentes;

		Collections.sort(triCroissantTaille, new Comparator<Chaine>() {
			@Override
			public int compare(Chaine c1, Chaine c2) {
				return c2.tailleChaine().compareTo(c1.tailleChaine());
			}
		});
		// On prend la taille de la premiere chaine donc de la plus grande
		int taillePremiereChaine = triCroissantTaille.get(0).tailleChaine();
		// On it�re sur les autres chaines, on compare leurs taille et on
		// n'ajoute que celle qui ont la m�me taille
		for (Chaine ch : triCroissantTaille) {
			if (ch.tailleChaine() == taillePremiereChaine)
				returnGrandesChaines.add(ch);

		}
		// Si cette liste avec la grande taille � la m�me taille que la liste de
		// chaine en entr�e, toutes les chaines ont la m�me taille donc return
		// null
		if (chaineDifferentes.size() == returnGrandesChaines.size()) {
			return null;
		} else
			return returnGrandesChaines;

	}

	/**
	 * Prend en parametre la liste de chaine du plateau, afin d'ajouter la liste
	 * de chaine Absorb�e � la chaine absorbante, avec la case cliqu�e
	 * 
	 * @param listeChaineTotale
	 * @param listeChaineAbsorbee
	 * @param chaineAbsorbante
	 * @param listeCaseAbsorbee
	 * @return
	 */
	public ArrayList<Chaine> fusionChaines(ArrayList<Chaine> listeChaineTotale, ArrayList<Chaine> listeChaineAbsorbee,
			Chaine chaineAbsorbante, ArrayList<Case> listeCaseAbsorbee) {
		listeChaineAbsorbee.remove(chaineAbsorbante);

		for (Chaine c : listeChaineAbsorbee) {
			listeChaineTotale.get(chaineAbsorbante.getTypeChaine().getNumero() - 2).setListeCase(
					listeChaineTotale.get(chaineAbsorbante.getTypeChaine().getNumero() - 2).modifChain(c));
			listeChaineTotale.get(c.getTypeChaine().getNumero() - 2).getListeCase().clear();

		}
		// Au lieu d'ajouter une case, faire une boucle d'ajout de la liste de
		// case Hotels
		for (Case c : listeCaseAbsorbee) {
			listeChaineTotale.get(chaineAbsorbante.getTypeChaine().getNumero() - 2).addCase(c);
		}

		// On refais une boucle de changement d'�tat des cases
		for (Case cas : listeChaineTotale.get(chaineAbsorbante.getTypeChaine().getNumero() - 2).getListeCase()) {
			this.getCase(cas.getNom()).setEtat(cas.getEtat());
		}
		return listeChaineTotale;
	}

	/**
	 * Mise a jour des cases grises (case entour� par deux chaines sup � 11)
	 * 
	 * @param listChaines
	 * @return
	 */
	public ArrayList<String> CasesGrises(ArrayList<Chaine> listChaines, ArrayList<String> main) {
		ArrayList<String> casesToRemove = new ArrayList<String>();
		for (String c : main) {
			Case cas = plateauMap.get(c);
			ArrayList<Case> tab = cas.tabAdjascent();
			ArrayList<Chaine> chaineDifferente = listeChaineDifferentes(tab, listChaines);
			boolean setGrise = setGrise(chaineDifferente);
			if (setGrise) {
				plateauMap.get(c).setEtat(-1);
				casesToRemove.add(c);
			}
		}
		return casesToRemove;
	}

	/**
	 * Retourne true si deux chaines parmis la liste ont une taille sup�rieur �
	 * 11
	 * 
	 * @param chain
	 * @return 
	 */
	public boolean setGrise(ArrayList<Chaine> chain) {
		// Recuperation des chaines avec taille sup�rieure a 11
		ArrayList<Chaine> listeChaineSupp = new ArrayList<>();
		for (Chaine ch : chain) {
			if (ch.tailleChaine() >= 11)
				listeChaineSupp.add(ch);
		}
		if (listeChaineSupp.size() >= 2)
			return true;
		else
			return false;
	}

	/**
	 * Met les cases de la main du joueur injouable si � cot� d'un hotel
	 * cr�ation chaine impossible
	 * 
	 * @param listChaines
	 * @param listeClient
	 */
	public void fullChaine(ArrayList<Chaine> listChaines, Collection<ClientInfo> listeClient) {
		boolean isOkay = false;
		for (Chaine c : listChaines) {
			if (c.tailleChaine() == 0) {
				isOkay = true;
			}
		}
		if (!isOkay) {
			for (ClientInfo c : listeClient) {
				for (String cas : c.getMain()) {
					Case cas2 = this.getCase(cas);
					if (cas2.surroundedByHotels() && !cas2.surroundedByChains()) {
						cas2.setEtat(-2);
					} else {
						cas2.setEtat(0);
					}
				}
			}
		} else {
			for (ClientInfo c : listeClient) {
				for (String cas : c.getMain()) {
					Case cas2 = this.getCase(cas);
					cas2.setEtat(0);
				}
			}
		}
	}

	/**
	 * Utilisee lors du chargement de la partie via la sauvegarde JSON
	 * 
	 * @param p
	 * @return np
	 */
	public static Plateau plateauRegeneration(Plateau p) {
		Plateau np = new Plateau();
		for (int x = 0; x <= ligne.length - 1; x++) {
			for (int i = 0; i <= colonne.length - 1; i++) {
				np.getCase(ligne[x] + colonne[i]).setEtat(p.getCase(ligne[x] + colonne[i]).getEtat());
			}
		}
		np.setCasesDisponible(p.casesDisponible);
		return np;

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

}