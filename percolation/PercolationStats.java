import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] storage;
    // private Percolation perc;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if ((n <= 0) || (trials <= 0))
        {
            throw new IllegalArgumentException();
        }
        else
        {
            storage = new double[trials];
            for (int i = 0; i < trials; i++)
            {
                Percolation perc = new Percolation(n);
                boolean finish = perc.percolates();
                while (!finish)
                {
                    int row = StdRandom.uniform(1, n+1);
                    int col = StdRandom.uniform(1, n+1);
                    if (!perc.isOpen(row, col))
                    {
                        perc.open(row, col);
                    }
                    finish = perc.percolates();
                }

                storage[i] = (double) perc.numberOfOpenSites() / (n*n);
            }
        }
        
    }

    // sample mean of percolation threshold
    public double mean()
    {
      return StdStats.mean(storage);  
        
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(storage);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        double xBar = mean();
        double s = stddev();
        double low = xBar - ((1.96*s)/Math.sqrt(storage.length));
        return low;
        
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {   
        double xBar = mean();
        double s = stddev();
        double high = xBar + ((1.96*s)/Math.sqrt(storage.length));
        return high;
    }

   // test client (see below)
   public static void main(String[] args) 
   {
    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);
    PercolationStats perf = new PercolationStats(n, t);
    double m = perf.mean();
    double s = perf.stddev();
    double low = perf.confidenceLo();
    double high = perf.confidenceHi();
    System.out.println("mean                    = "+ m);
    System.out.println("stddev                  = " + s);
    System.out.println("95% confidence interval = ["+ low+ ", "+high+"]");

   }

}