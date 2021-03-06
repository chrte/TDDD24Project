package com.TDDD24Project.client;

import java.util.ArrayList;

import com.TDDD24Project.shared.FeedMessage;
import com.TDDD24Project.shared.WidgetInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("project")
public interface ProjectService extends RemoteService {

	String addWidget(int userId, String widgetData, int widgetPosition, String widgetType);
	String removeWidget(int userId, int position);
	int authUser(String userName, String password);
	String[] getUserData(int userId);
	ArrayList<WidgetInfo> getUsersWidgetData(int userId);
	ArrayList<FeedMessage> readRSS(String url);
	String swapWidgetPlaceInDatabase(int userId, int position1, int position2);
	String editWidget(int userId, String widgetData, int widgetPosition, String widgetType);
	String setUserImage(int userId, String imageSrc);
}