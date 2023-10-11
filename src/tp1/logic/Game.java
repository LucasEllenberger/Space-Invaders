package tp1.logic;

import java.util.Random;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import tp1.control.Controller;
import tp1.logic.gameobjects.UCMShip;
import tp1.logic.gameobjects.UCMLaser;
import tp1.logic.gameobjects.Entity;
import tp1.view.Messages;

// TODO implementarlo
public class Game {

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;
	private Entity[][] board = new Entity[DIM_Y][DIM_X];
	private Entity[][] other_board = new Entity[DIM_Y][DIM_X];
	private Set<Entity> entities = new HashSet<Entity>();
	private UCMShip player = new UCMShip();
	private UCMLaser curr_laser = null;
	private AlienManager aliens;
	private int cycles = 0;
	private int points = 0;
	private boolean laser = false;
	private Level level;
	private Random random;
	private boolean updateBoard = true;
	private boolean running = true;

	//TODO fill your code

	public Game(Level level, long seed) {
		//TODO fill your code}
        Position position = player.getPosition();
        board[position.getRow()][position.getCol()] = player;
        entities.add(player);
        this.level = level;
        this.random = new Random(seed);
        aliens = new AlienManager(this, level);
        
	}

	public String stateToString() {
		//TODO fill your code
		return "";
	}

	public int getCycle() {
		//TODO fill your code
		return cycles;
	}

	public int getRemainingAliens() {
		//TODO fill your code
		// ask Alien manager for remaining aliens
		return aliens.getRemainingAliens();
	}

	public String positionToString(int col, int row) {
		//TODO fill your code
		if (board[row][col] != null) {
			return board[row][col].getSymbol(); 
		}
		if (other_board[row][col] != null) { 
			return other_board[row][col].getSymbol();
		}
		return Messages.EMPTY;
	}

	public boolean playerWin() {
		//TODO fill your code
		// need to know if all the aliens are dead
//		if (game.getRemainingAliens() == 0) {
//		this.running = false;
//			return true;
//		}
		return false;
	}

	public boolean aliensWin() {
		//TODO fill your code
		// need to return true when UCM health == 0
//		if (UCMShip.health == 0) {
//			this.running = false;
//			return true;
//		} else {
//			return false;
//		}
		return false;
	}

	public void enableLaser() {
		//TODO fill your code	
		laser = false;
	}
	
	public void disableLaser() {
		laser = true;
	}

	public Random getRandom() {
		//TODO fill your code
		return this.random;
	}

	public Level getLevel() {
		//TODO fill your code
		return this.level;
	}
	
	public boolean none() {
		return true;
	}
	
	public boolean list() {
		return true;
	}
	
	public boolean reset() {
		return true;
	}
	
	public boolean help() {
		System.out.println(Messages.HELP);
		return false;
	}	
	
	public boolean running() {
		return this.running;
	}
	
	public boolean update() {
		return updateBoard;
	}
	
	public void enableUpdate() {
		this.updateBoard = true;
	}
	
	public void disableUpdate() {
		this.updateBoard = false;
	}
	
	public boolean exit() {
		this.running = false;
		return false;
	}
	
	public boolean shockwave() {
		return true;
	}
	
	public boolean shoot() {
		if (laser) {
			System.out.println(Messages.LASER_ERROR);
			return false;
		} else {
			curr_laser = new UCMLaser(player, this);
			Position laser_position = curr_laser.getPosition();
	        other_board[laser_position.getRow()][laser_position.getCol()] = curr_laser;
	        entities.add(curr_laser);
			return true;
		}
	}
	
	public boolean remove(Object entity) {
		entities.remove(entity);
		return true;
	}
	
	public boolean move(String direction) {
		Move move = switch (direction) {
	    case "right" -> Move.RIGHT;
	    case "rright" -> Move.RRIGHT;
	    case "left" -> Move.LEFT;
	    case "lleft" -> Move.LLEFT;
	    case "up" -> Move.UP;
	    case "down" -> Move.DOWN;
	    case "none" -> Move.NONE;
	    default -> null;
		};
		update(player.getPosition(), null, true);
		boolean result = Position.updateSafe(player.getPosition(), move);
		update(player.getPosition(), player, true);
		return result;
//		if (move == null) {
//			return false;
//		} else {
//			return true;
//		}
	}
	
	private void update(Position position, Entity entity, boolean original) {
	    if (original)
	    	board[position.getRow()][position.getCol()] = entity;
	    else
	    	other_board[position.getRow()][position.getCol()] = entity;
	}
	
	public void next(){
		cycles += 1;
		if (laser) {
			update(curr_laser.getPosition(), null, false);
			boolean still_in = curr_laser.automaticMove();
			if (still_in)
				update(curr_laser.getPosition(), curr_laser, false);
			// TODO check if the move made it collide with something
			// need to see if it attacks anything
		}
	}
}
