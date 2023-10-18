package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * 
 * Class representing a regular alien
 *
 */
public class DestroyerAlien implements Entity{

	//TODO fill your code
	private static Move dir;
	private Game game;
	private Position position;
	private Bomb currBomb;
	private int health = 1;
	private int points = 10;
	private int damage = 1;
	private boolean canBomb = true;

	//TODO fill your code
	
	public DestroyerAlien(Game game, Position position) {
		this.game = game;
		this.position = position;
		dir = game.getDirection();
		game.add(this);
	}
	
	public String getSymbol() {
		return String.format(Messages.GAME_OBJECT_STATUS, Messages.DESTROYER_ALIEN_SYMBOL, health);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getDamage() {
		return damage;
	}
	
	/**
	 *  Implements the automatic movement of the regular alien	
	 */
	public boolean automaticMove() {
		//TODO fill your code
		if (game.shouldMove()) {
			Position.update(position, dir);
			if (Position.onBorder(position) && !dir.equals(Move.DOWN)) {
				game.changeState("edge", true);
			}
		}
		if (canBomb) {
			dropBomb();
		}
		return true;
	}
	
	public void enableBomb() {
		canBomb = true;
	}
	
	private boolean canDropBomb(){
		return game.getRandom().nextDouble() < game.getLevel().getShootFrequency();
	}
	
	public void dropBomb() {
		if (canDropBomb()) {
			currBomb = new Bomb(this, game);
			game.addTemp(currBomb);
			canBomb = false;
		}
	}
	
	public static void changeDirection(Move move) {
		dir = move;
	}
	
	public boolean onLastRow() {
		return (position.getRow() == (game.DIM_Y - 1));
	}
	
	public boolean reduceHealth(int damage)  {
		this.health -= damage;
		if (health <= 0) {
			game.addPoints(points);
			return true;
		}
		return false;
	}
}