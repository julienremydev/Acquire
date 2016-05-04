package test.model;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.model.Case;
import application.model.CaseBot;
import application.model.CaseBotLeft;
import application.model.CaseBotRight;
import application.model.CaseLeft;
import application.model.CaseRight;
import application.model.CaseTop;
import application.model.CaseTopLeft;
import application.model.CaseTopRight;

public class TestCase {
	Case caseTest;
	Case north;
	Case south;
	Case east;
	Case west;
	
	CaseBot caseTestBot;
	CaseTop caseTestTop;
	CaseLeft caseTestLeft;
	CaseRight caseTestRight;
	CaseBotLeft caseTestBotLeft;
	CaseBotRight caseTestBotRight;
	CaseTopLeft caseTestTopLeft;
	CaseTopRight caseTestTopRight;

	@Before
	public void initCase(){
		caseTest = new Case("test");
		caseTestBot = new CaseBot("bot");
		caseTestTop = new CaseTop("top");
		caseTestRight = new CaseRight("right");
		caseTestLeft = new CaseLeft("left");
		caseTestBotLeft = new CaseBotLeft("botLeft");
		caseTestBotRight = new CaseBotRight("botRight");
		caseTestTopLeft = new CaseTopLeft("topLeft");
		caseTestTopRight = new CaseTopRight("topRight");
		
		north = new Case("north");
		south = new Case("south");
		east = new Case("east");
		west = new Case("west");

		caseTest.setNorth(north);
		caseTest.setSouth(south);
		caseTest.setEast(east);
		caseTest.setWest(west);
		
		caseTestBotLeft.setNorth(north);
		caseTestBotLeft.setEast(east);
		
	}

	@Test
	public void testSurroundedByNothing() {
		// toutes les cases autour ont un etat egal a 0
		assertTrue(caseTest.surroundedByNothing());

		// une case autour a un etat egal a une valeur >= 2
		north.setEtat(5);
		assertFalse(caseTest.surroundedByNothing());

		// une case autour a un etat egal a 1
		north.setEtat(1);
		assertFalse(caseTest.surroundedByNothing());

		// toutes les cases autour ont un etat egal a 1
		south.setEtat(1);
		east.setEtat(1);
		west.setEtat(1);
		assertFalse(caseTest.surroundedByNothing());
	}

	@Test
	public void testSurroundedByHotels() {
		// toutes les cases autour ont un etat egal a 0
		assertFalse(caseTest.surroundedByHotels());

		// une case autour a un etat egal a -1
		north.setEtat(-1);
		assertFalse(caseTest.surroundedByHotels());

		// une case autour a un etat egal a une valeur >= 2
		north.setEtat(5);
		assertFalse(caseTest.surroundedByHotels());

		// une case autour a un etat egal a une valeur >= 2 et un etat egal a 1
		south.setEtat(1);
		assertTrue(caseTest.surroundedByHotels());

		// une case autour a un etat egal a 1
		north.setEtat(1);
		assertTrue(caseTest.surroundedByHotels());

		// toutes les cases autour ont un etat egal a 1
		south.setEtat(1);
		east.setEtat(1);
		west.setEtat(1);
		assertTrue(caseTest.surroundedByHotels());
	}

	@Test
	public void testSurroundedByChains() {
		// toutes les cases autour ont un etat egal a 0
		assertFalse(caseTest.surroundedByChains());
		assertFalse(caseTestBotLeft.surroundedByChains());

		// une case autour a un etat egal a 1 et les autres a 0
		north.setEtat(1);
		assertFalse(caseTest.surroundedByChains());
		assertFalse(caseTestBotLeft.surroundedByChains());

		// une case autour a un etat egal a -1 et les autres a 0
		north.setEtat(-1);
		assertFalse(caseTest.surroundedByChains());
		assertFalse(caseTestBotLeft.surroundedByChains());

		// une case autour a un etat egal a une valeur >= 2 et les autres a 0
		north.setEtat(5);
		assertTrue(caseTest.surroundedByChains());
		assertTrue(caseTestBotLeft.surroundedByChains());

		// toutes les cases autour ont un etat egal a une meme valeur >= 2
		south.setEtat(5);
		east.setEtat(5);
		west.setEtat(5);
		assertTrue(caseTest.surroundedByChains());
		assertTrue(caseTestBotLeft.surroundedByChains());

		// toutes les cases autour ont un etat egal a differente valeur >= 2
		south.setEtat(3);
		east.setEtat(7);
		west.setEtat(6);
		assertTrue(caseTest.surroundedByChains());
		assertTrue(caseTestBotLeft.surroundedByChains());
	}

	// Test sur le tab adjascent dans le cas ou la case est au milieu donc 4 cases autour, ou certaines sont injouables ou vide, et ou la case
	// est une case particuliere donc dans un coins ou un coté
	@Test
	public void testTabAdjacent(){
		
		ArrayList<Case> test = new ArrayList<Case>();
		test.add(north);
		test.add(south);
		test.add(east);
		test.add(west);
		
		assertNotEquals(test, caseTest.tabAdjascent());

		north.setEtat(1);
		south.setEtat(2);
		east.setEtat(3);
		west.setEtat(4);
		
		assertEquals(test, caseTest.tabAdjascent());
		
		ArrayList<Case> particulier = new ArrayList<Case>();
		particulier.add(north);
		particulier.add(east);
		
		east.setEtat(0);
		north.setEtat(0);

		assertNotEquals(particulier, caseTestBotLeft.tabAdjascent());
		
		east.setEtat(5);
		north.setEtat(8);
		assertEquals(particulier, caseTestBotLeft.tabAdjascent());
		
		// tabAdjascent ne doit récupérer que les cases Hotels ou Chaines.
		// -1 étant une case grise donc injouable et 0 étant une case vide
		ArrayList<Case> testValeurDifferentes = new ArrayList<Case>();
		testValeurDifferentes.add(north);
		testValeurDifferentes.add(west);
		east.setEtat(-1);
		south.setEtat(0);
		assertEquals(testValeurDifferentes, caseTest.tabAdjascent());
		
		
		
		
	}
	
