package tp1.logic.gameobjects;

import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;



/**
 * Class implementing bombs, the projectiles produced by DestroyerAliens
 */

public class Bomb implements Entity {

	private Position position;
	private DestroyerAlien ship;
	
	public Bomb(DestroyerAlien ship) {
		this.position = new Position(ship.getPosition());
		this.ship = ship;
	}
	
	public String getSymbol() {
		return Messages.BOMB_SYMBOL;
	}

	public Position getPosition() {
		return position;
	}
	
	private void die() {
		ship.enableBomb();
	}
	
	public boolean reduceHealth(int damage) {
		die();
		return true;
	}
	
	public boolean automaticMove() {
		Position.update(position, Move.DOWN);
		if(Position.outside(position)) {
			die();
			return false;
		}
		return true;
	}
}
