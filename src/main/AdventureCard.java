package main;

public class AdventureCard {
	public final static int STRENGTH_ENEMY = 0;
	public static final int CRAFT_ENEMY = 1;
	
	private String cardName;
	private int advenureCardType;
	private int power;
	
	public AdventureCard(String cardName, int advenureCardType, int power){
		this.cardName = cardName;
		this.advenureCardType = advenureCardType;
		this.power = power;
	}
	
	public String getName(){
		return this.cardName;
	}
	
	public int getAdvenureCardType(){
		return this.advenureCardType;
	}
	
	public int getPower(){
		return this.power;
	}
}
