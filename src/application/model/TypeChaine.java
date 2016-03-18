package application.model;

import java.util.HashMap;

import javafx.scene.paint.Color;

public enum TypeChaine {
	SACKSON(1, Color.web("#CC3333")),
	ZETA(1, Color.web("#FFCC33")),
	HYDRA(2, Color.web("#FF6600")),
	FUSION(2, Color.web("#336633")),
	AMERICA(2, Color.web("#333399")),
	PHOENIX(3, Color.web("#996699")),
	QUANTUM(3, Color.web("#669999"));

	private int catChaine; // categorie de la chaine (1 = faible, 2 = moyen, 3 = fort)
	private Color couleurChaine;
	private static int[] nbHotel = {2,3,4,5,10,20,30,40};
	
	private TypeChaine(int cat, Color c){
		this.catChaine = cat;
	}
	
	private static int prixAction(TypeChaine tc, int nb){
		int res = 200 + (tc.catChaine - 1) * 100; // prix de base de la chaine d hotel
		for(int i : nbHotel){
			if(nb > i){
				res += 100;
			} 
		}	
		return res;
	}
	
	public static int primeActionnairePrincipal(TypeChaine tc, int nb){
		return prixAction(tc, nb)*10;
	}
	
	public static int primeActionnaireSecondaire(TypeChaine tc, int nb){
		return prixAction(tc, nb)*5;
	}
}