package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class UCMShip implements Entity {
	private Position position;
	private int health = 3;
	private int endurance = 3;
	private int damage = 1;
	private Game game;
	
	public UCMShip(Game game) {
		this.position = new Position(4, 7);
		this.game = game;
	}
	
	public String getSymbol() {
		return (health == 0) ?  Messages.UCMSHIP_DEAD_SYMBOL : Messages.UCMSHIP_SYMBOL;
	}
	
	public int getHealth() {
		return health;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public int getEndurance() {
		return endurance;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public boolean automaticMove() {
		return false;
	}
	
	public void reset() {
		position.setShipSpawn();
		health = 3;
	}
	
	public boolean reduceHealth(int damage) {
		health -= damage;
		if (health <= 0) {
			game.aliensWin();
			return true;
		}
		return false;
	}
}