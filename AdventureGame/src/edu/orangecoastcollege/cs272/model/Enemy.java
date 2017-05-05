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
public class Enemy extends Character {
	private static final double DEFAULT_BASE_DEFENSE = 5;

	private int mExperienceReward;
	private double mAttackPower;
	private double mBaseDefense;
	private double mSpeed;
	private double mLevel;
	private double mCurrHP;
	private double mMaxHP;
	private boolean mWounded;

	/**
	 * The class constructor that initializes all the instance variables in the
	 * class
	 * 
	 * @param x
	 * @param y
	 * @param image
	 */
	public Enemy(double x, double y, String name, Image image) {
		super(x, y, name, image);
		mSpeed = 0.5;
		mCurrHP = 100;
		mMaxHP = 100;
		mBaseDefense = DEFAULT_BASE_DEFENSE;
		mWounded = false;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Accessor method that return the wounded
	 * 
	 * @return the wounded
	 */
	public boolean isWounded() {
		return mWounded;
	}

	/**
	 * Mutator method that sets the wounded of the class
	 * 
	 * @param wounded
	 *            the wounded to set
	 */
	public void setWounded(boolean wounded) {
		mWounded = wounded;
	}

	/**
	 * Accessor method that return the speed
	 * 
	 * @return the speed
	 */
	public double getSpeed() {
		return mSpeed;
	}

	/**
	 * Mutator method that sets the speed of the class
	 * 
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(double speed) {
		mSpeed = speed;
	}

	/**
	 * Accessor method that return the hP
	 * 
	 * @return the hP
	 */
	public double getCurrHP() {
		return mCurrHP;
	}

	/**
	 * Accessor method that return the experienceReward
	 * 
	 * @return the experienceReward
	 */
	public int getExperienceReward() {
		return mExperienceReward;
	}

	/**
	 * Accessor method that return the baseDefense
	 * 
	 * @return the baseDefense
	 */
	public double getBaseDefense() {
		return mBaseDefense;
	}

	/**
	 * Accessor method that return the level
	 * 
	 * @return the level
	 */
	public double getLevel() {
		return mLevel;
	}

	/**
	 * Accessor method that return the maxHP
	 * 
	 * @return the maxHP
	 */
	public double getMaxHP() {
		return mMaxHP;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.orangecoastcollege.cs272.model.Attackable#attack()
	 */
	@Override
	public double getAttackPower() {
		return mAttackPower;
	}

	public void update(Player player) {
		if (isAlive()) {
			double dY = (double) (mY - player.getY());
			double dX = (double) (mX - player.getX());
			double angle = Math.atan(dY / dX);
			if ((dX > 0 && dY > 0) || (dX > 0 && dY < 0))
				angle += Math.PI;
			double mSpeedY = Math.sin(angle) * mSpeed;
			double mSpeedX = Math.cos(angle) * mSpeed;
			this.setX(mX + mSpeedX);
			this.setY(mY + mSpeedY);
		}

	}

	public void gotAttacked(double attackDamage) {
		double actualDamage = attackDamage - mBaseDefense;
		if (actualDamage > 0) {
			mCurrHP -= actualDamage;
		}
	}

	public boolean isAlive() {
		return mCurrHP > 0;
	}
}
