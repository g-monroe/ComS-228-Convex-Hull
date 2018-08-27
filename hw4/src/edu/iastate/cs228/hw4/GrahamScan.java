package edu.iastate.cs228.hw4;
/**
 *  
 * @author Gavin Monroe
 *
 */

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class GrahamScan extends ConvexHull {
	/**
	 * Stack used by Grahma's scan to store the vertices of the convex hull of the
	 * points scanned so far. At the end of the scan, it stores the hull vertices in
	 * the counterclockwise order.
	 */
	private PureStack<Point> vertexStack;

	/**
	 * Call corresponding constructor of the super class. Initialize two variables:
	 * algorithm (from the class ConvexHull) and vertexStack.
	 * 
	 * @throws IllegalArgumentException
	 *             if pts.length == 0
	 */
	public GrahamScan(Point[] pts) throws IllegalArgumentException {

		super(pts);

		if (pts.length == 0) {
			throw new IllegalArgumentException();
		}

		algorithm = "Graham's Scan";
		vertexStack = new ArrayBasedStack<Point>();
	}

	/**
	 * Call corresponding constructor of the super class. Initialize algorithm and
	 * vertexStack.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException
	 *             when the input file contains an odd number of integers
	 */
	public GrahamScan(String inputFileName) throws FileNotFoundException, InputMismatchException {
		super(inputFileName);
		// TODO
		vertexStack = new ArrayBasedStack<Point>();
		algorithm = "Graham's Scan";
	}

	// -------------
	// Graham's scan
	// -------------

	/**
	 * This method carries out Graham's scan in several steps below:
	 * 
	 * 1) Call the private method setUpScan() to sort all the points in the array
	 * pointsNoDuplicate[] by polar angle with respect to lowestPoint.
	 * 
	 * 2) Perform Graham's scan. To initialize the scan, push pointsNoDuplicate[0]
	 * and pointsNoDuplicate[1] onto vertexStack.
	 * 
	 * 3) As the scan terminates, vertexStack holds the vertices of the convex hull.
	 * Pop the vertices out of the stack and add them to the array hullVertices[],
	 * starting at index vertexStack.size() - 1, and decreasing the index toward 0.
	 * 
	 * Two degenerate cases below must be handled:
	 * 
	 * 1) The array pointsNoDuplicates[] contains just one point, in which case the
	 * convex hull is the point itself.
	 * 
	 * 2) The array contains only collinear points, in which case the hull is the
	 * line segment connecting the two extreme points.
	 */
	public void constructHull() {
		// TODO
		// Step 1
		long tmrStart = System.nanoTime();
		setUpScan();
		int index = 0;
		if (pointsNoDuplicate.length == 1) { // Only One Point
			index = 0;
			vertexStack.push(pointsNoDuplicate[index]);
			
		} else if (pointsNoDuplicate.length == 2) { //Only two Points
			index = 0;
			vertexStack.push(pointsNoDuplicate[index]);
			vertexStack.push(pointsNoDuplicate[index + 1]);
			
		} else {// Other(three points)
			index = 0;
			vertexStack.push(pointsNoDuplicate[index]);
			vertexStack.push(pointsNoDuplicate[index + 1]);
			vertexStack.push(pointsNoDuplicate[index + 2]);
			
		}
		// Step 2
		for (int idx = 3; idx < pointsNoDuplicate.length; idx++) {
			Point start = new Point();
			Point val = new Point();
			
			PolarAngleComparator comp = new PolarAngleComparator(start, true);
			do {
				
				start = vertexStack.pop(); //Remove
				//Continue
				val = vertexStack.peek();
				comp = new PolarAngleComparator(start, false);
				
			} while (comp.compare(val, pointsNoDuplicate[idx]) <= 0);

			vertexStack.push(start);
			vertexStack.push(pointsNoDuplicate[idx]);
		}
		// Final Step

		hullVertices = new Point[vertexStack.size()];
		for (int idx = vertexStack.size() - 1; idx >= 0; idx--) {
			hullVertices[idx] = vertexStack.pop();
		}
		time = System.nanoTime() - tmrStart;
	}

	/**
	 * Set the variable quicksorter from the class ConvexHull to sort by polar angle
	 * with respect to lowestPoint, and call quickSort() from the QuickSortPoints
	 * class on pointsNoDupliate[]. The argument supplied to quickSort() is an
	 * object created by the constructor call PolarAngleComparator(lowestPoint,
	 * true).
	 * 
	 * Ought to be private, but is made public for testing convenience.
	 *
	 */
	public void setUpScan() {
		//Init
		quicksorter = new QuickSortPoints(pointsNoDuplicate);
		PolarAngleComparator comp = new PolarAngleComparator(lowestPoint,true);
		//Sort
		quicksorter.quickSort(comp);
	}
}
