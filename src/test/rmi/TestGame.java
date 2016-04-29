package test.rmi;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import application.model.Case;
import application.model.Chaine;
import application.model.Plateau;
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
		ArrayList<Case> list = new ArrayList<Case>();
		list.add(game.getPlateau().getPlateauMap().get("A10"));
		list.add(game.getPlateau().getPlateauMap().get("A11"));
		list.add(game.getPlateau().getPlateauMap().get("A12"));
		list.add(game.getPlateau().getPlateauMap().get("B12"));
		list.add(game.getPlateau().getPlateauMap().get("B11"));
		list.add(game.getPlateau().getPlateauMap().get("C12"));

		game.creationChaine(list, TypeChaine.AMERICA);

		assertTrue(game.getPlateau().getPlateauMap().get("A10").getEtat() ==  TypeChaine.AMERICA.getNumero());
		assertTrue(game.getPlateau().getPlateauMap().get("A11").getEtat() == TypeChaine.AMERICA.getNumero());
		assertTrue(game.getPlateau().getPlateauMap().get("A12").getEtat() == TypeChaine.AMERICA.getNumero());
		assertTrue(game.getPlateau().getPlateauMap().get("B11").getEtat() == TypeChaine.AMERICA.getNumero());
		assertTrue(game.getPlateau().getPlateauMap().get("B12").getEtat() == TypeChaine.AMERICA.getNumero());
		assertTrue(game.getPlateau().getPlateauMap().get("C12").getEtat() == TypeChaine.AMERICA.getNumero());
	}
}
