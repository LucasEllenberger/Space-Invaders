package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * 
 * Class representing a regular alien
 *
 */
public class RegularAlien implements Entity{

	//TODO fill your code
	private static Move dir;
	private Game game;
	private Position position;
	private int health = 2;
	private int points = 5;

	//TODO fill your code
	
	public RegularAlien(Game game, Position position) {
		this.game = game;
		this.position = position;
		game.add(this);
	}
	
	public String getSymbol() {
		return String.format(Messages.GAME_OBJECT_STATUS, Messages.REGULAR_ALIEN_SYMBOL, health);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public int getPoints() {
		return points;
	}
	
	/**
	 *  Implements the automatic movement of the regular alien	
	 */
	public boolean automaticMove() {
		//TODO fill your code
		if (game.shouldMove()) {
			Position.update(position, dir);
			if (Position.onBorder(position) && !dir.equals(Move.DOWN)) {
				game.changeState("edge", true);
			}
		}
		return true;
	}
	
	public static void changeDirection(Move move) {
		dir = move;
	}
	
	public void reduceHealth(int damage)  {
		this.health -= damage;
		if (health <= 0) {
			game.addPoints(points);
			game.remove(this);
		}
	}
}