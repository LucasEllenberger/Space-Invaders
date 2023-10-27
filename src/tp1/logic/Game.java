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
import tp1.logic.gameobjects.Attributes;
import tp1.logic.gameobjects.Bomb;
import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.Space;
import tp1.logic.gameobjects.Ufo;
import tp1.view.Messages;



/**
 * Class that implements the core logic of the game. Resolves all commands from the Controller
 */

public class Game {

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;
	private Move direction = Move.LEFT;
	private Level level;
	private Random random;
	private Space space = new Space();
	private UCMShip player = new UCMShip(this);
	private UCMLaser currentLaser;
	private Ufo ufo = new Ufo(this);
	private Entity[][] board;
	private Set<Entity> entities = new HashSet<Entity>();
	private Set<Entity> temp = new HashSet<Entity>();
	private Map<String, Boolean> state = new HashMap<String, Boolean>() {{
         put("laser", false);
         put("edge", false);
         put("ufo", false);
         put("shockwave", false);
         put("running", true);
    }};
	private Map<String, Integer> metrics = new HashMap<String, Integer>() {{
	     put("points", 0);
	     put("cycles", 0);
	     put("aliens", 0);
	     put("speed", 0);
	}};
  
	public Game(Level level, long seed) {
        this.level = level;
        this.random = new Random(seed);
        changeMetric("speed", level.getSpeed());
        resetBoard();
        initialize();
		fill(player);
	}
	
	public Random getRandom() {
		return this.random;
	}

	public Level getLevel() {
		return this.level;
	}
	
	public String positionToString(int col, int row) {
		return board[row][col].getSymbol();
	}
	
	public String stateToString() {
		StringBuilder buffer = new StringBuilder();
		String NEW_LINE = System.lineSeparator();
		String SPACE = " ";
		buffer.append(Messages.LIFE).append(SPACE).append(player.getHealth()).append(NEW_LINE)
		.append(Messages.POINTS).append(SPACE).append(getMetric("points")).append(NEW_LINE)
		.append(Messages.SHOCKWAVE).append(SPACE).append(getState("shockwave") ? "ON" : "OFF").append(NEW_LINE);
		return buffer.toString();
	}
	
	/**
	 * Change boolean game attributes
	 * 
	 * @param field Name of attribute
	 * @param value Value that the attribute should be
	 */
	public void changeState(String field, Boolean value) {
		state.put(field, value);
	}
	
	/**
	 * Access boolean game attributes
	 * 
	 * @param field Name of attribute 
	 * @return Boolean value corresponding to attribute
	 */
	
	public boolean getState(String field) {
		
		return state.get(field);
	}
	
	/**
	 * Access integer game attributes
	 * 
	 * @param field Name of attribute 
	 * @return Integer value corresponding to attribute
	 */
	
	public int getMetric(String field) {
		return metrics.get(field);
	}
	
	/**
	 * Change integer game attributes
	 * 
	 * @param field Name of attribute
	 * @param value Value that the attribute should be
	 */
	
	public void changeMetric(String field, int value) {
		metrics.put(field, metrics.get(field) + value);
	}
	

	/**
	 * Check if a player has won
	 * 
	 * @return A boolean representing whether the player has won
	 */
	
	public boolean playerWin() {
		
		return (getMetric("aliens") == 0);
	}
		
	/**
	 * Remove entity from game logic
	 * 
	 * @param entity Entity to be removed from game 
	 */
	
	public void remove(Entity entity) {
		if (entity instanceof RegularAlien || entity instanceof DestroyerAlien) {
			changeMetric("aliens", -1);
		}
		entities.remove(entity);
	}
	
	/**
	 * Add entity to game logic
	 * 
	 * @param entity Entity to be added to game 
	 */
	
	public boolean add(Entity entity) {
		entities.add(entity);
		return true;
	}
	
	/**
	 * Add entity to a temporary stack
	 * 
	 * @param entity Entity to be added a temporary stack
	 */
	
