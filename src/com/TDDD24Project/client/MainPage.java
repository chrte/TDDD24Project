package com.TDDD24Project.client;




import java.util.ArrayList;

import com.TDDD24Project.shared.WidgetInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;




/**
 * Creates the main page which is displayed after login, with all the users data
 */
public class MainPage extends Composite {

	//Defining globals	

	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel widgetsPanel = new HorizontalPanel();

	private int userId;	
	private ArrayList<DroppablePanel> droppablePanels = new ArrayList<DroppablePanel>();
	protected ProjectServiceAsync projectSvc = GWT.create(ProjectService.class);


	/**
	 * Constructor
	 * @param userId - the users unique id
	 */
	public MainPage(int userId) {

		this.userId=userId;
		LoadStandardView();
		LoadUserData();
		initWidget(mainPanel);

	}

	/**
	 * Loads the users personal data from the database
	 */

	private void LoadUserData() {

		AsyncCallback<ArrayList<WidgetInfo>> callback = new AsyncCallback<ArrayList<WidgetInfo>>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");				
			}

			@Override
			public void onSuccess(ArrayList<WidgetInfo> result) {
				AddDataFromDatabase(result);
			}
		};

		projectSvc.getUsersWidgetData(userId, callback);
	}

	/**
	 * Adds the users data to the main page
	 * @param result - the user data extracted from the database
	 */
	private void AddDataFromDatabase(ArrayList<WidgetInfo> result) {	
		for(int i=0; i<result.size(); i++){
			String widgetType = result.get(i).getWidgetType();
			if(widgetType.equals("link")){

				addLinkWidgetAlreadyInDatabase(positionToIndex(result.get(i).getPosition()), result.get(i).getWidgetData());
			}
			else if(widgetType.equals("RSS")){

				addRSSWidgetAlreadyInDatabase(positionToIndex(result.get(i).getPosition()), result.get(i).getWidgetData());
			}
			else{	//For future implementations
				addLinkWidgetAlreadyInDatabase(positionToIndex(result.get(i).getPosition()), result.get(i).getWidgetData());
			}
		}
	}

	/**
	 * Adds a new link widget
	 * @param index - the index of widget where it should be added
	 * @param url - the URL of the link
	 */

	protected void addLinkWidget(int index, String url){ 

		final int position = indexToPosition(index);
		LinkWidget linkWidget = new LinkWidget(this, userId, position, url, positionToColumn(position), positionToRow(position));
		linkWidget.addLinkToDatabase(url, userId);	
		droppablePanels.get(positionToColumn(position)-1).setWidget(linkWidget, positionToRow(position)-1);
	}

	/**
	 * Adds a link widget to the main page which is already in the database
	 * @param index - the index of widget where it should be added
	 * @param url - the URL of the link
	 */

	protected void addLinkWidgetAlreadyInDatabase(int index, String url){

		final int position = indexToPosition(index);
		LinkWidget tempWidget = new LinkWidget(this, userId, position, url, positionToColumn(position), positionToRow(position));
		droppablePanels.get(positionToColumn(position)-1).setWidget(tempWidget, positionToRow(position)-1);
	}

	/**
	 * Adds a new RSS widget
	 * @param index - the index of widget where it should be added
	 * @param url - the URL of the RSS
	 */

	void addRssWidget(int index, String url){
		final int position = indexToPosition(index);			
		RSSWidget rssWidget = new RSSWidget(this, userId, position, url);
		rssWidget.addRSSToDatabase(url, userId);
		droppablePanels.get(positionToColumn(position)-1).setWidget(rssWidget, positionToRow(position)-1);
	}

	/**
	 * Adds a RSS widget to the main page which is already in the database
	 * @param index - the index of widget where it should be added
	 * @param url - the URL of the RSS
	 */

	protected void addRSSWidgetAlreadyInDatabase(int index, String url){

		final int position = indexToPosition(index);		
		Widget rss = new RSSWidget(this, userId, position, url);
		droppablePanels.get(positionToColumn(position)-1).setWidget(rss, positionToRow(position)-1);
	}

	/**
	 * Adds a new Empty Widget to the main page
	 * @param index - the index of widget where it should be added
	 */

	void addEmptyWidget(int index){
		final int position = indexToPosition(index);
		EmptyWidget emptyWidget = new EmptyWidget(this, userId, position, "");
		droppablePanels.get(positionToColumn(position)-1).setWidget(emptyWidget, positionToRow(position)-1);
	}
	
	/**
	 * Takes a widget index and converts it to the corresponding x,y-position
	 * @param index - the index of the widget
	 * @return - the x,y-position
	 */
	protected int indexToPosition(int index) {	

		int x = (int) Math.floor(index/3.0+1);
		int y = index%(3)+1;

		int position = x+10*y;

		return position;
	}

	/**
	 * Takes a widget x,y-position and converts it to the corresponding index
	 * @param index - the x,y-position of the widget
	 * @return - the index
	 */
	
	protected int positionToIndex(int position){ 

		int one = (int) Math.floor(position/10)-1;
		int ten = position-(one+1)*10;

		int tenIsWorth = (ten-1)*3;

		int index = tenIsWorth+one;

		return index;
	}
	
	
	protected int positionToColumn(int position){	//TODO: Is this really used, probably shouldn't be anyway!
		char temp = String.valueOf(position).charAt(0);
		return (int) temp - (int) '0';
	}
	protected int positionToRow(int position){
		char temp = String.valueOf(position).charAt(1);
		return (int) temp - (int) '0';
	}

	
	/**
	 * Loads the standard, empty view, which is then used if the user has nothing stored in the database
	 */
	private void LoadStandardView(){

		droppablePanels.add(new DroppablePanel(1));
		Widget emptyWidget = new EmptyWidget(this,userId,11,"");
		droppablePanels.get(0).add(emptyWidget);
		droppablePanels.get(0).setPixelSize(300,600); 

		emptyWidget = new EmptyWidget(this,userId,12,"");
		droppablePanels.get(0).add(emptyWidget);

		emptyWidget = new EmptyWidget(this,userId,13,"");
		droppablePanels.get(0).add(emptyWidget);
		widgetsPanel.add(droppablePanels.get(0));

		droppablePanels.add(new DroppablePanel(2));
		droppablePanels.get(1).setPixelSize(300,600);
		emptyWidget = new EmptyWidget(this,userId,21,"");
		droppablePanels.get(1).add(emptyWidget);
		emptyWidget = new EmptyWidget(this,userId,22,"");
		droppablePanels.get(1).add(emptyWidget);
		emptyWidget = new EmptyWidget(this,userId,23,"");
		droppablePanels.get(1).add(emptyWidget);
		widgetsPanel.add(droppablePanels.get(1));

		droppablePanels.add(new DroppablePanel(3));
		droppablePanels.get(2).setPixelSize(300,600);
		emptyWidget = new EmptyWidget(this,userId,31,"");
		droppablePanels.get(2).add(emptyWidget);
		emptyWidget = new EmptyWidget(this,userId,32,"");
		droppablePanels.get(2).add(emptyWidget);
		emptyWidget = new EmptyWidget(this,userId,33,"");
		droppablePanels.get(2).add(emptyWidget);
		widgetsPanel.add(droppablePanels.get(2));

		mainPanel.add(widgetsPanel);
		RootPanel.get("main").add(mainPanel);	

	}
}
