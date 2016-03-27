package test.model;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import application.model.Chaine;
import application.model.TypeChaine;

public class TestChaine {
	Chaine chaine;

	@Before
	public void initChaine(){
		chaine = new Chaine(TypeChaine.SACKSON);
	}

	@Test
	public void testAchatActionJoueur() {
		// cas action achete < 0
		// action achete = 0, action restante = 25
		// pas d ajout d actionnaire
		int nbActionAchete = -10;
		int actionAchete = chaine.achatActionJoueur(nbActionAchete, "Yodaii");
		assertEquals(0, actionAchete);
		assertEquals(25, chaine.getNbActionRestante());
		assertFalse(chaine.getActionParClient().containsKey("Yodaii"));

		// cas normal action achete < action restantes
		// action achete = 3, action restante = 22
		// actionnaire Yodaii ajoute, 3 action detenu par Yodaii
		nbActionAchete = 3;
		actionAchete = chaine.achatActionJoueur(nbActionAchete, "Yodaii");
		assertEquals(3, actionAchete);
		assertEquals(22, chaine.getNbActionRestante());
		assertTrue(chaine.getActionParClient().containsKey("Yodaii"));
		assertEquals(3, (int)chaine.getActionParClient().get("Yodaii"));

		// cas normal action achete < action restantes
		// action achete = 3, action restante = 17
		// actionnaire Neo ajoute, 5 action detenu par Neo
		nbActionAchete = 5;
		actionAchete = chaine.achatActionJoueur(nbActionAchete, "Neo");
		assertEquals(5, actionAchete);
		assertEquals(17, chaine.getNbActionRestante());
		assertTrue(chaine.getActionParClient().containsKey("Neo"));
		assertEquals(5, (int)chaine.getActionParClient().get("Neo"));

		// cas action achete > action restantes	
		// action achete = 17, action restante = 0
		// pas d ajout d actionnaire, 25 action detenu par Yodaii
		nbActionAchete = 24;
		actionAchete = chaine.achatActionJoueur(nbActionAchete, "Yodaii");
		assertEquals(17, actionAchete);
		assertEquals(0, chaine.getNbActionRestante());
		assertTrue(chaine.getActionParClient().containsKey("Yodaii"));
		assertEquals(20, (int)chaine.getActionParClient().get("Yodaii"));
	}

	@Test
	public void testVendActionJoueur(){
		// cas actionnaire innexistant
		int nbActionVendue = 2;
		int actionVendue = chaine.vendActionJoueur(nbActionVendue, "Yodaii");
		assertEquals(0, actionVendue);
		assertEquals(25, chaine.getNbActionRestante());
		assertFalse(chaine.getActionParClient().containsKey("Yodaii"));

		chaine.achatActionJoueur(5, "Yodaii");

		// cas action achete < 0
		// action vendue = 0, action restante = 20
		// Yodaii actionnaire, 5 action detenue
		nbActionVendue = -10;
		actionVendue = chaine.vendActionJoueur(nbActionVendue, "Yodaii");
		assertEquals(0, actionVendue);
		assertEquals(20, chaine.getNbActionRestante());
		assertTrue(chaine.getActionParClient().containsKey("Yodaii"));
		assertEquals(5, (int)chaine.getActionParClient().get("Yodaii"));

		// cas action achete < nb action detenue par actionnaire et pas bon actionnaire
		// action vendue = 2, action restante = 22
		// Yodaii actionnaire, 3 action detenue
		nbActionVendue = 2;
		actionVendue = chaine.vendActionJoueur(nbActionVendue, "Neo");
		assertEquals(0, actionVendue);
		assertEquals(20, chaine.getNbActionRestante());
		assertFalse(chaine.getActionParClient().containsKey("Neo"));
		
		// cas action achete < nb action detenue par actionnaire
		// action vendue = 2, action restante = 22
		// Yodaii actionnaire, 3 action detenue
		nbActionVendue = 2;
		actionVendue = chaine.vendActionJoueur(nbActionVendue, "Yodaii");
		assertEquals(2, actionVendue);
		assertEquals(22, chaine.getNbActionRestante());
		assertTrue(chaine.getActionParClient().containsKey("Yodaii"));
		assertEquals(3, (int)chaine.getActionParClient().get("Yodaii"));

		// cas action achete > nb action detenue par actionnaire
		// action vendue = 3, action restante = 25
		// plus d actionnaire, 0 action detenue
		nbActionVendue = 4;
		actionVendue = chaine.vendActionJoueur(nbActionVendue, "Yodaii");
		assertEquals(3, actionVendue);
		assertEquals(25, chaine.getNbActionRestante());
		assertFalse(chaine.getActionParClient().containsKey("Yodaii"));
	}
}
