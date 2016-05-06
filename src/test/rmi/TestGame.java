package test.rmi;


import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import application.model.Case;
import application.model.ClientInfo;
import application.model.TypeChaine;
import application.rmi.Game;

public class TestGame {
	
	Game game;
	
	@Before
	public void initPlateau() {
		game = new Game();	
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
	
	
	
	/*
	 * Tests sur  les méthodes whoseTurn et whoseBeForeTurn
	 */
	@Test
	public void whoseTurn() throws RemoteException {
		ArrayList<String> arr = new ArrayList<String> ();
		arr.add("toto");
		arr.add("tata");
		arr.add("titi");
		game.setOrdre_joueur(arr);
		assertEquals(game.whoseTurn("toto"), "tata");
		assertEquals(game.whoseTurn("tata"), "titi");
		assertEquals(game.whoseTurn("titi"), "toto");
		
		assertEquals(game.whoseBeforeTurn("toto"), "titi");
		assertEquals(game.whoseBeforeTurn("tata"), "toto");
		assertEquals(game.whoseBeforeTurn("titi"), "tata");
	}
	/*
	 * Tests sur  la méthode calculArgentImmobiliseAction
	 * Retourne le total du prix des actions choisies par le joueur
	 */
	@Test
	public void calculArgentImmobiliseAction() throws RemoteException {
		HashMap<TypeChaine, Integer> liste_actions = new HashMap<TypeChaine, Integer> ();
		liste_actions.put(TypeChaine.SACKSON, 3);
		assertEquals(600, game.calculArgentImmobiliseAction(liste_actions));
		game.getListeChaine().get(0).addCase(new Case("A1"));
		game.getListeChaine().get(0).addCase(new Case("A2"));
		game.getListeChaine().get(0).addCase(new Case("A3"));
		assertEquals(900, game.calculArgentImmobiliseAction(liste_actions));
	}
	
	/*
	 * Tests sur  la méthode totalesActionsJoueurs
	 */
	@Test
	public void totalesActionsJoueurs() throws RemoteException {
		HashMap<TypeChaine, Integer> liste_actions = new HashMap<TypeChaine, Integer> ();
		liste_actions.put(TypeChaine.SACKSON, 3);
		assertEquals(3, game.totalesActionsJoueurs(liste_actions));
		liste_actions = new HashMap<TypeChaine, Integer> ();
		liste_actions.put(TypeChaine.SACKSON, 0);
		assertEquals(0, game.totalesActionsJoueurs(liste_actions));
	}
	
}
