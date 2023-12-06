package tp1.control.commands;

public abstract class NoParamsCommand extends Command {

	@Override
	public Command parse(String[] commandWords) {
		if (this.matchCommandName(commandWords[0]) && commandWords.length == 1) {
			return this;
		}
		return null;
	}
}