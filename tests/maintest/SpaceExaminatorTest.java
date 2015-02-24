package maintest;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.*;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import main.AdventureCard;
import main.AdventureCardsController;
import main.Adventurer;
import main.CombatController;
import main.GameController;
import main.Space;
import main.SpaceExaminator;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.legacy.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {SpaceExaminator.class, SpaceExaminatorTest.class} )
public class SpaceExaminatorTest {
	private SpaceExaminator spaceExaminator;
	
	private Map spacesMapMock;
	private PrintStream printStreamMock;
	private LinkedList adventureCardStockPileMock;
	private AdventureCardsController adventureCardsControllerMock;
	private GameController gameControllerMock;
	private GameController gameControllerMock2;
	private List inGameAdventurersMock;
	private CombatController combatControllerMock;
	private Adventurer adventurerMock;
	
	@Before
	public void setUp() {
		spacesMapMock = createMock(Map.class);
		printStreamMock = createMock(PrintStream.class);
		adventureCardStockPileMock = createMock(LinkedList.class);
		adventureCardsControllerMock = createMock(AdventureCardsController.class);
		gameControllerMock = createMock(GameController.class);
		gameControllerMock2 = createMock(GameController.class);
		inGameAdventurersMock = createMock(List.class);
		combatControllerMock = createMock(CombatController.class);
		adventurerMock = createMock(Adventurer.class);
		
		expect(gameControllerMock.getPrintStream()).andReturn(printStreamMock);
		expectLastCall().times(1);
		expect(gameControllerMock.getSpacesMap()).andReturn(spacesMapMock);
		expect(gameControllerMock.getAdventureCardStockPile()).andReturn(adventureCardStockPileMock);
		replay(gameControllerMock);
		spaceExaminator = new SpaceExaminator(gameControllerMock);
		verify(gameControllerMock);
	}
	
	
	@Test
	public void encountSpaceEndGame() throws Exception {
		Whitebox.setInternalState(spaceExaminator, "gameController", gameControllerMock2);
		Whitebox.setInternalState(spaceExaminator, "combatController", combatControllerMock);
		expect(gameControllerMock2.getInGameAdventurers()).andReturn(inGameAdventurersMock);
		expect(inGameAdventurersMock.get(0)).andReturn(adventurerMock);
		expect(combatControllerMock.guardianCombatResolver(EasyMock.isA(Adventurer.class))).andReturn(true);
		
		replay(combatControllerMock, gameControllerMock2, inGameAdventurersMock);
		boolean gameStatus = spaceExaminator.encountSpace(17, 1);
		verify(combatControllerMock, gameControllerMock2, inGameAdventurersMock);
		assertEquals(true, gameStatus);
	}
	
	@Test
	public void encountSpaceGuardianNotDefeated() throws Exception {
		Whitebox.setInternalState(spaceExaminator, "gameController", gameControllerMock2);
		Whitebox.setInternalState(spaceExaminator, "combatController", combatControllerMock);
		expect(gameControllerMock2.getInGameAdventurers()).andReturn(inGameAdventurersMock);
		expect(inGameAdventurersMock.get(0)).andReturn(adventurerMock);
		expect(combatControllerMock.guardianCombatResolver(EasyMock.isA(Adventurer.class))).andReturn(false);
		
		replay(combatControllerMock, gameControllerMock2, inGameAdventurersMock);
		boolean gameStatus = spaceExaminator.encountSpace(17, 1);
		verify(combatControllerMock, gameControllerMock2, inGameAdventurersMock);
		assertEquals(false, gameStatus);
	}
	
