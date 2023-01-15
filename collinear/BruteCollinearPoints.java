import java.util.Arrays;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;


public class BruteCollinearPoints {
    private Stack<LineSegment> lines = new Stack<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException("argument is null");
        if (points.length == 0) throw new IllegalArgumentException("array is of length 0");

        int n = points.length;
        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new IllegalArgumentException("points[" + i + "] is null");
        }

        Point[] a = points.clone();

        Arrays.sort(a);

        for (int i = 0; i < n - 1; i++) {
            if (a[i].compareTo(a[i+1]) == 0) throw new IllegalArgumentException();
        }

        for (int i = 0; i < n - 3; i++) {
            for (int j = i+1; j < n - 2; j++) {
                double slopePQ = a[i].slopeTo(a[j]);
                for (int k = j+1; k < points.length - 1; k++) {
                    double slopePR = a[i].slopeTo(a[k]);
                    if (slopePQ == slopePR) {
                        for (int m = k+1; m < n; m++) {
                            double slopePS = a[i].slopeTo(a[m]);
                            if (slopePQ == slopePS) {
                                LineSegment line = new LineSegment(a[i], a[m]);
                                lines.push(line);
                            }
                        }
                    }
                }
            }
        }
    }  

    public int numberOfSegments() { return lines.size(); }     

    public LineSegment[] segments() {
        LineSegment[] segs = new LineSegment[lines.size()];
        int k = 0;
        for (LineSegment p: lines) { segs[k++] = p; }
        return segs;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt(); 
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }  
}
