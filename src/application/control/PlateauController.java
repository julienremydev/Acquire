package application.control;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import application.rmi.Client;
import application.rmi.ClientInterface;
import application.rmi.Game;
import application.rmi.ServeurInterface;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PlateauController{
	
	
	private Client client;
	

	@FXML
	private GridPane grid;
	
	public void setDisable(ActionEvent e) throws Exception{
		Button b = (Button) e.getSource();
		b.setTextFill(Color.ALICEBLUE);
		b.setDisable(true);
		String text = b.getText();
		client.getServeur().getCasePlayed(text);
	}
	

	public void setClient(Client c, ServeurInterface serveur) throws RemoteException {
		client=c;
		client.setServeur(serveur);
		client.setController(this);
		client.getServeur().distribution();
	}
	
	public void setGame(Game g) {
		//faire les modifications graphiques necessaires
		//game = g;
	}
	
	public void setDisable2(ActionEvent e) {
		ObservableList<Node> childrens = grid.getChildren();
		System.out.println(childrens.size());
		int i =0;
        for(Node node : childrens) {

        	System.out.println(i);
        	System.out.println("Row : "+grid.getRowIndex(node));
        	System.out.println("Col : "+grid.getColumnIndex(node));
        	System.out.println("");
            if(grid.getRowIndex(node) == 0 && grid.getColumnIndex(node) == 0) {
            	//game.getP
            }
            i++;
        	if (i==108) {
        		break;
        	}
        }
	}



	
}
