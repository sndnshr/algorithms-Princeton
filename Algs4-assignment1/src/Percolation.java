/******************************************************************************
 *  Name:    Sanduni Premaratne
 *  NetID:   sp
 *  Precept: P01
 *
 *  Description:  Model an n-by-n percolation system using the union-find
 *                data structure.
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Percolation {

  private static final int TOP = 0;
  private final int size;
  private final int bottom;
  private int openSites = 0;
  private final boolean [][] grid;
  private final WeightedQuickUnionUF uf;

  // create n-by-n grid, with all sites blocked
  public Percolation(int n) {
    if (n < 1)
        throw new IllegalArgumentException("Grid size is out of bounds!");
    
    size = n;
    bottom = size * size + 1;
    grid = new boolean [size][size];
    uf = new WeightedQuickUnionUF(size*size+2);
  }

  // Validate whether grid indices are within bounds
  private void validateIndices(int row, int col) {
    if (row < 1 || row > size)
        throw new IllegalArgumentException("Row index is out of bounds!");
    if (col < 1 || col > size)
        throw new IllegalArgumentException("Column index is out of bounds!");
  }

  // Mapping from 2-D indices to 1-D index
  private int xyTo1D(int row, int col) {
      return (row - 1)*size + col;
  }

  // open site (row, col) if it is not open already
  public void open(int row, int col) {
      validateIndices(row, col);

      if (isOpen(row, col))
          return;

      grid[row-1][col-1] = true;
      openSites++;

      if (row == 1)
          uf.union(TOP, xyTo1D(row, col));
      if (row == size)
          uf.union(bottom, xyTo1D(row, col));


      if (col > 1 && isOpen(row, col-1))
          uf.union(xyTo1D(row, col), xyTo1D(row, col-1));

      if (col < size && isOpen(row, col+1))
          uf.union(xyTo1D(row, col), xyTo1D(row, col+1));

      if (row > 1 && isOpen(row-1, col))
          uf.union(xyTo1D(row-1, col), xyTo1D(row, col));

      if (row < size && isOpen(row+1, col))
          uf.union(xyTo1D(row+1, col), xyTo1D(row, col));
  }

  // is site (row, col) open?
  public boolean isOpen(int row, int col) {
      validateIndices(row, col);
      return grid[row-1][col-1];
  }

  // is site (row, col) full?
  public boolean isFull(int row, int col) {
      validateIndices(row, col);
      return uf.connected(TOP, xyTo1D(row, col));
  }

  // number of open sites
  public int numberOfOpenSites() {
      return openSites;
  }

  // does the system percolate?
  public boolean percolates() {
      return uf.connected(TOP, bottom);
  }

  // test client (optional)
  public static void main(String[] args) {
      StdOut.print("Enter grid size: ");
      int gridSize = StdIn.readInt();
        int row, col;
        Percolation percolation = new Percolation(gridSize);
        while (!StdIn.isEmpty()) {
            row = StdIn.readInt();
            col = StdIn.readInt();
            percolation.open(row, col);
        }
        StdOut.print("Percolates? : " + percolation.percolates());
  }
}
