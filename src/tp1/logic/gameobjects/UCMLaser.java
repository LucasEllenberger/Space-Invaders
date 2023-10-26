package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class UCMLaser implements Entity{

	private Move dir = Move.UP;
	private Game game;
	private Position position;
	private int damage = 1;
	
	
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
	
	public int getDamage() {
		return damage;
	}
	
	private void die() {
		game.remove(this);
		game.changeState("laser", false);
	}

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
				game.remove(entity);
			}
			die();
			return true;
		} 
		return false;
	}
}
