package main;

import java.util.Map;

public class MapController {

	private Map spacesMap;

	public MapController(Map spacesMap) {
		this.spacesMap = spacesMap;
	}

	/**
	 * Oblicza gdzie zakonczy sie ruch w lewo
	 * @param currentSpace
	 * @param rollResult
	 * @return
	 */
	public int calculateLeftMove(int currentSpace, int rollResult) {
		SpaceInterface space = (SpaceInterface) spacesMap.get(currentSpace);
		while (rollResult > 0) {
			currentSpace = space.getLeftPathway();
			space = (SpaceInterface) spacesMap.get(currentSpace);
			rollResult--;
		}
		return space.getSpaceNumber();
	}

	/**
	 * Oblicza gdzie zakonczy sie ruch w prawo
	 * @param currentSpace
	 * @param rollResult
	 * @return
	 */
	public int calculateRightMove(int currentSpace, int rollResult) {
		SpaceInterface space = (SpaceInterface) spacesMap.get(currentSpace);
		while (rollResult > 0) {
			currentSpace = space.getRightPathway();
			space = (SpaceInterface) spacesMap.get(currentSpace);
			rollResult--;
		}
		return space.getSpaceNumber();
	}

	
	public Space getSpace(int spaceNumber){
		return (Space) spacesMap.get(spaceNumber);
	}

}
