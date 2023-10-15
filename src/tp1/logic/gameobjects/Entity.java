package tp1.logic.gameobjects;
import tp1.logic.Position;
public interface Entity {

    String getSymbol();

    Position getPosition();
    
    boolean automaticMove();
    
    void reduceHealth(int damage);
}
