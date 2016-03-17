package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PlateauController {
	
	@FXML
	private GridPane grid;
	
	public void setDisable(ActionEvent e) {
		Button b = (Button) e.getSource();
		b.setDisable(true);
	}
}
