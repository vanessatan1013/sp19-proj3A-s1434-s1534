package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private Percolation p;
    private int dim;
    private int count;
    private int t;
    private double[] results;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Not a valid input.");
        }
        dim = N;
        t = T;
        results = new double[T];
        for (int i = 0; i < T; i++) {
            p = pf.make(N);
            count = 0;
            while (!p.percolates()) {
                int r = StdRandom.uniform(N);
                int c = StdRandom.uniform(N);
                if (!p.isOpen(r, c)) {
                    p.open(r, c);
                    count++;
                }
            }
            results[i] = (double) count / (dim * dim);
        }
    }


    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLow() {
        double mean = mean();
        double std = stddev();
        return mean - ((1.96 * std) / Math.sqrt(t));
    }

    public double confidenceHigh() {
        double mean = mean();
        double std = stddev();
        return mean + ((1.96 * std) / Math.sqrt(t));
    }

}
