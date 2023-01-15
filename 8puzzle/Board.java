import java.util.Stack;

public class Board {
    private int[][] tiles;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
        }
        s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() { return n; }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if ((tiles[i][j] != 0) && (tiles[i][j] != (n * i + j + 1))) hamming++;
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    int x1 = (tiles[i][j] - 1) / n;
                    int y1 = (tiles[i][j] - 1) % n;
                    manhattan += Math.abs(i - x1);
                    manhattan += Math.abs(j -y1);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() { return hamming() == 0; }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == tiles) return true;
        if (y == null) return false;
        if (y.getClass() != tiles.getClass()) return false;
        Board that = (Board) y;
        return (that.manhattan() == manhattan()) && (that.hamming() == hamming()); // Might change this 
    }

    private boolean isValid(int i, int j)  { return (i >= 0 && i < n) && (j >= 0 && j < n); }

    private void swap(int i, int j, int k, int m) {
        int t = tiles[i][j];
        tiles[i][j] = tiles[k][m];
        tiles[k][m] = t;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> s = new Stack<>();
         // Find the location of the zero 
         int xVal = 0;
         int yVal = 0;
         for (int i = 0; i < n; i++) {
             for (int j = 0; j < n; j++) {
                 if (tiles[i][j] == 0) {
                     xVal = i;
                     yVal = j;
                 }
             }
         }
 
         int[] xVals = {xVal- 1, xVal +1, xVal, xVal};
         int[] yVals = {yVal, yVal, yVal-1, yVal+1};
 
         for (int i = 0; i < 4; i++) {
             if (isValid(xVals[i], yVals[i])) {
                 Board neighbor = new Board(tiles);
                 neighbor.swap(xVal, yVal, xVals[i], yVals[i]);
                 s.push(neighbor);
             }
         }
        
        return s;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board board = new Board(tiles);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (tiles[i][j] != 0 && tiles[i][j + 1] != 0) {
                    board.swap(i, j, i, j + 1);
                    return board;
                }
            }
        }
        return board;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // Nothing here 

    }

}