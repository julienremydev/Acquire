package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import application.globale.Globals;
import javafx.scene.paint.Color;

public class Chaine implements Serializable{
	public TypeChaine getTypeChaine() {
		return typeChaine;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 6824670178136694341L;
	private TypeChaine typeChaine;
	private ArrayList<Case> listeCase;
	private int nbActionRestante;
	private HashMap<String, Integer> actionParClient;
	
	/**
	 * Constructeur permettant de definir le type des chaines d hotels
	 * initialisation des autres attributs a leur valeur par defaut
	 * @param tc 
	 */
	public Chaine(TypeChaine tc){
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
	 * modification de l'�tat de la case en m�me temps
	 * @param c
	 */
	public void addCase(Case c){
		c.setEtat(this.typeChaine.getNumero());
		this.getListeCase().add(c);
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

	public HashMap<String, Integer> getActionParClient() {
		return actionParClient;
	}

	public void setActionParClient(HashMap<String, Integer> actionParClient) {
		this.actionParClient = actionParClient;
	}
	public boolean chaineDisponible(){
		return listeCase.isEmpty();
	}
	/*
	 * Ajout les cases de la chaine pass�e en parametre � la chaine actuelle, puis supprime de la chaine en parametre
	 */
	public void modifChain(Chaine c) {
		ArrayList<Case> listeChangement = c.getListeCase();
		for (Case caseChaine : listeChangement)
		{
			this.addCase(caseChaine);
		}
		c.removeAll();
	}

	private void removeAll() {
		this.listeCase.removeAll(listeCase);		
	}

	public boolean isSup10() {
		return (this.tailleChaine()>10);
	}

	public boolean isSup41() {
		return (this.tailleChaine()>=41);
	}
}
