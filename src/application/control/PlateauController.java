package application.control;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import application.globale.Globals;
import application.model.Action;
import application.model.Case;
import application.model.Chaine;
import application.model.ClientInfo;
import application.model.TypeChaine;
import application.rmi.Client;
import application.rmi.ClientInterface;
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
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
	/**
	 * element plateau
	 */
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


	private void setHMAction ( int keep, int trade, int sell){
		actions_fusions.put(Globals.hashMapKEEP, keep);
		actions_fusions.put(Globals.hashMapTRADE, trade);
		actions_fusions.put(Globals.hashMapSELL, sell);
	}
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
	/*
	 * Fusion de chaîne -> le joueur doit choisir les actions qu'il souhaite
	 * acheter/vendre/échanger
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
						client.choiceFusionAction(getActions_fusions(), g.getAction().getListeChainesAbsorbees().get(0), g.getAction().getChaineAbsorbante());
						getActions_fusions().clear();
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
			Button b = setStyleButton(c.getTypeChaine(), c.getTypeChaine().toString().substring(0, 1));

			b.setOnAction((event) -> {
				try {
					//					ArrayList<Chaine> nouvelleListeAModifier = g.getAction().getListeDeChaineAProposer();
					//					g.getAction().setListeChainesAbsorbees(nouvelleListeAModifier);
					client.sendChoixCouleurFusionSameChaine(g.getListeChaine(),
							g.getAction().getListeDeChaineAProposer(), c, g.getAction().getListeCaseAbsorbees());

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
		int depense = calculArgentImmobiliseAction(game);
		
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
			if (game.getTableauDeBord().actionAvailableForPlayer(client.getPseudo(), c.getTypeChaine().getNumero(),depense)) {
				int i = j;
				Button b = setStyleButton(c.getTypeChaine(),
						c.getTypeChaine().toString().substring(0, 1) + "\n" + TypeChaine.prixAction(c.getTypeChaine(),
								game.getListeChaine().get(c.getTypeChaine().getNumero() - 2).tailleChaine()));

				b.setOnAction((event) -> {
					try {
						if (totalesActionsJoueurs() < 3) {
							if (liste_actions.containsKey(c.getTypeChaine()))
								liste_actions.put(c.getTypeChaine(), 1 + liste_actions.get(c.getTypeChaine()));
							else
								liste_actions.put(c.getTypeChaine(), 1);

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

	private int calculArgentImmobiliseAction(Game g){
		int tot = 0;

		Collection<TypeChaine> keys = liste_actions.keySet();
		for (TypeChaine key : keys) {
			tot += TypeChaine.prixAction(key,g.getListeChaine().get(key.getNumero()-2).tailleChaine()) * liste_actions.get(key);
		}
		return tot;
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
				Button b = setStyleButton(c.getTypeChaine(), c.getTypeChaine().toString().substring(0, 1));

				b.setOnAction((event) -> {
					try {
						client.pickColor(g.getAction(), c.getTypeChaine());
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
		if (this.endGame) {
			buttonEND.setDisable(false);
		}
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

						// si le joueur possède la case dans sa main
						if (c.getEtat()!= -1 && g.getTableauDeBord().getInfoParClient().get(client.getPseudo()).getMain().contains(b.getText())) {
							//b.setStyle(Globals.colorCasePlayer);
							b.setId("caseDisponible");
							b.setStyle("-fx-text-fill: red");
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
								b.setStyle(g.getListeChaine().get(c.getEtat() - 2).getTypeChaine().getCouleurChaine());
							}
						}

					}
				});

			}
		}
	}

	public void envoyerTchat() throws RemoteException {
		if (input.getText().trim().length() > 0){
			client.getServeur().distributionTchat(client.getPseudo(), input.getText().trim());
			Platform.runLater(() -> input.clear());
		}
	}

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
		scroll.setContent(tchat);

		buttonEND.setDisable(true);
		endGame=false;
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

		// Convert object to JSON string
		String jsonInString = mapper.writeValueAsString(g);

		// Convert object to JSON string and pretty print

	}

	/*
	 * 
	 * Enregistrement du GAME au format JSON sur la machine du client
	 */
	public void saveTheGame(Game g) throws RemoteException {
		if (g != null) {
			try {
				sauvgarderGame(g, "AcquireGame.json");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("la partie n'a pas commencé");
		}

	}

	public void isOver() { 
		try {
			client.isOver();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void endingGame(ArrayList<String> winner) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				notificationTour.setText(winner.get(0) +" a gagné !");	
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