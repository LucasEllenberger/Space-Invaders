package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * Logic/schema describing Regular Aliens
 */
public class RegularAlien extends AlienShip {

	
	public RegularAlien(Game game, Position position) {
		this.game = game;
		this.position = position;
		health = Attributes.RegularAlien.endurance;
		game.changeMetric("aliens", 1);
		game.add(this);
	}
	
	public String getSymbol() {
		return String.format(Messages.GAME_OBJECT_STATUS, Messages.REGULAR_ALIEN_SYMBOL, health);
	}
	
	protected void die() {}
	
	protected int getPoints() {
		return Attributes.RegularAlien.points;
	}
}