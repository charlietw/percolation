/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
    }

    // // sample mean of percolation threshold
    // public double mean()
    //
    // // sample standard deviation of percolation threshold
    // public double stddev()
    //
    // // low endpoint of 95% confidence interval
    // public double confidenceLo()
    //
    // // high endpoint of 95% confidence interval
    // public double confidenceHi()
    //
    // test client
    public static void main(String[] args) {
        int n = 10;
        int t = 11;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        }
        Percolation test = new Percolation(n);
        // for as long as it doesn't percolate, keep opening tiles
        while (!test.percolates()) {
            System.out.println("Starting again...");
            int i = StdRandom.uniform(1, n + 1);
            int j = StdRandom.uniform(1, n + 1);
            if (!test.isOpen(i, j)) {
                test.open(i, j);
            }
            else {
                System.out.println("already open");
            }
        }
        System.out.println(test.numberOfOpenSites());
    }

}
