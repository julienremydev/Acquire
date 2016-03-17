package control;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import rmi.Game;
import rmi.ServeurInterface;

public class PlateauController {
	
	private ServeurInterface serveur;
	
	private Game game;
	
	public PlateauController() {
	}

	@FXML
	private GridPane grid;
	
	public void setDisable(ActionEvent e) throws Exception{
		Button b = (Button) e.getSource();
		b.setDisable(true);
		String text = b.getText();
		serveur.getCasePlayed(text);
	}
	
	public void setServeur (ServeurInterface serveur) throws Exception{
		this.serveur=serveur;
		setGame();
	}
	
	public void setGame() throws Exception{
		game = serveur.getCurrentGame();
	}
	
	public void setDisable2(ActionEvent e) {
		ObservableList<Node> childrens = grid.getChildren();
        for(Node node : childrens) {
        	System.out.println("Row : "+grid.getRowIndex(node));
        	System.out.println("Col : "+grid.getColumnIndex(node));
        	System.out.println("");
            if(grid.getRowIndex(node) == 0 && grid.getColumnIndex(node) == 0) {
            }
        }
	}
	
}
