package application.control;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import application.model.Case;
import application.model.Chaine;
import application.model.ClientInfo;
import application.rmi.Client;
import application.rmi.Game;
import application.rmi.ServeurInterface;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PlateauController implements Initializable{

	private Client client;

	@FXML
	private GridPane grid;
	@FXML
	private TextArea tchat;
	@FXML
	private TextField input;
	@FXML
	private Button letsplay;
	@FXML
	private TableView<ClientInfo> tableauDeBord;

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

				Platform.runLater(new Runnable() {
					public void run() {
						if (c.getEtat()==1) {
							b.setTextFill(Color.RED);
						}
						if (c.getEtat()==2){
							b.setTextFill(Color.YELLOW);

						}
					}
				}
						);
			}
			if (i==108) {
				break;
			}
		}
	}

	public void getMain() {
	}


	public void envoyerTchat() throws RemoteException{
		if (input.getText().trim().length() > 0)
			client.getServeur().distributionTchat(client.getPseudo(), input.getText().trim());
	}
	public void setChat(String s) {
		Platform.runLater(() -> tchat.setText(s));
		Platform.runLater(() -> tchat.setScrollTop(tchat.getHeight()));
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tchat.setEditable(false);
		letsplay.setDisable(true);
	}


	public void setBEnable(boolean b) {
		letsplay.setDisable(!b);
	}
	public void lancement() throws RemoteException{
		client.getServeur().setLancement();
		
		try {
			final ObservableList<ClientInfo> data = FXCollections.observableArrayList();
			
			for(ClientInfo ci : client.getServeur().getGame().getTableau().getInfoParClient()){
				data.add(ci);
			}
			
			tableauDeBord.setItems(data);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}


	public static void nouvelleChaine(Chaine nouvelleChaine) {
		// TODO Auto-generated method stub

	}
}
