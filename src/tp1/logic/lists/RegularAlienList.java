package tp1.logic.lists;

import tp1.logic.Position;
import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.gameobjects.UCMLaser;

/**
 * Container of regular aliens, implemented as an array with a counter
 * It is in charge of forwarding petitions from the game to each regular alien
 * 
 */
public class RegularAlienList {

	private RegularAlien[] objects;
	private int num;
	private int pointer = 0;
	
	//TODO fill your code
	public RegularAlienList(int numAliens) {
		objects = new RegularAlien[numAliens];
		num = numAliens;
	}
	
	public boolean addAlien(RegularAlien alien) {
		if (objects == null) {
			return false;
		}
		if (pointer < num) {
			objects[pointer] = alien;
			pointer++;
			return true;
		}
		return false;
	}

}
