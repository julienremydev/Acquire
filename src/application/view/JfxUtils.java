package application.view;

import java.io.IOException;
import application.control.PlateauController;
import application.rmi.Client;
import application.rmi.ServeurInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class JfxUtils {
 
	/**
	 * Charge le ficheir fxml
	 * @param fxml
	 * @param serveur
	 * @param client
	 * @return
	 * @throws Exception
	 */
    public static Node loadFxml(String fxml, ServeurInterface serveur, Client client) throws Exception{
        FXMLLoader loader = new FXMLLoader();

        try {
            loader.setLocation(JfxUtils.class.getResource(fxml));
            Node root = (Node) loader.load(JfxUtils.class.getResource(fxml).openStream());
            ((PlateauController)loader.getController()).setClient(client,serveur);
            return root;
        } catch (IOException e) {
            throw new IllegalStateException("cannot load FXML screen", e);
        }
    }
    /**
     * Charge le fichier fxml 
     * @param fxml
     * @return
     * @throws Exception
     */
    public static Node loadFxmlConnexion(String fxml) throws Exception{
        FXMLLoader loader = new FXMLLoader();

        try {
            loader.setLocation(JfxUtils.class.getResource(fxml));
            Node root = (Node) loader.load(JfxUtils.class.getResource(fxml).openStream());
            return root;
        } catch (IOException e) {
            throw new IllegalStateException("cannot load FXML screen", e);
        }
    }
}