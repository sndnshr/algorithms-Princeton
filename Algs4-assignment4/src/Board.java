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

public class Board {
  private final int [][] cells;
  private final int n;  // Dimension of board
  private int emptyCellRow;
  private int emptyCellCol;
  
  // construct a board from an n-by-n array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public Board(int[][] blocks) {
    cells = deepCopyIntMatrix(blocks);
    n = blocks.length;
    
    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        if (blocks[row][col] == 0) {
          emptyCellRow = row;
          emptyCellCol = col;
          return;
        }
      }
    }     
  }
  
  // board dimension n
  public int dimension() {
    return n;
  }
  
  // number of blocks out of place
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
  
  // sum of Manhattan distances between blocks and goal
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
  
  // is this board the goal board?
  public boolean isGoal() {
    return hamming() == 0;
  }
  
  // a board that is obtained by exchanging any pair of blocks
  public Board twin() {
    int[][] clone = deepCopyIntMatrix(cells);
    
    if (emptyCellRow != 0) {
      swap(clone, 0, 0, 0, 1);
    } else {
        swap(clone, 1, 0, 1, 1);
    }
    
    return new Board(clone);
  }
  
  // does this board equal y?
  public boolean equals(Object y) {
    if (y == this)
      return true;
    if (y == null)
      return false;
    if (this.getClass() != y.getClass())
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
        if (this.cells[row][col] != that.cells[row][col])
          return false;
      }
    }
    return true;
  }
  
  // all neighboring boards
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
    return neighbors;
  }
  
  // string representation of this board (in the output format specified below)
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(n + "\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            s.append(String.format("%2d ", cells[i][j]));
        }
        s.append("\n");
    }
    return s.toString();
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
    int val = cells[row][col] - 1;
    int finalRow = val / n;
    int finalCol = val % n;
    return Math.abs(row - finalRow) + Math.abs(col - finalCol);
  }
  
  // swap two cells of int matrix
  private void swap(int[][] input, int row1, int col1, int row2, int col2) {
    int temp = input[row1][col1];
    input[row1][col1] = input[row2][col2];
    input[row2][col2] = temp;
  }

}
