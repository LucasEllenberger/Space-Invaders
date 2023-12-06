package tp1.logic.gameobjects;

public class Shockwave extends UCMWeapon {
	
	public Shockwave() {}
	
	public int getDamage() {
		return 1;
	}

	public String getSymbol() {
		return null;}

	public boolean automaticMove() {
		return false;
	}

	public boolean reduceHealth(int damage) {
		return false;
	}
}
