package tp1.logic;

import java.util.Random;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.Constructor;

import tp1.control.Controller;
import tp1.logic.gameobjects.UCMShip;
import tp1.logic.gameobjects.UCMLaser;
import tp1.logic.gameobjects.Entity;
import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.gameobjects.Space;
import tp1.view.Messages;


public class Game {

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;
	private Entity[][] board = new Entity[DIM_Y][DIM_X];
	private Set<Entity> entities = new HashSet<Entity>();
	private UCMShip player = new UCMShip(this);
	private UCMLaser currentLaser = null;
	private int cycles = 0;
	private int points = 0;
	private int speed;
	private boolean laser = false;
	private Move direction = Move.LEFT;
	private boolean edge = false;
	private Level level;
	private Random random;
	private boolean updateBoard = true;
	private boolean running = true;
	private Space space = new Space();
	private int numRemainingAliens = 0;

	//TODO fill your code

	public Game(Level level, long seed) {
		//TODO fill your code}
        this.level = level;
        this.speed = level.getSpeed();
        this.random = new Random(seed);
        resetBoard();
        initialize();
		fill(player);
	}
	
	private void initialize() {
		switch (level.name()) {
		case "INSANE":  fillMany(2, 4, RegularAlien.class);
		case "HARD": fillMany(2, 4, RegularAlien.class);
		case "EASY": fillMany(1, 4, RegularAlien.class);
	    default: Controller.commandError();
		}
	}
	
	// not safe, be careful
	private void fillMany(int row, int amount, Class clazz) {
		int start = (DIM_X - amount) / 2;
		try {
			Constructor constructor = clazz.getDeclaredConstructor(Game.class, Position.class);
			 for (int i = 0; i < amount; i++) {
			 	Position position = new Position(start + i, row);
	            board[row][start + i] = (Entity) constructor.newInstance(this, position);
	            numRemainingAliens++;
		     }
		} catch (Exception e) {
			e.printStackTrace();
		}
      		
		
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
		return numRemainingAliens;
	}

	public String positionToString(int col, int row) {
		//TODO fill your code
		return board[row][col].getSymbol();
	}

	public boolean playerWin() {
		//TODO fill your code
		// need to know if all the aliens are dead
//		if (game.getRemainingAliens() == 0) {
//		this.running = false;
//			return true;
//		}
		return (numRemainingAliens == 0);
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
		if (numRemainingAliens == 0) {
			return false;
		}
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
			currentLaser = new UCMLaser(player, this);
			return true;
		}
	}
	
	public boolean remove(Object entity) {
		if (entity instanceof RegularAlien) {
			numRemainingAliens--;
		}
		entities.remove(entity);
		return true;
	}
	
	public boolean add(Entity entity) {
		entities.add(entity);
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
		return Position.updateSafe(player.getPosition(), move);
	}
	
	private void fill(Entity entity) {
		Position position = entity.getPosition();
		board[position.getRow()][position.getCol()] = entity;
	}
	
	
	private void resetBoard() {
		board = new Entity[DIM_Y][DIM_X];
		for (int i = 0; i < board.length; i++) {
		    Arrays.fill(board[i], space);
		}
	}
	
	private void orienter() {
		if (shouldMove()) {
			if (edge) {
				RegularAlien.changeDirection(Move.DOWN);
				changeEdge(!edge);
				if (direction.equals(Move.LEFT)) {
					direction = Move.RIGHT;
				} else {
					direction = Move.LEFT;
				}
			} else {
				RegularAlien.changeDirection(direction);
			}
		}
	}
	
	public boolean shouldMove() {
		return ((cycles % speed == 0) && (cycles != 0));
	}
	
	public void changeEdge(boolean edge) {
		this.edge = edge;
	}
	
	public void next(){
		
		resetBoard();
		
		orienter();
		
		for (Entity entity : entities) {
			entity.automaticMove();
			fill(entity);
		}
	
		
		Entity playerPosition = board[player.getPosition().getRow()][player.getPosition().getCol()];
		switch (playerPosition.getClass().getName())  {
		
			default :
				fill(player);
		}
		
		if (laser && currentLaser.automaticMove()) {
			Entity entity = board[currentLaser.getPosition().getRow()][currentLaser.getPosition().getCol()];
			if (!currentLaser.attack(entity)) {
				fill(currentLaser);
			} else if (!entities.contains(entity)) {
				board[currentLaser.getPosition().getRow()][currentLaser.getPosition().getCol()] = space;
			}
			
		} 
		cycles += 1;
	}
}
