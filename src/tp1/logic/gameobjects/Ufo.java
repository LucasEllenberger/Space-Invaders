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
	private int points = 25;

	private boolean enabled = false;
	private Game game;
	
	//TODO fill your code
	public Ufo(Game game) {
		this.game = game;
	}

//	public boolean computerAction() {
//		if(!enabled && canGenerateRandomUfo()) {
//			enable();
//			return true;
//		} else {
//			return false;
//		}
//	}
	
//	private void enable() {
//		//TODO fill your code
//		this.position = new Position(game.DIM_X - 1, 0);
//		health = 1;
//	}
	
	public void onDelete() {
		//TODO fill your code
		die();
	}
	
	public void computerAction() {
		if(!game.getState("ufo") && canGenerateRandomUfo()) {
			health = 1;
			position = new Position(game.DIM_X - 1, 0);
			game.add(this);
			game.changeState("ufo", true);			
		}
	}
	
	private boolean canGenerateRandomUfo(){
		return game.getRandom().nextDouble() < game.getLevel().getUfoFrequency();
	}


	public String getSymbol() {
		return String.format(Messages.GAME_OBJECT_STATUS, Messages.UFO_SYMBOL, health);
	}

	public Position getPosition() {
		return position;
	}
	
	public int getPoints() {
		return points;
	}


	public boolean automaticMove() {
		// BIG PROBLEM : calling die() here isn't cool because we aren't allowed to 
		// modify the entities lists while its being processed
		Position.update(position, dir);
		if(Position.outside(position)) {
			game.changeState("ufo", false);
			return false;
		}
		return true;
	}

	private void die() {
		game.changeState("ufo", false);
//		position = null;
		game.remove(this);
	}

	public boolean reduceHealth(int damage) {
		health -= damage;
		if (health <= 0) {
			position = null;
			enabled = false;
			game.addPoints(points);
			game.changeState("shockwave", true);
			die();
			return true;
		}
		return false;
	}
	
}
