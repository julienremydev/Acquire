package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import application.model.Chaine;
import application.model.ClientInfo;
import application.model.TableauDeBord;
import application.model.TypeChaine;

public class TestTableauDeBord {
	ClientInfo c1;
	ClientInfo c2;
	Chaine ch1;
	Chaine ch2;
	
	TableauDeBord tableauTest;
	ArrayList<Chaine> listeTypeChaine;
	HashMap<String,ClientInfo> infoParClient;
	
	
	@Before
	public void initTableauBord(){
		listeTypeChaine = new ArrayList<Chaine>();
		ch1 = new Chaine (TypeChaine.AMERICA);
		ch2 = new Chaine (TypeChaine.FUSION);
		listeTypeChaine.add(ch1);
		listeTypeChaine.add(ch2);
		tableauTest = new TableauDeBord();
		infoParClient = new HashMap<String,ClientInfo>();
		c1 = new ClientInfo("Yodaii");
		c2 = new ClientInfo("Neo");
		infoParClient.put(c1.getPseudo(),c1);
		infoParClient.put(c2.getPseudo(),c2);
		
		tableauTest.setInfoParClient(infoParClient);
		tableauTest.setListeTypeChaine(listeTypeChaine);
	}
	
	@Test
	public void testGetChaineById() {
		
		assertEquals(ch1, tableauTest.getChaineById(6));
		assertEquals(ch2, tableauTest.getChaineById(5));
		assertNotEquals(ch2, tableauTest.getChaineById(6));
	}
	
	@Test
	public void testAchatActionJoueur() {
		// cas action achete < 0
		// action achete = 0, action restante = 25
		// pas d ajout d actionnaire
		int nbActionAchete = -10;
		int actionAchete = tableauTest.achatActionJoueur(nbActionAchete, "Yodaii", ch1.getNomChaine());
		assertEquals(0, actionAchete);
		
		assertEquals(25, tableauTest.getListeChaine().get(0).getNbActionRestante());
		
		

		// cas normal action achete < action restantes
		// action achete = 3, action restante = 22
		// actionnaire Yodaii ajoute, 3 action detenu par Yodaii
		nbActionAchete = 3;
		actionAchete = tableauTest.achatActionJoueur(nbActionAchete, "Yodaii", ch1.getNomChaine());
		assertEquals(3, actionAchete);
		assertEquals(22, tableauTest.getListeChaine().get(0).getNbActionRestante());
		assertTrue(c1.getActionParChaine().containsKey(ch1.getNomChaine()));
		assertEquals(3, (int)c1.getActionParChaine().get(ch1.getNomChaine()));

		// cas normal action achete < action restantes
		// action achete = 5, action restante = 17
		// actionnaire Neo ajoute, 5 action detenu par Neo
		nbActionAchete = 5;
		actionAchete = tableauTest.achatActionJoueur(nbActionAchete, "Neo", ch1.getNomChaine());
		assertEquals(5, actionAchete);
		assertEquals(17, tableauTest.getListeChaine().get(0).getNbActionRestante());
		assertTrue(c2.getActionParChaine().containsKey(ch1.getNomChaine()));
		assertEquals(5, (int)c2.getActionParChaine().get(ch1.getNomChaine()));

		// cas action achete > action restantes	
		// action achete = 17, action restante = 0
		// pas d ajout d actionnaire, 25 action detenu par Yodaii
		nbActionAchete = 24;
		actionAchete = tableauTest.achatActionJoueur(nbActionAchete, "Yodaii", ch1.getNomChaine());
		assertEquals(17, actionAchete);
		assertEquals(0, tableauTest.getListeChaine().get(0).getNbActionRestante());
		assertTrue(c1.getActionParChaine().containsKey(ch1.getNomChaine()));
		assertEquals(20, (int)c1.getActionParChaine().get(ch1.getNomChaine()));
	}

	@Test
	public void testVendActionJoueur(){
		// cas actionnaire innexistant
		int nbActionVendue = 2;
		int actionVendue = tableauTest.vendActionJoueur(nbActionVendue, "Yodaii", ch1.getNomChaine());
		assertEquals(0, actionVendue);
		assertEquals(25, tableauTest.getListeChaine().get(0).getNbActionRestante());

		tableauTest.achatActionJoueur(5, "Yodaii", ch1.getNomChaine());

		// cas action vend < 0
		// action vendue = 0, action restante = 20
		// Yodaii actionnaire, 5 action detenue
		nbActionVendue = -10;
		actionVendue = tableauTest.vendActionJoueur(nbActionVendue, "Yodaii", ch1.getNomChaine());
		assertEquals(0, actionVendue);
		assertEquals(20, tableauTest.getListeChaine().get(0).getNbActionRestante());
		assertTrue(c1.getActionParChaine().containsKey(ch1.getNomChaine()));
		assertEquals(5, (int)c1.getActionParChaine().get(ch1.getNomChaine()));

		// cas action vendu < nb action detenue par actionnaire et pas bonne chaine
		// action vendue = 2, action restante = 20
		// Yodaii actionnaire, 3 action detenue
		nbActionVendue = 2;
		actionVendue = tableauTest.vendActionJoueur(nbActionVendue, "Neo", ch1.getNomChaine());
		assertEquals(0, actionVendue);
		assertEquals(20, tableauTest.getListeChaine().get(0).getNbActionRestante());
		
		// cas action vendu < nb action detenue par actionnaire
		// action vendue = 2, action restante = 22
		// Yodaii actionnaire, 3 action detenue
		nbActionVendue = 2;
		actionVendue = tableauTest.vendActionJoueur(nbActionVendue, "Yodaii", ch1.getNomChaine());
		assertEquals(2, actionVendue);
		assertEquals(22, tableauTest.getListeChaine().get(0).getNbActionRestante());
		assertTrue(c1.getActionParChaine().containsKey(ch1.getNomChaine()));
		assertEquals(3, (int)c1.getActionParChaine().get(ch1.getNomChaine()));

		
		// cas action vendu > nb action detenue par actionnaire
		// action vendue = 3, action restante = 25
		// plus d actionnaire, 0 action detenue
		nbActionVendue = 4;
		System.out.println(tableauTest.getInfoParClient().get("Yodaii").getActionParChaine().get(ch1.getTypeChaine()));
		actionVendue = tableauTest.vendActionJoueur(nbActionVendue, "Yodaii", ch1.getNomChaine());
		System.out.println(tableauTest.getInfoParClient().get("Yodaii").getActionParChaine().get(ch1.getTypeChaine()));
		System.out.println(actionVendue);
		assertEquals(3, actionVendue);
		assertEquals(25, tableauTest.getListeChaine().get(0).getNbActionRestante());
	}

}
