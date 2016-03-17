package control;

import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import rmi.Client;

public class GameController {

	/*
	 * Déclaration de tous les composants du fichier fxml
	 */
	@FXML private TextField tf;
	
	public void setDisable(ActionEvent e) {
		Button b = (Button) e.getSource();
		b.setDisable(true);
	}
}
