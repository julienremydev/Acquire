package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import application.model.Case;
import application.model.ClientInfo;

public class TestClientInfo {
	ClientInfo clientTest;
	
	@Before
	public void initClient(){
		clientTest = new ClientInfo("Yodaii");
	}
	
	@Test
	public void testUpdateCash() {
		/*
		 * Soustraction Cash
		 */
		// cas normal cash - montant > 0, cash depart = 6000
		int montantApplique = clientTest.updateCash(-1000);
		assertEquals(5000, clientTest.getCash());
		assertEquals(-1000, montantApplique);
		
		// cas cash - montant < 0, cash depart = 5000
		montantApplique = clientTest.updateCash(-6000);
		assertEquals(0, clientTest.getCash());
		assertEquals(-5000, montantApplique);
		
		
		/*
		 * Addition Cash
		 */
		// cash depart = 0
		montantApplique = clientTest.updateCash(6000);
		assertEquals(6000, clientTest.getCash());
		assertEquals(6000, montantApplique);
	}

	@Test
	public void testAddCaseToMain(){
		Case c = new Case("test");
		Case c2 = new Case("test2");
		Case fail = null;
		
		// cas main ajout case vide
		// pas d'ajout dans la main
		clientTest.addCaseToMain(fail);
		assertTrue(clientTest.getMain().isEmpty());
		
		// cas normal ajout d une case dans main vide
		// ajout d une case dans la main
		clientTest.addCaseToMain(c);
		HashMap<String, Case> test = new HashMap<String, Case>();
		test.put(c.getNom(), c);
		assertEquals(test, clientTest.getMain());
		
		// cas normal ajout d une case dans main avec deja case
		// main contient 2 cases
		clientTest.addCaseToMain(c2);
		test.put(c2.getNom(), c2);
		assertEquals(test, clientTest.getMain());
	}
	
	@Test 
	public void testRmCaseToMain(){
		Case c = new Case("test");
		Case c2 = new Case("test2");
		clientTest.addCaseToMain(c);
		clientTest.addCaseToMain(c2);
		
		// cas ou il reste des case dans la main
		clientTest.rmCaseToMain(c2);
		HashMap<String, Case> test = new HashMap<String, Case>();
		test.put(c.getNom(), c);
		assertEquals(test, clientTest.getMain());
		
		// cas ou la main et vide
		clientTest.rmCaseToMain(c);
		test.remove(c.getNom());
		assertEquals(test, clientTest.getMain());	
		
		// cas case pas dans main
		clientTest.rmCaseToMain(new Case("test3"));
		assertEquals(test, clientTest.getMain());
		
		// cas case null
		clientTest.rmCaseToMain(null);
		assertEquals(test, clientTest.getMain());
	}
}
