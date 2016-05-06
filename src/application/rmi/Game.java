package application.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import application.model.Action;
import application.model.Case;
import application.model.Chaine;
import application.model.Plateau;
import application.model.TableauDeBord;
import application.model.TypeChaine;

public class Game implements Serializable{

	private static final long serialVersionUID = 1964688809311788179L;

	private Plateau plateau;

	private TableauDeBord tableauDeBord;

	private String playerTurn;

	private Action action;

	private ArrayList<String> ordre_joueur;

	private ArrayList<String> ordre_joueur_action;

	private ArrayList<String> tchat;

	private String chef;
	
	private boolean partieDeuxJoueurs;

	@JsonProperty
	private boolean partiecommencee;

	@JsonProperty
	private boolean partiechargee;

	/**
	 * Constructeur permettant l'initialisation de la liste de chaîne accessible par plateau et tableauDeBord
	 * Initialisation du plateau et du TableauDeBord
	 */
	
	@JsonCreator
	public Game(){
		super();

		Chaine sackson = new Chaine (TypeChaine.SACKSON);
		Chaine zeta = new Chaine (TypeChaine.ZETA);
		Chaine hydra = new Chaine (TypeChaine.HYDRA);
		Chaine fusion = new Chaine (TypeChaine.FUSION);
		Chaine america = new Chaine (TypeChaine.AMERICA);
		Chaine phoenix = new Chaine (TypeChaine.PHOENIX);
		Chaine quantum= new Chaine (TypeChaine.QUANTUM);

		this.plateau=new Plateau();
		this.tableauDeBord = new TableauDeBord();
		setOrdre_joueur(new ArrayList<String>());
		setOrdre_joueur_action(new ArrayList<String>());
		setTchat(new ArrayList<String>());
		tchat.add("[Serveur]");
		tchat.add("Serveur lancé.");
		setPartiecommencee(false);
		setPartiechargee(false);
		setPartieDeuxJoueurs(false);


		getListeChaine().add(sackson);
		getListeChaine().add(zeta);
		getListeChaine().add(hydra);
		getListeChaine().add(fusion);
		getListeChaine().add(america);
		getListeChaine().add(phoenix);
		getListeChaine().add(quantum);


	}

	/**
	 * Methode de calcul du prochain tour
	 * @throws RemoteException 
	 */
	public String whoseTurn (String pseudo) throws RemoteException {
		int currentIndice = getOrdre_joueur().indexOf(pseudo);
		if (getOrdre_joueur().size()==currentIndice+1) 
			return getOrdre_joueur().get(0);
		else
			return getOrdre_joueur().get(currentIndice+1);
	}
	/**
	 * Retourne le nom du joeuru qui a joué précédemment
	 * @param pseudo
	 * @return
	 * @throws RemoteException
	 */
	public String whoseBeforeTurn (String pseudo) throws RemoteException {
		int currentIndice = getOrdre_joueur().indexOf(pseudo);
		if (0==currentIndice) {
			return getOrdre_joueur().get(getOrdre_joueur().size()-1);
		}
			return getOrdre_joueur().get(currentIndice-1);
	}


	/**
	 * Méthode qui détermine l'ordre des joueurs qui doivent réaliser un choix 
	 * sur les actions qu'il détiennent d'une chaîne absorbée
	 * @throws RemoteException 
	 */
	public void setOrdreFusion() throws RemoteException {
		String player = getPlayerTurn();
		HashMap<TypeChaine,Integer> liste_actions_player = new HashMap<TypeChaine,Integer> ();
		for ( int i = 0 ; i < getOrdre_joueur().size() ; i++){
			liste_actions_player = getTableauDeBord().getInfoParClient().get(player).getActionParChaine();
			if ( liste_actions_player.get(getAction().getListeChainesAbsorbees().get(0).getTypeChaine()) > 0){
				getOrdre_joueur_action().add(player);
			}
			player = whoseTurn(player);
		}
	}

