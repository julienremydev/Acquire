package application.model;

import java.util.ArrayList;

public class ClientInfo {
	private String pseudo;
	
	private ArrayList<Case> main;
	
	private int net;
	
	private int cash;
	
	/**
	 * Constructeur de la classe client info qui initialise les donnees d un client
	 * @param n : nom du pseudo du client
	 */
	public ClientInfo(String n){
		this.pseudo = n;
		this.net = 6000;
		this.cash = 6000;
		this.main = new ArrayList<Case>();
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
		if (c != null){
			this.getMain().add(c);			
		}
	}
	
	/**
	 * supprime une case de la main du joueur
	 * @param c
	 */
	public void rmCaseToMain(Case c){
		this.getMain().remove(c);
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

	public ArrayList<Case> getMain() {
		return main;
	}

	public void setMain(ArrayList<Case> main) {
		this.main = main;
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
}