	public boolean addTemp(Entity entity) {
		temp.add(entity);
		return true;
	}
	
	/**
	 * Returns a value that determines when it is permitted to move for entites that are affected by speed
	 * 
	 * @return A boolean representing whether the cycle and speed coincide
	 */
	
	public boolean shouldMove() {
		
		return ((getMetric("cycles") % (getMetric("speed") + 1) == 0) && (getMetric("cycles")  != 0));
	}
	
	/**
	 * Fills board with aliens according to game level
	 */
	
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
	
	/**
	 * Helper method that creates and fills an arbitrary amount of
	 * entities that have (Game, Posiition) constructors to the center of a given row
	 * 
	 * @param row Row to fill entities in
	 * @param amount How many entities to put in the row
	 * @param clazz What type of entity to fill
	 * @throws Generic error if amount exceeds row space or the class to instantiate wasn't valid
	 */
	
	private void fillMany(int row, int amount, Class clazz) {
		
		int start = (DIM_X - amount) / 2;
		try {
			Constructor constructor = clazz.getDeclaredConstructor(Game.class, Position.class);
			for (int j = 0; j < amount; j++) {
				Position position = new Position(start + j, row);
	            board[row][start + j] = (Entity) constructor.newInstance(this, position);
	            changeMetric("aliens", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
      		
		
	}

	/**
	 * Determines if aliens have won 
	 *
	 * @return A boolean representing whether or not aliens have won
	 */
	
	public boolean aliensWin() {
		
		if (player.getHealth() == 0) {
			return true;
		}
		for (Entity entity : entities) {
			if ((entity instanceof RegularAlien || entity instanceof DestroyerAlien) 
					&& Position.onLastRow(entity.getPosition())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Executes "none" command 
	 * 
	 * @returns true, to update the game
	 */
	
	public boolean none() {
		
		return true;
	}
	
	/**
	 * Executes "list" command 
	 * 
	 * @returns false, to not update the game
	 */
	
	public boolean list() {
		System.out.println(Messages.ucmShipDescription(Messages.UCMSHIP_DESCRIPTION, Attributes.Player.endurance,  Attributes.Player.damage));
		System.out.println(Messages.alienDescription(Messages.REGULAR_ALIEN_DESCRIPTION, Attributes.RegularAlien.points, Attributes.RegularAlien.damage, Attributes.RegularAlien.endurance));
		System.out.println(Messages.alienDescription(Messages.DESTROYER_ALIEN_DESCRIPTION, Attributes.DestroyerAlien.points,  Attributes.DestroyerAlien.damage, Attributes.DestroyerAlien.endurance));
		System.out.println(Messages.alienDescription(Messages.UFO_DESCRIPTION, Attributes.Ufo.points,  Attributes.Ufo.damage,  Attributes.Ufo.endurance));
		return false;
	}
	
	/**
	 * Executes "reset" command 
	 * 
	 * @returns false, to not update the game
	 */
	
	public boolean reset() {
		temp.clear();
		entities.clear();
		changeState("laser", false);
		changeState("edge", false);
		changeState("running", true);
		changeState("ufo", false);
		changeState("shockwave", false);
		changeMetric("points", -getMetric("points"));
		changeMetric("cycles", -getMetric("cycles"));
		changeMetric("aliens", -getMetric("aliens"));
		resetBoard();
        initialize();
        player = new UCMShip(this);
		fill(player);
		return false;
	}
	
	/**
	 * Executes "help" command 
	 * 
	 * @returns false, to not update the game
	 */
	
	public boolean help() {
		System.out.println(Messages.HELP);
		return false;
	}	
	
	/**
	 * Executes "help" command 
	 * 
	 * @returns false, to not update the game
	 */
	
	public boolean exit() {
		changeState("running", false);
		return false;
	}
	
	
	/**
	 * Executes "shockwave" command 
	 * 
	 * @returns Boolean representing whether or not shockwave command was valid
	 */
	
	public boolean shockwave() {
		if (state.get("shockwave")) {
			Iterator<Entity> iterator = entities.iterator();
			while (iterator.hasNext()) {
				Entity entity = iterator.next();
				if (entity instanceof RegularAlien || entity instanceof DestroyerAlien) {
					if (entity.reduceHealth(1)) {
						iterator.remove();
						changeMetric("aliens", -1);
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
	
	/**
	 * Executes "shoot" command 
	 * 
	 * @returns Boolean representing whether or not shoot command was valid
	 */
	
	public boolean shoot() {
		if (state.get("laser")) {
			System.out.println(Messages.LASER_ERROR);
			return false;
		} else {
			currentLaser = new UCMLaser(player, this);
			return true;
		}
	}
	
	/**
	 * Executes "move" command 
	 * 
	 * @returns Boolean representing whether or not move command was valid
	 */

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
	
	/**
	 * Puts an entity on the board. After this methods, reading from the board (in its position) 
	 * will return some representation of this entity 
	 */
	
	private void fill(Entity entity) {
		Position position = entity.getPosition();
		board[position.getRow()][position.getCol()] = entity;
	}
	
	/**
	 * Instantiates a new board according to board dimensions and fills it with Space
	 */
	
	private void resetBoard() {
		board = new Entity[DIM_Y][DIM_X];
		for (int i = 0; i < board.length; i++) {
		    Arrays.fill(board[i], space);
		}
	}
	
	/**
	 * Orients all aliens to move properly for the next move based on current information
	 */
	
	private void orienter() {
		if (shouldMove()) {
			if (state.get("edge")) {
				RegularAlien.changeDirection(Move.DOWN);
				DestroyerAlien.changeDirection(Move.DOWN);
				changeState("edge", !state.get("edge"));
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
	
	/**
	 * Updates positions for all entities that automatically move
	 */
	
	private void automaticMoves() {
		Iterator<Entity> iterator = entities.iterator();
		while (iterator.hasNext()) {
			Entity entity = iterator.next();
			if (entity.automaticMove()) {
				fill(entity);
			} else {
				iterator.remove();
			}
		}
		
		
		for (Entity entity : temp) {
			if (entity.automaticMove()) {
				entities.add(entity);
				fill(entity);
			}
		}
		
		temp.clear();
		
		if (!state.get("ufo") && ufo.computerAction()) {
			fill(ufo);
		}
	}
	
	/**
	 * Defines core laser behavior by resolving collisions for whatever might be in the laser's position
	 */
	
	private void resolveLaser() {
		if (state.get("laser")) {
			Entity entity = board[currentLaser.getPosition().getRow()][currentLaser.getPosition().getCol()];
			if (currentLaser.attack(entity)) {
				if (entity instanceof Bomb) {
					remove(entity);
				}
				if (!entities.contains(entity)) {
					board[currentLaser.getPosition().getRow()][currentLaser.getPosition().getCol()] = space;
				}
			} else if (currentLaser.automaticMove()) {
				entity = board[currentLaser.getPosition().getRow()][currentLaser.getPosition().getCol()];
				if (currentLaser.attack(entity)) {
					if (entity instanceof Bomb) {
						remove(entity);
					}
					if (!entities.contains(entity)) {
						board[currentLaser.getPosition().getRow()][currentLaser.getPosition().getCol()] = space;
					}
				} else {
					fill(currentLaser);
				}
			}
		}
	}
	
	/**
	 * Resolves collisions of the player with anything else, readying the board for display
	 */
	
	private void resolvePlayer() {
		Entity playerPosition = board[player.getPosition().getRow()][player.getPosition().getCol()];
		if (playerPosition instanceof Bomb) {
			player.reduceHealth(Attributes.DestroyerAlien.damage);
		}
		fill(player);
	}
	
	/**
	 *  Advances the game by one cycle and draws the board
	 */
	
	public void next(){
		resetBoard();
		orienter();
		automaticMoves();
		resolveLaser();
		resolvePlayer();
		changeMetric("cycles", 1);
	}
}
