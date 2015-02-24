package maintest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import main.AdventureCard;
import main.Adventurer;
import main.GameController;
import main.InputController;
import main.Space;
import main.SpaceExaminator;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * 
 * @author Mateusz Put
 * 
 * @class KontrolerGry
 * 
 * @responsibility 
 * Zmienianie stanow gry (tj. rzuty koscia, wybor trasy, rozpatrywanie pola, walka)
 * 
 * @collaborators 
 * Klasy wykonywane w danym stanie
 */
public class GameControllerTest{

	private GameController gameController;
	private Map adventurersMapMock;
	private Map spacesMapMock;
	private PrintStream printStreamMock;
	private InputController inputControllerMock;
	private Adventurer adventurerMock;
	private List<Adventurer> inGameAdventurersMock;
	private LinkedList<AdventureCard> adventureCardStockPileMock;
	private SpaceExaminator spaceExaminatorMock;
	private Space spaceMock;
	
	@Before
	public void setUp() {
		adventurersMapMock = mock(Map.class);
		spacesMapMock = mock(Map.class);
		printStreamMock = mock(PrintStream.class);
		inputControllerMock = mock(InputController.class);
		adventurerMock = mock(Adventurer.class);
		inGameAdventurersMock = mock(List.class);
		adventureCardStockPileMock = mock(LinkedList.class);
		spaceExaminatorMock = mock(SpaceExaminator.class);
		spaceMock = mock(Space.class);
		
		gameController = new GameController(adventurersMapMock, spacesMapMock, adventureCardStockPileMock, printStreamMock);
	}
	
	
	@Test
	public void processPlayerTurnEndGame(){
		Whitebox.setInternalState(gameController, "inputController", inputControllerMock);
		Whitebox.setInternalState(gameController, "inGameAdventurers", inGameAdventurersMock);
		Whitebox.setInternalState(gameController, "spaceExaminator", spaceExaminatorMock);
		
		when(inputControllerMock.selectPlayersCount()).thenReturn(1);
		when(inputControllerMock.adventurerSelection()).thenReturn(adventurerMock);
		when(inputControllerMock.diceRoll()).thenReturn(2);
		when(inputControllerMock.directionSelection(anyInt(), (Adventurer) anyObject() )).thenReturn(true);
		when(inGameAdventurersMock.size()).thenReturn(1);
		when(inGameAdventurersMock.get(anyInt())).thenReturn(adventurerMock);
		when(spacesMapMock.get(anyInt())).thenReturn(spaceMock);
		when(spaceMock.getName()).thenReturn("SPACE_NAME");
		when(adventurerMock.getCurrentSpace()).thenReturn(17);
		when(spaceExaminatorMock.encountSpace(anyInt(), anyInt())).thenReturn(true);
		
		gameController.processPlayerTurn(1);
		
		verify(inputControllerMock).selectPlayersCount();
		verify(inputControllerMock).adventurerSelection();
		verify(inGameAdventurersMock).size();
		verify(inputControllerMock).diceRoll();
		verify(inputControllerMock).directionSelection(anyInt(), (Adventurer) anyObject());
		verify(inGameAdventurersMock, times(3)).get(anyInt());
		verify(spacesMapMock).get(anyInt());
		verify(spaceMock).getName();
		verify(adventurerMock, times(3)).getCurrentSpace();
		verify(spaceExaminatorMock, times(1)).encountSpace(anyInt(), anyInt());
	}
	
