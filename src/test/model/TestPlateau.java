package test.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import application.model.Plateau;

public class TestPlateau {
	Plateau plateauTest;
	
	@Before
	public void initPlateau(){
		plateauTest = new Plateau();
	}
	// Vérification de la bonne initialisation des cases adjacentes dans les Cases du plateau
	@Test
	public void testGetTopLeft() {
		
		assertNull(plateauTest.getCase("A1").getNorth());
		assertNull(plateauTest.getCase("A1").getWest());
	}
	@Test
	public void testGetTopRight() {
		
		assertNull(plateauTest.getCase("A12").getNorth());
		assertNull(plateauTest.getCase("A12").getEast());
	}
	@Test
	public void testGetBotLeft() {
		
		assertNull(plateauTest.getCase("I1").getSouth());
		assertNull(plateauTest.getCase("I1").getWest());
	}
	@Test
	public void testGetBotRight() {
		
		assertNull(plateauTest.getCase("I12").getSouth());
		assertNull(plateauTest.getCase("I12").getEast());
	}
	@Test
	public void testGetCaseMilieu(){
		for (int i = 1 ; i<12;i++)
		{
			for(int y = 1; y<8;y++)
			{
				assertNotNull(plateauTest.getCase("E5").getSouth());
				assertNotNull(plateauTest.getCase("E5").getEast());
				assertNotNull(plateauTest.getCase("E5").getNorth());
				assertNotNull(plateauTest.getCase("E5").getWest());
			}
		}
	}
	

}
