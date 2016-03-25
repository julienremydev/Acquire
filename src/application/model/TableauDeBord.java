package application.model;

import java.util.ArrayList;
import java.util.HashMap;

public class TableauDeBord {

	private ArrayList<ClientInfo> infoParClient;
	private ArrayList<Chaine> listeChaine;

	/**
	 * Constructeur Tableau de bord
	 */
	public TableauDeBord() {
		this.infoParClient = new ArrayList<ClientInfo>();
		this.listeChaine=new ArrayList<Chaine>();
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

}
