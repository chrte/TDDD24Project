package com.TDDD24Project.client;




import java.util.ArrayList;

import com.TDDD24Project.shared.WidgetInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;




/**
 * Main
 */
public class MainPage extends Composite {

	//Defining globals	

	//private HelperFunctions helperFunctions = new  HelperFunctions();
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel widgetsPanel = new HorizontalPanel();
	private Image logo = new Image("images/logo.jpg");
	//	private Image background = new Image("images/background.jpg");
	private Image bottomlogo = new Image("images/logo.jpg");
	private int userId;

	
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

//		Widget tempPanel =  widgets.get(index);
//		tempPanel.clear();
		final int position = indexToPosition(index);
		LinkWidget linkWidget = new LinkWidget(this, userId, position, url, positionToColumn(position), positionToRow(position));
		linkWidget.addLinkToDatabase(url, userId);
	
		droppablePanels.get(positionToColumn(position)-1).setWidget(linkWidget, positionToRow(position)-1);
		


	}




	protected void addLinkWidgetAlreadyInDatabase(int index, String url){

//		Widget tempPanel =  widgets.get(index);
//		tempPanel.remove();
		final int position = indexToPosition(index);
		LinkWidget tempWidget = new LinkWidget(this, userId, position, url, positionToColumn(position), positionToRow(position));
		//		DraggableWidget<LinkWidget> tempDragWidget = new DraggableWidget<LinkWidget>(tempWidget);
		//		tempDragWidget.setDraggingCursor(Cursor.MOVE);
		//		tempDragWidget.setDraggingZIndex(100);

		droppablePanels.get(positionToColumn(position)-1).setWidget(tempWidget, positionToRow(position)-1);
//		tempPanel.setLayoutData(tempWidget);	


	}


	protected void addRSSWidgetAlreadyInDatabase(int index, String url){

//		Widget tempPanel =  widgets.get(index);
		
		final int position = indexToPosition(index);
		
		Widget rss = new RSSWidget(this, userId, position, url);
//		tempPanel.setLayoutData(rss);	 //TODO, correct??
		droppablePanels.get(positionToColumn(position)-1).setWidget(rss, positionToRow(position)-1);

	}

	void addRssWidget(int index, String url){
//		AbsolutePanel tempPanel = (AbsolutePanel) widgets.get(index);
//		tempPanel.clear();
		final int position = indexToPosition(index);			
		RSSWidget rssWidget = new RSSWidget(this, userId, position, url);
		rssWidget.addRSSToDatabase(url, userId);
		droppablePanels.get(positionToColumn(position)-1).setWidget(rssWidget, positionToRow(position)-1);


	}

	protected int indexToPosition(int index) {	//TODO: Can this be done more general if we add more fields???


		int x = (int) Math.floor(index/3.0+1);
		int y = index%(3)+1;

		int position = x+10*y;

		return position;
	}


	protected int positionToIndex(int position){ //TODO: test, make more general?

		int one = (int) Math.floor(position/10)-1;
		int ten = position-(one+1)*10;

		int tenIsWorth = (ten-1)*3;

		int index = tenIsWorth+one;

		return index;
	}
	protected int positionToColumn(int position){
		char temp = String.valueOf(position).charAt(0);
		return (int) temp - (int) '0';
	}
	protected int positionToRow(int position){
		char temp = String.valueOf(position).charAt(1);
		return (int) temp - (int) '0';
	}
	
	private void LoadStandardView(){



		droppablePanels.add(new DroppablePanel());
		Widget emptyWidget = new EmptyWidget(this,userId,11,"awesome");
		droppablePanels.get(0).add(emptyWidget);
		
		droppablePanels.add(new DroppablePanel());
		emptyWidget = new EmptyWidget(this,userId,12,"awesome");
		droppablePanels.get(0).add(emptyWidget);
		
		droppablePanels.add(new DroppablePanel());
		emptyWidget = new EmptyWidget(this,userId,13,"awesome");
		droppablePanels.get(0).add(emptyWidget);
		widgetsPanel.add(droppablePanels.get(0));

		
		
		droppablePanels.add(new DroppablePanel());
		emptyWidget = new EmptyWidget(this,userId,21,"awesome");
		droppablePanels.get(1).add(emptyWidget);
		emptyWidget = new EmptyWidget(this,userId,22,"awesome");
		droppablePanels.get(1).add(emptyWidget);
		emptyWidget = new EmptyWidget(this,userId,23,"awesome");
		droppablePanels.get(1).add(emptyWidget);
		widgetsPanel.add(droppablePanels.get(1));
		
		droppablePanels.add(new DroppablePanel());
		emptyWidget = new EmptyWidget(this,userId,31,"awesome");
		droppablePanels.get(2).add(emptyWidget);
		emptyWidget = new EmptyWidget(this,userId,32,"awesome");
		droppablePanels.get(2).add(emptyWidget);
		emptyWidget = new EmptyWidget(this,userId,33,"awesome");
		droppablePanels.get(2).add(emptyWidget);
		widgetsPanel.add(droppablePanels.get(2));


		logo.setPixelSize(1024, 60);
		bottomlogo.setPixelSize(1024, 60);
		mainPanel.add(logo);
		mainPanel.add(widgetsPanel);
		mainPanel.add(bottomlogo);
		RootPanel.get("main").add(mainPanel);	

	}
}
