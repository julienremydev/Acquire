package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
	
import application.control.PlateauController;

public class TableauDeBord implements Serializable{
	private static final long serialVersionUID = -3628602150383225255L;
	
	public static ArrayList<ClientInfo> infoParClient;
	public static ArrayList<Chaine> listeChaine;


	/**
	 * Constructeur Tableau de bord
	 */
	public TableauDeBord(ArrayList<Chaine> listeDeChaine) {
		this.infoParClient = new ArrayList<ClientInfo>();
		this.listeChaine=listeDeChaine;
	}
	/**
	 * Retourne la liste des clients
	 * @return
	 */
	public ArrayList<ClientInfo> getInfoParClient() {
		return infoParClient;
	}
	/**
	 * D�fini la nouvelle liste d'infoParClient avec la lister en param�tre
	 * @param infoParClient
	 */
	public void setInfoParClient(ArrayList<ClientInfo> infoParClient) {
		this.infoParClient = infoParClient;
	}
	/**
	 * Retourne la liste des chaine
	 * @return
	 */
	public ArrayList<Chaine> getListeTypeChaine() {
		return listeChaine;
	}
	/**
	 * Permet de d�finir la liste de chaines
	 * @param listeTypeChaine
	 */
	public void setListeTypeChaine(ArrayList<Chaine> listeTypeChaine) {
		this.listeChaine = listeTypeChaine;
	}
	/**
	 * Ajout d'un client dans la liste de client
	 * @param c
	 */
	public void ajouterClient(ClientInfo c) {
		this.infoParClient.add(c);
	}
	/**
	 * Retourne la chaine si on passe correspondante � son id
	 * @param id
	 * @return
	 */
	public Chaine getChaineById(int id){
		Chaine toReturn = null;
		for(Chaine c:listeChaine){
			if(c.getNomChaine().getNumero()==id){
				toReturn=c;
				
			}
		}
		return toReturn;
	}
	/**
	 * Creation d'une nouvelle chaine, Changement de l'etat des hotels en cha�ne, ajout de la chaine, � la liste de cha�ne.
	 * @param listeHotels
	 * @param nomChaine
	 */
//	public static void creationChaine(ArrayList<Case> listeHotels, TypeChaine nomChaine)
//	{
//		Chaine nouvelleChaine = new Chaine(nomChaine);
//		for(Case hotelChaine : listeHotels)
//		{
//			nouvelleChaine.addCase(hotelChaine);
//			hotelChaine.setEtat(nomChaine.getNumero());
//		}
//		listeChaine.add(nouvelleChaine);
//		PlateauController.nouvelleChaine(nouvelleChaine);
//	}

}
