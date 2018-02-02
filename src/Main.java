import java.io.FileNotFoundException;
import java.util.List;

import com.catalog.book.Book;
import com.catalog.book.BookCatalog;
import com.catalog.book.BookCatalogService;
import com.catalog.book.BookParser;
import com.rss.parse.RSSParser;

/**
 * @author DzmitryF
 *
 */
public class Main {

	public static void main(String[] args) {
						
		BookCatalogService manager = new BookCatalogService(new BookCatalog());
		manager.start();
	}
}
