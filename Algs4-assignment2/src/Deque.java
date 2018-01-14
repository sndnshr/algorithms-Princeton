/******************************************************************************
 *  Name:    Sanduni Premaratne
 *  NetID:   sp
 *  Precept: P02
 *
 *  Description:  Model a double-ended queue or deque 
 *                data structure.
 ******************************************************************************/


import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private int size;      // size of Deque
    private Node first;    // First node of deque
    private Node last;     // Last node of deque

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return current != null;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            if (!hasNext())
                throw new java.util.NoSuchElementException("End of deque!");

            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("Null item!");

        Node node = new Node();
        node.item = item;

        if (size == 0) {
            first = node;
            last = node;
        }
        else {
            first.prev = node;
            node.next = first;
            first = node;
        }

        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("Null Item!");

        Node node = new Node();
        node.item = item;
        
        if (size == 0) {
            first = node;
            last = node;
        }
        else {
            last.next = node;
            node.prev = last;
            last = node;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Dequeue is empty!");

        Item item = first.item;

        if (first == last) {
            first = null;
            last = null;
        }
        else {
            Node second = first.next;
            second.prev = null;
            first.next = null;
            first = second;
        }
        size--;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Dequeue is empty!");

        Item item = last.item;

        if (first == last) {
            first = null;
            last = null;
        }
        else {
            Node secondLast = last.prev;
            secondLast.next = null;
            last.prev = null;
            last = secondLast;
        }
        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        // TODO Auto-generated method stub
        return new DequeIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("-")) StdOut.print(deque.removeFirst());
            else if (item.equals("+")) StdOut.print(deque.removeLast());
            else deque.addLast(item);
        }
        StdOut.println("Size: " + deque.size());
    }

}
