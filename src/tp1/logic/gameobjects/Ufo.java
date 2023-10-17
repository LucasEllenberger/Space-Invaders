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
	private Game game;
	
	//TODO fill your code
	public Ufo(Game game) {
		this.game = game;
	}

	public void computerAction() {
		if(!game.getState("ufo") && canGenerateRandomUfo()) {
			position = new Position(game.DIM_X, 0);
			game.add(this);
			game.changeState("ufo", true);
		}
	}
	

	private boolean canGenerateRandomUfo(){
		return game.getRandom().nextDouble() < game.getLevel().getUfoFrequency();
	}


	public String getSymbol() {
		return Messages.UFO_SYMBOL;
	}

	public Position getPosition() {
		return position;
	}


	public boolean automaticMove() {
		// BIG PROBLEM : calling die() here isn't cool because we aren't allowed to 
		// modify the entities lists while its being processed
		Position.update(position, dir);
		if(Position.outside(position)) {
			die();
			return false;
		}
		return true;
	}

	private void die() {
		game.remove(this);
		game.changeState("ufo", false);
	}

	public void reduceHealth(int damage) {
		health -= damage;
		if (health <= 0) {
			die();
		}
	}
	
}
