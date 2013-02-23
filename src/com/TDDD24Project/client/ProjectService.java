package com.TDDD24Project.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("project")
public interface ProjectService extends RemoteService {

	String addWidget(int userId, String widgetData, int widgetPosition);



}