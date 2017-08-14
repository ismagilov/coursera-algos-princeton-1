import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author Ilya Ismagilov <ilya@singulator.net>
 */
public class PercolationStats {
    private final int n;
    private final int trials;

    private double mean;
    private double stddev;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;

        stats();
    }

    private void stats() {
        double[] data = new double[trials];

        for (int t = 0; t < trials; t++) {

            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                int cell = StdRandom.uniform(n * n);
                p.open(cell / n + 1, cell % n + 1);
            }
            data[t] = p.numberOfOpenSites() / (double) (n * n);
        }

        mean = StdStats.mean(data);
        stddev = StdStats.stddev(data);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - 1.96 * stddev / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + 1.96 * stddev / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        if (n <= 0)
            throw new IllegalArgumentException("Dimension must be positive");

        int trials = Integer.parseInt(args[1]);
        if (trials <= 0)
            throw new IllegalArgumentException("The number of trials must be positive");

        PercolationStats ps = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]", ps.confidenceLo(), ps.confidenceHi());
    }
}
