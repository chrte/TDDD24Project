package com.TDDD24Project.server;

import com.TDDD24Project.client.ProjectService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */

public class ProjectServiceImpl extends RemoteServiceServlet implements
		ProjectService {
	
	
	private static final long serialVersionUID = 1L;
	DatabaseHandler dbHandler = new DatabaseHandler();

	

	public String addWidget(int userId, String widgetData, int widgetPosition) {
		dbHandler.addWidget(userId, widgetData, widgetPosition);
		return "i'm awesome";
	}
	
	
	
	
	
	
	
	
	
	
	
	

	




}
