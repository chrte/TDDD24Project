package com.TDDD24Project.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * From google tutorial
 * A widgets that handles the login process
 * @author google, modifications made by : chrte707 & hento581
 *
 */
public class LoginManager extends Composite {
	private TDDD24Project parent;
	
	/**
	 * Constructor
	 * @param parent The parent class, the TDDD24Project class
	 */
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

	/**
	 * Method used when the user has been auth by the db, starts the mainpage
	 * @param i
	 */
	protected void userLoggedIn(int i) {
		parent.userLoggedIn(i);	
	}
}
