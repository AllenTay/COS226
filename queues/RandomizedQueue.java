import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] a;
    private int n = 0;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() { return n == 0; }

    // return the number of items on the randomized queue
    public int size() { return n; }

    // resize the underlying array holding the elements
    private void resize(int capacity) { 
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = a[i];
        a = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == a.length) resize(2*a.length);    // double size of array if necessary
        a[n++] = item;                            // add item 
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int samp = StdRandom.uniform(n);
        Item item = a[samp];
        a[samp] = a[--n];
        a[n] = null;
        if (n > 0 && n == a.length/4) resize(a.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        // Choose a random number between 0 and n and return it
        if (isEmpty()) throw new NoSuchElementException();
        int winner = StdRandom.uniform(n);
        return a[winner];  
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
        private int i = n;
        private final Item[] copy;

        public ListIterator() {
            copy = (Item[]) new Object[n];
            for (int j = 0; j < n; j++)
                copy[j] = a[j];
            StdRandom.shuffle(copy);
        }

        public boolean hasNext() { return i > 0; }

        public void remove() { throw new UnsupportedOperationException(); }
        
        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            return copy[--i];
        }            
    }
    

    // unit testing (required)
    public static void main(String[] args)
    {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);

        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }
}