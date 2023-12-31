package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * Logic/schema describing Destroyer Aliens
 */

public class DestroyerAlien implements Entity{

	private static Move direction = Move.LEFT;
	private Bomb currBomb;
	private Game game;
	private Position position;
	private int health = 1;
	private boolean canBomb = true;
	
	public DestroyerAlien(Game game, Position position) {
		this.game = game;
		this.position = position;
		game.changeMetric("aliens", 1);
		game.add(this);
	}
	
	public String getSymbol() {
		return String.format(Messages.GAME_OBJECT_STATUS, Messages.DESTROYER_ALIEN_SYMBOL, health);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public static void changeDirection(Move move) {
		direction = move;
	}
	
	public void enableBomb() {
		canBomb = true;
	}
	
	public boolean automaticMove() {
		if (game.shouldMove()) {
			Position.update(position, direction);
			if (Position.onBorder(position) && !direction.equals(Move.DOWN)) {
				game.changeState("edge", true);
			}
		}
		dropBomb();
		return true;
	}
	
	public boolean reduceHealth(int damage)  {
		this.health -= damage;
		if (health <= 0) {
			game.changeMetric("points", Attributes.DestroyerAlien.points);
			game.changeMetric("aliens", -1);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Attempts to spawn a bomb from the reference ship
	 */
	
	public void dropBomb() {
		
		if (canBomb && game.getRandom().nextDouble() < game.getLevel().getShootFrequency()) {
			currBomb = new Bomb(this);
			game.addTemp(currBomb);
			canBomb = false;
		}
	}
}