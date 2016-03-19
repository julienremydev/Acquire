package application.control;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.Naming;

import application.rmi.Client;
import application.rmi.ClientInterface;
import application.rmi.ServeurInterface;
import application.view.ClientView;
import application.view.ClientViewConnexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ConnexionController {

	private ServeurInterface serveur;
	private ClientViewConnexion client;

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

	public boolean ipCorrect(String s) {
		if (s.matches("\\d+") && Integer.parseInt(s) >= 0 && Integer.parseInt(s) <= 255)
			return true;
		else
			return false;
	}

	public void seConnecter(ActionEvent e) throws Exception {

		/*
		 * On vérifie que toutes les données du formulaire de connexion sont
		 * valables d'un point de vue syntaxique. Le pseudo doit contenir au
		 * moins 3 caractères. L'adresse IP doit comporter 4 nombres entre 0 et
		 * 255.
		 */

		if (pseudo.getText().trim().length() < 3) {
			erreur.setText("Le pseudo doit contenir au moins 3 caractères.");
		} else if (!ipCorrect(ip1.getText()) && !ipCorrect(ip2.getText()) && !ipCorrect(ip3.getText())
				&& !ipCorrect(ip4.getText())) {
			erreur.setText("L'adresse IP n'est pas bonne.");
		} else {
			try {
				ServeurInterface serveur = (ServeurInterface) Naming.lookup("rmi://" + ip1.getText() + "."
						+ ip2.getText() + "." + ip3.getText() + "." + ip4.getText() + ":42000/ACQUIRE");
				
				
				boolean pseudoDispo = serveur.pseudoDisponible(pseudo.getText());
				
				//Le pseudo n'est pas utilisé, on peut connecter le joueur.
				if ( pseudoDispo ){
					
				    Node  source = (Node)  e.getSource(); 
				    Stage stage  = (Stage) source.getScene().getWindow();
				    stage.close();
				    
				    //Inscription du client sur le serveur
				    serveur.register(new Client(pseudo.getText()));
				    
				    //Nouvelle ig
				    ClientView view = new ClientView(serveur);
				    stage = view.getStage(); 
				    stage.show();
	
				}else{
					erreur.setText("Le pseudo est utilisé par un autre joueur.");
				}
			} catch (ConnectException exc) {
				erreur.setText("L'adresse du serveur n'est pas bonne ou le serveur n'a pas été lancé.");
			}
		}

	}

	public void setView(ClientViewConnexion clientViewConnexion) throws UnknownHostException {
		this.client = clientViewConnexion;
		String ipc = InetAddress.getLocalHost().toString().split("/")[1];
		this.ipclient.setText("Mon IP: [" + ipc + "]");

	}

	public ClientViewConnexion getClient() {
		return client;
	}

}
