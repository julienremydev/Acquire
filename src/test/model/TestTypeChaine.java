package test.model;

import static org.junit.Assert.*;

import org.junit.Test;
import application.model.TypeChaine;

public class TestTypeChaine {

	@Test
	public void testPrixAction() {
		// cas categorie hotel = 1
		assertEquals(200, TypeChaine.prixAction(TypeChaine.SACKSON, 2));
		assertEquals(300, TypeChaine.prixAction(TypeChaine.SACKSON, 3));
		assertEquals(400, TypeChaine.prixAction(TypeChaine.SACKSON, 4));
		assertEquals(500, TypeChaine.prixAction(TypeChaine.SACKSON, 5));
		assertEquals(600, TypeChaine.prixAction(TypeChaine.SACKSON, 6));
		assertEquals(600, TypeChaine.prixAction(TypeChaine.SACKSON, 8));
		assertEquals(600, TypeChaine.prixAction(TypeChaine.SACKSON, 10));
		assertEquals(700, TypeChaine.prixAction(TypeChaine.SACKSON, 11));
		assertEquals(700, TypeChaine.prixAction(TypeChaine.SACKSON, 15));
		assertEquals(700, TypeChaine.prixAction(TypeChaine.SACKSON, 20));
		assertEquals(800, TypeChaine.prixAction(TypeChaine.SACKSON, 21));
		assertEquals(800, TypeChaine.prixAction(TypeChaine.SACKSON, 22));
		assertEquals(800, TypeChaine.prixAction(TypeChaine.SACKSON, 30));
		assertEquals(900, TypeChaine.prixAction(TypeChaine.SACKSON, 31));
		assertEquals(900, TypeChaine.prixAction(TypeChaine.SACKSON, 33));
		assertEquals(900, TypeChaine.prixAction(TypeChaine.SACKSON, 40));
		assertEquals(1000, TypeChaine.prixAction(TypeChaine.SACKSON, 41));
		assertEquals(1000, TypeChaine.prixAction(TypeChaine.SACKSON, 42));

		// cas categorie hotel = 2
		assertEquals(300, TypeChaine.prixAction(TypeChaine.HYDRA, 2));
		assertEquals(400, TypeChaine.prixAction(TypeChaine.HYDRA, 3));
		assertEquals(500, TypeChaine.prixAction(TypeChaine.HYDRA, 4));
		assertEquals(600, TypeChaine.prixAction(TypeChaine.HYDRA, 5));
		assertEquals(700, TypeChaine.prixAction(TypeChaine.HYDRA, 6));
		assertEquals(700, TypeChaine.prixAction(TypeChaine.HYDRA, 8));
		assertEquals(700, TypeChaine.prixAction(TypeChaine.HYDRA, 10));
		assertEquals(800, TypeChaine.prixAction(TypeChaine.HYDRA, 11));
		assertEquals(800, TypeChaine.prixAction(TypeChaine.HYDRA, 15));
		assertEquals(800, TypeChaine.prixAction(TypeChaine.HYDRA, 20));
		assertEquals(900, TypeChaine.prixAction(TypeChaine.HYDRA, 21));
		assertEquals(900, TypeChaine.prixAction(TypeChaine.HYDRA, 22));
		assertEquals(900, TypeChaine.prixAction(TypeChaine.HYDRA, 30));
		assertEquals(1000, TypeChaine.prixAction(TypeChaine.HYDRA, 31));
		assertEquals(1000, TypeChaine.prixAction(TypeChaine.HYDRA, 33));
		assertEquals(1000, TypeChaine.prixAction(TypeChaine.HYDRA, 40));
		assertEquals(1100, TypeChaine.prixAction(TypeChaine.HYDRA, 41));
		assertEquals(1100, TypeChaine.prixAction(TypeChaine.HYDRA, 42));

		// cas categorie hotel = 3
		assertEquals(400, TypeChaine.prixAction(TypeChaine.PHOENIX, 2));
		assertEquals(500, TypeChaine.prixAction(TypeChaine.PHOENIX, 3));
		assertEquals(600, TypeChaine.prixAction(TypeChaine.PHOENIX, 4));
		assertEquals(700, TypeChaine.prixAction(TypeChaine.PHOENIX, 5));
		assertEquals(800, TypeChaine.prixAction(TypeChaine.PHOENIX, 6));
		assertEquals(800, TypeChaine.prixAction(TypeChaine.PHOENIX, 8));
		assertEquals(800, TypeChaine.prixAction(TypeChaine.PHOENIX, 10));
		assertEquals(900, TypeChaine.prixAction(TypeChaine.PHOENIX, 11));
		assertEquals(900, TypeChaine.prixAction(TypeChaine.PHOENIX, 15));
		assertEquals(900, TypeChaine.prixAction(TypeChaine.PHOENIX, 20));
		assertEquals(1000, TypeChaine.prixAction(TypeChaine.PHOENIX, 21));
		assertEquals(1000, TypeChaine.prixAction(TypeChaine.PHOENIX, 22));
		assertEquals(1000, TypeChaine.prixAction(TypeChaine.PHOENIX, 30));
		assertEquals(1100, TypeChaine.prixAction(TypeChaine.PHOENIX, 31));
		assertEquals(1100, TypeChaine.prixAction(TypeChaine.PHOENIX, 33));
		assertEquals(1100, TypeChaine.prixAction(TypeChaine.PHOENIX, 40));
		assertEquals(1200, TypeChaine.prixAction(TypeChaine.PHOENIX, 41));
		assertEquals(1200, TypeChaine.prixAction(TypeChaine.PHOENIX, 42));
	}

