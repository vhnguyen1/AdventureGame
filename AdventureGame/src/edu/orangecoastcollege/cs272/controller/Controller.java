/**
 * 
 */
package edu.orangecoastcollege.cs272.controller;

import java.io.File;
import java.util.ArrayList;

import edu.orangecoastcollege.cs272.model.Enemy;
import edu.orangecoastcollege.cs272.model.Player;
import edu.orangecoastcollege.cs272.model.Projectile;
import edu.orangecoastcollege.cs272.model.Skill;
import javafx.scene.image.Image;

/**
 * 
 * @author mengv
 * @version 1.0
 */
public class Controller {
	public static final double WINDOW_WIDTH = 1000;
	public static final double WINDOW_HEIGHT = WINDOW_WIDTH * 9.0 / 16.0;
	public static final int ENEMY_COUNT = 1;

	private static Controller theOne;
	private Player mPlayer;
	private ArrayList<Enemy> mEnemysList;
	private ArrayList<Projectile> mAllProjectiles;

	private Controller() {}

	public static Controller getInstance() {
		if (theOne == null) {
			theOne = new Controller();
			theOne.mPlayer = new Player(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, "Vichet", new Image(new File("player-move.png").toURI().toString()));
			theOne.mEnemysList = new ArrayList<Enemy>();
			theOne.mAllProjectiles = new ArrayList<>();
			for (int i = 0; i < ENEMY_COUNT; i++) {
				Enemy e = new Enemy((Math.random() * (WINDOW_WIDTH - 32)), 100, "Trolls", new Image(new File("troll-move.png").toURI().toString())); 
				theOne.mEnemysList.add(e);
			}
		}
		return theOne;
	}
	
	public Player getPlayer() {
		return theOne.mPlayer;
	}
	
	public void movePlayer(double dx, double dy, double cx, double cy) {
		if (dx == 0 && dy == 0)
			return;
		double x = dx + theOne.mPlayer.getX();
		double y = dy + theOne.mPlayer.getY();

		moveTo(x, y, cx, cy);
	}
	
	public void moveTo(double x, double y, double cx, double cy) {
		if (x - cx >= 0 && x + cx <= WINDOW_WIDTH && y - cy >= 0 && y + cy <= WINDOW_HEIGHT) {
			theOne.mPlayer.setPos(x, y);
		}
	}
	
	public void playerGotHit() {
		theOne.mPlayer.wasHit(10);
	}
	
	public ArrayList<Enemy> getAllEnemyList() {
		return theOne.mEnemysList;
	}
	public ArrayList<Projectile> getAllProjectiles() {
		return mAllProjectiles;
	}
	public void playerShot(double mouseX, double mouseY) {
		String projectileFileStr = null;
		switch(theOne.mPlayer.getSkill()) {
		case NoSkill:
			projectileFileStr = "projectile.png";
			break;
		case FireBall:
			projectileFileStr = "fireball.png";
			break;
		}
		Projectile shot = new Projectile(theOne.mPlayer.getX(), theOne.mPlayer.getY(), mouseX, mouseY, theOne.mPlayer.getAttackPower(), "Player Shot", new Image(new File(projectileFileStr).toURI().toString()));
		theOne.mAllProjectiles.add(shot);
	}
	
	public void playerUsedSkill(Skill skill) {
		theOne.mPlayer.setSkill(skill);
	}
}
