package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.logic.Game;
import tp1.view.Messages;

public class ShockwaveCommand extends NoParamsCommand {

	@Override
	protected String getName() {
		return Messages.COMMAND_SHOCKWAVE_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_SHOCKWAVE_SHORTCUT;
	}

	@Override
	protected String getDetails() {
		return Messages.COMMAND_SHOCKWAVE_DETAILS;
	}

	@Override
	protected String getHelp() {
		return Messages.COMMAND_SHOCKWAVE_HELP;
	}

	@Override
	public ExecutionResult execute(Game game) {
		// TODO Test
		if (game.shockwave()) {
			game.next();
			return new ExecutionResult(true);
		} else {
			return new ExecutionResult(Messages.SHOCKWAVE_ERROR);
		}
	}

}
