package test.rmi;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import application.model.Case;
import application.model.Chaine;
import application.model.ClientInfo;
import application.model.TypeChaine;
import application.rmi.Game;

public class TestGame {
	
	Game game;
	
	ArrayList<Chaine> listeChaine = new ArrayList<Chaine>();
	Chaine sackson = new Chaine (TypeChaine.SACKSON);
	Chaine zeta = new Chaine (TypeChaine.ZETA);
	Chaine hydra = new Chaine (TypeChaine.HYDRA);
	@Before
	public void initPlateau() {
		game = new Game();	
		listeChaine.add(sackson);
		listeChaine.add(zeta);
		listeChaine.add(hydra);
	}
	/**
	 * la methode verifie si une chaine est crée correctement ou pas 
	 */
	@Test
	public void TestcreationChaine() {
		Set<Case> list = new HashSet<Case>();
		list.add(game.getPlateau().getPlateauMap().get("A10"));
		list.add(game.getPlateau().getPlateauMap().get("A11"));
		list.add(game.getPlateau().getPlateauMap().get("A12"));
		list.add(game.getPlateau().getPlateauMap().get("B12"));
		list.add(game.getPlateau().getPlateauMap().get("B11"));
		list.add(game.getPlateau().getPlateauMap().get("C12"));
		
		game.getTableauDeBord().ajouterClient(new ClientInfo("Yodaii"));
		game.creationChaine(list, TypeChaine.AMERICA, "Yodaii");

		assertTrue(game.getPlateau().getPlateauMap().get("A10").getEtat() ==  TypeChaine.AMERICA.getNumero());
		assertTrue(game.getPlateau().getPlateauMap().get("A11").getEtat() == TypeChaine.AMERICA.getNumero());
		assertTrue(game.getPlateau().getPlateauMap().get("A12").getEtat() == TypeChaine.AMERICA.getNumero());
		assertTrue(game.getPlateau().getPlateauMap().get("B11").getEtat() == TypeChaine.AMERICA.getNumero());
		assertTrue(game.getPlateau().getPlateauMap().get("B12").getEtat() == TypeChaine.AMERICA.getNumero());
		assertTrue(game.getPlateau().getPlateauMap().get("C12").getEtat() == TypeChaine.AMERICA.getNumero());
		int nbAction = game.getTableauDeBord().getInfoParClient().get("Yodaii").getActionParChaine().get(TypeChaine.AMERICA);
		assertEquals(1, nbAction);
	}
	
	@Test
	public void isOver() {
		assertFalse(game.isOver());
		for (int i = 0; i<= 50; i++) {
			game.getTableauDeBord().getListeChaine().get(0).addCase(new Case());
		}
		assertTrue(game.isOver());
		game.getTableauDeBord().getListeChaine().get(0).getListeCase().clear();
		assertFalse(game.isOver());
		for (int j = 0; j<= 6; j++) {
			for (int i = 0; i<= 12; i++) {
				game.getTableauDeBord().getListeChaine().get(j).addCase(new Case());
			}
		}
		assertTrue(game.isOver());
	}
}
