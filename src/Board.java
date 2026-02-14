import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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

                if (line == null) {
                    throw new IOException("Jumlah baris kurang dari ukuran board.");
                }

                line = line.trim(); 

                if (line.length() != size) {
                    throw new IOException("Format board tidak sesuai pada baris " + (i + 2));
                }

                for (int j = 0; j < size; j++) {
                    char c = line.charAt(j);

                    if (c < 'A' || c > 'Z') {
                        throw new IOException(
                            "Region harus berupa huruf A-Z. Ditemukan '" + c +
                            "' pada baris " + (i + 2) + ", kolom " + (j + 1)
                        );
                    }

                    regions[i][j] = c;
                }
            }

            // Validasi jumlah region maksimal 26
            Set<Character> uniqueRegions = new HashSet<>();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    uniqueRegions.add(regions[i][j]);
                }
            }

            if (uniqueRegions.size() > 26) {
                throw new IOException(
                    "Jumlah region melebihi 26 (" + uniqueRegions.size() +
                    "). Maksimal 26 region yang diperbolehkan."
                );
            }

            // Validasi baris berlebih
            if (br.readLine() != null) {
                throw new IOException("Jumlah baris lebih dari ukuran board.");
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