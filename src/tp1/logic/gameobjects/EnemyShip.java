package tp1.logic.gameobjects;

import tp1.logic.Move;

public abstract class EnemyShip extends Ship {
	abstract int getPoints();
	
	abstract void die();
	
	public boolean reduceHealth(int damage) {
		health -= damage;
		if (health <= 0) {
			die();
			return true;
		}
		return false;
	}

}
