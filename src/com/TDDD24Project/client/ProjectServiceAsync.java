package com.TDDD24Project.client;

import java.util.ArrayList;

import com.TDDD24Project.shared.FeedMessage;
import com.TDDD24Project.shared.WidgetInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProjectServiceAsync {
	void addWidget(int userId, String widgetData, int widgetPosition, String widgetType, AsyncCallback<String> callback);

	void removeWidget(int i, AsyncCallback<String> callback);
	void authUser(String userName, String password, AsyncCallback<Integer> callback);

	void getUserData(int userId, AsyncCallback<String[]> callback);
	void getUsersWidgetData(int userId, AsyncCallback<ArrayList<WidgetInfo>> callback);
	void readRSS(String url, AsyncCallback<ArrayList<FeedMessage>> callback);
	
}
