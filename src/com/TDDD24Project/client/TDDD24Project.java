package com.TDDD24Project.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TDDD24Project implements EntryPoint {

	private VerticalPanel mainPanel = new VerticalPanel();
	private LoginManager loginManager;
	private MainPage mainPage;
	@Override
	
	public void onModuleLoad() {

		RootPanel.get("main").add(mainPanel);
		loginManager = new LoginManager(this);
		mainPanel.add(loginManager);

		
	}

	public void userLoggedIn(int userId){
		mainPanel.remove(loginManager);
		mainPage = new MainPage();
		mainPanel.add(mainPage);
	}
}
