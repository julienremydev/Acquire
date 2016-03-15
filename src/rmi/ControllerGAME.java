package rmi;

import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerGAME {

	/*
	 * Déclaration de tous les composants du fichier fxml
	 */
	@FXML private TextField tf;
	
	
	//Contrôleur associé à un client
	private Client client;
    

    

	public void setClient(Client client) {
		this.client = client;
	}
}
