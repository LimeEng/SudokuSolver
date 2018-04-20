package view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SudokuPlay extends GridPane {

	public SudokuPlay() {
		super();
		setup();
	}

	private void setup() {
		SudokuBoard problem = new SudokuBoard();
		SudokuBoard solution = new SudokuBoard();
		Button solveButton = new Button("Solve");
		this.getChildren()
				.addAll(problem, solution);
	}

	private HBox createHBox(Node... children) {
		HBox box = new HBox();
		box.getChildren()
				.addAll(children);

		box.setFillHeight(true);
		for (Node n : box.getChildren()) {
			HBox.setHgrow(n, Priority.ALWAYS);
		}
		return box;
	}

	private VBox createVBox(Node... children) {
		VBox box = new VBox();
		box.getChildren()
				.addAll(children);

		box.setFillWidth(true);
		for (Node n : box.getChildren()) {
			VBox.setVgrow(n, Priority.ALWAYS);
		}
		return box;
	}

}