	@Test
	public void processPlayerTurnWrongPlayerSelection(){
		Whitebox.setInternalState(gameController, "inputController", inputControllerMock);
		Whitebox.setInternalState(gameController, "inGameAdventurers", inGameAdventurersMock);
		Whitebox.setInternalState(gameController, "spaceExaminator", spaceExaminatorMock);
		
		when(inputControllerMock.selectPlayersCount()).thenReturn(-1);
		when(inputControllerMock.selectPlayersCount()).thenReturn(1);
		when(inputControllerMock.adventurerSelection()).thenReturn(adventurerMock);
		when(inputControllerMock.diceRoll()).thenReturn(2);
		when(inputControllerMock.directionSelection(anyInt(), (Adventurer) anyObject() )).thenReturn(true);
		when(inGameAdventurersMock.size()).thenReturn(1);
		when(inGameAdventurersMock.get(anyInt())).thenReturn(adventurerMock);
		when(spacesMapMock.get(anyInt())).thenReturn(spaceMock);
		when(spaceMock.getName()).thenReturn("SPACE_NAME");
		when(adventurerMock.getCurrentSpace()).thenReturn(17);
		when(spaceExaminatorMock.encountSpace(anyInt(), anyInt())).thenReturn(true);
		
		gameController.processPlayerTurn(1);
		
		verify(inputControllerMock).selectPlayersCount();
		verify(inputControllerMock).selectPlayersCount();
		verify(inputControllerMock).adventurerSelection();
		verify(inGameAdventurersMock).size();
		verify(inputControllerMock).diceRoll();
		verify(inputControllerMock).directionSelection(anyInt(), (Adventurer) anyObject());
		verify(inGameAdventurersMock, times(3)).get(anyInt());
		verify(spacesMapMock).get(anyInt());
		verify(spaceMock).getName();
		verify(adventurerMock, times(3)).getCurrentSpace();
		verify(spaceExaminatorMock, times(1)).encountSpace(anyInt(), anyInt());
	}
	
	@Test
	public void processPlayerTurnAdventurerWrongSelection(){
		Whitebox.setInternalState(gameController, "inputController", inputControllerMock);
		Whitebox.setInternalState(gameController, "inGameAdventurers", inGameAdventurersMock);
		Whitebox.setInternalState(gameController, "spaceExaminator", spaceExaminatorMock);
		
		when(inputControllerMock.selectPlayersCount()).thenReturn(1);
		when(inputControllerMock.adventurerSelection())
			.thenThrow(new IndexOutOfBoundsException())
			.thenReturn(adventurerMock);
		when(inputControllerMock.diceRoll()).thenReturn(2);
		when(inputControllerMock.directionSelection(anyInt(), (Adventurer) anyObject() )).thenReturn(true);
		when(inGameAdventurersMock.size()).thenReturn(1);
		when(inGameAdventurersMock.get(anyInt())).thenReturn(adventurerMock);
		when(spaceExaminatorMock.encountSpace(anyInt(), anyInt())).thenReturn(true);
		
		when(adventurerMock.getCurrentSpace()).thenReturn(17);
		when(spacesMapMock.get(anyInt())).thenReturn(spaceMock);
		when(spaceMock.getName()).thenReturn("SPACE_NAME");
		gameController.processPlayerTurn(1);
		
		verify(inputControllerMock).selectPlayersCount();
		verify(inputControllerMock, times(2)).adventurerSelection();
		verify(inGameAdventurersMock).size();
		verify(inputControllerMock).diceRoll();
		verify(inputControllerMock).directionSelection(anyInt(), (Adventurer) anyObject());
		verify(inGameAdventurersMock, times(3)).get(anyInt());
		verify(spacesMapMock).get(anyInt());
		verify(spaceMock).getName();
		verify(adventurerMock, times(3)).getCurrentSpace();
	}
	
