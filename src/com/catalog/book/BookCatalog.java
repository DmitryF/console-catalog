package com.catalog.book;

import com.catalog.base.AbstractCatalog;

/**
 * @author DzmitryF
 *
 */
public class BookCatalog extends AbstractCatalog<Book> {
	
	public void addBook(String name, String author, String genre, String description, int year) {
		
		Book book = new Book();
		book.setId(getItems().size());
		book.setName(name);
		book.setAuthor(author);
		book.setGenre(genre);
		book.setDescription(description);
		book.setYear(year);
		add(book);
	}
}
