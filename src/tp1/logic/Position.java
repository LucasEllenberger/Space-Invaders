package tp1.logic;

import tp1.view.Messages;

/**
 * Class to abstract away the true nature of the board. Contains many utility functions that give information about a position
 */

public class Position {

	private int col;
	private int row;
	
	public Position(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	
	/**
	 * Alternate constructor for when you desire to create a copy of a Position
	 * 
	 * @param position Position to be copied
	 */
	
	public Position(Position position) {
		
		this.col = position.getCol();
		this.row = position.getRow();
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	private void set(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public static boolean onLastRow(Position position) {
		return (position.getRow() == (Game.DIM_Y - 1));
	}
	
	public static boolean onBorder(Position position) {
		int col = position.getCol();
		if (col == 0 || col == Game.DIM_X - 1) {
			return true;
		}
		return false;
	}
	
	
	public static boolean outside(Position position) {
		int row = position.getRow();
		int col = position.getCol();
		if (row < 0 || col < 0 || row >= Game.DIM_Y || col >= Game.DIM_X) {
			return true;
		} 
		return false;
	}
	
	
	/**
	 * Attempts to update a Position, but errors and alerts if the update is invalid
	 * 
	 * @param position Position that you want to update
	 * @param move The direction in which you want to update the position
	 * @throws Movement Error if the update is not valid
	 * @return A boolean representing whether the updated position is valid. If the position is not valid, then the
	 * position is not updated.
	 */
	
	public static boolean updateSafe(Position position, Move move) {
		int newRow = position.getRow() + move.getY();
		int newCol = position.getCol() + move.getX();
		if (newRow < 0 || newCol < 0 || newRow >= Game.DIM_Y || newCol >= Game.DIM_X) {
			System.out.println(Messages.MOVEMENT_ERROR);
			return false;
		} else {
			position.set(newRow, newCol);
			return true;
		}
	}
	
	/**
	 * Updates a position without regard for validity
	 * 
	 * @param position Position that you want to update
	 * @param move The direction in which you want to update
	 */
	
	public static void update(Position position, Move move) {
		int newRow = position.getRow() + move.getY();
		int newCol = position.getCol() + move.getX();
		position.set(newRow, newCol);
 	}
	

}
