package com.TDDD24Project.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * From google tutorial
 * Entry point classes define <code>onModuleLoad()</code>.
 */



public class LoginManager extends Composite {
	private TDDD24Project parent;
	public LoginManager(TDDD24Project parent) {

		this.parent=parent;
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSize("470px", "212px");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		
		
		Login login = new Login(this);
		horizontalPanel.add(login);
	
		horizontalPanel.setStyleName("login");
		this.initWidget(horizontalPanel);
		
		
		
	}

	public void userLoggedIn(int i) {
		parent.userLoggedIn(i);
		
	}
}
