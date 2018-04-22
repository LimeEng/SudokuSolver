package view;

import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class SudokuFX extends Application {

	@Override
	public void start(Stage primaryStage) {

		// SudokuPlay main = new SudokuPlay();
		// SudokuBoard board = new SudokuBoard();
		SudokuCanvas canvas = new SudokuCanvas(new Insets(40), 9, 9, 3, 3);
		HBox box = createHBox(canvas);

		canvas.widthProperty()
				.bind(box.widthProperty());
		canvas.heightProperty()
				.bind(box.heightProperty());

		Scene scene = new Scene(box, 1200, 900);
		scene.getStylesheets()
				.add(toStylesheetString("src/resources/sudoku.css"));
		primaryStage.setScene(scene);
		primaryStage.show();
		canvas.requestFocus();
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

	public String toStylesheetString(String stylesheet) {
		try {
			return new File(stylesheet).toURI()
					.toURL()
					.toString();
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}