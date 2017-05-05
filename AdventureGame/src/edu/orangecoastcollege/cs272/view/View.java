/**
 * 
 */
package edu.orangecoastcollege.cs272.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import edu.orangecoastcollege.cs272.controller.Controller;
import edu.orangecoastcollege.cs272.model.Enemy;
import edu.orangecoastcollege.cs272.model.Projectile;
import edu.orangecoastcollege.cs272.model.Skill;
import edu.orangecoastcollege.cs272.model.SpriteAnimation;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author mengv
 * @version 1.0
 */
public class View extends Application {

	public static final double WINDOW_WIDTH = 1000;
	public static final double WINDOW_HEIGHT = WINDOW_WIDTH * 9.0 / 16.0;
	private static final double MAX_HP_BAR_WIDTH = 400;
	private static final double HP_BAR_HEIGHT = 30;

	// Controller
	private Controller controller = Controller.getInstance();

	// Enemies and projectiles Lists
	private ArrayList<Enemy> allEnemyList = controller.getAllEnemyList();
	private ArrayList<Projectile> allProjectileList = controller.getAllProjectiles();

	// Scenes and Stages
	private Stage mainStage;
	private Scene mainScene;
	private Scene gameWinScreen = createGameWinScreen();
	private Scene gameLostScreen = createGameOverScreen();

	// Views
	private ImageView playerIV;
	private ArrayList<ImageView> enemyIVs = new ArrayList<>();
	private ArrayList<ImageView> projectilesIV = new ArrayList<>();
	private Rectangle HPBar;
	Pane container;

	// Sound and animation
	private MediaPlayer mainGameSound;
	private Animation playerAnimation;
	private ArrayList<Animation> enemysAnimation = new ArrayList<>();

	// Movements
	boolean up = false, down = false, left = false, right = false, running = false;
	double dx, dy;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		mainStage.setTitle("Adventure Games");
		preparePlayerIV();
		container = mainContainerSetup();
		mainScene = new Scene(container, WINDOW_WIDTH, WINDOW_HEIGHT, Color.AQUA);

		prepareEnemiesIVs();
		prepareSounds();
		prepareAnimations();
		handleKeyInputs();

		mainStage.setScene(mainScene);
		mainStage.show();

