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
	private int damage = 1;
	
	
	public UCMLaser(UCMShip ship, Game game) {
		this.game = game;
		this.position = new Position(ship.getPosition().getCol(), ship.getPosition().getRow());
		game.changeState("laser", true);
	}
	
	public String getSymbol() {
		return Messages.LASER_SYMBOL;
	}

	public Position getPosition() {
		return position;
	}
	
	public int getDamage() {
		return damage;
	}
	
	/**
	 *  Method called when the laser disappears from the board
	 */
	private void die() {
		game.remove(this);
		game.changeState("laser", false);
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

	public boolean reduceHealth(int damage) {
		die();
		return true;
	}
	
	public boolean attack(Entity entity) {
		if (!(entity instanceof Space)) {
			if (entity.reduceHealth(damage)) {
				if (!(entity instanceof Bomb)) {
					game.remove(entity);
				}
			}
			die();
			return true;
		} 
		return false;
	}
}
