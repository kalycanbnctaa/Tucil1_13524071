import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

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

        statusLabel = new JLabel("Load a board file (.txt).", SwingConstants.CENTER);
        boardPanel = new JPanel();

        loadButton.addActionListener(e -> loadAndSolve());
        saveButton.addActionListener(e -> saveSolution());

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void loadAndSolve() {

        showInfo("Silakan pilih file input dengan format .txt");

        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Text Files (*.txt)", "txt"
        ));

        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {

            File selectedFile = chooser.getSelectedFile();
            String fileName = selectedFile.getName().toLowerCase();

            if (!fileName.endsWith(".txt")) {
                showError("File input harus berformat .txt");
                return;
            }

            try {
                currentBoard = new Board(selectedFile.getAbsolutePath());

                if (currentBoard.getSize() > 10) {
                    showWarning("N besar dapat menyebabkan waktu komputasi sangat lama.");
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
                        return solver.solve((queensSnapshot, iteration) -> publish(queensSnapshot));
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
                                statusLabel.setText(
                                        "Solved! Iterations: " + solver.getIterationCount()
                                                + " | Time: " + (endTime - startTime) + " ms"
                                );
                                saveButton.setEnabled(true);
                            } else {
                                showWarning("Tidak ditemukan solusi.");
                                statusLabel.setText(
                                        "No solution found. Iterations: " + solver.getIterationCount()
                                                + " | Time: " + (endTime - startTime) + " ms"
                                );
                            }
                        } catch (Exception e) {
                            showError("Terjadi error saat proses solving.");
                        }
                    }
                };

                worker.execute();

            } catch (IOException ex) {
                showError("Error membaca file:\n" + ex.getMessage());
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
                cell.setOpaque(true);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cell.setBackground(colorMap.get(board.getRegion(i, j)));

                if (queens[i] == j) {
                    cell.setText("♛");
                    cell.setFont(new Font("Serif", Font.BOLD, size > 8 ? 24 : 36));
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

        int idx = 0;
        for (char[] row : regions) {
            for (char c : row) {
                if (!map.containsKey(c)) {
                    map.put(c, palette[idx++ % palette.length]);
                }
            }
        }
        return map;
    }

    private void saveSolution() {

        if (currentSolution == null || currentBoard == null) {
            showError("No solution available to save!");
            return;
        }

        String[] options = {"Save as TXT", "Save as PNG"};
        int choice = JOptionPane.showOptionDialog(
                frame,
                "Pilih format penyimpanan:",
                "Save Solution",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == -1) return;

        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                if (choice == 0) {
                    saveAsTXT(new File(file.getAbsolutePath() + ".txt"));
                } else {
                    saveAsPNG(new File(file.getAbsolutePath() + ".png"));
                }
                showInfo("Solution saved successfully!");
            } catch (IOException e) {
                showError("Error saving file.");
            }
        }
    }

    private void saveAsTXT(File file) throws IOException {
        try (PrintWriter pw = new PrintWriter(file)) {
            int size = currentBoard.getSize();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    pw.print(currentSolution[i] == j ? "# " : ". ");
                }
                pw.println();
            }
        }
    }

    private void saveAsPNG(File file) throws IOException {
        int cell = 60;
        int size = currentBoard.getSize();
        BufferedImage img = new BufferedImage(
                size * cell, size * cell, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = img.createGraphics();
        Map<Character, Color> colors = generateRegionColors(currentBoard);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                g.setColor(colors.get(currentBoard.getRegion(i, j)));
                g.fillRect(j * cell, i * cell, cell, cell);
                g.setColor(Color.BLACK);
                g.drawRect(j * cell, i * cell, cell, cell);

                if (currentSolution[i] == j) {
                    g.setFont(new Font("Serif", Font.BOLD, 40));
                    g.drawString("♛", j * cell + 15, i * cell + 45);
                }
            }
        }

        g.dispose();
        ImageIO.write(img, "png", file);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                frame,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
                frame,
                message,
                "Warning",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(
                frame,
                message,
                "Information",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
