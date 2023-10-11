package tp1.logic;

//import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.RegularAlien;
//import tp1.logic.lists.DestroyerAlienList;
import tp1.logic.lists.RegularAlienList;

/**
 * 
 * Manages alien initialization and
 * used by aliens to coordinate movement
 *
 */
public class AlienManager {
	
	private Level level;
	private Game game;
	private int remainingAliens;
	
	private boolean squadInFinalRow;
	private int shipsOnBorder;
	private boolean onBorder;
	private RegularAlienList alienList;

	public AlienManager(Game game, Level level) {
		this.level = level;
		this.game = game;
		this.remainingAliens = 0;
		this.alienList = initializeRegularAliens();
	}
		
	// INITIALIZER METHODS
	
	/**
	 * Initializes the list of regular aliens
	 * @return the initial list of regular aliens according to the current level
	 */
	protected RegularAlienList initializeRegularAliens() {
		//TODO fill your code
		if (this.level == Level.EASY) {
			alienList = new RegularAlienList(4);
			populateAlienRow(1, 3);
		} else if (this.level == Level.HARD || this.level == Level.INSANE) {
			alienList = new RegularAlienList(8);
			populateAlienRow(1, 2);
			populateAlienRow(2, 2);
		} else if (this.level == Level.INSANE){
			alienList = new RegularAlienList(8);
			populateAlienRow(1, 1);
			populateAlienRow(2, 1);
		} else {
			System.out.print("ERROR: RegularAlianList(): checking level difficulty");
		}
		return alienList;
	}
	
	private boolean populateAlienRow(int col, int speed) {
		boolean flag = true;
		for (int i = 0; i < 4; i++) {
			Position temp_pos = new Position(2+i, col);
			RegularAlien temp_alien = new RegularAlien(this, temp_pos, speed);
			boolean check = alienList.addAlien(temp_alien);
			if (!check) {
				System.out.println("Error initializing aliens");
				flag = false;
			} else {
				remainingAliens++;
			}
		}
		return flag;
	}

	/**
	 * Initializes the list of destroyer aliens
	 * @return the initial list of destroyer aliens according to the current level
	 */
//	protected  DestroyerAlienList initializeDestroyerAliens() {
//		//TODO fill your code
//	}

	public int getRemainingAliens() {
		return this.remainingAliens;
	}
	
	// CONTROL METHODS
		
	public void shipOnBorder() {
		if(!onBorder) {
			onBorder = true;
			shipsOnBorder = remainingAliens;
		}
	}

	public boolean onBorder() {
		// TODO Auto-generated method stub
		// iterate over each ship and check if one is on the boarder
		// then you can return true, else return false
		return false;
	}

}
