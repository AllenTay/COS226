import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.Queue;

public class WordNet {
    private SeparateChainingHashST<String, Queue<Integer>> stwords; 
    private SeparateChainingHashST<Integer, String> stnums;
    private Digraph G;
    private SAP sap;

    // Constructor takes the name of two input files
    public WordNet(String synsets, String hypernyms) {
        // Map all the synsets to numbers 
        // Then use a digraph to map all the vertices
        // Keys will return the word stored in a particular vertex
        if ((synsets == null) || (hypernyms == null)) throw new IllegalArgumentException();
        stwords = new SeparateChainingHashST<>();
        stnums = new SeparateChainingHashST<>();
        
        int vertices = 0;
        In in1 = new In(synsets);
        // Build the symbol table 
        while (in1.hasNextLine()) {
                vertices++;
                String[] a = in1.readLine().split(","); // a[0] is the number 
                String[] words = a[1].split(" ");
                stnums.put(Integer.parseInt(a[0]), a[1]);
                for (int i = 0; i < words.length; i++) {
                    Queue<Integer> snQ = stwords.get(words[i]);
                    if (snQ == null) {
                        snQ  = new Queue<>();
                        snQ.enqueue(Integer.parseInt(a[0]));
                        stwords.put(words[i], snQ);
                    }
                    else {
                        if (!contains(snQ, Integer.parseInt(a[0])))
                            snQ.enqueue(Integer.parseInt(a[0]));
                    }
                            
                }
        }
        
        // Build the graph using the hypernyms 
        G = new Digraph(vertices);
        In in2 = new In(hypernyms);
        while(in2.hasNextLine()) {
            String[] a = in2.readLine().split(",");
            for (int i = 1; i < a.length; i++)
                G.addEdge(Integer.parseInt(a[0]), Integer.parseInt(a[i]));
        }
        DirectedCycle cyclefinder = new DirectedCycle(G);
        if (cyclefinder.hasCycle()) throw new IllegalArgumentException(); // Is the graph a DAG or not?
    }

    private <Item> boolean contains (Iterable<Item> iterable, Item item) {
        for (Item query : iterable)
            if (query == item) return true;
        return false;
    }
    
    // returns all WordNet nouns
    public Iterable<String> nouns() { return stwords.keys(); }

    public boolean isNoun(String word) { 
        if (word == null) throw new IllegalArgumentException();
        return stwords.contains(word);
    }

    public int distance(String nounA, String nounB) {
        if ((nounA == null) || (nounB == null)) throw new IllegalArgumentException(); // Any of the noun argumements is not a Wordnet noun or null 
        if ((stwords.get(nounB) == null) || (stwords.get(nounA)) == null) throw new IllegalArgumentException();

        sap = new SAP(G);
        
        // Return distances between Symbol Table
        return sap.length(stwords.get(nounA), stwords.get(nounB));
    }
    
    public String sap(String nounA, String nounB) {
        if ((nounA == null) || (nounB == null)) throw new IllegalArgumentException(); // Any of the noun argumements is not a Wordnet noun or null 
        if ((stwords.get(nounB) == null) || (stwords.get(nounA)) == null) throw new IllegalArgumentException();

        sap = new SAP(G);

        Iterable<Integer> a = stwords.get(nounA);
        Iterable<Integer> b = stwords.get(nounB);
        return stnums.get(sap.ancestor(a, b)); // return anyone with ancestor number 
    }

    public static void main(String[] args) {
        // pls
    }
}
