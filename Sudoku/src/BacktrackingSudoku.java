
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code BacktrackingSudoku} class describes a game of Sudoku in which the
 * rules are described as following: The idea is that a 9x9 grid will be made in
 * which a number (1-9) can be inserted. The rules are to fill the grid but on
 * every row, column and section only one of each unique number 1-9 are allowed.
 * 
 * <p>
 * <b> Sections </b>
 * </p>
 * 
 * The section consists of nine 3x3 grids. They are split up evenly on the grid
 * so the first will be on the top left corner, row 1-3 and column 1-3 will make
 * the first section. Next section is added on the side of the first section
 * (row 1-3 and column 4-6) and so on.
 * 
 * <p>
 * <b> The solver </b>
 * </p>
 * To solve a Sudoku, simply use the {@link #solve} method. Create a 9x9 matrix,
 * fill in some valid numbers into the matrix and then use solve to complete the
 * matrix. If the numbers are all according to the rules of the game, the solve
 * will fill in the missing numbers into the matrix and return true, otherwise
 * it return false.
 * 
 * <p>
 * <b> Example: </b>
 * </p>
 * int[][] board = { <br>
 * { 8, 0, 0, 0, 0, 0, 0, 0, 0 }, <br>
 * { 0, 0, 3, 6, 0, 0, 0, 0, 0 }, <br>
 * { 0, 7, 0, 0, 9, 0, 2, 0, 0 }, <br>
 * { 0, 5, 0, 0, 0, 7, 0, 0, 0 }, <br>
 * { 0, 0, 0, 0, 4, 5, 7, 0, 0 }, <br>
 * { 0, 0, 0, 1, 0, 0, 0, 3, 0 }, <br>
 * { 0, 0, 1, 0, 0, 0, 0, 6, 8 }, <br>
 * { 0, 0, 8, 5, 0, 0, 0, 1, 0 }, <br>
 * { 0, 9, 0, 0, 0, 0, 4, 0, 0 } <br>
 * }; <br>
 * <p>
 * {@link #solve}(board); <br>
 * <p>
 * After the solve method has run, the matrix will look like this: <br>
 * <p>
 * { 8, 1, 2, 7, 5, 3, 6, 4, 9, }, <br>
 * { 9, 4, 3, 6, 8, 2, 1, 7, 5, }, <br>
 * { 6, 7, 5, 4, 9, 1, 2, 8, 3, }, <br>
 * { 1, 5, 4, 2, 3, 7, 8, 9, 6, }, <br>
 * { 3, 6, 9, 8, 4, 5, 7, 2, 1, }, <br>
 * { 2, 8, 7, 1, 6, 9, 5, 3, 4, }, <br>
 * { 5, 2, 1, 9, 7, 4, 3, 6, 8, }, <br>
 * { 4, 3, 8, 5, 2, 6, 9, 1, 7, }, <br>
 * { 7, 9, 6, 3, 1, 8, 4, 5, 2, } <br>
 * 
 * <p>
 * 
 * @author Eneas Hållsten
 *         </p>
 *
 */

public class BacktrackingSudoku {
	
	private static final int GRID_SIZE = 9;
	private static final int SUBSECTION_SIZE = 3;
	private static final int GRID_START_INDEX = 0;

	private static final int NO_VALUE = 0;
	private static final int MIN_VALUE = 1;
	private static final int MAX_VALUE = 9;

	/**
	 * Backtracking method to solve the Sudoku according to the rules.
	 * 
	 * @param grid
	 *            the matrix of numbers
	 * @return true if possible to solve
	 */
	public boolean solve(int[][] grid) {
		for (int row = GRID_START_INDEX; row < GRID_SIZE; row++) {
			for (int column = GRID_START_INDEX; column < GRID_SIZE; column++) {
				if (grid[row][column] == NO_VALUE) { // If no user input have been put into the position
					for (int tryNbr = MIN_VALUE; tryNbr <= MAX_VALUE; tryNbr++) {
						grid[row][column] = tryNbr;
						if (isValid(grid, row, column) && solve(grid)) { // Check if the number added to the matrix is
																			// according to the rules, if so solve the
																			// grid recursive again with the new number
																			// in the grid
							return true;
						}
						grid[row][column] = NO_VALUE; // Reset the positions value
					}
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the current Matrix grid is accepted by the rules of the game.
	 * 
	 * @param grid
	 *            the matrix
	 * @param row
	 * @param column
	 * @return true if the grid is according to the rules
	 */
	private boolean isValid(int[][] grid, int row, int column) {
		return checkRow(grid, row) && checkCol(grid, column) && checkRegion(grid, row, column);
	}

	/**
	 * Checks if the column in question does not contain any same numbers.
	 * 
	 * @param grid
	 *            the sudoku field
	 * @param col
	 * @return true if the column is accepted by the rules
	 */
	private boolean checkCol(int[][] grid, int col) {
		Set<Integer> set = new HashSet<>();
		for (int i = GRID_START_INDEX; i < GRID_SIZE; i++) {
			if (grid[i][col] != 0) {
				if (!set.add(grid[i][col])) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the row in question does not contain any same numbers.
	 * 
	 * @param grid
	 *            the sudoku field
	 * @param row
	 * @return true if the row is accepted by the rules
	 */
	private boolean checkRow(int[][] grid, int row) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = GRID_START_INDEX; i < GRID_SIZE; i++) {
			if (grid[row][i] != 0) {
				if (!set.add(grid[row][i])) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the region (in which the position row, col is in) contains any same
	 * numbers.
	 * 
	 * @param grid
	 *            the sudoku field
	 * @param row
	 * @param col
	 * @return true is the region is acepted by the rules
	 */
	private boolean checkRegion(int[][] grid, int row, int col) {
		Set<Integer> set = new HashSet<Integer>();
		int regionRowStart = (row / SUBSECTION_SIZE) * SUBSECTION_SIZE;
		int regionRowEnd = regionRowStart + SUBSECTION_SIZE;
		int regionColStart = (col / SUBSECTION_SIZE) * SUBSECTION_SIZE;
		int regionColEnd = regionColStart + SUBSECTION_SIZE;

		for (int i = regionRowStart; i < regionRowEnd; i++) {
			for (int j = regionColStart; j < regionColEnd; j++) {
				if (grid[i][j] != 0) {
					if (!set.add(grid[i][j])) {
						return false;
					}
				}
			}
		}
		return true;
	}
}