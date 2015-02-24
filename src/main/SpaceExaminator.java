package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SpaceExaminator {
	private AdventureCardsController adventureCardsController;
	private Map adventureCardsOnSpaces;
	private Map spacesMap;
	private GameController gameController;
	private CombatController combatController;
	
	
	public SpaceExaminator(GameController gameController) {
		this.gameController = gameController;
		this.spacesMap = gameController.getSpacesMap();
		this.adventureCardsController = new AdventureCardsController(gameController.getAdventureCardStockPile());
		this.adventureCardsOnSpaces = new HashMap();
		this.combatController = new CombatController(gameController, adventureCardsController);
	}

	/**
	 * 1. Losowanie
	 * 2. walka - (klasa CombatController)
	 * 3. zapamietywanie po przegranej
	 * @param spaceInt
	 * @return -1 - koniec gry
	 * 
	 */
	public boolean encountSpace(int spaceInt, int playerNumber) {
		Adventurer currentAdventurer = gameController.getInGameAdventurers().get(playerNumber-1);
		
		if(spaceInt == 17){
			boolean adventurerWins = combatController.guardianCombatResolver(currentAdventurer);
			
			if(adventurerWins)
				return true;
			return false;
		} else{
			
			Space currentSpace = (Space) spacesMap.get(spaceInt);
			int cartToDraw = currentSpace.getAdventureCardsNumberToDraw();
			
			List spaceCardList = (List) adventureCardsOnSpaces.get(spaceInt);
			if(spaceCardList == null){
				spaceCardList = new LinkedList();
			}
			
			while(spaceCardList.size() < cartToDraw){
				AdventureCard adventureCard = adventureCardsController.getCardFromPile();
				if(adventureCard != null){
					spaceCardList.add(adventureCard);
				} else{
					break;
				}
			}
			
			List returnCreaturesList = combatController.combatResolver(currentAdventurer, spaceCardList);
			if(returnCreaturesList.size() != 0){
				adventureCardsOnSpaces.put(spaceInt, returnCreaturesList);
			}
			
			return false;
		}
	}
	
	
	

}
