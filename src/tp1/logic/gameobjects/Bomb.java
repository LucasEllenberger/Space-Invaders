package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;



/**
 * Class implementing bombs, the projectiles produced by DestroyerAliens
 */

public class Bomb extends EnemyWeapon {

	private DestroyerAlien ship;
	
	public Bomb(DestroyerAlien ship, Game game) {
		this.ship = ship;
		this.game = game;
		position = new Position(ship.getPosition());
	}
	
	public String getSymbol() {
		return Messages.BOMB_SYMBOL;
	}
	
	public int getDamage() {
		return Attributes.DestroyerAlien.damage;
	}

	public boolean reduceHealth(int damage) {
		ship.enableBomb();
		game.remove(this);
		return true;
	}
	
	public boolean automaticMove() {
		Position.update(position, Move.DOWN);
		if(Position.outside(position)) {
			ship.enableBomb();
			return false;
		}
		return true;
	}
}
