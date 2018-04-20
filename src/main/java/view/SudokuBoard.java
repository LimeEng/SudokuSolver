package view;

import java.util.Arrays;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectExpression;
import javafx.css.PseudoClass;
import javafx.scene.Node;
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

	// private final DoubleProperty fontSize = new SimpleDoubleProperty(10);

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

		// fontSize.bind(this.widthProperty()
		// .add(this.heightProperty())
		// .divide(50));

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
		
//		area.prefHeightProperty().bind(this.heightProperty());
//		area.prefWidthProperty().bind(this.widthProperty());
		
		area.setWrapText(true);
		
		area.setMinSize(0, 0);

		area.textProperty()
				.addListener((obs, oldText, newText) -> {
					if (!newText.isEmpty()) {
						sudoku[row][col] = Integer.parseInt(area.getText());
					}
					print(sudoku);
					System.out.println();
				});

		return area;
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