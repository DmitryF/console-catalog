package com.catalog.book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.catalog.service.CatalogService;

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
		
		private static final String BOOKS_FILE_PATH = "D:/books.txt";		
		
		/**
		 * Console tool tips information
		 */
		private static final String READ_COMPLETE_INFO = "Read complete.";
		private static final String COUN_BOOKS_INFO = "Count books = ";
		private static final String FILE_NOT_FOUND_INFO = "File with books not found by path: ";
		private static final String READ_FILE_ERROR_INFO = "Error while read file: ";
		
		@Override
		public void execute(String... params) {
			try (Stream<String> stream = Files.lines(Paths.get(BOOKS_FILE_PATH))) {
				
				BookCatalog bookCatalog = (BookCatalog) getCatalog();
				bookCatalog.getItems().clear();
				
				stream.map(bookAttributes -> readBook(bookAttributes))
						.forEach(book -> bookCatalog.add(book));				
				
				println(READ_COMPLETE_INFO);
				println(COUN_BOOKS_INFO + bookCatalog.getItems().size());
				
				executeCommand(StartCommand.START_COMMAND);
			} catch (NoSuchFileException e) {
				println(FILE_NOT_FOUND_INFO + e.getMessage());
			} catch (IOException e) {
				println(READ_FILE_ERROR_INFO + e.getStackTrace());
			}
		}
		
		private Book readBook(String line) {
			
			if (line != null && line.length() > 0) {
				try {
					Object[] attributes = line.split(",");
					Book book = new Book();
					book.setId(Integer.valueOf(attributes[0].toString()));
					book.setName(attributes[1].toString());
					book.setAuthor(attributes[2].toString());
					book.setGenre(attributes[3].toString());
					book.setDescription(attributes[4].toString());
					book.setYear(Integer.valueOf(attributes[5].toString()));
					return book;
				} catch (Exception e) {
					return null;
				}
			}
			return null;
		}
		
	}
}
