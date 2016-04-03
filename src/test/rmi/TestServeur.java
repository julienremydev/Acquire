package test.rmi;

import static org.junit.Assert.*;

import org.junit.Test;

import application.globale.Globals;
import application.rmi.Client;
import application.rmi.Serveur;

public class TestServeur {

	Serveur serveur;
	
	
	
	@Test
	public void testRegisterClient() throws Exception {
		serveur = new Serveur();

		/*
		 * Scénario de Test avec tous les cas possibles : 
		 * -Connexion du premier joueur (chef de la partie ) -> toto1 
		 * -Connexion d'un autre joueur avec le même pseudo -> erreur 
		 * -Connexion de 5 autres joueurs
		 * -Connexion d'un 7ème joueur -> erreur 
		 * -Déconnexion du chef de la partie (toto1) 
		 * -Connexion d'un 6ème joueur qui n'est pas le chef de la partie -> erreur 
		 * -Reconnexion de toto1
		 */
		assertEquals(serveur.erreurRegister("toto1"), null);
		serveur.getListe_clients().put("toto1", new Client());
		serveur.setChef("toto1");
		assertEquals(serveur.erreurRegister("toto1"), Globals.erreurPseudoUtilise);

		assertEquals(serveur.erreurRegister("toto2"), null);
		serveur.getListe_clients().put("toto2", new Client());

		assertEquals(serveur.erreurRegister("toto3"), null);
		serveur.getListe_clients().put("toto3", new Client());

		assertEquals(serveur.erreurRegister("toto4"), null);
		serveur.getListe_clients().put("toto4", new Client());

		assertEquals(serveur.erreurRegister("toto5"), null);
		serveur.getListe_clients().put("toto5", new Client());

		assertEquals(serveur.erreurRegister("toto6"), null);
		serveur.getListe_clients().put("toto6", new Client());

		assertEquals(serveur.erreurRegister("toto7"), Globals.erreurPartieComplete);

		serveur.getListe_clients().remove("toto1");

		assertEquals(serveur.erreurRegister("toto7"), Globals.erreurPartieComplete2);

		assertEquals(serveur.erreurRegister("toto1"), null);
		serveur.getListe_clients().put("toto1", new Client());

	}

}
