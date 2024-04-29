package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Represents the main game class for "World's Hardest Game".
 * Extends the Game class and includes the game logic, player, enemies, and the checkered board.
 */
public class WorldsHardestGame extends Game {
	// Inner class: Timer
	private static class Timer {
		protected long startTime;

		/**
		 * Constructs a Timer and initializes the start time.
		 */
		public Timer() {
			this.startTime = System.currentTimeMillis();
		}

		/**
		 * Gets the elapsed time since the timer started.
		 *
		 * @return The elapsed time in milliseconds.
		 */
		public long getElapsedTime() {
			return System.currentTimeMillis() - startTime;
		}
	}

	// Inner class: Score
	private static class Score {
		private int score;

		/**
		 * Constructs a Score with an initial score of 0.
		 */
		public Score() {
			this.score = 0;
		}

		/**
		 * Decreases the score by the specified points.
		 *
		 * @param points The points to decrease the score by.
		 */
		public void decreaseScore(int points) {
			score -= points;
		}

		/**
		 * Gets the current score.
		 *
		 * @return The current score.
		 */
		public int getScore() {
			return score;
		}
	}

	private Timer gameTimer;
	protected double elapsedTime;
	private Score playerScore;
	protected static int width = 800;
	protected static int height = 600;
	protected static int checkeredSize = 400;  // Adjust the size as needed
	protected static int checkeredX = (width - checkeredSize) / 2;
	protected static int checkeredY = (height - checkeredSize) / 2;
	protected Player player;  // Instance of the Player class
	protected ArrayList<Enemy> enemies = new ArrayList<>();
	protected Enemy enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, enemy7, enemy8, enemy9;  // Instances of the Enemy class
	protected SpinningRectangle spinningRectangle1;
	protected SpinningRectangle spinningRectangle2;
	protected boolean gameCompleted = false;

	/**
	 * Constructs the WorldsHardestGame, initializes the player, enemies, and sets up the game.
	 */
	public WorldsHardestGame() {
		super("WorldsHardestGame!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();

		// Initialize the player here
		player = new Player(new Point(215, 290), 0);
		enemy1 = new Enemy(280, 140, 10, 4.0);
		enemy2 = new Enemy(310, 180, 10, 4.0);
		enemy3 = new Enemy(340, 220, 10, 4.0);
		enemy4 = new Enemy(370, 300, 10, 6.0);
		enemy5 = new Enemy(400, 300, 10, 6.0);
		enemy6 = new Enemy(430, 300, 10, 6.0);
		enemy7 = new Enemy(460, 380, 10, 4.0);
		enemy8 = new Enemy(490, 420, 10, 4.0);
		enemy9 = new Enemy(520, 460, 10, 4.0);

		spinningRectangle1 = new SpinningRectangle(new Point(275, 150), 2.0);
		spinningRectangle2 = new SpinningRectangle(new Point(475, 440), 2.0);

		enemies.add(enemy1);
		enemies.add(enemy2);
		enemies.add(enemy3);
		enemies.add(enemy4);
		enemies.add(enemy5);
		enemies.add(enemy6);
		enemies.add(enemy7);
		enemies.add(enemy8);
		enemies.add(enemy9);

		// Register the player as a KeyListener
		this.addKeyListener(keyListener);

		this.gameTimer = new Timer();

		this.playerScore = new Score();
	}

	/**
	 * Checks if the player is in the right green area.
	 *
	 * @param player The player object.
	 * @return True if the player is in the right green area, false otherwise.
	 */
	private boolean isInRightGreenArea(Player player) {
		int eighthWidth = checkeredSize / 8;
		int rightGreenX = checkeredX + 7 * eighthWidth;
		int rightGreenY = checkeredY;
		int playerX = (int) player.position.getX();
		int playerY = (int) player.position.getY();

		return playerX >= rightGreenX && playerX + 20 <= rightGreenX + eighthWidth &&
				playerY >= rightGreenY && playerY <= rightGreenY + checkeredSize;
	}

	/**
	 * Draws the game elements, including the background, checkered pattern, enemies, spinning rectangles,
	 * and the player. Also handles player movement and collision detection.
	 *
	 * @param brush The Graphics object used for painting.
	 */
	public void paint(Graphics brush) {
		if (!gameCompleted) {
			// Draw the light blue background
			brush.setColor(new Color(173, 216, 230));  // Light blue color
			brush.fillRect(0, 0, width, height);

			// Draw the black border around the checkered area
			brush.setColor(Color.black);
			brush.fillRect(checkeredX - 4, checkeredY - 4, checkeredSize + 8, checkeredSize + 8);

			// Draw the checkered pattern with larger checkers
			int cellSize = 40;  // Adjust the size as needed

			for (int col = 0; col < checkeredSize / cellSize; col++) {
				for (int row = 0; row < checkeredSize / cellSize; row++) {
					// Alternate between white and grey cells
					if ((col + row) % 2 == 0) {
						brush.setColor(Color.white);
					} else {
						brush.setColor(Color.lightGray);
					}

					int x = checkeredX + col * cellSize;
					int y = checkeredY + row * cellSize;

					brush.fillRect(x, y, cellSize, cellSize);
				}
			}

			// Draw the left translucent green area on top of the checkered pattern
			brush.setColor(new Color(0, 255, 0, 100));  // Translucent green color
			int checkpointWidth = checkeredSize / 8;
			brush.fillRect(checkeredX, checkeredY, checkpointWidth, checkeredSize);

			// Draw the right translucent green area on top of the checkered pattern
			brush.fillRect(checkeredX + 7 * checkpointWidth, checkeredY, checkpointWidth, checkeredSize);

			if (player != null) {
				if (isInRightGreenArea(player) && !gameCompleted) {
					// Print the completion message
					System.out.println("Congratulations! You completed the game.");

					// Set the gameCompleted flag to true to prevent further movement
					gameCompleted = true;
				}
			}

			if (enemies != null) {
				enemies.forEach(enemy -> {
					enemy.move();

					if (player != null && enemy.intersects(player)) {
						// Respawn player in the left green area
						playerScore.decreaseScore(1);
						player.respawn();
					}

					enemy.paint(brush);
				});
			}

			if (spinningRectangle1 != null && spinningRectangle2 != null) {
				spinningRectangle1.move();

				if (player != null && spinningRectangle1.intersects(player)) {
					// Respawn player in the left green area
					playerScore.decreaseScore(1);
					player.respawn();
				}

				spinningRectangle1.paint(brush);
				spinningRectangle2.move();

				if (player != null && spinningRectangle2.intersects(player)) {
					// Respawn player in the left green area
					playerScore.decreaseScore(1);
					player.respawn();
				}

				spinningRectangle2.paint(brush);
			}

			// Draw the player
			brush.setColor(Color.red);
			if (player != null && !gameCompleted) {
				// Check for collisions before moving
				player.move(checkeredX, checkeredY, checkeredSize);
				player.paint(brush);

				// Display square's position for debugging
				brush.setColor(Color.black);
				brush.drawString("Square Position: (" + player.position.getX() + ", " + player.position.getY() + ")", 10, 20);
				brush.drawString("Elapsed Time: " + elapsedTime + " seconds", 10, 40);
				if (playerScore != null) {
					brush.drawString("Player's Score: " + playerScore.getScore(), 675, 20);
				}
			}

		} else {
			// Draw the "Game Over" screen
			brush.setColor(Color.black);
			brush.setFont(new Font("Arial", Font.BOLD, 72));
			brush.drawString("Game Won!!", width / 2 - 200, height / 2);
			brush.setFont(new Font("Arial", Font.BOLD, 24));
			brush.drawString("Player's Final Score: " + playerScore.getScore(), width / 2 - 200, height / 2 + 100);
		}
	}

	/**
	 * Handles key press events, allowing the player to control movement and spinning.
	 */
	protected KeyListener keyListener = new KeyListener() {
		// Inside the handleKeyPress method
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
				case KeyEvent.VK_UP:
					player.setMovingUp(true);  // Start moving forward
					break;
				case KeyEvent.VK_DOWN:
					player.setMovingDown(true);  // Start moving forward
					break;
				case KeyEvent.VK_LEFT:
					player.setMovingLeft(true);  // Start moving forward
					break;
				case KeyEvent.VK_RIGHT:
					player.setMovingRight(true);  // Start moving forward
					break;
				case KeyEvent.VK_E:
					player.setSpinning(true);
					break;
				// Handle other keys if needed
			}
		}

