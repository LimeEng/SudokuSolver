package view;

import core.BacktrackingDfsSolver;
import core.SudokuSolver;
import core.SudokuUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class SudokuPlay extends HBox {

	private SudokuSolver solver = new BacktrackingDfsSolver();

	private static final int[][] EMPTY_GRID = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	public SudokuPlay() {
		super();
		setup();
	}

	private void setup() {
		SudokuCanvas problem = new SudokuCanvas(new Insets(30), 9, 9, 3, 3);
		SudokuCanvas solution = new SudokuCanvas(new Insets(30), 9, 9, 3, 3);
		Button solveButton = new Button("Solve");
		Button clearButton = new Button("Clear");

		solveButton.setOnAction(e -> {
			int[][] sudoku = problem.toGrid();
			if (SudokuUtils.isValid(sudoku, 9, 9)) {
				solution.setGrid(solver.solve(sudoku));
			}
		});

		clearButton.setOnAction(e -> {
			problem.setGrid(EMPTY_GRID);
		});

		problem.widthProperty()
				.bind(this.widthProperty()
						.multiply(0.45));
		problem.heightProperty()
				.bind(this.heightProperty()
						.multiply(1));

		solution.widthProperty()
				.bind(this.widthProperty()
						.multiply(0.45));
		solution.heightProperty()
				.bind(this.heightProperty()
						.multiply(1));

		this.getChildren()
				.addAll(problem, solveButton, clearButton, solution);
	}
}
