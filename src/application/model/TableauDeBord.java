package application.model;

import java.util.ArrayList;
import java.util.HashMap;

public class TableauDeBord {

	private ArrayList<ClientInfo> infoParClient;
	private ArrayList<Chaine> listeChaine;

	public TableauDeBord() {
		this.infoParClient = new ArrayList<ClientInfo>();
		this.listeChaine=new ArrayList<Chaine>();
	}
	public ArrayList<ClientInfo> getInfoParClient() {
		return infoParClient;
	}
	public void setInfoParClient(ArrayList<ClientInfo> infoParClient) {
		this.infoParClient = infoParClient;
	}
	public ArrayList<Chaine> getListeTypeChaine() {
		return listeChaine;
	}
	public void setListeTypeChaine(ArrayList<Chaine> listeTypeChaine) {
		this.listeChaine = listeTypeChaine;
	}
	
	/**
	 * Qui permets d'ajouter un client dans le tableauDeBord
	 * @param c
	 */
	public void ajouterClient(ClientInfo c) {
		this.infoParClient.add(c);
	}
	
	
	/**
	 * Retourne la chaine à partir de son id
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
