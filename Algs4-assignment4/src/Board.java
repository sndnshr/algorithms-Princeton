/******************************************************************************
 *  Name:         Sanduni Premaratne
 *  NetID:        sp
 *  Precept:      P04
 *  
 *  Description:  Board data type 
 *
 ******************************************************************************/
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.StdRandom;

public class Board {
  private final int [][] cells;
  private final int n;  // Dimension of board
  private int emptyCellRow;
  private int emptyCellCol;
  
  //construct a board from an n-by-n array of blocks
  //(where blocks[i][j] = block in row i, column j)
  public Board(int[][] blocks) {
    cells = deepCopyIntMatrix(blocks);
    n = blocks.length;
    
    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        if (blocks[row][col] == 0) {
          emptyCellRow = row;
          emptyCellCol = col;
        }
      }
    }     
  }
  
  //board dimension n
  public int dimension() {
    return n;
  }
  
  //number of blocks out of place
  public int hamming() {
    int result = 0;
    
    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        if (row == emptyCellRow && col == emptyCellCol)
          continue;
        
        if (dist(row, col) != 0)
          result++;
      }
    }
    return result;
  }
  
  //sum of Manhattan distances between blocks and goal
  public int manhattan() {
    int result = 0;
    
    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        if (row == emptyCellRow && col == emptyCellCol)
          continue;
        
        result += dist(row, col);
      }
    }
    
    return result;
  }
  
  //is this board the goal board?
  public boolean isGoal() {
    return hamming() == 0;
  }
  
  //a board that is obtained by exchanging any pair of blocks
  public Board twin() {
    int[][] clone = deepCopyIntMatrix(cells);
    
    int row1 = StdRandom.uniform(n-1);
    int col1 = StdRandom.uniform(n-1);
    int row2 = StdRandom.uniform(n-1);
    int col2 = StdRandom.uniform(n-1);
    
    do {
      row1 = StdRandom.uniform(n-1);
    } while (row1 == emptyCellRow);
      
    do {
      row2 = StdRandom.uniform(n-1);
    } while (row1 == row2 && col1 == col2 || row2 == emptyCellRow);
    
    swap(clone, row1, col1, row2, col2);
    
    return new Board(clone);
  }
  
  //does this board equal y?
  public boolean equals(Object y) {
    if (y == this)
      return true;
    if (y == null)
      return false;
    Board that = (Board) y;
    
    if (this.emptyCellRow != that.emptyCellRow)
      return false;
    if (this.emptyCellCol != that.emptyCellCol)
      return false;
    if (this.n != that.n)
      return false;
    
    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        if (cells[row][col] != that.cells[row][col])
          return false;
      }
    }
    return true;
  }
  
  //all neighboring boards
  public Iterable<Board> neighbors() {
    List<Board> neighbors = new LinkedList<>();
    
    if (emptyCellRow > 0) {
      int[][] up = deepCopyIntMatrix(cells);
      swap(up, emptyCellRow, emptyCellCol, emptyCellRow-1, emptyCellCol);
      neighbors.add(new Board(up));
    }
    
    if (emptyCellCol > 0) {
      int[][] left = deepCopyIntMatrix(cells);
      swap(left, emptyCellRow, emptyCellCol, emptyCellRow, emptyCellCol-1);
      neighbors.add(new Board(left));
    }
    
    if (emptyCellRow < n-1) {
      int[][] down = deepCopyIntMatrix(cells);
      swap(down, emptyCellRow, emptyCellCol, emptyCellRow+1, emptyCellCol);
      neighbors.add(new Board(down));
    }
    
    if (emptyCellCol < n-1) {
      int[][] right = deepCopyIntMatrix(cells);
      swap(right, emptyCellRow, emptyCellCol, emptyCellRow, emptyCellCol+1);
      neighbors.add(new Board(right));
    }
    return null;
  }
  
  //string representation of this board (in the output format specified below)
  public String toString() {
    return null;
  }
  
  private int[][] deepCopyIntMatrix(int [][]input) {
    if (input == null)
      return null;
    
    int [][] clone = new int[input.length][];
    for (int row = 0; row < input.length; row++) {
      clone[row] = input[row].clone();
    }
    return clone;
  }
  
  private int dist(int row, int col) {
    int val = cells[row][col];
    int finalRow = val / n;
    int finalCol = val % n - 1;
    return Math.abs(row - finalRow) + Math.abs(col - finalCol);
  }
  
  //swap two cells of int matrix
  private void swap(int[][] input, int row1, int col1, int row2, int col2) {
    int temp = input[row1][col1];
    input[row1][col1] = input[row2][col2];
    input[row2][col2] = temp;
  }

  //unit tests (not graded)
  public static void main(String[] args) {
    
  }
}
