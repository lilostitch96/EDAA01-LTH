import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestSudoku {
	BacktrackingSudoku sudoku;
	int[][] grid;

	@BeforeEach
	void setUp() throws Exception {
		sudoku = new BacktrackingSudoku();
		grid = new int[9][9];
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSolveEmptyGrid() {
		assertTrue(sudoku.solve(grid));
	}

	@Test
	void testValidGrid() {
		int[][] premadeSudoku = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
				{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 3, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };
		int[][] solution = { { 5, 4, 8, 1, 7, 9, 3, 6, 2 }, { 3, 7, 6, 8, 2, 4, 9, 1, 5 },
				{ 1, 9, 2, 5, 6, 3, 8, 7, 4 }, { 7, 8, 4, 2, 1, 6, 5, 9, 3 }, { 2, 5, 9, 3, 8, 7, 6, 4, 1 },
				{ 6, 3, 1, 9, 4, 5, 7, 2, 8 }, { 4, 1, 5, 6, 9, 8, 2, 3, 7 }, { 8, 6, 7, 4, 3, 2, 1, 5, 9 },
				{ 9, 2, 3, 7, 5, 1, 4, 8, 6 } };
		sudoku.solve(premadeSudoku);
		for (int i = 0; i < premadeSudoku.length; i++) {
			for (int j = 0; j < premadeSudoku.length; j++) {
				assertTrue(premadeSudoku[i][j] == solution[i][j]);
			}
		}
	}

	@Test
	void testImpossibleSudoku() {
		int[][] impossibleSudoku = { { 1, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
				{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 3, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };
		assertFalse(sudoku.solve(impossibleSudoku));
	}

}
