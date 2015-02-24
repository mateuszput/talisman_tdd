package maintest;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import main.AdventureCard;
import main.AdventureCardsController;
import main.Adventurer;
import main.CombatController;
import main.DicesController;
import main.GameController;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.legacy.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.powermock.api.easymock.PowerMock.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest( CombatController.class )
public class CombatControllerTest {
	private CombatController combatController;
	private GameController gameControllerMock;
	private GameController gameControllerMock2;
	private DicesController dicesControllerMock;
	private Adventurer adventurer;
	private List spaceCardList;
	private PrintStream printStreamMock;
	private AdventureCardsController adventureCardsControllerMock;
	
	
	@Before
	public void setUp() throws Exception {
		gameControllerMock = createMock(GameController.class);
		gameControllerMock2 = createMock(GameController.class);
		dicesControllerMock = createMock(DicesController.class);
		printStreamMock = createMock(PrintStream.class);
		adventureCardsControllerMock = createMock(AdventureCardsController.class);
		
		expect(gameControllerMock.getPrintStream()).andReturn(printStreamMock);
		replay(gameControllerMock);
		combatController = new CombatController(gameControllerMock, adventureCardsControllerMock);
		verify(gameControllerMock);
	}
	
	@Test
	public void combatResolverOneEnemyAdventurerWins(){
		List strengCreaturesList = null;
		adventurer = new Adventurer("TEST_ADV", 1, 7, 2, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.STRENGTH_ENEMY, 1));
		
		adventureCardsControllerMock.putOnUsedCardStockPile(EasyMock.isA(List.class));
		replay(adventureCardsControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(adventureCardsControllerMock);
		assertEquals(0, strengCreaturesList.size());
	}
	
