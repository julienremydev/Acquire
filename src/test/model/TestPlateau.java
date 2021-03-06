package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import application.model.Case;
import application.model.Chaine;
import application.model.ClientInfo;
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

	// V�rification de la bonne initialisation des cases adjacentes dans les
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

	//La methode verifie les cases apr�s l'initialisation
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

	//La methode verifie les cases apr�s l'initialisation
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

	//La methode teste si les cases noires sont gener�es correctement
	@Test
	public void TestinitialiseMainCaseNoir() {
		int j = 0;
		int nbJoueur = 3;
		int casesDispDebut = plateauTest.getCasesDisponible().size();
		plateauTest.initialiseMainCaseNoir();
		plateauTest.initialiseMainCaseNoir();
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
	// Test dans le cas ou les chaines n'ont pas de taille diff�rentes
	@Test
	public void testlisteChaineDifferentesEgales(){
		// on joue sur la case 8, il faudra donc passer en parametre de la fonction les cases adjascentes non null
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
	// Test dans le cas ou les chaines n'ont pas de taille diff�rentes
	@Test
	public void testlisteChaineDifferentesNonEgales(){
		// on joue sur la case 8, il faudra donc passer en parametre de la fonction les cases adjascentes non null
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
		Chaine chTest = plateauTest.sameSizeChaine(listeChaine).get(0);
		assertEquals(listeChaine.get(0),chTest);

	}
	@Test
	public void testupdateCase(){
		// cr�ation de 3 Chaines avec des taille diff�rentes
		// l'une d'elle aura une taille plus grande et les autres devrons donc fusionner avec celle-ci
		// Ajout de 2 cases � la chaine 1
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E9"));
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E10"));

		// Ajout de 3 cases � la chaine 2
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("B8"));
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("C8"));
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("D8"));
		// Ajout de 4 cases � la chaine 3
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
	@Test
	public void testupdateCaseChaineSameSize(){
		// cr�ation de 3 Chaines avec des taille diff�rentes
		// l'une d'elle aura une taille plus grande et les autres devrons donc fusionner avec celle-ci
		// Ajout de 2 cases � la chaine 1
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E9"));
		listeChaine.get(0).addCase(plateauTest.getPlateauMap().get("E10"));

		// Ajout de 3 cases � la chaine 2
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("C8"));
		listeChaine.get(1).addCase(plateauTest.getPlateauMap().get("D8"));



		// on applique la fonction update case
		// on doit donc se retrouver avec toutes les cases dans la chaine la plus grande

		plateauTest.updateCase("E8", listeChaine);

	}

	@Test
	public void testCasesGrises() {
		ArrayList<String> main = new ArrayList<>();
		Case c1 = plateauTest.getCase("C1");
		Case c3 = plateauTest.getCase("C3");
		listeChaine.get(0).addCase(c1);
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		//liste2
		listeChaine.get(1).addCase(c3);
		listeChaine.get(1).addCase(new Case("south"));
		listeChaine.get(1).addCase(new Case("south"));
		listeChaine.get(1).addCase(new Case("south"));
		listeChaine.get(1).addCase(new Case("south"));
		listeChaine.get(1).addCase(new Case("south"));
		listeChaine.get(1).addCase(new Case("south"));
		listeChaine.get(1).addCase(new Case("south"));
		listeChaine.get(1).addCase(new Case("south"));
		listeChaine.get(1).addCase(new Case("south"));
		listeChaine.get(1).addCase(new Case("south"));
		listeChaine.get(1).addCase(new Case("south"));
		listeChaine.get(1).addCase(new Case("south"));
		
		main.add("C2");
		
		ArrayList<String> returnCasesGrises = plateauTest.CasesGrises(listeChaine,main);
		int varTest = plateauTest.getCase("C2").getEtat();
		assertEquals(-1,varTest);
		assertEquals(returnCasesGrises.get(0),plateauTest.getCase("C2").getNom());
	}
	// full chiane /  fusion chaine
	@Test
	public void testSetGrise(){
		for(int i = 0 ; i< 9 ; i++)
			listeChaine.get(0).addCase(new Case("north"));
		
		for(int i = 0 ; i< 13 ; i++)
			listeChaine.get(1).addCase(new Case("south"));
		
		for(int i = 0 ; i< 9 ; i++)
			listeChaine.get(2).addCase(new Case("south"));
		
		// une seule chaine est sup�rieure
		assertFalse(plateauTest.setGrise(listeChaine));
		
		listeChaine.get(2).addCase(new Case("south"));
		listeChaine.get(2).addCase(new Case("south"));
		listeChaine.get(2).addCase(new Case("south"));
		listeChaine.get(2).addCase(new Case("south"));
		listeChaine.get(2).addCase(new Case("south"));
		
		// deux chiane sup�rieures a 11 
		
		assertTrue(plateauTest.setGrise(listeChaine));
		
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		listeChaine.get(0).addCase(new Case("north"));
		
		// les trois chaines sont sup�rieures  � 11
		assertTrue(plateauTest.setGrise(listeChaine));
	}
	
	@Test
	public void testPlateauRegeneration(){
		Plateau p=new Plateau();
		plateauTest=Plateau.plateauRegeneration(p);
		assertTrue(plateauTest.getCase("A1").getEast().getNom().equals("A2"));
		assertTrue(plateauTest.getCase("A1").getNorth()==null);
	}
	
	@Test
	public void testFullChaine(){
				
	ArrayList<Chaine>  listChaines=new ArrayList<Chaine>();
	Collection<ClientInfo> listeClient=new ArrayList<ClientInfo>();
			
	ArrayList<Case> listCase=new ArrayList<Case>();
	
	plateauTest.getCase("E5").setEtat(1);
	
	listCase.add(plateauTest.getCase("A1"));
	listCase.add(plateauTest.getCase("B1"));
	listCase.add(plateauTest.getCase("C1"));
	listCase.add(plateauTest.getCase("D1"));
	
	
	ArrayList<Case> listCase2=new ArrayList<Case>();

	listCase2.add(plateauTest.getCase("A3"));
	listCase2.add(plateauTest.getCase("B3"));
	listCase2.add(plateauTest.getCase("C3"));
	listCase2.add(plateauTest.getCase("D3"));
	
	ArrayList<Case> listCase3=new ArrayList<Case>();
	listCase3.add(plateauTest.getCase("A5"));
	listCase3.add(plateauTest.getCase("B5"));
	listCase3.add(plateauTest.getCase("C5"));
	listCase3.add(plateauTest.getCase("D5"));

	ArrayList<Case> listCase4=new ArrayList<Case>();
	listCase4.add(plateauTest.getCase("A7"));
	listCase4.add(plateauTest.getCase("B7"));
	listCase4.add(plateauTest.getCase("C7"));
	listCase4.add(plateauTest.getCase("D7"));

	ArrayList<Case> listCase5=new ArrayList<Case>();
	listCase5.add(plateauTest.getCase("A9"));
	listCase5.add(plateauTest.getCase("B9"));
	listCase5.add(plateauTest.getCase("C9"));
	listCase5.add(plateauTest.getCase("D9"));

	ArrayList<Case> listCase6=new ArrayList<Case>();
	listCase6.add(plateauTest.getCase("A11"));
	listCase6.add(plateauTest.getCase("B11"));
	listCase6.add(plateauTest.getCase("C11"));
	listCase6.add(plateauTest.getCase("D11"));


	ArrayList<Case> listCase7=new ArrayList<Case>();
	listCase7.add(plateauTest.getCase("A11"));
	listCase7.add(plateauTest.getCase("B11"));
	listCase7.add(plateauTest.getCase("C11"));
	listCase7.add(plateauTest.getCase("D11"));
	
	
	
listChaines.add(new Chaine(TypeChaine.AMERICA));
	listChaines.get(0).setListeCase(listCase);
listChaines.add(new Chaine(TypeChaine.FUSION));
	listChaines.get(1).setListeCase(listCase2);
	listChaines.add(new Chaine(TypeChaine.HYDRA));
	listChaines.get(2).setListeCase(listCase3);
listChaines.add(new Chaine(TypeChaine.PHOENIX));
	listChaines.get(3).setListeCase(listCase4);
listChaines.add(new Chaine(TypeChaine.QUANTUM));
	listChaines.get(4).setListeCase(listCase5);
listChaines.add(new Chaine(TypeChaine.SACKSON));
	listChaines.get(5).setListeCase(listCase6);
listChaines.add(new Chaine(TypeChaine.ZETA));
	listChaines.get(6).setListeCase(listCase7);

	ClientInfo ci=new ClientInfo();
	ArrayList<String> main=new ArrayList<String>();
	main.add("E4");
	ci.setMain(main);
	listeClient.add(ci);
	plateauTest.fullChaine(listChaines, listeClient);
	assertTrue(plateauTest.getCase("E4").getEtat()==-2);
	
	}



}