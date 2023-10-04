package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.view.Messages;

public class UCMShip implements Entity {
	private Position position = new Position(4, 7);
	
	public String getSymbol() {
		return Messages.UCMSHIP_SYMBOL;
	}
	
	public Position getPosition() {
		return position;
	}
	

}