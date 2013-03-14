package com.TDDD24Project.server;

import java.util.ArrayList;

import com.TDDD24Project.shared.Feed;
import com.TDDD24Project.shared.FeedMessage;
import com.TDDD24Project.shared.WidgetInfo;
import com.TDDD24Project.client.ProjectService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The class that implements the ProjectService
 * @author chrte707, hento581
 *
 */

public class ProjectServiceImpl extends RemoteServiceServlet implements
ProjectService {


	private static final long serialVersionUID = 1L;
	DatabaseHandler dbHandler = new DatabaseHandler();
	
	
	/**
	 * adds a widget to the db
	 */
	@Override
	public String addWidget(int userId, String widgetData, int widgetPosition, String widgetType, int column, int row) {
		System.out.println("addWidget");
		dbHandler.addWidget(userId, widgetData, widgetPosition, widgetType, column, row);
		return "return";
	}
	
	/**
	 * Removes a widget from the db
	 */
	@Override
	public String removeWidget(int userId, int position) {
		dbHandler.removeWidget(userId, position);
		return "";
	}
	
	/**
	 * Auth the user agains the db
	 */
	@Override
	public int authUser(String userName, String password) {
		int userId = dbHandler.authUser(userName, password);
		return userId;

	}
	
	/**
	 * gets the user data
	 */
	@Override
	public String[] getUserData(int userId) {
		String[] returnArray = new String[2];
		returnArray[0] = dbHandler.getUserName(userId);
		returnArray[1] = dbHandler.getUserImage(userId);
		return returnArray;

	}
	
	/**
	 * gets the information about an users widget data
	 */
	@Override
	public ArrayList<WidgetInfo> getUsersWidgetData(int userId) {

		ArrayList<WidgetInfo> returnList = dbHandler.getUsersWidgetData(userId);
		return returnList;
	}
	
	/**
	 * Sets the widget column and row
	 */
	@Override
	public int setWidgetColumnAndRow(int widgetID, int column, int row){
		dbHandler.setWidgetColumnAndRow(widgetID, column, row);
		return 0;
	}
	
	/**
	 * gets the widget column and row in an arraylist
	 */
	@Override
	public int[] getWidgetColumnAndRow(int widgetID){
		int[] temp = new int[1];
		temp[0]= dbHandler.getWidgetColumn(widgetID);
		temp[1]= dbHandler.getWidgetRow(widgetID);
		return temp;
	}


	/**
	 * method for reading an RSS, given an URL
	 */
	public ArrayList<FeedMessage> readRSS(String url){
		RSSFeedParser parser = new RSSFeedParser(url);
		Feed feed = parser.readFeed();	   
		ArrayList<FeedMessage> message = feed.getMessages();
		return message;
	}
	
	/**
	 * Swap widgets in the DB
	 */
	public String swapWidgetPlaceInDatabase(int userId, int position1, int position2){
		dbHandler.swapWidgetPlace(userId, position1, position2);
		return "";
		
	}
	
	/**
	 * Edits a widget in the DB
	 */
	public String editWidget(int userId, String widgetData, int widgetPosition, String widgetType){
		dbHandler.editWidget(userId, widgetData, widgetPosition, widgetType);
		return "";
	}
	
	/**
	 * Sets the user image src in the DB
	 */
	@Override
	public String setUserImage(int userId, String imageSrc){
		dbHandler.setUserImange(userId, imageSrc);
		return "";
	}
	
	
	

}
