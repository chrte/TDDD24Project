package com.TDDD24Project.server;

import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import com.TDDD24Project.shared.WidgetInfo;

public class DatabaseHandler {


	private static final String IP ="chrte.dyndns.org"; 
	private static final String DATABASENAME ="TDDD24";
	private static final String WIDGETS = "widgets";
	private static final String USERTOWIDGET = "userToWidgets";
	private static final String WIDGETID = "widgetId";
	private static final String USERS = "users";
	private static final String USERID = "userId";
	private static final String USERNAME = "userName";
	private static final String USERPASSWORD = "password";
	private static final String USERIMAGE = "userImage";
	private static final String WIDGETDATA ="widgetData";	
	private static final String WIDGETPOSITION ="widgetPosition";
	private static final String USERNAMEFORDB="TDDD24";
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
					connection  = DriverManager.getConnection("jdbc:mysql://"+IP+"/"+DATABASENAME,USERNAMEFORDB,PASSWORD);
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
			stmt.executeUpdate("INSERT IGNORE INTO "+DATABASENAME+"."+WIDGETS+" VALUES (NULL,'"+userId+"','"+widgetData+"',"+widgetPosition+");");			
		} catch (SQLException e) {

			e.printStackTrace();
		}	
	}


	public String removeWidget(int widgetId){
		initiateConnection();
		java.sql.Statement stmt=null;
		try {
			stmt =connection.createStatement();

			String query = "DELETE FROM `"+DATABASENAME+"`.`"+WIDGETS+"` WHERE `"+WIDGETS+"`.`"+WIDGETID+"`='"+widgetId+"';";
			connection.prepareStatement(query);

			int statResult = stmt.executeUpdate(query);
			System.out.println(statResult+". query:" +query);




		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stmt=null;
		try {
			stmt =connection.createStatement();

			String query = "DELETE FROM `"+DATABASENAME+"`.`"+USERTOWIDGET+"` WHERE `"+USERTOWIDGET+"`.`"+WIDGETID+"`='"+widgetId+"';";
			connection.prepareStatement(query);

			int statResult = stmt.executeUpdate(query);
			System.out.println(statResult+". query:" +query);
			stmt.close();
			connection.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("");

	}

	public int authUser(String userName, String password) {

		initiateConnection();
		java.sql.Statement stmt = null;
		ResultSet rs=null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT "+USERID+" FROM " +DATABASENAME+"."+USERS+" WHERE "+USERNAME+"='"+userName+"' AND "+USERPASSWORD+"='"+password+"';");
			while (rs.next()){
				return rs.getInt(USERID);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return 0; 
	}

	public String getUserName(int userId) {

		initiateConnection();
		java.sql.Statement stmt = null;
		ResultSet rs=null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT "+USERNAME+" FROM " +DATABASENAME+"."+USERS+" WHERE "+USERID+"='"+userId+"';");
			while (rs.next()){
				return rs.getString(USERNAME);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return "";
	}

	public String getUserImage(int userId) {
		initiateConnection();
		java.sql.Statement stmt = null;
		ResultSet rs=null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT "+USERIMAGE+" FROM " +DATABASENAME+"."+USERS+" WHERE "+USERID+"='"+userId+"';");
			while (rs.next()){
				return rs.getString(USERIMAGE);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return "";
	}


	public ArrayList<WidgetInfo> getUsersWidgetData(int userId){	
		ArrayList<WidgetInfo> widgets = new ArrayList<WidgetInfo>();
		 
		initiateConnection();
		java.sql.Statement stmt = null;
		ResultSet rs=null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT * FROM " +DATABASENAME+"."+WIDGETS+" WHERE "+USERID+"='"+userId+"';");
			while (rs.next()){
				WidgetInfo widget = new WidgetInfo((rs.getString(WIDGETDATA)), (rs.getInt(WIDGETPOSITION)));
				widgets.add(widget);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
		
		
		
		
		return widgets;
	}


}
