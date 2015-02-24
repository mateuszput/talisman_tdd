package maintest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Map;

import main.AdventureCard;
import main.Adventurer;
import main.AdventurerInterface;
import main.Space;
import main.TalismanGame;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;

/**
 * 
 * @author Mateusz Put
 * 
 * @class TalismanGame
 * 
 * Klasa testujaca, testy rozdzielone na dwie klasy z powodu problemow
 * z kompatybilnoscia XStream i PowerMockRunner 
 *
 */
public class TalismanGameTest2 {

	private PrintStream printStreamEasyMock;
	private TalismanGame talismanGame;
	
	@Before
	public void setUp() {
		printStreamEasyMock = createMock(PrintStream.class);
        talismanGame = new TalismanGame(printStreamEasyMock);
	}
	
	/**
	 * testowane jest czy poprawnie zostaly zaladowane poszczegolne pola
	 * @throws Exception 
	 */
	@Test
	public void loadSpacesMapSize() throws Exception {
		Map spacesMap = (Map) Whitebox.invokeMethod(talismanGame, "loadSpaces");
		
		assertEquals(24, spacesMap.size());
	}
	
	@Test
	public void loadSpacesIncorrectBeforeFirstSpace() throws Exception {
		Map spacesMap = (Map) Whitebox.invokeMethod(talismanGame, "loadSpaces");
		
		assertEquals(24, spacesMap.size());
		assertNull((Space) spacesMap.get(0));
	}
	
	@Test
	public void loadSpacesFirstCity() throws Exception {
		Map spacesMap = (Map) Whitebox.invokeMethod(talismanGame, "loadSpaces");
		
		assertEquals("Opis miasta", ((Space) spacesMap.get(1)).getDescription());
	}
	
	@Test
	public void loadSpacesMiddleSpace() throws Exception {
		Map spacesMap = (Map) Whitebox.invokeMethod(talismanGame, "loadSpaces");
		
		assertEquals("Opis gospody", ((Space) spacesMap.get(7)).getDescription());
	}
	
	@Test
	public void loadSpacesLastSpace() throws Exception {
		Map spacesMap = (Map) Whitebox.invokeMethod(talismanGame, "loadSpaces");
		
		assertEquals("Opis pól", ((Space) spacesMap.get(24)).getDescription());
	}

	@Test
	public void loadSpacesIncorrectAfterLastSpace() throws Exception {
		Map spacesMap = (Map) Whitebox.invokeMethod(talismanGame, "loadSpaces");
		
		assertEquals(24, spacesMap.size());
		assertNull((Space) spacesMap.get(25));
	}
	
	
	/**
	 * testowane jest czy poprawnie zostali zaladowani poszczegolni poszukiwacze
	 * @throws Exception 
	 */
	@Test
	public void loadAdventurersMapSize() throws Exception{
		Map adventurerMap = (Map) Whitebox.invokeMethod(talismanGame, "loadAdventurers");
		assertEquals(9, adventurerMap.size());
	}
	
	
	@Test
	public void loadAdventurersBeforeFirst() throws Exception{
		Map adventurerMap = (Map) Whitebox.invokeMethod(talismanGame, "loadAdventurers");
		assertEquals(9, adventurerMap.size());
		
		AdventurerInterface adventurer = new Adventurer("FILOZOF", 1, 2, 4, 4 );
		assertNotNull(adventurer);
		
		adventurer = (AdventurerInterface) adventurerMap.get(0);
		assertNull(adventurer);
	}
	
	
	@Test
	public void loadAdventurersFirstName() throws Exception{
		Map adventurerMap = (Map) Whitebox.invokeMethod(talismanGame, "loadAdventurers");
		
		AdventurerInterface adventurer = null;
		adventurer = (AdventurerInterface) adventurerMap.get(1);
		
		assertEquals("FILOZOF", adventurer.getName());
		assertEquals(1, adventurer.getStartingSpace());

	}
	
