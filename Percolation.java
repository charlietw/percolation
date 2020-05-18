import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:              Charlie Wren
 *  Last modified:     17/05/2020
 *
 *  Implements WeightUnionQuickFind algorithm using the provided functions.
 *  My work is validating the inputs and ensuring that the correct calls are
 *  made to the provided 'WeightQuickUnionUF' class.
 **************************************************************************** */
public class Percolation {
    private final WeightedQuickUnionUF grid;
    private final int gridLimit;
    private final int gridSize;
    private final int virtualTop;
    private final int virtualBot;
    private int openSites;
    private boolean[] openSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        openSites = 0;
        gridSize = n * n;
        grid = new WeightedQuickUnionUF(gridSize + 2);
        virtualTop = gridSize;
        virtualBot = gridSize + 1;
        openSite = new boolean[gridSize + 2]; // they are all closed to start with
        gridLimit = n;

    }

    // validates input to functions
    private void validateInt(int n) {
        if (n > gridLimit || n < 1) {
            throw new IllegalArgumentException();
        }
    }

    // validates input to functions
    private void validateInput(int row, int col) {
        validateInt(row);
        validateInt(col);
    }

    // takes a set of coordinates and returns the relevant 0-indexed array
    private int coordsToInt(int row, int col) {
        int n; // the index of the array
        n = ((row - 1) * gridLimit); // factoring in row first
        n = n + col; // then col
        return n - 1; // minus 1 to factor in zero-index
    }

    // returns site to the left of a given site, or -1
    private int left(int n) {
        if (n % gridLimit == 0) {
            return -1;
        }
        return n - 1;
    }

    // returns site to the right of a given site, or -1
    private int right(int n) {
        if ((n + 1) % gridLimit == 0) {
            return -1;
        }
        return n + 1;
    }


    // returns site above a given site, or -1
    private int up(int n) {
        if (n < gridLimit) {
            return -1;
        }
        return n - gridLimit;
    }

    // returns site below a given site, or -1
    private int down(int n) {
        if (n >= (gridSize - gridLimit)) {
            return -1;
        }
        return n + gridLimit;
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int[] adjacent = new int[4]; // all possible adjacent locations
        validateInput(row, col);
        int n = coordsToInt(row, col);
        if (!openSite[n]) {
            openSite[n] = true; // opens the location
            openSites += 1;
        }

        adjacent[0] = up(n);
        adjacent[1] = down(n);
        adjacent[2] = left(n);
        adjacent[3] = right(n);

        // if it is first row, connect to virtual top site
        if (adjacent[0] == -1) {
            grid.union(n, virtualTop);
        }

        // if it is last row, connect to virtual top site
        if (adjacent[1] == -1) {
            grid.union(n, virtualBot);
        }

        for (int i = 0; i < adjacent.length; i++) {
            if (adjacent[i] != -1) {
                // System.out
                //         .println(String.format("%s. Trying to connect to ... %s", i, adjacent[i]));
                // if it is open, connect
                if (openSite[adjacent[i]]) {
                    grid.union(n, adjacent[i]);
                }
            }
        }

    }

    //
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateInput(row, col);
        int n = coordsToInt(row, col);
        return openSite[n];
    }

    // // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateInput(row, col);
        int n = coordsToInt(row, col);
        // if it is connected to the virtual top site, then full
        if (grid.find(n) == grid.find(virtualTop)) {
            return true;
        }
        return false;
    }

    // // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // // does the system percolate?
    public boolean percolates() {
        if (grid.find(virtualTop) == grid.find(virtualBot)) {
            return true;
        }
        return false;
    }

    // // test client (optional)
    public static void main(String[] args) {
        Percolation test = new Percolation(10);
        test.open(0, 5);
        test.open(2, 1);
        // System.out.println(test.grid.find(test.coordsToInt(2, 1)));
        test.open(3, 1);
        test.open(4, 1);
        test.open(5, 1);
        System.out.println(test.grid.find(test.virtualTop));
        System.out.println(test.grid.find(test.virtualBot));
        System.out.println(test.percolates());

    }
}

