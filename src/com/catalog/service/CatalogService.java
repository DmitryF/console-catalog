package com.catalog.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.catalog.base.AbstractCatalog;
import com.catalog.base.CatalogItem;

/**
 * @author DzmitryF
 *
 */
public class CatalogService extends ConsoleService {
		
	/**
	 * Console tool tips information
	 */			
	private static final String UNSUPPORTED_COMMAND_INFO = "Unsupported command: ";	
	
	private AbstractCatalog<? extends CatalogItem> catalog;
	
	public CatalogService(AbstractCatalog<? extends CatalogItem> catalog) {
		super();
		
		this.catalog = catalog;
	}
	
	@Override
	protected void addCommands() {
		addCommand(StartCommand.START_COMMAND, new StartCommand());
		addCommand(ShowCommand.SHOW_COMMAND, new ShowCommand());
		addCommand(ShowItemsCatalogCommand.ITEMS_COMMAND, new ShowItemsCatalogCommand());
		addCommand(ShowGroupsCatalogCommand.GROUPS_COMMAND, new ShowGroupsCatalogCommand());		
		addCommand(ExitCommand.EXIT_COMMAND, new ExitCommand());		
	}

	public void start() {				
		executeCommand(StartCommand.START_COMMAND);		
	}
	
	protected void println(String message) {		
		System.out.println(message);
	}
	
	protected void print(String message) {		
		System.out.print(message);
	}
	
	public AbstractCatalog<? extends CatalogItem> getCatalog(){
		return catalog;
	}
	
	private void showUnsupportedCommandMessage(Command lastCommand, String... params) {
		println(UNSUPPORTED_COMMAND_INFO + getNextConsoleCommad());
		lastCommand.execute(params);
	}
	
	/**
	 * Run catalog
	 * @author DzmitryF
	 *
	 */
	protected class StartCommand implements Command {

		public static final String START_COMMAND = "start";
		
		/**
		 * Console tool tips information
		 */
		private static final String CATALOG_INFO = "catalog: ";
		
		@Override
		public void execute(String... params) {
			print(CATALOG_INFO);
			if (hasNextConsoleCommand()) {
				String consoleParam = getNextConsoleCommad();				
				Set<String> commands = getCommands().keySet();
				for (String commandName : commands) {
					if (commandName.equals(consoleParam)) {					
						executeCommand(commandName);
						return;
					} 
				}
			}			
			showUnsupportedCommandMessage(this, params);			
		}		
	}
	
	/**
	 * Show catalog content
	 * @author DzmitryF
	 *
	 */
	protected class ShowCommand implements Command {

		public static final String SHOW_COMMAND = "show";
		
		/**
		 * Console tool tips information
		 */		
		private static final String CATALOG_SHOW_INFO = "catalog show: ";
		
		@Override
		public void execute(String... params) {	
			print(CATALOG_SHOW_INFO);
			if (hasNextConsoleCommand()) {
				String param = getNextConsoleCommad();
				if (ShowGroupsCatalogCommand.GROUPS_COMMAND.equals(param)) {					
					executeCommand(ShowGroupsCatalogCommand.GROUPS_COMMAND);
					return;
				} else if (ShowItemsCatalogCommand.ITEMS_COMMAND.equals(param)) {
					executeCommand(ShowItemsCatalogCommand.ITEMS_COMMAND);
					return;
				}
			}
			showUnsupportedCommandMessage(this, params);		
		}		
	}
	
	/**
	 * Show catalog groups
	 * @author DzmitryF
	 *
	 */
	protected class ShowItemsCatalogCommand implements Command {

		public static final String ITEMS_COMMAND = "items";
		
		/**
		 * Console tool tips information
		 */	
		private static final String CATALOG_EMPTY_INFO = "Catalog empty.";
		
		@Override
		public void execute(String... params) {			
			showItems();
			executeCommand(StartCommand.START_COMMAND);
		}	
		
		private void showItems() {
			List<? extends CatalogItem> items = catalog.getItems();
			
			if (items.size() > 0) {	            
					println("/////////// Catalog has size = " + items.size() + " ///////////");					
		            for(CatalogItem filterItem : items){		            	
		            	println("");
		            	println(filterItem.toString());
		            	println("");		            	
		            }
		            println("//////////////////////////////");		            		
			} else {
				println(CATALOG_EMPTY_INFO);
			}
		}
	}	
	
	/**
	 * Show catalog groups
	 * @author DzmitryF
	 *
	 */
	protected class ShowGroupsCatalogCommand implements Command {

		public static final String GROUPS_COMMAND = "groups";
		
		/**
		 * Console tool tips information
		 */		
		private static final String FILTER_NAME_INFO = "filter name: ";
		private static final String GROUPS_NOT_FOUND_INFO = "Groups not found.";
		
		@Override
		public void execute(String... params) {
			print(FILTER_NAME_INFO);
			String filterGroupName = getNextConsoleCommad();
			showGroups(filterGroupName);
			executeCommand(StartCommand.START_COMMAND);
		}	
		
		private void showGroups(String groupName) {
			Map<String, ? extends List<? extends CatalogItem>> items = catalog.getGroups(groupName);
			
			if (items.size() > 0) {
				println("/////////// GROUPING BY: " + groupName + " ///////////");
				println("");
				for(Entry<String, ? extends List<? extends CatalogItem>> item : items.entrySet()){		            
					println("/////////// GROUP: " + item.getKey() + " has size = " + item.getValue().size() + " ///////////");					
		            for(CatalogItem filterItem : item.getValue()){		            			            	
		            	println(filterItem.toString());
		            	println("");		            	
		            }
		            println("//////////////////////////////");
		            println("");
		        } 
				println("//////////////////////////////");
				println("");
			} else {
				println(GROUPS_NOT_FOUND_INFO);
			}
		}
	}	
	
	/**
	 * Exit from catalog
	 * @author DzmitryF
	 *
	 */
	protected class ExitCommand implements Command {

		public static final String EXIT_COMMAND = "exit";
		
		/**
		 * Console tool tips information
		 */				
		private static final String EXIT_INFO = "Exit.";
		
		@Override
		public void execute(String... params) {
			println(EXIT_INFO);
			closeConsole();
		}		
	}
}
