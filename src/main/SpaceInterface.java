package main;

public interface SpaceInterface {

	public abstract int getSpaceNumber();
	public abstract int getAdventureCardsNumberToDraw();
	public abstract String getDescription();
	public abstract String getName();

	public abstract int getLeftPathway();
	public abstract int getRightPathway();
	public abstract int getAdditionalPathway();

	public abstract void setDescription(String description);
}
