package view;

import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class SudokuCanvas extends Canvas {

	private final int width;
	private final int height;
	private final int boxWidth;
	private final int boxHeight;

	private final int[][] sudoku;

	private Insets insets;

	private int selectedRow = -1;
	private int selectedCol = -1;

	public SudokuCanvas(int width, int height, int boxWidth, int boxHeight) {
		this(Insets.EMPTY, width, height, boxWidth, boxHeight);
	}

	public SudokuCanvas(Insets insets, int width, int height, int boxWidth, int boxHeight) {
		super();
		this.width = width;
		this.height = height;
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		this.sudoku = new int[width][height];
		this.insets = insets;

		createMouseListener();
		createKeyListener();

		widthProperty().addListener(evt -> draw());
		heightProperty().addListener(evt -> draw());
	}

	private void createMouseListener() {
		this.setOnMouseClicked(e -> {
			this.requestFocus();
			int mouseX = (int) (e.getX() - insets.getLeft());
			int mouseY = (int) (e.getY() - insets.getTop());
			int row = (int) (mouseY / getCellHeight());
			int col = (int) (mouseX / getCellWidth());
			if (row == selectedRow && col == selectedCol) {
				selectedRow = -1;
				selectedCol = -1;
			} else {
				selectedRow = row;
				selectedCol = col;
			}
			draw();
		});
	}

	private void setValueAt(int value, int row, int col) {
		if (row >= 0 && row < sudoku.length && col >= 0 && col < sudoku[0].length) {
			sudoku[row][col] = value;
		}
	}

	private void createKeyListener() {
		this.setOnKeyPressed(e -> {

			switch (e.getCode()) {

			case DIGIT0:
				setValueAt(0, selectedRow, selectedCol);
				break;
			case DIGIT1:
				setValueAt(1, selectedRow, selectedCol);
				break;
			case DIGIT2:
				setValueAt(2, selectedRow, selectedCol);
				break;
			case DIGIT3:
				setValueAt(3, selectedRow, selectedCol);
				break;
			case DIGIT4:
				setValueAt(4, selectedRow, selectedCol);
				break;
			case DIGIT5:
				setValueAt(5, selectedRow, selectedCol);
				break;
			case DIGIT6:
				setValueAt(6, selectedRow, selectedCol);
				break;
			case DIGIT7:
				setValueAt(7, selectedRow, selectedCol);
				break;
			case DIGIT8:
				setValueAt(8, selectedRow, selectedCol);
				break;
			case DIGIT9:
				setValueAt(9, selectedRow, selectedCol);
				break;

			case W:
			case UP:
				if (shouldMoveSelected()) {
					if (selectedRow == 0) {
						selectedRow = height - 1;
					} else {
						selectedRow -= 1;
					}
				}
				break;
			case S:
			case DOWN:
				if (shouldMoveSelected()) {
					selectedRow = Math.abs((selectedRow + 1) % height);
				}
				break;
			case A:
			case LEFT:
				if (shouldMoveSelected()) {
					if (selectedCol == 0) {
						selectedCol = width - 1;
					} else {
						selectedCol -= 1;
					}
				}
				break;
			case D:
			case RIGHT:
				if (shouldMoveSelected()) {
					selectedCol = Math.abs((selectedCol + 1) % width);
				}
				break;
			default:
				break;
			}
			draw();
		});
	}

	private boolean shouldMoveSelected() {
		if (selectedRow == -1 || selectedCol == -1) {
			selectedRow = 0;
			selectedCol = 0;
			return false;
		}
		return true;
	}

	public void draw() {
		GraphicsContext g = this.getGraphicsContext2D();
		fillCanvas(g, Color.rgb(52, 73, 94));
		drawSubBoxes(g);
		drawCells(g);
		System.out.println("===========================");
		for (int[] a : sudoku) {
			System.out.println(Arrays.toString(a));
		}
		System.out.println("===========================");
	}

	private void drawCells(GraphicsContext g) {
		double cellWidth = getCellWidth();
		double cellHeight = getCellHeight();
		for (int row = 0; row < width; row++) {
			for (int col = 0; col < height; col++) {
				drawCell(g, col, row, cellWidth, cellHeight, selectedRow == row && selectedCol == col);
			}
		}
	}

	private void fillCanvas(GraphicsContext g, Color color) {
		g.setFill(color);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void drawSubBoxes(GraphicsContext g) {
		double cellWidth = getCellWidth();
		double cellHeight = getCellHeight();
		g.setStroke(Color.BLACK);

		for (int row = 0; row < width; row++) {
			if ((row + 1) % boxHeight == 0 && row != 0 && row != height - 1) {
				g.setLineWidth(0.1 * cellHeight);
				double x1 = insets.getLeft() + 0.05 * cellWidth;
				double y1 = insets.getTop() + cellHeight * (row + 1) - 0.05 * cellHeight;
				double x2 = getWidth() - insets.getRight() - 0.05 * cellWidth;
				double y2 = y1;
				g.strokeLine(x1, y1, x2, y2);
			}
		}

		for (int col = 0; col < height; col++) {
			if ((col + 1) % boxWidth == 0 && col != 0 && col != width - 1) {
				g.setLineWidth(0.1 * cellWidth);
				double x1 = insets.getLeft() + cellWidth * (col + 1) - 0.05 * cellHeight;
				double y1 = insets.getTop() + 0.05 * cellHeight;
				double x2 = x1;
				double y2 = getHeight() - insets.getBottom() - 0.05 * cellHeight;
				g.strokeLine(x1, y1, x2, y2);
			}
		}
	}

	private void drawCell(GraphicsContext g, int x, int y, double cellWidth, double cellHeight, boolean highlight) {
		double xModifier = insets.getLeft();
		double yModifier = insets.getTop();
		double x2 = (x * cellWidth) + xModifier;
		double y2 = (y * cellHeight) + yModifier;
		double w = 0.9 * cellWidth;
		double h = 0.9 * cellHeight;
		double arcWidth = 20;
		double arcHeight = arcWidth;
		g.setFill(Color.ANTIQUEWHITE);
		g.fillRect(x2, y2, w, h);
		if (highlight) {
			g.setLineWidth(0.1 * cellWidth);
			g.setStroke(Color.RED);
			g.strokeRoundRect(x2, y2, w, h, arcWidth, arcHeight);
		}
		int value = sudoku[y][x];
		Font font = Font.font(48);
		if (value != 0) {
			g.setFill(Color.GREEN);
			g.setFont(font);
			g.setTextAlign(TextAlignment.CENTER);
			g.setTextBaseline(VPos.CENTER);
			double textX = x2 + (w / 2);
			double textY = y2 + (h / 2);
			g.fillText(String.valueOf(value), textX, textY, w);
		}
	}

	private double getCellWidth() {
		double modifier = insets.getLeft() + insets.getRight();
		return (getWidth() - modifier) / width;
	}

	private double getCellHeight() {
		double modifier = insets.getBottom() + insets.getTop();
		return (getHeight() - modifier) / height;
	}

	public void setGrid(int[][] sudoku) {
		for (int i = 0; i < sudoku.length; i++) {
			for (int k = 0; k < sudoku[i].length; k++) {
				this.sudoku[i][k] = sudoku[i][k];
			}
		}
		draw();
	}

	public int[][] toGrid() {
		return copy(sudoku);
	}

	private int[][] copy(int[][] array) {
		int height = array[0].length;
		int[][] copy = new int[width][height];
		for (int row = 0; row < width; row++) {
			for (int col = 0; col < height; col++) {
				copy[row][col] = array[row][col];
			}
		}
		return copy;
	}
}
