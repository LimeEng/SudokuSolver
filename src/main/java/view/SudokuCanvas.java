package view;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

	private void createKeyListener() {
		this.setOnKeyPressed(e -> {
			switch (e.getCode()) {
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
		double xModifier = insets.getLeft();
		double yModifier = insets.getTop();
		g.setStroke(Color.BLACK);
		for (int row = 0; row < width; row++) {
			for (int col = 0; col < height; col++) {
				if ((col + 1) % boxWidth == 0 && col != 0 && col != width - 1) {
					g.setLineWidth(0.1 * cellWidth);
					double x1 = ((col * cellWidth) + xModifier) + 0.9 * cellWidth + (0.1 * cellWidth);
					double y1 = ((row * cellHeight) + yModifier) + 0.9 * cellHeight;
					double x2 = x1;
					double y2 = y1 - 0.9 * cellHeight;
					g.strokeLine(x1, y1, x2, y2);
				}
				if ((row + 1) % boxHeight == 0 && row != 0 && row != height - 1) {
					g.setLineWidth(0.1 * cellHeight);
					double x1 = ((col * cellWidth) + xModifier) + 0.9 * cellWidth + (0.1 * cellWidth);
					double y1 = ((row * cellHeight) + yModifier) + 0.9 * cellHeight;
					double x2 = x1 - 0.9 * cellWidth;
					double y2 = y1;
					g.strokeLine(x1, y1, x2, y2);
				}
			}
		}
	}

	private void drawCell(GraphicsContext g, double x, double y, double cellWidth, double cellHeight,
			boolean highlight) {
		g.setFill(Color.ANTIQUEWHITE);
		double xModifier = insets.getLeft();
		double yModifier = insets.getTop();
		double x2 = (x * cellWidth) + xModifier;
		double y2 = (y * cellHeight) + yModifier;
		double w = 0.9 * cellWidth;
		double h = 0.9 * cellHeight;
		double arcWidth = 20;
		double arcHeight = arcWidth;
		g.fillRect(x2, y2, w, h);
		if (highlight) {
			g.setLineWidth(0.1 * cellWidth);
			g.setStroke(Color.RED);
			g.strokeRoundRect(x2, y2, w, h, arcWidth, arcHeight);
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
