package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import application.control.PlateauController;

public class TableauDeBord implements Serializable{
	private static final long serialVersionUID = -3628602150383225255L;

	private HashMap<String,ClientInfo> infoParClient;


	private ArrayList<Chaine> listeChaine;

	/**
	 * Constructeur Tableau de bord
	 */

	public TableauDeBord() {
		this.infoParClient = new HashMap<String,ClientInfo>();
		this.listeChaine= new ArrayList<Chaine>();

	}
	/**
	 * Retourne la liste des clients
	 * @return
	 */
	public HashMap<String,ClientInfo> getInfoParClient() {
		return infoParClient;
	}
	/**
	 * D�fini la nouvelle liste d'infoParClient avec la lister en param�tre
	 * @param infoParClient
	 */
	public void setInfoParClient(HashMap<String,ClientInfo> infoParClient) {
		this.infoParClient = infoParClient;
	}
	/**
	 * Retourne la liste des chaine
	 * @return
	 */
	public ArrayList<Chaine> getListeTypeChaine() {
		return listeChaine;
	}
	public ArrayList<Chaine> getListeChaine() {
		return listeChaine;
	}
	public void setListeChaine(ArrayList<Chaine> listeChaine) {
		this.listeChaine = listeChaine;
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
		this.infoParClient.put(c.getPseudo(),c);
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
	 * fonction permettant au joueur d acheter des actions et met a jour le nombre d action restante
	 * @param nb : nombre d action voulant etre acheter par le joueur
	 * @param nomJoueur : nom du joueur qui achete
	 * @param tc : type chaine de la chaine
	 * @return nombre effectivement acheter
	 */
	public int achatActionJoueur(int nb, String nomJoueur, TypeChaine tc){
		int indexChaine = -1;
		ClientInfo joueur = null;


		for(int i=0; i<listeChaine.size(); i++){
			if (listeChaine.get(i).getNomChaine().equals(tc)){
				indexChaine = i;
			}
		}
		if (infoParClient.containsKey(nomJoueur)){
			joueur = infoParClient.get(nomJoueur);
		}

	int res = 0;

	if (indexChaine != -1 && this.infoParClient.get(nomJoueur)!=null){
		if(nb < 0 || this.listeChaine.get(indexChaine).getNbActionRestante() == 0){
			nb = 0;
		}

		if(this.listeChaine.get(indexChaine).getNbActionRestante()-nb < 0){ // on ne peut pas avoir un nombre daction restante negatif
			res = this.listeChaine.get(indexChaine).getNbActionRestante();
			this.listeChaine.get(indexChaine).setNbActionRestante(0);
		} else {
			res = nb;
			this.listeChaine.get(indexChaine).setNbActionRestante(this.listeChaine.get(indexChaine).getNbActionRestante()-nb);
		}

		if(res != 0){
			joueur.getActionParChaine().put(tc, res + joueur.getActionParChaine().get(tc));
		}
	}
	return res;
}

/**
 * fonction permettant au joueur de vendre des actions et met a jour le nombre d action restante
 * @param nb : nombre d action voulant etre vendue par le joueur
 * @param nomJoueur : nom du joueur qui vend
 * @param tc : type de la chaine
 * @return nombre effectivement vendue
 */
public int vendActionJoueur(int nb, String nomJoueur, TypeChaine tc){
	int indexChaine = -1;
	ClientInfo joueur = null;

	for(int i=0; i<listeChaine.size(); i++){
		if (listeChaine.get(i).getNomChaine().equals(tc)){
			indexChaine = i;
		}
	}
	if (infoParClient.containsKey(nomJoueur)){
		joueur = infoParClient.get(nomJoueur);
	}

	int res = 0;

	if (indexChaine != -1 && this.infoParClient.get(nomJoueur)!=null){
		Integer nbActionJoueur = this.infoParClient.get(nomJoueur).getActionParChaine().get(tc);
		if (nb < 0 || nbActionJoueur<=0){
			nb = 0;
			res=0;
		}else if (nb > nbActionJoueur){
			res = nbActionJoueur;
		} else {
			res = nb;
		}

		this.infoParClient.get(nomJoueur).getActionParChaine().put(tc, nbActionJoueur - res);
		this.listeChaine.get(indexChaine).setNbActionRestante(this.listeChaine.get(indexChaine).getNbActionRestante() + res);;
	}

	return res;
}


}
