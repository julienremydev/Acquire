package control;

import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import rmi.Client;

public class GameController {

	/*
	 * D�claration de tous les composants du fichier fxml
	 */
	@FXML private TextField tf;
	
	
	//Contr�leur associ� � un client
	private Client client;
    

    

	public void setClient(Client client) {
		this.client = client;
	}
}
