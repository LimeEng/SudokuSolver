package view;

import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SudokuFX extends Application {

	@Override
	public void start(Stage stage) {

		SudokuPlay box = new SudokuPlay();

		Scene scene = new Scene(box, 1200, 900);
		scene.getStylesheets()
				.add(toStylesheetString("src/resources/sudoku.css"));
		stage.setScene(scene);
		stage.show();
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