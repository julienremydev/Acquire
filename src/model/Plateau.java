package model;

public class Plateau {
	
	private Case[][] plateau;
	
	public Plateau() {
		plateau=new Case[12][9];
		
	}
	
	public void init(){
		String [] tab = {"A","B","C","D","E","F","G","H","I"};
		String [] tab2 = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		for(int i=0;i<plateau.length;i++){
			for(int j=0;j<plateau[0].length;j++){
				plateau[i][j]=new Case(tab[j]+tab2[i]);
			}
		}
	}
 
	public void initCaseNoir(){
		for(int i=0;i<plateau.length;i++){
			for(int j=0;j<plateau[0].length;j++){
				
			}
		}
	}
	
	public static void lol () {
		
		String res="";
		for (int i=0;i<12;i++) {
			for (int j =0;j<9;j++) {
				res+="<Button onAction='#<setDisable' alignment='CENTER' contentDisplay='CENTER' mnemonicParsing='false' text='"+tab[j]+tab2[i]+"' GridPane.columnIndex='"+i+"' GridPane.halignment='CENTER' GridPane.rowIndex='"+j+"' GridPane.valignment='CENTER' maxWidth='70.0' minWidth='70.0' maxHeight='70.0' minHeight='70.0' />\n";
			}
		}
		System.out.println(res);
	}
	
	
}