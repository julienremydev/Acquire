package application.control;

import java.rmi.RemoteException;

import application.rmi.Client;
import application.rmi.ServeurInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public abstract class GameController {

	public void setDisable(ActionEvent e) {
		Button b = (Button) e.getSource();
		b.setDisable(true);
		
	}
	
	
}
