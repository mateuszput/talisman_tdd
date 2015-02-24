package maintest;

import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;

import main.AdventureCard;
import main.AdventureCardsController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.legacy.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AdventureCardsController.class)
public class AdventureCardsControllerTest {

	private LinkedList<AdventureCard> adventureCardStockPileMock;
	private LinkedList<AdventureCard> usedCardStockPileMock;
	private AdventureCardsController adventureCardsController;
	
	@Before
	public void setUp() throws Exception {
		adventureCardStockPileMock = new LinkedList();
		adventureCardStockPileMock.add(new AdventureCard("TEST_AC1", 1, 1));
		Class[] parameterTypes = new Class[] { LinkedList.class };
		Object[] arguments = new Object[] { adventureCardStockPileMock };
		adventureCardsController = Whitebox.invokeConstructor(AdventureCardsController.class, parameterTypes, arguments);
	}

	@Test
	public void getCardFromPileCorrect() throws Exception {
		AdventureCard adventureCard = adventureCardsController
				.getCardFromPile();
		
		assertEquals("TEST_AC1", adventureCard.getName());
	}

	@Test
	public void getCardFromPileBothStockPileEmpty() throws Exception {
		adventureCardStockPileMock = new LinkedList();
		Whitebox.setInternalState(adventureCardsController, "adventureCardStockPile", adventureCardStockPileMock);
		AdventureCard adventureCard = adventureCardsController.getCardFromPile();
		assertNull(adventureCard);
	}

	
	@Test
	public void getCardFromPileAdventureStockPileEmptyAreUsedCards() throws Exception {
		adventureCardStockPileMock = new LinkedList();
		usedCardStockPileMock = new LinkedList();
		usedCardStockPileMock.add(new AdventureCard("TEST_AC1", 1, 1));
		Whitebox.setInternalState(adventureCardsController, "adventureCardStockPile", adventureCardStockPileMock);
		Whitebox.setInternalState(adventureCardsController, "usedCardStockPile", usedCardStockPileMock);
		AdventureCard adventureCard = adventureCardsController.getCardFromPile();
		assertEquals("TEST_AC1", adventureCard.getName());
	}
	
	
	@Test
	public void putOnUsedCardStockPileStockEmpty() throws Exception {
		List spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("TEST_AC1", 1, 1));
		List usedCardStockPile;
		
		adventureCardsController.putOnUsedCardStockPile(spaceCardList);
		usedCardStockPile = (List) Whitebox.getInternalState(adventureCardsController, "usedCardStockPile");
		assertEquals("TEST_AC1", ( (AdventureCard) usedCardStockPile.get(0)).getName());
	}
	
	@Test
	public void putOnUsedCardStockPileStockNotEmpty() throws Exception {
		usedCardStockPileMock = new LinkedList();
		usedCardStockPileMock.add(new AdventureCard("TEST_AC1", 1, 1));
		Whitebox.setInternalState(adventureCardsController, "usedCardStockPile", usedCardStockPileMock);
		List spaceCardList = new LinkedList();
		spaceCardList.add(new AdventureCard("TEST_AC2", 1, 1));
		
		adventureCardsController.putOnUsedCardStockPile(spaceCardList);
		List usedCardStockPile = (List) Whitebox.getInternalState(adventureCardsController, "usedCardStockPile");
		assertEquals("TEST_AC1", ( (AdventureCard) usedCardStockPile.get(0)).getName());
		assertEquals("TEST_AC2", ( (AdventureCard) usedCardStockPile.get(1)).getName());
	}
}
