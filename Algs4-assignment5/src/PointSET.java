/******************************************************************************
 *  Name:         Sanduni Premaratne
 *  NetID:        sp
 *  Precept:      P05
 *  
 *  Description:  Point set
 *
 ******************************************************************************/

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
  
  private final TreeSet<Point2D> points;
  
  // construct an empty set of points
  public PointSET() {
     points = new TreeSet<>();
  }
  
  // is the set empty?
  public boolean isEmpty() {
     return points.isEmpty();
  }
  
  // number of points in the set
  public int size() {
     return points.size();
  }
  
  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    checkNull(p);
    if (!points.contains(p)) {
      points.add(p);
    }  
  }
  
  // does the set contain point p?
  public boolean contains(Point2D p) {
    checkNull(p);
    return points.contains(p);
  }
  
  // draw all points to standard draw
  public void draw() {
    for (Point2D p: points)
      p.draw();
  }
  
  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    checkNull(rect);
    List<Point2D> pointsInRect = new LinkedList<Point2D>();
    
    for (Point2D p: points) {
      if (rect.contains(p)) {
        pointsInRect.add(p);
      }
    }
    return pointsInRect;
  }
  
  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    checkNull(p);
    Point2D nNeighbor = null;
    
    for (Point2D point:points) {
      if (nNeighbor == null)
        nNeighbor = point;
      
      if (point.distanceSquaredTo(p) < nNeighbor.distanceSquaredTo(p))
        nNeighbor = point;
    }
    
    return nNeighbor;
  }
  
  private void checkNull(Object object) {
    if (object == null)
      throw new IllegalArgumentException();
  }
  
  // Unit testing of the methods (optional)
  public static void main(String[] args) {
  }

}
