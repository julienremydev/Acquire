package application.control;

import java.io.File;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.globale.Globals;
import application.rmi.Client;
import application.rmi.Game;
import application.rmi.ServeurInterface;
import application.view.JfxUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConnexionController implements Initializable {

	private File fileJSON;
	private Client client;

	@FXML
	private TextField ip1;
	@FXML
	private TextField ip2;
	@FXML
	private TextField ip3;
	@FXML
	private TextField ip4;
	@FXML
	private TextField pseudo;
	@FXML
	private Label ipclient;
	@FXML
	private Label erreur;
	@FXML
	private Button chargerJSON;
	@FXML
	private Label pathJSON;
	@FXML
	private CheckBox checkBoxJSON;

	/**
	 * Vérification syntaxique de l'adresse IP. Return true si la syntaxe est
	 * correcte, false sinon.
	 */
	public boolean ipCorrecte(String s) {
		if (s.trim().matches("\\d+") && s.trim().length() < 4 && Integer.parseInt(s.trim()) >= 0
				&& Integer.parseInt(s.trim()) <= 255)
			return true;
		else
			return false;
	}

	/**
	 * Méthode appelée quand le client se connecte avec succès. On met à jour la
	 * scène de l'IG.
	 * 
	 */
	private void setNewIG(Stage stage, Group root, ServeurInterface serveur) throws RemoteException, Exception {

		Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
		//Application du code CSS
		scene.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
				
		stage.setTitle("Acquire");
		stage.setScene(scene);

		/*
		 * Fermeture de l'application quand un client quitte la fenêtre. On
		 * informe le serveur qu'un client a quitté l'application.
		 */
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				try {
					serveur.logout(client.getPseudo());
				} catch (RemoteException e) {
					Logger.getLogger("Client").log(Level.SEVERE,"Erreur logout client");
				}

				Platform.exit();
				System.exit(0);
			}
		});

	}
	/**
	 * Connexion
	 * @param ip1
	 * @param ip2
	 * @param ip3
	 * @param ip4
	 * @return
	 * @throws Exception
	 */
	public ServeurInterface connexion(String ip1, String ip2, String ip3, String ip4) throws Exception {
		ServeurInterface serveur = (ServeurInterface) Naming
				.lookup("rmi://" + ip1 + "." + ip2 + "." + ip3 + "." + ip4 + ":42000/ACQUIRE");
		return serveur;
	}
	/**
	 * Vérification du pseudo / taille entre 3 et 12 / pseudo reservé / adresse ip problem
	 * @param pseudo
	 * @param ip1
	 * @param ip2
	 * @param ip3
	 * @param ip4
	 * @return
	 */
	public String verification_Pseudo_IP(String pseudo, String ip1, String ip2, String ip3, String ip4) {
		if (pseudo.length() < 3 || pseudo.length() > 12) {
			return (Globals.erreurTaillePseudo);
		} else if (pseudo.equals("Serveur")) {
			return (Globals.erreurPseudoReserve);
		} else if (!ipCorrecte(ip1) || !ipCorrecte(ip2) || !ipCorrecte(ip3) || !ipCorrecte(ip4)) {
			return (Globals.erreurAdresseIP);
		} else {
			return null;
		}
	}

	/**
	 * En paramètre : le booléen pour savoir si la case est cochée et le fichier
	 * de l'utilisateur Retourne un String contenant l'erreur, un null si aucune
	 * erreur
	 */
	public String verification_JSON(boolean selected, File file) {
		if (selected && file != null) {
			String errorJSON = checkFile(file);
			if (errorJSON != null) {
				return errorJSON;
			} else {
				return null;
			}
		} else if (selected) {
			return Globals.erreurChooseFileJSON;
		}
		return null;
	}

	/**
	 * On vérifie que toutes les données du formulaire de connexion sont
	 * valables d'un point de vue syntaxique. Le pseudo doit contenir au moins 3
	 * caractères. L'adresse IP doit comporter 4 nombres entre 0 et 255.
	 */
	public void seConnecter(ActionEvent e) throws Exception {
		String verifErreur = verification_Pseudo_IP(pseudo.getText().trim(), ip1.getText().trim(), ip2.getText().trim(),
				ip3.getText().trim(), ip4.getText().trim());
		String verifErreurJSON = verification_JSON(checkBoxJSON.isSelected(), fileJSON);
		if (verifErreur != null) {
			erreur.setText(verifErreur);
		}  else if (verifErreurJSON != null) {
			erreur.setText(verifErreurJSON);
		} else { 
			try {

				ServeurInterface serveur = connexion(ip1.getText().trim(), ip2.getText().trim(), ip3.getText().trim(),
						ip4.getText().trim());

				Node source = (Node) e.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				Group root = new Group();
				root.getChildren().add(JfxUtils.loadFxml("game.fxml", serveur, client));

				/* Inscription du client sur le serveur
				 * La méthode register retourne un String avec l'erreur si la
				 * connexion n'est pas possible
				 */
				String register = serveur.register(client, pseudo.getText().trim(), checkBoxJSON.isSelected(),fileJSON);

				if (register != null) {
					erreur.setText(register);
				} else {
					client.setPseudo(pseudo.getText().trim());
					setNewIG(stage, root, serveur);

				}

			} catch (ConnectException exc1) {
				erreur.setText(Globals.erreurIPServeur1);
			} catch (ConnectIOException exc2) {
				erreur.setText(Globals.erreurIPServeur2);
			}
		}

	}

	/**
	 * Méthode qui vérifie la syntaxe et le format du fichier JSON
	 * 
	 */
	public String checkFile(File file) {

			ObjectMapper mapper = new ObjectMapper();
			// JSON from file to Object
			try {
				Game g = mapper.readValue(file, Game.class);
				if (!g.getTableauDeBord().getInfoParClient().containsKey(pseudo.getText().trim())){
					return Globals.erreurPlayerNotFoundJSON;
				}
				
			} catch (JsonParseException e) {
				return Globals.erreurFileJSON;
			} catch (JsonMappingException e) {
				return Globals.erreurFileJSON;
			} catch (IOException e) {
				return Globals.erreurFileNotFoundJSON;
			}
			return null;	
		 
	}

	/**
	 * Méthode appelée lors de l'initialisation du contrôleur. On affiche l'IP
	 * du client.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//curseur sur le pseudo
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            pseudo.requestFocus();
	        }
	    });
		
		/*
		 * Chargement du fichier JSON Vérification de la bonne syntaxe du
		 * fichier (méthode checkFile)
		 */
		chargerJSON.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();

				// On définit le filtre des extensions :JSON
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)",
						"*.json");
				fileChooser.getExtensionFilters().add(extFilter);

				// Récupération du stage
				Node source = (Node) e.getSource();
				Stage stage = (Stage) source.getScene().getWindow();

				File file = fileChooser.showOpenDialog(stage);

				if (file != null) {
					pathJSON.setText(file.toString());
					fileJSON = file;
				}
			}
		});

		try {
			client = new Client();
		} catch (Exception e1) {
			Logger.getLogger("Client").log(Level.SEVERE,"L'utilisateur ne peux pas se connecter");
		}
		String ipc;
		try {
			ipc = InetAddress.getLocalHost().toString().split("/")[1];
			ipclient.setText("Mon IP: [" + ipc + "]");
		} catch (UnknownHostException e) {
			ipclient.setText("Impossible de déterminer votre IP.");
		}

	}

}
