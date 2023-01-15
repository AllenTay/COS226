import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;;

public class PointSET {

    private SET<Point2D> set;

    public PointSET() { set = new SET<Point2D>(); }

    public boolean isEmpty() { return set.isEmpty();}

    public int size() { return set.size();}

    public void insert(Point2D p) { 
        if (p == null) throw new IllegalArgumentException();
        if (!set.contains(p))
            set.add(p);  
    }

    public boolean contains(Point2D p) { 
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    public void draw() { 
        for (Point2D p : set) 
            p.draw(); 
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> q = new Queue<>();
        for (Point2D p : set) {
            if (rect.contains(p))
                q.enqueue(p);
        }
        return q;   
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (set.isEmpty()) return null;
        double min = Double.POSITIVE_INFINITY;
        Point2D s = null;
        for (Point2D q : set) {
            if (p.distanceSquaredTo(q) < min) {
                min = p.distanceSquaredTo(q);
                s = q;
            }
        }
        return s;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional
        // unit testing of the methods (optional) 
        StdDraw.setPenRadius(0.01);
        PointSET set1 = new PointSET();

        String filename = args[0];
        In in = new In(filename);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            set1.insert(p);
        }
        set1.draw();

    }                  
}