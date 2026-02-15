import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private int size;
    private char[][] regions;

    public Board(String filePath) throws IOException {
        readFromFile(filePath);
    }

    private void readFromFile(String filePath) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            List<String> lines = new ArrayList<>();
            String line;
            int lineNumber = 0;

            // Baca semua baris
            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    throw new IOException(
                        "Terdapat baris kosong pada baris " + lineNumber
                    );
                }

                lines.add(line.trim());
            }

            if (lines.isEmpty()) {
                throw new IOException("File kosong.");
            }

            size = lines.size();
            int rowLength = lines.get(0).length();

            if (rowLength == 0) {
                throw new IOException("Baris tidak boleh kosong.");
            }

            if (rowLength != size) {
                throw new IOException(
                    "Board harus berbentuk persegi (NxN). " +
                    "Ditemukan " + size + " baris dan panjang " + rowLength
                );
            }

            regions = new char[size][size];

            // Isi regions dan validasi karakter
            for (int i = 0; i < size; i++) {

                String currentLine = lines.get(i);

                if (currentLine.length() != rowLength) {
                    throw new IOException(
                        "Panjang baris tidak konsisten pada baris " + (i + 1)
                    );
                }

                for (int j = 0; j < size; j++) {

                    char c = currentLine.charAt(j);

                    if (c < 'A' || c > 'Z') {
                        throw new IOException(
                            "Region harus berupa huruf A-Z. Ditemukan '" + c +
                            "' pada baris " + (i + 1) +
                            ", kolom " + (j + 1)
                        );
                    }

                    regions[i][j] = c;
                }
            }

            // Hitung jumlah region unik
            Set<Character> uniqueRegions = new HashSet<>();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    uniqueRegions.add(regions[i][j]);
                }
            }

            if (uniqueRegions.size() > 26) {
                throw new IOException(
                    "Jumlah region melebihi 26 (" +
                    uniqueRegions.size() +
                    "). Maksimal 26 region yang diperbolehkan."
                );
            }

            if (uniqueRegions.size() != size) {
                throw new IOException(
                    "Jumlah region harus sama dengan ukuran board (" +
                    size + "), tetapi ditemukan " +
                    uniqueRegions.size() + " region unik."
                );
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