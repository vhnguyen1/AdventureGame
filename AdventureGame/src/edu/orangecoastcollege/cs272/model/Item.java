/**
 * 
 */
package edu.orangecoastcollege.cs272.model;

/**
 * 
 * @author mengv
 * @version 1.0
 */
public abstract class Item extends Object {
	
	private double mWeight;
	
	/**
	 * The class constructor that initializes all the instance
	 * variables in the class
	 * @param x
	 * @param y
	 */
	protected Item(double x, double y, String name, double weight) {
		super(x, y, name);
		mWeight = weight;
	}

}
