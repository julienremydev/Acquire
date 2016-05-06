package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	@SuppressWarnings("unused")
	private Integer actionSackson;
	@SuppressWarnings("unused")
	private Integer actionAmerica;
	@SuppressWarnings("unused")
	private Integer actionFusion;
	@SuppressWarnings("unused")
	private Integer actionPhoenix;
	@SuppressWarnings("unused")
	private Integer actionHydra;
	@SuppressWarnings("unused")
	private Integer actionQuantum;
	@SuppressWarnings("unused")
	private Integer actionZeta;
	//

	@JsonCreator
	public ClientInfo(){
		super();
	}

	/**
	 * Constructeur de la classe client info qui initialise les donnees d un client
	 * @param n : nom du pseudo du client
	 */

	@JsonCreator
	public ClientInfo(@JsonProperty("pseudo")String pseudo){
		this.pseudo = pseudo;
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
			if (listeChaine.get(c.getNumero()-2).tailleChaine()>0) {
				this.net+=this.getPrime(c,listeChaine.get(c.getNumero()-2).tailleChaine(),true);
			}
		}
	}
	/*
	 * Permet de récupérer les primes par rapport aux actions (majoritaire / secondaire)
	 */
	public int getPrime(TypeChaine c, int nombreHotel, boolean calculNet) {
		int action = 0;
		if (calculNet) {
			action = TypeChaine.prixAction(c, nombreHotel);
		}
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
			if (nbJoueursAction>1) {
				return action+((Globals.getResultat((TypeChaine.primeActionnairePrincipal(c, nombreHotel)
						+TypeChaine.primeActionnaireSecondaire(c, nombreHotel))/nbJoueursAction)));
			}
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
		if (!plateau.getCasesDisponible().isEmpty()) {
			String c=plateau.getCasesDisponible().get(0 + (int)(Math.random() * plateau.getCasesDisponible().size()-1));
			main.add(c);	
			plateau.getCasesDisponible().remove(c);
		}
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

	public HashMap<TypeChaine, String> getEtatParChaine() {
		return etatParChaine;
	}

	public void setMain(ArrayList<String> main) {
		this.main = main;
	}

	public void setNet(Integer net) {
		this.net = net;
	}

	public void setCash(Integer cash) {
		this.cash = cash;
	}

	public void setEtatParChaine(HashMap<TypeChaine, String> etatParChaine) {
		this.etatParChaine = etatParChaine;
	}


	public Integer getNet() {
		return net;
	}



	public Integer getCash() {
		return cash;
	}

	public HashMap<TypeChaine, Integer> getActionParChaine() {
		return actionParChaine;
	}

	public void setActionParChaine(HashMap<TypeChaine, Integer> actionParChaine) {
		this.actionParChaine = actionParChaine;
	}


	public Integer getActionSackson() {
		return actionParChaine.get(TypeChaine.SACKSON);
	}

	public void setActionSackson(Integer actionSackson) {
		this.actionSackson = actionSackson;
	}

	public Integer getActionAmerica() {
		return actionParChaine.get(TypeChaine.AMERICA);
	}

	public void setActionAmerica(Integer actionAmerica) {
		this.actionAmerica = actionAmerica;
	}

	public Integer getActionFusion() {
		return actionParChaine.get(TypeChaine.FUSION);
	}

	public void setActionFusion(Integer actionFusion) {
		this.actionFusion = actionFusion;
	}

	public Integer getActionPhoenix() {
		return actionParChaine.get(TypeChaine.PHOENIX);
	}

	public void setActionPhoenix(Integer actionPhoenix) {
		this.actionPhoenix = actionPhoenix;
	}

	public Integer getActionHydra() {
		return actionParChaine.get(TypeChaine.HYDRA);
	}

	public void setActionHydra(Integer actionHydra) {
		this.actionHydra = actionHydra;
	}

	public Integer getActionQuantum() {
		return actionParChaine.get(TypeChaine.QUANTUM);
	}

	public void setActionQuantum(Integer actionQuantum) {
		this.actionQuantum = actionQuantum;
	}

	public Integer getActionZeta() {
		return actionParChaine.get(TypeChaine.ZETA);
	}

	public void setActionZeta(Integer actionZeta) {
		this.actionZeta = actionZeta;
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
