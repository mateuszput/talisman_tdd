package maintest;
import static org.junit.Assert.*;

import main.DicesController;

import org.junit.Before;
import org.junit.Test;


/**
 * 
 * @author Mateusz Put
 * 
 * @class KontrolerKosci
 * 
 * @responsibility 
 * Losowanie liczby tak jak na kosci 6cio sciennej
 * Losowanie liczb dla kilku kosci
 * 
 * @collaborators 
 * KontrolaRuchu - przekazywanie wynikow rzutow
 */
public class DicesControllerTest {

	DicesController dicesController;
	
	@Before
	public void setUp() throws Exception {
		dicesController = new DicesController();
	}
	
	/**
	 * Test poprawnego wyniku rzutu jedna koscia.
	 * Wynik powinien pochodzic z zakresu 1-6
	 */
	@Test
	public void dicesRollOneDice(){
		int dicesCount = 1;
		this.dicesRollTest(dicesCount);
	}
	
	/**
	 * Test poprawnego wyniku rzutu dwoma koscmi.
	 * Wynik powinien pochodzic z zakresu 2-12
	 */
	@Test
	public void dicesRollTwoDices(){
		int dicesCount = 2;
		this.dicesRollTest(dicesCount);
        
	}
	
	private void dicesRollTest(int dicesCount){
		int rollResult = dicesController.dicesRoll(dicesCount);
		
		assertTrue(rollResult >= 1 * dicesCount);
        assertTrue(rollResult <= 6 * dicesCount);
	}
}
