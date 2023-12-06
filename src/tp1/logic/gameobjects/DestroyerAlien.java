package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * Logic/schema describing Destroyer Aliens
 */

public class DestroyerAlien extends AlienShip{

	private Bomb currBomb;
	private boolean canBomb = true;
	
	public DestroyerAlien(Game game, Position position) {
		this.game = game;
		this.position = position;
		health = Attributes.DestroyerAlien.endurance;
		game.changeMetric("aliens", 1);
		game.add(this);
	}
	
	public String getSymbol() {
		return String.format(Messages.GAME_OBJECT_STATUS, Messages.DESTROYER_ALIEN_SYMBOL, health);
	}
	
	public void enableBomb() {
		canBomb = true;
	}
	
	@Override
	public boolean automaticMove() {
		if (game.shouldMove()) {
			Position.update(position, direction);
			if (Position.onBorder(position) && !direction.equals(Move.DOWN)) {
				game.changeState("edge", true);
			}
		}
		dropBomb();
		return true;
	}
	
	protected int getPoints() {
		return Attributes.DestroyerAlien.points;
	}
	
	protected void die() {}
	/**
	 * Attempts to spawn a bomb from the reference ship
	 */
	
	public void dropBomb() {
		
		if (canBomb && game.getRandom().nextDouble() < game.getLevel().getShootFrequency()) {
			currBomb = new Bomb(this, game);
			game.addTemp(currBomb);
			canBomb = false;
		}
	}
}