import java.util.Arrays;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private Stack<LineSegment> lines = new Stack<LineSegment>();

    public FastCollinearPoints(Point[] points) {
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

        // For each of the n points, sort according to the slope the make with p and check the 3 adjacent
        for (int i = 0; i < n; i++) {
            Point[] aCopy = a.clone();
            Arrays.sort(aCopy, a[i].slopeOrder());
            double slopePQ = aCopy[0].slopeTo(aCopy[1]);
            double slopePR = aCopy[0].slopeTo(aCopy[2]);
            double slopePS = aCopy[0].slopeTo(aCopy[3]);
            if ((slopePQ == slopePR) && (slopePQ == slopePS)) {
                LineSegment line = new LineSegment(aCopy[0], aCopy[3]);
                lines.push(line);
            }
        }
    }
    
    public int numberOfSegments () { return lines.size(); }

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }  
}
