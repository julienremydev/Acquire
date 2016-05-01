package application.globale;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import application.model.ClientInfo;

public class Globals {
	/*
	 * Constantes -> Erreurs 
	 */
	public final static String erreurTaillePseudo = "Le pseudo doit contenir entre 3 et 12 caractères.";
	public final static String erreurPseudoReserve = "Le pseudo 'Serveur' ne peut pas être utilisé par un joueur.";
	public final static String erreurAdresseIP = "L'adresse IP n'est pas bonne.";
	public final static String erreurPseudoUtilise = "Le pseudo est déjà utilisé.";
	public final static String erreurPartieComplete = "La partie est complète.";
	public final static String erreurPartieComplete2 = "La place restante est reservée au chef de la partie.";
	public final static String erreurIPServeur1 = "L'adresse du serveur n'est pas bonne ou le serveur n'a pas été lancé.";
	public final static String erreurIPServeur2 = "L'adresse du serveur n'est pas bonne.";
	public final static String erreurFileJSON = "Le fichier JSON a été corrompu ou n'est pas du bon format.";
	public final static String erreurChooseFileJSON = "Vous devez sélectionner un fichier au format JSON, ou décocher la case pour vous connecter.";
	public final static String erreurFileNotFoundJSON = "Le chemin du fichier n'est pas correct.";
	public final static String erreurChargementJSONimpossible = "Chargement JSON impossible. Une partie est en cours.";
	public final static String erreurForbiddenPlayer = "Vous n'êtes pas autorisé à rejoindre cette partie. \n Attendez la fin de la partie en cours ou vérifiez la syntaxe de votre pseudo si vous en faites partie.";

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
	public final static int choixActionCreationChaine = 0;
	public final static int choixActionFusionSameSizeChaine = 1;
	public final static int choixActionFusionEchangeAchatVente = 2;
	
	
	/**
	 * Methode de tri d'une map (utilisé pour l'ordre des joueurs selon la pioche)
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
	
	/**
	 * Methode de tri d'une map (utilisé pour l'ordre des joueurs selon la pioche)
	 * @param map
	 * @return
	 */
	public static ArrayList<ClientInfo> 
	sortByValueAction( HashMap<ClientInfo, Integer> map ) {
		ArrayList<ClientInfo> list = new ArrayList<ClientInfo>();
		for (ClientInfo c : map.keySet()) {
			if (map.get(c)==0) {
				list.add(c);
			}
		}
		for (ClientInfo c : list) {
			map.remove(c);
		}
		ArrayList<ClientInfo> liste_clients = new ArrayList<ClientInfo>();
		Stream<Map.Entry<ClientInfo, Integer>> st = map.entrySet().stream();
		st.sorted( Map.Entry.comparingByValue() )
		.forEachOrdered(e -> liste_clients.add(e.getKey()) );
		Collections.reverse(liste_clients);
		
		return liste_clients;
	}
	
	public static int getResultat(int param) {
		int modulo = param%100;
		if (modulo==0) {
			return param;
		}
		if (modulo<50) {
			return ((param/100))*100;
		}
		else {
			return ((param/100)+1)*100;
		}
	}
}
