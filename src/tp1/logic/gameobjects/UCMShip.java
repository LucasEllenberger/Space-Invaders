package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * Logic/schema for UCMShip, the object representing the player.
 */
public class UCMShip implements Entity {
	
	private Game game;
	private Position position;
	private int health = 3;
	
	public UCMShip(Game game) {
		this.position = new Position(4, 7);
		this.game = game;
	}
	
	public String getSymbol() {
		return (health == 0) ?  Messages.UCMSHIP_DEAD_SYMBOL : Messages.UCMSHIP_SYMBOL;
	}
	
	public int getHealth() {
		return health;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public boolean automaticMove() {
		return false;
	}
	
	public boolean reduceHealth(int damage) {
		health -= damage;
		if (health <= 0) {
			game.aliensWin();
			return true;
		}
		return false;
	}
}