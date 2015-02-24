package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;

public class InputController {

	private Map adventurersMap;
	private MapController mapController;
	private PrintStream printStream;
	private BufferedReader bufferedReader;
	
	private final int MAX_PLAYER_COUNT = 4;
	private final int MIN_PLAYER_COUNT = 1;
	
	public InputController(Map charactersMap, Map spacesMap, PrintStream printStream, BufferedReader bufferedReader){
		this.adventurersMap = charactersMap;
		mapController = new MapController(spacesMap);
		this.printStream = printStream;
		this.bufferedReader = bufferedReader;
	}
	
	/**
	 * Wyswietlenie poszukiwaczy zdefiniowanych w systemie do wyboru
	 * adventurer - ryzykant:)
	 */
	public void showAdventurersList(){
		Iterator adventurersIterator = adventurersMap.keySet().iterator();
		int adventurerKey = -1;
		AdventurerInterface adventurer = null;
		while(adventurersIterator.hasNext()){
			adventurerKey = (Integer) adventurersIterator.next();
			adventurer = (AdventurerInterface) adventurersMap.get(adventurerKey);
			printStream.print(" " + adventurerKey + ") ");
			printStream.print(adventurer.getName());
			printStream.println();
		}
	}
	
	/**
	 * Obsluga wybierania poszukiwacza
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public Adventurer adventurerSelection() throws IndexOutOfBoundsException{
		printStream.print("\nWybierz numer poszukiwacza: ");
		
		String selectetNumber = null;
		
		try {
			selectetNumber = bufferedReader.readLine();
		} catch (IOException ioe) {
			printStream.println("Blad pobierania numeru bohatera.");
			throw new IndexOutOfBoundsException();
		}
		
		int number = Integer.parseInt(selectetNumber);
		printStream.print("Wybrałeś: " + number);
		
		if(number > adventurersMap.size()){
			throw new IndexOutOfBoundsException();
			
		} else if(number <= 0){
			throw new IndexOutOfBoundsException();
			
		} else{
			Adventurer selectedAdventurer = null;
			selectedAdventurer = (Adventurer) adventurersMap.get(number);
			if(selectedAdventurer == null){
				throw new IndexOutOfBoundsException();
			}
			
			printStream.print(". Poszukiwacz: " + selectedAdventurer.getName());
			adventurersMap.remove(number);
			return selectedAdventurer;
		}
	}
	
	/**
	 * Akcje wykonywane podczas rzucania koscia przez poszukiwacza.
	 * @return zwraca 0 dla niepoprawnego rzucenia koscia
	 */
	public int diceRoll(){
		int rollResult = 0;
		printStream.println();
		printStream.println("Nacisnij 'r' i Enter, zeby rzucic koscia.");
		String keyPressed = null;
		try {
			keyPressed = bufferedReader.readLine();
			
			if(keyPressed.compareToIgnoreCase("r") == 0){
				DicesController dicesController = new DicesController();
				rollResult = dicesController.dicesRoll(1);
				printStream.println("Wyrzuciles: " + rollResult);
			}
			
		} catch (IOException ioe) {
			printStream.println("Blad podczas rzucania koscia.");
			return rollResult;
		}
		
		return rollResult;
	}


	/**
	 * Obsluga wybierania drogi przez poszukiwacza
	 * @param rollResult
	 * @param adventurer
	 * @return
	 */
	public boolean directionSelection(int rollResult, Adventurer adventurer) {
		
		int currentSpaceInt = adventurer.getCurrentSpace();

		int spaceAfterLeftMoveInt = mapController.calculateLeftMove(currentSpaceInt, rollResult);
		Space spaceAfterLeftMove = mapController.getSpace(spaceAfterLeftMoveInt);
		int spaceAfterRightMoveInt = mapController.calculateRightMove(currentSpaceInt, rollResult);
		Space spaceAfterRightMove = mapController.getSpace(spaceAfterRightMoveInt);
		
		printStream.println("Jezeli wybierzesz ruch w lewo, to zatrzymasz sie na polu: " + spaceAfterLeftMoveInt + ", " + spaceAfterLeftMove.getName());
		printStream.println("Jezeli wybierzesz ruch w prawo, to zatrzymasz sie na polu: " + spaceAfterRightMoveInt + ", " + spaceAfterRightMove.getName());
		printStream.println("Wybierz kierunek ruchu 'l'-lewo, 'p'-prawo: ");
		
		String keyPressed = null;
		try {
			keyPressed = bufferedReader.readLine();
			
			if(keyPressed.compareToIgnoreCase("l") == 0){
				adventurer.setCurrentSpace(spaceAfterLeftMoveInt);
				printStream.println("Stanales na polu: " + spaceAfterLeftMoveInt + ", "+ spaceAfterLeftMove.getName());
				return true;
			} else if(keyPressed.compareToIgnoreCase("p") == 0){
				adventurer.setCurrentSpace(spaceAfterRightMoveInt);
				printStream.println("Stanales na polu: " + spaceAfterRightMoveInt + ", " + spaceAfterRightMove.getName());
				return true;
			} else{
				return false;
			}
			
		} catch (IOException ioe) {
			printStream.println("Blad podczas wyboru drogi.");
			return false;
		}
	}

	public int selectPlayersCount() {
		printStream.println("Wybierz liczbe graczy (1-4): ");
		String keyPressed = null;
		try {
			keyPressed = bufferedReader.readLine();
		} catch (IOException e) {
			printStream.println("Blad podczas wyboru liczby graczy.");
			return -1;
		}
		
		int number = Integer.parseInt(keyPressed);
		
		if(number > MAX_PLAYER_COUNT || number < MIN_PLAYER_COUNT){
			printStream.println("Blad podczas wyboru liczby graczy.");
			return -1;
		}
		
		printStream.println("Ilość graczy w grze: " + number);
		return number;
	}
}
