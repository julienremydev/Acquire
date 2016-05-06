package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import application.globale.Globals;
import application.model.Case;
import application.model.Chaine;
import application.model.TypeChaine;

public class TestChaine {
	Chaine chaine;

	@Before
	public void initChaine(){
		chaine = new Chaine(TypeChaine.SACKSON);
	}
	
	
	/*
	 * Ajout les cases de la chaine passée en parametre à la chaine actuelle, puis supprime de la chaine en parametre
	 */
	public ArrayList<Case> testModifChain() {
		Chaine c=new Chaine(TypeChaine.PHOENIX);
		Chaine d=c;
		chaine.modifChain(c;)
		for(Case cc: chaine.getListeCase()){
			
		}
		
		ArrayList<Case> listeChangement = c.getListeCase();
		for (Case caseChaine : listeChangement)
		{
			this.addCase(caseChaine);
		}
		c.removeAll();
		return this.getListeCase();
	}


	public boolean testIsSup10() {
		return (this.tailleChaine()>10);
	}

	public boolean testIsSup41() {
		return (this.tailleChaine()>=41);
	}
	
}
