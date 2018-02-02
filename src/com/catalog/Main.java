package com.catalog;
import java.net.URISyntaxException;

import com.catalog.book.BookCatalog;
import com.catalog.book.BookCatalogService;

/**
 * @author DzmitryF
 *
 */
public class Main {

	public static void main(String[] args) throws URISyntaxException {
						
		BookCatalogService manager = new BookCatalogService(new BookCatalog());
		manager.start();
	}
}
