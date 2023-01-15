import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import java.util.ArrayList;


public class BaseballElimination {
    private ST<String, Integer> st;
    private ST<Integer, String> ts;
    private int[][] g;
    private int[] w;
    private int[] l;
    private int[] r;
    private int flow;
    
    public BaseballElimination(String filename) { // create a baseball division from given filename in format specified below
        if (filename == null) throw new IllegalArgumentException();
        st = new ST<String, Integer>(); // Match the names to number
        ts = new ST<Integer, String>();

        In in = new In(filename);
        int n = in.readInt(); // number of teams
        g = new int[n][n]; // array of teams and matches between them 
        w = new int[n];
        l = new int[n];
        r = new int[n];

        int i = 0;
        while (!in.isEmpty()) {
            String name = in.readString();
            st.put(name, i);
            ts.put(i, name);
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < n; j++)
                g[i][j] = in.readInt();
            i++;
        }  
    }

    // number of teams
    public int numberOfTeams() { return st.size(); } 

    // all teams
    public Iterable<String> teams()  { return st.keys(); }
                                   
    public int wins(String team) { // number of wins for given team
        if (team == null) throw new IllegalArgumentException();
        if (!st.contains(team)) throw new IllegalArgumentException();
        return w[st.get(team)];
    }  

    public int losses(String team) { // number of losses for given team
        if (team == null) throw new IllegalArgumentException();
        if (!st.contains(team)) throw new IllegalArgumentException();
        return l[st.get(team)];
    }    
 
    public int remaining(String team) { // number of remaining games for given team
        if (team == null) throw new IllegalArgumentException();
        if (!st.contains(team)) throw new IllegalArgumentException();
        return r[st.get(team)];
    }      

    public int against(String team1, String team2) { // number of remaining games between team1 and team2
        if (team1 == null || team2 == null) throw new IllegalArgumentException();
        if (!st.contains(team1) || !st.contains(team2)) throw new IllegalArgumentException();
        int i = st.get(team1);
        int j = st.get(team2);
        return g[i][j];
    }  

    private FordFulkerson eliminationNetwork(String team) {
        int x = st.get(team);
        int numberOfGames = numberOfTeams() * (numberOfTeams()-1) / 2;
        int numberOfVertices = numberOfGames + numberOfTeams() + 2;
        FlowNetwork flowNetwork = new FlowNetwork(numberOfVertices); // We need to create the eliminationNetwork 
        int source = 0;
        int target = numberOfVertices - 1;
        flow = 0;
        int index = 1; // source is 0
        for (int i = 0; i < numberOfTeams(); i++) {
            if (i == x) {
                continue;
            }
            for (int j = i + 1; j < numberOfTeams(); j++) {
                if (j == x) {
                    continue;
                }
                flowNetwork.addEdge(new FlowEdge(source, index, g[i][j]));
                flowNetwork.addEdge(new FlowEdge(index, i + numberOfGames + 1, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(index, j + numberOfGames + 1, Double.POSITIVE_INFINITY));
                index++;
                flow += g[i][j];
            }
            int wX = w[st.get(team)];
            int rX = r[st.get(team)];
            int wI = w[st.get(ts.get(i))];
            if (wX + rX - wI < 0) {
                return null;
            } else {
                flowNetwork.addEdge(new FlowEdge(i + numberOfGames + 1, target, wX + rX - wI));
            }
        }
        return new FordFulkerson(flowNetwork, source, target);
    }

    public boolean isEliminated(String team)   { // is given team eliminated?
        if (team == null) throw new IllegalArgumentException();
        if (!st.contains(team)) throw new IllegalArgumentException();
        FordFulkerson maxFlow = eliminationNetwork(team);
        if (maxFlow == null){ return true; }
        else return flow > maxFlow.value();
    }  

    public Iterable<String> certificateOfElimination(String team)   { // subset R of teams that eliminates given team; null if not eliminated
        if (team == null) throw new IllegalArgumentException();
        if (!st.contains(team)) throw new IllegalArgumentException();
        if (!isEliminated(team)) { return null; }
        ArrayList<String> list = new ArrayList<>();
        int x = st.get(team);
        int numberOfGameVertices = numberOfTeams() * (numberOfTeams() - 1) / 2;
        FordFulkerson maxFlow = eliminationNetwork(team);

        for (int index = 0; index < numberOfTeams(); index++) {
            if (index == x) { continue; }
            if (maxFlow == null) {
                int wX = w[st.get(team)];
                int rX = r[st.get(team)];
                int wI = w[st.get(ts.get(index))];
                if (wX + rX - wI < 0) {
                    list.add(ts.get(index));
                }
            }
            else if (maxFlow.inCut(index + numberOfGameVertices + 1)) {
                list.add(ts.get(index));
            }
        }
        return list;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
        
    }

}
