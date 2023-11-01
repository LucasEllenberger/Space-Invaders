package tp1.logic.gameobjects;

/**
 * Enum to deal with storing metrics relating to different type of game objects necessary for list functionality
 */

public enum  Attributes {
	Player(0, 1, 3),
	RegularAlien(5, 0, 2),
	DestroyerAlien(10, 1, 1),
	Ufo(25, 0, 1);
	
	public final int points;
	public final int endurance;
	public final int damage;
	
	Attributes(int points, int damage, int endurance) {
		this.points = points;
		this.damage = damage;
		this.endurance = endurance;
	}

}
