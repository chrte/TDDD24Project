package com.TDDD24Project.client;



import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Main
 */
@SuppressWarnings("deprecation")
public class MainPage extends Composite {

	//Defining globals	

	//private HelperFunctions helperFunctions = new  HelperFunctions();
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel widgetsPanel = new HorizontalPanel();
	private Image logo = new Image("images/logo.jpg");
//	private Image background = new Image("images/background.jpg");
	private Image bottomlogo = new Image("images/logo.jpg");
	private int userId;

	private ArrayList<AbsolutePanel> widgets = new ArrayList<AbsolutePanel>();
	private ArrayList<FlowPanel> flowPanels = new ArrayList<FlowPanel>();

	protected ProjectServiceAsync projectSvc = GWT.create(ProjectService.class);

	public MainPage(int userId) {

		this.userId=userId;
//		RootPanel.get("main").setStyleName("main");	
		LoadStandardView();
		LoadUserData();
		initWidget(mainPanel);
		
	}

	private void LoadUserData() {
		// TODO: Implement this?
		
		sysOutAllWidgetData();

	}

	//TODO: Remove/change this test function!!
	
	private void sysOutAllWidgetData() {
		
		ArrayList<String> widgetData = null;
		
		AsyncCallback<ArrayList<String>> callback = new AsyncCallback<ArrayList<String>>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");
			}
			
			@Override
			public void onSuccess(ArrayList<String> result) {
				for(int i=0; i<result.size()+1; i++){
					System.out.println(result.get(i));
				}
				
			}
			
		};
		
		
		projectSvc.getUsersWidgetData(userId, callback);
		
		
	}

	private void deleteFromDatabase(int widgetId){
		
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");
			}
			@Override
			public void onSuccess(String result) {
				System.out.println("success");				
				
			}
			
		};
		
		
		projectSvc.removeWidget(12, callback);
		
	}
	
	


	protected void addWidget(int index, String url){
		
		AbsolutePanel tempPanel = (AbsolutePanel) widgets.get(index);
		tempPanel.clear();
		final int position = indexToPosition(index);
		tempPanel.add(new LinkWidget(this, userId, position, url));	
		
	}
	
	private void addAlertWidget(int index){
		AbsolutePanel tempPanel = (AbsolutePanel) widgets.get(index);
		tempPanel.clear();
		tempPanel.add(new AlertWidget());	
	}
	
	private void removeWidget(int column, int row, int userId){
		int removePos = column*10+row;
		
		//TODO: Fix

	}

	private Widget createEmptyWidget(final int index){
		


		Image plusSign = new Image("images/plus_sign.png");
		plusSign.setPixelSize(160, 160);


		plusSign.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {

				chooseNewWidget(index);
				
			}

		});

		return plusSign;

	}

	private int indexToPosition(int index) {	//TODO: Can this be done more general if we add more fields???
		
			
		int x = (int) Math.floor(index/3.0+1);
		int y = index%(3)+1;
		
		int position = x+10*y;
		
		return position;
	}
	
	
	private int positionToIndex(int position){ //TODO: test, make more general?
	
		
		int index = (int) (Math.floor(position/10.0)+position-Math.floor(position/10.0));
		
		
		return index;
	}

	private void chooseNewWidget(final int index) {
		final PopupPanel chooseWidget = new PopupPanel(false);	
		chooseWidget.setStyleName("demo-popup");
		chooseWidget.setPixelSize(200,100);
		VerticalPanel popUpPanelContents = new VerticalPanel();
		chooseWidget.setTitle("Add Widget");
		final TextBox widgetLink = new TextBox();
		HTML message = new HTML("Insert link here:");
		message.setStyleName("demo-PopUpPanel-message");
		ClickListener cancelListener = new ClickListener()
		{
			public void onClick(Widget sender)
			{
				chooseWidget.hide();
			}
		};
		ClickListener addListener = new ClickListener()
		{
			public void onClick(Widget sender)
			{

				String url = widgetLink.getText();
				addWidget(index, url);
//				addAlertWidget(index);
				chooseWidget.hide();
			}
		};
		Button addButton = new Button("Add");
		addButton.addClickListener(addListener); 		//TODO: deprecated, should use something else probably..
		Button cancelButton = new Button("Cancel");
		cancelButton.addClickListener(cancelListener);
		SimplePanel holder = new SimplePanel();
		SimplePanel holder2 = new SimplePanel();
		holder.add(addButton);
		holder2.add(cancelButton);
		holder.setStyleName("demo-PopUpPanel-footer");
		popUpPanelContents.add(message);
		popUpPanelContents.add(widgetLink);
		popUpPanelContents.add(holder);
		popUpPanelContents.add(holder2);
		
		chooseWidget.setWidget(popUpPanelContents);
		chooseWidget.setGlassEnabled(true);
		chooseWidget.center();
	}
		

	private void LoadStandardView(){

		//		widgetColumn1.getElement().getStyle().setProperty("margin", "10px");


		ArrayList<Image> plusSigns = new ArrayList<Image>();

		for(int i=0;i<9;i++){
			AbsolutePanel tempPanel = new AbsolutePanel();
			tempPanel.getElement().getStyle().setProperty("margin", "10px");
			Image plusSign = (Image) createEmptyWidget(i);
			plusSigns.add(plusSign);
			tempPanel.add(plusSigns.get(i));
			widgets.add(tempPanel);
			
			
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
