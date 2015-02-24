package main;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CombatController {
	private final static String STRENGTH_STRING = "siła";
	private final static String CRAFT_STRING = "moc";
	
	private DicesController dicesController;
	private GameController gameController;
	private PrintStream printStream;
	private AdventureCardsController adventureCardsController;
	
	public CombatController(GameController gameController, AdventureCardsController adventureCardsController){
		this.gameController = gameController;
		this.dicesController = new DicesController();
		this.printStream = gameController.getPrintStream();
		this.adventureCardsController = adventureCardsController;
	}

	/**
	 * 
	 * @param currentAdventurer
	 * @param spaceCardList - wyciagniete karty
	 * @return 
	 */
	public List combatResolver(Adventurer currentAdventurer, List spaceCardList) {
		List strengCreaturesList = new LinkedList();
		List craftCreaturesList = new LinkedList();
		List returnCreaturesList = new LinkedList();
		
		Iterator listIterator = spaceCardList.iterator();

		AdventureCard adventureCard = null;
		while(listIterator.hasNext()){
			adventureCard = (AdventureCard) listIterator.next();
			if(adventureCard.getAdvenureCardType() == adventureCard.STRENGTH_ENEMY){
				strengCreaturesList.add(adventureCard);
			}else if(adventureCard.getAdvenureCardType() == adventureCard.CRAFT_ENEMY){
				craftCreaturesList.add(adventureCard);
			}
		}

		// rozpatrywanie walki silowej
		if(strengCreaturesList.size() != 0){
			printStream.println("Walka siłowa przeciwko:");
			returnCreaturesList = combatResolver(CombatController.STRENGTH_STRING, strengCreaturesList, currentAdventurer, currentAdventurer.getStrength(), returnCreaturesList);
		}
		
		// rozpatrywanie walki z uzyciem mocy
		if(craftCreaturesList.size() != 0){
			printStream.println("Walka z użyciem mocy przeciwko:");
			returnCreaturesList = combatResolver(CombatController.CRAFT_STRING, craftCreaturesList, currentAdventurer, currentAdventurer.getCraft(), returnCreaturesList);
		}
		return returnCreaturesList;
	}
	
	
	private int countCombatStatus(int adventurerPower, int creaturesPower){
		int adventurerDicesRollResult = dicesController.dicesRoll(1);
		int monstersDicesRollResult = dicesController.dicesRoll(1);
		printStream.println("Poszukiwacz wyrzucil: " + adventurerDicesRollResult);
		printStream.println("Wrog wyrzucil: " + monstersDicesRollResult);
		
		return adventurerPower + adventurerDicesRollResult - creaturesPower - monstersDicesRollResult;
	}
	
	
	private List combatResolver(String fightTypeString, List creaturesList,
			Adventurer currentAdventurer, int adventurerPower,
			List returnCreaturesList) {
		
		int creaturesPower = 0;
		Iterator listIterator = creaturesList.iterator();		
		AdventureCard adventureCard = null;
		while (listIterator.hasNext()) {
			adventureCard = (AdventureCard) listIterator.next();
			printStream.println(adventureCard.getName() + ", "
					+ fightTypeString + ": " + adventureCard.getPower());
			creaturesPower = creaturesPower + adventureCard.getPower();
		}

		int combatStatus = countCombatStatus(adventurerPower, creaturesPower);
		if (combatStatus > 0) {
			// wygral poszukiwacz
			printStream.println("Przeciwnik został pokonany!");
			adventureCardsController.putOnUsedCardStockPile(creaturesList);
			
			boolean counterIncreased = false;
			if(fightTypeString == CombatController.STRENGTH_STRING){
				counterIncreased = currentAdventurer.addStrengthTrophy(creaturesPower);
			} else{
				counterIncreased = currentAdventurer.addCraftTrophy(creaturesPower);
			}
			
			if(counterIncreased){
				printStream.println("Po tej walce twoja " + fightTypeString + " zwiększyła się!");
			}
			
		} else if (combatStatus == 0) {
			// remis
			returnCreaturesList.addAll(creaturesList);
			printStream.println("Walka zakończyła się remisem.");
		} else {
			// wygral potwor
			printStream.println("Przegrałeś walke poszukiwaczu...");
			currentAdventurer.removeLive();
			if (!currentAdventurer.isAlive()) {
				gameController.removeAdventurerFromGame(currentAdventurer);
				printStream.println("Zginąłeś poszukiwaczu!. RIP "
						+ currentAdventurer.getName());
			}

			returnCreaturesList.addAll(creaturesList);
		}
		return returnCreaturesList;
	}

	public boolean guardianCombatResolver(Adventurer currentAdventurer) {
		boolean fightResult = false;
		printStream.println("Walczysz ze strażnikiem...");
		int guarianPower = 9;
		int combatStatus = countCombatStatus(currentAdventurer.getStrength(), guarianPower);
		if(combatStatus > 0){
			printStream.println("Przeciwnik został pokonany!");
			fightResult = true;
		} else if(combatStatus == 0){
			printStream.println("Walka zakończyła się remisem.");
			fightResult = false;
		} else {
			printStream.println("Przegrałeś walke poszukiwaczu...");
			currentAdventurer.removeLive();
			if (!currentAdventurer.isAlive()) {
				gameController.removeAdventurerFromGame(currentAdventurer);
				printStream.println("Zginąłeś poszukiwaczu!. RIP "
						+ currentAdventurer.getName());
			}
			fightResult = false;
		}
		return fightResult;
	}
}
