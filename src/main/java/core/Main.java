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

					int[][] problem = SudokuSerializer.deserialize(parts[0]);

					boolean validProblem = SudokuUtils.isValid(problem, 3, 3);
					boolean solvedProblem = SudokuUtils.isSolved(problem, 3, 3);

					System.out.println("The problem is" + (validProblem ? " " : " not ") + "valid and"
							+ (solvedProblem ? " " : " not ") + "solved");

					System.out.println(SudokuPrinter.sudokuToPrettyString(problem));

					long start = System.nanoTime();
					int[][] solution = solver.solve(problem);
					long result = System.nanoTime() - start;

					System.out.println(SudokuPrinter.sudokuToPrettyString(solution));
					System.out.println("Solved #" + (i + 1) + " after " + (result / 1000000) + " ms");
					boolean valid = SudokuUtils.isValid(solution, 3, 3);
					boolean solved = SudokuUtils.isSolved(solution, 3, 3);

					System.out.println("The solution is" + (valid ? " " : " not ") + "valid and"
							+ (solved ? " " : " not ") + "solved");
					System.out.println("=====================================");
				}
				long totalTime = System.nanoTime() - totalStart;
				System.out.println("All puzzles solved in " + (totalTime / 1000000000) + " secs");
			}
		}

	}

	private static List<String> read(Path path) throws IOException {
		return Files.lines(path).filter(e -> !e.isEmpty()).collect(Collectors.toList());
	}

}
