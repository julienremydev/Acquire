package application.control;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import application.globale.Globals;
import application.rmi.Client;
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
	 * Vérification syntaxique de l'adresse IP. Return true si la syntaxe est
	 * correcte, false sinon.
	 */
	public boolean ipCorrecte(String s) {
		if (s.trim().matches("\\d+") && s.trim().length() < 4 && Integer.parseInt(s.trim()) >= 0 && Integer.parseInt(s.trim()) <= 255)
			return true;
		else
			return false;
	}

	/*
	 * Méthode appelée quand le client se connecte avec succès. 
	 *  On met à jour la scène de l'IG.
	 * 
	 */
	private void setNewIG(Stage stage, Group root, ServeurInterface serveur) throws RemoteException, Exception {
		
		Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
		stage.setTitle("Acquire");
		stage.setScene(scene);

		/*
		 * Fermeture de l'application quand un client quitte la fenêtre.
		 * On informe le serveur qu'un client a quitté l'application.
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

	public ServeurInterface connexion (String ip1, String ip2, String ip3, String ip4) throws Exception{
		ServeurInterface serveur = (ServeurInterface) Naming.lookup("rmi://" + ip1 + "."
				+ ip2 + "." + ip3 + "." + ip4 + ":42000/ACQUIRE");
		return serveur;
	}
	public String verification_Pseudo_IP(String pseudo, String ip1, String ip2, String ip3, String ip4){
		if (pseudo.length() < 3 || pseudo.length() > 12) {
			return(Globals.erreurTaillePseudo);
		} else if (pseudo.equals("Serveur")){
			return(Globals.erreurPseudoReserve);
		} else if (!ipCorrecte(ip1) || !ipCorrecte(ip2) || !ipCorrecte(ip3)
				|| !ipCorrecte(ip4)) {
			return(Globals.erreurAdresseIP);
		}else{
			return null;
		}
	}
	/*
	 * On vérifie que toutes les données du formulaire de connexion sont
	 * valables d'un point de vue syntaxique. Le pseudo doit contenir au moins 3
	 * caractères. L'adresse IP doit comporter 4 nombres entre 0 et 255.
	 */
	public void seConnecter(ActionEvent e) throws Exception {
		String verifErreur = verification_Pseudo_IP(pseudo.getText().trim(), ip1.getText().trim(),ip2.getText().trim(),ip3.getText().trim(),ip4.getText().trim());
		if (verifErreur != null){
			erreur.setText(verifErreur);
		}
		else{
			try {
				
				ServeurInterface serveur = connexion(ip1.getText().trim(),ip2.getText().trim(),ip3.getText().trim(),ip4.getText().trim());
				
				Node source = (Node) e.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				Group root = new Group();
				root.getChildren().add(JfxUtils.loadFxml("game.fxml", serveur, client));

				//Inscription du client sur le serveur
				//La méthode register retourne un String avec l'erreur si la connexion n'est pas possible
				String register = serveur.register(client, pseudo.getText());
				
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

	/*
	 * Méthode appelée lors de l'initialisation du contrôleur. On affiche l'IP
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
			ipclient.setText("Impossible de déterminer votre IP.");
		}

	}

}
