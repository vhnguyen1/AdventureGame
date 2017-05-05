/**
 * 
 */
package edu.orangecoastcollege.cs272.model;


/**
 * 
 * @author mengv
 * @version 1.0
 */
public abstract class Object {
	protected String mName;
	protected double mX;
	protected double mY;
	
	protected Object(double x, double y, String name) {
		mX = x;
		mY = y;
		mName = name;
	}

	/**
	 * Accessor method that return the name
	 * @return the name
	 */
	public String getName() {
		return mName;
	}

	/**
	 * Mutator method that sets the name of the class
	 * @param name the name to set
	 */
	public void setName(String name) {
		mName = name;
	}

	/**
	 * Accessor method that return the x
	 * @return the x
	 */
	public double getX() {
		return mX;
	}

	/**
	 * Mutator method that sets the x of the class
	 * @param x the x to set
	 */
	public void setX(double x) {
		mX = x;
	}

	/**
	 * Accessor method that return the y
	 * @return the y
	 */
	public double getY() {
		return mY;
	}

	/**
	 * Mutator method that sets the y of the class
	 * @param y the y to set
	 */
	public void setY(double y) {
		mY = y;
	}
	
	public void setPos(double x, double y) {
		mX = x;
		mY = y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mName == null) ? 0 : mName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(mX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Object other = (Object) obj;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		if (Double.doubleToLongBits(mX) != Double.doubleToLongBits(other.mX))
			return false;
		if (Double.doubleToLongBits(mY) != Double.doubleToLongBits(other.mY))
			return false;
		return true;
	}
	
}
