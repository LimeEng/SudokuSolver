package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SudokuFX extends Application {

	@Override
	public void start(Stage stage) {

		SudokuPlay box = new SudokuPlay();

		Scene scene = new Scene(box, 1200, 900);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}