		// Inside the handleKeyRelease method
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
				case KeyEvent.VK_UP:
					player.setMovingUp(false);  // Start moving forward
					break;
				case KeyEvent.VK_DOWN:
					player.setMovingDown(false);  // Start moving forward
					break;
				case KeyEvent.VK_LEFT:
					player.setMovingLeft(false);  // Start moving forward
					break;
				case KeyEvent.VK_RIGHT:
					player.setMovingRight(false);  // Start moving forward
					break;
				case KeyEvent.VK_E:
					player.setSpinning(false);
					break;
				// Handle other keys if needed
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// Leave this method empty
		}
	};

	/**
	 * Processes key events, calling the corresponding KeyListener methods based on the event type.
	 *
	 * @param e The KeyEvent to be processed.
	 */
	public void processKeyEvent(KeyEvent e) {
		// Example: Call the KeyListener methods based on the event type
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			keyListener.keyPressed(e);
		} else if (e.getID() == KeyEvent.KEY_RELEASED) {
			keyListener.keyReleased(e);
		} else if (e.getID() == KeyEvent.KEY_TYPED) {
			keyListener.keyTyped(e);
		}
	}

	/**
	 * Runs the main game loop, updating the game logic, repainting the screen, and controlling the frame rate.
	 */
	private void runGameLoop() {
		while (true) {
			// Update game logic
			updateGame();

			// Repaint the screen
			repaint();

			// Sleep to control the frame rate (adjust as needed)
			try {
				Thread.sleep(16); // Aim for approximately 60 frames per second
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Updates the game logic, for example, printing the elapsed time using the Timer.
	 */
	private void updateGame() {
		elapsedTime = gameTimer.getElapsedTime();
		elapsedTime /= 1000.0; // Convert milliseconds to seconds
	}

	/**
	 * The main method to start the game.
	 *
	 * @param args Command-line arguments (unused).
	 */
	public static void main(String[] args) {
		WorldsHardestGame game = new WorldsHardestGame();
		game.runGameLoop();
	}
}
