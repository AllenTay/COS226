import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordnet;


    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) { 
        String s = " ";
        int distance = 0;
        for (int i = 0; i < nouns.length; i++) {
            int cycleDistance = 0; // start the inner loop at zero 
            for (int j = 0; j < nouns.length; j++){
                    if (wordnet.distance(nouns[i], nouns[j])!= -1)
                        cycleDistance += wordnet.distance(nouns[i], nouns[j]); // Add the various distances

            }
            if (cycleDistance >= distance)   {
                distance = cycleDistance;
                s = nouns[i];
            }  
        }
        return s;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
