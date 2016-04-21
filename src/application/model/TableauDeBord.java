package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
	
import application.control.PlateauController;

public class TableauDeBord implements Serializable{
	private static final long serialVersionUID = -3628602150383225255L;
	
	private ArrayList<ClientInfo> infoParClient;
	private ArrayList<Chaine> listeChaine;

	/**
	 * Constructeur Tableau de bord
	 */

	public TableauDeBord() {
		this.infoParClient = new ArrayList<ClientInfo>();

	}
	/**
	 * Retourne la liste des clients
	 * @return
	 */
	public ArrayList<ClientInfo> getInfoParClient() {
		return infoParClient;
	}
	/**
	 * Défini la nouvelle liste d'infoParClient avec la lister en paramètre
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
		this.infoParClient.add(c);
	}
	/**
	 * Retourne la chaine si on passe correspondante à son id
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
		int indexJoueur = -1;
		
		for(int i=0; i<listeChaine.size(); i++){
			if (listeChaine.get(i).getNomChaine().equals(tc)){
				indexChaine = i;
			}
		}
		for(int i=0; i<infoParClient.size(); i++){
			if (infoParClient.get(i).getPseudo() == nomJoueur){
				indexJoueur = i;
			}
		}
		int res = 0;
		
		if (indexChaine != -1 && indexJoueur != -1){
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
				if(this.infoParClient.get(indexJoueur).getActionParChaine().containsKey(tc)){
					this.infoParClient.get(indexJoueur).getActionParChaine().put(tc, res + this.infoParClient.get(indexJoueur).getActionParChaine().get(tc));
				}else{
					this.infoParClient.get(indexJoueur).getActionParChaine().put(tc, res);			
				}			
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
		int indexJoueur = -1;
		
		for(int i=0; i<listeChaine.size(); i++){
			if (listeChaine.get(i).getNomChaine().equals(tc)){
				indexChaine = i;
			}
		}
		for(int i=0; i<infoParClient.size(); i++){
			if (infoParClient.get(i).getPseudo() == nomJoueur){
				indexJoueur = i;
			}
		}
		
		int res = 0;
		
		if (indexChaine != -1 && indexJoueur != -1){
			boolean joueurExiste = this.infoParClient.get(indexJoueur).getActionParChaine().containsKey(tc);
			if (nb < 0 || !joueurExiste){
				nb = 0;
			}
			
			if (joueurExiste && nb > this.infoParClient.get(indexJoueur).getActionParChaine().get(tc)){
				res = this.infoParClient.get(indexJoueur).getActionParChaine().get(tc);
			}
			
			if(this.listeChaine.get(indexChaine).getNbActionRestante()+nb > Chaine.getNbactiontotal()){ // on ne peut pas avoir plus de 25 action
				res = Chaine.getNbactiontotal()-this.listeChaine.get(indexChaine).getNbActionRestante();
				this.listeChaine.get(indexChaine).setNbActionRestante(Chaine.getNbactiontotal());
			} else {
				res=nb;
				this.listeChaine.get(indexChaine).setNbActionRestante(this.listeChaine.get(indexChaine).getNbActionRestante()+nb);
			}
			
			if(joueurExiste){
				if(this.infoParClient.get(indexJoueur).getActionParChaine().get(tc) - res > 0){
					this.infoParClient.get(indexJoueur).getActionParChaine().put(tc, this.infoParClient.get(indexJoueur).getActionParChaine().get(tc) - res);
					
				}else{
					this.infoParClient.get(indexJoueur).getActionParChaine().remove(tc);
				}
			}
		}
		
		return res;
	}


}
