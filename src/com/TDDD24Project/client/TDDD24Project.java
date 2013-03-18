package com.TDDD24Project.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The main class, starting the project - TDDD24 CERTIFIED!!!!!
 * @author Henrik Tosteberg - hento581, Christian Tennstedt - chrte707 
 *
 */
public class TDDD24Project implements EntryPoint {

	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel headerPanel = new HorizontalPanel();
	private LoginManager loginManager;
	private MainPage mainPage;
	private Image logo = new Image("images/logo.jpg");
	
	@Override	
	public void onModuleLoad() {
		RootPanel.get("main").setStyleName("main");
		RootPanel.get("main").add(mainPanel);
		loginManager = new LoginManager(this);
		mainPanel.add(loginManager);		
	}
	
	/**
	 * Creates the main page when the user logs in
	 * @param userId
	 */

	public void userLoggedIn(int userId){
		mainPanel.remove(loginManager);
		UserWidget userWidget = new UserWidget(userId);
		logo.setPixelSize(700, 120);
		HorizontalPanel strut  = new HorizontalPanel();
		strut.setPixelSize(10, 120);
		headerPanel.add(logo);		
		headerPanel.add(strut);
		headerPanel.add(userWidget);	
		mainPanel.add(headerPanel);
		mainPage = new MainPage(userId);
		mainPanel.add(mainPage);
	}
}
