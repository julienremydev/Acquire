package test.control;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.control.ConnexionController;
import application.control.PlateauController;
import application.globale.Globals;
import application.model.Action;
import application.model.Chaine;
import application.model.ClientInfo;
import application.model.TypeChaine;
import application.rmi.Game;
import application.rmi.Serveur;
import application.rmi.ServeurInterface;

public class TestPlateauController {

	
	PlateauController controller;
	Game game;

	@Before
	public void initController() {
		controller = new PlateauController();
		game = new Game();
		ClientInfo toto = new ClientInfo("toto");
		
		HashMap<String,ClientInfo> hm = new HashMap<String,ClientInfo> ();
		
		HashMap<TypeChaine,Integer> actionParChaine = new HashMap<TypeChaine,Integer>();
		actionParChaine.put(TypeChaine.SACKSON, 0);
		actionParChaine.put(TypeChaine.AMERICA, 0);
		actionParChaine.put(TypeChaine.FUSION, 0);
		actionParChaine.put(TypeChaine.PHOENIX, 0);
		actionParChaine.put(TypeChaine.HYDRA, 0);
		actionParChaine.put(TypeChaine.QUANTUM, 0);
		actionParChaine.put(TypeChaine.ZETA, 0);
		toto.setActionParChaine(actionParChaine);
		
		hm.put(toto.getPseudo(), toto);
		game.getTableauDeBord().setInfoParClient(hm);
	}
	private void setActionparChaine(TypeChaine tc, int nb, HashMap<TypeChaine,Integer> actionsclient){
		actionsclient.put(tc, nb);
	}
	private ArrayList<Integer> equalsHashMap(){
		ArrayList<Integer> compareArray = new ArrayList<Integer> ();
		compareArray.add(controller.getActions_fusions().get(Globals.hashMapKEEP));
		compareArray.add(controller.getActions_fusions().get(Globals.hashMapTRADE));
		compareArray.add(controller.getActions_fusions().get(Globals.hashMapSELL));
		return compareArray;
	}
	private ArrayList<Integer> arrayExpected(int keep, int trade, int sell){
		ArrayList<Integer> compareArray = new ArrayList<Integer> ();
		compareArray.add(keep);
		compareArray.add(trade);
		compareArray.add(sell);
		return compareArray;
	}
	
	@Test
	public void testKeepTradeSellAction() {
		/*
		 * Cha�ne absorb�e : AMERICA
		 * Cha�ne absorbante : FUSION
		 */
		ArrayList<Chaine> al = new ArrayList<Chaine> ();
		al.add(game.getListeChaine().get(TypeChaine.AMERICA.getNumero()-2));
		Action action = new Action(Globals.choixActionFusionEchangeAchatVente, al,game.getListeChaine().get(TypeChaine.FUSION.getNumero()-2));
		game.setAction(action);
		controller.initializeMapAction (game,"toto");
		
		
		/*
		 * Le joueur toto n'a pour l'instant aucune action.
		 * Avant chaque test, nous affecterons � toto un nombre d'actions diff�rent,
		 * et nous pourrons �galement modifier le nombre d'actions restantes de la chaine absorbante.
		 * Rappel des r�gles, les joueurs peuvent :
		 * -garder leurs actions, en pr�vision de la r�apparition de la cha�ne absorb�e.
		 * -les revendre � la banque.
		 * -les �changer � raison de 2 actions pour 1 action de la cha�ne qui a fusionn� avec la cha�ne absorb�e. 
		 * (Si toutefois la banque ne poss�de plus d'actions � �changer, cette op�ration n'est pas possible.)
		 * 
		 * Un menu dynamique permet au joueur de s�lectionner les actions qu'il souhaite garder, �changer ou vendre.
		 * Diff�rents choix s'offrent � lui:
		 * -Garder toutes ses actions (maxKEEP)
		 * -Echanger le maximum d'actions (maxTRADE)
		 * -Echanger une action en plus (moreTRADE)
		 * -Echanger une action en moins(lessTRADE)
		 * -Vendre toutes ses actions (maxSELL)
		 * -Vendre une action en plus (moreSELL)
		 * -Vendre une action en moins (lessSELL)
		 */
		
		setActionparChaine(TypeChaine.AMERICA, 4, game.getTableauDeBord().getClientInfo("toto").getActionParChaine());
		
		//Le joueur a 4 actions, il d�cide de toutes les garder
		String choix = "maxKEEP";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(4,0,0),equalsHashMap());

