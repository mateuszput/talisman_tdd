package maintest;

import static org.junit.Assert.*;
import main.AdventureCard;

import org.junit.Before;
import org.junit.Test;

public class AdventureCardTest {

	AdventureCard adventureCard;
	
	@Before
	public void setUp() {
		adventureCard = new AdventureCard("Hobgoblin", AdventureCard.STRENGTH_ENEMY, 3);
	}
	
	@Test
	public void getNameCorrect(){
		String expectedName = "Hobgoblin";
		String actualName = null;
		actualName = adventureCard.getName();
		assertEquals(expectedName, actualName);
	}
	
}
