import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.SudokuSerializer;
import core.SudokuSolver;

public abstract class SudokuSolverTest {

	private SudokuSolver solver;

	public abstract SudokuSolver getSolver();

	@Before
	public void setup() {
		solver = getSolver();
	}

	@After
	public void cleanup() {
		solver = null;
	}

	@Test
	public void testSudokusFromFile() throws IOException {
		List<String> lines = read("src/test/resources/sudokus_and_solutions.txt").filter(e -> !e.isEmpty())
				.collect(Collectors.toList());

		while (lines.size() > 2) {
			lines.remove(lines.size() - 1);
		}

		Map<String, String> pairs = new HashMap<>();
		for (String line : lines) {
			String[] parts = line.split(" -> ");
			String problem = parts[0].trim();
			String solution = parts[1].trim();
			pairs.put(problem, solution);
		}
		for (Entry<String, String> entry : pairs.entrySet()) {
			int[][] problem = SudokuSerializer.deserialize(entry.getKey(), 9, 9);
			int[][] solution = SudokuSerializer.deserialize(entry.getValue(), 9, 9);
			int[][] actual = solver.solve(problem);
			assertEquals2dArray(solution, actual);
		}
	}

	private static void print(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.println(Arrays.toString(array[i]));
		}
	}

	// We assume that expected never is null
	private void assertEquals2dArray(int[][] expected, int[][] actual) {
		if (actual == null) {
			fail("The returned solution is null");
		}

		assertEquals("The 2D arrays does not have the same length", expected.length, actual.length);

		for (int row = 0; row < expected.length; row++) {
			for (int col = 0; col < expected[row].length; col++) {
				assertEquals(expected[row].length, actual[row].length);
				assertEquals(expected[row][col], actual[row][col]);
			}
		}
	}

	private Stream<String> read(Path path) throws IOException {
		return Files.lines(path);
	}

	private Stream<String> read(String path) throws IOException {
		return read(Paths.get(path));
	}
}
