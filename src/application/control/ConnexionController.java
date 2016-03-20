package application.control;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import application.rmi.Client;
import application.rmi.ClientInterface;
import application.rmi.ServeurInterface;
import application.view.JfxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnexionController implements Initializable {

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

	/*
	 * Vérification syntaxique de l'adresse IP. Return true si la syntaxe est
	 * correcte, false sinon.
	 */
	public boolean ipCorrect(String s) {
		if (s.matches("\\d+") && Integer.parseInt(s) >= 0 && Integer.parseInt(s) <= 255)
			return true;
		else
			return false;
	}

	/*
	 * Méthode appelée quand le client se connecte avec succès. 
	 *  On met à jour la scène de l'IG.
	 * 
	 */
	private void setNewIG(ActionEvent e, ServeurInterface serveur, ClientInterface client) throws RemoteException, Exception {
		Node source = (Node) e.getSource();
		Stage stage = (Stage) source.getScene().getWindow();

		Group root = new Group();
		root.getChildren().add(JfxUtils.loadFxml("game.fxml", serveur, client));
		Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
		stage.setTitle("Acquire");
		stage.setScene(scene);

	}

	/*
	 * On vérifie que toutes les données du formulaire de connexion sont
	 * valables d'un point de vue syntaxique. Le pseudo doit contenir au moins 3
	 * caractères. L'adresse IP doit comporter 4 nombres entre 0 et 255.
	 */
	public void seConnecter(ActionEvent e) throws Exception {

		if (pseudo.getText().trim().length() < 3) {
			erreur.setText("Le pseudo doit contenir au moins 3 caractères.");
		} else if (!ipCorrect(ip1.getText()) || !ipCorrect(ip2.getText()) || !ipCorrect(ip3.getText())
				|| !ipCorrect(ip4.getText())) {
			erreur.setText("L'adresse IP n'est pas bonne.");
		} else {
			try {
				ServeurInterface serveur = (ServeurInterface) Naming.lookup("rmi://" + ip1.getText() + "."
						+ ip2.getText() + "." + ip3.getText() + "." + ip4.getText() + ":42000/ACQUIRE");


				//Inscription du client sur le serveur
				ClientInterface client = serveur.register(pseudo.getText());
				if (client != null) {
					setNewIG(e, serveur, client);

				} else {
					erreur.setText("Le pseudo est utilisé par un autre joueur.");
				}
			} catch (ConnectException exc) {
				erreur.setText("L'adresse du serveur n'est pas bonne ou le serveur n'a pas été lancé.");
			}
		}

	}

	/*
	 * Méthode appelée lors de l'initialisation du contrôleur. On affiche l'IP
	 * du client.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String ipc;
		try {
			ipc = InetAddress.getLocalHost().toString().split("/")[1];
			ipclient.setText("Mon IP: [" + ipc + "]");
		} catch (UnknownHostException e) {
			ipclient.setText("Impossible de déterminer votre IP.");
		}

	}

}
