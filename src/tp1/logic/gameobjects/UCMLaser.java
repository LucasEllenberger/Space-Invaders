package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * 
 * Class that represents the laser fired by {@link UCMShip}
 *
 */
public class UCMLaser implements Entity{
	
	//TODO fill your code
	private Move dir = Move.UP;
	private Game game;
	private Position position;
	
	
	public UCMLaser(UCMShip ship, Game game) {
		this.game = game;
		this.position = new Position(ship.getPosition().getCol(), ship.getPosition().getRow());
		game.disableLaser();
	}
	
	public String getSymbol() {
		return Messages.LASER_SYMBOL;
	}

	public Position getPosition() {
		return position;
	}
	
	/**
	 *  Method called when the laser disappears from the board
	 */
	public void onDelete() {
		game.enableLaser();
	}

	/**
	 *  Implements the automatic movement of the laser	
	 */
	public boolean automaticMove () {
		performMovement(dir);
		if(isOut()) {
			die();
			return false;
		}
		return true;
	}

	
	// PERFORM ATTACK METHODS
	
	
	
	
	private void die() {
		//TODO fill your code
		game.remove(this);
		onDelete();
		
	}

	private boolean isOut() {
		//TODO fill your code
		return Position.outside(position);
	}

	private void performMovement(Move dir) {
		//TODO fill your code
		Position.update(position, dir);
	}

	/**
	 * Method that implements the attack by the laser to a regular alien.
	 * It checks whether both objects are alive and in the same position.
	 * If so call the "actual" attack method {@link weaponAttack}.
	 * @param other the regular alien possibly under attack
	 * @return <code>true</code> if the alien has been attacked by the laser.
	 */
	public boolean performAttack(RegularAlien other) {
		//TODO fill your code
		return false;
	}

	/**
	 * Method that implements the attack by the laser to a destroyer alien.
	 * It checks whether both objects are alive and in the same position.
	 * If so call the "actual" attack method {@link weaponAttack}.
	 * @param other the destroyer alien possibly under attack
	 * @return <code>true</code> if the alien has been attacked by the laser.
	 */

	/*
	public boolean performAttack(DestroyerAlien other) {
		//TODO fill your code
		return false;
	}
	*/
	
	//TODO fill your code


	//ACTUAL ATTACK METHODS
	

	/**
	 * 
	 * @param other regular alien under attack by the laser
	 * @return always returns <code>true</code>
	 */
	private boolean weaponAttack(RegularAlien other) {
		return other.receiveAttack(this);	
	}

	//TODO fill your code


	// RECEIVE ATTACK METHODS
	
	/**
	 * Method to implement the effect of bomb attack on a laser
	 * @param weapon the received bomb
	 * @return always returns <code>true</code>
	 */
	/*
	public boolean receiveAttack(Bomb weapon) {
		receiveDamage(weapon.getDamage());
		return true;
	}
	*/

}
