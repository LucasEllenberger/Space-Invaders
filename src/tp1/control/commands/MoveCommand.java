package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.view.Messages;

public class MoveCommand extends Command {

	private Move move;

	public MoveCommand() {}

	protected MoveCommand(Move move) {
		this.move = move;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_MOVE_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_MOVE_SHORTCUT;
	}

	@Override
	protected String getDetails() {
		return Messages.COMMAND_MOVE_DETAILS;
	}

	@Override
	protected String getHelp() {
		return Messages.COMMAND_MOVE_HELP;
	}

	@Override
	public ExecutionResult execute(Game game) {
		//TODO Test
		if (game.move(move)) {
			game.next();
			return new ExecutionResult(true);
		} else {
			return new ExecutionResult(Messages.MOVEMENT_ERROR);
		}
	}


	@Override
	public Command parse(String[] commandWords) {
		if (this.matchCommandName(commandWords[0]) && commandWords.length > 1) {
			Move move = switch (commandWords[1]) {
		    case "right" -> Move.RIGHT;
		    case "rright" -> Move.RRIGHT;
		    case "left" -> Move.LEFT;
		    case "lleft" -> Move.LLEFT;
		    case "up" -> Move.UP;
		    case "down" -> Move.DOWN;
		    case "none" -> Move.NONE;
		    default -> null;
			};
			if (move != null) {
				return new MoveCommand(move);
			} 			
		}
	    return null;
	}

}