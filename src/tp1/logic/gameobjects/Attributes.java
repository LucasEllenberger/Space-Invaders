package tp1.logic.gameobjects;

public enum  Attributes {
	Player(0, 1, 3),
	RegularAlien(5, 0, 1),
	DestroyerAlien(10, 1, 2),
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
