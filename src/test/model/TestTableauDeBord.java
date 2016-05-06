package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import application.model.Case;
import application.model.Chaine;
import application.model.ClientInfo;
import application.model.TableauDeBord;
import application.model.TypeChaine;

public class TestTableauDeBord {
	ClientInfo c1;
	ClientInfo c2;
	ClientInfo c3;
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
		Chaine ch3 = new Chaine (TypeChaine.HYDRA);
		Chaine ch4 = new Chaine (TypeChaine.PHOENIX);
		Chaine ch5 = new Chaine (TypeChaine.QUANTUM);
		Chaine ch6 = new Chaine (TypeChaine.SACKSON);
		Chaine ch7 = new Chaine (TypeChaine.ZETA);
		listeTypeChaine.add(ch1);
		listeTypeChaine.add(ch2);
		listeTypeChaine.add(ch3);
		listeTypeChaine.add(ch4);
		listeTypeChaine.add(ch5);
		listeTypeChaine.add(ch6);
		listeTypeChaine.add(ch7);
		tableauTest = new TableauDeBord();
		infoParClient = new HashMap<String,ClientInfo>();
		c1 = new ClientInfo("Yodaii");
		c2 = new ClientInfo("Neo");
		c3 = new ClientInfo("Joke");
		infoParClient.put(c1.getPseudo(),c1);
		infoParClient.put(c2.getPseudo(),c2);
		infoParClient.put(c3.getPseudo(),c3);
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
		ch1.getListeCase().add(new Case("A1"));
		ch1.getListeCase().add(new Case("B1"));
		
		// cas action achete < 0
		// action achete = 0, action restante = 25
		// pas d ajout d actionnaire
		int nbActionAchete = -10;
		int actionAchete = tableauTest.achatActionJoueur(nbActionAchete, "Yodaii", ch1.getTypeChaine());
		assertEquals(0, actionAchete);
		assertEquals(25, tableauTest.getListeChaine().get(0).getNbActionRestante());
		assertEquals(new Integer(6000), c1.getCash());

		// cas normal action achete < action restantes
		// action achete = 3, action restante = 22
		// actionnaire Yodaii ajoute, 3 action detenu par Yodaii
		nbActionAchete = 3;
		int cashAAvoir = c1.getCash()-nbActionAchete*TypeChaine.prixAction(ch1.getTypeChaine(), ch1.getListeCase().size());
		actionAchete = tableauTest.achatActionJoueur(nbActionAchete, "Yodaii", ch1.getTypeChaine());
		assertEquals(3, actionAchete);
		assertEquals(22, tableauTest.getListeChaine().get(0).getNbActionRestante());
		assertTrue(c1.getActionParChaine().containsKey(ch1.getTypeChaine()));
		assertEquals(3, (int)c1.getActionParChaine().get(ch1.getTypeChaine()));
		assertEquals(new Integer(cashAAvoir), c1.getCash());
		
		// cas normal action achete < action restantes
		// action achete = 5, action restante = 17
		// actionnaire Neo ajoute, 5 action detenu par Neo
		nbActionAchete = 5;
		cashAAvoir = c2.getCash()-nbActionAchete*TypeChaine.prixAction(ch1.getTypeChaine(), ch1.getListeCase().size());
		actionAchete = tableauTest.achatActionJoueur(nbActionAchete, "Neo", ch1.getTypeChaine());
		assertEquals(5, actionAchete);
		assertEquals(17, tableauTest.getListeChaine().get(0).getNbActionRestante());
		assertTrue(c2.getActionParChaine().containsKey(ch1.getTypeChaine()));
		assertEquals(5, (int)c2.getActionParChaine().get(ch1.getTypeChaine()));
		assertEquals(new Integer(cashAAvoir), c2.getCash());