	@Test
	public void combatResolveOneEnemyStandOff(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		adventurer = new Adventurer("TEST_ADV", 1, 3, 2, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.STRENGTH_ENEMY, 3));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		replay(dicesControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock);
		assertNotNull(strengCreaturesList);
		assertEquals("ADV_ENEMY", strengCreaturesList.get(0).getName());
	}
	
	@Test
	public void combatResolverOneEnemyAdventurerLoses(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		adventurer = new Adventurer("TEST_ADV", 1, 3, 2, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.STRENGTH_ENEMY, 7));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		replay(dicesControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock);
		assertNotNull(strengCreaturesList);
		assertEquals("ADV_ENEMY", strengCreaturesList.get(0).getName());
	}
	
	@Test
	public void combatResolverTwoEnemyAdventurerWins(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		adventurer = new Adventurer("TEST_ADV", 1, 8, 2, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.STRENGTH_ENEMY, 1));
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.STRENGTH_ENEMY, 1));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		replay(dicesControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock);
		assertEquals(0, strengCreaturesList.size());
	}
	
	@Test
	public void combatResolverTwoEnemyAdventurerStandOff(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		adventurer = new Adventurer("TEST_ADV", 1, 4, 2, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.STRENGTH_ENEMY, 2));
		spaceCardList.add(new AdventureCard("ADV_ENEMY2", AdventureCard.STRENGTH_ENEMY, 2));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		replay(dicesControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock);
		assertNotNull(strengCreaturesList);
		assertEquals("ADV_ENEMY", strengCreaturesList.get(0).getName());
		assertEquals("ADV_ENEMY2", strengCreaturesList.get(1).getName());
	}
	
	@Test
	public void combatResolverTwoEnemyAdventurerLoses(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		adventurer = new Adventurer("TEST_ADV", 1, 3, 2, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.STRENGTH_ENEMY, 7));
		spaceCardList.add(new AdventureCard("ADV_ENEMY2", AdventureCard.STRENGTH_ENEMY, 7));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		replay(dicesControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock);
		assertNotNull(strengCreaturesList);
		assertEquals("ADV_ENEMY", strengCreaturesList.get(0).getName());
		assertEquals("ADV_ENEMY2", strengCreaturesList.get(1).getName());
	}
	
	@Test
	public void combatResolverOneCraftEnemyAdventurerWins(){
		List creaturesList = null;
		adventurer = new Adventurer("TEST_ADV", 1, 1, 17, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.CRAFT_ENEMY, 7));
		
		adventureCardsControllerMock.putOnUsedCardStockPile(EasyMock.isA(List.class));
		replay(adventureCardsControllerMock);
		creaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(adventureCardsControllerMock);
		assertEquals(0, creaturesList.size());
	}
	
	@Test
	public void combatResolveOneCraftEnemyStandOff(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		adventurer = new Adventurer("TEST_ADV", 1, 15, 3, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.CRAFT_ENEMY, 3));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		replay(dicesControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock);
		assertNotNull(strengCreaturesList);
		assertEquals("ADV_ENEMY", strengCreaturesList.get(0).getName());
	}
	
	@Test
	public void combatResolverOneCraftEnemyAdventurerLoses(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		adventurer = new Adventurer("TEST_ADV", 1, 8, 2, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.CRAFT_ENEMY, 7));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		replay(dicesControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock);
		assertNotNull(strengCreaturesList);
		assertEquals("ADV_ENEMY", strengCreaturesList.get(0).getName());
	}
	
	@Test
	public void combatResolverTwoCraftEnemyAdventurerWins(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		adventurer = new Adventurer("TEST_ADV", 1, 1, 8, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.CRAFT_ENEMY, 1));
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.CRAFT_ENEMY, 1));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		replay(dicesControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock);
		assertEquals(0, strengCreaturesList.size());
	}
	
	@Test
	public void combatResolverTwoCraftEnemyAdventurerStandOff(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		adventurer = new Adventurer("TEST_ADV", 1, 15, 4, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.CRAFT_ENEMY, 2));
		spaceCardList.add(new AdventureCard("ADV_ENEMY2", AdventureCard.CRAFT_ENEMY, 2));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		replay(dicesControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock);
		assertNotNull(strengCreaturesList);
		assertEquals("ADV_ENEMY", strengCreaturesList.get(0).getName());
		assertEquals("ADV_ENEMY2", strengCreaturesList.get(1).getName());
	}
	
	@Test
	public void combatResolverTwoCraftEnemyAdventurerLoses(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		adventurer = new Adventurer("TEST_ADV", 1, 15, 2, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.CRAFT_ENEMY, 7));
		spaceCardList.add(new AdventureCard("ADV_ENEMY2", AdventureCard.CRAFT_ENEMY, 7));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		replay(dicesControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock);
		assertNotNull(strengCreaturesList);
		assertEquals("ADV_ENEMY", strengCreaturesList.get(0).getName());
		assertEquals("ADV_ENEMY2", strengCreaturesList.get(1).getName());
	}
	
	
	
	@Test
	public void combatResolverTwoEnemyStrengthAndCraftAdventurerWinsBoth(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		adventurer = new Adventurer("TEST_ADV", 1, 4, 4, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.STRENGTH_ENEMY, 1));
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.CRAFT_ENEMY, 1));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2).andReturn(2).andReturn(2);
		replay(dicesControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock);
		assertEquals(0, strengCreaturesList.size());
	}
	
	@Test
	public void combatResolverTwoEnemyStrengthCraftAdventurerWinsOne(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		adventurer = new Adventurer("TEST_ADV", 1, 4, 2, 4);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.STRENGTH_ENEMY, 1));
		spaceCardList.add(new AdventureCard("ADV_ENEMY2", AdventureCard.CRAFT_ENEMY, 7));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2).andReturn(2).andReturn(2);
		replay(dicesControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock);
		assertNotNull(strengCreaturesList);
		assertEquals(1, strengCreaturesList.size());
		assertEquals("ADV_ENEMY2", strengCreaturesList.get(0).getName());
	}
	
	
	@Test
	public void combatResolverAdventurerDie(){
		List <AdventureCard> strengCreaturesList = null;
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		Whitebox.setInternalState(combatController, "gameController", gameControllerMock2);
		adventurer = new Adventurer("TEST_ADV", 1, 15, 2, 1);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.CRAFT_ENEMY, 7));
		spaceCardList.add(new AdventureCard("ADV_ENEMY2", AdventureCard.CRAFT_ENEMY, 7));
		
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		gameControllerMock2.removeAdventurerFromGame(EasyMock.isA(Adventurer.class));
		replay(dicesControllerMock, gameControllerMock2);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(dicesControllerMock, gameControllerMock2);
		assertNotNull(strengCreaturesList);
		assertEquals("ADV_ENEMY", strengCreaturesList.get(0).getName());
		assertEquals("ADV_ENEMY2", strengCreaturesList.get(1).getName());
	}
	
	@Test
	public void countCombatStatusCorrect() throws Exception{
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		expect(dicesControllerMock.dicesRoll(1)).andReturn(1).andReturn(2);
		replay(dicesControllerMock);
		int countCombatResult = (Integer) Whitebox.invokeMethod(combatController, "countCombatStatus", new Integer [] {3,4});
		verify(dicesControllerMock);
		assertEquals(-2, countCombatResult);
	}
	
	
	@Test
	public void combatResolverOneEnemyAdventurerWinsGetStrenhtForTrophy(){
		List strengCreaturesList = null;
		adventurer = new Adventurer("TEST_ADV", 1, 7, 2, 4);
		adventurer.addStrengthTrophy(6);
		adventurer.setCurrentSpace(1);
		spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("ADV_ENEMY", AdventureCard.STRENGTH_ENEMY, 1));
		
		//TODO: dodanie trofeum poszukiwaczowi mock
		adventureCardsControllerMock.putOnUsedCardStockPile(EasyMock.isA(List.class));
		replay(adventureCardsControllerMock);
		strengCreaturesList = combatController.combatResolver(adventurer, spaceCardList);
		verify(adventureCardsControllerMock);
		assertEquals(0, strengCreaturesList.size());
		
		assertEquals(8, adventurer.getStrength());
	}
	
	
	@Test
	public void guardianCombatResolverAdventurerWins(){
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		boolean adventurerWins = false;
		adventurer = new Adventurer("TEST_ADV", 1, 10, 2, 4);
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		
		replay(dicesControllerMock);
		adventurerWins = combatController.guardianCombatResolver(adventurer);
		verify(dicesControllerMock);
		assertEquals(true, adventurerWins);
	}
	
	
	@Test
	public void guardianCombatResolverStandOff(){
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		boolean adventurerWins = false;
		adventurer = new Adventurer("TEST_ADV", 1, 9, 2, 4);
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		
		replay(dicesControllerMock);
		adventurerWins = combatController.guardianCombatResolver(adventurer);
		verify(dicesControllerMock);
		assertEquals(false, adventurerWins);
	}
	
	
	@Test
	public void guardianCombatResolverAdventurerLosesAndLive(){
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		boolean adventurerWins = false;
		adventurer = new Adventurer("TEST_ADV", 1, 7, 2, 4);
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		
		replay(dicesControllerMock);
		adventurerWins = combatController.guardianCombatResolver(adventurer);
		verify(dicesControllerMock);
		assertEquals(false, adventurerWins);
	}
	
	@Test
	public void guardianCombatResolverAdventurerLosesAndDie(){
		Whitebox.setInternalState(combatController, "gameController", gameControllerMock2);
		Whitebox.setInternalState(combatController, "dicesController", dicesControllerMock);
		boolean adventurerWins = false;
		adventurer = new Adventurer("TEST_ADV", 1, 7, 2, 1);
		expect(dicesControllerMock.dicesRoll(1)).andReturn(2).andReturn(2);
		gameControllerMock2.removeAdventurerFromGame(EasyMock.isA(Adventurer.class));
		
		replay(dicesControllerMock, gameControllerMock2);
		adventurerWins = combatController.guardianCombatResolver(adventurer);
		verify(dicesControllerMock, gameControllerMock2);
		assertEquals(false, adventurerWins);
	}
}
