package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import application.model.Case;
import application.model.ClientInfo;
import application.model.Plateau;

public class TestClientInfo {
	ClientInfo clientTest;
	
	Plateau plateau = new Plateau();
	
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
	public void testAjouteMain1fois() {
		clientTest.ajouteMain1fois(plateau);
		assertEquals(1, clientTest.getMain().size());
		String cas = clientTest.getMain().get(0);
		assertFalse(plateau.getCasesDisponible().contains(cas));
	}
	
	@Test 
	public void testUpdateNet() {
		//TODO ajouter actions
	}
	
	@Test
	public void testGetPrime() {
		//TODO ajouter actions
	}
}
