package edu.iastate.cs228.hw4;
/**
 *  
 * @author Gavin Monroe
 *
 */

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class JarvisMarch extends ConvexHull {
	// last element in pointsNoDuplicate(), i.e., highest of all points (and the
	// rightmost one in case of a tie)
	private Point highestPoint;

	// left chain of the convex hull counterclockwise from lowestPoint to
	// highestPoint
	private PureStack<Point> leftChain;

	// right chain of the convex hull counterclockwise from highestPoint to
	// lowestPoint
	private PureStack<Point> rightChain;

	/**
	 * Call corresponding constructor of the super class. Initialize the variable
	 * algorithm (from the class ConvexHull). Set highestPoint. Initialize the two
	 * stacks leftChain and rightChain.
	 * 
	 * @throws IllegalArgumentException
	 *             when pts.length == 0
	 */
	public JarvisMarch(Point[] pts) throws IllegalArgumentException {
		super(pts);
		// TODO
		leftChain = new ArrayBasedStack<Point>();
		rightChain = new ArrayBasedStack<Point>();

		algorithm = "Jarvis' March";
		highestPoint = pointsNoDuplicate[pointsNoDuplicate.length - 1];
	}

	/**
	 * Call corresponding constructor of the superclass. Initialize the variable
	 * algorithm. Set highestPoint. Initialize leftChain and rightChain.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException
	 *             when the input file contains an odd number of integers
	 */
	public JarvisMarch(String inputFileName) throws FileNotFoundException, InputMismatchException {
		super(inputFileName);
		// TODO
		leftChain = new ArrayBasedStack<Point>();
		rightChain = new ArrayBasedStack<Point>();

		algorithm = "Jarvis' March";
		highestPoint = pointsNoDuplicate[pointsNoDuplicate.length - 1];
	}

	// ------------
	// Javis' march
	// ------------

	/**
	 * Calls createRightChain() and createLeftChain(). Merge the two chains stored
	 * on the stacks rightChain and leftChain into the array hullVertices[].
	 * 
	 * Two degenerate cases below must be handled:
	 * 
	 * 1) The array pointsNoDuplicates[] contains just one point, in which case the
	 * convex hull is the point itself.
	 * 
	 * 2) The array contains collinear points, in which case the hull is the line
	 * segment connecting the two extreme points from them.
	 */
	public void constructHull() {
		long tmr = System.nanoTime();
		int index = 0;
		if (pointsNoDuplicate.length == 1) { // One point
			index = 0;
			//Push Right
			rightChain.push(pointsNoDuplicate[index]);
		} else if (pointsNoDuplicate.length == 2) { //Only Two points
			index = 0;
			//Push Right
			rightChain.push(pointsNoDuplicate[index]);
			//Push Left
			leftChain.push(pointsNoDuplicate[index + 1]);
		} else { //Call Create
			//Left
			createLeftChain();
			//Right
			createRightChain();
		}
		
		// Merge two Chains.

		int lftSize = leftChain.size();
		int rghtSize = rightChain.size();
		
		int chainSz = rghtSize + lftSize;
		hullVertices = new Point[chainSz];
		//Left Pop
		for (int idx = chainSz - 1; idx > ((chainSz - 1) - (lftSize)); idx--) {
			hullVertices[idx] = leftChain.pop();
		}
		// Right Pop
		for (int idx = ((chainSz - 1) - (lftSize)); idx >= 0; idx--) {
			hullVertices[idx] = rightChain.pop();
		}
		time = System.nanoTime() - tmr;
	}

	/**
	 * Construct the right chain of the convex hull. Starts at lowestPoint and wrap
	 * around the points counterclockwise. For every new vertex v of the convex
	 * hull, call nextVertex() to determine the next vertex, which has the smallest
	 * polar angle with respect to v. Stop when the highest point is reached.
	 * 
	 * Use the stack rightChain to carry out the operation.
	 * 
	 * Ought to be private, but is made public for testing convenience.
	 */
	public void createRightChain() {
		// TODO
		Point point = lowestPoint;
		while (!(point.equals(highestPoint))) {
			rightChain.push(point);
			point = nextVertex(point);
		}
	}

	/**
	 * Construct the left chain of the convex hull. Starts at highestPoint and
	 * continues the counterclockwise wrapping. Stop when lowestPoint is reached.
	 * 
	 * Use the stack leftChain to carry out the operation.
	 * 
	 * Ought to be private, but is made public for testing convenience.
	 */
	public void createLeftChain() {
		// TODO
		Point point = highestPoint;
		while (!(point.equals(lowestPoint))) {
			leftChain.push(point);
			point = nextVertex(point);
		}
	}

	/**
	 * Return the next vertex, which is less than all other points by polar angle
	 * with respect to the current vertex v. When there is a tie, pick the point
	 * furthest from v. Comparison is done using a PolarAngleComparator object
	 * created by the constructor call PolarAngleCompartor(v, false).
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param v
	 *            current vertex
	 * @return
	 */
	public Point nextVertex(Point v) {
		Point minPt = v;
		PolarAngleComparator comp = new PolarAngleComparator(v, false);
		for(int idx = 0; idx<pointsNoDuplicate.length; idx++){
			if(( minPt.equals(v) || comp.compare(minPt, pointsNoDuplicate[idx]) > 0) && !pointsNoDuplicate[idx].equals(v)){
				minPt = pointsNoDuplicate[idx];
			}
		}
		return minPt; 
	}
}
