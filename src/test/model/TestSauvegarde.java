package test.model;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import application.model.Case;
import application.rmi.Game;




public class TestSauvegarde {
/**
	public static void main(String[] args) {
		String name="test.json";
		//Game a= new Game();
		//Set<Case> c=new HashSet<Case>() {
		//};
		//Game a= new Game(c,1);
		ArrayList<Game> ch=new ArrayList<Game>();
		Game a=new Game();
		//Game a= new Game(1,ch,cha);
		
		//Game a=new Game(//"ers");
		//Game a=new Game(TypeGame.AMERICA);
		
		//Game a=new Game();
		
		
		
		try {
			sauvgarderGame(a, name);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Game b=chargerGame(name);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}**/
	
	
	
	
	/**
	 * methode permets de sauvgarder l'objet Game courant sous formatJSON via Jackson
	 * @param g
	 * @param adr
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void sauvgarderGame(Game g, String adr)
			throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		File nf = new File(adr);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(nf, g);

		// Convert object to JSON string
		String jsonInString = mapper.writeValueAsString(g);

		// Convert object to JSON string and pretty print

	}
	
	
	
	/**
	 * methode permets de charger l'objet Game recuperé en JSON 
	 * @param adr
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Game chargerGame(String adr) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();

		// JSON from file to Object
		Game g = mapper.readValue(new File(adr), Game.class);

		// JSON from String to Object
		System.out.println(g.toString());
		return g;
	}

}
