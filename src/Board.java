import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Board {

    private int size;
    private char[][] regions;

    public Board(String filePath) throws IOException {
        readFromFile(filePath);
    }

    private void readFromFile(String filePath) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String firstLine = br.readLine();
            if (firstLine == null) {
                throw new IOException("File kosong.");
            }

            // Validasi ukuran board
            try {
                size = Integer.parseInt(firstLine.trim());
            } catch (NumberFormatException e) {
                throw new IOException("Baris pertama harus berupa angka ukuran board.");
            }

            if (size <= 0) {
                throw new IOException("Ukuran board tidak valid.");
            }

            regions = new char[size][size];

            for (int i = 0; i < size; i++) {
                String line = br.readLine();

                if (line == null || line.length() != size) {
                    throw new IOException("Format board tidak sesuai pada baris " + i);
                }

                for (int j = 0; j < size; j++) {
                    regions[i][j] = line.charAt(j);
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public char getRegion(int row, int col) {
        return regions[row][col];
    }

    public char[][] getRegions() {
        return regions;
    }
}