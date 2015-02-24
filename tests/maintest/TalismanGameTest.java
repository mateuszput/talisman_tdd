package maintest;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectPrivate;
import static org.powermock.api.easymock.PowerMock.verify;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.replay;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Map;

import main.GameController;
import main.TalismanGame;

import org.easymock.classextension.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

//legacy junit4, bez legacy junit4.4
import org.powermock.modules.junit4.legacy.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * 
 * @author Mateusz Put
 * 
 * @class TalismanGame
 * 
 * @responsibility 
 * Ladowanie pol do systemu
 * Ladowanie poszukiwaczy do systemu
 * 
 * @collaborators 
 * mapaPol - wczytywanie do niej pol
 * mapaPoszukiwaczy - wczytywanie do niej poszukiwaczy
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( TalismanGame.class )
public class TalismanGameTest {

	private PrintStream printStreamEasyMock;
	private GameController gameControllerEasyMock;
	private Map adventurersMapEasyMock;
	private Map spacesMapEasyMock;
	private TalismanGame talismanGame;
	private LinkedList adventureCardStockPile;
	
	@Before
	public void setUp() {
		printStreamEasyMock = createMock(PrintStream.class);
        gameControllerEasyMock = createMock(GameController.class);
        adventurersMapEasyMock = createMock(Map.class);
        spacesMapEasyMock = createMock(Map.class);
        adventureCardStockPile = createMock(LinkedList.class);
        
        talismanGame = new TalismanGame(printStreamEasyMock);
	}

	
	@Test
	public void mainCorrect() throws Exception{
		TalismanGame talismanGameMock = createMock(TalismanGame.class);
		
		expectNew(TalismanGame.class, EasyMock.isA(PrintStream.class)).andReturn(talismanGameMock);
		expect(Whitebox.invokeMethod(talismanGameMock, "loadSpaces")).andReturn(spacesMapEasyMock);
		expect(Whitebox.invokeMethod(talismanGameMock, "loadAdventurers")).andReturn(adventurersMapEasyMock);
		expect(Whitebox.invokeMethod(talismanGameMock, "loadAdventureCards")).andReturn(adventureCardStockPile);
		
		Whitebox.invokeMethod(talismanGameMock, "firstGameInformation");
		expectPrivate(talismanGameMock, "runGame", EasyMock.isA(Map.class), EasyMock.isA(Map.class), EasyMock.isA(LinkedList.class), EasyMock.isA(PrintStream.class));

		replay(talismanGameMock, spacesMapEasyMock, adventurersMapEasyMock, TalismanGame.class);
		
		String [] args = {"arg1", "arg2"};
		TalismanGame.main(args);
		
        verify(talismanGameMock, spacesMapEasyMock, adventurersMapEasyMock, TalismanGame.class);
	}
	
	@Test
	public void runGameCorrect() throws Exception{
		
        expectNew(GameController.class, adventurersMapEasyMock, spacesMapEasyMock, adventureCardStockPile, printStreamEasyMock).andReturn(gameControllerEasyMock);
        
        printStreamEasyMock.println( EasyMock.isA(String.class) );
        expectLastCall().times(2);
        gameControllerEasyMock.processPlayerTurn(1);
        
        replay(gameControllerEasyMock, adventurersMapEasyMock, spacesMapEasyMock, adventureCardStockPile, printStreamEasyMock, GameController.class);
        
//INFO: metoda przyjmuje te mocki ktore zdefiniowalismy 
        Object parametry[] = {adventurersMapEasyMock, spacesMapEasyMock, adventureCardStockPile, printStreamEasyMock};
        Whitebox.invokeMethod(talismanGame, "runGame", parametry);

        verify(gameControllerEasyMock, adventurersMapEasyMock, spacesMapEasyMock, adventureCardStockPile, printStreamEasyMock, GameController.class);

	}

}
