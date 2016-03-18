package test.model;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Case;

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
}
