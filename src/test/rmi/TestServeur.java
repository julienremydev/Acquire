package test.rmi;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import application.globale.Globals;
import application.model.ClientInfo;
import application.rmi.Client;
import application.rmi.Serveur;

public class TestServeur {

	Serveur serveur;

	@Before
	public void itinializeServer() throws RemoteException{
		serveur = new Serveur();
	}

	@Test
	public void testRegisterClient() throws Exception{

		/*
		 * Scénario de Test avec tous les cas possibles d'une partie normale avant le lancement : 
		 * -Connexion du premier joueur (chef de la partie ) -> toto1 
		 * -Connexion d'un autre joueur avec le même pseudo -> erreur 
		 * -Connexion de 5 autres joueurs
		 * -Connexion d'un 7ème joueur -> erreur 
		 * -Déconnexion du chef de la partie (toto1) 
		 * -Connexion d'un 6ème joueur qui n'est pas le chef de la partie -> erreur 
		 * -Reconnexion de toto1
		 */
		assertEquals(serveur.erreurRegister("toto1", false), null);
		serveur.getListe_clients().put("toto1", new Client());
		serveur.getGame().setChef("toto1");
		assertEquals(serveur.erreurRegister("toto1", false), Globals.erreurPseudoUtilise);

		assertEquals(serveur.erreurRegister("toto2", false), null);
		serveur.getListe_clients().put("toto2", new Client());

		assertEquals(serveur.erreurRegister("toto3", false), null);
		serveur.getListe_clients().put("toto3", new Client());

		assertEquals(serveur.erreurRegister("toto4", false), null);
		serveur.getListe_clients().put("toto4", new Client());

		assertEquals(serveur.erreurRegister("toto5", false), null);
		serveur.getListe_clients().put("toto5", new Client());

		assertEquals(serveur.erreurRegister("toto6", false), null);
		serveur.getListe_clients().put("toto6", new Client());

		assertEquals(serveur.erreurRegister("toto7", false), Globals.erreurPartieComplete);

		serveur.getListe_clients().remove("toto1");

		assertEquals(serveur.erreurRegister("toto7", false), Globals.erreurPartieComplete2);

		assertEquals(serveur.erreurRegister("toto1", false), null);
		serveur.getListe_clients().put("toto1", new Client());


	}

	@Test
	public void testRegisterClientPartieCommencee() throws Exception{
		serveur = new Serveur();

		/*
		 * Scénario de Test avec lancement  : 
		 * -Connexion du premier joueur (chef de la partie ) -> toto1 
		 * -Connexion de toto2
		 * -Lancement
		 * -Connexion d'un 3ème joueur -> erreur 
		 * -Connexion d'un 3ème joueur avec un pseudo déjà utilisé -> erreur
		 * -Deconnexion de toto1
		 * -Deconnexion de toto2
		 * -Reconnexion de toto1
		 * -Reconnexion de toto2
		 */

		assertEquals(serveur.erreurRegister("toto1", false), null);
		serveur.getListe_clients().put("toto1", new Client());
		serveur.getGame().getTableau().ajouterClient(new ClientInfo("toto1"));
		serveur.getGame().setChef("toto1");

		assertEquals(serveur.erreurRegister("toto2", false), null);
		serveur.getListe_clients().put("toto2", new Client());
		serveur.getGame().getTableau().ajouterClient(new ClientInfo("toto2")); 

		serveur.getGame().setPartiecommencee(true);

		assertEquals(serveur.erreurRegister("toto3", false), Globals.erreurForbiddenPlayer);
		assertEquals(serveur.erreurRegister("toto1", false), Globals.erreurPseudoUtilise);

		serveur.getListe_clients().remove("toto1");
		serveur.getListe_clients().remove("toto2");

		assertEquals(serveur.erreurRegister("toto1", false), null);
		serveur.getListe_clients().put("toto1", new Client());

		assertEquals(serveur.erreurRegister("toto2", false), null);
		serveur.getListe_clients().put("toto2", new Client());
	}

	@Test
	public void testSetTurn() throws RemoteException{
		ArrayList<String> hmTest = new ArrayList<>();
		hmTest.add("Yodaii");
		hmTest.add("Neo");
		hmTest.add("Jakkos");

		HashMap<String, String> hmATrie = new HashMap<>();
		hmATrie.put("Jakkos", "I9");
		hmATrie.put("Yodaii", "A1");
		hmATrie.put("Neo", "C6");

		serveur.setTurn(hmATrie);
		assertEquals(hmTest, serveur.getGame().getOrdre_joueur());
	}

	@Test
	public void testPiocheCaseFinTour() throws RemoteException{
		ClientInfo c = new ClientInfo("Yodaii");
		c.getMain().add("A1");
		c.getMain().add("B1");
		c.getMain().add("C1");
		c.getMain().add("D1");
		c.getMain().add("E1");
		c.getMain().add("F1");

		serveur.getGame().getTableau().getInfoParClient().put("Yodaii", c);
		serveur.piocheCaseFinTour("A1", "Yodaii");
		assertFalse(c.getMain().get(0) == "A1");
		assertEquals(6, c.getMain().size());
	}
}
