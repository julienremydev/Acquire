package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import application.model.Case;
import application.model.Chaine;
import application.model.Plateau;
import application.model.TypeChaine;

public class TestPlateau {
	Plateau plateauTest;
	ArrayList<Chaine> listeChaine = new ArrayList<>();
	Chaine sackson = new Chaine (TypeChaine.SACKSON);
	Chaine zeta = new Chaine (TypeChaine.ZETA);
	Chaine hydra = new Chaine (TypeChaine.HYDRA);
	@Before
	public void initPlateau() {
		plateauTest = new Plateau();
		
		
		listeChaine.add(sackson);
		listeChaine.add(zeta);
		listeChaine.add(hydra);
	}

	// Vérification de la bonne initialisation des cases adjacentes dans les
	// Cases du plateau
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
	public void testGetCaseMilieu() {
		for (int i = 1; i < 12; i++) {
			for (int y = 1; y < 8; y++) {
				assertNotNull(plateauTest.getCase("E5").getSouth());
				assertNotNull(plateauTest.getCase("E5").getEast());
				assertNotNull(plateauTest.getCase("E5").getNorth());
				assertNotNull(plateauTest.getCase("E5").getWest());
			}
		}
	}

	//La methode verifie les cases après l'initialisation
	@Test
	public void testInitialisation() {
		// testCasesDisponible
		assertEquals(108, plateauTest.getCasesDisponible().size());
		assertTrue((plateauTest.getCasesDisponible().get(107).equals("I12")));
		assertTrue((plateauTest.getCasesDisponible().get(0).equals("A1")));

		// Test presence des cases
		assertTrue((plateauTest.getCase("A1").getNom().equals("A1")));
		assertTrue((plateauTest.getCase("F1").getNom().equals("F1")));
		assertTrue((plateauTest.getCase("I11").getNom().equals("I11")));
	}

	//La methode verifie les cases après l'initialisation
	@Test
	public void testInitCasesPlateau() {
		// Test presence des cases
		for (int i = 0; i < Plateau.ligne.length; i++) {
			for (int j = 0; j < Plateau.colonne.length; j++) {
				assertTrue((plateauTest.getCase(Plateau.ligne[i] + Plateau.colonne[j]).getNom()
						.equals(Plateau.ligne[i] + Plateau.colonne[j])));
			}
		}
		assertTrue((plateauTest.getCase("C1").getNom().equals("C1")));
		assertTrue((plateauTest.getCase("B1").getNom().equals("B1")));
		assertTrue((plateauTest.getCase("D11").getNom().equals("D11")));
	}


	//La methode test les cases adjacentes des cases present sur le plateau
	@Test
	public void testInitCasesAdjascentes() {

		// Test adj des cases du milieu
		for (int i = 1; i < Plateau.ligne.length - 1; i++) {
			for (int j = 1; j < Plateau.colonne.length - 1; j++) {
				assertTrue((plateauTest.getCase(Plateau.ligne[i] + Plateau.colonne[j]).getNorth().getNom()
						.equals(Plateau.ligne[i - 1] + Plateau.colonne[j])));
				assertTrue((plateauTest.getCase(Plateau.ligne[i] + Plateau.colonne[j]).getSouth().getNom()
						.equals(Plateau.ligne[i + 1] + Plateau.colonne[j])));
				assertTrue((plateauTest.getCase(Plateau.ligne[i] + Plateau.colonne[j]).getEast().getNom()
						.equals(Plateau.ligne[i] + Plateau.colonne[j + 1])));
				assertTrue((plateauTest.getCase(Plateau.ligne[i] + Plateau.colonne[j]).getWest().getNom()
						.equals(Plateau.ligne[i] + Plateau.colonne[j - 1])));
			}
		}

		// Test presence des cases

		assertTrue((plateauTest.getCase("A1").getNorth() == null));
		assertTrue((plateauTest.getCase("A1").getSouth().getNom().equals("B1")));
		assertTrue((plateauTest.getCase("A1").getEast().getNom().equals("A2")));
		assertTrue((plateauTest.getCase("A1").getWest() == null));

		assertTrue((plateauTest.getCase("A12").getNorth() == null));
		assertTrue((plateauTest.getCase("A12").getSouth().getNom().equals("B12")));
		assertTrue((plateauTest.getCase("A12").getEast() == null));
		assertTrue((plateauTest.getCase("A12").getWest().getNom().equals("A11")));

		assertTrue((plateauTest.getCase("I1").getNorth().getNom().equals("H1")));
		assertTrue((plateauTest.getCase("I1").getSouth() == null));
		assertTrue((plateauTest.getCase("I1").getEast().getNom().equals("I2")));
		assertTrue((plateauTest.getCase("I1").getWest() == null));

		assertTrue((plateauTest.getCase("I12").getNorth().getNom().equals("H12")));
		assertTrue((plateauTest.getCase("I12").getSouth() == null));
		assertTrue((plateauTest.getCase("I12").getEast() == null));
		assertTrue((plateauTest.getCase("I12").getWest().getNom().equals("I11")));

	}

