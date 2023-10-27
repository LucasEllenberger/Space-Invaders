package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 *  Logic/schema for UCMLasers, the projectiles produced by UCMShip
 *
 */
public class UCMLaser implements Entity{

	private Game game;
	private Position position;
	
	public UCMLaser(UCMShip ship, Game game) {
		this.game = game;
		this.position = new Position(ship.getPosition());
		game.changeState("laser", true);
	}
	
	public String getSymbol() {
		return Messages.LASER_SYMBOL;
	}

	public Position getPosition() {
		return position;
	}
	
	public boolean reduceHealth(int damage) {
		die();
		return true;
	}
	
	private void die() {
		game.remove(this);
		game.changeState("laser", false);
	}

	public boolean automaticMove () {
		Position.update(position, Move.UP);
		if(Position.outside(position)) {
			die();
			return false;
		}
		return true;
	}

	/**
	 * Attacks an entity using the coded damage value
	 * 
	 * @param entity Entity to be attacked
	 * @return A boolean describing whether the entity was something that can be attacked (not Space)
	 */
	
	public boolean attack(Entity entity) {
		
		if (!(entity instanceof Space)) {
			if (entity.reduceHealth(1)) {
				game.remove(entity);
			}
			die();
			return true;
		} 
		return false;
	}
}
