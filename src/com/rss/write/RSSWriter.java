package com.rss.write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * @author DzmitryF
 *
 */
public abstract class RSSWriter<T> {

	private static final String CHANNEL_TAG = "channel";
	private static final String VERSION_VALUE = "2.0";
	private static final String VERSION_TAG = "version";
	private static final String RSS_TAG = "rss";
	
	private final Path filePath;
	
	public RSSWriter(String filePath) {		
		this.filePath = Paths.get(filePath);		
	}
	
	public void write(List<T> items) throws RSSWriteException {

        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

        XMLEventWriter eventWriter;
		try {
			eventWriter = outputFactory
			        .createXMLEventWriter(new FileOutputStream(filePath.toString()));
			XMLEventFactory eventFactory = XMLEventFactory.newInstance();
			XMLEvent end = eventFactory.createDTD("\n");
			
			StartDocument startDocument = eventFactory.createStartDocument();
			
			eventWriter.add(startDocument);
			
			eventWriter.add(end);
			
			StartElement rssStart = eventFactory.createStartElement("", "", RSS_TAG);
			eventWriter.add(rssStart);
			eventWriter.add(eventFactory.createAttribute(VERSION_TAG, VERSION_VALUE));
			eventWriter.add(end);
			
			eventWriter.add(eventFactory.createStartElement("", "", CHANNEL_TAG));
			eventWriter.add(end);
			
			startWrite(eventWriter, eventFactory, end, items);
			
			eventWriter.add(end);
			eventWriter.add(eventFactory.createEndElement("", "", CHANNEL_TAG));
			eventWriter.add(end);
			eventWriter.add(eventFactory.createEndElement("", "", RSS_TAG));
			
			eventWriter.add(end);
			
			eventWriter.add(eventFactory.createEndDocument());
			
			eventWriter.close();
		} catch (FileNotFoundException | XMLStreamException e) {
			throw new RSSWriteException(e.getMessage());
		}
	}	
	
	protected abstract void startWrite(XMLEventWriter eventWriter, XMLEventFactory eventFactory, XMLEvent end,
			List<T> items) throws XMLStreamException;

	protected void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");

		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(sElement);

		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);

		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);
	}
}
