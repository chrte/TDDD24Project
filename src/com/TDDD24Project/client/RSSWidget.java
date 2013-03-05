package com.TDDD24Project.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;


public class RSSWidget extends Composite {

	
	int userId;
	MainPage parent;
	int position;
	
	
	public RSSWidget(MainPage parent, int userId, int position, String url){
		this.parent=parent;
		this.position = position;
		this.userId=userId;
		addRSS(url);				
	}
	
	
	
	private void addRSS(final String url) {
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		
		
		
		
		RSSFeedParser parser = new RSSFeedParser(url);
	    Feed feed = parser.readFeed();	   
	   
//	    for (FeedMessage message : feed.getMessages() ) {
//	    	      System.out.println(message.getLink());	      
//	     }
	    
	    FeedMessage message = (FeedMessage) feed.getMessages();
		String link = message.getLink();
		String title = message.getTitle();
		
		
		Anchor anchor = new Anchor(title, link); 
		

		absolutePanel.add(anchor); 
		
		initWidget(absolutePanel);
		
	}
	
	
}
