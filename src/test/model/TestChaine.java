package test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.model.Case;
import application.model.Chaine;
import application.model.TypeChaine;

public class TestChaine {
	Chaine chaine;

	@Before
	public void initChaine(){
		chaine = new Chaine(TypeChaine.SACKSON);
		
		chaine.addCase(new Case("A1"));
		chaine.addCase(new Case("B1"));
		chaine.addCase(new Case("C1"));
		chaine.addCase(new Case("D1"));
		chaine.addCase(new Case("E1"));
		
		
	}
	
	  
	 
	@Test
	public void testModifChain() {
		Chaine c=new Chaine(TypeChaine.PHOENIX);
		Chaine d=new Chaine(c.getTypeChaine());
		//il y a les meme cases
		c.addCase(new Case("A1"));
		c.addCase(new Case("B1"));
		c.addCase(new Case("C1"));
		c.addCase(new Case("D1"));
		c.addCase(new Case("E1"));
		
		d.addCase(new Case("A1"));
		d.addCase(new Case("B1"));
		d.addCase(new Case("C1"));
		d.addCase(new Case("D1"));
		d.addCase(new Case("E1"));
	
		chaine.modifChain(c);
		for(Case cc: d.getListeCase()){
			assertTrue(chaine.getListeCase().contains(cc));
		}
		
		Chaine cc=new Chaine(TypeChaine.PHOENIX);
		Chaine dd=new Chaine(c.getTypeChaine());
		//il y a des cases loin
		cc.addCase(new Case("A3"));
		cc.addCase(new Case("B3"));
		cc.addCase(new Case("C3"));
		cc.addCase(new Case("D3"));
		cc.addCase(new Case("E3"));
		
		dd.addCase(new Case("A3"));
		dd.addCase(new Case("B3"));
		dd.addCase(new Case("C3"));
		dd.addCase(new Case("D3"));
		dd.addCase(new Case("E3"));
	
		chaine.modifChain(cc);
		for(Case ccc: dd.getListeCase()){
			assertFalse(chaine.getListeCase().contains(ccc));
		}
		
		
	}
}
