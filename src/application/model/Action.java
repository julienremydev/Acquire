package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	@JsonCreator
	public Action(){
		super();
	}
	
	/**
	 * Action de création de chaine à partir d'une liste d'hotel
	 * @param listeDeCase
	 * @param type
	 */
	
	@JsonCreator
	public Action(Set<Case> listeDeCaseAModifier, int choix) {
		this.choix = choix;
		this.setListeDeCaseAModifier(listeDeCaseAModifier);
	}
	
	/**
	 * Action de fusion de chaine, avec une chaine plus grande
	 * @param type
	 * @param listeDeChaine
	 * @param chaineFusion
	 */
	
	@JsonCreator
	public Action(@JsonProperty("choix")int choix,@JsonProperty("listeChainesAbsorbees")ArrayList<Chaine> listeChainesAbsorbees, @JsonProperty("chaineAbsorbante")Chaine chaineAbsorbante) {
		
		this.setChoix(choix);
		this.setChaineAbsorbante(chaineAbsorbante);
		this.setListeChainesAbsorbees(listeChainesAbsorbees);
	
	}
	
	/**
	 * Action de fusion de chaine avec choix couleur utilisateur
	 * @param type
	 * @param listePlateau
	 * @param listeDeChaineAProposer
	 * @param caseModif
	 */
	
	//@JsonCreator
	public Action (int choix, ArrayList<Chaine> listeDeChainePlateau,ArrayList<Chaine> listeDeChaineAProposer,ArrayList<Chaine> chaineAbsorbee, ArrayList<Case> listeCaseHotelAbsorbe)
	{
		this.setChoix(choix);
		this.setListeDeChainePlateau(listeDeChainePlateau);
		this.setListeDeChaineAProposer(listeDeChaineAProposer);
		this.setListeChainesAbsorbees(chaineAbsorbee);
		this.setListeCaseAbsorbees(listeCaseHotelAbsorbe);
				
	}

	public ArrayList<Case> getListeCaseAbsorbees() {
		return listeCaseAbsorbees;
	}

	public void setListeCaseAbsorbees(ArrayList<Case> listeCaseAbsorbees) {
		this.listeCaseAbsorbees = listeCaseAbsorbees;
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

	@JsonProperty
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
