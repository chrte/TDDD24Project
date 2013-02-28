package com.TDDD24Project.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("project")
public interface ProjectService extends RemoteService {

	String addWidget(int userId, String widgetData, int widgetPosition);
	String removeWidget(int i);
	int authUser(String userName, String password);
	String[] getUserData(int userId);
	ArrayList<String> getUsersWidgetData(int userId);


}