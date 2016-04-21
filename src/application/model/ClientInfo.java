package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientInfo implements Serializable{
	private static final long serialVersionUID = -4425247831910575515L;

	private String pseudo;
	
	private HashMap<String,Case> mainn;
	
	private ArrayList<String> main;
	
	private Integer net;
	
	private Integer cash;
	
	private HashMap<TypeChaine, Integer> actionParChaine;

	/**
	 * Constructeur de la classe client info qui initialise les donnees d un client
	 * @param n : nom du pseudo du client
	 */
	public ClientInfo(String n){
		this.pseudo = n;
		this.net = 6000;
		this.cash = 6000;
		this.mainn = new HashMap<String,Case>();
		this.main=new ArrayList<String>();
		this.actionParChaine = new HashMap<TypeChaine,Integer>();
	}

	/**
	 * methode metant a jour le cash du joueur en fonction d un montant passe en parametre
	 * le montant peut etre positif (gain du joueur)
	 * le montant peut etre negatif (achat du joueur)
	 * @param montant
	 * @return montant effectivement appliquee au cash
	 */
	public int updateCash(int montant){
		int res = montant;
		
		if(this.getCash() + montant < 0){
			res = - this.getCash();
			this.setCash(0);
		}else{
			this.setCash(this.getCash() + montant);			
		}
		
		return res;
	}
	
	/**
	 * methode permettant de calculer le montant net du joueur
	 */
	public void updateNet(){
		
	}
	
	/**
	 * ajoute une case dans la main du joueur
	 * @param c
	 */
	public void addCaseToMain(Case c){
		if(c != null){
			this.getMainn().put(c.getNom(),c);			
		}
	}
	
	/**
	 * supprime une case de la main du joueur
	 * @param c
	 */
	public void rmCaseToMain(Case c){
		if(c != null && this.getMainn().containsKey(c.getNom())){
			this.getMainn().remove(c.getNom());			
		}
	}

	/*
	 * Getter & Setter
	 */
//	public String getPseudo() {
//		return pseudo;
//	}
//
//	public void setPseudo(String pseudo) {
//		this.pseudo = pseudo;
//	}

	public HashMap<String,Case> getMainn() {
		return mainn;
	}

	public void setMain(HashMap<String,Case> main) {
		this.mainn = main;
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
	
	/**
	 * @return
	 */
	public String getPseudo() {
		// TODO Auto-generated method stub
		return pseudo;
	}
	
	/**
	 * Ajoute 6 cases cliquable pour le joueur
	 */
	public ArrayList<String> initialiseMain(Plateau plateau) {
		int max=6;
		//main.add("D3");
		//plateau.getCasesDisponible().remove("D3");
		for(int i=0;i<max;i++){
			String c=plateau.getCasesDisponible().get(0 + (int)(Math.random() * plateau.getCasesDisponible().size()-1));
			//String c=plateau.getCasesDisponible().get("D3");
			main.add(c);
			plateau.getCasesDisponible().remove(c);
		}
		return main;
	}
	
	
	/**
	 * Ajoute 1 cases cliquable pour le joueur
	 */

	public void ajouteMain1fois(Plateau plateau) {
			String c=plateau.getCasesDisponible().get(0 + (int)(Math.random() * plateau.getCasesDisponible().size()-1));
			main.add(c);	
			plateau.getCasesDisponible().remove(c);
}

	public ArrayList<String> getMain(){
		return this.main;
	}
}
