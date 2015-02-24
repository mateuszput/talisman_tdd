package main;

public class Adventurer implements AdventurerInterface{

	private String adventurerName;
	private int adventurerStartingSpace;
	private int adventurerCurrentSpace;
	private int adventurerStrength;
	private int adventurerCraft;
	private int adventurerLives;
	private int strengthTrophy;
	private int craftTrophy;
	
	public Adventurer(String adventurerName, int adventurerStartingSpace, int adventurerStrength, int adventurerCraft, int adventurerLives) {
		this.adventurerName = adventurerName;
		this.adventurerStartingSpace = adventurerStartingSpace;
		this.adventurerStrength = adventurerStrength;
		this.adventurerCraft = adventurerCraft;
		this.adventurerLives = adventurerLives;
		this.strengthTrophy = 0;
		this.strengthTrophy = 0;
	}

	public String getName() {
		return adventurerName;
	}

	public int getStartingSpace() {
		return adventurerStartingSpace;
	}

	public int getCurrentSpace() {
		return adventurerCurrentSpace;
	}

	public void setCurrentSpace(int spaceNumber) {
		this.adventurerCurrentSpace = spaceNumber;
	}

	public int getStrength() {
		return adventurerStrength;
	}
	
	public int getCraft() {
		return adventurerCraft;
	}

	public int getLives() {
		return adventurerLives;
	}
	
	public int getStrengthTrophy() {
		return strengthTrophy;
	}
	
	public int getCraftTrophy() {
		return craftTrophy;
	}

	public void removeLive() {
		this.adventurerLives--;
	}

	public boolean isAlive() {
		if(adventurerLives > 0)
			return true;
		return false;
	}

	public boolean addStrengthTrophy(int creaturesPower) {
		boolean strengthIncreased = false;
		strengthTrophy = strengthTrophy + creaturesPower;
		while(strengthTrophy >= 7){
			strengthTrophy = strengthTrophy - 7;
			adventurerStrength++;
			strengthIncreased = true;
		}
		return strengthIncreased;
	}

	public boolean addCraftTrophy(int creaturesPower) {
		boolean craftIncreased = false;
		craftTrophy = craftTrophy + creaturesPower;
		while(craftTrophy >= 7){
			craftTrophy = craftTrophy - 7;
			adventurerCraft++;
			craftIncreased = true;
		}
		return craftIncreased;
	}
}
