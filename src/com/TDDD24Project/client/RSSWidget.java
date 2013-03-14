package com.TDDD24Project.client;

import java.util.ArrayList;
import com.TDDD24Project.shared.FeedMessage;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * A class for a RSS Widget
 * @author Henrik Tosteberg - hento581, Christian Tennstedt - chrte707
 *
 */
public class RSSWidget extends SuperWidget{

	AbsolutePanel absolutePanel;
	
	/**
	 * Constructor for the RSS Widget 	
	 * @param parent
	 * @param userId
	 * @param position
	 * @param url
	 */
	public RSSWidget(MainPage parent, int userId, int position, String url){
		this.parent=parent;
		this.position = position;
		this.userId=userId;
		this.url=url;
		addRSS(url);	
		setup();
		addButtons();
		superPanel.add(absolutePanel);
	}

	/**
	 * Adds the RSS feed to the main page
	 * @param url
	 */
	
	private void addRSS(final String url) {

		absolutePanel = new AbsolutePanel();

		AsyncCallback<ArrayList<FeedMessage>> callback = new AsyncCallback<ArrayList<FeedMessage>>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");
			}
			@Override
			public void onSuccess(ArrayList<FeedMessage> message) {

				VerticalPanel rssPanel = new VerticalPanel();
				rssPanel.setPixelSize(160, 160);
				//				rssPanel.setBorderWidth(10);
				rssPanel.setStyleName("verticalpanel");
				for(int i=0; i<3;i++){
					String link = message.get(i).getLink();
					String title = message.get(i).getTitle();
					Anchor anchor = new Anchor(title, link); 
					anchor.addStyleName("anchor");
					rssPanel.add(anchor); 
				}
				absolutePanel.add(rssPanel); 
			}
		};
		projectSvc.readRSS(url, callback);
	}
	
	/**
	 * Adds a RSS-feed to the database using the widgets position
	 * @param link
	 * @param userId
	 */

	public void addRSSToDatabase(String link, int userId){
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");
			}
			@Override
			public void onSuccess(String result) {
				System.out.println("success");	
			}
		};
		parent.projectSvc.addWidget(userId, link, position,"RSS",parent.positionToColumn(position), parent.positionToRow(position), callback);
	}
	
	@Override
	public String getWidgetType(){
		return "RSS";
	}
	
}
