package core;

public class SudokuSerializer {

	public static int[][] deserialize(String text) {
		int squareLength = (int) Math.sqrt(text.length());
		return deserialize(text, squareLength, squareLength);
	}

	public static int[][] deserialize(String text, int width, int height) {
		int[][] sudoku = new int[width][height];

		int index = 0;
		for (int row = 0; row < width; row++) {
			for (int col = 0; col < height; col++) {
				int codePoint = text.codePointAt(index);
				if (Character.isDigit(codePoint)) {
					sudoku[row][col] = Integer.parseInt(new String(Character.toChars(codePoint)));
				} else {
					sudoku[row][col] = 0;
				}
				index++;
			}
		}
		return sudoku;
	}

	public static String serialize(int[][] array) {
		StringBuilder sb = new StringBuilder();
		int width = array.length;
		int height = array[0].length;
		for (int row = 0; row < width; row++) {
			for (int col = 0; col < height; col++) {
				int value = array[row][col];
				if (value == 0) {
					sb.append(".");
				} else {
					sb.append(value);
				}
			}
		}
		return sb.toString();
	}
}
