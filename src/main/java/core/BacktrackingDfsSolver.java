package core;

import static core.SudokuUtils.*;

public class BacktrackingDfsSolver implements SudokuSolver {

	/**
	 * A backtracking depth first search algorithm.
	 * Assumes a NxN matrix with square sub-boxes with side sqrt(N);
	 * 
	 * @param a NxN matrix
	 * 
	 * @return null if no solution found, or the same matrix that was inputted.
	 */
	@Override
	public int[][] solve(int[][] sudoku) {

		int side = sudoku.length;
		int boxSide = (int) Math.sqrt(side);

		boolean noEmptyCells = true;
		int currentRow = 0;
		int currentCol = 0;

		checkForEmpty: for (int row = 0; row < side; row++) {
			for (int col = 0; col < side; col++) {
				if (sudoku[row][col] == 0) {
					currentRow = row;
					currentCol = col;
					noEmptyCells = false;
					break checkForEmpty;
				}
			}
		}
		if (noEmptyCells) {
			return sudoku;
		}

		for (int choice = 1; choice <= side; choice++) {
			boolean isValid = validForSubBox(sudoku, currentRow, currentCol, choice, boxSide, boxSide)
					&& validForRow(sudoku, currentRow, choice) && validForCol(sudoku, currentCol, choice);

			if (isValid) {
				sudoku[currentRow][currentCol] = choice;
				int[][] solution = solve(sudoku);
				if (solution != null) {
					return solution;
				}
				sudoku[currentRow][currentCol] = 0;
			}
		}
		return null;
	}
}
