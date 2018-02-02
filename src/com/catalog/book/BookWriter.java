package com.catalog.book;

import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.rss.write.RSSWriter;

/**
 * @author DzmitryF
 *
 */
public class BookWriter extends RSSWriter<Book> {

	private static final String YEAR_TAG = "year";
	private static final String GENRE_TAG = "genre";
	private static final String AUTHOR_TAG = "author";
	private static final String DESCRIPTION_TAG = "description";
	private static final String NAME_TAG = "name";
	private static final String ITEM_TAG = "item";

	public BookWriter(String filePath) {
		super(filePath);
	}

	@Override
	protected void startWrite(XMLEventWriter eventWriter, XMLEventFactory eventFactory, XMLEvent end, List<Book> items)
			throws XMLStreamException {

		for (Book book : items) {

			eventWriter.add(eventFactory.createStartElement("", "", ITEM_TAG));
			eventWriter.add(end);
			createNode(eventWriter, NAME_TAG, book.getName());
			createNode(eventWriter, DESCRIPTION_TAG, book.getDescription());
			createNode(eventWriter, AUTHOR_TAG, book.getAuthor());
			createNode(eventWriter, GENRE_TAG, book.getGenre());
			createNode(eventWriter, YEAR_TAG, String.valueOf(book.getYear()));
			eventWriter.add(end);
			eventWriter.add(eventFactory.createEndElement("", "", ITEM_TAG));
			eventWriter.add(end);
		}
	}
	
}
