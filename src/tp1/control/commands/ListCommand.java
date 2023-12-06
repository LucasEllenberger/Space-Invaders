package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.logic.Game;
import tp1.logic.gameobjects.Attributes;
import tp1.view.Messages;

public class ListCommand extends NoParamsCommand {

	@Override
	protected String getName() {
		return Messages.COMMAND_LIST_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_LIST_SHORTCUT;
	}

	@Override
	protected String getDetails() {
		return Messages.COMMAND_LIST_DETAILS;
	}

	@Override
	protected String getHelp() {
		return Messages.COMMAND_LIST_HELP;
	}

	@Override
	public ExecutionResult execute(Game game) {
		System.out.println(Messages.ucmShipDescription(Messages.UCMSHIP_DESCRIPTION, Attributes.Player.endurance,  Attributes.Player.damage));
		System.out.println(Messages.alienDescription(Messages.REGULAR_ALIEN_DESCRIPTION, Attributes.RegularAlien.points, Attributes.RegularAlien.damage, Attributes.RegularAlien.endurance));
		System.out.println(Messages.alienDescription(Messages.DESTROYER_ALIEN_DESCRIPTION, Attributes.DestroyerAlien.points,  Attributes.DestroyerAlien.damage, Attributes.DestroyerAlien.endurance));
		System.out.println(Messages.alienDescription(Messages.UFO_DESCRIPTION, Attributes.Ufo.points,  Attributes.Ufo.damage,  Attributes.Ufo.endurance));
		return new ExecutionResult(false);
	}

}