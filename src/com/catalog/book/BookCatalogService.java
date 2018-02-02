package com.catalog.book;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Stream;

import com.catalog.service.CatalogService;
import com.rss.parse.RSSParsingException;

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
	}		

	private class ReadBooksCommand implements Command {

		protected static final String READ_BOOKS_COMMAND = "read";
		
		private static final String BOOKS_FILE_PATH = "D:/mathematics.xml";		
		
		/**
		 * Console tool tips information
		 */
		private static final String READ_COMPLETE_INFO = "Read complete.";
		private static final String COUN_BOOKS_INFO = "Count books = ";
		private static final String FILE_NOT_FOUND_INFO = "File with books not found by path: ";
		private static final String READ_FILE_ERROR_INFO = "Error while read file: ";
		
		@Override
		public void execute(String... params) {
			try {
				
				BookParser bookParser = new BookParser(BOOKS_FILE_PATH);				
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
				println(FILE_NOT_FOUND_INFO + e.getMessage());
			} catch (RSSParsingException e) {
				println(READ_FILE_ERROR_INFO + e.getStackTrace());
			}
		}				
	}
}
