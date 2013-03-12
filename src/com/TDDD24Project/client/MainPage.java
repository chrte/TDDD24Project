package com.TDDD24Project.client;



import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableTolerance;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

import java.util.ArrayList;

import com.TDDD24Project.shared.WidgetInfo;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragHandlerAdapter;
import com.allen_sauer.gwt.dnd.client.drop.GridConstrainedDropController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.plugins.Widgets;
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

	private ArrayList<Widget> widgets = new ArrayList<Widget>();
	private ArrayList<DroppablePanel> droppablePanels = new ArrayList<DroppablePanel>();
	//	private ArrayList<FlowPanel> flowPanels = new ArrayList<FlowPanel>();

	protected ProjectServiceAsync projectSvc = GWT.create(ProjectService.class);

	public MainPage(int userId) {

		this.userId=userId;
		//		RootPanel.get("main").setStyleName("main");	
		LoadStandardView();
		LoadUserData();


		//		addRssWidget(8, "http://www.aftonbladet.se/rss.xml");

		initWidget(mainPanel);
	}



	private void LoadUserData() {

		addAllUserWidgets();

	}


	private void addAllUserWidgets() {



		AsyncCallback<ArrayList<WidgetInfo>> callback = new AsyncCallback<ArrayList<WidgetInfo>>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");				
			}

			@Override
			public void onSuccess(ArrayList<WidgetInfo> result) {

				AddDataFromDatabase(result);

			}

			private void AddDataFromDatabase(ArrayList<WidgetInfo> result) {	//TODO: Move
				for(int i=0; i<result.size(); i++){
					String widgetType = result.get(i).getWidgetType();
					if(widgetType.equals("link")){

						addLinkWidgetAlreadyInDatabase(positionToIndex(result.get(i).getPosition()), result.get(i).getWidgetData());
					}
					else if(widgetType.equals("RSS")){

						addRSSWidgetAlreadyInDatabase(positionToIndex(result.get(i).getPosition()), result.get(i).getWidgetData());
					}
					else{	//TODO: Something else???
						addLinkWidgetAlreadyInDatabase(positionToIndex(result.get(i).getPosition()), result.get(i).getWidgetData());
					}
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




	protected void addWidget(int index, String url){ 	//TODO: make more general (add support for RSS etc..)

		Widget tempPanel =  widgets.get(index);
//		tempPanel.clear();
		final int position = indexToPosition(index);
		LinkWidget linkWidget = new LinkWidget(this, userId, position, url);
		linkWidget.addLinkToDatabase(url, userId);
	
		tempPanel.setLayoutData(linkWidget);  //TODO, correct??
		


	}




	protected void addLinkWidgetAlreadyInDatabase(int index, String url){

		Widget tempPanel =  widgets.get(index);
//		tempPanel.remove();
		final int position = indexToPosition(index);
		LinkWidget tempWidget = new LinkWidget(this, userId, position, url);
		//		DraggableWidget<LinkWidget> tempDragWidget = new DraggableWidget<LinkWidget>(tempWidget);
		//		tempDragWidget.setDraggingCursor(Cursor.MOVE);
		//		tempDragWidget.setDraggingZIndex(100);

		droppablePanels.get ((int) Math.floor(index/3.0)).setLayoutData(tempWidget);
		tempPanel.setLayoutData(tempWidget);	


	}


	protected void addRSSWidgetAlreadyInDatabase(int index, String url){

		Widget tempPanel =  widgets.get(index);
		
		final int position = indexToPosition(index);
		Widget rss = new RSSWidget(this, userId, position, url);
		tempPanel.setLayoutData(rss);	 //TODO, correct??
		droppablePanels.get ((int) Math.floor(index/3.0)).setLayoutData(rss);

	}

	void addRssWidget(int index, String url){
		AbsolutePanel tempPanel = (AbsolutePanel) widgets.get(index);
		tempPanel.clear();
		final int position = indexToPosition(index);			
		RSSWidget rssWidget = new RSSWidget(this, userId, position, url);
		rssWidget.addRSSToDatabase(url, userId);
		tempPanel.add(rssWidget);


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

		int one = (int) Math.floor(position/10)-1;
		int ten = position-(one+1)*10;

		int tenIsWorth = (ten-1)*3;

		int index = tenIsWorth+one;

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


		ArrayList<DraggableWidget<Image>> plusSigns = new ArrayList<DraggableWidget<Image>>();

		int i = 0;
		//		AbsolutePanel tempPanel = new AbsolutePanel();
		//		tempPanel.getElement().getStyle().setProperty("margin", "10px");
		//
		//		final DragHandler demoDragHandler = new DragHandlerAdapter();			
				Image plusSign = (Image) createEmptyWidget(i);

		droppablePanels.add(new DroppablePanel());
		Widget portlet = new Portlet("testHeader"+i++, "testin");
		droppablePanels.get(0).add(portlet);
		widgets.add(portlet);
		
		
		portlet = new Portlet("testHeader"+i++, "testin");
		droppablePanels.get(0).add(portlet);
		widgets.add(portlet);
		
		
		portlet = new Portlet("testHeader"+i++, "testin");
		droppablePanels.get(0).add(portlet);
		widgets.add(portlet);
		widgetsPanel.add(droppablePanels.get(0));

		
		
		droppablePanels.add(new DroppablePanel());
		portlet = new Portlet("testHeader"+i++, "testin");
		droppablePanels.get(1).add(portlet);
		widgets.add(portlet);
		portlet = new Portlet("testHeader"+i++, "testin");
		droppablePanels.get(1).add(portlet);
		widgets.add(portlet);
		portlet = new Portlet("testHeader"+i++, "testin");
		droppablePanels.get(1).add(portlet);
		widgets.add(portlet);
		widgetsPanel.add(droppablePanels.get(1));
		

		droppablePanels.add(new DroppablePanel());
		portlet = new Portlet("testHeader"+i++, "testin");
		droppablePanels.get(2).add(portlet);
		widgets.add(portlet);
		portlet = new Portlet("testHeader"+i++, "testin");
		droppablePanels.get(2).add(portlet);
		widgets.add(portlet);
		portlet = new Portlet("testHeader"+i++, "testin");
		droppablePanels.get(2).add(portlet);
		widgets.add(portlet);
		widgetsPanel.add(droppablePanels.get(2));


		logo.setPixelSize(1024, 60);
		bottomlogo.setPixelSize(1024, 60);
		mainPanel.add(logo);
		mainPanel.add(widgetsPanel);
		mainPanel.add(bottomlogo);
		RootPanel.get("main").add(mainPanel);	

	}
}
