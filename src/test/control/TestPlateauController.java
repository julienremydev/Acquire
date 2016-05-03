package test.control;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
		actionParChaine.put(TypeChaine.AMERICA, 2);
		actionParChaine.put(TypeChaine.FUSION, 0);
		actionParChaine.put(TypeChaine.PHOENIX, 0);
		actionParChaine.put(TypeChaine.HYDRA, 0);
		actionParChaine.put(TypeChaine.QUANTUM, 0);
		actionParChaine.put(TypeChaine.ZETA, 0);
		toto.setActionParChaine(actionParChaine);
		
		hm.put(toto.getPseudo(), toto);
		game.getTableauDeBord().setInfoParClient(hm);
	}

	@Test
	public void test() {
		ArrayList<Chaine> al = new ArrayList<Chaine> ();
		al.add(new Chaine(TypeChaine.AMERICA));
		Action action = new Action(Globals.choixActionFusionEchangeAchatVente, al,new Chaine(TypeChaine.FUSION));
		game.setAction(action);
		HashMap<String, Integer> actions_fusions_loc = new HashMap<String, Integer>();
		
		controller.initializeMapAction (game,"toto");
		
		actions_fusions_loc = controller.onEventActionFusion ("maxKEEP", game, "toto");
		System.out.println(actions_fusions_loc.get(Globals.hashMapKEEP));
		System.out.println(actions_fusions_loc.get(Globals.hashMapTRADE));
		System.out.println(actions_fusions_loc.get(Globals.hashMapSELL));
		
		actions_fusions_loc = controller.onEventActionFusion ("moreTRADE", game, "toto");
		System.out.println(actions_fusions_loc.get(Globals.hashMapKEEP));
		System.out.println(actions_fusions_loc.get(Globals.hashMapTRADE));
		System.out.println(actions_fusions_loc.get(Globals.hashMapSELL));
		
		actions_fusions_loc = controller.onEventActionFusion ("maxTRADE", game, "toto");
		System.out.println(actions_fusions_loc.get(Globals.hashMapKEEP));
		System.out.println(actions_fusions_loc.get(Globals.hashMapTRADE));
		System.out.println(actions_fusions_loc.get(Globals.hashMapSELL));
		

	}
}
