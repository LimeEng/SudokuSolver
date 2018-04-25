package view;

import core.BacktrackingDfsSolver;
import core.SudokuSolver;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SudokuPlay extends HBox {

	private SudokuSolver solver = new BacktrackingDfsSolver();

	public SudokuPlay() {
		super();
		setup();
	}

	private void setup() {
		SudokuCanvas problem = new SudokuCanvas(new Insets(30), 9, 9, 3, 3);
		SudokuCanvas solution = new SudokuCanvas(new Insets(30), 9, 9, 3, 3);
		Button solveButton = new Button("Solve");

		solveButton.setOnAction(e -> {
			solution.setGrid(solver.solve(problem.toGrid()));
		});

		problem.widthProperty()
				.bind(this.widthProperty()
						.multiply(0.5));
		problem.heightProperty()
				.bind(this.heightProperty()
						.multiply(1));

		solution.widthProperty()
				.bind(this.widthProperty()
						.multiply(0.5));
		solution.heightProperty()
				.bind(this.heightProperty()
						.multiply(1));

		this.getChildren()
				.addAll(solveButton, problem, solution);

		// this.setFillWidth(true);
		// for (Node n : this.getChildren()) {
		// VBox.setVgrow(n, Priority.ALWAYS);
		// }
	}

	private HBox createHBox(Canvas node) {
		HBox box = new HBox();
		box.getChildren()
				.add(node);

		node.widthProperty()
				.bind(box.widthProperty());
		node.heightProperty()
				.bind(box.heightProperty());

		box.setFillHeight(true);
		for (Node n : box.getChildren()) {
			HBox.setHgrow(n, Priority.ALWAYS);
		}
		return box;
	}
}
