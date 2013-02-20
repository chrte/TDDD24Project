package com.TDDD24Project.client;



import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;








/**
 * Main
 */
public class TDDD24Project implements EntryPoint {
	
	//Defining globals	
	
	//private HelperFunctions helperFunctions = new  HelperFunctions();
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel widgetsPanel = new HorizontalPanel();
	private Image logo = new Image("images/logo.jpg");
	private Image bottomlogo = new Image("images/logo.jpg");
	private AbsolutePanel tempPanel;
	private ArrayList<AbsolutePanel> widgets = new ArrayList<AbsolutePanel>();
	private ArrayList<FlowPanel> flowPanels = new ArrayList<FlowPanel>();
	
	
	
	
	
	public void onModuleLoad() {
		
	
		RootPanel.get("main").setStyleName("main");	
		LoadStandardView();
		LoadUserData();
		
	}
	
	
	
	
	private void LoadUserData() {
		// TODO Auto-generated method stub
		
	}


	private void addWidget(){
		
	}
	
	
	private void removeWidget(int column, int row){
		
		
		
	}
	
	private Widget createEmptyWidget(){
		
		Image plusSign = new Image("images/plus_sign.png");
		plusSign.setPixelSize(160, 160);
		plusSign.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				
				ChooseNewWidget();
				System.out.println("You just clicked my buttons!");
				
				
			}

			
			
			
		});
		
		return plusSign;
		
	}

	private void ChooseNewWidget() {
		// TODO Auto-generated method stub
		
	}	
	
	private void LoadStandardView(){
		
//		widgetColumn1.getElement().getStyle().setProperty("margin", "10px");
		
	
		ArrayList<Image> plusSigns = new ArrayList<Image>();
		
		for(int i=0;i<9;i++){
			AbsolutePanel tempPanel = new AbsolutePanel();
			tempPanel.getElement().getStyle().setProperty("margin", "10px");
			widgets.add(tempPanel);
			Image plusSign = (Image) createEmptyWidget();
			plusSigns.add(plusSign);
			tempPanel = (AbsolutePanel) widgets.get(i);
			tempPanel.add(plusSigns.get(i));
		}
		
		
		for(int i=0;i<3;i++){
			flowPanels.add(new FlowPanel());
		}
		
		
		
		
		for(int i=0;i<3;i++){
			for(int j=0; j<3; j++ ){
				flowPanels.get(i).getElement().getStyle().setProperty("margin", "50px");
				flowPanels.get(i).add(widgets.get(j+3*i));					
			}
			widgetsPanel.add(flowPanels.get(i));
		}
		
		

		
		logo.setPixelSize(1024, 60);
		bottomlogo.setPixelSize(1024, 60);
		mainPanel.add(logo);
		mainPanel.add(widgetsPanel);
		mainPanel.add(bottomlogo);

		
		
		
		
		
		
		RootPanel.get("main").add(mainPanel);	
		
		
	}
}
