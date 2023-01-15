import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first; // top of deque 
    private Node last; // bottom of deque 
    private int n; // number of items 

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() { 
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() { return n == 0; }

    // return the number of items on the deque
    public int size() { return n; }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldfirst = first; // Two cases are adding to an empty list or adding to a list with items in it 
        first = new Node(); // 
        first.prev = null;
        first.item = item;
        first.next = oldfirst;
        if (n != 0) oldfirst.prev = first;
        if (n == 0) last = first; // Adding to front but making it accessible to the back 
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldlast = last; // Check enqueue for previous node correction 
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (first == null) first = last; // This checks if first = null and then makes first = last 
        else oldlast.next = last;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (n == 0) throw new NoSuchElementException(); 
        Item item = first.item;
        first = first.next; // If first.next is null then first will be null 
        if (first == null) last = null;
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast()
    { 
        if (n == 0) throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev; // This does the moving 
        if (last != null) last.next = null; // 
        if (last == null) first = null;   
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException(); 
            Item item = current.item;
            current = current.next;
            return item;
            }
        }

    // unit testing (required)
    public static void main(String[] args) {
        // Send help  
        int n = 5;
        Deque<Integer> deque = new Deque<Integer>();
        for (int i = 0; i < n; i++)
            deque.addFirst(i);

        for (int i = 0; i < n; i++)
            StdOut.println(deque.removeFirst());
    }

}