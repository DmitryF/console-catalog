package com.catalog.service;

import java.util.Map;
import java.util.Scanner;

/**
 * @author DzmitryF
 *
 */
public abstract class ConsoleService extends CommandService {
	
	private static String commandsRegex = "";
	
	private Scanner scanner = null;	
	
	public ConsoleService() {
		super();
		
		scanner = new Scanner(System.in);
	}
	
	protected abstract void addCommands();

	@Override
	protected void initCommands() {
		addCommands();

		initCommandsValidator();
	}
	
	private void initCommandsValidator() {
		StringBuilder commandsBuilder = new StringBuilder();
		
		Map<String, Command> commands = getCommands();
		for (String commandName : commands.keySet()) {
			if (commandsBuilder.length() > 0) {
				commandsBuilder.append("|");
			}
			commandsBuilder.append("(").append(commandName).append(")");	
		}
		commandsBuilder.insert(0, "^");
		
		commandsRegex = commandsBuilder.toString();
	}
	
	public boolean hasNextConsoleCommand() {
		return scanner.hasNext(commandsRegex);
	}
	
	public String getNextConsoleCommad() {
		return scanner.next();
	}
	
	public void closeConsole() {
		scanner.close();
	}		
}