	@Test
	public void loadAdventurersFirstStrength() throws Exception{
		Map adventurerMap = (Map) Whitebox.invokeMethod(talismanGame, "loadAdventurers");
		
		AdventurerInterface adventurer = null;
		adventurer = (AdventurerInterface) adventurerMap.get(1);
		
		assertEquals("FILOZOF", adventurer.getName());
		assertEquals(2, adventurer.getStrength());
	}
	
	@Test
	public void loadAdventurersFirstCraft() throws Exception{
		Map adventurerMap = (Map) Whitebox.invokeMethod(talismanGame, "loadAdventurers");
		
		AdventurerInterface adventurer = null;
		adventurer = (AdventurerInterface) adventurerMap.get(1);
		
		assertEquals("FILOZOF", adventurer.getName());
		assertEquals(4, adventurer.getCraft());

	}
	
	@Test
	public void loadAdventurersFirstLives() throws Exception{
		Map adventurerMap = (Map) Whitebox.invokeMethod(talismanGame, "loadAdventurers");
		
		AdventurerInterface adventurer = null;
		adventurer = (AdventurerInterface) adventurerMap.get(1);
		
		assertEquals("FILOZOF", adventurer.getName());
		assertEquals(4, adventurer.getLives());

	}
	
	
	@Test
	public void loadAdventurersMiddle() throws Exception{
		Map adventurerMap = (Map) Whitebox.invokeMethod(talismanGame, "loadAdventurers");
		
		AdventurerInterface adventurer = null;
		adventurer = (AdventurerInterface) adventurerMap.get(5);
		
		assertEquals("DRUID", adventurer.getName());
		assertEquals(11, adventurer.getStartingSpace());

	}
	
	@Test
	public void loadAdventurersLast() throws Exception{
		Map adventurerMap = (Map) Whitebox.invokeMethod(talismanGame, "loadAdventurers");
		
		AdventurerInterface adventurer = null;
		adventurer = (AdventurerInterface) adventurerMap.get(9);
		
		assertEquals("PIELGRZYM", adventurer.getName());
		assertEquals(19, adventurer.getStartingSpace());

	}
	
	@Test
	public void loadAdventurersAfterLast() throws Exception{
		Map adventurerMap = (Map) Whitebox.invokeMethod(talismanGame, "loadAdventurers");
		
		AdventurerInterface adventurer =  new Adventurer("FILOZOF", 1, 2, 4, 4 );
		assertNotNull(adventurer);
		
		adventurer = (AdventurerInterface) adventurerMap.get(10);
		assertNull(adventurer);
	}
	
	
	/*
	 * Testy ladowania kart przygody 
	 */
	@Test
	public void loadAdventureCardsListSize() throws Exception{
		LinkedList adventureCardsList = (LinkedList) Whitebox.invokeMethod(talismanGame, "loadAdventureCards");
		assertEquals(21, adventureCardsList.size());
	}
	
	@Test
	public void loadAdventureCardsGetFirst() throws Exception{
		LinkedList adventureCardsList = (LinkedList) Whitebox.invokeMethod(talismanGame, "loadAdventureCards");
		AdventureCard adventureCard = (AdventureCard) adventureCardsList.getFirst();
		assertEquals("Hobgoblin", adventureCard.getName());
	}
	
	/*
	 * Pozostale testy
	 */
	@Test
	public void firstGameInformationPrinted() throws Exception{
		printStreamEasyMock.println(EasyMock.isA(String.class));
		replay(printStreamEasyMock);
		Whitebox.invokeMethod(talismanGame, "firstGameInformation");
		verify(printStreamEasyMock);
	}
	
	
	@Test(expected=StreamException.class)
	public void loadFromXMLExceptionTest() throws Exception{
		String pathName = "incorrect path";
		XStream xstream = new XStream();
		Whitebox.invokeMethod(talismanGame, "loadFromXML", new Object [] {xstream, pathName});
	}
}
