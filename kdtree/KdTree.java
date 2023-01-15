import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        private Point2D p;                   // the point
        private RectHV rect;                 // the axis-aligned rectangle corresponding to this node
        private Node lb;                     // the left/bottom subtree
        private Node rt;                     // the right/top subtree
        
        public Node(Point2D p, RectHV rect)
        { this.p = p; this.rect = rect;}
    }
     

    public KdTree() { root = null; }

    public boolean isEmpty() { return size() == 0; }

    public int size() { return size; }

    public boolean contains(Point2D p) { 
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return false;
        return contains(root, p, true);
    }

    private boolean contains(Node x, Point2D p, boolean orientation) {
        if (x == null) return false;
        if (x.p.equals(p)) return true;

        if (orientation) { // Orientation means compare x values, split left - right
            if (p.x() < x.p.x()) return contains(x.lb, p, !orientation);
            else return contains(x.rt, p, !orientation);
        }

        if (!orientation) { // !Orientation means compare y values, split up down 
            if (p.y() < x.p.y()) return contains(x.lb, p, orientation);
            else return contains(x.rt, p, orientation);
        }

        return false; // Hopefully this works!! 
    }

    public void insert(Point2D p) {  
        if (p == null) throw new IllegalArgumentException();
        root = insert(root, p, new RectHV(0, 0, 1, 1), true);
    }

    private Node insert(Node x, Point2D p, RectHV rect, boolean orientation)  {
        if (x == null) {
            size++;
            return new Node(p, rect); // orientation here means the one you were given in the header 
        }
        
        if (orientation) { // Orientation means you have a point like the root and want to attach to its left or right. Compare x values 
            double cmp = p.x() - x.p.x(); //
            if (cmp < 0) x.lb = insert(x.lb, p, new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax()), !orientation); // Go left 
            if (cmp > 0) x.rt = insert(x.rt, p, new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax()), !orientation); // Go right 
            else if (!contains(p)) // Basically (x1 = x2) && (x1,y2) != (x2,y2)
                x.rt = insert(x.rt, p, new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax()), !orientation);
        }
        if (!orientation) {
            double cmp = p.y() - x.p.y();
            if (cmp < 0) x.lb = insert(x.lb, p, new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y()), orientation); // Go down 
            if (cmp > 0) x.rt = insert(x.rt, p, new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax()), orientation); // Go up 
            else if (!contains(p))
                x.rt = insert(x.rt, p, new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax()), orientation);
        }
        return x;
    }


    private void draw(Node root, boolean isX) {
        if(root == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        root.p.draw();

        StdDraw.setPenRadius();
        if(isX) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(root.p.x(), root.rect.ymin(), root.p.x(), root.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(root.rect.xmin(), root.p.y(), root.rect.xmax(), root.p.y());
        }

        draw(root.lb, !isX);
        draw(root.rt, !isX);
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, true);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> q = new Queue<>();
        range(root, q, rect);
        return q;   
    }

    private void range(Node x, Queue<Point2D> queue, RectHV rect) {
        if (x == null) return; // Return if the current node is null

        if (!x.rect.intersects(rect)) return; // Return if bounding box does not intersect query rectangle 

        boolean checker = rect.contains(x.p); // Check if the point belongs to the query rectangle 
        if (checker) queue.enqueue(x.p);
        
        range(x.lb, queue, rect); // Go left
        range(x.rt, queue, rect); // Go right
        
            
    }
            

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        else return nearest(root, p, root.p); // If root is null, it will return root.p which is null hopefully!
    }

    private Point2D nearest(Node x, Point2D p, Point2D closest) {
        if (x == null) return closest; // Return if the current node is null

        double bestDist = closest.distanceSquaredTo(p); // best distance so far 
        double checker = x.rect.distanceSquaredTo(p);  // Is the box closer than the best?

        if (x.p.distanceSquaredTo(p) < closest.distanceSquaredTo(p))
            closest = x.p;
        
        if (checker > bestDist) return closest; // Return if bounding box is not closer to the best so far
        else {
            double p1 = x.p.distanceSquaredTo(p);

            if (p1 < bestDist) {
                bestDist = p1;
                closest = x.p;
                closest =nearest(x.lb, p, closest);
                closest =nearest(x.rt, p, closest);
            }

            else {
                closest = nearest(x.rt, p, closest);
                closest = nearest(x.lb, p, closest);
            }

        
        }
        return closest;
    }

    public static void main(String[] args) {
        
        // StdDraw.setPenRadius(0.05);
        // KdTree set = new KdTree();

        // In in = new In(args[0]);
        // double doubles[] = in.readAllDoubles();
        // for(int i = 0; i < doubles.length; i += 2) {
        //     set.insert(new Point2D(doubles[i], doubles[i + 1]));
        // }

        // for(int i = 0; i < doubles.length; i += 2) {
        //     System.out.println(set.contains(new Point2D(doubles[i], doubles[i + 1])));
        // }

        // for(Point2D p : set.range(new RectHV(0, 0, 0.6, 0.6))) {
        //     System.out.println(p);
        // }


        // set.draw();
    }


}