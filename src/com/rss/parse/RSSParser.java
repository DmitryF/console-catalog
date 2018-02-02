package com.rss.parse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

/**
 * @author DzmitryF
 *
 */
public abstract class RSSParser<T> {

	private final Path filePath;
	
	public RSSParser(String filePath) throws FileNotFoundException {
		this.filePath = Paths.get(filePath);
		if (!Files.exists(this.filePath)) {
			throw new FileNotFoundException();
		}
	}
	
	public List<T> read() throws RSSParseException{
		List<T> parseObject = null;
		try (InputStream in = getInputStream();) {           
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();            
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);            
            parseObject = startRead(eventReader);            
		} catch (XMLStreamException | IOException e) {
            throw new RSSParseException(e.getMessage());
        }
		return parseObject;
	}
	
	protected abstract List<T> startRead(XMLEventReader eventReader) throws XMLStreamException;
	
	protected String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }
	
	protected LocalDate getLocalDate(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
		LocalDate localDate = null;
		DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
    	try {                		
    		String characterData = getCharacterData(event, eventReader);
    		localDate = LocalDate.parse(characterData, formatter);    		
		} catch (DateTimeParseException e) {						
			e.printStackTrace();
		}	
    	return localDate;
	}
	
	private InputStream getInputStream() throws FileNotFoundException {		 
         return new FileInputStream(filePath.toString());        
	}
}

	
