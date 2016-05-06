package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import application.globale.Globals;

public class Chaine implements Serializable{
	
	
	private static final long serialVersionUID = 6824670178136694341L;
	private TypeChaine typeChaine;
	private ArrayList<Case> listeCase;
	private int nbActionRestante;
	private HashMap<String, Integer> actionParClient;
	
	
	@JsonCreator
	public Chaine(){
	}
	
	/**
	 * Constructeur permettant de definir le type des chaines d hotels
	 * initialisation des autres attributs a leur valeur par defaut
	 * @param tc 
	 */
	
	@JsonCreator
	public Chaine(@JsonProperty("tc")TypeChaine tc){
		this.typeChaine = tc;
		this.nbActionRestante = Globals.nbActionTotal;
		this.listeCase = new ArrayList<Case>();
		this.actionParClient = new HashMap<String, Integer>();
	}
	
	/**
	 * fonction retournant la taille de la chaine d hotel
	 * @return taille de la chaine dhotel
	 */
	public Integer tailleChaine(){
		return this.getListeCase().size();
	}
	
	/**
	 * fonction permettant d ajouter une case a la liste de case de la chaine
	 * modification de l'état de la case en même temps
	 * @param c
	 */
	public void addCase(Case c){
		c.setEtat(this.typeChaine.getNumero());
		this.getListeCase().add(c);
	}
	/**
	 * Ajout les cases de la chaine passée en parametre à la chaine actuelle, puis supprime de la chaine en parametre
	 */
	public ArrayList<Case> modifChain(Chaine c) {
		ArrayList<Case> listeChangement = c.getListeCase();
		for (Case caseChaine : listeChangement)
		{
			this.addCase(caseChaine);
		}
		c.removeAll();
		return this.getListeCase();
	}
	/**
	 * Enleve les cases de la chaine
	 */
	private void removeAll() {
		this.listeCase.removeAll(listeCase);		
	}
	
	/*
	 * Liste des Getters et Setters des tous les attributs
	 */

	public boolean chaineDisponible(){
		return listeCase.isEmpty();
	}
	public TypeChaine getTypeChaine() {
		return typeChaine;
	}

	public void setTypeChaine(TypeChaine typeChaine) {
		this.typeChaine = typeChaine;
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

	public HashMap<String, Integer> getActionParClient() {
		return actionParClient;
	}

	public void setActionParClient(HashMap<String, Integer> actionParClient) {
		this.actionParClient = actionParClient;
	}

	

	

	
	@JsonIgnore
	public boolean isSup10() {
		return (this.tailleChaine()>10);
	}

	@JsonIgnore
	public boolean isSup41() {
		return (this.tailleChaine()>=41);
	}
	
	public String toString() {
		return this.listeCase.toString();
	}
}
