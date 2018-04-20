package view;

import java.util.Arrays;

import core.SudokuUtils;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectExpression;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class SudokuBoard extends GridPane {

	private final int width;
	private final int height;
	private final int boxWidth;
	private final int boxHeight;

	private final ObjectExpression<Font> fontTracking = Bindings.createObjectBinding(() -> Font.font(getWidth() / 40),
			widthProperty());

	private final int[][] sudoku;

	public SudokuBoard() {
		this(9, 9, 3, 3);
	}

	public SudokuBoard(int width, int height, int boxWidth, int boxHeight) {
		super();
		this.width = width;
		this.height = height;
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		this.sudoku = new int[width][height];
		setup();
	}

	public int[][] toGrid() {
		return copy(sudoku);
	}

	private void setup() {

		PseudoClass right = PseudoClass.getPseudoClass("right");
		PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

		for (int row = 0; row < width; row++) {
			for (int col = 0; col < height; col++) {
				StackPane cell = new StackPane();
				cell.getStyleClass()
						.add("cell");
				cell.pseudoClassStateChanged(bottom, (row + 1) % boxWidth == 0 && (row + 1) != width);
				cell.pseudoClassStateChanged(right, (col + 1) % boxHeight == 0 && (col + 1) != height);

				TextArea area = createTextArea(row, col);
				cell.getChildren()
						.add(area);
				this.add(cell, col, row);
			}
		}
	}

	private TextArea createTextArea(int row, int col) {
		TextArea area = new TextArea();
		// Integers from 0-9
		area.setTextFormatter(new TextFormatter<>(c -> {
			if (c.getControlNewText()
					.matches("\\d?")) {
				return c;
			}
			return null;
		}));

		// area.styleProperty()
		// .bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));

		area.fontProperty()
				.bind(fontTracking);

		PseudoClass centered = PseudoClass.getPseudoClass("centeredText");
		area.pseudoClassStateChanged(centered, true);

		Platform.runLater(() -> {
			ScrollPane pane = (ScrollPane) area.lookup(".scroll-pane");
			pane.setVbarPolicy(ScrollBarPolicy.NEVER);
			pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		});

		area.setMinSize(0, 0);

		area.textProperty()
				.addListener((obs, oldText, newText) -> {
					handleInput(newText, area, row, col);
				});

		return area;
	}

	private void handleInput(String input, TextArea area, int row, int col) {
		if (!input.isEmpty()) {
			int value = Integer.parseInt(area.getText());
			if (!SudokuUtils.validForRow(sudoku, row, value)) {
				colorRow("#ffaaaa", row);
				area.setStyle("text-area-background: #ff4242;");
			}
			if (!SudokuUtils.validForCol(sudoku, col, value)) {
				colorCol("#ffaaaa", col);
				area.setStyle("text-area-background: #ff4242;");
			}
			if (!SudokuUtils.validForSubBox(sudoku, row, col, value, boxWidth, boxHeight)) {

			}
			sudoku[row][col] = value;
		} else {
			area.setStyle("text-area-background: #ffffff;");
		}
	}

	private void colorRow(String color, int row) {
		for (int col = 0; col < sudoku[row].length; col++) {
			StackPane pane = (StackPane) getNode(row, col);
			for (Node node : pane.getChildren()) {
				node.setStyle("text-area-background: " + color + ";");
			}
		}
	}

	private void colorCol(String color, int col) {
		for (int row = 0; row < sudoku.length; row++) {
			StackPane pane = (StackPane) getNode(row, col);
			for (Node node : pane.getChildren()) {
				node.setStyle("text-area-background: " + color + ";");
			}
		}
	}

	private void colorSubBox(String color) {

	}

	// https://stackoverflow.com/questions/20655024/javafx-gridpane-retrive-specific-cell-content
	private Node getNode(int row, int col) {
		for (Node node : this.getChildren()) {
			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
				return node;
			}
		}
		return null;
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

	private void print(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.println(Arrays.toString(array[i]));
		}
	}
}
