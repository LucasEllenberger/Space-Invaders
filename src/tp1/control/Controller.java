package tp1.control;

import static tp1.view.Messages.debug;

import java.util.Scanner;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.view.GamePrinter;
import tp1.view.Messages;

/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller {

	private Game game;
	private Scanner scanner;
	private GamePrinter printer;
	// private boolean result;

	public Controller(Game game, Scanner scanner) {
		this.game = game;
		this.scanner = scanner;
		printer = new GamePrinter(game);
	}

	/**
	 * Show prompt and request command.
	 *
	 * @return the player command as words
	 */
	private String[] prompt() {
		System.out.print(Messages.PROMPT);
		String line = scanner.nextLine();
		String[] words = line.toLowerCase().trim().split("\\s+");

		System.out.println(debug(line));

		return words;
	}

	/**
	 * Runs the game logic
	 */
	public void run() {
		//TODO fill your code
		printGame();
		boolean result = true;
		while (game.running() && !game.playerWin() && !game.aliensWin()) {
			result = new_handler(prompt());
			// checkUpdate will be false when the command doesn't require a board update
			// used in help command
			if (result) {
				game.next();
				printGame();
			} // else { 			// reset the boolean to update the board next cycle
//				game.enableUpdate();
//			}
		}
		System.out.print(printer.endMessage());
	}

	/**
	 * Draw / paint the game
	 */
	private void printGame() {
		System.out.println(printer);
	}
	
	/**
	 * Prints the final message once the game is finished
	 */
	public void printEndMessage() {
		System.out.println(printer.endMessage());
	}
	
	/**
	 * Command parsing and validation
	 */
	private boolean new_handler(String[] command) {
		if (command.length == 0) {
			return errorHandler(Messages.INVALID_COMMAND);
		} else {
			return switch (command[0].toLowerCase()) {
			case "", "n", "none" -> game.none();
		    case "w", "shockwave" -> game.shockwave();
		    case "s", "shoot" -> game.shoot();
		    case "l", "list" -> game.list();
		    case "r", "reset" -> game.reset();
		    case "h", "help" -> game.help();
		    case "e", "exit" -> game.exit();
		    case "m", "move" -> moveHandler(command);
		    default -> errorHandler(Messages.INVALID_COMMAND);
			};
		}
	}

	private boolean moveHandler(String[] command) {
		if (command.length < 2) {
			return errorHandler(Messages.INVALID_COMMAND);
		} else {
			return switch (command[1].toLowerCase()) {
			case "lleft", "left", "right", "rright", "up", "down", "none" -> game.move(command[1].toLowerCase());
			default -> invalidMovement(command[1]);
			};
		}
	}
	
	private boolean errorHandler(String message) {
		System.out.println(message);
		return false;
	}
	
	private boolean invalidMovement(String movement) {
		System.out.print(Messages.DIRECTION_ERROR);
		System.out.println(movement);
		return false;
	}

//	private void handler(String[] command) {
//		if (command.length >= 3) {
//			commandError();
//		} else if (command.length == 0) {
//			game.none();
//		} else if (command.length == 1) {
//			switch (command[0].toLowerCase()) {
//		    case "n", "none" -> game.none();
//		    case "w", "shockwave" -> game.shockwave();
//		    case "s", "shoot" -> game.shoot();
//		    case "l", "list" -> game.list();
//		    case "r", "reset" -> game.reset();
//		    case "h", "help" -> game.help();
//		    case "e", "exit" -> game.exit();
//		    default -> System.out.println(Messages.INVALID_COMMAND);
//			}
//		} else {
//			String cleaned = command[0].toLowerCase();
//			if (cleaned.equals("move") || cleaned.equals("m")) {
//				switch (command[1].toLowerCase()) {
//				case "lleft", "left", "right", "rright", "up", "down", "none" -> game.move(command[1].toLowerCase());
//				default -> commandError();
//				}
//			} else {
//				game.disableUpdate();
//				commandError();
//			}
//		}
//	}
	
	public static void commandError() {
		System.out.println("command is misspelled, does not exist, or cannot be executed");
	}

	
}
