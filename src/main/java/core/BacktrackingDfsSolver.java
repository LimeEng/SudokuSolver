package core;

import static core.SudokuUtils.*;

public class BacktrackingDfsSolver implements SudokuSolver {

	/**
	 * A backtracking depth first search algorithm.
	 * Assumes a NxN matrix with square sub-boxes with side sqrt(N);
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

//	private static boolean validBox(int[][] sudoku, int row, int col, int value, int boxSide) {
//		int boxRow = row / boxSide;
//		int boxCol = col / boxSide;
//
//		for (int r = 0; r < boxSide; r++) {
//			for (int c = 0; c < boxSide; c++) {
//				if (sudoku[boxSide * boxRow + r][boxSide * boxCol + c] == value) {
//					return false;
//				}
//			}
//		}
//		return true;
//	}
//
//	private static boolean validForRow(int[][] sudoku, int row, int value) {
//		for (int col = 0; col < sudoku[row].length; col++) {
//			if (sudoku[row][col] == value) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	private static boolean validForCol(int[][] sudoku, int col, int value) {
//		for (int row = 0; row < sudoku.length; row++) {
//			if (sudoku[row][col] == value) {
//				return false;
//			}
//		}
//		return true;
//	}

}
