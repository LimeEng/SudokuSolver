package core;

import java.util.HashSet;
import java.util.Set;

public final class SudokuUtils {

	private SudokuUtils() {}

	public static boolean isValid(int[][] sudoku, int boxWidth, int boxHeight) {
		return uniqueRows(sudoku) && uniqueCols(sudoku) && uniqueSubBoxes(sudoku, boxWidth, boxHeight);
	}

	public static boolean isSolved(int[][] sudoku, int boxWidth, int boxHeight) {
		if (hasEmptyBoxes(sudoku)) {
			return false;
		}
		return isValid(sudoku, boxWidth, boxHeight);
	}

	private static boolean hasEmptyBoxes(int[][] sudoku) {
		for (int row = 0; row < sudoku.length; row++) {
			for (int col = 0; col < sudoku[row].length; col++) {
				if (sudoku[row][col] == 0) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean uniqueRows(int[][] sudoku) {
		for (int row = 0; row < sudoku.length; row++) {
			Set<Integer> numbers = new HashSet<>();
			for (int col = 0; col < sudoku[row].length; col++) {
				int value = sudoku[row][col];
				if (value == 0) {
					continue;
				}
				boolean unique = numbers.add(value);
				if (!unique) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean uniqueCols(int[][] sudoku) {
		for (int col = 0; col < sudoku.length; col++) {
			Set<Integer> numbers = new HashSet<>();
			for (int row = 0; row < sudoku[col].length; row++) {
				int value = sudoku[row][col];
				if (value == 0) {
					continue;
				}
				boolean unique = numbers.add(value);
				if (!unique) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean uniqueCol(int[][] sudoku, int col) {
		Set<Integer> numbers = new HashSet<>();
		for (int row = 0; row < sudoku[col].length; row++) {
			int value = sudoku[row][col];
			if (value == 0) {
				continue;
			}
			boolean unique = numbers.add(value);
			if (!unique) {
				return false;
			}
		}
		return true;
	}

	public static boolean uniqueRow(int[][] sudoku, int row) {
		Set<Integer> numbers = new HashSet<>();
		for (int col = 0; col < sudoku.length; col++) {
			int value = sudoku[row][col];
			if (value == 0) {
				continue;
			}
			boolean unique = numbers.add(value);
			if (!unique) {
				return false;
			}
		}
		return true;
	}

	private static boolean uniqueSubBoxes(int[][] sudoku, int boxWidth, int boxHeight) {
		int nbrOfBoxesX = sudoku.length / boxWidth;
		int nbrOfBoxesY = sudoku[0].length / boxHeight;

		for (int r = 0; r < nbrOfBoxesX; r++) {
			for (int c = 0; c < nbrOfBoxesY; c++) {
				uniqueSubBoxAt(sudoku, r * boxWidth, c * boxHeight, boxWidth, boxHeight);
			}
		}
		return true;
	}

	public static boolean uniqueSubBoxAt(int[][] sudoku, int row, int col, int boxWidth, int boxHeight) {
		int boxRow = row / boxWidth;
		int boxCol = col / boxHeight;

		Set<Integer> numbers = new HashSet<>();
		for (int r = 0; r < boxHeight; r++) {
			for (int c = 0; c < boxWidth; c++) {
				boolean unique = numbers.add(sudoku[boxHeight * boxRow + r][boxWidth * boxCol + c]);
				if (!unique) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean validForSubBox(int[][] sudoku, int row, int col, int value, int boxWidth, int boxHeight) {
		int boxRow = row / boxWidth;
		int boxCol = col / boxHeight;

		for (int r = 0; r < boxHeight; r++) {
			for (int c = 0; c < boxWidth; c++) {
				if (sudoku[boxHeight * boxRow + r][boxWidth * boxCol + c] == value) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean validForRow(int[][] sudoku, int row, int value) {
		for (int col = 0; col < sudoku[row].length; col++) {
			if (sudoku[row][col] == value) {
				return false;
			}
		}
		return true;
	}

	public static boolean validForCol(int[][] sudoku, int col, int value) {
		for (int row = 0; row < sudoku.length; row++) {
			if (sudoku[row][col] == value) {
				return false;
			}
		}
		return true;
	}

}
