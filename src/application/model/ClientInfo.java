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
