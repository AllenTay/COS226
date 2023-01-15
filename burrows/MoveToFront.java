import edu.princeton.cs.algs4.Alphabet;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;
    private static Alphabet alphabet = Alphabet.EXTENDED_ASCII;
        

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] characters = new char[R]; // Your task is to maintain an ordered sequence of the 256 extended ASCII characters.
        
        for (int i = 0; i < R; i++)
            characters[i] = alphabet.toChar(i);

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int location = 0;
            
            for (int j = 0; j < R; j++) {
                if (characters[j] == c) 
                    location = j;
            }
            BinaryStdOut.write(location, 8);

            for (int k = location; k > 0; k--)
                characters[k] = characters[k-1];
            characters[0] = c;       
        }

        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] characters = new char[R];

        for (int i = 0; i < R; i++)
            characters[i] = alphabet.toChar(i); 
        
        String s = BinaryStdIn.readString();
        char[] letters = s.toCharArray();
    
        for (int i = 0; i < letters.length; i++) {
            int index = letters[i];
            BinaryStdOut.write(characters[index], 8);
    
            char c = characters[index];
            for (int j = index; j > 0; j--)
                characters[j] = characters[j-1];
            characters[0] = c;
        }   
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

    }

