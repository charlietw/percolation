/* *****************************************************************************
 *  Name:              Charlie Wren
 *  Last modified:     17/05/2020
 *
 *  Implements WeightUnionQuickFind algorithm manually, creating the data
 *  structure required to provide the solution.
 **************************************************************************** */

public class PercolationManual {
    private int[] grid;
    private boolean[] open;
    private int[] size; // size of tree for given element
    private int gridLimit;
    private int gridTotalSize; // initial grid squared
    private int virtualTop;
    private int virtualBot;
    private int numberOpen;

    // creates n-by-n grid, with all sites initially blocked except
    // virtual top and bottom which are open
    public PercolationManual(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        int firstRow;
        int lastRow;
        gridTotalSize = n * n;
        firstRow = n;
        lastRow = gridTotalSize - n;
        grid = new int[gridTotalSize + 2]; // plus two for virtual top/bot
        open = new boolean[gridTotalSize + 2];
        size = new int[gridTotalSize + 2];
        numberOpen = 0;
        virtualTop = gridTotalSize;
        virtualBot = gridTotalSize + 1; // these are the last two elements (as 0 index)
        // connects first row to virtual top site
        for (int i = 0; i < firstRow; i++) {
            grid[i] = virtualTop; // set root to virtual top
            open[i] = false;
        }
        // middle is not connected to anything
        for (int i = n; i < lastRow; i++) {
            grid[i] = i;
            open[i] = false;
            size[i] = 1;
        }
        // last row connects to virtual bottom site
        for (int i = lastRow; i < gridTotalSize; i++) {
            grid[i] = virtualBot;
            open[i] = false;
        }

        // open virtual top and bot
        open[virtualTop] = true;
        open[virtualBot] = true;
        // sets size as n + 1 as it has the first row attached
        size[virtualTop] = n + 1;
        size[virtualBot] = n + 1;
        // sets virtual top and bot as root of themself
        grid[virtualTop] = virtualTop;
        grid[virtualBot] = virtualBot;
        this.gridLimit = n; // stores for easy referral later
    }

    // validates input to functions
    private void validateInt(int n) {
        if (n > gridLimit || n < 0) {
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

    // gets root and flattens tree while it touches the memory
    private int root(int n) {
        while (grid[n] != n) {
            grid[n] = grid[grid[n]]; // path compression
            n = grid[n];
        }
        return n;
    }

    // connects two elements depending on size of tree
    // private void union(int p, int q) {
    //     int i = root(p);
    //     int j = root(q);
    //     if (i == j) {
    //         return;
    //     }
    //     if (size[i] < size[j]) {
    //         grid[i] = j;
    //         size[j] += size[i];
    //     }
    //     else {
    //         grid[j] = i;
    //         size[i] += size[j];
    //     }
    // }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateInput(row, col);
        int n = coordsToInt(row, col);
        return open[n];
    }

    // is the site (int) open?
    public boolean isOpenInt(int n) {
        validateInt(n);
        return open[n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateInput(row, col);
        int n = coordsToInt(row, col);
        // creates array of all possible adjacent elements
        int[] adjacent = new int[4];
        adjacent[0] = coordsToInt(row - 1, col);
        adjacent[1] = coordsToInt(row + 1, col);
        adjacent[2] = coordsToInt(row, col + 1);
        adjacent[3] = coordsToInt(row, col - 1);
        // if not already open...
        if (!open[n]) {
            open[n] = true; // opens site
            numberOpen += 1; // increments the count of number open
        }

        // iterates through adjacent sites and creates union if open
        for (int i = 0; i < adjacent.length; i++) {
            try {
                if (isOpenInt(adjacent[i])) {
                    // WeightedQuickUnionUF.union(n, adjacent[i]);
                }
            }
            catch (IllegalArgumentException e) {
                // System.out.println("Not checking as out of bounds");
            }
        }
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateInput(row, col);
        int n = coordsToInt(row, col);
        if (root(n) == root(virtualTop)) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (root(virtualTop) == root(virtualBot)) {
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        PercolationManual test;
        test = new PercolationManual(5);
        System.out.println(test.numberOfOpenSites());
        System.out.println(test.virtualTop);
        System.out.println(test.root(test.virtualBot));
        System.out.println(test.root(21));
        // System.out.println(test.root(6));
        test.open(2, 2);
        test.open(1, 2);
        test.open(3, 2);
        test.open(4, 2);
        test.open(5, 2);
        System.out.println(test.root(21));
        System.out.println(test.percolates());

        // System.out.println(test.root(3));
    }
}


