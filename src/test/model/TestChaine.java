package test.model;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import application.model.Chaine;
import application.model.TypeChaine;

public class TestChaine {
	Chaine chaine;

	@Before
	public void initChaine(){
		chaine = new Chaine(TypeChaine.SACKSON);
	}
}