	@Test
	public void hotelAdjascent(){
		ArrayList<Case> test = new ArrayList<Case>();
		
		
		assertEquals(test, caseTest.hotelAdjascent());

		north.setEtat(1);
		south.setEtat(2);
		east.setEtat(0);
		west.setEtat(-1);
		// Seul north est un hotel donc hotel adjascent ne doit retourner qu'un chaine de taille 1 avec seulement la case à l'etat 1
		test.add(north);
		assertEquals(test, caseTest.hotelAdjascent());
		
		
	}
	
	@Test
	public void setNeighbours(){
		// cas simple
		caseTest.setNeighbours(north, south, east, west);
		assertEquals(north,caseTest.getNorth());
		assertEquals(south,caseTest.getSouth());
		assertEquals(east,caseTest.getEast());
		assertEquals(west,caseTest.getWest());
		
		// on passe une case particuliere 
		CaseBotRight caseTestBR = new CaseBotRight("test");
		caseTest.setNeighbours(null, south, caseTestBR, west);
		assertEquals(null,caseTest.getNorth());
		assertEquals(south,caseTest.getSouth());
		assertEquals(caseTestBR,caseTest.getEast());
		assertEquals(west,caseTest.getWest());
		
		
		
	}

	@Test
	public void testSameColorsArround(){
		// Test Case Particulier
		ArrayList<Case> testCaseParticulier = new ArrayList<Case>();
		testCaseParticulier.add(north);
		testCaseParticulier.add(south);
		// cas 1 seul avec un etat entre 2 et 8 --> False
		north.setEtat(5);
		assertFalse(caseTestBotLeft.sameColorsArround(testCaseParticulier, testCaseParticulier.size()));
		// cas avec 2 etats différents --> False
		south.setEtat(4);
		assertFalse(caseTestBotLeft.sameColorsArround(testCaseParticulier, testCaseParticulier.size()));
		// case les 2 etats dans l'interval 2 8 --> True
		south.setEtat(5);
		assertTrue(caseTestBotLeft.sameColorsArround(testCaseParticulier, testCaseParticulier.size()));
		
		//Test Case Normale
		ArrayList<Case> test = new ArrayList<Case>();
		//
		/*
		 *  TAILLE LIST = 2
		 */
		test.add(north);
		test.add(west);
		
		
		
		// cas 1 seul avec un etat entre 2 et 8 --> False
		north.setEtat(5);
		assertFalse(caseTest.sameColorsArround(test, test.size()));

		// cas les 2 etats dans l interval 2 8 --> True
		west.setEtat(5);
		assertTrue(caseTest.sameColorsArround(test, test.size()));

		/*
		 *  TAILLE LIST = 3
		 */
		test.add(south);
		north.setEtat(0);
		west.setEtat(0);
		south.setEtat(0);
		

		// cas 1 seul avec un etat entre 2 et 8 --> False
		north.setEtat(5);
		assertFalse(caseTest.sameColorsArround(test, test.size()));

		// cas 2 etats dans l interval 2 8 --> False
		west.setEtat(5);
		assertFalse(caseTest.sameColorsArround(test, test.size()));

		// cas les 3 etats dans l interval 2 8 --> True
		south.setEtat(5);
		assertTrue(caseTest.sameColorsArround(test, test.size()));

		/*
		 *  TAILLE LIST = 4
		 */
		test.add(east);
		north.setEtat(0);
		west.setEtat(0);
		south.setEtat(0);

		

		// cas 1 seul avec un etat entre 2 et 8 --> False
		north.setEtat(5);
		assertFalse(caseTest.sameColorsArround(test, test.size()));

		// cas 2 etats dans l interval 2 8 --> False
		west.setEtat(5);
		assertFalse(caseTest.sameColorsArround(test, test.size()));

		// cas les 3 etats dans l interval 2 8 --> False
		south.setEtat(5);
		assertFalse(caseTest.sameColorsArround(test, test.size()));

		// cas les 3 etats dans l interval 2 8 --> True
		east.setEtat(5);
		assertTrue(caseTest.sameColorsArround(test, test.size()));
	}

	@Test
	public void testEquals(){
		caseTest.setNom("test");
		assertTrue(caseTest.equals(new Case("test")));
		assertFalse(caseTest.equals(new Case("False")));
		assertFalse(caseTest.equals(null));
		// Essai sur les autres types de cases
		assertFalse(caseTestBot.equals(null));
		assertFalse(caseTestBotLeft.equals(null));
		assertFalse(caseTestBotRight.equals(null));
		assertFalse(caseTestLeft.equals(null));
		assertFalse(caseTestRight.equals(null));
		assertFalse(caseTestTop.equals(null));
		assertFalse(caseTestTopLeft.equals(null));
		assertFalse(caseTestTopRight.equals(null));
		
	}
}
