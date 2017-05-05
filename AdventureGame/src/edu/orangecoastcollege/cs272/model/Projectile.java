/**
 * 
 */
package edu.orangecoastcollege.cs272.model;

import edu.orangecoastcollege.cs272.view.View;
import javafx.scene.image.Image;

/**
 * 
 * @author mengv
 * @version 1.0
 */
public class Projectile extends Object {

	private Image mImage;
	private double mDamage;
	private double mSpeed;
	private double mSpeedX, mSpeedY;
	private boolean mVisible;
	
	
	/**
	 * The class constructor that initializes all the instance
	 * variables in the class
	 * @param x
	 * @param y
	 * @param name
	 */
	public Projectile(double startX, double startY, double destX, double destY, double damage, String name, Image image) {
		super(startX, startY, name);
		mDamage = damage;
		mImage = image;
		mVisible = true;
		mSpeed = 15;
		double dY = (double) (mY - destY);
		double dX = (double) (mX - destX);
		double angle = Math.atan(dY / dX);
		if ((dX > 0 && dY > 0) || (dX > 0 && dY < 0))
			angle += Math.PI;
		mSpeedY = Math.sin(angle) * mSpeed;
		mSpeedX = Math.cos(angle) * mSpeed;
	}

	public double getDamage() {
		return mDamage;
	}
	
	public void update() {
		mX += mSpeedX;
		mY += mSpeedY;
		if (mX < 0 || mX > View.WINDOW_WIDTH || mY < 0 || mY > View.WINDOW_HEIGHT) {
			mVisible = false;
		}
	}
	
	public boolean isVisible() {
		return mVisible;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(mDamage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mSpeed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mSpeedX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mSpeedY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (mVisible ? 1231 : 1237);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Projectile other = (Projectile) obj;
		if (Double.doubleToLongBits(mDamage) != Double.doubleToLongBits(other.mDamage))
			return false;
		if (Double.doubleToLongBits(mSpeed) != Double.doubleToLongBits(other.mSpeed))
			return false;
		if (Double.doubleToLongBits(mSpeedX) != Double.doubleToLongBits(other.mSpeedX))
			return false;
		if (Double.doubleToLongBits(mSpeedY) != Double.doubleToLongBits(other.mSpeedY))
			return false;
		if (mVisible != other.mVisible)
			return false;
		return true;
	}

	/**
	 * Accessor method that return the speedX
	 * @return the speedX
	 */
	public double getSpeedX() {
		return mSpeedX;
	}

	/**
	 * Mutator method that sets the speedX of the class
	 * @param speedX the speedX to set
	 */
	public void setSpeedX(double speedX) {
		mSpeedX = speedX;
	}

	/**
	 * Accessor method that return the speedY
	 * @return the speedY
	 */
	public double getSpeedY() {
		return mSpeedY;
	}

	/**
	 * Mutator method that sets the speedY of the class
	 * @param speedY the speedY to set
	 */
	public void setSpeedY(double speedY) {
		mSpeedY = speedY;
	}

	/**
	 * Accessor method that return the image
	 * @return the image
	 */
	public Image getImage() {
		return mImage;
	}

	/**
	 * Mutator method that sets the image of the class
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		mImage = image;
	}
	
	
}