		//Le joueur a 4 actions, il d�cide d'�changer le max : 4/2 = 2
		choix="maxTRADE";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(0,2,0),equalsHashMap());
		
		//Le joueur a 4 actions, il d�cide de vendre le max
		choix="maxSELL";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(0,0,4),equalsHashMap( ));
		
		//Le joueur veut vendre une action en moins
		choix="lessSELL";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(1,0,3),equalsHashMap( ));
		
		//Le joueur veut vendre une action en plus
		choix="moreSELL";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(0,0,4),equalsHashMap( ));
	
		//Le joueur veut �changer une action en plus
		choix="moreTRADE";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(0,1,2),equalsHashMap( ));
	
		//Le joueur veut �changer une action en plus
		choix="moreTRADE";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(0,2,0),equalsHashMap( ));

		//Le joueur veut �changer une action en moins
		choix="lessTRADE";
		controller.onEventActionFusion (choix, game, "toto") ;
		assertEquals(arrayExpected(2,1,0),equalsHashMap());
	
		
		//Le nombre d'actions restante de la chaine absorbante est 0
		game.getListeChaine().get(TypeChaine.FUSION.getNumero()-2).setNbActionRestante(0);
		
		//Le joueur a 4 actions, il d�cide de toutes les garder
		choix = "maxKEEP";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(4,0,0),equalsHashMap());

		//Le joueur a 4 actions, il d�cide d'�changer le max :0
		choix="maxTRADE";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(4,0,0),equalsHashMap());
		
		//Le joueur a 4 actions, il d�cide de vendre le max
		choix="maxSELL";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(0,0,4),equalsHashMap( ));
		
		//Le joueur veut vendre une action en moins
		choix="lessSELL";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(1,0,3),equalsHashMap( ));
		
		//Le joueur veut vendre une action en plus
		choix="moreSELL";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(0,0,4),equalsHashMap( ));
	
		//Le joueur veut �changer une action en plus
		choix="moreTRADE";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(0,0,4),equalsHashMap( ));
	
		//Le joueur a 4 actions, il d�cide de toutes les garder
		choix = "maxKEEP";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(4,0,0),equalsHashMap());
				
		//Le joueur veut �changer une action en plus
		choix="moreTRADE";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(4,0,0),equalsHashMap( ));

		//Le joueur veut �changer une action en moins
		choix="lessTRADE";
		controller.onEventActionFusion (choix, game, "toto") ;
		assertEquals(arrayExpected(4,0,0),equalsHashMap());

		//Le nombre d'actions restante de la chaine absorbante est 1
		game.getListeChaine().get(TypeChaine.FUSION.getNumero()-2).setNbActionRestante(1);
		
		//Le joueur veut �changer une action en plus
		choix="moreTRADE";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(2,1,0),equalsHashMap( ));
		
		//Le joueur veut �changer une action en plus : choix impossible
		choix="moreTRADE";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(2,1,0),equalsHashMap( ));
		
		
		setActionparChaine(TypeChaine.AMERICA, 5, game.getTableauDeBord().getClientInfo("toto").getActionParChaine());
		controller.initializeMapAction (game,"toto");
		//Le nombre d'actions restante de la chaine absorbante est 25
		game.getListeChaine().get(TypeChaine.FUSION.getNumero()-2).setNbActionRestante(25);
		
		//Le joueur a 5 actions, il d�cide de garder le max
		choix="maxKEEP";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(5,0,0),equalsHashMap());
		
		//Le joueur veut �changer une action en plus
		choix="moreTRADE";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(3,1,0),equalsHashMap( ));
		
		//Le joueur veut �changer une action en plus
		choix="moreTRADE";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(1,2,0),equalsHashMap( ));
		
		//Le joueur veut �changer une action en plus : choix impossible
		choix="moreTRADE";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(1,2,0),equalsHashMap( ));
		
		//Le joueur veut vendre une action en plus
		choix="moreSELL";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(0,2,1),equalsHashMap( ));
		
		//Le joueur veut vendre une action en plus : choix impossible-> le joueur doit enlever une action �chang�e
		choix="moreSELL";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(0,2,1),equalsHashMap( ));
		
		//Le joueur veut vendre une action en moins
		choix="lessSELL";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(1,2,0),equalsHashMap( ));
		
		
		//Le nombre d'actions restante de la chaine absorbante est 2
		game.getListeChaine().get(TypeChaine.FUSION.getNumero()-2).setNbActionRestante(2);
				
		//Le joueur veut �changer le max d'actions
		choix="maxTRADE";
		controller.onEventActionFusion (choix, game, "toto");
		assertEquals(arrayExpected(1,2,0),equalsHashMap( ));
	}
}