	@Test
	public void processPlayerTurnAdventurerNumberFormatException(){
		Whitebox.setInternalState(gameController, "inputController", inputControllerMock);
		Whitebox.setInternalState(gameController, "inGameAdventurers", inGameAdventurersMock);
		Whitebox.setInternalState(gameController, "spaceExaminator", spaceExaminatorMock);
		
		when(inputControllerMock.selectPlayersCount()).thenReturn(1);
		when(inputControllerMock.adventurerSelection())
			.thenThrow(new NumberFormatException())
			.thenReturn(adventurerMock);
		when(inputControllerMock.diceRoll()).thenReturn(2);
		when(inputControllerMock.directionSelection(anyInt(), (Adventurer) anyObject() )).thenReturn(true);
		when(inGameAdventurersMock.size()).thenReturn(1);
		when(inGameAdventurersMock.get(anyInt())).thenReturn(adventurerMock);
		when(spacesMapMock.get(anyInt())).thenReturn(spaceMock);
		when(spaceMock.getName()).thenReturn("SPACE_NAME");
		when(adventurerMock.getCurrentSpace()).thenReturn(17);
		when(spaceExaminatorMock.encountSpace(anyInt(), anyInt())).thenReturn(true);
		
		gameController.processPlayerTurn(1);
		
		verify(inputControllerMock).selectPlayersCount();
		verify(inputControllerMock, times(2)).adventurerSelection();
		verify(inGameAdventurersMock).size();
		verify(inputControllerMock).diceRoll();
		verify(inputControllerMock).directionSelection(anyInt(), (Adventurer) anyObject());
		verify(inGameAdventurersMock, times(3)).get(anyInt());
		verify(spacesMapMock).get(anyInt());
		verify(spaceMock).getName();
		verify(adventurerMock, times(3)).getCurrentSpace();
		verify(spaceExaminatorMock, times(1)).encountSpace(anyInt(), anyInt());
	}
	
	@Test
	public void processPlayerTurnDicesError(){
		Whitebox.setInternalState(gameController, "inputController", inputControllerMock);
		Whitebox.setInternalState(gameController, "inGameAdventurers", inGameAdventurersMock);
		Whitebox.setInternalState(gameController, "spaceExaminator", spaceExaminatorMock);
		
		when(inputControllerMock.selectPlayersCount()).thenReturn(1);
		when(inputControllerMock.adventurerSelection()).thenReturn(adventurerMock);
		when(inputControllerMock.diceRoll())
			.thenReturn(0)
			.thenReturn(2);
		when(inputControllerMock.directionSelection(anyInt(), (Adventurer) anyObject() )).thenReturn(true);
		when(inGameAdventurersMock.size()).thenReturn(1);
		when(inGameAdventurersMock.get(anyInt())).thenReturn(adventurerMock);
		when(spacesMapMock.get(anyInt())).thenReturn(spaceMock);
		when(spaceMock.getName()).thenReturn("SPACE_NAME");
		when(adventurerMock.getCurrentSpace()).thenReturn(17);
		when(spaceExaminatorMock.encountSpace(anyInt(), anyInt())).thenReturn(true);
		
		gameController.processPlayerTurn(1);
		
		verify(inputControllerMock).selectPlayersCount();
		verify(inputControllerMock).adventurerSelection();
		verify(inGameAdventurersMock, times(2)).size();
		verify(inputControllerMock, times(2)).diceRoll();
		verify(inputControllerMock).directionSelection(anyInt(), (Adventurer) anyObject());
		verify(inGameAdventurersMock, times(4)).get(anyInt());
		verify(spacesMapMock, times(2)).get(anyInt());
		verify(spaceMock, times(2)).getName();
		verify(adventurerMock, times(5)).getCurrentSpace();
		verify(spaceExaminatorMock, times(1)).encountSpace(anyInt(), anyInt());
	}
	
	@Test
	public void processPlayerTurnIncorrectPathSelection(){
		Whitebox.setInternalState(gameController, "inputController", inputControllerMock);
		Whitebox.setInternalState(gameController, "inGameAdventurers", inGameAdventurersMock);
		Whitebox.setInternalState(gameController, "spaceExaminator", spaceExaminatorMock);
		
		when(inputControllerMock.selectPlayersCount()).thenReturn(1);
		when(inputControllerMock.adventurerSelection()).thenReturn(adventurerMock);
		when(inputControllerMock.diceRoll()).thenReturn(2);
		when(inputControllerMock.directionSelection(anyInt(), (Adventurer) anyObject() ))
			.thenReturn(false)
			.thenReturn(true);
		when(inGameAdventurersMock.size()).thenReturn(1);
		when(inGameAdventurersMock.get(anyInt())).thenReturn(adventurerMock);
		when(spacesMapMock.get(anyInt())).thenReturn(spaceMock);
		when(spaceMock.getName()).thenReturn("SPACE_NAME");
		when(adventurerMock.getCurrentSpace()).thenReturn(17);
		when(spaceExaminatorMock.encountSpace(anyInt(), anyInt())).thenReturn(true);
		
		gameController.processPlayerTurn(1);
		
		verify(inputControllerMock).selectPlayersCount();
		verify(inputControllerMock).adventurerSelection();
		verify(inGameAdventurersMock).size();
		verify(inputControllerMock).diceRoll();
		verify(inputControllerMock, times(2)).directionSelection(anyInt(), (Adventurer) anyObject());
		verify(inGameAdventurersMock, times(4)).get(anyInt());
		verify(spacesMapMock, times(1)).get(anyInt());
		verify(spaceMock, times(1)).getName();
		verify(adventurerMock, times(3)).getCurrentSpace();
		verify(spaceExaminatorMock, times(1)).encountSpace(anyInt(), anyInt());
	}
	
