package com.TDDD24Project.server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseHandler {


	private static final String IP ="chrte.dyndns.org"; 
	private static final String DATABASENAME ="TDDD24";
	private static final String WIDGETS = "widgets";
	private static final String USERTOWIDGET = "userToWidgets";
	private static final String WIDGETID = "widgetId";
	private static final String USERID = "userId";
	private static final String WIDGETDATA ="widgetData";	
	private static final String WIDGETPOSITION ="widgetPosition";
	private static final String USERNAME="TDDD24";
	private static final String PASSWORD="TDDD24";
	private Connection connection;


	public DatabaseHandler(){
		initiateConnection();

	}

	/**
	 * Initiates the connection to the database
	 */
	private void initiateConnection(){
		try {
			if (connection==null || connection.isClosed()){
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					connection  = DriverManager.getConnection("jdbc:mysql://"+IP+"/"+DATABASENAME,USERNAME,PASSWORD);
					connection.setAutoCommit(true);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * Gets the widget data (String) given a widgetId
	 * @param widgetId The widget which to get the data from
	 * @return The widget data
	 */
	public String getWidgetData(int widgetId){
		initiateConnection();
		java.sql.Statement stmt = null;
		ResultSet rs=null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT "+WIDGETDATA+" FROM " +DATABASENAME+"."+WIDGETS+" WHERE "+WIDGETID+"='"+widgetId+"';");
			while (rs.next()){
				return rs.getString(WIDGETDATA);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return ""; 
	}
	
	/**
	 * Gets the widget position (String) given a widgetId
	 * @param widgetId The widget which to get the position for
	 * @return The widget position
	 */
	
	
	public int getWidgetPosition(int widgetId){
		initiateConnection();
		java.sql.Statement stmt = null;
		ResultSet rs=null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT "+WIDGETDATA+" FROM " +DATABASENAME+"."+WIDGETS+" WHERE "+WIDGETPOSITION+"='"+widgetId+"';");
			while (rs.next()){
				return rs.getInt(WIDGETPOSITION);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return 0; 
	}
	
	/**
	 * Inserts a new Widget together with it's data and it's position into the database,
	 *  and connects it to it's user
	 * @param userId
	 * @param widgetData
	 * @param widgetPosition
	 */
	
	
	public void addWidget(int userId, String widgetData, int widgetPosition){
		initiateConnection();
		
		//Connects with UserId
		try {
			java.sql.Statement stmt=null;
			stmt =connection.createStatement();			
			stmt.executeUpdate("INSERT IGNORE INTO "+DATABASENAME+"."+USERTOWIDGET+" VALUES (NULL,"+userId+");");
			} catch (SQLException e) {

			e.printStackTrace();
		}
		
		//Adds data and position
		try {
			java.sql.Statement stmt=null;
			stmt =connection.createStatement();			
			stmt.executeUpdate("INSERT IGNORE INTO "+DATABASENAME+"."+WIDGETS+" VALUES ('SELECT LAST_INSERT_ID()','"+widgetData+"',"+widgetPosition+");");			
			} catch (SQLException e) {

			e.printStackTrace();
		}	
	}
	
	
	

}
