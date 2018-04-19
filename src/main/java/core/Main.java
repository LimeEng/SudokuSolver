package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello World!");

		SudokuSolver solver = new BacktrackingDfsSolver();

		try (Scanner scan = new Scanner(System.in)) {
			while (true) {
				System.out.print("> ");
				String line = scan.nextLine();
				if (line.equalsIgnoreCase("exit")) {
					System.out.println("See you around!");
					break;
				}
				List<String> lines;
				try {
					lines = read(Paths.get(line));
				} catch (Exception e) {
					System.out.println("Something went wrong. Does the file exist and correctly formatted?");
					continue;
				}

				String[] parts = lines.get(0)
						.split(" -> ");

				System.out.println("Input: ");
				System.out.println(SudokuPrinter.sudokuToPrettyString(SudokuSerializer.deserialize(parts[0])));

				System.out.println("Solved: ");
				System.out.println(
						SudokuPrinter.sudokuToPrettyString(solver.solve(SudokuSerializer.deserialize(parts[0]))));
			}
		}

	}

	private static List<String> read(Path path) throws IOException {
		return Files.lines(path)
				.filter(e -> !e.isEmpty())
				.collect(Collectors.toList());
	}

}
