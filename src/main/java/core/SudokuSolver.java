package core;

public interface SudokuSolver {

	// Returns null if and only if no solution exists
	int[][] solve(int[][] sudoku);
	
}
