package com.TDDD24Project.client;

import com.google.gwt.user.client.rpc.RemoteService;


public interface ProjectService extends RemoteService {

	String addWidget(int userId, String widgetData, int widgetPosition);



}