/**
 * 
 */
package edu.orangecoastcollege.cs272.model;

import javafx.scene.Scene;

/**
 * 
 * @author mengv
 * @version 1.0
 */
public interface Moveable {
	public abstract void moveTo(double dx, double dy, Scene parent);
	public abstract void moveBy(double dx, double dy, Scene parent);
}
