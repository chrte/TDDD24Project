package com.TDDD24Project.server;

import java.util.ArrayList;

import com.TDDD24Project.shared.Feed;
import com.TDDD24Project.shared.FeedMessage;
import com.TDDD24Project.shared.WidgetInfo;
import com.TDDD24Project.client.ProjectService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */

public class ProjectServiceImpl extends RemoteServiceServlet implements
		ProjectService {
	
	
	private static final long serialVersionUID = 1L;
	DatabaseHandler dbHandler = new DatabaseHandler();
	@Override
	

	public String addWidget(int userId, String widgetData, int widgetPosition, String widgetType) {
		System.out.println("addWidget");
		dbHandler.addWidget(userId, widgetData, widgetPosition, widgetType);
		return "return";
	}
	@Override
	public String removeWidget(int widgetId) {
		dbHandler.removeWidget(widgetId);
		return "";
	}
	@Override
	public int authUser(String userName, String password) {
		int userId = dbHandler.authUser(userName, password);
		return userId;
		
	}
	@Override
	public String[] getUserData(int userId) {
		String[] returnArray = new String[2];
		returnArray[0] = dbHandler.getUserName(userId);
		returnArray[1] = dbHandler.getUserImage(userId);
		return returnArray;
		 
	}
	@Override
	public ArrayList<WidgetInfo> getUsersWidgetData(int userId) {

		 ArrayList<WidgetInfo> returnList = dbHandler.getUsersWidgetData(userId);
		return returnList;
	}
	
	
	public ArrayList<FeedMessage> readRSS(String url){
		
		
		RSSFeedParser parser = new RSSFeedParser(url);
	    Feed feed = parser.readFeed();	   
	   
		
	 
	    
	    ArrayList<FeedMessage> message = feed.getMessages();
		
		
		
		return message;
	}
	
	
	
	
	
	
	
	
	
	
	

	




}
