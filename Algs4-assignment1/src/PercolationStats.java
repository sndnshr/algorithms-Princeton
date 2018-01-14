/******************************************************************************
 *  Name:    Sanduni Premaratne
 *  NetID:   sp
 *  Precept: P01
 * 
 *  Description:  Model an n-by-n percolation system using the union-find
 *                data structure.
 ******************************************************************************/

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    
    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double [] thresholds;

    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0)
            throw new IllegalArgumentException("n is out of bounds");
        if (trials <= 0)
            throw new IllegalArgumentException("trials is out of bounds");
                         
        this.trials = trials;
        thresholds = new double[trials];
        
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int row, col;
//            int count = 0;
            while (!percolation.percolates()) {
                do {
                    row = StdRandom.uniform(n)+1;
                    col = StdRandom.uniform(n)+1; 
                } while (percolation.isOpen(row, col));
                
                percolation.open(row, col);
//                count++;
                thresholds[i] = percolation.numberOfOpenSites() /Math.pow(n, 2);
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }   

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev()/ Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }                  

    // test client (described below)
    public static void main(String[] args) {
        
        int n = new Integer(args[0]);
        int trials = new Integer(args[1]);   
            
        PercolationStats percStats = new PercolationStats(n, trials);
        
        StdOut.println("Gird size\t\t\t\t: " + n);
        StdOut.println("Number of trials\t\t: " + trials);
        StdOut.println("Mean\t\t\t\t: " + percStats.mean());
        StdOut.println("Standard deviation\t\t: " + percStats.stddev());
        StdOut.println("95% confidence interval\t: [ " 
        + percStats.confidenceLo() + " , " + percStats.confidenceHi() + " ]");
        
        
    }        
}
