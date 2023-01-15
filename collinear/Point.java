/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
   
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException();
        if (that.compareTo(this) == 0) return Double.NEGATIVE_INFINITY;
        if (that.y == this.y) return 0.0;
        else if (that.x == this.x) return Double.POSITIVE_INFINITY; // Line segment is vertical 
        else {   
            double num = that.y - this.y;
            double denom = that.x - this.x;
            return (1.0 * num/denom);
        }
    }

    public int compareTo(Point that) {
        if (that == null) throw new NullPointerException();
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    public Comparator<Point> slopeOrder() { return new SlopeOrder(); }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p, Point q) { return Double.compare(slopeTo(p), slopeTo(q)); }
    }

    public String toString() { return "(" + x + ", " + y + ")"; }

    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
