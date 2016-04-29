package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import application.globale.Globals;

public class ClientInfo implements Serializable{
	private static final long serialVersionUID = -4425247831910575515L;

	private String pseudo;

	private ArrayList<String> main;

	private Integer net;

	private Integer cash;

	private HashMap<TypeChaine, Integer> actionParChaine;

	// NE PAS TOUCHER A CES ATTRIBUTS (NECESSAIRE POUR LA VUE MEME SI NON UTILISE)
	private Integer actionSackson;
	private Integer actionAmerica;
	private Integer actionFusion;
	private Integer actionPhoenix;
	private Integer actionHydra;
	private Integer actionQuantum;
	private Integer actionZeta;
	//

	/**
	 * Constructeur de la classe client info qui initialise les donnees d un client
	 * @param n : nom du pseudo du client
	 */
	public ClientInfo(String n){
		this.pseudo = n;
		this.net = 6000;
		this.cash = 6000;
		this.main=new ArrayList<String>();
		this.actionParChaine = new HashMap<TypeChaine,Integer>();
		this.actionParChaine.put(TypeChaine.SACKSON, 0);
		this.actionParChaine.put(TypeChaine.AMERICA, 0);
		this.actionParChaine.put(TypeChaine.FUSION, 0);
		this.actionParChaine.put(TypeChaine.PHOENIX, 0);
		this.actionParChaine.put(TypeChaine.HYDRA, 0);
		this.actionParChaine.put(TypeChaine.QUANTUM, 0);
		this.actionParChaine.put(TypeChaine.ZETA, 0);
	}

	/**
	 * methode metant a jour le cash du joueur en fonction d un montant passe en parametre
	 * le montant peut etre positif (gain du joueur)
	 * le montant peut etre negatif (achat du joueur)
	 * @param montant
	 * @return montant effectivement appliquee au cash
	 */
	public int updateCash(int montant) {
		int res = montant;

		if(this.getCash() + montant < 0) {
			res = - this.getCash();
			this.setCash(0);
		}
		else {
			this.setCash(this.getCash() + montant);			
		}
		
		return res;
	}

	/**
	 * methode permettant de calculer le montant net du joueur
	 */
	public void updateNet(){
		//TODO algorithme de calcul
	}

	/**
	 * ajoute une case dans la main du joueur
	 * @param c
	 */
	public void addCaseToMain(Case c){
		if(c != null){
			this.getMain().add(c.getNom());			
		}
	}

	/**
	 * supprime une case de la main du joueur
	 * @param c
	 */
	public void rmCaseToMain(Case c){
		if(c != null && this.getMain().contains(c.getNom())){
			this.getMain().remove(c.getNom());			
		}
		else {
			// TODO Gestion Case pas dans la main 
		}
	}
	
	/**
	 * Ajoute 6 cases cliquable pour le joueur
	 * @param plateau
	 * @return
	 */
	public ArrayList<String> initialiseMain(Plateau plateau) {
		int max=Globals.nombre_joueurs_max;
		Random randomGenerator = new Random();
		int index;
		for(int i=0;i<max;i++){
			index = randomGenerator.nextInt(plateau.getCasesDisponible().size());
			String c=plateau.getCasesDisponible().get(index);
			main.add(c);
			plateau.getCasesDisponible().remove(c);
		}
		return main;
	}

	/**
	 * Ajoute 1 cases cliquable pour le joueur
	 * @param plateau
	 */
	public void ajouteMain1fois(Plateau plateau) {
		String c=plateau.getCasesDisponible().get(0 + (int)(Math.random() * plateau.getCasesDisponible().size()-1));
		main.add(c);	
		plateau.getCasesDisponible().remove(c);
	}

	/*
	 * Getter & Setter
	 */
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getNet() {
		return net;
	}

	public void setNet(int net) {
		this.net = net;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public HashMap<TypeChaine, Integer> getActionParChaine() {
		return actionParChaine;
	}

	public void setActionParChaine(HashMap<TypeChaine, Integer> actionParChaine) {
		this.actionParChaine = actionParChaine;
	}

	public Integer getActionSackson() {
		return this.actionParChaine.put(TypeChaine.SACKSON, 0);
	}

	public Integer getActionHydra() {
		return this.actionParChaine.put(TypeChaine.HYDRA, 0);
	}

	public Integer getActionAmerica() {
		return this.actionParChaine.put(TypeChaine.AMERICA, 0);
	}

	public Integer getActionFusion() {
		return this.actionParChaine.put(TypeChaine.FUSION, 0);
	}

	public Integer getActionPhoenix() {
		return this.actionParChaine.put(TypeChaine.PHOENIX, 0);
	}

	public Integer getActionQuantum() {
		return this.actionParChaine.put(TypeChaine.QUANTUM, 0);
	}

	public Integer getActionZeta() {
		return this.actionParChaine.put(TypeChaine.ZETA, 0);
	}
	
	public ArrayList<String> getMain(){
		return this.main;
	}
}
