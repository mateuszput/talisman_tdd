package maintest;
import static org.junit.Assert.*;

import main.Space;
import main.SpaceInterface;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Mateusz Put
 * 
 * @class Pole
 * 
 * @responsibility 
 * Przechowywanie i zwracanie informacji na temat danego pola
 * 
 * @collaborators 
 * MapaPol - mapa przetrzymujaca pola
 */
public class SpaceTest {

	private SpaceInterface poleMiasto;
	
	@Before
	public void setUp() throws Exception {
		poleMiasto = new Space(1, 0, "Miasto", 2, 25, -1);
		poleMiasto.setDescription("Opis miasta");
		
	}

	/**
	 * Testowane pobieranie poszczegolnych atrybutow pola
	 */
	@Test
	public void getSpaceNumber(){
		assertEquals(1, poleMiasto.getSpaceNumber());
	}
	
	@Test
	public void getAdventureCardsNumberToDraw(){
		assertEquals(0, poleMiasto.getAdventureCardsNumberToDraw());
	}
	
	@Test
	public void getName(){
		assertEquals("Miasto", poleMiasto.getName());
	}
	
	@Test
	public void getDescription(){
		assertEquals("Opis miasta", poleMiasto.getDescription());
	}
	
	@Test
	public void getLeftPathway(){
		assertEquals(2, poleMiasto.getLeftPathway());
	}
	
	@Test
	public void getRightPathway(){
		assertEquals(25, poleMiasto.getRightPathway());
	}
	
	@Test
	public void getAdditionalPathway(){
		assertEquals(-1, poleMiasto.getAdditionalPathway());
	}
	
}
