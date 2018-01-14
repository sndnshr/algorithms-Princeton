/******************************************************************************
 *  Name:    Sanduni Premaratne
 *  NetID:   sp
 *  Precept: P01
 *
 *  Description:  Takes an integer k from command line; reads a sequence of 
 *                strings from standard input and print exactly k of them,
 *                uniformly at random.
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        
        RandomizedQueue<String> randQueue = new RandomizedQueue<>();
        
        while (!StdIn.isEmpty())
            randQueue.enqueue(StdIn.readString());
        
        if (k < 0 || k > randQueue.size())
            throw new IndexOutOfBoundsException();
        
        
        for (int i = 0; i < k; i++) {
            StdOut.println(randQueue.dequeue());
        }
    }
}
