package main;


public class Space implements SpaceInterface {

	private int spaceNumber;
	private int adventureCardsNumberToDraw;
	private String spaceName;
	private String spaceDescription;
	private int leftPathway;
	private int rightPathway;
	private int additionalPathway;
	
	public Space(int spaceNumber, int adventureCardsNumberToDraw, String spaceName, int leftPathway, int rightPathway, int additionalPathway) {
		this.spaceNumber = spaceNumber;
		this.adventureCardsNumberToDraw = adventureCardsNumberToDraw;
		this.spaceName = spaceName;
		this.leftPathway = leftPathway;
		this.rightPathway = rightPathway;
		this.additionalPathway = additionalPathway;
	}

	
	public int getAdditionalPathway() {
		return additionalPathway;
	}

	public int getAdventureCardsNumberToDraw() {
		return adventureCardsNumberToDraw;
	}

	public int getLeftPathway() {
		return leftPathway;
	}

	public String getName() {
		return spaceName;
	}

	public int getSpaceNumber() {
		return spaceNumber;
	}

	public String getDescription() {
		return spaceDescription;
	}

	public int getRightPathway() {
		return rightPathway;
	}

	public void setDescription(String spaceDescription) {
		this.spaceDescription = spaceDescription;
	}

}
