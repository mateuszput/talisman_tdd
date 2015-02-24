package maintest;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import main.MapController;
import main.Space;
import main.SpaceInterface;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;


/**
 * @class KontrolaMapu
 * 
 * @responsibility 
 * Zwracanie pola mapy
 * Obliczanie ruchu na mapie
 * 
 * @collaborators 
 * KontrolerWyboru - do obliczania pozycji poszukiwacza po rzucie koscia 
 */
public class MapControllerTest{
	MapController mapController;
	private Map<Integer, Space> spacesMapMock;
	SpaceInterface spaceMock;
	
	@Before
	public void setUp(){
		 //mock creation
		spacesMapMock = mock(Map.class);
		spaceMock = mock(Space.class);
		
		mapController = new MapController(spacesMapMock);
	}
	
	/**
	 * Test poprawnego obliczania ruchu w lewo na mapie
	 */
	@Test
	public void calculateLeftMove() {
		int currentSpace = 1;
		int rollResult = 2;
		int receivedSpaceNumber = -1;
		
		when(spacesMapMock.get(1)).thenReturn((Space) spaceMock);
		when(spaceMock.getLeftPathway()).thenReturn(2);
		when(spacesMapMock.get(2)).thenReturn((Space) spaceMock);
		when(spaceMock.getSpaceNumber()).thenReturn(3);
		
		receivedSpaceNumber = mapController.calculateLeftMove(currentSpace, rollResult);
		
		InOrder inOrder = inOrder(spacesMapMock, spaceMock);
		inOrder.verify(spacesMapMock).get(1);
		inOrder.verify(spaceMock, times(1)).getLeftPathway();
		inOrder.verify(spacesMapMock).get(2);
		inOrder.verify(spaceMock, times(1)).getLeftPathway();
		inOrder.verify(spaceMock, times(1)).getSpaceNumber();
		
		assertEquals(3, receivedSpaceNumber);
		
	}

	/**
	 * Test poprawnego obliczania ruchu w prawo na mapie
	 */
	@Test
	public void calculateRightMove() {
		int currentSpace = 1;
		int rollResult = 2;
		int receivedSpaceNumber = -1;
		
		when(spacesMapMock.get(1)).thenReturn((Space) spaceMock);
		when(spaceMock.getRightPathway()).thenReturn(24);
		when(spacesMapMock.get(24)).thenReturn((Space) spaceMock);
		when(spaceMock.getSpaceNumber()).thenReturn(23);
		
		receivedSpaceNumber = mapController.calculateRightMove(currentSpace, rollResult);
		
		InOrder inOrder = inOrder(spacesMapMock, spaceMock);
		inOrder.verify(spacesMapMock).get(1);
		inOrder.verify(spaceMock, times(1)).getRightPathway();
		inOrder.verify(spacesMapMock).get(24);
		inOrder.verify(spaceMock, times(1)).getRightPathway();
		inOrder.verify(spaceMock, times(1)).getSpaceNumber();
		
		assertEquals(23, receivedSpaceNumber);
	}
	
}
