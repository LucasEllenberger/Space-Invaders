package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class Bomb implements Entity {

	private Move dir = Move.DOWN;
	private Game game;
	private Position position;
	private DestroyerAlien ship;
	private int damage = 1;
	
	
	public Bomb(DestroyerAlien ship, Game game) {
		this.game = game;
		this.position = new Position(ship.getPosition().getCol(), ship.getPosition().getRow());
		this.ship = ship;
	}
	
	public String getSymbol() {
		return Messages.BOMB_SYMBOL;
	}

	public Position getPosition() {
		return position;
	}
	
	public int getDamage() {
		return damage;
	}
	
	
	/**
	 *  Method called when the bomb disappears from the board
	 */
	private void die() {
		ship.enableBomb();
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
				if (!(entity instanceof UCMLaser)) {
					game.remove(entity);
				}
			}
			die();
			return true;
		} 
		return false;
	}
}
