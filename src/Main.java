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
		
		/*try {
			BookParser bookParser = new BookParser("D:/mathematics.xml");
			List<Book> books = bookParser.read();
			for (Book book : books) {
				System.out.println(book.toString());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
