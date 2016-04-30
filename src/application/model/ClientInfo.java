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

	private HashMap<TypeChaine, String> etatParChaine;
	//etat 0 = pas d'action
	//etat 1 = actionnaire
	//etat 2 = secondaire
	//etat 3 = primaire
	//etat 4 = secondaire2
	//etat 5 = primaire2

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
		this.etatParChaine = new HashMap<TypeChaine,String>();
		this.etatParChaine.put(TypeChaine.SACKSON, "0,0");
		this.etatParChaine.put(TypeChaine.AMERICA, "0,0");
		this.etatParChaine.put(TypeChaine.FUSION,"0,0");
		this.etatParChaine.put(TypeChaine.PHOENIX, "0,0");
		this.etatParChaine.put(TypeChaine.HYDRA, "0,0");
		this.etatParChaine.put(TypeChaine.QUANTUM, "0,0");
		this.etatParChaine.put(TypeChaine.ZETA, "0,0");
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
	public void updateNet(ArrayList<Chaine> listeChaine){
		this.net=this.cash;
		for (TypeChaine c : this.actionParChaine.keySet()) {
			this.net+=this.getPrime(c,listeChaine.get(c.getNumero()-2).tailleChaine());
		}
	}

	public int getPrime(TypeChaine c, int nombreHotel) {
		int action = TypeChaine.prixAction(c, nombreHotel);
		action*=actionParChaine.get(c);
		String etat = etatParChaine.get(c);
		String[] tabEtat = etat.split(",");
		int nbJoueursAction = Integer.parseInt(tabEtat[1]);
		switch (tabEtat[0]) {
		case "0" :
		return 0;
		case "A" :
			return action;
		case "S" :
			return action+(Globals.getResultat(TypeChaine.primeActionnaireSecondaire(c, nombreHotel)/nbJoueursAction));
		case "M" :
			return action+(Globals.getResultat(TypeChaine.primeActionnairePrincipal(c, nombreHotel)/nbJoueursAction));
		case "M+" :
			return action+(Globals.getResultat(TypeChaine.primeActionnairePrincipal(c, nombreHotel)+TypeChaine.primeActionnaireSecondaire(c, nombreHotel)));
		default :
			return -1;
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
		return this.actionParChaine.get(TypeChaine.SACKSON);
	}

	public Integer getActionHydra() {
		return this.actionParChaine.get(TypeChaine.HYDRA);
	}

	public Integer getActionAmerica() {
		return this.actionParChaine.get(TypeChaine.AMERICA);
	}

	public Integer getActionFusion() {
		return this.actionParChaine.get(TypeChaine.FUSION);
	}

	public Integer getActionPhoenix() {
		return this.actionParChaine.get(TypeChaine.PHOENIX);
	}

	public Integer getActionQuantum() {
		return this.actionParChaine.get(TypeChaine.QUANTUM);
	}

	public Integer getActionZeta() {
		return this.actionParChaine.get(TypeChaine.ZETA);
	}

	public ArrayList<String> getMain(){
		return this.main;
	}

	public void setEtat(int nb, String etat) {
		this.etatParChaine.replace(TypeChaine.getTypeChaine(nb), etat);
	}
	
	public String toString() {
		return this.pseudo;
	}

}
