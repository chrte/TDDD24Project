package com.TDDD24Project.server;

//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URLConnection;
//
//import com.TDDD24Project.client.Feed;
//import com.TDDD24Project.client.FeedMessage;
//import com.google.gwt.http.client.URL;

//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;

//import javax.xml.stream.XMLEventReader;
//import javax.xml.stream.XMLInputFactory;
//import javax.xml.stream.XMLStreamException;
//import javax.xml.stream.events.XMLEvent;

//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//
//import com.google.gwt.xml.client.Document;
//import com.google.gwt.xml.client.Element;
//import com.google.gwt.xml.client.Node;
//import com.google.gwt.xml.client.NodeList;
//import com.google.gwt.xml.client.XMLParser;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import com.TDDD24Project.shared.Feed;
import com.TDDD24Project.shared.FeedMessage;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;



/*
 * Reads the xml-file - source: http://www.vogella.com
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

  public RSSFeedParser(String feedUrl) {
    try {
      this.url = new URL(feedUrl);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }


  public Feed readFeed() {
    Feed feed = null;
    
    URLConnection conn = null;
    
    try {
		conn = url.openConnection();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    
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
      
      
//      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();		
//		Document doc =  dBuilder.parse(conn.getInputStream());
//		
//		doc.getDocumentElement().normalize();
//		
//		
//			
//			NodeList titles = doc.getElementsByTagName("TITLE");
//			NodeList descriptions = doc.getElementsByTagName("DESCRIPTION");
//			NodeList links = doc.getElementsByTagName("LINK");
//			NodeList guids = doc.getElementsByTagName("GUID");
//			NodeList languages = doc.getElementsByTagName("LANGUAGE");
//			NodeList copyrights = doc.getElementsByTagName("COPYRIGHT");
//			NodeList pubdates = doc.getElementsByTagName("PUB_DATE");
//			NodeList authors = doc.getElementsByTagName("AUTHOR");
//			
//			feed = new Feed(title, link, description, language, copyright, pubdate);
//			
//			for(int i=0;i<3;i++){
//			
//				Node nTitles = titles.item(0);
//				Element eTitles = (Element) nTitles;				
//				title = eTitles.getAttribute("value");
//				
//				Node nDescriptions = descriptions.item(0);
//				Element eDescriptions = (Element) nDescriptions;				
//				title = eDescriptions.getAttribute("value");
//				
//				
//				 FeedMessage message = new FeedMessage();
//	            message.setAuthor(author);
//	            message.setDescription(description);
//	            message.setGuid(guid);
//	            message.setLink(link);
//	            message.setTitle(title);
//	            feed.getMessages().add(message);
//				
////				Node nTitles = titles.item(0);
////				Element eTitles = (Element) nTitles;				
////				title = eTitles.getAttribute("value");
////				
////				Node nTitles = titles.item(0);
////				Element eTitles = (Element) nTitles;				
////				title = eTitles.getAttribute("value");
////				
////				Node nTitles = titles.item(0);
////				Element eTitles = (Element) nTitles;				
////				title = eTitles.getAttribute("value");
////				
////				Node nTitles = titles.item(0);
////				Element eTitles = (Element) nTitles;				
////				title = eTitles.getAttribute("value");
////				
////				Node nTitles = titles.item(0);
////				Element eTitles = (Element) nTitles;				
////				title = eTitles.getAttribute("value");
//				
//				
//			
//		}
//		
//		
//		
//		
//    } catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (ParserConfigurationException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (SAXException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//    
//   
//    
//    
//	return feed;

		
//       First create a new XMLInputFactory
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