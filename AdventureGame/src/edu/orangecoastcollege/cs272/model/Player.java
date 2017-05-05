/**
 * 
 */
package edu.orangecoastcollege.cs272.model;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.image.Image;



/**
 * 
 * @author mengv
 * @version 1.0
 */
public class Player extends Character {
	public static final double DEFAULT_BASE_DEFENSE = 5;
	public static final double DEFAULT_BASE_EQUIPMENTCAP = 100;
	public static final double DEFAULT_BASE_MOVESPEED = 1.5;
	// Instance variables
	private ArrayList<Item> mInventory;
	private double mBaseDefense;
	private double mEquipmentCap;
	private int mLevel;
	private int mExperience;
	private double mMovespeed;
	private double mNextLevelExp;
	private double mMaxHP;
	private double mCurrentHP;
	private double mAttackPower;
	private Skill mSkill;
	
	/**
	 * The class constructor that initializes all the instance
	 * variables in the class
	 * @param x
	 * @param y
	 * @param image
	 */
	public Player(double x, double y, String name, Image image) {
		super(x, y , name, image);
		// TODO Auto-generated constructor stub
		mInventory = new ArrayList<>();
		mBaseDefense = DEFAULT_BASE_DEFENSE;
		mEquipmentCap = DEFAULT_BASE_EQUIPMENTCAP;
		mLevel = 1;
		mExperience = 0;
		mMovespeed = DEFAULT_BASE_MOVESPEED;
		mNextLevelExp = 1000;
		mMaxHP = 100;
		mCurrentHP = 100;
		mAttackPower = 10;
		mSkill = Skill.NoSkill;
	}
	

	/**
	 * Accessor method that return the skill
	 * @return the skill
	 */
	public Skill getSkill() {
		return mSkill;
	}


	/**
	 * Mutator method that sets the skill of the class
	 * @param skill the skill to set
	 */
	public void setSkill(Skill skill) {
		this.mSkill = skill;
	}


	/**
	 * Accessor method that return the inventory
	 * @return the inventory
	 */
	public ArrayList<Item> getInventory() {
		return mInventory;
	}

	/**
	 * Mutator method that sets the inventory of the class
	 * @param inventory the inventory to set
	 */
	public void setInventory(ArrayList<Item> inventory) {
		mInventory = inventory;
	}

	/**
	 * Accessor method that return the baseDefense
	 * @return the baseDefense
	 */
	public double getBaseDefense() {
		return mBaseDefense;
	}

	/**
	 * Mutator method that sets the baseDefense of the class
	 * @param baseDefense the baseDefense to set
	 */
	public void setBaseDefense(double baseDefense) {
		mBaseDefense = baseDefense;
	}

	/**
	 * Accessor method that return the equipmentCap
	 * @return the equipmentCap
	 */
	public double getEquipmentCap() {
		return mEquipmentCap;
	}

	/**
	 * Mutator method that sets the equipmentCap of the class
	 * @param equipmentCap the equipmentCap to set
	 */
	public void setEquipmentCap(double equipmentCap) {
		mEquipmentCap = equipmentCap;
	}

	/**
	 * Accessor method that return the level
	 * @return the level
	 */
	public int getLevel() {
		return mLevel;
	}

	/**
	 * Mutator method that sets the level of the class
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		mLevel = level;
	}

	/**
	 * Accessor method that return the experience
	 * @return the experience
	 */
	public int getExperience() {
		return mExperience;
	}

	/**
	 * Mutator method that sets the experience of the class
	 * @param experience the experience to set
	 */
	public void setExperience(int experience) {
		mExperience = experience;
	}

	/**
	 * Accessor method that return the movespeed
	 * @return the movespeed
	 */
	public double getMovespeed() {
		return mMovespeed;
	}

	/**
	 * Mutator method that sets the movespeed of the class
	 * @param movespeed the movespeed to set
	 */
	public void setMovespeed(double movespeed) {
		mMovespeed = movespeed;
	}

	/**
	 * Accessor method that return the nextLevelExp
	 * @return the nextLevelExp
	 */
	public double getNextLevelExp() {
		return mNextLevelExp;
	}

	/**
	 * Mutator method that sets the nextLevelExp of the class
	 * @param nextLevelExp the nextLevelExp to set
	 */
	public void setNextLevelExp(double nextLevelExp) {
		mNextLevelExp = nextLevelExp;
	}

	/**
	 * Accessor method that return the maxHP
	 * @return the maxHP
	 */
	public double getMaxHP() {
		return mMaxHP;
	}

	/**
	 * Mutator method that sets the maxHP of the class
	 * @param maxHP the maxHP to set
	 */
	public void setMaxHP(double maxHP) {
		mMaxHP = maxHP;
	}

	/**
	 * Accessor method that return the currentHP
	 * @return the currentHP
	 */
	public double getCurrentHP() {
		return mCurrentHP;
	}

	/**
	 * Mutator method that sets the currentHP of the class
	 * @param currentHP the currentHP to set
	 */
	public void setCurrentHP(double currentHP) {
		mCurrentHP = currentHP;
	}
	
	public void wasHit(double damage) {
		double damageDealt = damage - mBaseDefense;
		if (mCurrentHP > 0) {
			if (mCurrentHP - damageDealt > 0)
				mCurrentHP -= damageDealt;
			else
				mCurrentHP = 0;
		}
	}
	
	public boolean isAlive() {
		return mCurrentHP > 0;
	}

	/* (non-Javadoc)
	 * @see edu.orangecoastcollege.cs272.model.Attackable#attack()
	 */
	@Override
	public double getAttackPower() {
		double actualAttackPower = mAttackPower;
		if (mSkill == Skill.FireBall)
			actualAttackPower *= 2;
		return actualAttackPower;
	}
	
//	public void update(Direction dir) {
//		switch (dir) {
//		case North:
//			break;
//		case South:
//			break;
//		case East:
//			break;
//		case West:
//			break;
//		case Northeast:
//			break;
//		case Northwest:
//			break;
//		case Southeast:
//			break;
//		case Southwest:
//			break;
//		}
//	}
	
}
