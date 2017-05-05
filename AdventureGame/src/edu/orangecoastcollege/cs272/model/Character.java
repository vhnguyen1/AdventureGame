
package edu.orangecoastcollege.cs272.model;

import javafx.scene.image.Image;

/**
 * 
 * @author mengv
 * @version 1.0
 */
public abstract class Character extends Object implements Attackable {
	
	protected Image image;
	/**
	 * The class constructor that initializes all the instance
	 * variables in the class
	 * @param x
	 * @param y
	 */
	protected Character(double x, double y, String name, Image image) {
		super(x, y, name);
		// TODO Auto-generated constructor stub
		this.image = image;
	}
	/**
	 * Accessor method that return the image
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
	/**
	 * Mutator method that sets the image of the class
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	
//	/**
//	 * Increment the x coordinate and y coordinate of object by
//	 * the amount specified.
//	 * @param xIncrease
//	 * @param yIncrease
//	 */
//	public void moveBy(double xIncrease, double yIncrease) {
//		mX += xIncrease;
//		mY += yIncrease;
//	}
//	
//	/**
//	 * Move the object to the new location.
//	 * @param newX
//	 * @param newY
//	 */
//	public void moveTo(double newX, int newY) {
//		mX = newX;
//		mY = newY;
//	}
	


}