	@Test
	public void testPrimeActionnairePrincipal(){
		// cas categorie hotel = 1
		assertEquals(2000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 2));
		assertEquals(3000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 3));
		assertEquals(4000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 4));
		assertEquals(5000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 5));
		assertEquals(6000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 6));
		assertEquals(6000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 8));
		assertEquals(6000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 10));
		assertEquals(7000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 11));
		assertEquals(7000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 15));
		assertEquals(7000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 20));
		assertEquals(8000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 21));
		assertEquals(8000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 22));
		assertEquals(8000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 30));
		assertEquals(9000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 31));
		assertEquals(9000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 33));
		assertEquals(9000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 40));
		assertEquals(10000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 41));
		assertEquals(10000, TypeChaine.primeActionnairePrincipal(TypeChaine.SACKSON, 42));

		// cas categorie hotel = 2
		assertEquals(3000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 2));
		assertEquals(4000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 3));
		assertEquals(5000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 4));
		assertEquals(6000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 5));
		assertEquals(7000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 6));
		assertEquals(7000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 8));
		assertEquals(7000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 10));
		assertEquals(8000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 11));
		assertEquals(8000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 15));
		assertEquals(8000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 20));
		assertEquals(9000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 21));
		assertEquals(9000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 22));
		assertEquals(9000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 30));
		assertEquals(10000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 31));
		assertEquals(10000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 33));
		assertEquals(10000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 40));
		assertEquals(11000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 41));
		assertEquals(11000, TypeChaine.primeActionnairePrincipal(TypeChaine.HYDRA, 42));

		// cas categorie hotel = 3
		assertEquals(4000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 2));
		assertEquals(5000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 3));
		assertEquals(6000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 4));
		assertEquals(7000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 5));
		assertEquals(8000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 6));
		assertEquals(8000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 8));
		assertEquals(8000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 10));
		assertEquals(9000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 11));
		assertEquals(9000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 15));
		assertEquals(9000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 20));
		assertEquals(10000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 21));
		assertEquals(10000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 22));
		assertEquals(10000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 30));
		assertEquals(11000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 31));
		assertEquals(11000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 33));
		assertEquals(11000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 40));
		assertEquals(12000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 41));
		assertEquals(12000, TypeChaine.primeActionnairePrincipal(TypeChaine.PHOENIX, 42));
	}
	
	@Test
	public void testPrimeActionnaireSecondaire(){
		// cas categorie hotel = 1
		assertEquals(1000, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 2));
		assertEquals(1500, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 3));
		assertEquals(2000, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 4));
		assertEquals(2500, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 5));
		assertEquals(3000, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 6));
		assertEquals(3000, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 8));
		assertEquals(3000, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 10));
		assertEquals(3500, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 11));
		assertEquals(3500, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 15));
		assertEquals(3500, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 20));
		assertEquals(4000, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 21));
		assertEquals(4000, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 22));
		assertEquals(4000, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 30));
		assertEquals(4500, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 31));
		assertEquals(4500, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 33));
		assertEquals(4500, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 40));
		assertEquals(5000, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 41));
		assertEquals(5000, TypeChaine.primeActionnaireSecondaire(TypeChaine.SACKSON, 42));

		// cas categorie hotel = 2
		assertEquals(1500, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 2));
		assertEquals(2000, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 3));
		assertEquals(2500, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 4));
		assertEquals(3000, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 5));
		assertEquals(3500, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 6));
		assertEquals(3500, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 8));
		assertEquals(3500, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 10));
		assertEquals(4000, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 11));
		assertEquals(4000, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 15));
		assertEquals(4000, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 20));
		assertEquals(4500, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 21));
		assertEquals(4500, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 22));
		assertEquals(4500, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 30));
		assertEquals(5000, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 31));
		assertEquals(5000, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 33));
		assertEquals(5000, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 40));
		assertEquals(5500, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 41));
		assertEquals(5500, TypeChaine.primeActionnaireSecondaire(TypeChaine.HYDRA, 42));

		// cas categorie hotel = 3
		assertEquals(2000, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 2));
		assertEquals(2500, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 3));
		assertEquals(3000, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 4));
		assertEquals(3500, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 5));
		assertEquals(4000, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 6));
		assertEquals(4000, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 8));
		assertEquals(4000, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 10));
		assertEquals(4500, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 11));
		assertEquals(4500, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 15));
		assertEquals(4500, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 20));
		assertEquals(5000, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 21));
		assertEquals(5000, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 22));
		assertEquals(5000, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 30));
		assertEquals(5500, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 31));
		assertEquals(5500, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 33));
		assertEquals(5500, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 40));
		assertEquals(6000, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 41));
		assertEquals(6000, TypeChaine.primeActionnaireSecondaire(TypeChaine.PHOENIX, 42));
	}
	
	@Test
	public void testGetTypeChaineInt(){
		assertEquals(TypeChaine.SACKSON, TypeChaine.getTypeChaine(2));
		assertEquals(TypeChaine.ZETA, TypeChaine.getTypeChaine(3));
		assertEquals(TypeChaine.HYDRA, TypeChaine.getTypeChaine(4));
		assertEquals(TypeChaine.FUSION, TypeChaine.getTypeChaine(5));
		assertEquals(TypeChaine.AMERICA, TypeChaine.getTypeChaine(6));
		assertEquals(TypeChaine.PHOENIX, TypeChaine.getTypeChaine(7));
		assertEquals(TypeChaine.QUANTUM, TypeChaine.getTypeChaine(8));
	}
	
	@Test
	public void testGetTypeChaineString(){
		assertEquals(TypeChaine.SACKSON, TypeChaine.getTypeChaine("SACKSON"));
		assertEquals(TypeChaine.ZETA, TypeChaine.getTypeChaine("ZETA"));
		assertEquals(TypeChaine.HYDRA, TypeChaine.getTypeChaine("HYDRA"));
		assertEquals(TypeChaine.FUSION, TypeChaine.getTypeChaine("FUSION"));
		assertEquals(TypeChaine.AMERICA, TypeChaine.getTypeChaine("AMERICA"));
		assertEquals(TypeChaine.PHOENIX, TypeChaine.getTypeChaine("PHOENIX"));
		assertEquals(TypeChaine.QUANTUM, TypeChaine.getTypeChaine("QUANTUM"));
	}
}
