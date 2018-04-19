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

				long totalStart = System.nanoTime();
				for (int i = 0; i < lines.size(); i++) {
					String[] parts = lines.get(i).split(" -> ");

					System.out.println("Input #" + (i + 1) + ": ");
					System.out.println(SudokuPrinter.sudokuToPrettyString(SudokuSerializer.deserialize(parts[0])));

					int[][] problem = SudokuSerializer.deserialize(parts[0]);
					int[][] problemCopy = copy(problem);
					long start = System.nanoTime();
					int[][] solution = solver.solve(problem);
					long result = System.nanoTime() - start;

					System.out.println(SudokuPrinter.sudokuToPrettyString(solution));
					System.out.println("Solved #" + (i + 1) + " after " + (result / 1000000) + " ms");
					boolean valid = SudokuUtils.isValid(problemCopy, 3, 3);
					boolean solved = SudokuUtils.isSolved(problemCopy, 3, 3);
					System.out.println("Valid? " + valid);
					System.out.println("Solved? " + solved);
				}
				long totalTime = System.nanoTime() - totalStart;
				System.out.println("All puzzles solved in " + (totalTime / 1000000000) + " secs");
			}
		}

	}

	private static int[][] copy(int[][] array) {
		int[][] copy = new int[array.length][array[0].length];
		for (int i = 0; i < array.length; i++) {
			for (int k = 0; k < array[i].length; k++) {
				copy[i][k] = array[i][k];
			}
		}
		return copy;
	}

	private static List<String> read(Path path) throws IOException {
		return Files.lines(path).filter(e -> !e.isEmpty()).collect(Collectors.toList());
	}

}
