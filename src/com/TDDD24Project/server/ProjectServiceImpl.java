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
	@Override
	

	public String addWidget(int userId, String widgetData, int widgetPosition) {
		System.out.println("addWidget");
		dbHandler.addWidget(userId, widgetData, widgetPosition);
		return "return";
	}
	@Override
	public String removeWidget(int widgetId) {
		dbHandler.removeWidget(widgetId);
		return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	

	




}
