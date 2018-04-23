package view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SudokuPlay extends VBox {

	public SudokuPlay() {
		super();
		setup();
	}

	private void setup() {
		SudokuCanvas problem = new SudokuCanvas(new Insets(30), 9, 9, 3, 3);
		SudokuCanvas solution = new SudokuCanvas(new Insets(30), 9, 9, 3, 3);
		Button solveButton = new Button("Solve");

		problem.widthProperty()
				.bind(this.widthProperty());
		problem.heightProperty()
				.bind(this.heightProperty());

		solution.widthProperty()
				.bind(this.widthProperty());
		solution.heightProperty()
				.bind(this.heightProperty());

		this.getChildren()
				.addAll(problem, solution);

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
