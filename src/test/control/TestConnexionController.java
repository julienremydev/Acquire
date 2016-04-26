package test.control;

import static org.junit.Assert.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Test;

import application.control.ConnexionController;
import application.globale.Globals;
import application.rmi.Serveur;
import application.rmi.ServeurInterface;




public class TestConnexionController {

	ConnexionController controller;
	ServeurInterface serveurInterface;

	@BeforeClass
	public static void creationServeur() throws RemoteException, MalformedURLException, UnknownHostException {
		Serveur.main(new String[0]);
	}

	@Before
	public void initController() {
		controller = new ConnexionController();
	}

	@Test
	public void testIPcorrecte() {
		/*
		 * Tests fonction ipCorrecte
		 */
		assertTrue(controller.ipCorrecte("  24  "));
		assertTrue(controller.ipCorrecte("0"));
		assertTrue(controller.ipCorrecte("255"));
		assertFalse(controller.ipCorrecte("hello"));
		assertFalse(controller.ipCorrecte("24.5"));
		assertFalse(controller.ipCorrecte("1111111111111111111111"));
		assertFalse(controller.ipCorrecte("256"));
		assertFalse(controller.ipCorrecte("-1"));
	}

	@Test
	public void testConnexionServeur() throws Exception {
		serveurInterface = controller.connexion("127", "0", "0", "1");
	}

	@Test(expected = ConnectException.class)
	public void testConnexionMauvaiseIP1() throws Exception {
		controller.connexion("128", "0", "0", "1");
	}

	@Test(expected = ConnectIOException.class)
	public void testConnexionMauvaiseIP2() throws Exception {
		controller.connexion("0", "0", "0", "1");
	}

	@Test
	public void testPseudo_IP() {
		assertEquals(controller.verification_Pseudo_IP("toto", "127", "0", "0", "1"), null);
		assertEquals(controller.verification_Pseudo_IP("tot", "127", "0", "0", "1"), null);
		assertEquals(controller.verification_Pseudo_IP("to", "127", "0", "0", "1"), Globals.erreurTaillePseudo);
		assertEquals(controller.verification_Pseudo_IP("totototototot", "127", "0", "0", "1"),
				Globals.erreurTaillePseudo);
		assertEquals(controller.verification_Pseudo_IP("111111111111111111111111", "127", "0", "0", "1"),
				Globals.erreurTaillePseudo);
		assertEquals(controller.verification_Pseudo_IP("Serveur", "127", "0", "0", "1"), Globals.erreurPseudoReserve);
		assertEquals(controller.verification_Pseudo_IP("toto", "", "0", "0", "1"), Globals.erreurAdresseIP);
	}
	@Test
	public void testVerificationJSON() {
		File file = null;
		assertEquals(controller.verification_JSON(true, file), Globals.erreurChooseFileJSON);
		
		file = new File("");
		assertEquals(controller.verification_JSON(true, file), Globals.erreurFileNotFoundJSON);
		
	}
	
}
