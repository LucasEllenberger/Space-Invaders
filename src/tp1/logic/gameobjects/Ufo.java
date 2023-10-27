package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;


/**
 * Logic/schema for UFO functionality. The UFO is only instantiated once. We provide functionality by 
 * resetting positions and choosing whether to display it.
 */

public class Ufo implements Entity{

	private Game game;
	private Position position;
	private int health = 1;

	public Ufo(Game game) {
		this.game = game;
	}

	public String getSymbol() {
		return String.format(Messages.GAME_OBJECT_STATUS, Messages.UFO_SYMBOL, health);
	}

	public Position getPosition() {
		return position;
	}
	
	private void die() {
		game.changeState("ufo", false);
		game.remove(this);
	}
	
	public boolean automaticMove() {
		Position.update(position, Move.LEFT);
		if(Position.outside(position)) {
			game.changeState("ufo", false);
			return false;
		}
		return true;
	}
	
	/**
	 * Attempt to spawn a UFO
	 * 
	 * @return A boolean representing whether a UFO was created 
	 */
	
	public boolean computerAction() {
		
		if(!game.getState("ufo") && game.getRandom().nextDouble() < game.getLevel().getUfoFrequency()) {
			health = 1;
			position = new Position(Game.DIM_X - 1, 0);
			game.add(this);
			game.changeState("ufo", true);	
			return true;
		}
		return false;
	}

	public boolean reduceHealth(int damage) {
		health -= damage;
		if (health <= 0) {
			position = null;
			game.addPoints(Attributes.Ufo.points);
			game.changeState("shockwave", true);
			die();
			return true;
		}
		return false;
	}
	
}
