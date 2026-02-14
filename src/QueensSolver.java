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

    private long lastUpdateTime = 0;
    private static final long UPDATE_INTERVAL = 250; 

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

            // Live update berdasarkan waktu
            long now = System.currentTimeMillis();
            if (listener != null && now - lastUpdateTime >= UPDATE_INTERVAL) {
                listener.onUpdate(queens.clone(), iterationCount);
                lastUpdateTime = now;
            }

            if (isValid()) {
                foundSolution = true;

                if (listener != null) {
                    listener.onUpdate(queens.clone(), iterationCount);
                }
                
                return true;
            }

        } while (PermutationGenerator.nextPermutation(queens));

        return false;
    }

    private boolean isValid() {
        return checkAdjacent() && checkRegion();
    }

    private boolean checkAdjacent() {
    for (int i = 0; i < size - 1; i++) {
        int colDiff = Math.abs(queens[i] - queens[i + 1]);
        if (colDiff <= 1) {
            return false;
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