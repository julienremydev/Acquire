package application.view;

import java.awt.Dimension;
import java.net.InetAddress;

import application.control.ConnexionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientView extends Application{

	
	@Override
	public void start(Stage primaryStage) throws Exception {
				
		primaryStage.setTitle("Connexion au serveur");
		
		//Recupere les dimensions de l'�cran
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();
		
		
		//R�cup�ration de l'ig
		BorderPane root = new BorderPane();
		//configurer add position
		root.getChildren().add(JfxUtils.loadFxmlConnexion("connexion.fxml"));
		Scene scene = new Scene(root,width-100,height-100);
		
		//Application du code CSS
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		//Mise en place de la Scene et Lancement
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
				
				
				
				
				
				
				
				
		//FXMLLoader loader = new FXMLLoader();
	//	loader.setLocation(ClientViewConnexion.class.getResource("connexion.fxml"));
	//	BorderPane root = (BorderPane) loader.load();
		// BorderPane page = (BorderPane)loader.load(ClientViewConnexion.class.getResource("connexion.fxml"));
		 
       
     // Give the controller access to the main app.
      //  controller = loader.getController();
       // controller.setView(this);
		//Scene scene = new Scene(root,700,height-100);
		
		//Application du code CSS
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		//Mise en place de la Scene et Lancement
		//primaryStage.setScene(scene);
		//primaryStage.sizeToScene();
		//primaryStage.show();
	}
	
	public void lancer() {
		launch();
	}
}
