package ca.concordia.lanternsentities.helper;

import ca.concordia.lanternsentities.LakeTile;

/**
 * Class that helps to manage a bi-dimensional matrix of {@link LakeTile}.
 */
public class MatrixOrganizer {

	public enum Direction {
		EAST, SOUTH, WEST, NORTH;
	}
	
	public static boolean isEmpty(LakeTile[][] matrix) {
		return (matrix == null) || (matrix.length == 0) || (matrix[0].length == 0);
	}
	
	/**
	 * Get the line and column of a tile inside a matrix give its id.
	 * @param matrix	The matrix to search.
	 * @param id	The id of the tile.
	 * @return	An array with {line,column} values, or null, if id is not found in the matrix.
	 */
	public static int[] getLineColumn(LakeTile[][] matrix, String id) {
		int[] lineColumn = null;
		int line = -1;
		int column = -1;
		if (!isEmpty(matrix)) {
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					if ((matrix[i][j] != null) && (matrix[i][j].getId() != null) && (matrix[i][j].getId().equals(id))) {
						line = i;
						column = j;
						break;
					}
				}
				if (line != -1) {
					break;
				}
			}
		}
		if ((line >= 0) && (column >= 0)) {
			lineColumn = new int[] {line, column};
		}
		return lineColumn;
	}
	
	/**
	 * Find a tile in a matrix by its id.
	 * @param matrix	The matrix to search.
	 * @param id	The id of the tile.
	 * @return	The {@link LakeTile} in the matrix that has the same id as the parameter, or null if not found.
	 */
	public static LakeTile findTile(LakeTile[][] matrix, String id) {
		LakeTile found = null;
		int[] lineColumn = getLineColumn(matrix, id);
		if (lineColumn != null) {
			found = matrix[lineColumn[0]][lineColumn[1]];
		}
		return found;
	}
	
	/**
	 * Count the number of valid elements in the matrix.
	 * @param matrix
	 * @return
	 */
	public static int count(LakeTile[][] matrix) {
		int count = 0;
		if (!isEmpty(matrix)) {
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					if (matrix[i][j] != null) {
						count++;
					}
				}
			}
		}
		return count;
	}

	/**
	 * Add element to matrix in one specific direction of an existing element, expanding the matrix if necessary.
	 * 
	 * @param matrix
	 *            The original matrix
	 * @param value
	 *            The {@link LakeTile} to be added
	 * @param direction
	 *            The direction, regarding the existing element, where the new value should be placed
	 * @param line
	 *            the line of the existing element
	 * @param column
	 *            the column of the existing element
	 * @return The matrix with the value placed, or, if the matrix has been expanded, a new matrix with a copy of the values of
	 *         the original one on it, plus the new value in place.
	 * @throws IllegalArgumentException
	 *             If the matrix was not initialized; if the position [line,column] is invalid; or if the position 
	 *             the new element is being placed is already filled.
	 */
	public static LakeTile[][] addElement(LakeTile[][] matrix, LakeTile value, Direction direction, int line, int column) {
		if(isEmpty(matrix)) {
			throw new IllegalArgumentException("Matrix should have been initialized with at least one element.");
		}
		if ((line >= matrix.length) || (column >= matrix[0].length)) {
			throw new IllegalArgumentException("There is no position in matrix with line [" + line + "] and column [" 
					+ column + "]");
		}
		
		LakeTile[][] newboard = null;
		int nLine = line;
		int nColumn = column;
		
		LakeTile existingTile = matrix[line][column];
		if (existingTile == null) {
			throw new IllegalArgumentException("There is no existing element on index[" + line + "][" + column + "]!");
		}

		switch (direction) {
		case EAST:
			// if more east then possible, increase matrix
			if (column == matrix[0].length - 1) {
				newboard = new LakeTile[matrix.length][matrix[0].length + 1];
				for (int r = 0; r < matrix.length; r++) {
					System.arraycopy(matrix[r], 0, newboard[r], 0, matrix[r].length);
				}
			}
			nColumn = column + 1;
			break;
		case SOUTH:
			// if more east then possible, increase matrix
			if (line == matrix.length - 1) {
				newboard = new LakeTile[matrix.length + 1][matrix[0].length];
				for (int r = 0; r < matrix.length; r++) {
					System.arraycopy(matrix[r], 0, newboard[r], 0, matrix[r].length);
				}
			}
			nLine = line + 1;
			break;
		case WEST:
			// if more west then possible, increase matrix
			if (column == 0) {
				newboard = new LakeTile[matrix.length][matrix[0].length + 1];
				for (int r = 0; r < matrix.length; r++) {
					System.arraycopy(matrix[r], 0, newboard[r], 1, matrix[r].length);
				}
			} else {
				nColumn = column - 1;
			}
			break;
		case NORTH:
			// if more north then possible, increase matrix
			if (line == 0) {
				newboard = new LakeTile[matrix.length + 1][matrix[0].length];
				for (int r = 0; r < matrix.length; r++) {
					System.arraycopy(matrix[r], 0, newboard[r + 1], 0, matrix[r].length);
				}
			} else {
				nLine = line - 1;
			}
			break;
		default:
			break;
		}

		// if could fit in matrix, just populate it
		if (newboard == null) {
			newboard = matrix;
		}

		// add new element to newboard and set its adjacents
		if (newboard[nLine][nColumn] == null) {
			newboard[nLine][nColumn] = value;
			setAdjacents(newboard, value, nLine, nColumn);
		} else {
			throw new IllegalArgumentException("index[" + nLine + "][" + nColumn + "] already taken!");
		}

		return newboard;
	}
	
	private static void setAdjacents(LakeTile[][] newboard, LakeTile value, int nLine, int nColumn) {
		// if there is east, set east
		if (nColumn < newboard[0].length - 1) {
			LakeTile other = newboard[nLine][nColumn+1];
			if (other != null) {
				other.getSides()[Direction.WEST.ordinal()].setAdjacent(value);
				value.getSides()[Direction.EAST.ordinal()].setAdjacent(other);
			}
		}
		// if there is south, set south
		if (nLine < newboard.length - 1) {
			LakeTile other = newboard[nLine+1][nColumn];
			if (other != null) {
				other.getSides()[Direction.NORTH.ordinal()].setAdjacent(value);
				value.getSides()[Direction.SOUTH.ordinal()].setAdjacent(other);
			}
		}
		// if there is west, set west
		if (nColumn > 0) {
			LakeTile other = newboard[nLine][nColumn-1];
			if (other != null) {
				other.getSides()[Direction.EAST.ordinal()].setAdjacent(value);
				value.getSides()[Direction.WEST.ordinal()].setAdjacent(other);
			}
		}
		// if there is north, set north
		if (nLine > 0) {
			LakeTile other = newboard[nLine-1][nColumn];
			if (other != null) {
				other.getSides()[Direction.SOUTH.ordinal()].setAdjacent(value);
				value.getSides()[Direction.NORTH.ordinal()].setAdjacent(other);
			}
		}
	}
	
	/**
     * Can be used to obtain the index of an opposite side of a lake tile.
     * Useful when checking for adjacent tiles.
     *
     * @return The index of the opposing  side of a lake tile index.
     */
    public static Direction getOppositeTileSideIndex(Direction direction) {
    	Direction opposite = null;
    
    	switch (direction) {
		case EAST:
			opposite = Direction.WEST;
			break;
		case SOUTH:
			opposite = Direction.NORTH;
			break;
		case WEST:
			opposite = Direction.EAST;
			break;
		case NORTH:
			opposite = Direction.SOUTH;
			break;
    	}
    	return opposite;
    }

}
