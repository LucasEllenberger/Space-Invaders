package tp1.logic;

import java.util.Random;
import java.util.Arrays;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.Iterator;
import java.util.HashMap;
import java.lang.reflect.Constructor;

import tp1.control.Controller;
import tp1.logic.gameobjects.UCMShip;
import tp1.logic.gameobjects.UCMLaser;
import tp1.logic.gameobjects.Entity;
import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.Space;
import tp1.logic.gameobjects.Ufo;
import tp1.view.Messages;


public class Game {

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;
	private Entity[][] board;
	private Set<Entity> entities = new HashSet<Entity>();
	private UCMShip player = new UCMShip(this);
	private UCMLaser currentLaser;
	private Ufo ufo = new Ufo(this);
	private int cycles = 0;
	private int points = 0;
	private int speed;
	private Move direction = Move.LEFT;
	private Level level;
	private Random random;
	private Space space = new Space();
	private int numRemainingAliens = 0;
	private Map<String, Boolean> state = new HashMap<String, Boolean>() {{
         put("laser", false);
         put("edge", false);
         put("running", true);
         put("ufo", false);
         put("shockwave", false);
     }};
	
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
	
	public void changeState(String field, Boolean value) {
		state.put(field, value);
	}
	
	public boolean getState(String field) {
		return state.get(field);
	}
	
	private void initialize() {
		switch (level.name()) {
		case "INSANE":  
			fillMany(1, 4, RegularAlien.class);
			fillMany(2, 4, RegularAlien.class);
			fillMany(3, 4, DestroyerAlien.class);
			break;
		case "HARD": 
			fillMany(1, 4, RegularAlien.class);
			fillMany(2, 4, RegularAlien.class);
			fillMany(3, 2, DestroyerAlien.class);
			break;
		case "EASY": 
			fillMany(1, 4, RegularAlien.class); 
			fillMany(2, 2, DestroyerAlien.class);
			break;
	    default: Controller.commandError();
		}
	}
	
	// not safe, be careful
	private void fillMany(int row, int amount, Class clazz) {
		int start = (DIM_X - amount) / 2;
		try {
			Constructor constructor = clazz.getDeclaredConstructor(Game.class, Position.class);
			for (int j = 0; j < amount; j++) {
				Position position = new Position(start + j, row);
	            board[row][start + j] = (Entity) constructor.newInstance(this, position);
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
	
	public int getPoints() {
		return points;
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
		if (player.getHealth() == 0) {
			//TODO display dead ship on board on final print screen
			return true;
		}
		boolean ret = false;
		for (Entity entity : entities) {
			if (entity instanceof RegularAlien) {
				ret |= ((RegularAlien) entity).onLastRow();
			}
			// TODO: uncomment when destroyer is implemented
//			if (entity instanceof DestroyerAlien) {
//				ret |= ((DestroyerAlien) entity).onLastRow();
//			}
		}
		return ret;
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
		System.out.println(Messages.ucmShipDescription(Messages.UCMSHIP_DESCRIPTION, player.getEndurance(), player.getDamage()));
		System.out.println(Messages.alienDescription(Messages.REGULAR_ALIEN_DESCRIPTION, 5, 0, 2));
		System.out.println(Messages.alienDescription(Messages.DESTROYER_ALIEN_DESCRIPTION, 10, 1, 1));
		System.out.println(Messages.alienDescription(Messages.UFO_DESCRIPTION, 25, 0, 1));
		return false;
	}
	
	public boolean reset() {
		// TODO
		return true;
	}
	
	public boolean help() {
		System.out.println(Messages.HELP);
		return false;
	}	
	
	public boolean running() {
		return state.get("running");
	}
	
	
	public boolean exit() {
		changeState("running", false);
		return false;
	}
	
	public void addPoints(int newPoints) {
		points += newPoints;
	}
	
	public boolean shockwave() {
		if (state.get("shockwave")) {
			Iterator<Entity> iterator = entities.iterator();
			while (iterator.hasNext()) {
				Entity entity = iterator.next();
				if (entity instanceof RegularAlien || entity instanceof DestroyerAlien) {
					if (entity.reduceHealth(1)) {
						iterator.remove();
						numRemainingAliens -= 1;
					}
				}
			}
			state.put("shockwave", false);
			return true;
		} else {
			System.out.println(Messages.SHOCKWAVE_ERROR);
			return false;
		}
	}
	
	public int getUCMHealth() {
		return player.getHealth();
	}
	
	public boolean shoot() {
		if ((boolean) state.get("laser")) {
			System.out.println(Messages.LASER_ERROR);
			return false;
		} else {
			currentLaser = new UCMLaser(player, this);
			return true;
		}
	}
	
	public boolean remove(Object entity) {
		if (entity instanceof RegularAlien || entity instanceof DestroyerAlien) {
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
			boolean edge = state.get("edge");
			if (edge) {
				RegularAlien.changeDirection(Move.DOWN);
				DestroyerAlien.changeDirection(Move.DOWN);
				changeState("edge", !edge);
				if (direction.equals(Move.LEFT)) {
					direction = Move.RIGHT;
				} else {
					direction = Move.LEFT;
				}
			} else {
				RegularAlien.changeDirection(direction);
				DestroyerAlien.changeDirection(direction);
			}
		}
	}
	
	public boolean shouldMove() {
		return ((cycles % speed == 0) && (cycles != 0));
	}
	
	public void next(){
		resetBoard();
		
		orienter();
		
		Iterator<Entity> iterator = entities.iterator();
		while (iterator.hasNext()) {
			Entity entity = iterator.next();
			if (entity.automaticMove()) {
				fill(entity);
			} else {
				iterator.remove();
			}
		}
		
		if (!state.get("ufo")) {
			ufo.computerAction();
			if (state.get("ufo")) {
				fill(ufo);
			}
		}
		
		Entity playerPosition = board[player.getPosition().getRow()][player.getPosition().getCol()];
		switch (playerPosition.getClass().getName())  {
		
			default :
				fill(player);
		}
		
		if (state.get("laser") && currentLaser.automaticMove()) {
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
