package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;

/**
 * 
 * Class representing a regular alien
 *
 */
public class RegularAlien {

	//TODO fill your code
	private int cyclesToMove;
	private int speed;
	private Move dir;
	private Position position;	
	private AlienManager alienManager;

	//TODO fill your code
	
	public RegularAlien(AlienManager alienManager, Position position, int speed) {
		this.alienManager = alienManager;
		this.position = position;
		this.speed = speed;
		this.cyclesToMove = speed;
		this.dir = Move.LEFT;
	}

	/**
	 *  Implements the automatic movement of the regular alien	
	 */
	public void automaticMove() {
		//TODO fill your code
	}

	private void descent() {
		//TODO fill your code
		
	}

	private void performMovement(Move dir) {
		//TODO fill your code
		
	}

	private boolean isInBorder() {
		//TODO fill your code
		// WE ASSUME THAT THE BORDER IS 8
		return Position.onBorder(position);
	}

	public boolean receiveAttack(UCMLaser laser) {
		//TODO fill your code
		return false;
	}
	

}