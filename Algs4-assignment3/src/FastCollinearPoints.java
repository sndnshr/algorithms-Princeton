/******************************************************************************
 *  Name:         Sanduni Premaratne
 *  NetID:        sp
 *  Precept:      P03
 *
 *  Description:  Search for collinear points by sort based method
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
    private final int pointsLen;

   // finds all line segments containing 4 or more points
   public FastCollinearPoints(Point[] points) {

       if (points == null)
           throw new java.lang.IllegalArgumentException();

       for (int i = 0; i < points.length; i++) {
           if (points[i] == null)
               throw new java.lang.IllegalArgumentException();
       }

       checkDuplicates(points);

       pointsLen = points.length;

       lineSegments = new ArrayList<LineSegment>();

       Point[] pointsCopy = points.clone();
       Arrays.sort(pointsCopy);

       for (int i = 0; i < points.length; i++) {
           Point p = points[i];
           Point[] pointsBySlope = pointsCopy.clone();
           Arrays.sort(pointsBySlope, p.slopeOrder());

           int k = 1;
           while (k < pointsLen) {
               ArrayList<Point> pointList = new ArrayList<>();

               final double REF_SLOPE = p.slopeTo(pointsBySlope[k]);

               do {
                   pointList.add(pointsBySlope[k++]);
               } while (k < pointsLen &&
                       Double.compare(p.slopeTo(pointsBySlope[k]), REF_SLOPE) == 0);

               if (pointList.size() >= 3 && p.compareTo(pointList.get(0)) < 0) {
                   Point min = p;
                   Point max = pointList.get(pointList.size()-1);
                   lineSegments.add(new LineSegment(min, max));
               }
           }
       }
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
