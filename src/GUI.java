import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUI {

    private JFrame frame;
    private JLabel statusLabel;
    private JPanel boardPanel;

    public GUI() {
        frame = new JFrame("Queens Solver - Brute Force");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JButton loadButton = new JButton("Load & Solve");

        statusLabel = new JLabel("Load a board file.", SwingConstants.CENTER);

        boardPanel = new JPanel();

        loadButton.addActionListener(e -> loadAndSolve());

        frame.add(loadButton, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void loadAndSolve() {
        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                Board board = new Board(chooser.getSelectedFile().getAbsolutePath());

                QueensSolver solver = new QueensSolver(board);

                long start = System.currentTimeMillis();
                boolean solved = solver.solve();
                long end = System.currentTimeMillis();

                if (solved) {
                    displayBoard(board, solver.getQueens());
                    statusLabel.setText("Solved! Iterations: "
                            + solver.getIterationCount()
                            + " | Time: " + (end - start) + " ms");
                } else {
                    statusLabel.setText("No solution. Iterations: "
                            + solver.getIterationCount()
                            + " | Time: " + (end - start) + " ms");
                }

            } catch (IOException ex) {
                statusLabel.setText("Error reading file.");
            }
        }
    }

    private void displayBoard(Board board, int[] queens) {
        boardPanel.removeAll();

        int size = board.getSize();
        boardPanel.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JLabel cell = new JLabel("", SwingConstants.CENTER);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if (queens[i] == j) {
                    cell.setText("Q");
                }

                boardPanel.add(cell);
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }
}
