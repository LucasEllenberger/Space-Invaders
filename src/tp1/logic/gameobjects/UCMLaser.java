package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * 
 * Class that represents the laser fired by {@link UCMShip}
 *
 */
public class UCMLaser implements Entity{
	
	//TODO fill your code
	private Move dir = Move.UP;
	private Game game;
	private Position position;
	
	
	public UCMLaser(UCMShip ship, Game game) {
		this.game = game;
		this.position = new Position(ship.getPosition().getCol(), ship.getPosition().getRow());
		game.disableLaser();
	}
	
	public String getSymbol() {
		return Messages.LASER_SYMBOL;
	}

	public Position getPosition() {
		return position;
	}
	
	/**
	 *  Method called when the laser disappears from the board
	 */
	public void die() {
		game.remove(this);
		game.enableLaser();
	}

	/**
	 *  Implements the automatic movement of the laser	
	 */
	public boolean automaticMove () {
		Position.update(position, dir);
		if(Position.outside(position)) {
			die();
			return false;
		}
		return true;
	}
}
