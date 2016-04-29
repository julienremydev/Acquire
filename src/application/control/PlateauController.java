package application.control;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.globale.Globals;
import application.model.Action;
import application.model.Case;
import application.model.Chaine;
import application.model.ClientInfo;
import application.model.TypeChaine;
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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PlateauController implements Initializable {

	/**
	 * Client correspondant au plateau
	 */
	private Client client;

	private HashMap<TypeChaine, Integer> liste_actions = new HashMap<TypeChaine, Integer> ();
	/**
	 * element plateau
	 */
	@FXML
	private GridPane grid;
	@FXML
	private GridPane gridPaneAction;
	@FXML
	private TextArea tchat;
	@FXML
	private TextField input;
	@FXML
	private Button letsplay;
	@FXML
	private TableView<ClientInfo> tableauDeBord;
	
	/**
	 * Liste d'objet du tableau de bard
	 */
	ObservableList<ClientInfo> dataTableView;

	private int totalesActionsJoueurs (){
		int tot = 0;
		Collection<TypeChaine> keys = liste_actions.keySet();
		for (TypeChaine key : keys) {
			tot += liste_actions.get(key);
		}
		return tot;
	}
	public void setChoixAchatAction(Game game) {
		// TODO BIEN FAIRE LALGO 
		int j = 0;
		for (Chaine c : game.getListeChaine()){
			if (game.getTableau().actionAvailableForPlayer(client.getPseudo(), c.getNomChaine().getNumero())) {
				int i = j;
				Button b = new Button(c.getNomChaine().toString().substring(0, 1)+"\n"+TypeChaine.prixAction(c.getNomChaine(), Globals.nbActionTotal-c.getNbActionRestante()));
				
				b.setStyle(c.getNomChaine().getCouleurChaine());
				
				b.setPrefWidth(300);
				
				b.setPrefHeight(300);
				
				b.setOnAction((event) -> {
					try {
						if ( totalesActionsJoueurs() < 3 ){
							Button buttonAction = new Button(c.getNomChaine().toString().substring(0, 1));
							buttonAction.setStyle(c.getNomChaine().getCouleurChaine());
							buttonAction.setPrefWidth(300);
							buttonAction.setPrefHeight(300);
							Platform.runLater(() -> gridPaneAction.add(buttonAction, totalesActionsJoueurs() - 1, 1));
							if ( liste_actions.containsKey(c.getNomChaine()))
								liste_actions.put(c.getNomChaine(), 1 + liste_actions.get(c.getNomChaine()) );
							else
								liste_actions.put(c.getNomChaine(), 1);
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				Platform.runLater(() -> gridPaneAction.add(b, i, 0));
			}
			j++;
		}
		Button buyIT =new Button("Acheter");
		buyIT.setPrefWidth(300);
		buyIT.setPrefHeight(300);
		buyIT.setOnAction((event) -> {
			try {
				client.buyAction (liste_actions);
				liste_actions.clear();
				gridPaneAction.getChildren().clear();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Platform.runLater(() -> gridPaneAction.add(buyIT, 5, 1));
		//TODO ajouter bouton acheter action, qui appelle une fonction sur le serveur 
		//qui termine le tour du joueur 
	}
	
	
	/**
	 * Methode permettant d'afficher les choix possibles pour la creation dune
	 * chaine
	 * 
	 * @param a
	 * @param g
	 */
	public void setChoixCreationChaine(Action a, Game g) {
		int j = 0;
		for (Chaine c : g.getListeChaine()) {
			if (c.chaineDisponible()) {
				int i = j;
				Button b = new Button(c.getNomChaine().toString().substring(0, 1));

				// TODO définir le layout des boutons parce que c'est moche
				b.setStyle(c.getNomChaine().getCouleurChaine());
				b.setPrefWidth(300);
				b.setPrefHeight(300);
				b.setOnAction((event) -> {
					try {
						client.pickColor(a, c.getNomChaine());
						gridPaneAction.getChildren().clear();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				Platform.runLater(() -> gridPaneAction.add(b, i, 0));
			}
			j++;
		}
	}

	/**
	 * Methode lance au moment du clic sur une case
	 * 
	 * @param e
	 *            : correpond au bouton clique
	 * @throws Exception
	 */
	public void setDisable(ActionEvent e) throws Exception {
		// on recupere la source pour avoir le bouton
		Button b = (Button) e.getSource();
		// on recupere le nom de la case, exemple "A5"
		String text = b.getText();
		// on envoie via le rmi la case clique
		client.sendCase(text);
		this.setOff();
		/*
		 * 
		 */
		// TODO verifier que c'est bien la fin du tour (creation chaine etc)
		/*
		 * 
		 */
	}

	/**
	 * Methode permettant d'effectuer la liaison rmi
	 * 
	 * @param c
	 *            : client
	 * @param serveur
	 * @throws RemoteException
	 */
	public void setClient(Client c, ServeurInterface serveur) throws RemoteException {
		client = c;
		client.setServeur(serveur);
		client.setController(this);
		this.setOff();
	}

	/**
	 * Methode permettant de mettre a jour le plateau Appelé a chaque changement
	 * de tour
	 * 
	 * @param g
	 *            : game
	 */
	public void setGame(Game g) {
		setDataTableView(g);

		// recuperation de l'ensemble des cases du plateau (graphique)
		ObservableList<Node> childrens = grid.getChildren();
		for (Node node : childrens) {
			if (node instanceof Button) {
				Button b = (Button) node;
				b.setTextFill(Color.BLACK);

				// recuperation de la case correspondant au bouton
				Case c = g.getPlateau().getCase(b.getText());

				// methode pour rafraichir l'interface
				Platform.runLater(new Runnable() {
					public void run() {

						//si le joueur possède la case dans sa main
						if (g.getTableau().getInfoParClient().get(client.getPseudo()).getMain().contains(b.getText())) {
							b.setStyle(Globals.colorCasePlayer);
							b.setDisable(false);
						} else {
							// verification de l'etat de la case et maj
							if (c.getEtat() == -1) {
								// case grisée
								b.setStyle(Globals.colorCaseGrey);
							} else if (c.getEtat() == 0) {
								// case vide
								b.setStyle(Globals.colorCaseEmpty);
							} else if (c.getEtat() == 1) {
								// hôtel
								b.setStyle(Globals.colorCaseHotel);
								b.setTextFill(Color.WHITE);
							} else {
								b.setStyle(g.getListeChaine().get(c.getEtat()-2).getNomChaine().getCouleurChaine());
							}
						}

					}
				});

			}
		}
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
		dataTableView = FXCollections.observableArrayList();
	}

	public void setBEnable(boolean b) {
		letsplay.setOpacity(1);
		letsplay.setDisable(!b);
	}

	private void setDataTableView(Game g) {
		dataTableView.clear();
		HashMap<String, ClientInfo> infoClient = g.getTableau().getInfoParClient();
		Collection<ClientInfo> values = infoClient.values();
		for (ClientInfo ci : values) {
			dataTableView.add(ci);
		}
		tableauDeBord.setItems(dataTableView);
	}

	public void lancement() throws RemoteException {
		client.getServeur().setLancement();
		letsplay.setOpacity(0);
	}

	/**
	 * Methode permettant de bloquer les actions du joueur sur le plateau (fin
	 * de tour)
	 */
	public void setOff() {
		grid.setMouseTransparent(true);
	}

	/**
	 * Methode permettant d'activer les actions du joueur sur le plateau (début
	 * de tour)
	 */
	public void setOn() {
		grid.setMouseTransparent(false);
	}

}
