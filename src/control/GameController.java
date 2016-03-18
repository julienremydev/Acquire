package control;

import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import rmi.Client;
import rmi.ServeurInterface;

public abstract class GameController {

	public void setDisable(ActionEvent e) {
		Button b = (Button) e.getSource();
		b.setDisable(true);
		
	}
	
	
}
