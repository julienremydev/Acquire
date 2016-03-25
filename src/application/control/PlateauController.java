package application.control;

import java.rmi.RemoteException;
import java.util.ArrayList;

import application.model.Case;
import application.rmi.Client;
import application.rmi.Game;
import application.rmi.ServeurInterface;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
		b.setDisable(true);
		String text = b.getText();
		client.sendCase(text);
	}
	

	public void setClient(Client c, ServeurInterface serveur) throws RemoteException {
		client=c;
		client.setServeur(serveur);
		client.setController(this);
		
		//Plutot attendre le lancement du jeu non ?
		//client.getServeur().distribution();
		
	}
	public void setCases(ArrayList<Case> listCase) {
		ObservableList<Node> childrens = grid.getChildren();
		int i =0;
        for(Node node : childrens) {
        	if (node instanceof Button) {
        		Case c = new Case(((Button) node).getText());
        		if (listCase.contains(c)) {
        			((Button) node).setText("played");
        		}
        	}
        	i++;
        	if (i==108) {
        		break;
        	}
        }
	}
	
	public void setGame(Game g) {
		//recuperer la main du joueur
		ObservableList<Node> childrens = grid.getChildren();
		int i=0;
        for(Node node : childrens) {
        	if (node instanceof Button) {
        		Button b = (Button) node;
        		Case c = g.getPlateau().getCase(b.getText());
        		if (c.getEtat()==1) {
        			Platform.runLater(new Runnable() {
        				public void run() {
        					b.setTextFill(Color.RED);
        				}
        			});
        		}
        	}
        	if (i==108) {
        		break;
        	}
        }
	}
	
	public void getMain() {
	}
}
