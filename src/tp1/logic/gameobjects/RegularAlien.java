package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * Logic/schema describing Regular Aliens
 */
public class RegularAlien implements Entity{
	
	private static Move direction = Move.LEFT;
	private Game game;
	private Position position;
	private int health = 2;
	private int points = 5;
	
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
	
	public static void changeDirection(Move move) {
		direction = move;
	}

	public boolean automaticMove() {
		if (game.shouldMove()) {
			Position.update(position, direction);
			if (Position.onBorder(position) && !direction.equals(Move.DOWN)) {
				game.changeState("edge", true);
			}
		}
		return true;
	}
	
	public boolean reduceHealth(int damage)  {
		this.health -= damage;
		if (health <= 0) {
			game.addPoints(points);
			return true;
		}
		return false;
	}
}