package application.model;

import java.util.HashMap;

import javafx.scene.paint.Color;

public enum TypeChaine {
	SACKSON(1, Color.web("#CC3333"), 2),
	ZETA(1, Color.web("#FFCC33"), 3),
	HYDRA(2, Color.web("#FF6600"), 4),
	FUSION(2, Color.web("#336633"), 5),
	AMERICA(2, Color.web("#333399"), 6),
	PHOENIX(3, Color.web("#996699"), 7),
	QUANTUM(3, Color.web("#669999"), 8);

	private int catChaine; // categorie de la chaine (1 = faible, 2 = moyen, 3 = fort)
	private Color couleurChaine;
	private int numero;
	private static int[] nbHotel = {2,3,4,5,10,20,30,40};
	
	/**
	 * Constructeur de l enum TypeChaine 
	 * @param cat 
	 * @param c
	 * @param n
	 */
	private TypeChaine(int cat, Color c, int n){
		this.catChaine = cat;
		this.numero = n;
	}
	
	/**
	 * Calcul du prix de l action en fonction du type de la chaine et du nombre d action
	 * @param tc
	 * @param nb
	 * @return
	 */
	private static int prixAction(TypeChaine tc, int nb){
		int res = 200 + (tc.catChaine - 1) * 100; // prix de base de la chaine d hotel
		for(int i : nbHotel){
			if(nb > i){
				res += 100;
			} 
		}	
		return res;
	}
	
	/**
	 * methode permettant d obtenir le type chaine associe au nom
	 * @param nom
	 * @return un TypeChaine
	 */
	public static TypeChaine getTypeChaine(String nom){
		return TypeChaine.valueOf(nom);
	}
	
	/**
	 * methode permettant d obtenir le type de chaine en fonction de sa categorie
	 * @param num
	 * @return un TypeChaine
	 */
	public static TypeChaine getTypeChaine(int num){
		return TypeChaine.values()[num-2];
	}
	
	/**
	 * methode qui permet de calculer la prime pour le premier actionnaire de la chaine
	 * calcul: 10 fois le prix de l action actuel
	 * @param tc
	 * @param nb
	 * @return prime de l actionnaire principal
	 */
	public static int primeActionnairePrincipal(TypeChaine tc, int nb){
		return prixAction(tc, nb)*10;
	}
	
	/**
	 * methode qui permet de calculer la prime de l actionnaire secondaire
	 * calcul: 5 fois le prix de l action actuel
	 * @param tc
	 * @param nb
	 * @return prime de l actionnaire secondaire
	 */
	public static int primeActionnaireSecondaire(TypeChaine tc, int nb){
		return prixAction(tc, nb)*5;
	}
	
	/*
	 * getter and setter
	 */
	public int getNumero(){
		return this.numero;
	}
}