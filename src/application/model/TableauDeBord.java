package application.model;

import java.util.ArrayList;
import java.util.HashMap;

public class TableauDeBord {

	private ArrayList<ClientInfo> infoParClient;
	private HashMap<String, Integer> nbActionParCouleur;
	private HashMap<String, Integer> prixActionParCouleur;
	private ArrayList<Chaine> listeChaine;

	public TableauDeBord() {
		this.infoParClient = new ArrayList<ClientInfo>();
		this.nbActionParCouleur.put("Rouge", 25);
		this.nbActionParCouleur.put("Jaune", 25);
		this.nbActionParCouleur.put("Bleu", 25);
		this.nbActionParCouleur.put("Vert", 25);
		this.nbActionParCouleur.put("Brun", 25);
		this.nbActionParCouleur.put("Aqua", 25);
		this.nbActionParCouleur.put("Rose", 25);

		this.prixActionParCouleur.put("Rouge", 200);
		this.prixActionParCouleur.put("Jaune", 200);
		this.prixActionParCouleur.put("Bleu", 200);
		this.prixActionParCouleur.put("Vert", 200);
		this.prixActionParCouleur.put("Brun", 200);
		this.prixActionParCouleur.put("Aqua", 200);
		this.prixActionParCouleur.put("Rose", 200);

		this.listeChaine = new ArrayList<Chaine>();
	}

	public ArrayList<ClientInfo> getInfoParClient() {
		return infoParClient;
	}

	public void setInfoParClient(ArrayList<ClientInfo> infoParClient) {
		this.infoParClient = infoParClient;
	}

	public HashMap<String, Integer> getNbActionParCouleur() {
		return nbActionParCouleur;
	}

	public void setNbActionParCouleur(HashMap<String, Integer> nbActionParCouleur) {
		this.nbActionParCouleur = nbActionParCouleur;
	}

	public HashMap<String, Integer> getPrixActionParCouleur() {
		return prixActionParCouleur;
	}

	public void setPrixActionParCouleur(HashMap<String, Integer> prixActionParCouleur) {
		this.prixActionParCouleur = prixActionParCouleur;
	}

	public void ajouterClient(ClientInfo c) {
		this.infoParClient.add(c);
	}
	
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
