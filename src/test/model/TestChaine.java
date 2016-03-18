package test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.model.Chaine;

public class TestChaine {
	Chaine chaine;
	
	@Before
	public void initChaine(){
		chaine = new Chaine("SACKSON");
	}
	
	@Test
	public void testAchatActionJoueur() {
		// cas action achete < 0
		int nbActionAchete = -10;
		int actionAchete = chaine.achatActionJoueur(nbActionAchete);
		assertEquals(0, actionAchete);
		assertEquals(25, chaine.getNbActionRestante());
		
		// cas normal action achete < action restantes
		nbActionAchete = 3;
		actionAchete = chaine.achatActionJoueur(nbActionAchete);
		assertEquals(3, actionAchete);
		assertEquals(22, chaine.getNbActionRestante());
		
		// cas action achete > action restantes	
		nbActionAchete = 24;
		actionAchete = chaine.achatActionJoueur(nbActionAchete);
		assertEquals(22, actionAchete);
		assertEquals(0, chaine.getNbActionRestante());
	}
}
