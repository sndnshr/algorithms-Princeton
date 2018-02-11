/******************************************************************************
 *  Name:         Sanduni Premaratne
 *  NetID:        sp
 *  Precept:      P04
 *  
 *  Description:  Solver
 *
 ******************************************************************************/

import java.util.Deque;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
  // find a solution to the initial board (using the A* algorithm)
  
  
  private boolean solvable;
  private SearchNode goalNode;
  
  public Solver(Board initial) {
    if (initial == null)
      throw new java.lang.IllegalArgumentException("Board is null!");
    
    final MinPQ<SearchNode> minPq = new MinPQ<>();
    minPq.insert(new SearchNode(initial, 0, null));
    
    while (true) {
      SearchNode currentNode = minPq.delMin();
      Board currentBoard = currentNode.getBoard();
      
      if (currentBoard.isGoal()) {
        solvable = true;
        goalNode = currentNode;
        break;
      }
      
      if (currentBoard.hamming() == 2 && currentBoard.twin().isGoal()) {
        solvable = false;
        break;
      }
      
      int moves = currentNode.getMoves();
      
      Board prevBoard;
      if (moves > 0)
        prevBoard = currentNode.prev.getBoard();
      else
        prevBoard = null;
      
      
      for (Board b: currentBoard.neighbors()) {
        
        if (b != null && b.equals(prevBoard))
          continue;
        
        minPq.insert(new SearchNode(b, moves + 1, currentNode));
      }
    }
  }
  
  private class SearchNode implements Comparable<SearchNode> {

    private final Board board;
    private final int moves;
    private final SearchNode prev;
    private final int man;
    
    public SearchNode(Board board, int moves, SearchNode prev) {
      // TODO Auto-generated constructor stub
      this.board = board;
      this.moves = moves;
      this.prev = prev;
      man = board.manhattan();
    }
    @Override
    public int compareTo(SearchNode that) {
      // TODO Auto-generated method stub
      return this.priority() - that.priority();
    }
    
    public int priority() {
      return man + moves;
    }
    
    public Board getBoard() {
      return board;
    }
    
    public int getMoves() {
      return moves;
    }
    
    public SearchNode getPrev() {
      return prev;
    }
     
  }
  
  // is the initial board solvable?
  public boolean isSolvable() {
    return solvable;
    
  }
  
  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (solvable)
      return goalNode.getMoves();
    else
      return -1;
    
  }
  
  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (!solvable) 
      return null;
    
    Deque<Board> ans = new LinkedList<>();
    SearchNode node = goalNode;
    
    while (node != null) {
      ans.addFirst(node.getBoard());
      node = node.getPrev();
    }
    return ans;
    
  }
  
  // solve a slider puzzle (given below)
  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

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