	/**
	 * Creation d'une nouvelle chaine, Changement de l'etat des hotels en chaîne, ajout de la chaine, à la liste de chaîne.
	 * @param listeHotels
	 * @param nomChaine
	 */
	public void creationChaine(Set<Case> listeHotels, TypeChaine nomChaine, String pseudo){
		// Changement des états des hotels pour qu'ils appartiennent à la même chaine
		for(Case hotelToChaine : listeHotels){
			getListeChaine().get(nomChaine.getNumero()-2).addCase(getPlateau().getCase(hotelToChaine.getNom()));
		}
		getTableauDeBord().getClientInfo(pseudo).getActionParChaine().put(nomChaine, 1+ getTableauDeBord().getClientInfo(pseudo).getActionParChaine().get(nomChaine));
		getTableauDeBord().getInfosChaine().get(1).getInfos().put(nomChaine, 24);
		getTableauDeBord().getListeChaine().get(nomChaine.getNumero()-2).setNbActionRestante(getTableauDeBord().getListeChaine().get(nomChaine.getNumero()-2).getNbActionRestante()-1);

	}
	public Plateau getPlateau() {
		return this.plateau;
	}

	public TableauDeBord getTableauDeBord() {
		return this.tableauDeBord;
	}

	public ArrayList<Chaine> getListeChaine(){
		return tableauDeBord.getListeChaine();
	}

	public void setListeChaine(ArrayList<Chaine> list){
		tableauDeBord.setListeChaine(list);
	}

	public String getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(String playerTurn) {
		this.playerTurn = playerTurn;
	}

	public Action getAction() {
		return action;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public void setTableauDeBord(TableauDeBord tableauDeBord) {
		this.tableauDeBord = tableauDeBord;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	public ArrayList<String> getOrdre_joueur() {
		return ordre_joueur;
	}

	public void setOrdre_joueur(ArrayList<String> ordre_joueur) {
		this.ordre_joueur = ordre_joueur;
	}

	public ArrayList<String> getOrdre_joueur_action() {
		return ordre_joueur_action;
	}

	public void setOrdre_joueur_action(ArrayList<String> ordre_joueur_action) {
		this.ordre_joueur_action = ordre_joueur_action;
	}

	public ArrayList<String> getTchat() {
		return tchat;
	}

	public void setTchat(ArrayList<String> tchat) {
		this.tchat = tchat;
	}


	@JsonProperty("partiechargee")
	public boolean isPartiechargee() {
		return partiechargee;
	}

	public void setPartiechargee(boolean partiechargee) {
		this.partiechargee = partiechargee;
	}

	@JsonProperty("partiecommencee")
	public boolean isPartiecommencee() {
		return partiecommencee;
	}

	public void setPartiecommencee(boolean partiecommencee) {
		this.partiecommencee = partiecommencee;
	}

	public String getChef() {
		return chef;
	}

	public void setChef(String chef) {
		this.chef = chef;
	}
	/**
	 * Permet de savoir si la game se finit
	 * @return
	 */
	@JsonIgnore
	public boolean isOver() {
		int iterator = 0;
		int nbChaineSup10=0;
		ArrayList<Chaine> listChaine = getTableauDeBord().getListeChaine();
		while(nbChaineSup10<7 && iterator < listChaine.size()) {
			if (listChaine.get(iterator).isSup10()) {
				nbChaineSup10++;
			}
			if (listChaine.get(iterator).isSup41()) {
				return true;
			}
			iterator++;
		}
		if(nbChaineSup10>=7){
			return true;
		}
		return false;
	}

	@JsonIgnore
	public ArrayList<ArrayList<String>> getPrime() {
		return tableauDeBord.getPrime(action);
		
	}

	public boolean isPartieDeuxJoueurs() {
		return partieDeuxJoueurs;
	}

	public void setPartieDeuxJoueurs(boolean partieDeuxJoueurs) {
		this.partieDeuxJoueurs = partieDeuxJoueurs;
	}
	
	/**
	 * Méthode qui calcul le prix total des actions que le joueur a choisi
	 */
	public int calculArgentImmobiliseAction(HashMap<TypeChaine, Integer> liste_actions){
		int tot = 0;

		Collection<TypeChaine> keys = liste_actions.keySet();
		for (TypeChaine key : keys) {
			tot += TypeChaine.prixAction(key,this.getListeChaine().get(key.getNumero()-2).tailleChaine()) * liste_actions.get(key);
		}
		return tot;
	}
	
	/**
	 * Méthode qui calcul le nombre d'actions que le joueur a choisi
	 */
	public int totalesActionsJoueurs(HashMap<TypeChaine, Integer> liste_actions) {
		int tot = 0;
		Collection<TypeChaine> keys = liste_actions.keySet();
		for (TypeChaine key : keys) {
			tot += liste_actions.get(key);
		}
		return tot;
	}

}
