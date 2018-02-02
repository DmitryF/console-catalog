package com.catalog.book;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.rss.parse.RSSParser;

/**
 * @author DzmitryF
 *
 */
public class BookParser extends RSSParser<Book> {
	
	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String CATEGORY = "category";
	private static final String AUTHOR = "author";
	private static final String ITEM = "item";
	private static final String PUB_DATE = "pubDate";

	public BookParser(String filePath) throws FileNotFoundException {
		super(filePath);		
	}

	@Override
	protected List<Book> startRead(XMLEventReader eventReader) throws XMLStreamException {

		List<Book> books = null;
		
		boolean isFeedHeader = true;

        String description = "";
        String name = "";
        String author = "";
        String genre = "";
        int year = 0;
		
		while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                String localPart = event.asStartElement().getName().getLocalPart();
                
                if (ITEM.equals(localPart)) {
                	if (isFeedHeader) {
                        isFeedHeader = false;
                        books = new ArrayList<>();
                    }
                    event = eventReader.nextEvent();
                } else if (TITLE.equals(localPart)) {
                	name = getCharacterData(event, eventReader);
                } else if (DESCRIPTION.equals(localPart)) {
                	description = getCharacterData(event, eventReader);
                } else if (CATEGORY.equals(localPart)) {
                	genre = getCharacterData(event, eventReader);
                } else if (AUTHOR.equals(localPart)) {
                	author = getCharacterData(event, eventReader);
                } else if (PUB_DATE.equals(localPart)) {
                	LocalDate pubdate = getLocalDate(event, eventReader); 
                	if (pubdate != null) {
                		year = pubdate.getYear();
                	}
                }            
            } else if (event.isEndElement()) {
                if (event.asEndElement().getName().getLocalPart() == (ITEM)) {                	
                	Book book = new Book();
                	book.setId(books.size());
                	book.setName(name);
                	book.setAuthor(author);
                	book.setDescription(description);
                	book.setGenre(genre);
                	book.setYear(year);
                	
                	books.add(book);                  
                	
                    event = eventReader.nextEvent();
                    continue;
                }
            }
        }
		
		return books;
	}

}
