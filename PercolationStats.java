/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private final int trials;
    private final double mean;
    private final double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        int[] results;
        this.trials = trials;
        results = new int[trials];

        // for each trial
        for (int i = 0; i < trials; i++) {
            Percolation test = new Percolation(n);
            // for as long as it doesn't percolate, keep opening tiles
            while (!test.percolates()) {
                int h = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!test.isOpen(h, j)) {
                    test.open(h, j);
                }
            }
            results[i] = test.numberOfOpenSites();
        }
        mean = StdStats.mean(results) / (n * n);
        stddev = StdStats.stddev(results) / (n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - ((CONFIDENCE * stddev) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + ((CONFIDENCE * stddev) / Math.sqrt(trials));
    }

    // test client
    public static void main(String[] args) {
        int n = 10;
        int t = 11;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        }
        PercolationStats stats = new PercolationStats(n, t);
        System.out.println(stats.mean());
        System.out.println(stats.stddev());
        System.out.println(stats.confidenceLo());
        System.out.println(stats.confidenceHi());

    }

}
