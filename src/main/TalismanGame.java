package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.thoughtworks.xstream.XStream;


public class TalismanGame {
	private GameController gameController;
	private PrintStream printStream;
	
	public TalismanGame(PrintStream printStream) {
		this.printStream = printStream;
	}

	
	public static void main(String [] args){
		PrintStream printStream = System.out;
		Map spacesMap = null;
		Map adventurersMap = null;
		LinkedList adventureCardStockPile = null;
		
		TalismanGame talismanGame = new TalismanGame(printStream);
		
		spacesMap = talismanGame.loadSpaces();
		adventurersMap = talismanGame.loadAdventurers();
		adventureCardStockPile = talismanGame.loadAdventureCards();
		talismanGame.firstGameInformation();
		
		talismanGame.runGame(adventurersMap, spacesMap, adventureCardStockPile, printStream);
	}
	

	private void runGame(Map adventurersMap, Map spacesMap, LinkedList adventureCardStockPile, PrintStream printStream) {
		printStream.println("\n\n");
		printStream.println("Witaj poszukiwaczu!");
		
		gameController = new GameController(adventurersMap, spacesMap, adventureCardStockPile, printStream);
		gameController.processPlayerTurn(1);
	}

	
	private Map loadSpaces(){
		String pathName = "/media/MATEUSZ/studia-lekkie metodologie projektowania/eclipseMiM/magia/MapSpaces.xml";
		
		XStream xstream = new XStream();
		xstream.alias("spaces", Map.class);
		xstream.alias("space", Space.class);
		
		return (HashMap) loadFromXML(xstream, pathName);
	}
	
	private Map loadAdventurers(){
		String pathName = "/media/MATEUSZ/studia-lekkie metodologie projektowania/eclipseMiM/magia/Adventurers.xml";
				
		XStream xstream = new XStream();
		xstream.alias("adventurers", Map.class);
		xstream.alias("adventurer", Adventurer.class);
		
		return (HashMap) loadFromXML(xstream, pathName);
	}
	
	
	private LinkedList loadAdventureCards() {
		String pathName = "/media/MATEUSZ/studia-lekkie metodologie projektowania/eclipseMiM/magia/AdventureCards.xml";
		
		XStream xstream = new XStream();
		xstream.alias("adventureCards", LinkedList.class);
		xstream.alias("adventureCard", AdventureCard.class);
		
		return (LinkedList) loadFromXML(xstream, pathName);
	}
	
	
	private Object loadFromXML(XStream xstream, String pathName){
		
		File file = new File(pathName);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			printStream.println("Blad wczytywania pliku");
		}
		
		return xstream.fromXML(inputStream);
	}
	
	
	private void firstGameInformation(){
		printStream.println("Witaj w grze Magia i Miecz.");
	}
	
}