		// cas action achete > action restantes	
		// action achete = 17, action restante = 0
		// pas d ajout d actionnaire, 25 action detenu par Yodaii
		nbActionAchete = 24;
		actionAchete = tableauTest.achatActionJoueur(nbActionAchete, "Yodaii", ch1.getTypeChaine());
		assertEquals(17, actionAchete);
		assertEquals(0, tableauTest.getListeChaine().get(0).getNbActionRestante());
		assertTrue(c1.getActionParChaine().containsKey(ch1.getTypeChaine()));
		assertEquals(20, (int)c1.getActionParChaine().get(ch1.getTypeChaine()));
		assertEquals(new Integer(0), c1.getCash());
	}

	@Test
	public void testVendActionJoueur(){
		ch1.getListeCase().add(new Case("A1"));
		ch1.getListeCase().add(new Case("B1"));
		
		// cas actionnaire innexistant
		int nbActionVendue = 2;
		int actionVendue = tableauTest.vendActionJoueur(nbActionVendue, "Yodaii", ch1.getTypeChaine(),200, 200);
		assertEquals(0, actionVendue);
		assertEquals(25, tableauTest.getListeChaine().get(0).getNbActionRestante());
		
		tableauTest.achatActionJoueur(5, "Yodaii", ch1.getTypeChaine());

		// cas action vend < 0
		// action vendue = 0, action restante = 20
		// Yodaii actionnaire, 5 action detenue
		nbActionVendue = -10;
		actionVendue = tableauTest.vendActionJoueur(nbActionVendue, "Yodaii", ch1.getTypeChaine(),200,200);
		assertEquals(0, actionVendue);
		assertEquals(20, tableauTest.getListeChaine().get(0).getNbActionRestante());
		assertTrue(c1.getActionParChaine().containsKey(ch1.getTypeChaine()));
		assertEquals(5, (int)c1.getActionParChaine().get(ch1.getTypeChaine()));

		// cas action vendu < nb action detenue par actionnaire et pas bonne chaine
		// action vendue = 2, action restante = 20
		// Yodaii actionnaire, 3 action detenue
		nbActionVendue = 2;
		actionVendue = tableauTest.vendActionJoueur(nbActionVendue, "Neo", ch1.getTypeChaine(),200,200);
		assertEquals(0, actionVendue);
		assertEquals(20, tableauTest.getListeChaine().get(0).getNbActionRestante());

		// cas action vendu < nb action detenue par actionnaire
		// action vendue = 2, action restante = 22
		// Yodaii actionnaire, 3 action detenue
		nbActionVendue = 2;
		int cashAAvoir = c1.getCash()+nbActionVendue*TypeChaine.prixAction(ch1.getTypeChaine(), ch1.getListeCase().size());
		actionVendue = tableauTest.vendActionJoueur(nbActionVendue, "Yodaii", ch1.getTypeChaine(),200,200);
		assertEquals(2, actionVendue);
		assertEquals(22, tableauTest.getListeChaine().get(0).getNbActionRestante());
		assertTrue(c1.getActionParChaine().containsKey(ch1.getTypeChaine()));
		assertEquals(3, (int)c1.getActionParChaine().get(ch1.getTypeChaine()));
		assertEquals(new Integer(cashAAvoir), c1.getCash());


		// cas action vendu > nb action detenue par actionnaire
		// action vendue = 3, action restante = 25
		// plus d actionnaire, 0 action detenue
		nbActionVendue = 4;
		cashAAvoir = c1.getCash()+3*TypeChaine.prixAction(ch1.getTypeChaine(), ch1.getListeCase().size());
		actionVendue = tableauTest.vendActionJoueur(nbActionVendue, "Yodaii", ch1.getTypeChaine(),200,200);
		assertEquals(3, actionVendue);
		assertEquals(25, tableauTest.getListeChaine().get(0).getNbActionRestante());
		assertEquals(new Integer(cashAAvoir), c1.getCash());
	}


	@Test
	public void testEchangeAction(){
		tableauTest.achatActionJoueur(10, "Yodaii", ch1.getTypeChaine());

		// cas normal action detenu > action a echanger et action a acheter <= action restante
		// action a echanger = 4, action possedees = 10
		// action detenu de la chaine 1 = 6
		// action detenu de la chaine 2 = 2
		int nbActionAEchanger = 4;
		int res = tableauTest.echangeAction(nbActionAEchanger, ch1.getTypeChaine(), ch2.getTypeChaine(), "Yodaii");
		assertEquals(2, res);
		assertEquals(2, c1.getActionParChaine().get(ch2.getTypeChaine()).intValue());
		assertEquals(6, c1.getActionParChaine().get(ch1.getTypeChaine()).intValue());

		// cas normal action detenu > action a echanger et action a acheter <= action restante
		// action a echanger = 5 != paire, action possedes = 6
		// action detenu de la chaine 1 = 4
		// action detenu de la chaine 2 = 4
		nbActionAEchanger = 5;
		res = tableauTest.echangeAction(nbActionAEchanger, ch1.getTypeChaine(), ch2.getTypeChaine(), "Yodaii");
		assertEquals(2, res);
		assertEquals(4, c1.getActionParChaine().get(ch2.getTypeChaine()).intValue());
		assertEquals(2, c1.getActionParChaine().get(ch1.getTypeChaine()).intValue());

		// cas action a echanger > action detenu
		// action a echanger 6, action possedees 2
		// action detenu de la chaine 1 et 2 inchangees
		res = tableauTest.echangeAction(nbActionAEchanger, ch1.getTypeChaine(), ch2.getTypeChaine(), "Yodaii");
		assertEquals(4, c1.getActionParChaine().get(ch2.getTypeChaine()).intValue());
		assertEquals(2, c1.getActionParChaine().get(ch1.getTypeChaine()).intValue());

		// cas action a acheter < action restante
		// action a echanger 6, action restante 3
		// action detenu de la chaine 1 et 2 inchangees
		tableauTest.achatActionJoueur(18, "Yodaii", ch2.getTypeChaine());
		res = tableauTest.echangeAction(nbActionAEchanger, ch1.getTypeChaine(), ch2.getTypeChaine(), "Yodaii");
		assertEquals(22, c1.getActionParChaine().get(ch2.getTypeChaine()).intValue());
		assertEquals(2, c1.getActionParChaine().get(ch1.getTypeChaine()).intValue());
	}
	
	@Test
	public void testAchatActions(){
		HashMap<TypeChaine, Integer> hm= new HashMap<>();
		hm.put(ch1.getTypeChaine(), 2);
		hm.put(ch2.getTypeChaine(), 3); 
		
		tableauTest.achatActions("Yodaii", hm);
		assertEquals(2, c1.getActionParChaine().get(ch1.getTypeChaine()).intValue());
		assertEquals(3, c1.getActionParChaine().get(ch2.getTypeChaine()).intValue());
	}
	
	@Test
	public void testTraiteAction(){
		tableauTest.achatActionJoueur(10, "Yodaii", ch1.getTypeChaine());
		tableauTest.achatActionJoueur(10, "Yodaii", ch2.getTypeChaine());
		
		HashMap<String, Integer> hmTest = new HashMap<>();
		hmTest.put("SELL", 2);
		hmTest.put("TRADE", 2);
		
		tableauTest.traiteAction(hmTest, ch1, ch2, "Yodaii",200, 200);
		
		assertEquals(4, (int)c1.getActionParChaine().get(ch1.getTypeChaine()));
		assertEquals(12, (int)c1.getActionParChaine().get(ch2.getTypeChaine()));
	}
	
	@Test
	public void testUpdateActionnaire() {
		c1.getActionParChaine().put(TypeChaine.AMERICA, 1);
		tableauTest.updateActionnaire();
		String etat = c1.getEtatParChaine().get(TypeChaine.AMERICA);
		assertEquals("M+,0",etat);
		c2.getActionParChaine().put(TypeChaine.AMERICA, 2);
		tableauTest.updateActionnaire();
		etat = c1.getEtatParChaine().get(TypeChaine.AMERICA);
		String etat2 = c2.getEtatParChaine().get(TypeChaine.AMERICA);
		assertEquals("S,1", etat);
		assertEquals("M,1", etat2);
		c1.getActionParChaine().put(TypeChaine.AMERICA, 2);
		tableauTest.updateActionnaire();
		etat = c1.getEtatParChaine().get(TypeChaine.AMERICA);
		etat2 = c2.getEtatParChaine().get(TypeChaine.AMERICA);
		assertEquals("M,2", etat);
		assertEquals("M,2", etat2);
		c3.getActionParChaine().put(TypeChaine.AMERICA, 2);
		tableauTest.updateActionnaire();
		etat = c1.getEtatParChaine().get(TypeChaine.AMERICA);
		etat2 = c2.getEtatParChaine().get(TypeChaine.AMERICA);
		String etat3 = c3.getEtatParChaine().get(TypeChaine.AMERICA);
		assertEquals("M,3", etat);
		assertEquals("M,3", etat2);
		assertEquals("M,3", etat3);
		c3.getActionParChaine().put(TypeChaine.AMERICA, 4);
		tableauTest.updateActionnaire();
		etat = c1.getEtatParChaine().get(TypeChaine.AMERICA);
		etat2 = c2.getEtatParChaine().get(TypeChaine.AMERICA);
		etat3 = c3.getEtatParChaine().get(TypeChaine.AMERICA);
		assertEquals("S,2", etat);
		assertEquals("S,2", etat2);
		assertEquals("M,1", etat3);
		c1.getActionParChaine().put(TypeChaine.AMERICA, 2);
		c2.getActionParChaine().put(TypeChaine.AMERICA, 4);
		c3.getActionParChaine().put(TypeChaine.AMERICA, 4);
		tableauTest.updateActionnaire();
		etat = c1.getEtatParChaine().get(TypeChaine.AMERICA);
		etat2 = c2.getEtatParChaine().get(TypeChaine.AMERICA);
		etat3 = c3.getEtatParChaine().get(TypeChaine.AMERICA);
		assertEquals("M,2", etat2);
		assertEquals("M,2", etat3);
		assertEquals("A,0", etat);
	}
	
	@Test
	public void testActionAvailableForPlayer(){
		ch1.getListeCase().add(new Case("A1"));
		ch1.getListeCase().add(new Case("A2"));
		
		// cas normal action disponible et cash ok
		assertTrue(tableauTest.actionAvailableForPlayer("Yodaii"));
		
		// cas pas d actions disponibles
		tableauTest.achatActionJoueur(25, "Yodaii", ch1.getTypeChaine());
		assertFalse(tableauTest.actionAvailableForPlayer("Yodaii"));
		
		// cas pas de cash et pas d action disponible
		c1.setCash(0);
		assertFalse(tableauTest.actionAvailableForPlayer("Yodaii"));
		
		// cas cash pas disponible
		tableauTest.vendActionJoueur(25, "Yodaii", ch1.getTypeChaine(),200, 200);
		c1.setCash(0);
		assertFalse(tableauTest.actionAvailableForPlayer("Yodaii"));
	}
	
	@Test
	public void testActionAvailableForPlayer2(){
		ch1.getListeCase().add(new Case("A1"));
		ch1.getListeCase().add(new Case("A2"));
		
		// cas normal action disponible et cash ok
		assertTrue(tableauTest.actionAvailableForPlayer("Yodaii", 6, 2000));
		
		// cas pas d actions disponibles
		tableauTest.achatActionJoueur(25, "Yodaii", ch1.getTypeChaine());
		assertFalse(tableauTest.actionAvailableForPlayer("Yodaii", 6, 2000));
				
		// cas pas de cash et pas d action disponible
		c1.setCash(0);
		assertFalse(tableauTest.actionAvailableForPlayer("Yodaii", 6, 2000));
				
		// cas cash pas disponible
		tableauTest.vendActionJoueur(25, "Yodaii", ch1.getTypeChaine(),200, 200);
		c1.setCash(0);
		assertFalse(tableauTest.actionAvailableForPlayer("Yodaii", 6, 2000));
	}
}
