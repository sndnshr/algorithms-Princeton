/******************************************************************************
 *  Name:         Sanduni Premaratne
 *  NetID:        sp
 *  Precept:      P03
 *
 *  Description:  Search for collinear points by brute force method
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if (points == null)
            throw new java.lang.IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new java.lang.IllegalArgumentException();
        }

        checkDuplicates(points);

        lineSegments = new ArrayList<LineSegment>();

        Point[] pointsC = points.clone();
        Arrays.sort(pointsC);

        for (int p = 0; p < pointsC.length - 3; p++) {
          for (int q = p+1; q < pointsC.length - 2; q++) {
            for (int r = q+1; r < pointsC.length - 1; r++) {

              // if slope from point 1 to 2 == slope from point 1 to 3
              if (Double.compare(pointsC[p].slopeTo(pointsC[q]),
                      pointsC[p].slopeTo(pointsC[r])) == 0) {

                for (int s = r+1; s < pointsC.length; s++) {

                  // if slope from point 1 to 2 == slope from point 1 to 4
                  if (Double.compare(pointsC[p].slopeTo(pointsC[q]),
                          pointsC[p].slopeTo(pointsC[s])) == 0) {
                          lineSegments.add(new LineSegment(pointsC[p], pointsC[s]));
                  }
                }
              }
            }
          }
        }

    }

    // the number of line segments
    public int numberOfSegments()  {
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
