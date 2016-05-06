package application.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class ClientView extends Application{

	
	@Override
	public void start(Stage primaryStage) throws Exception {
				
		primaryStage.setTitle("Connexion au serveur");
		
		
		
		//R�cup�ration de l'ig
		BorderPane root = new BorderPane();
		//configurer add position
		root.getChildren().add(JfxUtils.loadFxmlConnexion("connexion.fxml"));
		Scene scene = new Scene(root,1000,700);
		
		//Mise en place de la Scene et Lancement
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
		
	}
	
	public void lancer() {
		launch();
	}
}
