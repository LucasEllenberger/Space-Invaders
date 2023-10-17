package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class UCMShip implements Entity {
	private Position position = new Position(4, 7);
	private int health = 3;
	private Game game;
	
	public UCMShip(Game game) {
		this.game = game;
	}
	public String getSymbol() {
		return Messages.UCMSHIP_SYMBOL;
	}
	
	public int getHealth() {
		return health;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public boolean automaticMove() {
		return false;
	}
	
	public void reduceHealth(int damage) {
		health -= damage;
		if (health <= 0) {
			game.aliensWin();
		}
	}

}