	@Test
	public void testaddRecurseCoin() {
		Set<Case> retourne = new HashSet<Case>();
		
		plateauTest.getPlateauMap().get("A10").setEtat(1);
		plateauTest.getPlateauMap().get("A11").setEtat(1);
		plateauTest.getPlateauMap().get("B11").setEtat(1);
		plateauTest.getPlateauMap().get("B12").setEtat(1);
		plateauTest.getPlateauMap().get("C12").setEtat(1);
		
		plateauTest.addRecurse(retourne, plateauTest.getPlateauMap().get("A12"));

		assertTrue(retourne.contains(plateauTest.getPlateauMap().get("A10")));
		assertTrue(retourne.contains(plateauTest.getPlateauMap().get("A11")));
		assertTrue(retourne.contains(plateauTest.getPlateauMap().get("A12")));
		assertTrue(retourne.contains(plateauTest.getPlateauMap().get("B11")));
		assertTrue(retourne.contains(plateauTest.getPlateauMap().get("B12")));
		assertTrue(retourne.contains(plateauTest.getPlateauMap().get("C12")));
	}
	@Test
	public void testaddRecurseMultiple() {
		Set<Case> retourne = new HashSet<Case>();
		
		plateauTest.getPlateauMap().get("C8").setEtat(1);
		plateauTest.getPlateauMap().get("C9").setEtat(1);
		plateauTest.getPlateauMap().get("C11").setEtat(1);
		plateauTest.getPlateauMap().get("C12").setEtat(1);
		
		plateauTest.addRecurse(retourne, plateauTest.getPlateauMap().get("C10"));

		assertTrue(retourne.contains(plateauTest.getPlateauMap().get("C8")));
		assertTrue(retourne.contains(plateauTest.getPlateauMap().get("C9")));
		assertTrue(retourne.contains(plateauTest.getPlateauMap().get("C11")));
		assertTrue(retourne.contains(plateauTest.getPlateauMap().get("C12")));
		assertFalse(retourne.contains(plateauTest.getPlateauMap().get("A12")));
	}

	//La methode test si les cases noirs sont generer correctement
	@Test
	public void TestinitialiseMainCaseNoir() {
		int j = 0;
		int nbJoueur = 3;
		int casesDispDebut = plateauTest.getCasesDisponible().size();
		plateauTest.initialiseMainCaseNoir();
		int casesDispApres = plateauTest.getCasesDisponible().size();
		assertTrue(casesDispApres == casesDispDebut - nbJoueur);

		for ( String e : plateauTest.getPlateauMap().keySet()) {
			if (plateauTest.getPlateauMap().get(e).getEtat() == 1) {
				j++;
			}
		}
		assertTrue(j == nbJoueur);

	}

