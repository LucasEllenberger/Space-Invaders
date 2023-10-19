package tp1.logic.gameobjects;
import tp1.logic.Position;
import tp1.view.Messages;

public class Space implements Entity{
	
	public String getSymbol() {
		return Messages.EMPTY;
	}
	
	public Position getPosition() {
		return null;
	}
	
	public boolean automaticMove() {
		return false;
	}
	
	public boolean reduceHealth(int damage) {
		return false;
	}
}
