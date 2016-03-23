package application.control;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import application.rmi.Client;
import application.rmi.ClientInterface;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConnexionController implements Initializable {
	
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

	/*
	 * V�rification syntaxique de l'adresse IP. Return true si la syntaxe est
	 * correcte, false sinon.
	 */
	public boolean ipCorrecte(String s) {
		if (s.trim().matches("\\d+") && s.trim().length() < 4 && Integer.parseInt(s.trim()) >= 0 && Integer.parseInt(s.trim()) <= 255)
			return true;
		else
			return false;
	}

	/*
	 * M�thode appel�e quand le client se connecte avec succ�s. 
	 *  On met � jour la sc�ne de l'IG.
	 * 
	 */
	private void setNewIG(ActionEvent e, ServeurInterface serveur) throws RemoteException, Exception {
		Node source = (Node) e.getSource();
		Stage stage = (Stage) source.getScene().getWindow();

		Group root = new Group();
		root.getChildren().add(JfxUtils.loadFxml("game.fxml", serveur, client));
		Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
		stage.setTitle("Acquire");
		stage.setScene(scene);

		/*
		 * Fermeture de l'application quand un client quitte la fen�tre.
		 * On informe le serveur qu'un client a quitt� l'application.
		 */
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
					try {
						serveur.logout(client.getPseudo());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

                Platform.exit();
                System.exit(0);
            }
		});
		
	}

	/*
	 * On v�rifie que toutes les donn�es du formulaire de connexion sont
	 * valables d'un point de vue syntaxique. Le pseudo doit contenir au moins 3
	 * caract�res. L'adresse IP doit comporter 4 nombres entre 0 et 255.
	 */
	public void seConnecter(ActionEvent e) throws Exception {

		if (pseudo.getText().trim().length() < 3 || pseudo.getText().trim().length() > 12) {
			erreur.setText("Le pseudo doit contenir entre 3 et 12 caract�res.");
		} else if (!ipCorrecte(ip1.getText()) || !ipCorrecte(ip2.getText()) || !ipCorrecte(ip3.getText())
				|| !ipCorrecte(ip4.getText())) {
			erreur.setText("L'adresse IP n'est pas bonne.");
		} else {
			try {
				ServeurInterface serveur = (ServeurInterface) Naming.lookup("rmi://" + ip1.getText().trim() + "."
						+ ip2.getText().trim() + "." + ip3.getText().trim() + "." + ip4.getText().trim() + ":42000/ACQUIRE");


				
				//Inscription du client sur le serveur
				boolean pseudoDispo = serveur.register(client, pseudo.getText());
				if (pseudoDispo) {
					client.setPseudo(pseudo.getText());
					setNewIG(e, serveur);

				} else {
					erreur.setText("Le pseudo est utilis� par un autre joueur.");
				}
			} catch (ConnectException exc1) {
				erreur.setText("L'adresse du serveur n'est pas bonne ou le serveur n'a pas �t� lanc�.");
			} catch (ConnectIOException exc2) {
				erreur.setText("L'adresse du serveur n'est pas bonne.");
			}
		}

	}

	/*
	 * M�thode appel�e lors de l'initialisation du contr�leur. On affiche l'IP
	 * du client.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			client = new Client();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ipc;
		try {
			ipc = InetAddress.getLocalHost().toString().split("/")[1];
			ipclient.setText("Mon IP: [" + ipc + "]");
		} catch (UnknownHostException e) {
			ipclient.setText("Impossible de d�terminer votre IP.");
		}

	}

}
