package test.control;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.control.ConnexionController;
import javafx.event.ActionEvent;
public class TestConnexionController {

	ConnexionController controller ;
	@Before
	public void initController(){
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
	public void testConnexion() throws Exception{
		
		//ActionEvent e = new ActionEvent();
		//controller.seConnecter(e);
	}
}

