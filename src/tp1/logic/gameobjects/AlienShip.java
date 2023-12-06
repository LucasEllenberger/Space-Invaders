package tp1.logic.gameobjects;

import tp1.logic.Move;
import tp1.logic.Position;

public abstract class AlienShip extends EnemyShip {
	protected static Move direction = Move.LEFT;
	
	public static void changeDirection(Move move) {
		direction = move;
	}
	
	public boolean automaticMove() {
		if (game.shouldMove()) {
			Position.update(position, direction);
			if (Position.onBorder(position) && !direction.equals(Move.DOWN)) {
				game.changeState("edge", true);
			}
		}
		return true;
	}
	
	public boolean reduceHealth(int damage)  {
		this.health -= damage;
		if (health <= 0) {
			die();
			game.changeMetric("points", getPoints());
			game.changeMetric("aliens", -1);
			return true;
		}
		return false;
	}
	

}
