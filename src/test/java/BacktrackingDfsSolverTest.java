import core.BacktrackingDfsSolver;
import core.SudokuSolver;

public class BacktrackingDfsSolverTest extends SudokuSolverTest {

	@Override
	public SudokuSolver getSolver() {
		return new BacktrackingDfsSolver();
	}
}
