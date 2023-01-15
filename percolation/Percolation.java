import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int[] id;
    private int dim;
    private int counter;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();
        uf = new WeightedQuickUnionUF((n * n) + 2); 
        dim = n;
        counter = 0;
        id = new int[n*n]; // Create a grid of (n*n) + 2 tiles
        for (int i = 0; i < n*n; i++) { id[i] = 0; }
        
        for (int j = 1; j <= n; j++) { uf.union(0, j); } // Join top row to the top site 
        
        for (int k = n*n; k > ((n*n) - n); k--) { uf.union(k, (n*n)+1);  } // Join bottom row to the bottom site. Will see implications for percolation  
    }

    private boolean isValid(int i) { return (i > 0) && (i <= dim); } // For grid of 3*3, values are 1 to 9

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isInvalid(row) || isInvalid(col)) throw new IllegalArgumentException();
        int site = conversion(row, col);
        if (id[site] != 1) {
            id[site] = 1; // You need to add joining to the top, left, right and bottom 
            counter++;
        }

        int[] rows = {row - 1, row + 1, row, row};
        int[] cols = {col, col, col - 1, col + 1};

        for (int i = 0; i < 4; i++) {
            if (isValid(rows[i])  && isValid(cols[i])) {
                if (isOpen(rows[i], cols[i])) uf.union(conversion(rows[i], cols[i]) + 1, site + 1);
            } 
        }       
    }

    private int conversion(int row, int col) { return ((row - 1) * dim) + col - 1;    } // Used for array access 

    private boolean isInvalid(int i) { return (i <= 0) || (i > dim); } // For grid of 3*3, values are 1 to 9

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) { 
        if (isInvalid(row) || isInvalid(col)) throw new IllegalArgumentException();
        int site = conversion(row, col);
        return id[site] == 1; // Array access here so we use (1, 1) to be 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isInvalid(row) || isInvalid(col)) throw new IllegalArgumentException();
       int site = conversion(row, col);
       return isOpen(row, col) && (uf.find(site+1) == uf.find(0)); // Using site to be 1 for (1,1)
    }

    // returns the number of open sites
    public int numberOfOpenSites() { return counter; }

    // does the system percolate?
    public boolean percolates() {
        if (numberOfOpenSites() > 0) return uf.find(0) == uf.find((dim*dim) + 1);
        else return false;
     }

    // test client (optional)
    public static void main(String[] args) {
        // Test site 
    }
}