	//La methode test si 1 cases est generer correctement
	@Test
	public void TestAjouteMain1foisCaseNoir() {
		int j = 0;
		int casesDispDebut = plateauTest.getCasesDisponible().size();
		plateauTest.ajouteMain1foisCaseNoir();
		int casesDispApres = plateauTest.getCasesDisponible().size();
		assertTrue(casesDispApres == casesDispDebut - 1);


		for ( String e : plateauTest.getPlateauMap().keySet()) {
			if(plateauTest.getPlateauMap().get(e).getEtat()==1){
				j++;
			}
		}
		assertTrue(j == 1);

	}
	// Test dans le cas ou les chaines n'on pas de taille différentes
	@Test
	public void testlisteChaineDifferentesEgales(){
		// on jour sur la case 8, il faudra donc passer en parametre de la fonction les cases adjascentes non null
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E9"));
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E10"));
		
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("C8"));
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("D8"));
		
		
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E6"));
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E7"));
		
		ArrayList<Case> tabCaseAdj = new ArrayList<>();
		tabCaseAdj.add(plateauTest.getPlateauMap().get("E7"));
		tabCaseAdj.add(plateauTest.getPlateauMap().get("E9"));
		tabCaseAdj.add(plateauTest.getPlateauMap().get("D8"));
		
		ArrayList<Chaine> listeTest = plateauTest.listeChaineDifferentes(tabCaseAdj, listeChaine);
		int tailleTest = listeTest.size();
		
		assertEquals (1,tailleTest);		
	}
	// Test dans le cas ou les chaines n'on pas de taille différentes
	@Test
	public void testlisteChaineDifferentesNonEgales(){
		// on jour sur la case 8, il faudra donc passer en parametre de la fonction les cases adjascentes non null
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E9"));
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E10"));
		
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("C8"));
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("D8"));
		
		
		listeChaine.get(2).addCase(plateauTest.getPlateauMap().get("E6"));
		listeChaine.get(2).addCase(plateauTest.getPlateauMap().get("E7"));
		
		ArrayList<Case> tabCaseAdj = new ArrayList<>();
		tabCaseAdj.add(plateauTest.getPlateauMap().get("E7"));
		tabCaseAdj.add(plateauTest.getPlateauMap().get("E9"));
		tabCaseAdj.add(plateauTest.getPlateauMap().get("D8"));
		
		ArrayList<Chaine> listeTest = plateauTest.listeChaineDifferentes(tabCaseAdj, listeChaine);
		int tailleTest = listeTest.size();
		
		assertEquals (3,tailleTest);		
	}
	@Test
	public void testsameSizeChaine(){
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E9"));
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E10"));
		
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("B8"));
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("C8"));
		
		
		listeChaine.get(2).addCase(plateauTest.getPlateauMap().get("E6"));
		listeChaine.get(2).addCase(plateauTest.getPlateauMap().get("E7"));
		
		// aucune chaine n'est plus grande, la fonction retourne donc null
		assertNull(plateauTest.sameSizeChaine(listeChaine));
		
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("D8"));
		
		listeChaine.get(2).addCase(plateauTest.getPlateauMap().get("F6"));
		listeChaine.get(2).addCase(plateauTest.getPlateauMap().get("F7"));
		// La derniere chaine est plus grande que les autres, doit donc retourner cette chaine
		assertEquals(listeChaine.get(2),plateauTest.sameSizeChaine(listeChaine));
		
	}
	// test updateCase pour changement de couleur de chaines




	@Test
	public void testupdateCase(){
		// création de 3 Chaines avec des taille différentes
		// l'une d'elle aura une taille plus grande et les autres devrons donc fusionner avec celle-ci
		// Ajout de 2 cases à la chaine 1
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E9"));
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E10"));
		
		// Ajout de 3 cases à la chaine 2
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("B8"));
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("C8"));
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("D8"));
		// Ajout de 4 cases à la chaine 3
		listeChaine.get(2).addCase(plateauTest.getPlateauMap().get("E6"));
		listeChaine.get(2).addCase(plateauTest.getPlateauMap().get("E7"));
		listeChaine.get(2).addCase(plateauTest.getPlateauMap().get("F6"));
		listeChaine.get(2).addCase(plateauTest.getPlateauMap().get("F7"));
		
		// on applique la fonction update case
		// on doit donc se retrouver avec toutes les cases dans la chaine la plus grande
		plateauTest.updateCase("E8", listeChaine);
		int variableTest1 = listeChaine.get(2).tailleChaine();
		int variableTest2 = listeChaine.get(1).tailleChaine();
		int variableTest3 = listeChaine.get(0).tailleChaine();
		assertEquals(10,variableTest1);
		assertEquals(0,variableTest2);
		assertEquals(0,variableTest3);
	}
	
	
	
}