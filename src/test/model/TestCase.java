package test.model;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import application.model.Case;

public class TestCase {
	Case caseTest;
	Case north;
	Case south;
	Case east;
	Case west;

	@Before
	public void initCase(){
		caseTest = new Case();
		north = new Case();
		south = new Case();
		east = new Case();
		west = new Case();
		caseTest.setNorth(north);
		caseTest.setSouth(south);
		caseTest.setEast(east);
		caseTest.setWest(west);
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

		// une case autour a un etat egal a 1 et les autres a 0
		north.setEtat(1);
		assertFalse(caseTest.surroundedByChains());

		// une case autour a un etat egal a -1 et les autres a 0
		north.setEtat(-1);
		assertFalse(caseTest.surroundedByChains());

		// une case autour a un etat egal a une valeur >= 2 et les autres a 0
		north.setEtat(5);
		assertTrue(caseTest.surroundedByChains());

		// toutes les cases autour ont un etat egal a une meme valeur >= 2
		south.setEtat(5);
		east.setEtat(5);
		west.setEtat(5);
		assertTrue(caseTest.surroundedByChains());

		// toutes les cases autour ont un etat egal a differente valeur >= 2
		south.setEtat(3);
		east.setEtat(7);
		west.setEtat(6);
		assertTrue(caseTest.surroundedByChains());
	}

	@Test
	public void testLookCase(){
		caseTest.lookCase();
		assertEquals(1, (int)caseTest.getEtat());
		north.setEtat(1);
		caseTest.lookCase();
		assertNotEquals(1, (int)caseTest.getEtat());
	}

	@Test
	public void testTabAdjacent(){
		ArrayList<Case> test = new ArrayList<Case>();
		assertEquals(test, caseTest.tabAdjascent(null, null, null, null));

		test.add(north);
		test.add(south);

		assertEquals(test, caseTest.tabAdjascent(north, south, null, null));
		assertNotEquals(test, caseTest.tabAdjascent(null, null, null, null));
		assertNotEquals(test, caseTest.tabAdjascent(north, null, null, null));
		assertNotEquals(test, caseTest.tabAdjascent(north, south, east, null));
		assertNotEquals(test, caseTest.tabAdjascent(north, south, east, west));

		test.add(east);
		test.add(west);

		assertEquals(test, caseTest.tabAdjascent(north, south, east, west));
	}

	@Test
	public void testSameColorsArround(){
		ArrayList<Case> test = new ArrayList<Case>();

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
	}
}
