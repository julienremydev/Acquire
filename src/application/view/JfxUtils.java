package application.view;

import java.io.IOException;
import java.rmi.Naming;

import application.control.PlateauController;
import application.rmi.ServeurInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class JfxUtils {
 
    public static Node loadFxml(String fxml) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        ServeurInterface serveur = (ServeurInterface) Naming.lookup("rmi://127.0.0.1:42000/ABC");
        try {
            loader.setLocation(JfxUtils.class.getResource(fxml));
            Node root = (Node) loader.load(JfxUtils.class.getResource(fxml).openStream());
            ((PlateauController)loader.getController()).setServeur(serveur);
            return root;
        } catch (IOException e) {
            throw new IllegalStateException("cannot load FXML screen", e);
        }
    }
}