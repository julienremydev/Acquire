package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import application.control.PlateauController;
import application.globale.Globals;

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
	 * Défini la nouvelle liste d'infoParClient avec la lister en paramètre
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
	 * Permet de définir la liste de chaines
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
	 * Retourne la chaine si on passe correspondante à son id
	 * @param id
	 * @return
	 */
	public Chaine getChaineById(int id){
		for(Chaine c:listeChaine){
			if(c.getNomChaine().getNumero()==id){
				return c;
			}
		}
		return null;
	}


	public boolean actionAvailableForPlayer(String pseudo){
		if (infoParClient.containsKey(pseudo)){
			int cash = infoParClient.get(pseudo).getCash();
			for(Chaine chaine : listeChaine){
				if ( !chaine.chaineDisponible() && chaine.getNbActionRestante() > 0 && TypeChaine.prixAction(chaine.getNomChaine(), Globals.nbActionTotal-chaine.getNbActionRestante()) < cash){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean actionAvailableForPlayer(String pseudo, int idChaine){
		if (infoParClient.containsKey(pseudo)){
			int cash = infoParClient.get(pseudo).getCash();
			if ( !getChaineById(idChaine).chaineDisponible() && getChaineById(idChaine).getNbActionRestante() > 0 && TypeChaine.prixAction(getChaineById(idChaine).getNomChaine(), Globals.nbActionTotal-getChaineById(idChaine).getNbActionRestante()) < cash)
				return true;
		}
		return false;
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
 * methode qui permet au joueur d acheter des actions dans plusieurs chaine a la fois
 * @param nomJoueur
 * @param actionAAcheter
 */
public void achatActions(String nomJoueur, HashMap<TypeChaine, Integer> actionAAcheter){
	for(int i=0; i<listeChaine.size(); i++){
		Integer nbAction = actionAAcheter.get(listeChaine.get(i).getTypeChaine());
		if(nbAction != null){
			achatActionJoueur((int)nbAction, nomJoueur, listeChaine.get(i).getTypeChaine());
		}
	}
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

	if (indexChaine != -1 && joueur!=null){
		Integer nbActionJoueur = joueur.getActionParChaine().get(tc);
		if (nb < 0 || nbActionJoueur<=0){
			nb = 0;
			res=0;
		}else if (nb > nbActionJoueur){
			res = nbActionJoueur;
		} else {
			res = nb;
		}

		joueur.getActionParChaine().put(tc, nbActionJoueur - res);
		this.listeChaine.get(indexChaine).setNbActionRestante(this.listeChaine.get(indexChaine).getNbActionRestante() + res);;
	}

	return res;
}

/**
 * Méthode permettant d echanger des actions d une chaine par une autre
 * @param nb d action que le joueur souhaite echanger
 * @param chaineVendAction chaine dont le joueur veux vendre ses actions
 * @param chaineAchatAction chaine dont le joueur veux acheter des actions
 * @param nomJoueur nom du joueur qui faire l echange
 * @return le nombre d action que le joueur obtient de chaineAchatAction
 */
public int echangeAction(int nb, TypeChaine chaineVendAction, TypeChaine chaineAchatAction, String nomJoueur){
	int res = 0;

	if(nb % 2 == 0){
		res = nb/2;
	}else{
		nb = nb-1;
		res = nb/2;
	}
	
	int indexChaineAchatAction = -1;
	for(int i=0; i<listeChaine.size(); i++){
		if (listeChaine.get(i).getTypeChaine().equals(chaineAchatAction)){
			indexChaineAchatAction = i;
		}
	}
	
	if(listeChaine.get(indexChaineAchatAction).getNbActionRestante()+res >= 0 && infoParClient.get(nomJoueur).getActionParChaine().get(chaineVendAction).intValue() >= nb){
		res = vendActionJoueur(nb, nomJoueur, chaineVendAction);
		res = res/2;
	}else{
		res = 0;
	}
	
	achatActionJoueur(res, nomJoueur, chaineAchatAction);
	
	return res;
}
}
