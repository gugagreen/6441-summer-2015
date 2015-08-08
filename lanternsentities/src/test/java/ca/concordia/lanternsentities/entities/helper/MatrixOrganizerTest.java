package ca.concordia.lanternsentities.entities.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.enums.TileStack;
import ca.concordia.lanternsentities.helper.MatrixOrganizer;

public class MatrixOrganizerTest {

	@Test
	public void testAddElementEastIncreaseColumns() {
		LakeTile initial = TileStack.T54.getTile();
		LakeTile[][] matrix = new LakeTile[][]{{initial}};
		int lineLength = matrix.length;
		int columnLength = matrix[0].length;
		
		LakeTile value = TileStack.T33.getTile();
		matrix = MatrixOrganizer.addElement(matrix, value, MatrixOrganizer.Direction.EAST, 0, 0);
		
		assertNotNull(matrix);
		assertEquals(lineLength, matrix.length);
		assertEquals(columnLength+1, matrix[0].length);
		assertEquals(initial, matrix[0][0]);
		assertEquals(value, matrix[0][1]);
	}
	
	@Test
	public void testAddElementNorthIncreaseLines() {
		LakeTile initial = TileStack.T54.getTile();
		LakeTile[][] matrix = new LakeTile[][]{{initial}};
		int lineLength = matrix.length;
		int columnLength = matrix[0].length;
		
		LakeTile value = TileStack.T33.getTile();
		matrix = MatrixOrganizer.addElement(matrix, value, MatrixOrganizer.Direction.NORTH, 0, 0);
		
		assertNotNull(matrix);
		assertEquals(lineLength+1, matrix.length);
		assertEquals(columnLength, matrix[0].length);
		assertEquals(value, matrix[0][0]);
		assertEquals(initial, matrix[1][0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddElementConflict() {
		LakeTile initial = TileStack.T54.getTile();
		LakeTile[][] matrix = new LakeTile[][]{{initial}};
		
		LakeTile value = TileStack.T33.getTile();
		matrix = MatrixOrganizer.addElement(matrix, value, MatrixOrganizer.Direction.EAST, 0, 0);
		
		assertNotNull(matrix);
		assertEquals(1, matrix.length);
		assertEquals(2, matrix[0].length);
		
		// try to add a new tile T11 in the same place T33 was placed 
		LakeTile valueConflict = TileStack.T11.getTile();
		matrix = MatrixOrganizer.addElement(matrix, valueConflict, MatrixOrganizer.Direction.EAST, 0, 0);
	}
	
	@Test
	public void testAddElementEastNotIncreasingColumns() {
		LakeTile initial = TileStack.T54.getTile();
		// create matrix with one empty space on the right (east) side
		LakeTile[][] matrix = new LakeTile[][]{{initial, null}};
		int lineLength = matrix.length;
		int columnLength = matrix[0].length;
		
		LakeTile value = TileStack.T33.getTile();
		matrix = MatrixOrganizer.addElement(matrix, value, MatrixOrganizer.Direction.EAST, 0, 0);
		
		assertNotNull(matrix);
		assertEquals(lineLength, matrix.length);
		assertEquals(columnLength, matrix[0].length);
		assertEquals(initial, matrix[0][0]);
		assertEquals(value, matrix[0][1]);
	}
}
