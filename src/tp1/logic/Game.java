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
	private Set<Entity> entities = new HashSet<Entity>();
	private UCMShip player = new UCMShip();
	private int cycles = 0;
	private int points = 0;
	private boolean laser = false;
	private Level level;
	private Random random;
	private boolean updateBoard = true;

	//TODO fill your code

	public Game(Level level, long seed) {
		//TODO fill your code}
        Position position = player.getPosition();
        board[position.getRow()][position.getCol()] = player;
        entities.add(player);
        this.level = level;
        this.random = new Random(seed);
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
		return 0;
	}

	public String positionToString(int col, int row) {
		//TODO fill your code
		if (entities.contains(board[row][col])) {
			return board[row][col].getSymbol();
		}
		return Messages.EMPTY;
	}

	public boolean playerWin() {
		//TODO fill your code
		System.out.print(Messages.PLAYER_WINS);
		System.exit(0);
		return false;
	}

	public boolean aliensWin() {
		//TODO fill your code
		System.out.print(Messages.ALIENS_WIN);
		System.exit(0);
		return false;
	}

	public void enableLaser() {
		//TODO fill your code	
		laser = true;
	}
	
	public void disableLaser() {
		laser = false;
	}

	public Random getRandom() {
		//TODO fill your code
		return this.random;
	}

	public Level getLevel() {
		//TODO fill your code
		return this.level;
	}
	
	public void none() {
		return;
	}
	
	public void list() {
		return;
	}
	
	public void reset() {
		return;
	}
	
	public void help() {
		System.out.println(Messages.HELP);
		this.updateBoard = false;
		// TODO don't draw the board again
		// don't increment game cycle
	}	
	
	public boolean checkUpdate() {
		return updateBoard;
	}
	
	public void updateBoard() {
		this.updateBoard = true;
	}
	
	public void exit() {
		System.out.print(Messages.PLAYER_QUITS);
		System.exit(0);
		return;
	}
	
	public void shockwave() {
		return;
	}
	
	public void shoot() {
		if (laser) {
			Controller.commandError();
		} else {
			entities.add(new UCMLaser(player, this));
		}
	}
	
	public void remove(Object entity) {
		entities.remove(entity);
	}
	
	public void move(String direction) {
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
		update(player.getPosition(), null);
		Position.updateSafe(player.getPosition(), move);
		update(player.getPosition(), player);
	}
	
	private void update(Position position, Entity entity) {
	    board[position.getRow()][position.getCol()] = entity;
	}
	
	public void next(){
		cycles += 1;
	}
}
