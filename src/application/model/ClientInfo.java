package application.model;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;
import javafx.beans.property.SimpleStringProperty;

public class ClientInfo implements Serializable{
	private static final long serialVersionUID = -4425247831910575515L;

	private String pseudo;
	
	private ArrayList<String> main;
	
	private Integer net;
	
	private Integer cash;
	
	/**
	 * Constructeur de la classe client info qui initialise les donnees d un
	 * client
	 * 
	 * @param n
	 *            : nom du pseudo du client
	 */
	public ClientInfo(String n) {
		this.pseudo = n;
		this.net = 6000;
		this.cash = 6000;
		this.main = new ArrayList<String>();
	}

	/**
	 * methode metant a jour le cash du joueur en fonction d un montant passe en
	 * parametre le montant peut etre positif (gain du joueur) le montant peut
	 * etre negatif (achat du joueur)
	 * 
	 * @param montant
	 * @return montant effectivement appliquee au cash
	 */
	public int updateCash(int montant) {
		int res = montant;

		if (this.getCash() + montant < 0) {
			res = -this.getCash();
			this.setCash(0);
		} else {
			this.setCash(this.getCash() + montant);
		}

		return res;
	}

	
	/**
	 * Ajoute 6 cases cliquable pour le joueur
	 */
	public void initialiseMain(Plateau plateau) {
		int max=6;
		for(int i=0;i<max;i++){
			Random generator = new Random();
			String c=plateau.getCaseDisponible().get(0 + (int)(Math.random() * plateau.getCaseDisponible().size()));
			main.add(c);
			plateau.getCaseDisponible().remove((0 + (int)(Math.random() * plateau.getCaseDisponible().size())));
		}
	}
	
	
	/**
	 * Ajoute 1 cases cliquable pour le joueur
	 */

	public void ajouteMain1fois(Plateau plateau) {
			Random generator = new Random();
			String c=plateau.getCaseDisponible().get(0 + (int)(Math.random() * plateau.getCaseDisponible().size()));
			main.add(c);	
			plateau.getCaseDisponible().remove((0 + (int)(Math.random() * plateau.getCaseDisponible().size())));
}
	/**
	public void addCaseToMain(Case c){
		if(c != null){
			this.getMain().put(c.getNom(),c);			
		}
	} **/
	
	
	/**
	 * methode permettant de calculer le montant net du joueur
	 */
	public void updateNet() {

	}


	/**
	 * supprime une case de la main du joueur
	 * 
	 * @param c
	 */
/**	
	public void rmCaseToMain(Case c){
		if(c != null && this.getMain().containsKey(c.getNom())){
			this.getMain().remove(c.getNom());			
		}
	}
	
	**/

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

	public ArrayList<String> getMain() {
		return main;
	}

	public void setMain(ArrayList<String> main) {
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

	public String getPseudo() {
		// TODO Auto-generated method stub
		return pseudo;
	}
}
