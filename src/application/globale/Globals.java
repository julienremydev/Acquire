package application.globale;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Globals {
	/*
	 * Constantes -> Erreurs 
	 */
	public final static String erreurTaillePseudo = "Le pseudo doit contenir entre 3 et 12 caract�res.";
	public final static String erreurPseudoReserve = "Le pseudo 'Serveur' ne peut pas �tre utilis� par un joueur.";
	public final static String erreurAdresseIP = "L'adresse IP n'est pas bonne.";
	public final static String erreurPseudoUtilise = "Le pseudo est d�j� utilis�.";
	public final static String erreurPartieComplete = "La partie est compl�te.";
	public final static String erreurPartieComplete2 = "La place restante est reserv�e au chef de la partie.";
	public final static String erreurIPServeur1 = "L'adresse du serveur n'est pas bonne ou le serveur n'a pas �t� lanc�.";
	public final static String erreurIPServeur2 = "L'adresse du serveur n'est pas bonne.";
	public final static String erreurFileJSON = "Le fichier JSON a �t� corrompu ou n'est pas du bon format.";
	public final static String erreurChooseFileJSON = "Vous devez s�lectionner un fichier au format JSON, ou d�cocher la case pour vous connecter.";
	public final static String erreurFileNotFoundJSON = "Le chemin du fichier n'est pas correct.";
	public final static String erreurChargementJSONimpossible = "Chargement JSON impossible. Une partie est en cours.";
	public final static String erreurForbiddenPlayer = "Vous n'�tes pas autoris� � rejoindre cette partie. \n Attendez la fin de la partie en cours ou v�rifiez la syntaxe de votre pseudo si vous en faites partie.";

	/*
	 * Constantes -> Couleurs des boutons du plateau
	 */
	public final static String colorCasePlayer = "-fx-background-color: #FFDE5E;";
	public final static String colorCaseGrey = "-fx-background-color: #C1BAB2;";
	public final static String colorCaseEmpty = "-fx-background-color: #FFFFFF;";
	public final static String colorCaseHotel = "-fx-background-color: #000000;";
	
	/*
	 * Constantes -> Variables du Game 
	 */
	public final static int nombre_joueurs_max = 6;
	public final static int nbActionTotal = 25;
	
	
	
	/**
	 * Methode de tri d'une map (utilis� pour l'ordre des joueurs selon la pioche)
	 * @param map
	 * @return
	 */
	public static <String extends Comparable<? super String>> HashMap<String, String> 
	sortByValue( HashMap<String, String> map ) {
		HashMap<String, String> result = new LinkedHashMap<>();
		Stream<Map.Entry<String, String>> st = map.entrySet().stream();

		st.sorted( Map.Entry.comparingByValue() )
		.forEachOrdered( e -> result.put(e.getKey(), e.getValue()) );

		return result;
	}
}
