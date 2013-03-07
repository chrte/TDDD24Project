package com.TDDD24Project.shared;

import java.io.Serializable;

public class WidgetInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String widgetData;
	private int position;
	private String widgetType;
	
	public WidgetInfo(){
		
	}
	
	public WidgetInfo(String widgetData, int position, String widgetType){
		this.widgetData = widgetData;
		this.position = position;
		this.widgetType = widgetType;
	}
	
	
	public String getWidgetData() {
		return widgetData;
	}
	public void setWidgetData(String widgetData) {
		this.widgetData = widgetData;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}

	public String getWidgetType() {
		return widgetType;
	}

	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}
	
	
	
	
	
}
