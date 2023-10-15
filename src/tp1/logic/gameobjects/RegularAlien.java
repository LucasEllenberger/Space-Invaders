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
	private static Move dir = Move.LEFT;
	private Position position;
	private int health = 2;

	//TODO fill your code
	
	public RegularAlien(Game game, Position position) {
		this.position = position;
		game.add(this);
	}
	
	public String getSymbol() {
		return String.format(Messages.GAME_OBJECT_STATUS, Messages.REGULAR_ALIEN_SYMBOL, health);
	}
	
	public Position getPosition() {
		return position;
	}
	/**
	 *  Implements the automatic movement of the regular alien	
	 */
	public boolean automaticMove() {
		//TODO fill your code
		Position.update(position, dir);
		return true;
	}
}