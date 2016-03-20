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
	 * Regarde les différents cas possible des états adjacents à la case.
	 * Appelle les fonctions qui vont bien à chaque fois.
	 */
	public void lookCase(){
		// par exemple : si askColor true && askchain false => this.demanderCouleur
		//				 si askColor true && askchain true  => this.demanderCouleur fusion
				
		boolean simpleCase = this.surroundedByNothing();
		boolean askColor = this.surroundedByHotels();
		boolean askChain = this.surroundedByChains();

		if(simpleCase)		// rien autour
			this.setEtat(1);
		if(askColor && !askChain)	// juste un ou des hotels
			// En vrai ici il faudra faire l'appel de la fonction qui va demander à l'utilisateur la couleur qu'il veut choisir pour sa nouvelle chaine
			// user.askColorForNewChain();
			this.setEtat(0);
		if(askChain && !askColor) // juste une ou plusieurs chaines, pas d'hotel
		{
			//tableau des cases non null donc dans ce cas des cases avec chaines.
			ArrayList<Case> tab = tabAdjascent(this.getNorth(),this.getSouth(),this.getEast(),this.getWest());
			// tableau taille 1 = une seule chaine
			if(tab.size() == 1)
				this.setEtat(tab.get(0).getEtat());// pas oublier de faire aussi changement de la petite chaine
			// tableau taille 2 = 2 chaines
			if(tab.size()==2)
			{
				int taillePremiereChaine = tab.get(0).getEtat();
				int tailleDeuxiemeChaine = tab.get(1).getEtat();
				if(tailleDeuxiemeChaine == taillePremiereChaine)
					//askColorUser
					this.setEtat(0); // sert a rien , ici il faut faire un askColorUser
				else
					if(tailleDeuxiemeChaine>taillePremiereChaine)
						this.setEtat(tab.get(1).getEtat());
						// pas oublier de faire aussi changement de la petite chaine
					else
						this.setEtat(tab.get(0).getEtat());
						// pas oublier de faire aussi changement de la petite chaine
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
		
		
		if(askChain && askColor){
			// Ici on a au mooins une chain et au moins 1 hotel
			// chercher ou on a la chaine et récupérer son etat pour avoir la couleur
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
	 * Retourne true si on à au moins un hotel autour
	 * @return
	 */
	public boolean surroundedByHotels(){
		if((this.getNorth().getEtat()==1) || (this.getEast().getEtat()==1) || (this.getSouth().getEtat()==1) || (this.getWest().getEtat()==1))
			return true;
		else
			return false;
	}
	/**
	 * Retourne true si on à au moins une une chaine autour
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
		Case c6 = null;
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
