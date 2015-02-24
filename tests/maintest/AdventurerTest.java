package maintest;
import main.Adventurer;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;


/**
 * 
 * @author Mateusz Put
 * 
 * @class Poszukiwacz
 * 
 * @responsibility 
 * Obsluga poszukiwacza, przechowywanie informacji na temat poszukiwacza
 * 
 * @collaborators
 * KontrolerWyboru - do przeprowadzania akcji na poszukiwaczach
 *
 */
public class AdventurerTest {

	private Adventurer adventurer1;
	
	@Before
	public void setUp() throws Exception {
		adventurer1 = new Adventurer("BARBARZYŃCA", 11, 5, 3, 4);
	}
	
	/**
	 * Testuje czy pobierana nazwa jest prawidlowa
	 */
	@Test
	public void getNameCorrect(){
		assertEquals("BARBARZYŃCA", adventurer1.getName());
	}
	
	/**
	 * Testuje czy pobierane pole startowe jest prawidlowe
	 */
	@Test
	public void getStartingSpaceCorrect(){
		assertEquals(11, adventurer1.getStartingSpace());
	}
	
	@Test
	public void setCurrentSpaceCorrect(){
		Whitebox.setInternalState(adventurer1, "adventurerCurrentSpace", 11);
		
		assertEquals(11, adventurer1.getCurrentSpace());
		adventurer1.setCurrentSpace(13);
		assertEquals(13, adventurer1.getCurrentSpace());
	}
	
	@Test
	public void getStrengthCorrect(){
		assertEquals(5, adventurer1.getStrength());
	}

	@Test
	public void getCraftCorrect(){
		assertEquals(3, adventurer1.getCraft());
	}
	
	@Test
	public void getLivesCorrect(){
		assertEquals(4, adventurer1.getLives());
	}
	
	@Test
	public void addStrenghtTrophyStrenghtNotIncreased(){
		adventurer1.addStrengthTrophy(5);
		assertEquals(5, adventurer1.getStrengthTrophy());
		assertEquals(5, adventurer1.getStrength());
	}
	
	@Test
	public void addStrenghtTrophyStrenghtIncreased(){
		adventurer1.addStrengthTrophy(5);
		adventurer1.addStrengthTrophy(5);
		assertEquals(3, adventurer1.getStrengthTrophy());
		assertEquals(6, adventurer1.getStrength());
	}
	
	@Test
	public void addCraftTrophyCraftNotIncreased(){
		adventurer1.addCraftTrophy(5);
		assertEquals(5, adventurer1.getCraftTrophy());
		assertEquals(3, adventurer1.getCraft());
	}
	
	@Test
	public void addCraftTrophyCraftIncreased(){
		adventurer1.addCraftTrophy(5);
		adventurer1.addCraftTrophy(5);
		assertEquals(3, adventurer1.getCraftTrophy());
		assertEquals(4, adventurer1.getCraft());
	}
}
