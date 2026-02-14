import java.util.*;

public class QueensSolver {

    private Board board;
    private int size;
    private int[] queens; 
    private long iterationCount = 0;
    private boolean foundSolution = false;

    public QueensSolver(Board board) {
        this.board = board;
        this.size = board.getSize();
        this.queens = new int[size];

        for (int i = 0; i < size; i++) {
            queens[i] = i;
        }
    }

    public boolean solve() {

        do {
            iterationCount++;

            if (isValid()) {
                foundSolution = true;
                return true;
            }

        } while (PermutationGenerator.nextPermutation(queens));

        return false;
    }

    private boolean isValid() {
        return checkDiagonal() && checkRegion() && checkAdjacency();
    }

    // Cek konflik diagonal
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

    // Cek 1 queen per region
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

    // Cek adjacency (tidak boleh berdampingan)
    private boolean checkAdjacency() {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (Math.abs(i - j) <= 1 && Math.abs(queens[i] - queens[j]) <= 1) {
                    return false;
                }
            }
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
