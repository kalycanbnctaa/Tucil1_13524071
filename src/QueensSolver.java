import java.util.Set;
import java.util.HashSet;

public class QueensSolver {

    public interface UpdateListener {
        void onUpdate(int[] queensSnapshot, long iteration);
    }

    private Board board;
    private int size;
    private int[] queens;
    private long iterationCount;
    private boolean foundSolution;

    public QueensSolver(Board board) {
        this.board = board;
        this.size = board.getSize();
        this.queens = new int[size];

        for (int i = 0; i < size; i++) {
            queens[i] = i;
        }

        this.iterationCount = 0;
        this.foundSolution = false;
    }

    public boolean solve(UpdateListener listener) {

        do {
            iterationCount++;

            if (listener != null && iterationCount % 100 == 0) {
                listener.onUpdate(queens.clone(), iterationCount);
            }

            if (isValid()) {
                foundSolution = true;
                return true;
            }

        } while (PermutationGenerator.nextPermutation(queens));

        return false;
    }

    private boolean isValid() {
        return checkDiagonal() && checkRegion();
    }

    private boolean checkDiagonal() {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (Math.abs(i - j) == Math.abs(queens[i] - queens[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkRegion() {
        Set<Character> usedRegions = new HashSet<>();

        for (int i = 0; i < size; i++) {
            char region = board.getRegion(i, queens[i]);
            if (usedRegions.contains(region)) {
                return false;
            }
            usedRegions.add(region);
        }

        return true;
    }

    public long getIterationCount() {
        return iterationCount;
    }

    public int[] getQueens() {
        return queens;
    }

    public boolean isFoundSolution() {
        return foundSolution;
    }
}
