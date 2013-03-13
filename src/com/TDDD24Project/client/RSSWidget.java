package com.TDDD24Project.client;

import java.util.ArrayList;

import com.TDDD24Project.shared.FeedMessage;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Anchor;


public class RSSWidget extends SuperWidget{
	

	
	
	public RSSWidget(MainPage parent, int userId, int position, String url){
		this.parent=parent;
		this.position = position;
		this.userId=userId;
		addRSS(url);	
		setup();
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
		
		initWidget(absolutePanel);
		
	}
	
	public void addRSSToDatabase(String link, int userId){
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");
			}
			@Override
			public void onSuccess(String result) {
				System.out.println("success");				
				
			}
			
		};
		parent.projectSvc.addWidget(userId, link, position,"RSS",parent.positionToColumn(position), parent.positionToRow(position), callback);
		
	}
		 
}