		new AnimationTimer() {

			@Override
			public void handle(long now) {
				final double cx = playerIV.getBoundsInLocal().getWidth() / 2;
				final double cy = playerIV.getBoundsInLocal().getHeight() / 2;
				if (isEnemyPlayerColliding()) {
					if (dx != 0)
						controller.movePlayer(-dx, 0, cx, cy);
					if (dy != 0)
						controller.movePlayer(0, -dy, cx, cy);
				}
				double moveSpeed = controller.getPlayer().getMovespeed();
				dx = 0;
				dy = 0;
				if (up)
					dy -= moveSpeed;
				if (down)
					dy += moveSpeed;
				if (left)
					dx -= moveSpeed;
				if (right)
					dx += moveSpeed;

				if (running) {
					dx *= 2;
					dy *= 2;
				}

				controller.movePlayer(dx, dy, cx, cy);
				redraw();
			}
		}.start();
	}

	/**
	 * Set up the main container, background image etc.
	 * @return the set up Pane.
	 */
	private Pane mainContainerSetup() {
		Pane newContainer = new Pane(playerIV);
		BackgroundImage BI = new BackgroundImage(new Image(new File("tile (2).png").toURI().toString()),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		newContainer.setBackground(new Background(BI));

		newContainer.getChildren().add(HPBar);
		return newContainer;
	}

	private void preparePlayerIV() {
		playerIV = new ImageView(controller.getPlayer().getImage());
		playerIV.setViewport(new Rectangle2D(0, 0, 64, 64));
		
		// HPBar setup
		HPBar = new Rectangle();
		HPBar.setWidth(MAX_HP_BAR_WIDTH);
		HPBar.setHeight(HP_BAR_HEIGHT);
		HPBar.relocate(20, 20);
		HPBar.setFill(Color.GREEN);
	}

	private void prepareEnemiesIVs() {
		// Create Enemy IVs
		for (int i = 0; i < allEnemyList.size(); i++) {
			ImageView enemyToAdd = new ImageView(allEnemyList.get(i).getImage());

			enemyIVs.add(enemyToAdd);
			container.getChildren().add(enemyToAdd);
		}
	}

	private void prepareSounds() {
		// Sound setup
		Media sound = new Media(new File("gameplaymusic.mp3").toURI().toString());
		mainGameSound = new MediaPlayer(sound);
		mainGameSound.setAutoPlay(true);
		mainGameSound.setVolume(0.5);
		mainGameSound.setOnEndOfMedia(new Runnable() {
			public void run() {
				mainGameSound.seek(Duration.ZERO);
			}
		});

	}

	private void prepareAnimations() {
		// Animation for enemies
		for (int i = 0; i < enemyIVs.size(); i++) {
			Animation animation = new SpriteAnimation(enemyIVs.get(i), Duration.millis(1000), 8, 8, 0, 0, 64, 64);
			enemysAnimation.add(animation);
			animation.setCycleCount(Animation.INDEFINITE);
			animation.play();
		}

		// Animation for player
		playerAnimation = new SpriteAnimation(playerIV, Duration.millis(1000), 8, 8, 0, 0, 64, 64);
		playerAnimation.setCycleCount(Animation.INDEFINITE);
	}

	private void handleKeyInputs() {

		// Mouse events
		playerIV.setOnMouseDragged(e -> {
			controller.moveTo(e.getSceneX(), e.getSceneY(), 32, 32);
		});
		playerIV.setOnMouseEntered(e -> {
			mainScene.setCursor(Cursor.OPEN_HAND);
		});
		playerIV.setOnMouseExited(e -> {
			mainScene.setCursor(Cursor.DEFAULT);
		});
		mainScene.setOnMouseClicked(e -> {

			controller.playerShot(e.getX(), e.getY());
			ImageView projectileToAdd = new ImageView(allProjectileList.get(allProjectileList.size() - 1).getImage());
			projectilesIV.add(projectileToAdd);
			container.getChildren().add(projectileToAdd);
		});

		
		// Keyboard inputs
		mainScene.setOnKeyPressed(e -> {

			switch (e.getCode()) {
			case W:
				up = true;
				break;
			case S:
				down = true;
				break;
			case A:
				left = true;
				break;
			case D:
				right = true;
				break;
			case SHIFT:
				running = true;
				break;
			case SPACE:
				controller.playerUsedSkill(Skill.FireBall);
				break;
			default:
				break;
			}
			
			playerAnimation.play();
			if (running)
				playerAnimation.setRate(2);
			if (!running)
				playerAnimation.setRate(1);
		});
		mainScene.setOnKeyReleased(e -> {

			switch (e.getCode()) {
			case W: 
				up = false;
				break;
			case S:
				down = false;
				break;
			case A:
				left = false;
				break;
			case D:
				right = false;
				break;
			case SHIFT:
				running = false;
				break;
			case SPACE:
				controller.playerUsedSkill(Skill.NoSkill);
				break;
			default:
				break;

			}

			if (!up && !down && !right && !left)
				playerAnimation.pause();
		});
	}
	
	/**
	 * Creates the gameover scene
	 * @return the gameover scene.
	 */

	// TODO: Redesign needed
	private Scene createGameOverScreen() {
		Group group = new Group();
		Scene gameOverScene = new Scene(group, WINDOW_WIDTH, WINDOW_HEIGHT, Color.RED);
		Label gameOverLb = new Label("GAME OVER!");
		gameOverLb.setTextFill(Color.BLACK);
		gameOverLb.setStyle("-fx-font: 40 arial;");
		group.getChildren().add(gameOverLb);
		gameOverLb.impl_processCSS(true);
		gameOverLb.relocate(WINDOW_WIDTH / 2 - gameOverLb.prefWidth(-1) / 2,
				WINDOW_HEIGHT / 2 - gameOverLb.prefHeight(-1) / 2);
		return gameOverScene;
	}
	
	/**
	 * Creates the win scene
	 * @return the win scene.
	 */
	
	// TODO: Redesign needed
	private Scene createGameWinScreen() {
		Group group = new Group();
		Scene gameOverScene = new Scene(group, WINDOW_WIDTH, WINDOW_HEIGHT, Color.GREEN);
		Label gameOverLb = new Label("WON!");
		gameOverLb.setTextFill(Color.BLACK);
		gameOverLb.setStyle("-fx-font: 40 arial;");
		group.getChildren().add(gameOverLb);
		gameOverLb.impl_processCSS(true);
		gameOverLb.relocate(WINDOW_WIDTH / 2 - gameOverLb.prefWidth(-1) / 2,
				WINDOW_HEIGHT / 2 - gameOverLb.prefHeight(-1) / 2);
		return gameOverScene;
	}

	// Function to syncronize the view with the model
	private void redraw() {
		updateEnemies();
		updatePlayer();

		updateBullets();
		checkingEnemyBulletCollision();

	}

	/**
	 * Loop and updates the player locations as well as the player ImageView.
	 * The game over screen is displayed if the player is dead.
	 */
	private void updatePlayer() {
		// Update the HPBar
		if (allEnemyList.size() > 0)
			HPBar.setWidth(MAX_HP_BAR_WIDTH * (allEnemyList.get(0).getCurrHP() / allEnemyList.get(0).getMaxHP()));

		// Check if the player is still alive and generate gameOverScreen if
		// player is dead
		if (!controller.getPlayer().isAlive()) {
			mainStage.setScene(gameLostScreen);
		}

		// Update player location
		playerIV.relocate(controller.getPlayer().getX() - playerIV.getBoundsInLocal().getWidth() / 2,
				controller.getPlayer().getY() - playerIV.getBoundsInLocal().getHeight() / 2);
		
		// Check the directional input of the player and rotate the image view accordingly.
		if (right) {
			if (right && up)
				playerIV.setRotate(45);
			else if (right && down)
				playerIV.setRotate(135);
			else
				playerIV.setRotate(90);
		} else if (left) {
			if (left && up)
				playerIV.setRotate(315);
			else if (left && down)
				playerIV.setRotate(225);
			else
				playerIV.setRotate(270);
		} else if (up)
			playerIV.setRotate(0);
		else
			playerIV.setRotate(180);
	}

	/**
	 * This function loops through all the enemies in the enemy list and update the location.
	 * It also update the animation of the enemies.
	 */
	private void updateEnemies() {
		// Update all enemys location
		for (Enemy enemy : allEnemyList) {
			enemy.update(controller.getPlayer());
		}
		for (int i = 0; i < enemyIVs.size(); i++) {
			ImageView currEnemyIV = enemyIVs.get(i);
			Enemy currEnemy = allEnemyList.get(i);
			if (((currEnemy.getCurrHP() / currEnemy.getMaxHP()) < 0.5) && !currEnemy.isWounded()) {
				currEnemy.setWounded(true);
				enemysAnimation.get(i).stop();
				enemysAnimation.remove(i);
				currEnemyIV.setImage(new Image(new File("troll-move-wounded.png").toURI().toString()));
				Animation woundedAnimation = new SpriteAnimation(currEnemyIV, Duration.millis(1000), 8, 8, 0, 0, 64,
						64);
				woundedAnimation.setCycleCount(Animation.INDEFINITE);
				woundedAnimation.play();
				enemysAnimation.add(i, woundedAnimation);
			}
			currEnemyIV.relocate(allEnemyList.get(i).getX() - currEnemyIV.getBoundsInLocal().getWidth() / 2,
					allEnemyList.get(i).getY() - currEnemyIV.getBoundsInLocal().getHeight() / 2);
		}
	}

	/**
	 * This function loops through all the projectile on screen and update the location.
	 */
	private void updateBullets() {
		// Loop through and update the projectiles location
		// Remove the projectile if it is not visible anymore
		for (int i = 0; i < allProjectileList.size(); i++) {
			Projectile currProjectile = allProjectileList.get(i);
			currProjectile.update(); 

			ImageView currProjectileIV = projectilesIV.get(i);

			if (!allProjectileList.get(i).isVisible()) {
				// Projectile not visible anymore, so remove it.
				container.getChildren().remove(projectilesIV.get(i));
				allProjectileList.remove(currProjectile);
				projectilesIV.remove(currProjectileIV);

			} else {
				currProjectileIV.relocate(currProjectile.getX() - currProjectileIV.getBoundsInLocal().getWidth() / 2,
						currProjectile.getY() - currProjectileIV.getBoundsInLocal().getHeight() / 2);
			}
		}
	}

	/**
	 * This checks if the player is colliding with the enemies on screen
	 * @return true if colliding, false otherwise
	 */
	private boolean isEnemyPlayerColliding() {
		boolean collsion = false;
		for (ImageView eIV : enemyIVs) {
			if (collisionDetected(playerIV, eIV)) {
				collsion = true;
				break;
			}
		}

		return collsion;
	}

	/**
	 * This check the collision of the bullet and the enemies
	 */
	private void checkingEnemyBulletCollision() {
		boolean found = false;
		if (allEnemyList.isEmpty()){
			mainStage.setScene(gameWinScreen);
			return;
		}
		// Check collision between bullet and enemy
		for (int bulletIndex = 0; bulletIndex < projectilesIV.size(); bulletIndex++) {
			ImageView currBulletIV = projectilesIV.get(bulletIndex);
			Projectile currBullet = allProjectileList.get(bulletIndex);
			for (int enemyIndex = 0; enemyIndex < enemyIVs.size(); enemyIndex++) {
				ImageView currEnemyIV = enemyIVs.get(enemyIndex);
				Enemy currEnemy = allEnemyList.get(enemyIndex);
				if (collisionDetected(currBulletIV, currEnemyIV) && currEnemy.isAlive()) {
					// Remove the bullet after it hits the enemy from the view
					// list, projectile list, and the screen
					container.getChildren().remove(currBulletIV);
					projectilesIV.remove(currBulletIV);
					allProjectileList.remove(currBullet);

					// Take away enemy HP
					currEnemy.gotAttacked(currBullet.getDamage());
					if (!currEnemy.isAlive()) {
						// Play death animation
						Animation deathAnimation = new SpriteAnimation(currEnemyIV, Duration.millis(1000), 32, 4, 0, 0,
								64, 64);
						enemysAnimation.get(enemyIndex).stop();
						currEnemyIV.setImage(new Image(new File("troll-death.png").toURI().toString()));

						deathAnimation.play();
						enemysAnimation.set(enemyIndex, deathAnimation);

						// Delay enemy deletion until the animation completes

						Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
							container.getChildren().remove(currEnemyIV);
							enemysAnimation.remove(deathAnimation);
							allEnemyList.remove(currEnemy);
							enemyIVs.remove(currEnemyIV);
						}));
						timeline.play();

					}

					found = true;
					break;
				}
				if (found)
					break;
			}
		}
	}

	/**
	 * Helper function to check collision between 2 image views
	 * @param obj1
	 * @param obj2
	 * @return true if they are colliding, false otherwise.
	 */
	private boolean collisionDetected(ImageView obj1, ImageView obj2) {
		return obj1.getBoundsInParent().intersects(obj2.getBoundsInParent());
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
