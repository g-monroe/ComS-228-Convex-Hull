package edu.iastate.cs228.hw4;

/**
 *  
 * @author Gavin Monroe
 *
 */


import java.util.Comparator;

/**
 * 
 * This class compares two points p1 and p2 by polar angle with respect to a reference point.  
 *  
 */
public class PolarAngleComparator implements Comparator<Point>
{
	private Point referencePoint; 
	private boolean flag;  // used for breaking a tie between two points that have 
	                       // the same polar angle with respect to referencePoint
	
	/**
	 * 
	 * @param p reference point
	 */
	public PolarAngleComparator(Point p, boolean flag)
	{
		referencePoint = p; 
		this.flag = flag; 
	}
	
	/**
	 * Use cross product and dot product to implement this method.  Do not take square roots 
	 * or use trigonometric functions. Calls private methods crossProduct() and dotProduct(). 
	 * 
	 * Precondition: both p1 and p2 are different from referencePoint. 
	 * 
	 * @param p1
	 * @param p2
	 * @return  0 if p1 and p2 are the same point
	 *         -1 if one of the following three conditions holds: 
	 *                a) the cross product between p1 - referencePoint and p2 - referencePoint 
	 *                   is greater than zero. 
	 *                b) the above cross product equals zero, flag == true, and p1 is closer to 
	 *                   referencePoint than p2 is. 
	 *                c) the above cross product equals zero, flag == false, and p1 is further 
	 *                   from referencePoint than p2 is.   
	 *          1  otherwise. 
	 *                   
	 */
	public int compare(Point p1, Point p2)
	{
		// TODO
		if (p1 == p2) {
			return 0;
		}
    	int result = crossProduct(p1, p2);
    	if ((0 < result) || (0 == result && flag == true && dotProduct(p1, referencePoint) < dotProduct(p2, referencePoint)) || (0 == result && flag == false && dotProduct(p1, referencePoint) > dotProduct(p2, referencePoint)) ) {
    		return -1;
    	}
		return 1; 
	}
	    

    /**
     * 
     * @param p1
     * @param p2
     * @return cross product of two vectors: p1 - referencePoint and p2 - referencePoint
     */
    public int crossProduct(Point p1, Point p2)
    {
    	
    	Point newP1 = new Point((p1.getX() - referencePoint.getX()), (p1.getY() - referencePoint.getY()));
    	Point newP2 = new Point((p2.getX() - referencePoint.getX()), (p2.getY() - referencePoint.getY()));
    	int result = (newP1.getX() * newP2.getY()) - (newP2.getX() * newP1.getY());
    	return result; 
    }

    /**
     * 
     * @param p1
     * @param p2
     * @return dot product of two vectors: p1 - referencePoint and p2 - referencePoint
     */
    public int dotProduct(Point p1, Point p2)
    {
    	// TODO 
    	Point newP1 = new Point((p1.getX() - referencePoint.getX()), (p1.getY() - referencePoint.getY()));
    	Point newP2 = new Point((p2.getX() - referencePoint.getX()), (p2.getY() - referencePoint.getY()));
    	int result = (newP1.getX() * newP2.getX()) + (newP2.getY() * newP1.getY());
    	return result;
    }
}
