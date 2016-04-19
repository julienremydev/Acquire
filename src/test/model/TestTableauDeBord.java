package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import application.model.Chaine;
import application.model.ClientInfo;
import application.model.TableauDeBord;
import application.model.TypeChaine;

public class TestTableauDeBord {
	ClientInfo c1;
	ClientInfo c2;
	Chaine ch1;
	Chaine ch2;
	
	TableauDeBord tableauTest;
	ArrayList<Chaine> listeTypeChaine;
	HashMap<String, ClientInfo> infoParClient;
	
	
	@Before
	public void initTableauBord(){
		tableauTest = new TableauDeBord();
		infoParClient = new HashMap<String, ClientInfo>();
		listeTypeChaine = new ArrayList<Chaine>();
//		c1 = new ClientInfo("client1");
//		c2 = new ClientInfo("client2");
		ch1 = new Chaine (TypeChaine.AMERICA);
		ch2 = new Chaine (TypeChaine.FUSION);
//		infoParClient.add(c1);
//		infoParClient.add(c2);
		listeTypeChaine.add(ch1);
		listeTypeChaine.add(ch2);
		
		
		tableauTest.setListeTypeChaine(listeTypeChaine);
		tableauTest.setInfoParClient(infoParClient);
		
		
	}
	
	@Test
	public void testGetChaineById() {
		
		assertEquals(ch1, tableauTest.getChaineById(6));
		assertEquals(ch2, tableauTest.getChaineById(5));
		assertNotEquals(ch2, tableauTest.getChaineById(6));
	}

}
