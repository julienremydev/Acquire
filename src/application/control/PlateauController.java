package application.control;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import application.globale.Globals;
import application.model.Case;
import application.model.Chaine;
import application.model.ClientInfo;
import application.model.InfoChaine;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PlateauController implements Initializable {

	/**
	 * Client correspondant au plateau
	 */
	private Client client;

	private HashMap<TypeChaine, Integer> liste_actions = new HashMap<TypeChaine, Integer>();

	private HashMap<String, Integer> actions_fusions = new HashMap<String, Integer>();

	private boolean endGame;

	private boolean endGameOK;
	/**
	 * element plateau
	 */
	@FXML
	private GridPane gridGame;
	@FXML
	private Button buttonEND;
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
	private TextFlow tchat;
	@FXML
	private TextField input;
	@FXML
	private Button letsplay;
	@FXML
	private Button buttonOK;
	@FXML
	private ScrollPane scroll;
	@FXML
	private TableView<ClientInfo> tableauDeBord;
	@FXML
	private TableView<InfoChaine> infosChaine;

	/**
	 * Liste d'objet du tableau de bard
	 */
	ObservableList<ClientInfo> dataTableView;
	ObservableList<InfoChaine> dataInfoChaine;



	/**
	 * Set hash map Action
	 * @param keep
	 * @param trade
	 * @param sell
	 */
	private void setHMAction ( int keep, int trade, int sell){
		actions_fusions.put(Globals.hashMapKEEP, keep);
		actions_fusions.put(Globals.hashMapTRADE, trade);
		actions_fusions.put(Globals.hashMapSELL, sell);
	}
	/**
	 * Event Action Fusion
	 * @param choix
	 * @param g
	 * @param pseudo
	 */
	public void onEventActionFusion(String choix, Game g, String pseudo) {
		int actionCAbsorbee = g.getTableauDeBord().getClientInfo(pseudo).getActionParChaine()
				.get(g.getAction().getListeChainesAbsorbees().get(0).getTypeChaine());

		int actionRestanteCAbsorbante = g.getListeChaine()
				.get(g.getAction().getChaineAbsorbante().getTypeChaine().getNumero() - 2).getNbActionRestante();

		if (choix.equals("maxKEEP")) {
			setHMAction ( actionCAbsorbee, 0, 0);
		} else if (choix.equals("maxTRADE")) {
			if (actionRestanteCAbsorbante > actionCAbsorbee / 2) {
				int modulo = actionCAbsorbee % 2;
				actionCAbsorbee -= modulo;
				if (getActions_fusions().get(Globals.hashMapSELL) > getActions_fusions().get(Globals.hashMapKEEP)) {
					setHMAction ( 0, actionCAbsorbee / 2, modulo);
				} else {
					setHMAction ( modulo, actionCAbsorbee / 2, 0);
				}
			} else {
				actionCAbsorbee -= actionRestanteCAbsorbante * 2;
				if (getActions_fusions().get(Globals.hashMapSELL) > getActions_fusions().get(Globals.hashMapKEEP)) {
					setHMAction ( 0, actionRestanteCAbsorbante , actionCAbsorbee);
				} else {
					setHMAction ( actionCAbsorbee, actionRestanteCAbsorbante , 0);
				}
			}
		} else if (choix.equals("moreTRADE")) {
			if (getActions_fusions().get(Globals.hashMapSELL) + getActions_fusions().get(Globals.hashMapKEEP) >= 2) {
				if ( actionRestanteCAbsorbante - getActions_fusions().get(Globals.hashMapTRADE) > 0){
					if ( getActions_fusions().get(Globals.hashMapKEEP) >= 2){
						setHMAction ( getActions_fusions().get(Globals.hashMapKEEP)-2, getActions_fusions().get(Globals.hashMapTRADE)+1, getActions_fusions().get(Globals.hashMapSELL));
					}
					else if (getActions_fusions().get(Globals.hashMapKEEP) == 1) {
						setHMAction ( getActions_fusions().get(Globals.hashMapKEEP)-1, getActions_fusions().get(Globals.hashMapTRADE)+1, getActions_fusions().get(Globals.hashMapSELL)-1);
					} else {
						setHMAction ( getActions_fusions().get(Globals.hashMapKEEP), getActions_fusions().get(Globals.hashMapTRADE)+1, getActions_fusions().get(Globals.hashMapSELL)-2);
					} 
				}
			}
		} else if (choix.equals("lessTRADE")) {
			if (getActions_fusions().get(Globals.hashMapTRADE) >= 1){
				setHMAction ( getActions_fusions().get(Globals.hashMapKEEP)+2, getActions_fusions().get(Globals.hashMapTRADE)-1, getActions_fusions().get(Globals.hashMapSELL));
			}

		} else if (choix.equals("maxSELL")) {
			setHMAction ( 0, 0, actionCAbsorbee);
		} else if (choix.equals("moreSELL")) {
			if (getActions_fusions().get(Globals.hashMapKEEP) >= 1){
				setHMAction ( getActions_fusions().get(Globals.hashMapKEEP)-1, getActions_fusions().get(Globals.hashMapTRADE), getActions_fusions().get(Globals.hashMapSELL)+1);
			}
		} else if (choix.equals("lessSELL")) {
			if (getActions_fusions().get(Globals.hashMapSELL) >= 1){
				setHMAction ( getActions_fusions().get(Globals.hashMapKEEP)+1, getActions_fusions().get(Globals.hashMapTRADE), getActions_fusions().get(Globals.hashMapSELL)-1);
			}
		}
	}
	/**
	 * Initialisation de Map Action
	 * @param g
	 * @param pseudo
	 */
	public void initializeMapAction(Game g, String pseudo) {
		getActions_fusions().put(Globals.hashMapKEEP, g.getTableauDeBord().getClientInfo(pseudo).getActionParChaine()
				.get(g.getAction().getListeChainesAbsorbees().get(0).getTypeChaine()));
		getActions_fusions().put(Globals.hashMapTRADE, 0);
		getActions_fusions().put(Globals.hashMapSELL, 0);

	}
	private void setLabelsActions (){
		labelKEEP.setText(getActions_fusions().get(Globals.hashMapKEEP).toString());
		labelTRADE.setText(getActions_fusions().get(Globals.hashMapTRADE).toString());
		labelSELL.setText(getActions_fusions().get(Globals.hashMapSELL).toString());
	}
	private void setActionBoutons (Button b, Game g){
		b.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				onEventActionFusion(b.getId().toString(), g, client.getPseudo());
				setLabelsActions ();
			}
		});
	}
	/**
	 * Fusion de cha�ne  le joueur doit choisir les actions qu'il souhaite
	 * acheter/vendre/�changer
	 */
	public void setChoixFusionEchangeAchatVente(Game g) {

		Platform.runLater(new Runnable() {
			public void run() {

				initializeMapAction(g, client.getPseudo());

				gridPaneAction.getChildren().clear();
				setLabelsActions ();

				gridKEEP.setStyle(g.getAction().getListeChainesAbsorbees().get(0).getTypeChaine().getCouleurChaine());
				gridTRADE.setStyle(g.getAction().getChaineAbsorbante().getTypeChaine().getCouleurChaine());

				gridPaneAction.add(gridKEEP, 0, 0, 2, 2);
				gridPaneAction.add(gridTRADE, 2, 0, 2, 2);
				gridPaneAction.add(gridSELL, 4, 0, 2, 2);

				setActionBoutons (maxKEEP, g);

				setActionBoutons (moreTRADE, g);
				setActionBoutons (lessTRADE, g);
				setActionBoutons (maxTRADE, g);

				setActionBoutons (moreSELL, g);
				setActionBoutons (lessSELL, g);
				setActionBoutons (maxSELL, g);


				buttonOK.setOpacity(1);
				buttonOK.setOnAction((event) -> {
					try {
						int prix_action_absorbante=TypeChaine.prixAction(g.getAction().getChaineAbsorbante().getTypeChaine(), g.getAction().getChaineAbsorbante().tailleChaine());
						int prix_action_absorbee=TypeChaine.prixAction(g.getAction().getListeChainesAbsorbees().get(0).getTypeChaine(), g.getAction().getListeChainesAbsorbees().get(0).tailleChaine());
						client.choiceFusionAction(getActions_fusions(), g.getAction().getListeChainesAbsorbees().get(0), g.getAction().getChaineAbsorbante(),prix_action_absorbante,prix_action_absorbee);
						getActions_fusions().clear();
						gridPaneAction.getChildren().clear();
					} catch (Exception e) {
						Logger.getLogger("Client").log(Level.SEVERE,"Probleme de fusion");
					}
				});
				gridPaneAction.add(buttonOK, 6, 1);
			}
		});

	}

	/**
	 * M�thode appel�e par le Serveur quand le joueur a cliqu� sur une Case
	 * adjacente � plusieurs cha�nes de m�mes tailles  Le joueur doit choisir
	 * la cha�ne absorbante.
	 */
	public void setChoixFusionSameSizeChaine(Game g) {
		Platform.runLater(() -> gridPaneAction.getChildren().clear());
		int j = 0;
		for (Chaine c : g.getAction().getListeDeChaineAProposer()) {

			int i = j;
			Button b = setStyleButton(c.getTypeChaine(), c.getTypeChaine().toString().substring(0, 1));

			b.setOnAction((event) -> {
				try {
					//					ArrayList<Chaine> nouvelleListeAModifier = g.getAction().getListeDeChaineAProposer();
					//					g.getAction().setListeChainesAbsorbees(nouvelleListeAModifier);
					client.sendChoixCouleurFusionSameChaine(g.getListeChaine(),
							g.getAction().getListeChainesAbsorbees(), c, g.getAction().getListeCaseAbsorbees());

					gridPaneAction.getChildren().clear();
				} catch (Exception e) {
					Logger.getLogger("Client").log(Level.SEVERE,"Probleme de fusion");
				}
			});
			Platform.runLater(() -> gridPaneAction.add(b, i, 0));
			j++;
		}

	}

	/**
	 * M�thode appel�e par le Serveur � la fin du tour d'un joueur Permet au
	 * client d'acheter des actions
	 */
	public void setChoixAchatAction(Game game) {
		int depense = game.calculArgentImmobiliseAction(liste_actions);

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
						Logger.getLogger("Client").log(Level.SEVERE,"Probleme liste choix action");
					}
				});
				Platform.runLater(() -> gridPaneAction.add(buttonAction, indexLocal - 1, 1));
			}
		}
		// MAJ BOUTONS ACTIONS DISPOS
		int j = 0;
		for (Chaine c : game.getListeChaine()) {
			if (game.getTableauDeBord().actionAvailableForPlayer(client.getPseudo(), c.getTypeChaine().getNumero(),depense)) {
				int i = j;
				Button b = setStyleButton(c.getTypeChaine(),
						c.getTypeChaine().toString().substring(0, 1) + "\n" + TypeChaine.prixAction(c.getTypeChaine(),
								game.getListeChaine().get(c.getTypeChaine().getNumero() - 2).tailleChaine()));

				b.setOnAction((event) -> {
					try {
						if (game.totalesActionsJoueurs(liste_actions) < 3) {
							if (liste_actions.containsKey(c.getTypeChaine()))
								liste_actions.put(c.getTypeChaine(), 1 + liste_actions.get(c.getTypeChaine()));
							else
								liste_actions.put(c.getTypeChaine(), 1);

							setChoixAchatAction(game);
						}
					} catch (Exception e) {
						Logger.getLogger("Client").log(Level.SEVERE,"Probleme liste action");
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
				if (endGameOK) {
					isOver();
				}
			} catch (Exception e) {
				Logger.getLogger("Client").log(Level.SEVERE,"Probleme achat actions");
			}
		});
		Platform.runLater(() -> gridPaneAction.add(buttonOK, 6, 1));
	}

	/**
	 * Changement du style des boutons
	 * @param tc
	 * @param text
	 * @return
	 */
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
				Button b = setStyleButton(c.getTypeChaine(), c.getTypeChaine().toString().substring(0, 1));
				b.setOnAction((event) -> {
					try {
						client.pickColor(g.getAction(), c.getTypeChaine());
						gridPaneAction.getChildren().clear();
					} catch (Exception e1) {
						Logger.getLogger("Client").log(Level.SEVERE,"Probleme choix creation chaine");
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
		if (this.endGame) {
			buttonEND.setDisable(false);
		}
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
	 * Methode permettant de mettre a jour le plateau Appel� a chaque changement
	 * de tour
	 * 
	 * @param g
	 *            : game
	 */
	public void setGame(Game g) {
		//fin du jeu
		if (g.isOver()) {
			endGame=true;
		}
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

						//case gris� full hotel dans la main du joueur
						if (c.getEtat()==-2 && g.getTableauDeBord().getInfoParClient().get(client.getPseudo()).getMain().contains(b.getText())) {
							b.setStyle(Globals.colorCaseFullChaine);
							b.setDisable(true);
						}
						//case dans la main du joueur
						else if (c.getEtat()!= -1 && g.getTableauDeBord().getInfoParClient().get(client.getPseudo()).getMain().contains(b.getText())) {
							b.setStyle(Globals.colorCasePlayer);
							b.setId("caseDisponible");
							b.setTextFill(Color.RED);
							b.setDisable(false);
						} 
						else {
							switch(c.getEtat()) {
							// verification de l'etat de la case et maj
							case (-2) :
								break;
							case (-1) :
								// case gris�e
								b.setStyle(Globals.colorCaseGrey);
							b.setDisable(true);
							break;
							case (0) :
								// case vide
								b.setStyle(Globals.colorCaseEmpty);
							break;
							case (1) :
								// case hotel
								b.setStyle(Globals.colorCaseHotel);
							b.setTextFill(Color.WHITE);

							break;
							default :
								//case chaine
								b.setStyle(g.getListeChaine().get(c.getEtat() - 2).getTypeChaine().getCouleurChaine());
								break;
							}
						}

					}
				});

			}
		}

		for(int i=0;i<g.getTableauDeBord().getListeChaine().size(); i++){
			g.getTableauDeBord().getInfosChaine().get(0).getInfos().put(g.getTableauDeBord().getListeChaine().get(i).getTypeChaine(), g.getTableauDeBord().getListeChaine().get(i).tailleChaine());
		}
	}
	/**
	 * Envoi du chat
	 * @throws RemoteException
	 */
	public void envoyerTchat() throws RemoteException {
		if (input.getText().trim().length() > 0){
			client.getServeur().distributionTchat(client.getPseudo(), input.getText().trim());
			Platform.runLater(() -> input.clear());
		}
	}
	/**
	 * modification du chat
	 * @param talk
	 */
	private void customiseChat(ArrayList<String> talk){
		int i = 0;
		while (i < talk.size()){
			if (talk.get(i).length()> 8 && talk.get(i).substring(0,9).equals("[Serveur]")){

				Text tbold = new Text ();
				tbold.setFill(Color.RED);
				tbold.setFont(Font.font(java.awt.Font.SERIF, FontWeight.BOLD, 15));
				tbold.setText(talk.get(i));
				Text t = new Text ();
				t.setFont(Font.font(java.awt.Font.SERIF, 13));
				t.setText(talk.get(i+1)+"\n");

				tchat.getChildren().addAll(tbold,t);
				i+=2;

			}else{
				Text tbold = new Text ();
				tbold.setFill(Color.LIGHTSEAGREEN);
				tbold.setFont(Font.font(java.awt.Font.SERIF, FontWeight.BOLD, 13));
				tbold.setText(talk.get(i));
				Text t = new Text ();
				t.setFont(Font.font(java.awt.Font.SERIF, 13));
				t.setText(talk.get(i+1)+"\n");


				tchat.getChildren().addAll(tbold,t);
				i+=2;
			}
		}
	}
	public void setChat(ArrayList<String> s) {
		Platform.runLater(new Runnable() {
			public void run() {
				tchat.getChildren().clear();
				customiseChat(s);
				scroll.setVvalue(1);
			}
		});
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Logger.getLogger("Client").log(Level.INFO, "Client lanc�");
		scroll.setContent(tchat);
		buttonEND.setDisable(true);
		endGame=false;
		endGameOK=false;
		Platform.runLater(() -> gridPaneAction.getChildren().clear());
		dataTableView = FXCollections.observableArrayList();
		saveGame.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				try {
					client.sauvegardePartie();
				} catch (RemoteException e1) {
					Logger.getLogger("Client").log(Level.SEVERE,"Probleme de connexion");
				} catch (IOException e1) {
					Logger.getLogger("Client").log(Level.SEVERE,"Probleme de connexion");
				}
			}
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Pane header = (Pane) infosChaine.lookup("TableHeaderRow");
				header.setMaxHeight(0);
				header.setPrefHeight(0);
				header.setVisible(false);
				infosChaine.setLayoutY(-header.getHeight());
				infosChaine.autosize();
			}
		});
		dataInfoChaine = FXCollections.observableArrayList();
	}

	public void setBEnable(boolean b) {
		if (b)
			Platform.runLater(() -> gridPaneAction.add(letsplay, 2, 1, 3, 1));
		else
			Platform.runLater(() -> gridPaneAction.getChildren().clear());

		letsplay.setDisable(!b);
	}

	/**
	 * Mise a jour du tableau
	 */
	private void setDataTableView(Game g) {
		dataTableView.clear();
		HashMap<String, ClientInfo> infoClient = g.getTableauDeBord().getInfoParClient();
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


		dataInfoChaine.clear();
		ArrayList<InfoChaine> infoChaine = g.getTableauDeBord().getInfosChaine();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (int i=0; i<infoChaine.size(); i++) {
					dataInfoChaine.add(infoChaine.get(i));
				}
			}
		});
		infosChaine.setItems(dataInfoChaine);
	}
	/**
	 * M�thode de lancement
	 * @throws RemoteException
	 */
	public void lancement() throws RemoteException {
		client.getServeur().setLancement();
		gridPaneAction.getChildren().clear();
	}

	/**
	 * Methode permettant de bloquer les actions du joueur sur le plateau (fin
	 * de tour)
	 */
	public void setOff() {
		grid.setMouseTransparent(true);
	}

	/**
	 * Methode permettant d'activer les actions du joueur sur le plateau (d�but
	 * de tour)
	 */
	public void setOn() {
		grid.setMouseTransparent(false);
	}


	/**
	 * methode permets de sauvgarder l'objet Game courant sous formatJSON via Jackson
	 * @param g
	 * @param adr
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void sauvgarderGame(Game g, String adr)
			throws JsonGenerationException, JsonMappingException, IOException {

		
		ObjectMapper mapper = new ObjectMapper();
		File nf = new File(adr);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(nf, g);
	}

	/**
	 * 
	 * Enregistrement du GAME au format JSON sur la machine du client
	 */
	public void saveTheGame(Game g) throws RemoteException, IOException {
		if (g != null) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Stage stage = (Stage) saveGame.getScene().getWindow();
					//sauvgarderGame(g, "AcquireGame.json");
					
					 FileChooser fileChooser = new FileChooser();
					    fileChooser.setTitle("Save JSON");
					 // On d�finit le filtre des extensions :JSON
						FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)",
								"*.json");
						fileChooser.getExtensionFilters().add(extFilter);

					    File file = fileChooser.showSaveDialog(stage);
					    if (file != null) {
					        try {
					        	ObjectMapper mapper = new ObjectMapper();
					        	
								mapper.enable(SerializationFeature.INDENT_OUTPUT);
								mapper.writeValue(file, g);
					        } catch (IOException ex) {
					            System.out.println(ex.getMessage());
					        }
					    }
				}
			});
			
		} else {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Text tbold = new Text ();
					tbold.setFill(Color.RED);
					tbold.setFont(Font.font(java.awt.Font.SERIF, FontWeight.BOLD, 13));
					tbold.setText("La partie doit �tre commenc�e avant de la sauvegarder.\n");
					tchat.getChildren().add(tbold);
				}
			});
		}

	}
	public void setOver() {
		endGameOK=true;
	}

	public void isOver() { 
		try {
			client.sendEndGame();
		}
		catch (Exception e) {
			Logger.getLogger("Client").log(Level.SEVERE,"Probleme de fin de partie");
		}
	}
	/**
	 * Fin du game
	 * @param winner
	 */
	public void endingGame(ArrayList<String> winner) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				HBox hbox = new HBox();
				hbox.setSpacing(50);
				Image image = new Image(getClass().getResourceAsStream("/application/view/trophy.png"));
				ImageView img = new ImageView(image);
				img.setFitWidth(30);
				img.setFitHeight(30);
				Label label1 = new Label("TABLEAU DES SCORES\n");
				hbox.getChildren().add((label1));
				int max = 0;
				for (int i =0; i<winner.size();i++){
					if ( i%2 == 0){
						Label label2 = new Label("\n"+winner.get(i)+" : "+winner.get(i+1)+"\n");
						if ( Integer.parseInt(winner.get(i+1)) >= max){
							max = Integer.parseInt(winner.get(i+1));
							label2.setGraphic(img);
						}

						hbox.getChildren().add((label2));

					}

				}

				gridGame.getChildren().clear();
				gridGame.add(hbox,0,0,2,3);	
			}
		}); 

	}

	public HashMap<String, Integer> getActions_fusions() {
		return actions_fusions;
	}

	public void setActions_fusions(HashMap<String, Integer> actions_fusions) {
		this.actions_fusions = actions_fusions;
	}
}