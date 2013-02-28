package com.TDDD24Project.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.datepicker.client.DatePicker;


/**
 * A stub for having some kind of "alert" widget........
 * @author Christian
 *
 */
public class AlertWidget extends Composite {
	
	public AlertWidget(){
		AbsolutePanel panel = new AbsolutePanel();
		DatePicker picker = new DatePicker();
		panel.add(picker);
		
		
		initWidget(panel);
				
	}

}
