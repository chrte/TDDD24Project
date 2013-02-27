package com.TDDD24Project.client;

import com.google.gwt.aria.client.WidgetRole;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * From google tutorial
 * Entry point classes define <code>onModuleLoad()</code>.
 */



public class LoginManager extends Composite {
	private TDDD24Project parent;
	public LoginManager(TDDD24Project parent) {
//		RootPanel rootPanel = RootPanel.get();
		this.parent=parent;
		HorizontalPanel horizontalPanel = new HorizontalPanel();
//		rootPanel.add(horizontalPanel, 10, 10);
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
