/******************************************************************************
 *  Name:         Sanduni Premaratne
 *  NetID:        sp
 *  Precept:      P03
 *  
 *  Description:  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private final ArrayList<LineSegment> lineSegments;
    
   // finds all line segments containing 4 or more points
   public FastCollinearPoints(Point[] points) {
       
       if (points == null)
           throw new java.lang.IllegalArgumentException();
       
       for (int i = 0; i < points.length; i++) {
           if (points[i] == null)
               throw new java.lang.IllegalArgumentException();
       }
       
       checkDuplicates(points);
       
       lineSegments = new ArrayList<LineSegment>();
       
       Point[] pointsCopy = points.clone();
       Arrays.sort(pointsCopy);
       
       
       for (int i = 0; i < points.length; i++) {
           Point p = points[i];
           Arrays.sort(pointsCopy, p.slopeOrder());
           
           ArrayList<Point> pointList = new ArrayList<>();
           
           pointList.add(pointsCopy[1]);
           
           for (int j = 2; j < points.length; j++) {
               Point lastPoint = pointList.get(pointList.size()-1);
               
               if (Double.compare(p.slopeTo(lastPoint), p.slopeTo(pointsCopy[j])) 
                       == 0) {
                   pointList.add(pointsCopy[j]);
               }
               else if (pointList.size() > 2) {
                   pointList.add(p);
                   Point[] pointsArray = pointList.toArray(new Point[0]);
                   Arrays.sort(pointsArray);
                   
                   Point first = pointsArray[0];
                   Point last = pointsArray[pointsArray.length-1];
                   lineSegments.add(new LineSegment(first, last));
                   pointList.clear();
                   pointList.add(pointsCopy[j]);
               }
               else {
                   pointList.clear();
                   pointList.add(pointsCopy[j]);
               }
           }
       }
       
//       for (int i = 0; i < pointsCopy.length; i++) {
//           Point p = points[i];
//           Arrays.sort(pointsCopy, p.slopeOrder());          
//           int min = 1;
//           int max = 1;
//           ArrayList<Point> pointList = new ArrayList<>();
//           while (min < pointsCopy.length - 2) {
////               int max  = min;
//               boolean continueSearch = true;               
//               while (max < pointsCopy.length && continueSearch) {                   
//                   if (p.slopeTo(pointsCopy[min]) == p.slopeTo(pointsCopy[max])) {
//                       pointList.add(pointsCopy[max]);
//                       max++;
//                   }
//                   else if (pointList.size() > 2) {
//                       pointList.add(p);
//                       Point[] segPoints = pointList.toArray(new Point[0]);
//                       Arrays.sort(segPoints);
//                       Point beg = segPoints[0];
//                       Point end = segPoints[segPoints.length - 1];
//                       lineSegments.add(new LineSegment(beg, end));
//                       continueSearch = false;
//                   }
//                   else
//                       continueSearch = false;
//               }
//               min = max;
//               pointList.clear();
//           }
//       }
   }
   
   // the number of line segments
   public int numberOfSegments() {
       return lineSegments.size();
   }
   
   // the line segments
   public LineSegment[] segments() {
       return lineSegments.toArray(new LineSegment[0]);
   }
   
   private void checkDuplicates(Point[] points) {
       
       for (int i = 0; i < points.length; i++) {
           for (int j = i+1; j < points.length; j++) {
               if (points[i].compareTo(points[j]) == 0)
                   throw new 
                   java.lang.IllegalArgumentException("Has duplicate points!");
           }
       }
   }
   
   
   
   public static void main(String[] args) {

       // read the n points from a file
       In in = new In(args[0]);
       int n = in.readInt();
       Point[] points = new Point[n];
       for (int i = 0; i < n; i++) {
           int x = in.readInt();
           int y = in.readInt();
           points[i] = new Point(x, y);
       }

       // draw the points
       StdDraw.enableDoubleBuffering();
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       for (Point p : points) {
           p.draw();
       }
       StdDraw.show();

       // print and draw the line segments
       FastCollinearPoints collinear = new FastCollinearPoints(points);
       for (LineSegment segment : collinear.segments()) {
           StdOut.println(segment);
           segment.draw();
       }
       StdDraw.show();
   }
}