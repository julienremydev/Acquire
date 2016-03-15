package application;
	
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.stage.Stage;
import rmi.Client;
import rmi.ServeurInterface;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws NotBoundException, IOException {
		ServeurInterface serveur= (ServeurInterface) Naming.lookup("rmi://127.0.0.1:42000/ABC");
		
		//Nouvelle instance de client qui va lancer l'application
		Client client = new Client(serveur);
		
		
		//La vue de la scene est instanciée dans la classe Client
		Scene scene = new Scene(client.getView(),400,400);
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
        
	}
	

	public void lancer() {
		launch();
		
	}
}
