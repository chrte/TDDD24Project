package com.TDDD24Project.server;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.TDDD24Project.shared.Feed;
import com.TDDD24Project.shared.FeedMessage;


/**
 * A class for reading the content of an RSS-feed
 * @author source: http://www.vogella.com, modified by: chrte707 & hento581
 *
 */

public class RSSFeedParser {
  static final String TITLE = "title";
  static final String DESCRIPTION = "description";
  static final String CHANNEL = "channel";
  static final String LANGUAGE = "language";
  static final String COPYRIGHT = "copyright";
  static final String LINK = "link";
  static final String AUTHOR = "author";
  static final String ITEM = "item";
  static final String PUB_DATE = "pubDate";
  static final String GUID = "guid";

  final URL url;

  /**
   * Constructor for the class
   * @param feedUrl The url from which to read the RSS
   */
  public RSSFeedParser(String feedUrl) {
    try {
      this.url = new URL(feedUrl);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

/**
 * Method for reading the feed
 * @return The feed
 */
  public Feed readFeed() {
    Feed feed = null;
    
    try {

      boolean isFeedHeader = true;
      // Set header values initial to the empty string
      String description = "";
      String title = "";
      String link = "";
      String language = "";
      String copyright = "";
      String author = "";
      String pubdate = "";
      String guid = "";
      
      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
          
      // Setup a new eventReader
      InputStream in = read();
      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
//       Read the XML document
      while (eventReader.hasNext()) {

        XMLEvent event = eventReader.nextEvent();

        if (event.isStartElement()) {
          if (event.asStartElement().getName().getLocalPart() == (ITEM)) {
            if (isFeedHeader) {
              isFeedHeader = false;
              feed = new Feed(title, link, description, language,
                  copyright, pubdate);
            }
            event = eventReader.nextEvent();
            continue;
          }

          if (event.asStartElement().getName().getLocalPart() == (TITLE)) {
            event = eventReader.nextEvent();
            title = event.asCharacters().getData();
            continue;
          }
          if (event.asStartElement().getName().getLocalPart() == (DESCRIPTION)) {
            event = eventReader.nextEvent();
            description = event.asCharacters().getData();
            continue;
          }

          if (event.asStartElement().getName().getLocalPart() == (LINK)) {
            event = eventReader.nextEvent();
            link = event.asCharacters().getData();
            continue;
          }

          if (event.asStartElement().getName().getLocalPart() == (GUID)) {
            event = eventReader.nextEvent();
            guid = event.asCharacters().getData();
            continue;
          }
          if (event.asStartElement().getName().getLocalPart() == (LANGUAGE)) {
            event = eventReader.nextEvent();
            language = event.asCharacters().getData();
            continue;
          }
          if (event.asStartElement().getName().getLocalPart() == (AUTHOR)) {
            event = eventReader.nextEvent();
            author = event.asCharacters().getData();
            continue;
          }
          if (event.asStartElement().getName().getLocalPart() == (PUB_DATE)) {
            event = eventReader.nextEvent();
            pubdate = event.asCharacters().getData();
            continue;
          }
          if (event.asStartElement().getName().getLocalPart() == (COPYRIGHT)) {
            event = eventReader.nextEvent();
            copyright = event.asCharacters().getData();
            continue;
          }
        } else if (event.isEndElement()) {
          if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
            FeedMessage message = new FeedMessage();
            message.setAuthor(author);
            message.setDescription(description);
            message.setGuid(guid);
            message.setLink(link);
            message.setTitle(title);
            feed.getMessages().add(message);
            event = eventReader.nextEvent();
            continue;
          }
        }
      }
	    } catch (XMLStreamException e) {
	      throw new RuntimeException(e);
	    }
    return feed;

  }

  private InputStream read() {
    try {
      return url.openStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
} 