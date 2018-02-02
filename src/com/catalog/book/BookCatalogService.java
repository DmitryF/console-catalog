package com.catalog.book;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.catalog.base.CatalogItem;
import com.catalog.service.CatalogService;
import com.rss.parse.RSSParseException;
import com.rss.write.RSSWriteException;

/**
 * @author DzmitryF
 *
 */
public class BookCatalogService extends CatalogService {
	
	public BookCatalogService(BookCatalog catalog) {
		super(catalog); 
	}

	@Override
	protected void addCommands() {	
		super.addCommands();
		addCommand(ReadBooksCommand.READ_BOOKS_COMMAND, new ReadBooksCommand());
		addCommand(WriteGroupsCommand.WRITE_BOOKS_COMMAND, new WriteGroupsCommand());
	}		

	private class ReadBooksCommand implements Command {

		protected static final String READ_BOOKS_COMMAND = "read";
		
		/**
		 * Console tool tips information
		 */
		private static final String READ_COMPLETE_INFO = "Read complete.";
		private static final String COUN_BOOKS_INFO = "Count books = ";
		private static final String FILE_NOT_FOUND_INFO = "File with books not found.";
		private static final String READ_FILE_ERROR_INFO = "Error while read file: ";
		private static final String ENTER_FILE_NAME_INFO = "file name: ";
		
		@Override
		public void execute(String... params) {
			try {
				print(ENTER_FILE_NAME_INFO);
				String filePath = new File(".").getAbsolutePath() + "/" + getNextConsoleCommad();				
				
				BookParser bookParser = new BookParser(filePath);				
				List<Book> books = bookParser.read();
				
				BookCatalog bookCatalog = (BookCatalog) getCatalog();
				if (books != null && books.size() > 0) {
					bookCatalog.getItems().clear();
					Stream<Book> bookStream = books.stream();
					bookStream.forEach(book -> bookCatalog.add(book));
				}
				println(READ_COMPLETE_INFO);
				println(COUN_BOOKS_INFO + bookCatalog.getItems().size());			
				executeCommand(StartCommand.START_COMMAND);
			} catch (FileNotFoundException e) {
				println(FILE_NOT_FOUND_INFO);
				executeCommand(StartCommand.START_COMMAND);
			} catch (RSSParseException e) {
				println(READ_FILE_ERROR_INFO + e.getStackTrace());
				executeCommand(StartCommand.START_COMMAND);
			}
		}				
	}
	
	private class WriteGroupsCommand implements Command {

		protected static final String WRITE_BOOKS_COMMAND = "write";
		
		public WriteGroupsCommand() {}
		
		/**
		 * Console tool tips information
		 */		
		private static final String FILTER_NAME_INFO = "filter name: ";
		private static final String WRITE_FILE_ERROR_INFO = "Error while write files.";
		
		@SuppressWarnings("unchecked")
		@Override
		public void execute(String... params) {
			try {
				print(FILTER_NAME_INFO);
				
				String groupName = getNextConsoleCommad();
				String basePath = new File("").getAbsolutePath() + "/" + groupName;
				
				if (!Files.exists(Paths.get(basePath))) {
					Files.createDirectories(Paths.get(basePath));
				}
				
				Map<String, ? extends List<? extends CatalogItem>> items = getCatalog().getGroups(groupName);
				
				if (items.size() > 0) {
					for(Entry<String, ? extends List<? extends CatalogItem>> item : items.entrySet()){
						
						String booksFolderPath = basePath + "/" + item.getKey();
						String booksFilePath = booksFolderPath + "/books.xml";
						
						if (!Files.exists(Paths.get(booksFolderPath))) {
							Files.createDirectories(Paths.get(booksFolderPath));
						}
						
						if (!Files.exists(Paths.get(booksFilePath))) {
							Files.createFile(Paths.get(booksFilePath));
						}
						
						BookWriter bookWriter = new BookWriter(booksFilePath);
						bookWriter.write((List<Book>) item.getValue());					
			        } 				
				}	
				executeCommand(StartCommand.START_COMMAND);
			} catch (RSSWriteException | IOException e) {
				println(WRITE_FILE_ERROR_INFO);
				executeCommand(StartCommand.START_COMMAND);
			}
		}
		
	}
}
