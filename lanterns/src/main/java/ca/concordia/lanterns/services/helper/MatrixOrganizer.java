package ca.concordia.lanterns.services.helper;


/**
 * Class that
 * 
 * @param <T>
 */
public class MatrixOrganizer<T> {
	
	// TODO - set as singleton

	enum Direction {
		EAST, SOUTH, WEST, NORTH;
	}

	/**
	 * Add element to matrix in one specific direction of an existing element, expanding the matrix if necessary. Both 
	 * the matrix given in the parameter and the matrix returned by this method should contain elements of the type T of 
	 * the new value. 
	 * 
	 * @param matrix The original matrix
	 * @param value	The value to be added
	 * @param direction	The direction, regarding the existing element, where the new value should be placed  
	 * @param line	the line of the existing element
	 * @param column	the column of the existing element
	 * @return The matrix with the value placed, or, if the matrix has been expanded, a new matrix with a copy of the values of
	 *         the original one on it, plus the new value in place.
	 * @throws IllegalArgumentException if the position the new element is being placed is already filled.
	 */
	public Object[][] addElement(Object[][] matrix, T value, Direction direction, int line, int column) {
		Object[][] newboard = null;
		int nLine = line;
		int nColumn = column;

		switch (direction) {
		case EAST:
			// if more east then possible, increase matrix
			if (column == matrix[0].length - 1) {
				newboard = new Object[matrix.length][matrix[0].length + 1];
				for (int r = 0; r < matrix.length; r++) {
					System.arraycopy(matrix[r], 0, newboard[r], 0, matrix[r].length);
				}
			}
			nColumn = column + 1;
			break;
		case SOUTH:
			// if more east then possible, increase matrix
			if (line == matrix.length - 1) {
				newboard = new Object[matrix.length + 1][matrix[0].length];
				for (int r = 0; r < matrix.length; r++) {
					System.arraycopy(matrix[r], 0, newboard[r], 0, matrix[r].length);
				}
			}
			nLine = line + 1;
			break;
		case WEST:
			// if more west then possible, increase matrix
			if (column == 0) {
				newboard = new Object[matrix.length][matrix[0].length + 1];
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
				newboard = new Object[matrix.length + 1][matrix[0].length];
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

		if (newboard[nLine][nColumn] == null) {
			newboard[nLine][nColumn] = value;
		} else {
			throw new IllegalArgumentException("index[" + nLine + "][" + nColumn + "] already taken!");
		}

		return newboard;
	}

	public void print(Object[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println("\t");
		}
		System.out.println("*****");
	}

	public static void main(String[] args) {
		MatrixOrganizer<Integer> d = new MatrixOrganizer<Integer>();

		Object[][] matrix = new Integer[][] { { 1 } };
		d.print(matrix);

		// east - increase
		System.out.println("EAST");
		matrix = d.addElement(matrix, 2, Direction.EAST, 0, 0);
		d.print(matrix);
		// south - increase
		System.out.println("SOUTH");
		matrix = d.addElement(matrix, 3, Direction.SOUTH, 0, 0);
		d.print(matrix);
		// west - increase
		System.out.println("WEST");
		matrix = d.addElement(matrix, 4, Direction.WEST, 0, 0);
		d.print(matrix);
		// north - increase
		System.out.println("NORTH");
		matrix = d.addElement(matrix, 5, Direction.NORTH, 0, 0);
		d.print(matrix);
		// east - stay
		System.out.println("EAST");
		matrix = d.addElement(matrix, 6, Direction.EAST, 0, 1);
		d.print(matrix);
		// south - stay
		System.out.println("SOUTH");
		matrix = d.addElement(matrix, 7, Direction.SOUTH, 1, 0);
		d.print(matrix);
		// west - stay
		System.out.println("WEST");
		matrix = d.addElement(matrix, 8, Direction.WEST, 0, 2);
		d.print(matrix);
		// north - error
		System.out.println("NORTH - error");
		matrix = d.addElement(matrix, 9, Direction.NORTH, 1, 1);
		d.print(matrix);
	}

}
