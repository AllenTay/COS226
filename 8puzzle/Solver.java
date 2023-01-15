import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private MinPQ<SNode> pq;
    Stack<Board> s = new Stack<>();
    private boolean solved;
    private SNode solutionNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // Insert initial search node
        if (initial == null) throw new IllegalArgumentException();
        solutionNode = null;

        pq = new MinPQ<>();
        pq.insert(new SNode(initial, 0, null)); // Insert the first sNode
        
        while ((!solved)) {
            SNode s = pq.delMin(); // Delete from the priority queue the search node with the minimum priority
            
            if (s.board.isGoal()) {
                solved = true;
                solutionNode = s;
            }

            int moves = s.moves();

            for (Board n : s.board().neighbors()) {
                if ((s.previous != null) && (s.previous.board() == n)) continue;
                pq.insert(new SNode(n, moves + 1, s)); // Insert onto the priority queue all neighboring search nodes (those that can be reached in one move from the dequeued search node)
            }
            
            Board working = s.board;
            solved = working.isGoal();
            }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {return solved; }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) return solutionNode.moves();
        else return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> s = new Stack<>();
        SNode node = solutionNode; // keep a solution node and the answers will link to previous nodes 
        while (node != null) {
            s.push(node.board()); 
            node = node.previous();
        }
        return s;
    }

    private static class SNode implements Comparable<SNode> {
        private final Board board;
        private final int moves;
        private final SNode previous;

        public SNode(Board board, int moves, SNode previous) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
        }

        public Board board(){ return board; }

        public SNode previous() {return previous; }

        public int moves() { return moves; }

        public int priority() { return board.manhattan() + moves; }

        public int compareTo(SNode that) { return Integer.compare(this.priority(), that.priority()); }
    }

    // test client (see below) 
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

}