package edu.iastate.cs228.hw4;

/**
 *  
 * @author Gavin Monroe
 *
 */

/**
 * 
 * This class executes two convex hull algorithms: Graham's scan and Jarvis' march, over randomly
 * generated integers as well integers from a file input. It compares the execution times of 
 * these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareHullAlgorithms 
{
	/**
	 * Repeatedly take points either randomly generated or read from files. Perform Graham's scan and 
	 * Jarvis' march over the input set of points, comparing their performances.  
	 * 
	 * @param args
	 * @throws IOException 
	 * @throws IllegalStateException 
	 **/
	public static void main(String[] args) throws IllegalStateException, IOException 
	{		
		// TODO 
		// 
		// Conducts multiple rounds of convex hull construction. Within each round, performs the following: 
		// 
		//    1) If the input are random points, calls generateRandomPoints() to initialize an array 
		//       pts[] of random points. Use pts[] to create two objects of GrahamScan and JarvisMarch, 
		//       respectively.
		//
		//    2) If the input is from a file, construct two objects of the classes GrahamScan and  
		//       JarvisMarch, respectively, using the file.     
		//
		//    3) Have each object call constructHull() to build the convex hull of the input points.
		//
		//    4) Meanwhile, prints out the table of runtime statistics.
		// 
		// A sample scenario is given in Section 5 of the project description. 
		// 	
		ConvexHull[] algorithms = new ConvexHull[2]; 
		
		// Within a hull construction round, have each algorithm call the constructHull() and draw()
		// methods in the ConvexHull class.  You can visually check the result. (Windows 
		// have to be closed manually before rerun.)  Also, print out the statistics table 
		// (see Section 5). 
		System.out.println("Comparisons between Convex-Hull algorithms\n");
		Scanner scanr = new Scanner(System.in);
		int cnt = 1;
		int trail = 0;
		boolean start = true;
		do {
			System.out.print("\nTrial " + cnt + ": ");
			trail = scanr.nextInt();
			if(trail == 1){ //Random
				Random rand = new Random();
				//Output
				System.out.print("Enter # of random pts: ");
				int numRnd = scanr.nextInt();
				//Generate
				Point[] rndPts = generateRandomPoints(numRnd, rand);
				//Get Results
				algorithms[0] = new GrahamScan(rndPts); //Get Scan
				algorithms[1] = new JarvisMarch(rndPts); //Get March
				//Output
				System.out.println("\nalgorithm\tsize\ttime(ns)");
				System.out.println("---------------------------------");
				//Loop through
				for(int idx = 0; idx < algorithms.length; idx++){
					//Do Work
					algorithms[idx].constructHull();
					//Output
					algorithms[idx].draw();
					//Write to File
					algorithms[idx].writeHullToFile();
					//Output Stats
					System.out.println(algorithms[idx].stats());
				}
				//Finish
				System.out.println("---------------------------------\n");
			}else if(trail == 2){ //File
				System.out.println("Get points from a file");
				System.out.print("Filename: ");
				String file = scanr.next();
				//Try to get points(results from file
				try{
					algorithms[0] = new GrahamScan(file);
					algorithms[1] = new JarvisMarch(file);
				}catch(FileNotFoundException e){
					throw new FileNotFoundException();
				}
				//Output
				System.out.println("\nalgorithm\tsize\ttime(ns)");
				System.out.println("---------------------------------");
				//Loop through
				for(int idx = 0; idx < algorithms.length; idx++){
					algorithms[idx].constructHull();
					algorithms[idx].draw();
					algorithms[idx].writeHullToFile();
					System.out.println(algorithms[idx].stats());
				}
				//Finish
				System.out.println("---------------------------------\n");
			}else {
				start = false; //End
			}
			cnt += 1;
		}while(start);
		scanr.close();
	}
	
	
	/**
	 * This method generates a given number of random points.  The coordinates of these points are 
	 * pseudo-random numbers within the range [-50,50] × [-50,50]. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	private static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if (numPts < 1) {
			throw new IllegalArgumentException();
		}
		Point[] result = new Point[numPts];
		for(int idx = 0; idx < numPts; idx++){
			Point temp = new Point(rand.nextInt(101)-50, rand.nextInt(101)-50);
			result[idx] = temp;
		}
		return result;
	}
}
