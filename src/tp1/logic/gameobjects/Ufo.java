package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;


public class Ufo implements Entity{

	//TODO fill your code
	private static Move dir = Move.LEFT;
	private Position position;
	private int health = 1;

	private boolean enabled = false;
	private Game game;
	
	//TODO fill your code
	public Ufo(Game game) {
		this.game = game;
	}

	public void computerAction() {
		if(!enabled && canGenerateRandomUfo()) {
			enable();
		}
	}
	
	private void enable() {
		//TODO fill your code
		this.position = new Position(game.DIM_X - 1, 0);
	}
	
	public void onDelete() {
		//TODO fill your code
	}

	/**
	 * Checks if the game should generate an ufo.
	 * 
	 * @return <code>true</code> if an ufo should be generated.
	 */
	private boolean canGenerateRandomUfo(){
		return game.getRandom().nextDouble() < game.getLevel().getUfoFrequency();
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return Messages.UFO_SYMBOL;
	}

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public boolean automaticMove() {
		// TODO Auto-generated method stub
		Position.update(position, dir);
		if(Position.outside(position)) {
			die();
			return false;
		}
		return true;
	}

	private void die() {
		position = null;
		enabled = false;
	}
	
	@Override
	public void reduceHealth(int damage) {
		// TODO Auto-generated method stub
		this.health -= damage;
		if (health <= 0) {
			position = null;
			enabled = false;
		}
	}
	
}
