package application.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import application.model.Action;
import application.model.Case;
import application.model.Chaine;
import application.model.TypeChaine;

public interface ServeurInterface extends Remote{

	String register(ClientInterface client, String p, boolean loadJSON) throws Exception;

	void distribution() throws RemoteException;
	
	void distributionTchat(String pseudo, String s) throws RemoteException;

	void getCasePlayed(String text, String pseudo) throws RemoteException;
	
	void creationChaineServeur(TypeChaine c) throws RemoteException;
	
	void logout (String p) throws RemoteException;

	void setLancement() throws RemoteException;
	
	Game getGame() throws RemoteException;

	void nextTurn(String pseudo) throws RemoteException;

	void achatAction(String nomJoueur, HashMap<TypeChaine, Integer> actionAAcheter) throws RemoteException;

	void choixCouleurFusion(ArrayList<Chaine> arrayList, ArrayList<Chaine> listeChaineAModif, Chaine c, ArrayList<Case> arrayList2) throws RemoteException;

	void clientSaveGame(String pseudo) throws RemoteException;

	void choiceFusionAction(HashMap<String, Integer> actions_fusions, Chaine chaineAbsorbee, Chaine chaineAbsorbante, String pseudo) throws RemoteException;

	void isOver() throws RemoteException;
}
