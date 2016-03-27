package application.view;

import java.awt.Dimension;
import java.net.InetAddress;

import application.control.ConnexionController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientView extends Application{

	
	@Override
	public void start(Stage primaryStage) throws Exception {
				
		primaryStage.setTitle("Connexion au serveur");
		
		//Recupere les dimensions de l'écran
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();
		
		
		//Récupération de l'ig
		BorderPane root = new BorderPane();
		//configurer add position
		root.getChildren().add(JfxUtils.loadFxmlConnexion("connexion.fxml"));
		Scene scene = new Scene(root,1000,600);
		
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
