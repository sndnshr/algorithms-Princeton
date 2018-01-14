/******************************************************************************
 *  Name:    Sanduni Premaratne
 *  NetID:   sp
 *  Precept: P01
 *
 *  Description:  Model a randomized queue - item removed it chosen uniformly
 *                at random.
 ******************************************************************************/

import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] queue;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[4];
        size = 0;
    }
    
    // Resize array
    private void resize(int len) {
        Item[] temp = (Item[]) new Object[len];
        for (int i = 0; i < size; i++)
            temp[i] = queue[i];
        queue = temp;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("Null item!");
        
        if (size+1 == queue.length)
            resize(queue.length*2);
        
        queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Queue is empty!");
        
        int randomIndex = StdRandom.uniform(size);
        Item item = queue[randomIndex];
        
        // Move last item to fill emptied space
        if (randomIndex != size-1) {
            queue[randomIndex] = queue[size-1];
        }
        
        // set the last item to null
        queue[size-1] = null;
        size--;
        
        // reduce array size when queue is too short
        if (size < queue.length/4) {
            resize(queue.length/2);
        }
        
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Queue is empty!");
        
        int randomIndex = StdRandom.uniform(size);
        Item item = queue[randomIndex];
        
        return item;
    }
    
    
    // Array Iterator for RandomizedQueue
    private class ArrayIterator implements Iterator<Item> {

        private int index = 0;      // iterator through indexArray
        private final int[] indexArray;   // random order of indexes
        
        public ArrayIterator() {
            // TODO Auto-generated constructor stub
            indexArray = new int[size];
            
            for (int i = 0; i < size; i++) {
                indexArray[i] = i;
            }
            
            StdRandom.shuffle(indexArray);
        }
        
        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return index < size;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            if (!hasNext())
                throw new java.util.NoSuchElementException("End of queue!");
            
            Item item = queue[indexArray[index]];
            index++;
            return item;
        }
        
        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        
    }
    
    @Override
    public Iterator<Item> iterator() {
        // TODO Auto-generated method stub
        return new ArrayIterator();
    }
    
    
    // Test client
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("-")) {
                StdOut.println(randomizedQueue.dequeue());
                StdOut.println("Queue size: " + randomizedQueue.size);
            }
            else if (item.equals("+")) {
                StdOut.println(randomizedQueue.sample());
            }
            else {
                randomizedQueue.enqueue(item);
            }
        }
        
        StdOut.println("\nQueue elements in random order:\n");
        Iterator<String> iter = randomizedQueue.iterator();
        
        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }
        StdOut.println("Queue size: " + randomizedQueue.size);
    }

}
