package control;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class PlateauController {
	public void setDisable(ActionEvent e) {
		Button b = (Button) e.getSource();
		b.setDisable(true);
	}
}
