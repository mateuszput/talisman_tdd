package maintest;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import main.Adventurer;
import main.InputController;
import main.Space;

import org.junit.Before;
import org.junit.Test;


/**
 * @class KontrolaWyboru
 * 
 * @responsibility 
 * Akcje zwiazane z wyborami, interakcja z graczem
 * Wyswietlanie poszukiwaczy do wyboru
 * Wybieranie poszukiwacza przez gracza
 * Rzucanie koscia przez gracza
 * Wybor trasy przez gracza
 * Przesuwanie poszukiwaczy na mapie
 * Obliczanie i wyswietlanie miejsca zakonczen ruchu poszukiwacza
 * 
 * @collaborators 
 * MapaPoszukiwaczy - mapa zawierajaca liste poszukiwaczy
 * KontrolerMapy - 
 * MapaPol - mapa z polami na planszy
 * 
 */
public class InputControllerTest{
	
	InputController inputController;
	
	private Map adventurersMapMock;
	private Map spacesMapMock;
	private PrintStream printStreamMock;
	private BufferedReader bufferedReaderMock;
	private Adventurer adventurerMock;
	private Space spaceMock;
	private Iterator adventurersIteratorMock;
	
	@Before
	public void setUp(){
		adventurersMapMock = mock(Map.class); 
		spacesMapMock = mock(Map.class);
		printStreamMock = mock(PrintStream.class);
		bufferedReaderMock = mock(BufferedReader.class);
		adventurerMock = mock(Adventurer.class);
		spaceMock = mock(Space.class);
		adventurersIteratorMock = mock(Iterator.class);
		
		inputController = new InputController(adventurersMapMock, spacesMapMock, printStreamMock, bufferedReaderMock);
	}
	
	
	@Test
	public void showAdventurersList(){
		Set setMock = mock(Set.class);
		when(adventurersMapMock.keySet()).thenReturn(setMock);
		when(setMock.iterator()).thenReturn(adventurersIteratorMock);
		when(adventurersIteratorMock.hasNext())
			.thenReturn(true)
			.thenReturn(true)
			.thenReturn(true)
			.thenReturn(false);
		when(adventurersIteratorMock.next())
			.thenReturn(1)
			.thenReturn(2)
			.thenReturn(3);
		when(adventurersMapMock.get(anyInt())).thenReturn(adventurerMock);
		printStreamMock.print(anyString());
		
		inputController.showAdventurersList();
		
		verify(adventurersMapMock).keySet();
		verify(setMock).iterator();
		verify(adventurersIteratorMock, times(4)).hasNext();
		verify(adventurersIteratorMock, times(3)).next();
		verify(adventurersMapMock, times(3)).get(anyInt());
		verify(printStreamMock, times(7)).print(anyString());
	}
	
	@Test
	public void showAdventurersListEmptyMap(){
		Set setMock = mock(Set.class);
		when(adventurersMapMock.keySet()).thenReturn(setMock);
		when(setMock.iterator()).thenReturn(adventurersIteratorMock);
		when(adventurersIteratorMock.hasNext())
			.thenReturn(false);
		when(adventurersIteratorMock.next())
			.thenReturn(3);
		when(adventurersMapMock.get(anyInt())).thenReturn(adventurerMock);
		printStreamMock.print(anyString());
		
		inputController.showAdventurersList();
		
		verify(adventurersMapMock).keySet();
		verify(setMock).iterator();
		verify(adventurersIteratorMock, times(1)).hasNext();
		verify(adventurersIteratorMock, times(0)).next();
		verify(adventurersMapMock, times(0)).get(anyInt());
		//Czemu 1, a nie 0 ?? przeciez w kodzie nie wywoluje nigdzie print
		verify(printStreamMock, times(1)).print(anyString());
	}
	
	
	/**
	 * Poprawny wybor poszukiwacza.
	 * @throws IOException 
	 */
	@Test
	public void adventurerSelectionCorrect() throws IOException{
		
		Adventurer receivedAdventurer = null;
		when(bufferedReaderMock.readLine()).thenReturn("2");
		when(adventurersMapMock.size()).thenReturn(9);
		when(adventurersMapMock.get(anyInt())).thenReturn(adventurerMock);
		when(adventurerMock.getName()).thenReturn("DRUID");
		
		receivedAdventurer = inputController.adventurerSelection();
		
		verify(bufferedReaderMock).readLine();
		verify(adventurersMapMock).size();
		verify(adventurersMapMock).get(anyInt());
		verify(adventurerMock).getName();
		assertEquals(adventurerMock, receivedAdventurer);
	}
	
