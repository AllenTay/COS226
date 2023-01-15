import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    private Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        this.G = new Digraph(G);
    }

    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);   // BFS rooted    
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);

        int shortest = Integer.MAX_VALUE;
        int ancestor = -1;

        for (int s = 0; s < G.V(); s++){
            if (bfs1.hasPathTo(s) && bfs2.hasPathTo(s)) {
                int dist = bfs1.distTo(s) + bfs2.distTo(s);
                if (dist < shortest) {
                    shortest = dist;
                    ancestor = s;
                }
            }
        }
        return ancestor; //If there is none, the ancestor is zero 
    }

    // length of shortest ancestral path between v and w; -1 if no such path;
    public int length(int v, int w) { 
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);

        int shortest = -1;
        for (int s = 0; s < G.V(); s++){
            if ((bfs1.hasPathTo(s)) && (bfs2.hasPathTo(s))) {
                int dist = bfs1.distTo(s) + bfs2.distTo(s);
                if ((dist < shortest) || (shortest == -1)){
                    shortest = dist;
                }
            }
        }
        return shortest;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if ((v == null) || (w == null)) throw new  IllegalArgumentException();
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);   // BFS rooted    
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);

        int shortest = Integer.MAX_VALUE;
        int ancestor = -1;

        for (int s = 0; s < G.V(); s++){
            if ((bfs1.hasPathTo(s)) && (bfs2.hasPathTo(s))) {
                int dist = bfs1.distTo(s) + bfs2.distTo(s);
                if (dist < shortest) {
                    shortest = dist;
                    ancestor = s;
                }
            }
        }
        return ancestor; //If there is none, the ancestor is zero 
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int length(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if ((subsetA == null) || (subsetB == null)) throw new  IllegalArgumentException();
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, subsetA);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, subsetB);

        int shortest = -1;
        for (int s = 0; s < G.V(); s++){
            if ((bfs1.hasPathTo(s)) && (bfs2.hasPathTo(s))) {
                int dist = bfs1.distTo(s) + bfs2.distTo(s);
                if ((dist < shortest) || (shortest == -1)){
                    shortest = dist;
                }
            }
        }
        return shortest;
    }

   // unit testing (required)
   public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

   }

}
    