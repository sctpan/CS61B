package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int T;
    private int N;
    private double[] threshold;
    Percolation system;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.T = T;
        this.N = N;
        threshold = new double[T];
        for (int i = 0; i < T; i++) {
            system = pf.make(N);
            do {
                int site = pickRandomSite();
                system.open(site / 10, site % 10);
            } while (!system.percolates());
            threshold[i] = system.numberOfOpenSites();
        }
    }

    private int pickRandomSite() {
        int site = StdRandom.uniform(0, N * N);
        while (system.isOpen(site / 10, site % 10)) {
            site = StdRandom.uniform(0, N * N);
        }
        return site;
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }

    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(T));
    }

    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }

}
