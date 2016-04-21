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

public class PlateauController implements Initializable {

	/**
	 * Client correspondant au plateau
	 */
	private Client client;

	/**
	 * element plateau
	 */
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

	/**
	 * Methode lance au moment du clic sur une case
	 * @param e :  correpond au bouton clique
	 * @throws Exception
	 */
	public void setDisable(ActionEvent e) throws Exception {
		//on recupere la source pour avoir le bouton
		Button b = (Button) e.getSource();
		//on recupere le nom de la case, exemple "A5"
		String text = b.getText();
		//on envoie via le rmi la case clique
		client.sendCase(text);
	}

	/**
	 * Methode permettant d'effectuer la liaison rmi
	 * @param c : client
	 * @param serveur
	 * @throws RemoteException
	 */
	public void setClient(Client c, ServeurInterface serveur) throws RemoteException {
		client = c;
		client.setServeur(serveur);
		client.setController(this);
	}

	/**
	 * Methode permettant de mettre a jour le plateau
	 * Appel� a chaque changement de tour
	 * @param g : game
	 */
	public void setGame(Game g) {
		// recuperer la main du joueur
		
		
		//recuperation de l'ensemble des cases du plateau (graphique)
		ObservableList<Node> childrens = grid.getChildren();
		int i = 0;
		for (Node node : childrens) {
			if (node instanceof Button) {
				Button b = (Button) node;
				//recuperation de la case correspondant au bouton
				Case c = g.getPlateau().getCase(b.getText());
				//methode pour rafraichir l'interface
				Platform.runLater(new Runnable() {
					public void run() {
						//verification de l'etat de la case et maj
						switch (c.getEtat()) {
						case -1 :
							b.setStyle("-fx-background-color: #000000;");
							break;
						case 0 :
							break;
						case 1 :
							b.setStyle("-fx-background-color: #000000;");
							break;
						case 2 :
							b.setStyle("-fx-background-color: #CC3333;");
							break;
						case 3 :
							b.setStyle("-fx-background-color: #FFCC33;");
							break;
						case 4 :
							b.setStyle("-fx-background-color: #FF6600;");
							break;
						case 5 :
							b.setStyle("-fx-background-color: #336633;");
							break;
						case 6 :
							b.setStyle("-fx-background-color: #333399;");
							break;
						case 7 :
							b.setStyle("-fx-background-color: #996699;");
							break;
						case 8 :
							b.setStyle("-fx-background-color: #669999;");
							break;
						default : 
							//lancer une exception ?
							break;
						}
					}
				});
			}
			if (i == 108) {
				break;
			}
		}
	}

	public void getMain() {
	}

	public void envoyerTchat() throws RemoteException {
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
		letsplay.setOpacity(0);
		letsplay.setDisable(true);
	}

	public void setBEnable(boolean b) {
		letsplay.setOpacity(1);
		letsplay.setDisable(!b);
	}

	public void setDataTableView() {
		try {
			final ObservableList<ClientInfo> data = FXCollections.observableArrayList();

			for (ClientInfo ci : client.getServeur().getGame().getTableau().getInfoParClient()) {
				data.add(ci);
			}

			tableauDeBord.setItems(data);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void genererLaMainJoueur() throws RemoteException{
		//distribuer l'affichage des mains

	}


	public void lancement() throws RemoteException {
		client.getServeur().setLancement();
		letsplay.setOpacity(0);
		//setGame(client.getServeur().getGame());
	}

	public static void nouvelleChaine(Chaine nouvelleChaine) {
		// TODO Auto-generated method stub

	}
}
