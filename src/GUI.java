import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GUI {

    private JFrame frame;
    private JLabel statusLabel;
    private JPanel boardPanel;
    private JButton loadButton;
    private JButton saveButton;

    private Board currentBoard;
    private int[] currentSolution;

    public GUI() {
        frame = new JFrame("Queens Solver - Brute Force");
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        loadButton = new JButton("Load & Solve");
        saveButton = new JButton("Save Solution");
        saveButton.setEnabled(false);

        JPanel topPanel = new JPanel();
        topPanel.add(loadButton);
        topPanel.add(saveButton);

        statusLabel = new JLabel("Load a board file.", SwingConstants.CENTER);
        boardPanel = new JPanel();

        loadButton.addActionListener(e -> loadAndSolve());
        saveButton.addActionListener(e -> saveSolution());

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void loadAndSolve() {
        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                currentBoard = new Board(chooser.getSelectedFile().getAbsolutePath());

                if (currentBoard.getSize() > 10) {
                    JOptionPane.showMessageDialog(frame,
                            "Warning: N besar dapat menyebabkan waktu komputasi sangat lama.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }

                QueensSolver solver = new QueensSolver(currentBoard);

                loadButton.setEnabled(false);
                saveButton.setEnabled(false);
                statusLabel.setText("Solving...");

                SwingWorker<Boolean, int[]> worker = new SwingWorker<>() {

                    long startTime;

                    @Override
                    protected Boolean doInBackground() {
                        startTime = System.currentTimeMillis();
                        return solver.solve((queensSnapshot, iteration) -> {
                            publish(queensSnapshot);
                        });
                    }

                    @Override
                    protected void process(java.util.List<int[]> chunks) {
                        int[] latest = chunks.get(chunks.size() - 1);
                        displayBoard(currentBoard, latest);
                        statusLabel.setText("Iterations: " + solver.getIterationCount());
                    }

                    @Override
                    protected void done() {
                        long endTime = System.currentTimeMillis();
                        loadButton.setEnabled(true);

                        try {
                            boolean solved = get();
                            if (solved) {
                                currentSolution = solver.getQueens().clone();
                                displayBoard(currentBoard, currentSolution);
                                statusLabel.setText("Solved! Iterations: "
                                        + solver.getIterationCount()
                                        + " | Time: " + (endTime - startTime) + " ms");
                                saveButton.setEnabled(true);
                            } else {
                                saveButton.setEnabled(false);
                                statusLabel.setText("No solution found. Iterations: "
                                        + solver.getIterationCount()
                                        + " | Time: " + (endTime - startTime) + " ms");
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(
                                    frame,
                                    "Error during solving.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                };

                worker.execute();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "❌ Error\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                boardPanel.removeAll();
                boardPanel.revalidate();
                boardPanel.repaint();

                statusLabel.setText("Error reading file.");
            }
        }
    }

    private void displayBoard(Board board, int[] queens) {
        boardPanel.removeAll();

        int size = board.getSize();
        boardPanel.setLayout(new GridLayout(size, size));

        Map<Character, Color> colorMap = generateRegionColors(board);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                JLabel cell = new JLabel("", SwingConstants.CENTER);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cell.setOpaque(true);

                char region = board.getRegion(i, j);
                cell.setBackground(colorMap.get(region));

                if (queens[i] == j) {
                    cell.setText("Q");
                    cell.setFont(new Font("SansSerif", Font.BOLD, size > 8 ? 16 : 24));
                }

                boardPanel.add(cell);
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private Map<Character, Color> generateRegionColors(Board board) {
        Map<Character, Color> map = new HashMap<>();
        char[][] regions = board.getRegions();

        Color[] palette = {
                Color.PINK, Color.CYAN, Color.ORANGE,
                Color.LIGHT_GRAY, Color.YELLOW, Color.GREEN
        };

        int colorIndex = 0;

        for (int i = 0; i < regions.length; i++) {
            for (int j = 0; j < regions.length; j++) {
                char r = regions[i][j];
                if (!map.containsKey(r)) {
                    map.put(r, palette[colorIndex % palette.length]);
                    colorIndex++;
                }
            }
        }

        return map;
    }

    private void saveSolution() {
        if (currentSolution == null || currentBoard == null) return;

        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter pw = new PrintWriter(chooser.getSelectedFile())) {

                int size = currentBoard.getSize();
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (currentSolution[i] == j) {
                            pw.print("Q ");
                        } else {
                            pw.print(". ");
                        }
                    }
                    pw.println();
                }

                JOptionPane.showMessageDialog(frame, "Solution saved successfully!");

            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error saving file.");
            }
        }
    }
}