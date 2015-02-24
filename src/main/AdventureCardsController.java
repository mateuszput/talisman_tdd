package main;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AdventureCardsController {
	private LinkedList <AdventureCard> adventureCardStockPile;
	private LinkedList <AdventureCard> usedCardStockPile;
	
	AdventureCardsController(LinkedList <AdventureCard> adventureCardStockPile){
		this.adventureCardStockPile = adventureCardStockPile;
		Collections.shuffle(adventureCardStockPile);
		usedCardStockPile = new LinkedList();
	}
	
	
	public AdventureCard getCardFromPile() {
		if(adventureCardStockPile.size() == 0){
			if(usedCardStockPile.size() == 0){
				return null;
			}else{
				LinkedList <AdventureCard> temporaryReference;
				temporaryReference = adventureCardStockPile;
				adventureCardStockPile = usedCardStockPile;
				usedCardStockPile = temporaryReference;
			}
		}
		return adventureCardStockPile.removeFirst();
	}


	public void putOnUsedCardStockPile(List spaceCardList) {
		usedCardStockPile.addAll(spaceCardList);
	}

}