	@Test
	public void encountSpaceNoNeedToDrawCard() throws Exception {
		Map spacesMapMock = new HashMap();
		spacesMapMock.put(2, new Space(2, 0, "TEST_SPACE", 1, 3, -1));
		Map adventureCardsOnSpacesMock = new HashMap();
		List returnCreaturesListMock = new LinkedList();
		
		Whitebox.setInternalState(spaceExaminator, "spacesMap", spacesMapMock);
		Whitebox.setInternalState(spaceExaminator, "adventureCardsOnSpaces", adventureCardsOnSpacesMock);
		Whitebox.setInternalState(spaceExaminator, "gameController", gameControllerMock2);
		Whitebox.setInternalState(spaceExaminator, "combatController", combatControllerMock);
		
		expect(gameControllerMock2.getInGameAdventurers()).andReturn(inGameAdventurersMock);
		expect(inGameAdventurersMock.get(0)).andReturn(adventurerMock);
		expect(combatControllerMock.combatResolver(EasyMock.isA(Adventurer.class), EasyMock.isA(List.class))).andReturn(returnCreaturesListMock);
		
		replay(gameControllerMock2, inGameAdventurersMock, combatControllerMock);
		boolean endGame = spaceExaminator.encountSpace(2, 1);
		verify(gameControllerMock2, inGameAdventurersMock, combatControllerMock);
		assertEquals(false, endGame);
	}
	
	
	@Test
	public void encountSpaceSpaceDrawOneCard() throws Exception {
		Map spacesMapMock = new HashMap();
		spacesMapMock.put(2, new Space(2, 1, "TEST_SPACE", 1, 3, -1));
		Map adventureCardsOnSpacesMock = new HashMap();
		List returnCreaturesListMock = new LinkedList();
		
		Whitebox.setInternalState(spaceExaminator, "spacesMap", spacesMapMock);
		Whitebox.setInternalState(spaceExaminator, "adventureCardsOnSpaces", adventureCardsOnSpacesMock);
		Whitebox.setInternalState(spaceExaminator, "gameController", gameControllerMock2);
		Whitebox.setInternalState(spaceExaminator, "combatController", combatControllerMock);
		Whitebox.setInternalState(spaceExaminator, "adventureCardsController", adventureCardsControllerMock);
		
		//W return nie wyszlo mi umieszczenie isA
		expect(adventureCardsControllerMock.getCardFromPile()).andReturn(new AdventureCard("TEST_AC", 1, 1));
		expect(gameControllerMock2.getInGameAdventurers()).andReturn(inGameAdventurersMock);
		expect(inGameAdventurersMock.get(0)).andReturn(adventurerMock);
		expect(combatControllerMock.combatResolver(EasyMock.isA(Adventurer.class), EasyMock.isA(List.class))).andReturn(returnCreaturesListMock);
		
		replay(gameControllerMock2, inGameAdventurersMock, combatControllerMock, adventureCardsControllerMock);
		boolean endGame = spaceExaminator.encountSpace(2, 1);
		verify(gameControllerMock2, inGameAdventurersMock, combatControllerMock, adventureCardsControllerMock);
		assertEquals(false, endGame);
	}
	
	
	@Test
	public void encountSpaceTwoCardExpected() throws Exception {
		Map spacesMapMock = new HashMap();
		spacesMapMock.put(2, new Space(2, 2, "TEST_SPACE", 1, 3, -1));
		Map adventureCardsOnSpacesMock = new HashMap();
		List returnCreaturesListMock = new LinkedList();
		
		Whitebox.setInternalState(spaceExaminator, "spacesMap", spacesMapMock);
		Whitebox.setInternalState(spaceExaminator, "adventureCardsOnSpaces", adventureCardsOnSpacesMock);
		Whitebox.setInternalState(spaceExaminator, "gameController", gameControllerMock2);
		Whitebox.setInternalState(spaceExaminator, "combatController", combatControllerMock);
		Whitebox.setInternalState(spaceExaminator, "adventureCardsController", adventureCardsControllerMock);
		
		//W return nie wyszlo mi umieszczenie isA
		expect(adventureCardsControllerMock.getCardFromPile()).andReturn(new AdventureCard("TEST_AC", 1, 1));
		expectLastCall().times(2);
		expect(gameControllerMock2.getInGameAdventurers()).andReturn(inGameAdventurersMock);
		expect(inGameAdventurersMock.get(0)).andReturn(adventurerMock);
		expect(combatControllerMock.combatResolver(EasyMock.isA(Adventurer.class), EasyMock.isA(List.class))).andReturn(returnCreaturesListMock);
		
		replay(gameControllerMock2, inGameAdventurersMock, combatControllerMock, adventureCardsControllerMock);
		boolean endGame = spaceExaminator.encountSpace(2, 1);
		verify(gameControllerMock2, inGameAdventurersMock, combatControllerMock, adventureCardsControllerMock);
		assertEquals(false, endGame);
	}
	
	
	@Test
	public void encountSpaceCreatureReturned() throws Exception {
		Map spacesMapMock = new HashMap();
		spacesMapMock.put(2, new Space(2, 2, "TEST_SPACE", 1, 3, -1));
		Map adventureCardsOnSpacesMock = new HashMap();
		List returnCreaturesListMock = new LinkedList();
		returnCreaturesListMock.add(new AdventureCard("TEST_AC", 1, 1));
		returnCreaturesListMock.add(new AdventureCard("TEST_AC2", 1, 2));
		
		Whitebox.setInternalState(spaceExaminator, "spacesMap", spacesMapMock);
		Whitebox.setInternalState(spaceExaminator, "adventureCardsOnSpaces", adventureCardsOnSpacesMock);
		Whitebox.setInternalState(spaceExaminator, "gameController", gameControllerMock2);
		Whitebox.setInternalState(spaceExaminator, "combatController", combatControllerMock);
		Whitebox.setInternalState(spaceExaminator, "adventureCardsController", adventureCardsControllerMock);
		
		expect(adventureCardsOnSpacesMock.get(2)).andReturn(null);
		//W return nie wyszlo mi umieszczenie isA
		expect(adventureCardsControllerMock.getCardFromPile()).andReturn(new AdventureCard("TEST_AC", 1, 1));
		expectLastCall().times(2);
		expect(gameControllerMock2.getInGameAdventurers()).andReturn(inGameAdventurersMock);
		expect(inGameAdventurersMock.get(0)).andReturn(adventurerMock);
		expect(combatControllerMock.combatResolver(EasyMock.isA(Adventurer.class), EasyMock.isA(List.class))).andReturn(returnCreaturesListMock);
//		adventureCardsOnSpacesMock.put(EasyMock.isA(Integer.class), EasyMock.isA(List.class));
		
		replay(gameControllerMock2, inGameAdventurersMock, combatControllerMock, adventureCardsControllerMock);
		boolean endGame = spaceExaminator.encountSpace(2, 1);
		verify(gameControllerMock2, inGameAdventurersMock, combatControllerMock, adventureCardsControllerMock);
		assertEquals(false, endGame);
	}
	
	
	@Test
	public void encountSpaceNoCardOnStockPiles() throws Exception {
		Map spacesMapMock = new HashMap();
		spacesMapMock.put(2, new Space(2, 2, "TEST_SPACE", 1, 3, -1));
		Map adventureCardsOnSpacesMock = new HashMap();
		List returnCreaturesListMock = new LinkedList();
		returnCreaturesListMock.add(new AdventureCard("TEST_AC", 1, 1));
		returnCreaturesListMock.add(new AdventureCard("TEST_AC2", 1, 2));
		
		Whitebox.setInternalState(spaceExaminator, "spacesMap", spacesMapMock);
		Whitebox.setInternalState(spaceExaminator, "adventureCardsOnSpaces", adventureCardsOnSpacesMock);
		Whitebox.setInternalState(spaceExaminator, "gameController", gameControllerMock2);
		Whitebox.setInternalState(spaceExaminator, "combatController", combatControllerMock);
		Whitebox.setInternalState(spaceExaminator, "adventureCardsController", adventureCardsControllerMock);
		
		expect(adventureCardsOnSpacesMock.get(2)).andReturn(null);
		//W return nie wyszlo mi umieszczenie isA
		expect(adventureCardsControllerMock.getCardFromPile()).andReturn(null);
		expect(gameControllerMock2.getInGameAdventurers()).andReturn(inGameAdventurersMock);
		expect(inGameAdventurersMock.get(0)).andReturn(adventurerMock);
		expect(combatControllerMock.combatResolver(EasyMock.isA(Adventurer.class), EasyMock.isA(List.class))).andReturn(returnCreaturesListMock);
		
		replay(gameControllerMock2, inGameAdventurersMock, combatControllerMock, adventureCardsControllerMock);
		boolean endGame = spaceExaminator.encountSpace(2, 1);
		verify(gameControllerMock2, inGameAdventurersMock, combatControllerMock, adventureCardsControllerMock);
		assertEquals(false, endGame);
	}
}
