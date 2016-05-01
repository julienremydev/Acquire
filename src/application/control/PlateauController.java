package application.control;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PlateauController implements Initializable {

	/**
	 * Client correspondant au plateau
	 */
	private Client client;

	private HashMap<TypeChaine, Integer> liste_actions = new HashMap<TypeChaine, Integer>();

	private HashMap<String, Integer> actions_fusions = new HashMap<String, Integer>();
	/**
	 * element plateau
	 */
	@FXML
	private Button saveGame;
	@FXML
	private Label notificationTour;
	@FXML
	private GridPane grid;
	@FXML
	private GridPane gridPaneAction;
	@FXML
	private GridPane gridKEEP;
	@FXML
	private Label labelKEEP;
	@FXML
	private Button maxKEEP;
	@FXML
	private GridPane gridTRADE;
	@FXML
	private Label labelTRADE;
	@FXML
	private Button maxTRADE;
	@FXML
	private Button moreTRADE;
	@FXML
	private Button lessTRADE;
	@FXML
	private GridPane gridSELL;
	@FXML
	private Label labelSELL;
	@FXML
	private Button maxSELL;
	@FXML
	private Button moreSELL;
	@FXML
	private Button lessSELL;
	@FXML
	private TextArea tchat;
	@FXML
	private TextField input;
	@FXML
	private Button letsplay;
	@FXML
	private Button buttonOK;
	@FXML
	private TableView<ClientInfo> tableauDeBord;

	/**
	 * Liste d'objet du tableau de bard
	 */
	ObservableList<ClientInfo> dataTableView;

	/*
	 * Méthode qui calcul le nombre d'actions que le joueur a choisi
	 */
	private int totalesActionsJoueurs() {
		int tot = 0;
		Collection<TypeChaine> keys = liste_actions.keySet();
		for (TypeChaine key : keys) {
			tot += liste_actions.get(key);
		}
		return tot;
	}

	public HashMap<String, Integer> onEventActionFusion(String choix, Game g, String pseudo) {
		HashMap<String, Integer> actions_fusions_loc = new HashMap<String, Integer>();
		// int actionCAbsorbante =
		// g.getTableau().getClientInfo(client.getPseudo()).getActionParChaine().get(g.getAction().getChaineAbsorbante().getNomChaine());

		int actionCAbsorbee = g.getTableau().getClientInfo(pseudo).getActionParChaine()
				.get(g.getAction().getListeChainesAbsorbees().get(0).getNomChaine());

		int actionRestanteCAbsorbante = g.getListeChaine()
				.get(g.getAction().getChaineAbsorbante().getNomChaine().getNumero() - 2).getNbActionRestante();
		if (choix.equals("maxKEEP")) {
			actions_fusions_loc.put(Globals.hashMapKEEP, actionCAbsorbee);
			actions_fusions_loc.put(Globals.hashMapTRADE, 0);
			actions_fusions_loc.put(Globals.hashMapSELL, 0);
		} else if (choix.equals("maxTRADE")) {
			if (actionRestanteCAbsorbante > actionCAbsorbee / 2) {
				int modulo = actionCAbsorbee % 2;
				actionCAbsorbee -= modulo;

				actions_fusions_loc.put(Globals.hashMapTRADE, actionCAbsorbee / 2);
				if (actions_fusions.get(Globals.hashMapSELL) > actions_fusions.get(Globals.hashMapKEEP)) {
					actions_fusions_loc.put(Globals.hashMapSELL, modulo);
					actions_fusions_loc.put(Globals.hashMapKEEP, 0);
				} else {
					actions_fusions_loc.put(Globals.hashMapSELL, 0);
					actions_fusions_loc.put(Globals.hashMapKEEP, modulo);
				}
			} else {
				actions_fusions_loc.put(Globals.hashMapTRADE, actionRestanteCAbsorbante);
				actionCAbsorbee -= actionRestanteCAbsorbante * 2;

				if (actions_fusions.get(Globals.hashMapSELL) > actions_fusions.get(Globals.hashMapSELL)) {
					actions_fusions_loc.put(Globals.hashMapSELL, actionCAbsorbee);
					actions_fusions_loc.put(Globals.hashMapKEEP, 0);
				} else {
					actions_fusions_loc.put(Globals.hashMapSELL, 0);
					actions_fusions_loc.put(Globals.hashMapKEEP, actionCAbsorbee);
				}
			}
		} else if (choix.equals("moreTRADE")) {
			if (actions_fusions.get(Globals.hashMapSELL) + actions_fusions.get(Globals.hashMapKEEP) >= 2) {
				if (actionRestanteCAbsorbante >= actions_fusions.get(Globals.hashMapSELL) + 1) {
					if (actions_fusions.get(Globals.hashMapKEEP) >= 2) {
						actions_fusions_loc.put(Globals.hashMapTRADE, actions_fusions.get(Globals.hashMapTRADE) + 1);
						actions_fusions_loc.put(Globals.hashMapKEEP, actions_fusions.get(Globals.hashMapKEEP) - 2);
						actions_fusions_loc.put(Globals.hashMapSELL, actions_fusions.get(Globals.hashMapSELL));
					} else if (actions_fusions.get(Globals.hashMapKEEP) == 1) {
						actions_fusions_loc.put(Globals.hashMapTRADE, actions_fusions.get(Globals.hashMapSELL) + 1);
						actions_fusions_loc.put(Globals.hashMapKEEP, actions_fusions.get(Globals.hashMapKEEP) - 1);
						actions_fusions_loc.put(Globals.hashMapSELL, actions_fusions.get(Globals.hashMapSELL) - 1);
					} else {
						actions_fusions_loc.put(Globals.hashMapTRADE, actions_fusions.get(Globals.hashMapTRADE) + 1);
						actions_fusions_loc.put(Globals.hashMapSELL, actions_fusions.get(Globals.hashMapSELL) - 2);
						actions_fusions_loc.put(Globals.hashMapKEEP, actions_fusions.get(Globals.hashMapKEEP));
					}
				} else {
					return actions_fusions;
				}
			} else {
				return actions_fusions;
			}
		} else if (choix.equals("lessTRADE")) {

		} else if (choix.equals("maxSELL")) {

		} else if (choix.equals("moreSELL")) {

		} else if (choix.equals("lessSELL")) {

		}
		return actions_fusions_loc;
	}

	public void initializeMapAction(Game g, String pseudo) {
		actions_fusions.put(Globals.hashMapKEEP, g.getTableau().getClientInfo(pseudo).getActionParChaine()
				.get(g.getAction().getListeChainesAbsorbees().get(0).getNomChaine()));
		actions_fusions.put(Globals.hashMapTRADE, 0);
		actions_fusions.put(Globals.hashMapSELL, 0);

	}

	/*
	 * Fusion de chaîne -> le joueur doit choisir les actions qu'il souhaite
	 * acheter/vendre/échanger
	 */
	public void setChoixFusionEchangeAchatVente(Game g) {
		initializeMapAction(g, client.getPseudo());
		Platform.runLater(new Runnable() {
			public void run() {
				gridPaneAction.getChildren().clear();
				gridPaneAction.add(gridKEEP, 0, 0, 2, 2);
				gridPaneAction.add(gridTRADE, 2, 0, 2, 2);
				gridPaneAction.add(gridSELL, 4, 0, 2, 2);
				maxKEEP.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						actions_fusions = onEventActionFusion("maxKEEP", g, client.getPseudo());
						labelKEEP.setText(actions_fusions.get(Globals.hashMapKEEP).toString());
						labelTRADE.setText(actions_fusions.get(Globals.hashMapTRADE).toString());
						labelSELL.setText(actions_fusions.get(Globals.hashMapSELL).toString());
					}
				});
				moreTRADE.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						actions_fusions = onEventActionFusion("moreTRADE", g, client.getPseudo());
						labelKEEP.setText(actions_fusions.get(Globals.hashMapKEEP).toString());
						labelTRADE.setText(actions_fusions.get(Globals.hashMapTRADE).toString());
						labelSELL.setText(actions_fusions.get(Globals.hashMapSELL).toString());
					}
				});

				buttonOK.setOpacity(1);
				buttonOK.setOnAction((event) -> {
					try {
						client.choiceFusionAction(actions_fusions);
						actions_fusions.clear();
						gridPaneAction.getChildren().clear();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				gridPaneAction.add(buttonOK, 6, 1);
			}
		});

	}

	/*
	 * Méthode appelée par le Serveur quand le joueur a cliqué sur une Case
	 * adjacente à plusieurs chaînes de mêmes tailles -> Le joueur doit choisir
	 * la chaîne absorbante.
	 */
	public void setChoixFusionSameSizeChaine(Game g) {
		Platform.runLater(() -> gridPaneAction.getChildren().clear());
		int j = 0;
		for (Chaine c : g.getAction().getListeDeChaineAProposer()) {

			int i = j;
			Button b = setStyleButton(c.getNomChaine(), c.getNomChaine().toString().substring(0, 1));

			b.setOnAction((event) -> {
				try {
					client.sendChoixCouleurFusionSameChaine(g.getAction().getListeDeChainePlateau(),
							g.getAction().getListeDeChaineAProposer(), c, g.getAction().getCaseModifiee());
					gridPaneAction.getChildren().clear();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			Platform.runLater(() -> gridPaneAction.add(b, i, 0));
			j++;
		}

	}

	/*
	 * Méthode appelée par le Serveur à la fin du tour d'un joueur Permet au
	 * client d'acheter des actions
	 */
	public void setChoixAchatAction(Game game) {
		// MAJ BOUTONS ACTIONS CHOISIES
		Platform.runLater(() -> gridPaneAction.getChildren().clear());

		Collection<TypeChaine> keys = liste_actions.keySet();
		int index = 0;
		for (TypeChaine key : keys) {
			for (int i = 0; i < liste_actions.get(key); i++) {

				index += 1;
				int indexLocal = index;
				Button buttonAction = setStyleButton(key, key.toString().substring(0, 1) + "\n"
						+ TypeChaine.prixAction(key, game.getListeChaine().get(key.getNumero() - 2).tailleChaine()));
				buttonAction.setOnAction((e) -> {
					try {
						// buttonAction.setOpacity(0);
						int nbactions = liste_actions.get(key);
						if (nbactions > 1) {
							liste_actions.put(key, liste_actions.get(key) - 1);
						} else {
							liste_actions.remove(key);
						}
						setChoixAchatAction(game);
					} catch (Exception exc) {
						// TODO Auto-generated catch block
						exc.printStackTrace();
					}
				});
				Platform.runLater(() -> gridPaneAction.add(buttonAction, indexLocal - 1, 1));
			}
		}
		// MAJ BOUTONS ACTIONS DISPOS
		int j = 0;
		for (Chaine c : game.getListeChaine()) {
			if (game.getTableau().actionAvailableForPlayer(client.getPseudo(), c.getNomChaine().getNumero())) {
				int i = j;
				Button b = setStyleButton(c.getNomChaine(),
						c.getNomChaine().toString().substring(0, 1) + "\n" + TypeChaine.prixAction(c.getNomChaine(),
								game.getListeChaine().get(c.getNomChaine().getNumero() - 2).tailleChaine()));

				b.setOnAction((event) -> {
					try {
						if (totalesActionsJoueurs() < 3) {
							if (liste_actions.containsKey(c.getNomChaine()))
								liste_actions.put(c.getNomChaine(), 1 + liste_actions.get(c.getNomChaine()));
							else
								liste_actions.put(c.getNomChaine(), 1);

							setChoixAchatAction(game);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				Platform.runLater(() -> gridPaneAction.add(b, i, 0));

			}
			j++;

		}

		buttonOK.setOpacity(1);
		buttonOK.setOnAction((event) -> {
			try {
				client.buyAction(liste_actions);
				liste_actions.clear();
				gridPaneAction.getChildren().clear();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Platform.runLater(() -> gridPaneAction.add(buttonOK, 6, 1));
	}

	private Button setStyleButton(TypeChaine tc, String text) {
		Button b = new Button();

		b.getStyleClass().remove(0, 1);
		b.getStyleClass().add(tc.toString());
		b.getStyleClass().add("style_commun");
		b.setText(text);
		b.setStyle(tc.getCouleurChaine());

		return b;
	}

	/**
	 * Methode permettant d'afficher les choix possibles pour la creation dune
	 * chaine
	 * 
	 * @param g
	 */
	public void setChoixCreationChaine(Game g) {
		int j = 0;
		for (Chaine c : g.getListeChaine()) {
			if (c.chaineDisponible()) {
				int i = j;
				Button b = setStyleButton(c.getNomChaine(), c.getNomChaine().toString().substring(0, 1));

				b.setOnAction((event) -> {
					try {
						client.pickColor(g.getAction(), c.getNomChaine());
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
		b.setDisable(true);
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

						if (client.getPseudo().equals(g.getPlayerTurn())) {
							notificationTour.setText("A votre tour de jouer");
							notificationTour.setTextFill(Color.GREEN);
						} else {
							notificationTour.setText("Au tour de " + g.getPlayerTurn());
							notificationTour.setTextFill(Color.DARKGREY);
						}

						// si le joueur possède la case dans sa main
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
								b.setStyle(g.getListeChaine().get(c.getEtat() - 2).getNomChaine().getCouleurChaine());
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
		Platform.runLater(() -> gridPaneAction.getChildren().clear());
		dataTableView = FXCollections.observableArrayList();
		saveGame.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				try {
					client.sauvegardePartie();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void setBEnable(boolean b) {
		if (b)
			Platform.runLater(() -> gridPaneAction.add(letsplay, 2, 1, 3, 1));
		else
			Platform.runLater(() -> gridPaneAction.getChildren().clear());

		letsplay.setDisable(!b);
	}

	/*
	 * Mise a jour du tableau
	 */
	private void setDataTableView(Game g) {
		dataTableView.clear();
		HashMap<String, ClientInfo> infoClient = g.getTableau().getInfoParClient();
		Collection<ClientInfo> values = infoClient.values();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (ClientInfo ci : values) {
					dataTableView.add(ci);
				}
			}
		});
		tableauDeBord.setItems(dataTableView);
	}

	public void lancement() throws RemoteException {
		client.getServeur().setLancement();
		Platform.runLater(() -> gridPaneAction.getChildren().clear());
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

	/*
	 * 
	 * Enregistrement du GAME au format JSON sur la machine du client
	 */
	public void saveTheGame(Game game) {
		if (game == null)
			System.out.println("la partie n'a pas commencé");

	}

}