	/**
	 * Niepoprawny wybor poszukiwacza. Poszukiwacz spoza zakresu.
	 * @throws IOException 
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void adventurerSelection0NotInList() throws IOException{
		when(bufferedReaderMock.readLine()).thenReturn("0");
		when(adventurersMapMock.size()).thenReturn(9);
		
		inputController.adventurerSelection();
		
		verify(bufferedReaderMock).readLine();
		verify(adventurersMapMock).size();
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void adventurerSelection100NotInList() throws IOException{
		when(bufferedReaderMock.readLine()).thenReturn("100");
		when(adventurersMapMock.size()).thenReturn(9);
		
		inputController.adventurerSelection();
		
		verify(bufferedReaderMock).readLine();
		verify(adventurersMapMock).size();
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void adventurerSelectionIOException() throws IOException {
		when(bufferedReaderMock.readLine()).thenThrow(new IOException());
		
		inputController.adventurerSelection();
		
		verify(bufferedReaderMock).readLine();
	}
	
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void adventurerSelectionAdventurerAlredySelected() throws IOException {
		when(bufferedReaderMock.readLine()).thenReturn("1");
		when(adventurersMapMock.size()).thenReturn(9);
		when(adventurersMapMock.get(1)).thenReturn(null);
		
		inputController.adventurerSelection();
		
		verify(bufferedReaderMock).readLine();
		verify(adventurersMapMock).size();
		verify(adventurersMapMock).get(1);
	}
	
	/**
	 * Test poprawnego rzutu koscia, uzytkownik wciska 'r'
	 * @throws IOException 
	 */
	@Test
	public void diceRollCorrect() throws IOException{
		int rollResult = -1;
		when(bufferedReaderMock.readLine()).thenReturn("r");
		
		rollResult = inputController.diceRoll();
		if(rollResult<1){
			fail();
		}else if (rollResult>6){
			fail();
		}
		verify(bufferedReaderMock).readLine();
		
	}
	
	/**
	 * Test niepoprawnego rzutu koscia, uzytkownik wciska cos innego niz 'r', w tescie 'l'
	 * @throws IOException 
	 */
	@Test
	public void diceRollWrongKeyPressed() throws IOException{
		int rollResult = -1;
		when(bufferedReaderMock.readLine()).thenReturn("l");
		
		rollResult = inputController.diceRoll();
		assertEquals(0, rollResult);
		
		verify(bufferedReaderMock).readLine();
	}
	
	
	@Test
	public void diceRollWrongInput() throws IOException{
		int rollResult = -1;
		when(bufferedReaderMock.readLine()).thenThrow(new IOException());
		
		rollResult = inputController.diceRoll();
		
		assertEquals(0, rollResult);
		verify(bufferedReaderMock).readLine();
	}
	
	/**
	 * Test poprawnego wyboru trasy, uzytkownik wciska 'l'
	 * @throws IOException 
	 */
	@Test
	public void directionSelectionLeft() throws IOException{
		int rollResult = 2;
		boolean directionSelectionResult = false;
		
		when(bufferedReaderMock.readLine()).thenReturn("l");
		when(adventurerMock.getCurrentSpace()).thenReturn(1);
		when(spacesMapMock.get(anyInt())).thenReturn(spaceMock);
		
		directionSelectionResult = inputController.directionSelection(rollResult, adventurerMock);
		
		verify(adventurerMock).getCurrentSpace();
		verify(spacesMapMock, times(8)).get(anyInt());
		assertTrue(directionSelectionResult);
	}
	
