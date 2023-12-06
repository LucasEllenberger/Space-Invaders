package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;


/**
 * Logic/schema for UFO functionality. The UFO is only instantiated once. We provide functionality by 
 * resetting positions and choosing whether to display it.
 */

public class Ufo extends EnemyShip{


	public Ufo(Game game) {
		this.game = game;
		health = Attributes.Ufo.endurance;
	}

	public String getSymbol() {
		return String.format(Messages.GAME_OBJECT_STATUS, Messages.UFO_SYMBOL, health);
	}

	protected void die() {
		game.changeState("ufo", false);
		game.remove(this);
		position = null;
		game.changeMetric("points", getPoints());
		game.changeState("shockwave", true);
	}
	
	public boolean automaticMove() {
		Position.update(position, Move.LEFT);
		if(Position.outside(position)) {
			game.changeState("ufo", false);
			return false;
		}
		return true;
	}
	
	protected int getPoints() {
		return Attributes.Ufo.points;
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
	
}