	@Test
	public void processPlayerTurnCorrectSpace(){
		Whitebox.setInternalState(gameController, "inputController", inputControllerMock);
		Whitebox.setInternalState(gameController, "inGameAdventurers", inGameAdventurersMock);
		Whitebox.setInternalState(gameController, "spaceExaminator", spaceExaminatorMock);
		
		when(inputControllerMock.selectPlayersCount()).thenReturn(1);
		when(inputControllerMock.adventurerSelection()).thenReturn(adventurerMock);
		when(inputControllerMock.diceRoll()).thenReturn(2);
		when(inputControllerMock.directionSelection(anyInt(), (Adventurer) anyObject() )).thenReturn(true);
		when(inGameAdventurersMock.get(anyInt())).thenReturn(adventurerMock);
		when(spacesMapMock.get(anyInt())).thenReturn(spaceMock);
		when(spaceMock.getName()).thenReturn("SPACE_NAME");
		when(adventurerMock.getCurrentSpace()).thenReturn(2).thenReturn(2).thenReturn(17);
		when(inGameAdventurersMock.size()).thenReturn(1);
		when(spaceExaminatorMock.encountSpace(anyInt(), anyInt()))
			.thenReturn(false)
			.thenReturn(true);
		
		gameController.processPlayerTurn(1);
		
		verify(inputControllerMock).selectPlayersCount();
		verify(inputControllerMock).adventurerSelection();
		verify(inGameAdventurersMock, times(3)).size();
		verify(inputControllerMock, times(2)).diceRoll();
		verify(inputControllerMock, times(2)).directionSelection(anyInt(), (Adventurer) anyObject());
		verify(inGameAdventurersMock, times(6)).get(anyInt());
		verify(adventurerMock, times(6)).getCurrentSpace();
		verify(spacesMapMock, times(2)).get(anyInt());
		verify(spaceMock, times(2)).getName();
		verify(spaceExaminatorMock, times(2)).encountSpace(anyInt(), anyInt());
	}
	
	
	@Test
	public void processPlayerTurnEndGameNoWinner(){
		Whitebox.setInternalState(gameController, "inputController", inputControllerMock);
		Whitebox.setInternalState(gameController, "inGameAdventurers", inGameAdventurersMock);
		
		when(inputControllerMock.selectPlayersCount()).thenReturn(1);
		when(inputControllerMock.adventurerSelection()).thenReturn(adventurerMock);
		when(inGameAdventurersMock.size()).thenReturn(0);

		gameController.processPlayerTurn(1);
		
		verify(inputControllerMock).selectPlayersCount();
		verify(inputControllerMock).adventurerSelection();
		verify(inGameAdventurersMock).size();
	}
	
	
	@Test
	public void processPlayerTurnTwoPlayersEndGame(){
		Whitebox.setInternalState(gameController, "inputController", inputControllerMock);
		Whitebox.setInternalState(gameController, "inGameAdventurers", inGameAdventurersMock);
		
		when(inputControllerMock.selectPlayersCount()).thenReturn(2);
		when(inputControllerMock.adventurerSelection()).thenReturn(adventurerMock);
		when(inGameAdventurersMock.size()).thenReturn(0);
		
		gameController.processPlayerTurn(1);
		
		verify(inputControllerMock).selectPlayersCount();
		verify(inputControllerMock, times(2)).adventurerSelection();
		verify(inGameAdventurersMock).size();
	}
	