	/**
	 * Test poprawnego wyboru trasy, uzytkownik wciska 'p'
	 * @throws IOException 
	 */
	@Test
	public void directionSelectionRight() throws IOException{
		int rollResult = 2;
		boolean directionSelectionResult = false;
		
		when(bufferedReaderMock.readLine()).thenReturn("p");
		when(adventurerMock.getCurrentSpace()).thenReturn(1);
		when(spacesMapMock.get(anyInt())).thenReturn(spaceMock);
		
		directionSelectionResult = inputController.directionSelection(rollResult, adventurerMock);
		
		verify(adventurerMock).getCurrentSpace();
		verify(spacesMapMock, times(8)).get(anyInt());
		assertTrue(directionSelectionResult);
	}
	
	/**
	 * Test niepoprawnego wyboru trasy, tzn. innego niz: 'p' i 'l'. W tescie 'r'
	 * @throws IOException 
	 */
	@Test
	public void directionSelectionIncorrect() throws IOException{
		int rollResult = 2;
		boolean directionSelectionResult = true;
		
		when(bufferedReaderMock.readLine()).thenReturn("r");
		when(adventurerMock.getCurrentSpace()).thenReturn(1);
		when(spacesMapMock.get(anyInt())).thenReturn(spaceMock);
		
		directionSelectionResult = inputController.directionSelection(rollResult, adventurerMock);
		
		verify(adventurerMock).getCurrentSpace();
		verify(spacesMapMock, times(8)).get(anyInt());
		
		assertFalse(directionSelectionResult);
	}
	
	@Test
	public void directionSelectionWrongInput() throws IOException{
		int rollResult = 2;
		boolean directionSelectionResult = true;

		when(bufferedReaderMock.readLine()).thenThrow(new IOException());
		when(adventurerMock.getCurrentSpace()).thenReturn(1);
		when(spacesMapMock.get(anyInt())).thenReturn(spaceMock);
		
		directionSelectionResult = inputController.directionSelection(rollResult, adventurerMock);
		
		verify(adventurerMock).getCurrentSpace();
		verify(spacesMapMock, times(8)).get(anyInt());
		
		assertFalse(directionSelectionResult);
	}
	
	
	
	@Test
	public void selectPlayerCountCorrectSelection() throws IOException{
		printStreamMock.println(anyString());
		when(bufferedReaderMock.readLine()).thenReturn("1");
		
		int playersNumber = inputController.selectPlayersCount();
		
		verify(printStreamMock, times(3)).println(anyString());
		verify(bufferedReaderMock).readLine();
		assertEquals(1, playersNumber);
	}
	
	@Test
	public void selectPlayerCountIOException() throws IOException{
		printStreamMock.println(anyString());
		when(bufferedReaderMock.readLine()).thenThrow(new IOException());
		
		int playersNumber = inputController.selectPlayersCount();
		
		verify(printStreamMock, times(3)).println(anyString());
		verify(bufferedReaderMock).readLine();
		assertEquals(-1, playersNumber);
	}
	
	@Test
	public void selectPlayerPlayerNoOutOfRangeToSmall() throws IOException{
		printStreamMock.println(anyString());
		when(bufferedReaderMock.readLine()).thenReturn("0");
		
		int playersNumber = inputController.selectPlayersCount();
		
		verify(printStreamMock, times(3)).println(anyString());
		verify(bufferedReaderMock).readLine();
		assertEquals(-1, playersNumber);
	}
	
	@Test
	public void selectPlayerPlayerNoOutOfRangeToBig() throws IOException{
		printStreamMock.println(anyString());
		when(bufferedReaderMock.readLine()).thenReturn("5");
		
		int playersNumber = inputController.selectPlayersCount();
		
		verify(printStreamMock, times(3)).println(anyString());
		verify(bufferedReaderMock).readLine();
		assertEquals(-1, playersNumber);
	}
}
