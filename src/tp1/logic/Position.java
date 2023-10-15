package tp1.logic;

import tp1.control.Controller;
import tp1.view.Messages;

/**
 * 
 * Immutable class to encapsulate and manipulate positions in the game board
 * 
 */
public class Position {

	private int col;
	private int row;

	//TODO fill your code
	public Position(int col, int row) {
		this.col = col;
		this.row = row;
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
	
	public static boolean onBorder(Position position) {
		int col = position.getCol();
		if (col == 0 || col == Game.DIM_X) {
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
	
	public static void update(Position position, Move move) {
		int newRow = position.getRow() + move.getY();
		int newCol = position.getCol() + move.getX();
		position.set(newRow, newCol);
 	}
	

}
