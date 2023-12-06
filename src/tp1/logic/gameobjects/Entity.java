package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;

/**
 * An interface that encapsulates anything that would go on the board to varying degrees
 */

public abstract class Entity {
	
	protected Position position;
	
	protected Game game;
	
	protected int health;
	
    public abstract String getSymbol();

    public Position getPosition() {
    	return position;
    }
    
    public abstract boolean automaticMove();
    
    public abstract boolean reduceHealth(int damage);
}
