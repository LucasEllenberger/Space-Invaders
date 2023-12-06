package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.view.Messages;
/**
 * Class used to represent empty space on the board. In practice, there is only ever one Space object, and the latter 
 * three methods should never be called.
 */
public class Space extends Ship{
	
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
