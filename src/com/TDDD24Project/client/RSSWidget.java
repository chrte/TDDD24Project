package com.TDDD24Project.client;

import java.util.ArrayList;

import com.TDDD24Project.server.RSSFeedParser;
import com.TDDD24Project.shared.FeedMessage;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;


public class RSSWidget extends Composite {

	
	int userId;
	MainPage parent;
	int position;
	
	protected ProjectServiceAsync projectSvc = GWT.create(ProjectService.class);
	
	
	public RSSWidget(MainPage parent, int userId, int position, String url){
		this.parent=parent;
		this.position = position;
		this.userId=userId;
		addRSS(url);				
	}
	
	
	
	private void addRSS(final String url) {
		
		final AbsolutePanel absolutePanel = new AbsolutePanel();
		
		AsyncCallback<ArrayList<FeedMessage>> callback = new AsyncCallback<ArrayList<FeedMessage>>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");
			}
			@Override
			public void onSuccess(ArrayList<FeedMessage> message) {
				
				for(int i=0; i<3;i++){
					String link = message.get(i).getLink();
					String title = message.get(i).getTitle();
			
				Anchor anchor = new Anchor(title, link); 

				absolutePanel.add(anchor); 
				}
								
				
			}
			
		};
		
		
		
		projectSvc.readRSS(url, callback);
		
//		
//		RSSFeedParser parser = new RSSFeedParser(url);
//	    Feed feed = parser.readFeed();	   
	   
//	    for (FeedMessage message : feed.getMessages() ) {
//	    	      System.out.println(message.getLink());	      
//	     }
		
		
		
		
	    
//	    FeedMessage message = (FeedMessage) feed.getMessages();
//		String link = message.getLink();
//		String title = message.getTitle();
//		
//		
//		Anchor anchor = new Anchor(title, link); 
//		
//
//		absolutePanel.add(anchor); 
//		
		initWidget(absolutePanel);
		
	}
	
	
}
