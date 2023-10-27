package tp1.logic.gameobjects;

import tp1.logic.Position;

/**
 * An interface that encapsulates anything that would go on the board to varying degrees
 */

public interface Entity {
	
    String getSymbol();

    Position getPosition();
    
    boolean automaticMove();
    
    boolean reduceHealth(int damage);
}
