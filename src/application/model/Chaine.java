package application.model;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Color;

public class Chaine {
	private TypeChaine typeChaine;
	private ArrayList<Case> listeCase;
	private static final int nbActionTotal = 25;
	private int nbActionRestante;
	private HashMap<String, Integer> actionParClient;
	
	/**
	 * Constructeur permettant de definir le type des chaines d hotels
	 * initialisation des autres attributs a leur valeur par defaut
	 * @param tc 
	 */
	public Chaine(TypeChaine tc){
		this.typeChaine = tc;
		this.nbActionRestante = 25;
		this.listeCase = new ArrayList<Case>();
		this.actionParClient = new HashMap<String, Integer>();
	}
	
	/**
	 * fonction permettant d ajouter une case a la liste de case de la chaine
	 * @param c
	 */
	public void addCase(Case c){
		this.getListeCase().add(c);
	}

	/**
	 * fonction permettant au joueur d acheter des actions et met a jour le nombre d action restante
	 * @param nb : nombre d action voulant etre acheter par le joueur
	 * @param nomJoueur : nom du joueur qui achete
	 * @return nombre effectivement acheter
	 */
	public int achatActionJoueur(int nb, String nomJoueur){
		if(nb < 0){
			nb = 0;
		}

		int res = nb;
		
		if(this.getNbActionRestante()-nb < 0){ // on ne peut pas avoir un nombre daction restante negatif
			res = this.getNbActionRestante();
			this.setNbActionRestante(0);
		} else {
			this.setNbActionRestante(this.getNbActionRestante()-nb);
		}
		
		if(this.actionParClient.containsKey(nomJoueur)){
			this.actionParClient.put(nomJoueur, res + this.actionParClient.get(nomJoueur));
		}else{
			this.actionParClient.put(nomJoueur, res);			
		}
		
		return res;
	}
	
	/**
	 * fonction permettant au joueur de vendre des actions et met a jour le nombre d action restante
	 * @param nb : nombre d action voulant etre vendue par le joueur
	 * @return nombre effectivement vendue
	 */
	public int vendActionJoueur(int nb, String nomJoueur){
		if (nb < 0){
			nb = 0;
		}
		
		int res = nb;
		
		if(this.getNbActionRestante()+nb > nbActionTotal){ // on ne peut pas avoir plus de 25 action
			res = nbActionTotal-this.getNbActionRestante();
			this.setNbActionRestante(nbActionTotal);
		} else {
			this.setNbActionRestante(this.getNbActionRestante()+nb);
		}
		
		if(this.actionParClient.containsKey(nomJoueur)){
			if (this.actionParClient.get(nomJoueur) >= res){
				this.actionParClient.put(nomJoueur, this.actionParClient.get(nomJoueur) - res);				
			}else{
				res = 0;
			}
		}
		
		return res;
	}
	
	/**
	 * fonction retournant la taille de la chaine d hotel
	 * @return taille de la chaine dhotel
	 */
	public int tailleChaine(){
		return this.getListeCase().size();
	}
	
	/**
	 * methode qui retourne le type de la chaine
	 * @return
	 */
	public static Chaine getChaine(int num){
		TypeChaine.getTypeChaine(num);
		return null;
	}

	
	/*
	 * Liste des Getters et Setters des tous les attributs
	 */
	public TypeChaine getNomChaine() {
		return typeChaine;
	}

	public void setTypeChaine(TypeChaine tc) {
		this.typeChaine = tc;
	}

	public ArrayList<Case> getListeCase() {
		return listeCase;
	}

	public void setListeCase(ArrayList<Case> listeCase) {
		this.listeCase = listeCase;
	}

	public int getNbActionRestante() {
		return nbActionRestante;
	}

	public void setNbActionRestante(int nbActionRestante) {
		this.nbActionRestante = nbActionRestante;
	}

	public static int getNbactiontotal() {
		return nbActionTotal;
	}
}
