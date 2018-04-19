package core;

public class SudokuPrinter {

	public static String sudokuToPrettyString(int[][] array) {
		int width = array.length;
		int height = array[0].length;
		int boxWidth = (int) Math.sqrt(width);
		int boxHeight = (int) Math.sqrt(height);
		return sudokuToPrettyString(array, width, height, boxWidth, boxHeight);
	}

	public static String sudokuToPrettyString(int[][] array, int width, int height, int boxWidth, int boxHeight) {
		StringBuilder sb = new StringBuilder();

		topBorder(sb, width, boxWidth);
		for (int row = 0; row < width; row++) {
			rowBorder(sb);
			for (int column = 0; column < width; column++) {
				value(sb, array[row][column]);
				rightColumnBorder(sb, column + 1, width, boxWidth);
			}
			rowBorder(sb);
			sb.append("\n");
			bottomRowBorder(sb, row + 1, width, height, boxWidth, boxHeight);
		}
		bottomBorder(sb, width, boxWidth);
		return sb.toString();
	}

	private static void horizontalLine(StringBuilder sb, int width, int boxWidth, String leftMost, String normal,
			String boxDelimiter, String rightMost) {
		sb.append(leftMost);
		for (int i = 0; i < width; i++) {
			if (i % boxWidth == 0 && i != 0) {
				sb.setLength(sb.length() - 1);
				sb.append(boxDelimiter);
			}
			sb.append(normal);
		}
		sb.setLength(sb.length() - 1);
		sb.append(rightMost);
	}

	private static void topBorder(StringBuilder sb, int width, int boxWidth) {
		horizontalLine(sb, width, boxWidth, "╔", "═══╤", "╦", "╗\n");
	}

	private static void rowBorder(StringBuilder sb) {
		sb.append("║");
	}

	private static void value(StringBuilder sb, int value) {
		String output = value != 0 ? String.valueOf(value) : " ";
		int length = output.length();
		switch (length) {
		case 1:
			sb.append(" " + output + " ");
			break;
		case 2:
			sb.append(output + " ");
			break;
		default:
			sb.append(output);
			break;
		}
	}

	private static void rightColumnBorder(StringBuilder sb, int column, int width, int boxWidth) {
		if (column == width) {
			return;
		}
		if (column % boxWidth == 0) {
			sb.append("║");
		} else {
			sb.append("│");
		}
	}

	private static void bottomRowBorder(StringBuilder sb, int row, int width, int height, int boxWidth, int boxHeight) {
		if (row == height) {
			return;
		}
		if (row % boxHeight == 0) {
			horizontalLine(sb, width, boxWidth, "╠", "═══╪", "╬", "╣\n");
		} else {
			horizontalLine(sb, width, boxWidth, "╟", "───┼", "╫", "╢\n");
		}
	}

	private static void bottomBorder(StringBuilder sb, int width, int boxWidth) {
		horizontalLine(sb, width, boxWidth, "╚", "═══╧", "╩", "╝\n");
	}
}
