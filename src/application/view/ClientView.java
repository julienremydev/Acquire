package application.view;

import java.awt.Dimension;
import java.rmi.Naming;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.rmi.Client;
import application.rmi.ServeurInterface;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientView  {
	
	private ServeurInterface serveur;
	private Stage stage;
	
	public ClientView(ServeurInterface serveur) throws Exception{
		this.serveur=serveur;
		this.stage = new Stage();
		//Recupere les dimensions de l'écran
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();
		
		//Récupération de l'ig
		Group root = new Group();
		//configurer add position
		root.getChildren().add(JfxUtils.loadFxml("game.fxml", serveur));
		Scene scene = new Scene(root,width-100,height-100);
		
		//Application du code CSS
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		//Mise en place de la Scene et Lancement
		stage.setScene(scene);
		stage.sizeToScene();
	}


	public Stage getStage(){
		return stage;
	}
	
}
