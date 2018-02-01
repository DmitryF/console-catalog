package com.catalog.book;

import com.catalog.base.CatalogItem;

/**
 * @author DzmitryF
 *
 */
public class Book implements CatalogItem {

	public static final String NAME_BOOK_GROUP_FILTER = "name";
	public static final String AUTHOR_BOOK_GROUP_FILTER = "author";
	public static final String GENRE_BOOK_GROUP_FILTER = "genre";
	public static final String YEAR_BOOK_GROUP_FILTER = "year";
	
	private int id = -1;
	
	private String name = "";
	
	private String author = "";
	
	private String genre = "";
	
	private int year = 0;
	
	private String description = "";
	
	public Book() {
		
	}
	
	@Override
	public boolean hasAttribute(String attributeName) {
		return attributeName.equals(NAME_BOOK_GROUP_FILTER) ||
				attributeName.equals(AUTHOR_BOOK_GROUP_FILTER) ||
				attributeName.equals(GENRE_BOOK_GROUP_FILTER) ||
				attributeName.equals(YEAR_BOOK_GROUP_FILTER);
	}

	@Override
	public String getAttributeValue(String attributeName) {
		
		if (attributeName.equals(NAME_BOOK_GROUP_FILTER)) {
			return getName();
		} else if (attributeName.equals(AUTHOR_BOOK_GROUP_FILTER)) {
			return getAuthor();
		} else if (attributeName.equals(GENRE_BOOK_GROUP_FILTER)) {
			return getGenre();
		} else if (attributeName.equals(YEAR_BOOK_GROUP_FILTER)) {
			return String.valueOf(getYear());
		}
		
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("Id: ").append(String.valueOf(getId())).append("\n")
				.append("Name: ").append(String.valueOf(getName())).append("\n")
				.append("Author: ").append(String.valueOf(getAuthor())).append("\n")
				.append("Genre: ").append(String.valueOf(getGenre())).append("\n")
				.append("Year: ").append(String.valueOf(getYear()))
				.toString();
	}
}
