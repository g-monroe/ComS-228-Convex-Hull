package edu.iastate.cs228.hw4;
/**
 *  
 * @author Gavin Monroe
 *
 */

import java.util.Arrays;
import java.util.Comparator;

/**
 * This class sorts an array of Point objects using a provided Comparator.  For the purpose
 * you may adapt your implementation of quicksort from Project 2.  
 */

public class QuickSortPoints
{
	private Point[] points;  	// Array of points to be sorted.
	

	/**
	 * Constructor takes an array of Point objects. 
	 * 
	 * @param pts
	 */
	QuickSortPoints(Point[] pts)
	{
		points = pts;//Set
		//Run
		getSortedPoints(pts);
	}
	
	
	/**
	 * Copy the sorted array to pts[]. 
	 * 
	 * @param pts  array to copy onto
	 */
	void getSortedPoints(Point[] pts)
	{
		for(int idx =0; idx<pts.length; idx++) {
			pts[idx] = this.points[idx];
		}
	}

	
	/**
	 * Perform quicksort on the array points[] with a supplied comparator. 
	 * 
	 * @param comp
	 */
	public void quickSort(Comparator<Point> comp)
	{
		quickSortRec(0, points.length -1, comp);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last, Comparator<Point> comp)
	{
		if (first >= last) {
			return;
		}
		int p = partition(first, last, comp);
		quickSortRec(first, p - 1, comp);
		quickSortRec(p + 1, last, comp);
	}
	

	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last, Comparator<Point> comp)
	{
		Point piviot = points[last];
		int i = (first - 1);
		for (int j = first; j < last; j++) {
			if (comp.compare(points[j], piviot) == -1) {
				i++;
				Point temp = points[i];
				points[i] = points[j];
				points[j] = temp;
			}
		}
		Point temp = points[i + 1];
		points[i + 1] = points[last];
		points[last] = temp;
		return i + 1;
	}
}


