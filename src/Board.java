import java.io.*;

public class Board {

    private int size;
    private char[][] regions;

    public Board(String filePath) throws IOException {
        readFromFile(filePath);
    }

    private void readFromFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        size = Integer.parseInt(br.readLine().trim());
        regions = new char[size][size];

        for (int i = 0; i < size; i++) {
            String line = br.readLine();
            for (int j = 0; j < size; j++) {
                regions[i][j] = line.charAt(j);
            }
        }

        br.close();
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
