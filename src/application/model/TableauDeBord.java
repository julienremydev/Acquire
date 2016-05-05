package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import application.globale.Globals;

public class TableauDeBord implements Serializable{
	private static final long serialVersionUID = -3628602150383225255L;

	private HashMap<String,ClientInfo> infoParClient;
	private ArrayList<Chaine> listeChaine;
	private ArrayList<InfoChaine> infosChaine;

	/**
	 * Constructeur Tableau de bord
	 */

	@JsonCreator
	public TableauDeBord() {
		this.infoParClient = new HashMap<String,ClientInfo>();
		this.listeChaine= new ArrayList<Chaine>();
		this.infosChaine= new ArrayList<InfoChaine>();
		this.infosChaine.add(new InfoChaine("Taille"));
		this.infosChaine.add(new InfoChaine("Actions"));

	}
	public ArrayList<InfoChaine> getInfosChaine() {
		return infosChaine;
	}
	public void setInfosChaine(ArrayList<InfoChaine> infosChaine) {
		this.infosChaine = infosChaine;
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
			if(c.getTypeChaine().getNumero()==id){
				return c;
			}
		}
		return null;
	}

	/**
	 * methode qui permet au joueur d acheter des actions dans plusieurs chaine a la fois
	 * @param nomJoueur
	 * @param actionAAcheter
	 */
	public void achatActions(String nomJoueur, HashMap<TypeChaine, Integer> actionAAcheter){
		Collection<TypeChaine> keys = actionAAcheter.keySet();
		for (TypeChaine key : keys) {
			achatActionJoueur(actionAAcheter.get(key), nomJoueur, key);
		}
	}
	/**
	 * Retourne vrai si le joueur peut acheter au moins une action
	 * @param pseudo
	 * @return
	 */
	public boolean actionAvailableForPlayer(String pseudo){
		if (infoParClient.containsKey(pseudo)){
			int cash = infoParClient.get(pseudo).getCash();
			for(Chaine chaine : listeChaine){
				if ( !chaine.chaineDisponible() && chaine.getNbActionRestante() > 0 && TypeChaine.prixAction(chaine.getTypeChaine(), chaine.getListeCase().size()) <= cash){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Retourne vrai si le joueur peut acheter une action
	 * @param pseudo
	 * @param idChaine
	 * @return
	 */
	public boolean actionAvailableForPlayer(String pseudo, int idChaine, int argentDepense){
		if (infoParClient.containsKey(pseudo)){
			int cash = infoParClient.get(pseudo).getCash();
			if ( !getChaineById(idChaine).chaineDisponible() && getChaineById(idChaine).getNbActionRestante() > 0 && TypeChaine.prixAction(getChaineById(idChaine).getTypeChaine(),getChaineById(idChaine).getListeCase().size()) <= cash-argentDepense)
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
		int indexChaine = getIndexChaine(tc);
		ClientInfo joueur = getClientInfo(nomJoueur);
		int res = getNbActionAAchete(nb, indexChaine, tc, joueur);

		joueur.getActionParChaine().put(tc, res + joueur.getActionParChaine().get(tc));
		joueur.updateCash(-res * TypeChaine.prixAction(tc, listeChaine.get(indexChaine).tailleChaine()));

		// ajout des infos dans InfoChaine
		infosChaine.get(1).getInfos().put(listeChaine.get(indexChaine).getTypeChaine(), listeChaine.get(indexChaine).getNbActionRestante());

		this.updateActionnaire();
		return res;
	}

	/**
	 * fonction permettant au joueur de vendre des actions et met a jour le nombre d action restante
	 * @param nb : nombre d action voulant etre vendue par le joueur
	 * @param nomJoueur : nom du joueur qui vend
	 * @param tc : type de la chaine
	 * @param prix_action_absorbee 
	 * @param prix_action_absorbante 
	 * @return nombre effectivement vendue
	 */
	public int vendActionJoueur(int nb, String nomJoueur, TypeChaine tc, int prix_action_absorbante, int prix_action_absorbee){
		int indexChaine = getIndexChaine(tc);
		ClientInfo joueur = getClientInfo(nomJoueur); 
		//int res = getNbActionAVendre(nb, indexChaine, tc, joueur);

		joueur.getActionParChaine().put(tc, (int)joueur.getActionParChaine().get(tc) - nb);
		this.listeChaine.get(indexChaine).setNbActionRestante(this.listeChaine.get(indexChaine).getNbActionRestante() + nb);
		joueur.updateCash(nb * prix_action_absorbee);

		// ajout des infos dans InfoChaine
		infosChaine.get(1).getInfos().put(listeChaine.get(indexChaine).getTypeChaine(), listeChaine.get(indexChaine).getNbActionRestante());

		this.updateActionnaire();
		return nb;
	}

	/**
	 * methode qui retourne l index de la chaine passe en parametre
	 * @param tc type de la chaine
	 * @return index de la chaine
	 */
	public int getIndexChaine(TypeChaine tc){
		int res = -1;

		for(int i=0; i<listeChaine.size(); i++){
			if (listeChaine.get(i).getTypeChaine().equals(tc)){
				res = i;
			}
		}

		return res;
	}

	/**
	 * methode qui retourne le nombre d action a vendre
	 * @param nb nombre d action voulant etre vendues
	 * @return action a vendre
	 */
	public int getNbActionAVendre(int nb, int indexChaine, TypeChaine tc, ClientInfo joueur){
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
		}

		return res;
	}

	/**
	 * methode qui retourne le nombre d action a achete
	 * @param nb nombre d action a achete
	 * @param indexChaine de la chaine ou l on doit acheter les actions
	 * @param tc type chaine de la chaine
	 * @param joueur qui achete les actions
	 * @return nombre d action a acheter
	 */
	public int getNbActionAAchete(int nb, int indexChaine, TypeChaine tc, ClientInfo joueur){
		int res = 0;

		if (indexChaine != -1 && this.infoParClient.get(joueur.getPseudo())!=null){
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
		}
		return res;
	}


	/**
	 * methode qui retourne le client info associé a un joueur
	 * @param nom du joueur
	 * @return le client info du joueur ou null si inconnu
	 */
	public ClientInfo getClientInfo(String nom){
		ClientInfo joueur = null;
		if (infoParClient.containsKey(nom)){
			joueur = infoParClient.get(nom);
		}
		return joueur;
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
		int indexChaineAchatAction = getIndexChaine(chaineAchatAction);
		int indexChaineVendAction = getIndexChaine(chaineVendAction);
		ClientInfo joueur = getClientInfo(nomJoueur);
		int res = 0;

		if(nb % 2 == 0){
			res = nb/2;
		}else{
			nb = nb-1;
			res = nb/2;
		}
		res = getNbActionAAchete(res, indexChaineAchatAction, chaineAchatAction, joueur);

		if(res >= 0 && infoParClient.get(nomJoueur).getActionParChaine().get(chaineVendAction).intValue() >= nb){
			joueur.getActionParChaine().put(chaineVendAction, (int)joueur.getActionParChaine().get(chaineVendAction) - nb);
			this.listeChaine.get(indexChaineVendAction).setNbActionRestante(this.listeChaine.get(indexChaineVendAction).getNbActionRestante() + res);
		}else{
			res = 0;
		}

		joueur.getActionParChaine().put(chaineAchatAction, res + joueur.getActionParChaine().get(chaineAchatAction));
		//joueur.updateCash(-res * TypeChaine.prixAction(chaineAchatAction, listeChaine.get(indexChaineAchatAction).tailleChaine()));

		// ajout des infos dans InfoChaine
		infosChaine.get(1).getInfos().put(listeChaine.get(indexChaineAchatAction).getTypeChaine(), listeChaine.get(indexChaineAchatAction).getNbActionRestante());
		infosChaine.get(1).getInfos().put(listeChaine.get(indexChaineVendAction).getTypeChaine(), listeChaine.get(indexChaineVendAction).getNbActionRestante());

		this.updateActionnaire();
		return res;
	}

	/**
	 * methode qui permet de vendre ou d echanger les actions d un joueur des chaines passees en parametre
	 * @param hm
	 * @param chaineVendAction
	 * @param chaineAchatAction
	 * @param nomJoueur
	 * @param prix_action_absorbee 
	 * @param prix_action_absorbante 
	 */
	public void traiteAction(HashMap<String, Integer> hm, Chaine chaineVendAction, Chaine chaineAchatAction, String nomJoueur, int prix_action_absorbante, int prix_action_absorbee){
		Collection<String> keys = hm.keySet();
		for (String key : keys) {
			if(key.equals(Globals.hashMapSELL)){
				vendActionJoueur(hm.get(key), nomJoueur, chaineVendAction.getTypeChaine(),prix_action_absorbante,prix_action_absorbee);
			}else if(key.equals(Globals.hashMapTRADE)){
				echangeAction(hm.get(key)*2, chaineVendAction.getTypeChaine(), chaineAchatAction.getTypeChaine(), nomJoueur);
			}
		}
	}

	/**
	 * Met a jour le status des joueurs pour chaque types de chaine
	 */
	public void updateActionnaire() {
		ArrayList<TypeChaine> chaineList = new ArrayList<TypeChaine>();
		for (Chaine c : listeChaine) {
			chaineList.add(c.getTypeChaine());
		}
		int nbActionMax=0;
		int nbActionMax2=0;
		Set<ClientInfo>liste_clients_major=new HashSet<ClientInfo>();
		Set<ClientInfo>liste_clients_second=new HashSet<ClientInfo>();
		Set<ClientInfo>liste_clients_actionnaire=new HashSet<ClientInfo>();
		HashMap<ClientInfo, Integer>  liste_sort = new HashMap<ClientInfo, Integer>();
		for (TypeChaine tc : chaineList) {
			for (ClientInfo c : infoParClient.values()) {
				liste_sort.put(c,c.getActionParChaine().get(tc));
			}
			ArrayList<ClientInfo> clients = Globals.sortByValueAction(liste_sort);
			int iterator=0;
			int iterator2=0;
			int nbMajor=0;
			while (iterator<=clients.size()-1) {
				if (iterator==0) {
					nbActionMax = liste_sort.get(clients.get(iterator));
				}
				if (nbActionMax == liste_sort.get(clients.get(iterator))&&liste_sort.get(clients.get(iterator))!=0) {
					nbActionMax = liste_sort.get(clients.get(iterator));
					liste_clients_major.add(clients.get(iterator));
					nbMajor++;
				}
				else if (nbMajor==1) {
					if (iterator==0) {
						nbActionMax = liste_sort.get(clients.get(iterator));
					}
					if (iterator2==0) {
						nbActionMax2=liste_sort.get(clients.get(iterator));
						iterator2++;
					}
					if (nbActionMax2>0&&nbActionMax2==liste_sort.get(clients.get(iterator))&&nbActionMax!=0) {
						nbActionMax2=liste_sort.get(clients.get(iterator));
						liste_clients_second.add(clients.get(iterator));
					}
					else {
						liste_clients_actionnaire.add(clients.get(iterator));
					}
				}
				iterator++;
			}
			for (ClientInfo c : liste_clients_major) {
				if (liste_clients_second.isEmpty()&&nbMajor==1) {
					c.setEtat(tc.getNumero(), "M+,0");
				}
				else {
					c.setEtat(tc.getNumero(), "M,"+liste_clients_major.size());
				}
			}
			for (ClientInfo c : liste_clients_second) {
				if (nbMajor>1) {
					c.setEtat(tc.getNumero(), "A,0");
				}
				else {
					c.setEtat(tc.getNumero(), "S,"+liste_clients_second.size());	
				}
			}
			for (ClientInfo c : liste_clients_actionnaire) {
				c.setEtat(tc.getNumero(), "A,0");
			}
			for (ClientInfo c : infoParClient.values()) {
				c.updateNet(listeChaine);
			}
			nbActionMax=0;
			nbActionMax2=0;
			liste_clients_major.clear();
			liste_clients_second.clear();
			clients.clear();
			nbMajor=0;
			iterator=0;
			iterator2=0;
		}
	}

	@JsonIgnore
	public ArrayList<ArrayList<String>> getPrime(Action action) {
		ArrayList<ArrayList<String>> arrayPrime = new ArrayList<ArrayList<String>> ();
		ArrayList<String> infoPrimeClient = new ArrayList<String> ();
		if (action.getChoix()==2) {
			for (Chaine chaine : action.getListeChainesAbsorbees()) {
				infoPrimeClient.add(chaine.getTypeChaine().toString());
				for (ClientInfo client : infoParClient.values()) {
					int primeJoueur = client.getPrime(chaine.getTypeChaine(),listeChaine.get(chaine.getTypeChaine().getNumero()-2).tailleChaine(),false);
					infoPrimeClient.add(client.getPseudo());
					infoPrimeClient.add(String.valueOf(primeJoueur));
					client.setCash(client.getCash() + primeJoueur);
				}
				arrayPrime.add(infoPrimeClient);
			}
		}
		return arrayPrime;
	}
}
