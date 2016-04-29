package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import application.model.Case;
import application.model.Plateau;
import application.model.TypeChaine;

public class TestPlateau {
	Plateau plateauTest;

	@Before
	public void initPlateau() {
		plateauTest = new Plateau();
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

	//La methode test quand on clique sur un bouton il créer une chaine
	@Test
	public void testaddRecurse() {
		ArrayList<Case> retourne = new ArrayList<Case>();
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

		// for(int i=0;i<retourne.size();i++){
		// System.out.println(retourne.get(i).toString());
		// }
	}

	/**
	 * Ajoute 6 cases cliquable pour le joueur
	 */

	
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

	
	//la methode verifie si une chaine est crée correctement ou pas 
	@Test
	public void TestcreationChaine() {
		ArrayList<Case> list = new ArrayList<Case>();
		list.add(plateauTest.getPlateauMap().get("A10"));
		list.add(plateauTest.getPlateauMap().get("A11"));
		list.add(plateauTest.getPlateauMap().get("A12"));
		list.add(plateauTest.getPlateauMap().get("B12"));
		list.add(plateauTest.getPlateauMap().get("B11"));
		list.add(plateauTest.getPlateauMap().get("C12"));

		plateauTest.creationChaine(list, TypeChaine.AMERICA);

		assertTrue(plateauTest.getPlateauMap().get("A10").getEtat() ==  TypeChaine.AMERICA.getNumero());
		assertTrue(plateauTest.getPlateauMap().get("A11").getEtat() == TypeChaine.AMERICA.getNumero());
		assertTrue(plateauTest.getPlateauMap().get("A12").getEtat() == TypeChaine.AMERICA.getNumero());
		assertTrue(plateauTest.getPlateauMap().get("B11").getEtat() == TypeChaine.AMERICA.getNumero());
		assertTrue(plateauTest.getPlateauMap().get("B12").getEtat() == TypeChaine.AMERICA.getNumero());
		assertTrue(plateauTest.getPlateauMap().get("C12").getEtat() == TypeChaine.AMERICA.getNumero());

	}
	@Test
	public void testlisteChaineDiffentes(){
		
	}
}