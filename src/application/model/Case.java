package application.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Case {
	private Case north;
	private Case south;
	private Case east;
	private Case west;
	// -1 injouable ; 0 vide ; 1 hotel ; 2-8 chaine
	private static int etat;
	private String nom;
	
	public Case(){
		this.etat=0;
	}
	
	public Case(String n) {
		this.etat = 0;
		this.nom=n;
	}
	
	public Case getNorth() {
		return north;
	}
	public void setNorth(Case north) {
		this.north = north;
	}
	public Case getSouth() {
		return south;
	}
	public void setSouth(Case south) {
		this.south = south;
	}
	public Case getEast() {
		return east;
	}
	public void setEast(Case east) {
		this.east = east;
	}
	public Case getWest() {
		return west;
	}
	public void setWest(Case west) {
		this.west = west;
	}
	public static Integer getEtat() {
		return etat;
	}
	/**
	 * Changement de l'etat d'une case
	 * @param etat
	 */
	public static void setEtat(int etat) {
		Case.etat = etat;
	}
	/**
	 * Regarde les diff�rents cas possible des �tats adjacents � la case.
	 * Appelle les fonctions qui vont bien � chaque fois.
	 */
	public void lookCase(){
	
				
		boolean simpleCase = this.surroundedByNothing();
		boolean askColor = this.surroundedByHotels();
		boolean askChain = this.surroundedByChains();
		
		/**
		 * Cas simple, aucune case initialis�e autour, la case deviens donc un hotel
		 * modification de l'etat de cette case
		 */
		if(simpleCase)		
			this.setEtat(1);
		
		/**
		 * Pr�sence d'un ou plusieurs hot�ls autour de la case
		 * Pas de cha�nes dans ce cas
		 * Appel de la fonction de choix de couleur de chaine cot� utilisateur
		 */
		if(askColor && !askChain)
			// En vrai ici il faudra faire l'appel de la fonction qui va demander � l'utilisateur la couleur qu'il veut choisir pour sa nouvelle chaine
			// user.askColorForNewChain();
			this.setEtat(0);
		
		/**
		 * Pr�sence d'une ou plusieures cha�nes autour de la case
		 * Pas d'h�tels dans ce cas
		 */
		if(askChain && !askColor) // juste une ou plusieurs chaines, pas d'hotel
		{
			//tableau des cases non null donc dans ce cas des cases avec chaines.
			ArrayList<Case> tab = tabAdjascent(this.getNorth(),this.getSouth(),this.getEast(),this.getWest());
			/**
			 * Le tableau n'a qu'une taille de 1, donc simple changement de la couleur de la case
			 */
			if(tab.size() == 1)
				this.setEtat(tab.get(0).getEtat());
			/**
			 * Tableau taille de 2, donc deux cases avec une chaine
			 */
			if(tab.size()==2)
			{
				
				int chainePremiereCase = tab.get(0).getEtat();
				int chaineDeuxiemeCase = tab.get(1).getEtat();
				/**
				 * Si ce sont les m�mes cha�nes, simple modification de l'etat de la case
				 */
				if(chainePremiereCase == chaineDeuxiemeCase)
					this.setEtat(tab.get(0).getEtat()); 
				/**
				 * Si ce ne sont pas les m�mes chaines on v�rifie la taille de chacunes
				 */
				else
				{
					/**
					 * Si leurs taille sont �gales, on demande la couleur � l'utilisateur
					 */
					if(chainePremiereCase==chaineDeuxiemeCase) // avec les fonctions que yoh va faire
						this.setEtat(tab.get(0).getEtat()); // client.askColorChaineVoulue() && chaine.SetChaine(int nouvelleChaine) 
					
					else
					{
						if(chainePremiereCase>chaineDeuxiemeCase)
							// La premiere chaine est plus grande donc changement etat case + tab.get(1).SetChaine(tab.get(0))
							this.setEtat(tab.get(0).getEtat());
						else
							// pareil mais inversement avec les cases du tableau
							this.setEtat(tab.get(1).getEtat());
					}
				}
					
			}
			
			
			if(tab.size()>=2)// regrouper le cas ou on a 2, 3 ou 4 cases autour avec une chaine
			{
				Collections.sort(tab, new Comparator<Case>() {
					@Override
					public int compare(Case tc1, Case tc2) {
						return tc1.getEtat().compareTo(tc2.getEtat());
					}
				});
			}
		}
		
		/**
		 * Pr�sense d'un ou plusieurs hot�ls avec une ou plusieures cha�nes
		 */
	
		if(askChain && askColor){
			// Ici on a au mooins une chain et au moins 1 hotel
			// chercher ou on a la chaine et r�cup�rer son etat pour avoir la couleur
			// chercher les hotels ou autres chaines
			// regarder la taille des chaines pour savoir la quelle est la plus grande 
			// si taille == user.askColorForChains
			// si taille != on prend etat de la plus grande chaine
			this.setEtat(3);
		}
		
		
	}
	/**
	 * Permet de savoir si les cases autour de la notre sont vides
	 * @return
	 */
	public boolean surroundedByNothing(){
		if((this.getNorth().getEtat()==0) && (this.getEast().getEtat()==0) && (this.getSouth().getEtat()==0) && (this.getWest().getEtat()==0))
			return true;
		else
			return false;
	}
	/**
	 * Retourne true si on � au moins un hotel autour
	 * @return
	 */
	public boolean surroundedByHotels(){
		if((this.getNorth().getEtat()==1) || (this.getEast().getEtat()==1) || (this.getSouth().getEtat()==1) || (this.getWest().getEtat()==1))
			return true;
		else
			return false;
	}
	/**
	 * Retourne true si on � au moins une une chaine autour
	 * @return
	 */
	public boolean surroundedByChains(){
		if((this.getNorth().getEtat()>=2) || (this.getEast().getEtat()>=2) || (this.getSouth().getEtat()>=2) || (this.getWest().getEtat()>=2))
			return true;
		else
			return false;
	}
	/**
	 * Retourne un tableau des cases adjascentes non nulles
	 * @param cN
	 * @param cS
	 * @param cE
	 * @param cW
	 * @return
	 */
	public ArrayList<Case> tabAdjascent(Case cN,Case cS,Case cE, Case cW){
		ArrayList<Case> tab = new ArrayList();
		if(cN != null)
			tab.add(cN);
		if(cS != null)
			tab.add(cS);
		if(cE != null)
			tab.add(cE);
		if(cW != null)
			tab.add(cW);
		return tab;
	}
	
	
}
