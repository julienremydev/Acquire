package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class Action implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1987155349689841351L;

	private int choix; // les choix sont dans la classe Globals
	
	private int couleur;
	
	private ArrayList<Chaine> listeDeChainePlateau;
	
	private Case caseModifiee;
	
	private Set<Case> listeDeCaseAModifier;
	
	private ArrayList<Chaine> listeDeChaineAProposer;
	
	private ArrayList<Chaine> listeChainesAbsorbees;
	
	private ArrayList<Case> listeCaseAbsorbees;
	
	private Chaine chaineAbsorbante;
	
	public int getChoix() {
		return choix;
	}

	public void setChoix(int choix) {
		this.choix = choix;
	}

	public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}
	/**
	 * Action de création de chaine à partir d'une liste d'hotel
	 * @param listeDeCase
	 * @param type
	 */
	public Action(Set<Case> listeDeCase, int type) {
		this.choix = type;
		this.setListeDeCaseAModifier(listeDeCase);
	}
	
	/**
	 * Action de fusion de chaine, avec une chaine plus grande
	 * @param type
	 * @param listeDeChaine
	 * @param chaineFusion
	 */
	public Action(int type,ArrayList<Chaine> listeDeChaine, Chaine chaineFusion) {
		
		this.setChoix(type);
		this.setChaineAbsorbante(chaineFusion);
		this.setListeChainesAbsorbees(listeDeChaine);
	
	}
	
	/**
	 * Action de fusion de chaine avec choix couleur utilisateur
	 * @param type
	 * @param listePlateau
	 * @param listeDeChaineAProposer
	 * @param caseModif
	 */
	public Action (int type,ArrayList<Chaine> listePlateau, ArrayList<Chaine> listeDeChaineAProposer, Case caseModif)
	{
		this.setChoix(type);
		this.setListeDeChainePlateau(listePlateau);
		this.setListeDeChaineAProposer(listeDeChaineAProposer);
		this.setCaseModifiee(caseModif);
				
	}

	public Set<Case> getListeDeCaseAModifier() {
		return listeDeCaseAModifier;
	}

	public void setListeDeCaseAModifier(Set<Case> listeDeCaseAModifier) {
		this.listeDeCaseAModifier = listeDeCaseAModifier;
	}

	public ArrayList<Chaine> getListeDeChaineAProposer() {
		return listeDeChaineAProposer;
	}

	public void setListeDeChaineAProposer(ArrayList<Chaine> listeDeChaineAProposer) {
		this.listeDeChaineAProposer = listeDeChaineAProposer;
	}

	public ArrayList<Chaine> getListeChainesAbsorbees() {
		return listeChainesAbsorbees;
	}

	public void setListeChainesAbsorbees(ArrayList<Chaine> listeChainesAbsorbees) {
		this.listeChainesAbsorbees = listeChainesAbsorbees;
	}

	public Chaine getChaineAbsorbante() {
		return chaineAbsorbante;
	}

	public void setChaineAbsorbante(Chaine chaineAbsorbante) {
		this.chaineAbsorbante = chaineAbsorbante;
	}

	public ArrayList<Chaine> getListeDeChainePlateau() {
		return listeDeChainePlateau;
	}

	public void setListeDeChainePlateau(ArrayList<Chaine> listeDeChainePlateau) {
		this.listeDeChainePlateau = listeDeChainePlateau;
	}

	public Case getCaseModifiee() {
		return caseModifiee;
	}

	public void setCaseModifiee(Case caseModifiee) {
		this.caseModifiee = caseModifiee;
	}


}
