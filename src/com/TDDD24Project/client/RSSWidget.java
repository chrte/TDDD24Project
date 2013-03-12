package com.TDDD24Project.client;

import static com.google.gwt.query.client.GQuery.$;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent.BeforeDragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent.DragStopEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

import java.util.ArrayList;

import com.TDDD24Project.shared.FeedMessage;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;


public class RSSWidget extends DraggableWidget<Widget> {
	interface RSSWidgetUiBinder extends UiBinder<Widget, Portlet> {
	
	  }
	private static class DraggablePositionHandler implements
    BeforeDragStartEventHandler, DragStopEventHandler {

  /**
   * before that the drag operation starts, we will "visually" detach the draggable by setting
   * it css position to absolute. 
   */
  public void onBeforeDragStart(BeforeDragStartEvent event) {
     // "detach" visually the element of the parent
    $(event.getDraggable()).css("position", "absolute");

  }

  public void onDragStop(DragStopEvent event) {
    // "reattach" the element
    $(event.getDraggable()).css("position", "relative").css("top", null).css(
        "left", null);

  }
}
	// This handler is stateless
	private static DraggablePositionHandler HANDLER = new DraggablePositionHandler();
	private static RSSWidgetUiBinder uiBinder = GWT.create(RSSWidgetUiBinder.class);
	
	int userId;
	MainPage parent;
	int position;
	
	protected ProjectServiceAsync projectSvc = GWT.create(ProjectService.class);
	
	
	public RSSWidget(MainPage parent, int userId, int position, String url){
		this.parent=parent;
		this.position = position;
		this.userId=userId;
		addRSS(url);	
		setup();
	}
	
	
	
	private void addRSS(final String url) {
		
		final AbsolutePanel absolutePanel = new AbsolutePanel();
		
		AsyncCallback<ArrayList<FeedMessage>> callback = new AsyncCallback<ArrayList<FeedMessage>>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");
			}
			@Override
			public void onSuccess(ArrayList<FeedMessage> message) {
				
				for(int i=0; i<3;i++){
					String link = message.get(i).getLink();
					String title = message.get(i).getTitle();
			
				Anchor anchor = new Anchor(title, link); 

				absolutePanel.add(anchor); 
				}
								
				
			}
			
		};
		
		
		
		projectSvc.readRSS(url, callback);
		
		initWidget(absolutePanel);
		
	}
	
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
		parent.projectSvc.addWidget(userId, link, position,"RSS", callback);
		
	}
	
	 private void setup() {
		    // opacity of the portlet during the drag
		    setDraggingOpacity(new Float(0.8));
		    // zIndex of the portlet during the drag
		    setDraggingZIndex(1000);
		    // add position handler
		    addBeforeDragHandler(HANDLER);
		    addDragStopHandler(HANDLER);

		  }	
}
