package application.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
	
	private StringBuffer tchat;
	
	private String chef;

	private boolean partiecommencee;

	private boolean partiechargee;

	/**
	 * Constructeur permettant l'initialisation de la liste de chaîne accessible par plateau et tableauDeBord
	 * Initialisation du plateau et du TableauDeBord
	 */
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
		setTchat(new StringBuffer("[Serveur] Serveur lancé.\n"));
		setPartiecommencee(false);
		setPartiechargee(false);
		
		
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
	//TODO TESTS
	String whoseTurn (String pseudo) throws RemoteException {
		int currentIndice = getOrdre_joueur().indexOf(pseudo);
		if (getOrdre_joueur().size()==currentIndice+1) 
			return getOrdre_joueur().get(0);
		else
			return getOrdre_joueur().get(currentIndice+1);
	}
	
	
	/**
	 * Méthode qui détermine l'ordre des joueurs qui doivent réaliser un choix 
	 * sur les actions qu'il détiennent d'une chaîne absorbée
	 * @throws RemoteException 
	 */
	//TODO TESTS
	void setOrdreFusion() throws RemoteException{
		String player = getPlayerTurn();
		HashMap<TypeChaine,Integer> liste_actions_player = new HashMap<TypeChaine,Integer> ();
		for ( int i = 0 ; i < getOrdre_joueur().size() ; i++){
			liste_actions_player = getTableau().getInfoParClient().get(player).getActionParChaine();
			if ( liste_actions_player.get(getAction().getListeChainesAbsorbees().get(0).getNomChaine()) > 0){
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
		getTableau().getClientInfo(pseudo).getActionParChaine().put(nomChaine, 1+ getTableau().getClientInfo(pseudo).getActionParChaine().get(nomChaine));
	}
	public Plateau getPlateau() {
		return this.plateau;
	}
	
	public TableauDeBord getTableau() {
		return this.tableauDeBord;
	}
	
	public ArrayList<Chaine> getListeChaine(){
		return tableauDeBord.getListeChaine();
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

	public StringBuffer getTchat() {
		return tchat;
	}

	public void setTchat(StringBuffer tchat) {
		this.tchat = tchat;
	}

	public boolean isPartiechargee() {
		return partiechargee;
	}

	public void setPartiechargee(boolean partiechargee) {
		this.partiechargee = partiechargee;
	}

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
	
	public boolean isOver() {
		boolean over=false;
		int iterator = 0;
		ArrayList<Chaine> listChaine = getTableau().getListeChaine();
		while(!over && iterator < listChaine.size()) {
			if (!listChaine.get(iterator).isSup10()) {
				over=true;
			}
			if (listChaine.get(iterator).isSup41()) {
				return true;
			}
			iterator++;
		}
		if (!over) {
			return true;
		}
		return false;
	}

}
