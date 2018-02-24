/******************************************************************************
 *  Name:         Sanduni Premaratne
 *  NetID:        sp
 *  Precept:      P05
 *  
 *  Description:  Kd Tree
 *
 ******************************************************************************/

import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
  // construct an empty set of points
  private static final boolean HORIZONTAL = true;
  private static final boolean VERTICAL = false;
  private Node root;
  private int size;
  
  public KdTree() {
    root = null;
    size = 0;
  }
  
  private static class Node {
    private Point2D p;
    private RectHV rect;
    private Node lb;
    private Node rt; 
    private boolean type;
    
    public Node(Point2D p, RectHV rect, boolean type) {
      this.p = p;
      this.rect = rect;
      this.type = type;
    }
    
    public boolean comparePointTo(Point2D q) {
      if (this.type == HORIZONTAL)
        return p.y() > q.y();   // 0 if top
      else
        return p.x() > q.x();   // 0 if right
    }
    
    public RectHV rectRT() {
      if (this.type == HORIZONTAL)
        return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
      else
        return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
    }
    
    public RectHV rectLB() {
      if (this.type == HORIZONTAL)
        return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
      else
        return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
    }
  }
  
  // is the set empty?
  public boolean isEmpty() {
    return size == 0;
  }
  
  // number of points in the set
  public int size() {
    return size;
  }
  
  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    checkNull(p);
    if (root == null) {
      root = new Node(p, new RectHV(0, 0, 1.0, 1.0), VERTICAL);
      size++;
      return;
    }
    
    Node curr = root;
    Node prev = null;
    
    do {
      if (curr.p.equals(p)) 
        return;
      
      prev = curr;
      if (curr.comparePointTo(p))
        curr = curr.lb;
      else
        curr = curr.rt;
    } while (curr != null);
    
    if (prev.comparePointTo(p))
      prev.lb = new Node(p, prev.rectLB(), !prev.type);
    else
      prev.rt = new Node(p, prev.rectRT(), !prev.type);
    
    size++;
  }
  
  // does the set contain point p?
  public boolean contains(Point2D p) {
    checkNull(p);
    
    Node curr = root;
    do {
      if (curr.p.equals(p)) 
        return true;
      
      if (curr.comparePointTo(p))
        curr = curr.lb;
      else
        curr = curr.rt;
    } while (curr != null);
    
    return false;
  }
  
  //draw all points to standard draw
  public void draw() {
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.line(0, 0, 1, 0);
    StdDraw.line(1, 0, 1, 1);
    StdDraw.line(1, 1, 0, 1);
    StdDraw.line(0, 1, 0, 0);
    
    draw(root);
  }
  
  private void draw(Node node) {
    if (node == null)
      return;
    
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);
    node.p.draw();
    
    if (node.type == HORIZONTAL) {
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
    }
    else {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
    }
    
    draw(node.lb);
    draw(node.rt);
  }

  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    checkNull(rect);
    List<Point2D> list = new LinkedList<Point2D>();
    pointsInRange(root, rect, list);
    return list;
  }
  
  private void pointsInRange(Node node, RectHV rect, List<Point2D> list) {
    if (node == null)
      return;
    
    if (rect.contains(node.p)) {
      list.add(node.p);
      pointsInRange(node.rt, rect, list);
      pointsInRange(node.lb, rect, list);
      return;
    }
    
    if (node.comparePointTo(new Point2D(rect.xmin(), rect.ymin()))) {
      pointsInRange(node.lb, rect, list);
    }
    if (!node.comparePointTo(new Point2D(rect.xmax(), rect.ymax()))) {
      pointsInRange(node.rt, rect, list);
    }
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    checkNull(p);
    if (isEmpty())
      return null;
    
    return nearest(root, p, root.p);
  }

  private Point2D nearest(Node node, Point2D query, Point2D closest) {
    // TODO Auto-generated method stub
    if (node == null) {
      return closest;
    }
    
    double closestDist = query.distanceTo(closest);
    if (node.rect.distanceTo(query) < closestDist) {
      if (node.p.distanceTo(query) < closestDist) {
        closest = node.p;
      }
      
      if (node.comparePointTo(query)) {
        closest = nearest(node.lb, query, closest);
        closest = nearest(node.rt, query, closest);
      }
      else {
        closest = nearest(node.rt, query, closest);
        closest = nearest(node.lb, query, closest);
      }
    }
    return closest;
  }

  private void checkNull(Object object) {
    if (object.equals(null))
      throw new NullPointerException();
  }
  
  public static void main(String[] args) {
    
  }
}
