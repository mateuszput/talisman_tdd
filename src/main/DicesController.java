package main;

import java.util.Random;

public class DicesController {

	private final int MIN = 1;
	private final int MAX = 6;
	
	/**
	 * Rzucanie kosciami
	 * @param dicesCount
	 * @return
	 */
	public int dicesRoll(int dicesCount) {
		Random drawRandom = new Random();
	    int rollResult = 0;
	    
	    while(dicesCount > 0){
	    	dicesCount--;
	    	rollResult = rollResult + drawRandom.nextInt(MAX - MIN) + MIN;
	    }
	    
		return rollResult;
	}

}
