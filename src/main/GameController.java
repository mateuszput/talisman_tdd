package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class GameController {
	private static final int PLAYERCOUNTSELECTION = 0;
    private static final int ADVENTURERSELECTION = 1;
    private static final int DICEROLL = 2;
    private static final int PATHSELECTION = 3;
    private static final int SPACEINVESTIGATION = 4;
    private static final int ENDGAME = 9;
    private static final int ENDGAME_NOWINNER = 10;
    
    private int turnState = PLAYERCOUNTSELECTION;
    private int playerCount = 0;
    
	private List<Adventurer> inGameAdventurers;
	private PrintStream printStream;
	
	private int rollResult = -1;
	private InputController inputController;
	
	private Map spacesMap;
	private LinkedList adventureCardStockPile;
	
	private SpaceExaminator spaceExaminator;
	
	public GameController(Map adventurersMap, Map spacesMap, LinkedList adventureCardStockPile, PrintStream printStream) {
		this.printStream = printStream;
		this.inGameAdventurers = new LinkedList<Adventurer>();
		
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		this.inputController = new InputController(adventurersMap, spacesMap, printStream, bufferedReader);
		
		this.spacesMap = spacesMap;
		this.adventureCardStockPile = adventureCardStockPile;
		this.spaceExaminator = new SpaceExaminator(this);
	}


	/**
	 * 
	 * @param playerNumber - numer gracza (1-n). Uzywany do pobierania poszukiwacza
	 * z listy. Wtedy uzywamy: numerGracza-1, bo lista jest od 0 do n-1.
	 */
	public void processPlayerTurn(int playerNumber) {
		if (turnState == PLAYERCOUNTSELECTION) {
			playerCount = inputController.selectPlayersCount();
			if(playerCount > 0){
				turnState = ADVENTURERSELECTION;
			}
			this.processPlayerTurn(1);
			
		} else if (turnState == ADVENTURERSELECTION) {
			printStream.println("\nGraczu numer: " + playerNumber + " wybierz poszukiwacza: ");
			
			inputController.showAdventurersList();
			
			Adventurer selectedAdventurer = null;
			try {
				selectedAdventurer = inputController.adventurerSelection();
				selectedAdventurer.setCurrentSpace(selectedAdventurer.getStartingSpace());
				inGameAdventurers.add(selectedAdventurer);
				
				if(playerNumber < playerCount){
					playerNumber++; 
				} else {
					playerNumber = 1;
					turnState = DICEROLL;
				}
				
			} catch (IndexOutOfBoundsException e) {
				printStream.println("\nNie ma poszukiwacza o takim numerze. Wybierz jeszcze raz.");
				turnState = ADVENTURERSELECTION;	
			} catch (NumberFormatException e){
				printStream.println("\nNie ma poszukiwacza o takim numerze. Wybierz jeszcze raz.");
				turnState = ADVENTURERSELECTION;
			}
			
			printStream.println();
			this.processPlayerTurn(playerNumber);
			
		} else if(turnState == DICEROLL){
			if (inGameAdventurers.size() == 0) {
				// wszyscy gracze odpadli - playerCount
				turnState = ENDGAME_NOWINNER;
			} else {

				Adventurer currentAdventurer = inGameAdventurers
						.get(playerNumber - 1);
				printStream.println("\nRuch poszukiwacza: "
						+ currentAdventurer.getName() + ", sila: "
						+ currentAdventurer.getStrength() + ", moc: "
						+ currentAdventurer.getCraft() + ", zycie: "
						+ currentAdventurer.getLives() + ", trofea siły: "
						+ currentAdventurer.getStrengthTrophy() + ", trofea mocy: "
						+ currentAdventurer.getCraftTrophy());

				String spaceName = ((Space) spacesMap.get(currentAdventurer.getCurrentSpace())).getName();
				printStream.println("Jesteś na polu: "
						+ currentAdventurer.getCurrentSpace() + ", " + spaceName);
				
				rollResult = inputController.diceRoll();

				if (rollResult == 0) {
					printStream.println("\nBlad podczas rzucania, rzuc koscia jeszcze raz.");
					turnState = DICEROLL;
				} else {
					turnState = PATHSELECTION;
				}

			}

			this.processPlayerTurn(playerNumber);
			
		} else if(turnState == PATHSELECTION){
			boolean correctPathSelection = false;
			
			correctPathSelection = inputController.directionSelection(rollResult, inGameAdventurers.get(playerNumber-1));
			
			if(!correctPathSelection){
				printStream.println("\nBlad podczas ruszania, wybierz trase jeszcze raz.");
				turnState = PATHSELECTION;
			} else {
				turnState = SPACEINVESTIGATION;
			}
			
			this.processPlayerTurn(playerNumber);
			
		} else if(turnState == SPACEINVESTIGATION){
			
			int spaceInt = inGameAdventurers.get(playerNumber-1).getCurrentSpace();
			
			boolean endGame = spaceExaminator.encountSpace(spaceInt, playerNumber); 
			
			if(endGame == true){
				turnState = ENDGAME;
				
			} else {
				turnState = DICEROLL;
				// playerCount
				if(playerNumber < inGameAdventurers.size()){
					playerNumber++;
				}else{
					playerNumber = 1;
				}
			}
			
			this.processPlayerTurn(playerNumber);
			
		} else if(turnState == ENDGAME){
			printStream.println("Graczu nr " + playerNumber + ". Wygrales gre! >>Fanfary<<");
			
		} else if(turnState == ENDGAME_NOWINNER){
			printStream.println("Wszyscy gracze zostali wyeliminowani. Koniec gry..");
		}
	}
	
	public InputController getInputController(){
		return inputController;
	}
	
	public List<Adventurer> getInGameAdventurers(){
		return inGameAdventurers;
	}
	
	public LinkedList getAdventureCardStockPile(){
		return adventureCardStockPile;
	}
	
	public Map getSpacesMap(){
		return spacesMap;
	}
	
	public PrintStream getPrintStream(){
		return printStream;
	}
	
	public void removeAdventurerFromGame(Adventurer currentAdventurer) {
		inGameAdventurers.remove(currentAdventurer);
		playerCount = inGameAdventurers.size();
	}
}
