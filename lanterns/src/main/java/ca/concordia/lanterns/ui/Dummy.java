package ca.concordia.lanterns.ui;

// FIXME - dummy class to print lake - not ready
public class Dummy<T> {

    public static void main(String[] args) {
        Dummy<Integer> d = new Dummy<Integer>();

        Object[][] matrix = new Integer[][]{{null, 1}, {2, null}};
        d.print(matrix);

        matrix = d.addElement(matrix, 3, Direction.EAST, 0, 0);
        d.print(matrix);

        matrix = d.addElement(matrix, 4, Direction.EAST, 1, 2);
        d.print(matrix);
    }

    public Object[][] addElement(Object[][] matrix, T value, Direction direction, int line, int column) {
        Object[][] newboard = null;
        switch (direction) {
            case EAST:
                if (column == 0) {
                    newboard = new Object[matrix.length][matrix[0].length + 1];
                    for (int r = 0; r < matrix.length; r++) {
                        System.arraycopy(matrix[r], 0, newboard[r], 1, matrix[r].length);
                    }
                    newboard[line][0] = value;
                } else {
                    newboard = new Object[matrix.length][matrix[0].length];
                    for (int r = 0; r < matrix.length; r++) {
                        System.arraycopy(matrix[r], 0, newboard[r], 0, matrix[r].length);
                    }
                    newboard[line][column] = value;
                }
                break;
            case SOUTH:

                break;
            case WEST:

                break;
            case NORTH:

                break;

            default:
                break;
        }

        return newboard;
    }

//	public Object[][] copyExpanding(Object[][] matrix, int srcColumn, int destColumn, int srcLine, int destLine) {
//		Object[][] newboard = new Object[3][3]; // build from int board[2][2]
//
//		for (int r = 0; r < matrix.length; r++) {
//			System.arraycopy(matrix[r], srcColumn, newboard[r + 1], srcColumn, matrix[r].length);
//		}
//
//		return newboard;
//	}

    public void print(Object[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println("\t");
        }
        System.out.println("*****");
    }

    enum Direction {
        EAST, SOUTH, WEST, NORTH;
    }

}
