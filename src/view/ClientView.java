package view;

import java.awt.Dimension;
import java.rmi.Naming;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rmi.Client;
import rmi.ServeurInterface;

public class ClientView extends Application {
	
	public ClientView() throws Exception{	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
				
		//Recupere les dimensions de l'écran
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();
		
		//Récupération de l'ig
		Group root = new Group();
		//configurer add position
		root.getChildren().add(JfxUtils.loadFxml("game.fxml"));
		Scene scene = new Scene(root,width-100,height-100);
		
		//Application du code CSS
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		//Mise en place de la Scene et Lancement
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
	public void lancer() {
		launch();
	}
}
