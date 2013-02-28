package com.TDDD24Project.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProjectServiceAsync {
	void addWidget(int userId, String widgetData, int widgetPosition, AsyncCallback<String> callback);

	void removeWidget(int i, AsyncCallback<String> callback);
	void authUser(String userName, String password, AsyncCallback<Integer> callback);

	void getUserData(int userId, AsyncCallback<String[]> callback);
	
}
