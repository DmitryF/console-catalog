package com.catalog.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DzmitryF
 *
 */
public abstract class CommandService {
	
	private Map<String, Command> commands;
	
	public interface Command {			
		void execute(String... params);
	}
	
	public CommandService() {		
		commands = new HashMap<>();		
		initCommands();	
	}

	protected abstract void initCommands();
	
	public void addCommand(String commandName, Command command) {
		if (command != null) {
			commands.put(commandName, command);
		}
	}
	
	public void executeCommand(String nameCommand, String... params) {
		if (commands.containsKey(nameCommand)){
			Command command = commands.get(nameCommand);
			if (command != null) {
				command.execute(params);
			}
		}
	}
	
	public Map<String, Command> getCommands() {
		return commands;
	}
}