	@Test
	public void processPlayerTurnTwoDicesRoll(){
		Whitebox.setInternalState(gameController, "inputController", inputControllerMock);
		Whitebox.setInternalState(gameController, "inGameAdventurers", inGameAdventurersMock);
		Whitebox.setInternalState(gameController, "spaceExaminator", spaceExaminatorMock);
		
		when(inputControllerMock.selectPlayersCount()).thenReturn(2);
		when(inputControllerMock.adventurerSelection()).thenReturn(adventurerMock);
		when(inputControllerMock.diceRoll()).thenReturn(2);
		when(inputControllerMock.directionSelection(anyInt(), (Adventurer) anyObject() )).thenReturn(true);
		when(inGameAdventurersMock.get(anyInt())).thenReturn(adventurerMock);
		when(spacesMapMock.get(anyInt())).thenReturn(spaceMock);
		when(spaceMock.getName()).thenReturn("SPACE_NAME");
		when(adventurerMock.getCurrentSpace()).thenReturn(2).thenReturn(2).thenReturn(17);
		when(inGameAdventurersMock.size()).thenReturn(3);
		when(spaceExaminatorMock.encountSpace(anyInt(), anyInt()))
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(true);
		
		gameController.processPlayerTurn(2);
		
		verify(inputControllerMock).selectPlayersCount();
		verify(inputControllerMock, times(2)).adventurerSelection();
		verify(inGameAdventurersMock, times(7)).size();
		verify(inputControllerMock, times(4)).diceRoll();
		verify(inputControllerMock, times(4)).directionSelection(anyInt(), (Adventurer) anyObject());
		verify(inGameAdventurersMock, times(12)).get(anyInt());
		verify(adventurerMock, times(12)).getCurrentSpace();
		verify(spacesMapMock, times(4)).get(anyInt());
		verify(spaceMock, times(4)).getName();
		verify(spaceExaminatorMock, times(4)).encountSpace(anyInt(), anyInt());
	}
	
	@Test
	public void getInputControllerCorrect(){
		Whitebox.setInternalState(gameController, "inputController", inputControllerMock);
		InputController returnedInputController;
		returnedInputController = gameController.getInputController();
		assertEquals(inputControllerMock, returnedInputController);
	}
	
	@Test
	public void getInGameAdventurers(){
		Whitebox.setInternalState(gameController, "inGameAdventurers", inGameAdventurersMock);
		List returnedInGameAdventurers;
		returnedInGameAdventurers = gameController.getInGameAdventurers();
		assertEquals(inGameAdventurersMock, returnedInGameAdventurers);
	}
	
	@Test
	public void removeAdventurerFromGameCorrect(){
		Adventurer currentAdventurerMock1 = new Adventurer("TEST_ADV", 1, 3, 2, 4);
		Adventurer currentAdventurerMock2 = new Adventurer("TEST_ADV2", 1, 5, 6, 7);
		List inGameAdventurersMock = new LinkedList();
		inGameAdventurersMock.add(currentAdventurerMock1);
		inGameAdventurersMock.add(currentAdventurerMock2);
		Whitebox.setInternalState(gameController, "playerCount", 2);
		Whitebox.setInternalState(gameController, "inGameAdventurers", inGameAdventurersMock);
		
		gameController.removeAdventurerFromGame(currentAdventurerMock1);
		int returnedPlayerCount = (Integer) Whitebox.getInternalState(gameController, "playerCount");
		List returnedInGameAdventurers = (List) Whitebox.getInternalState(gameController, "inGameAdventurers");
		
		assertEquals(1, returnedPlayerCount);
		assertEquals(1, returnedInGameAdventurers.size());
		assertEquals("TEST_ADV2", ((Adventurer) returnedInGameAdventurers.get(0)).getName() );
	}
}
