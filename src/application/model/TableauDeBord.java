package application.model;

import java.util.ArrayList;
import java.util.HashMap;

public class TableauDeBord {

private ArrayList<ClientInfo> infoParClient;
private HashMap<String,Integer> nbActionParCouleur;
private HashMap<String,Integer> prixActionParCouleur;

public TableauDeBord(){
	infoParClient=new ArrayList<ClientInfo>();
	nbActionParCouleur.put("Rouge",25);
	nbActionParCouleur.put("Jaune",25);
	nbActionParCouleur.put("Bleu",25);
	nbActionParCouleur.put("Vert",25);
	nbActionParCouleur.put("Brun",25);
	nbActionParCouleur.put("Aqua",25);
	nbActionParCouleur.put("Rose",25);

	
	prixActionParCouleur.put("Rouge",200);
	prixActionParCouleur.put("Jaune",200);
	prixActionParCouleur.put("Bleu",200);
	prixActionParCouleur.put("Vert",200);
	prixActionParCouleur.put("Brun",200);
	prixActionParCouleur.put("Aqua",200);
	prixActionParCouleur.put("Rose",200);
	
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

public void ajouterClient(ClientInfo c){
	this.infoParClient.add(c);
}

}
