package com.TDDD24Project.client;

import static com.google.gwt.query.client.GQuery.$;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent.BeforeDragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent.DragStopEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

import com.TDDD24Project.client.Portlet.PortletUiBinder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class LinkWidget extends DraggableWidget<Widget> {
	interface LinkWidgetUiBinder extends UiBinder<Widget, Portlet> {

		
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
private static LinkWidgetUiBinder uiBinder = GWT.create(LinkWidgetUiBinder.class);

@UiField
DivElement content;
@UiField
DivElement header;
	int userId;
	MainPage parent;
	int position;
	public LinkWidget(MainPage parent, int userId, int position, String url){
//		 initWidget(uiBinder.createAndBindUi(this));
		
		 setup();
		this.parent=parent;
		this.position = position;
		this.userId=userId;
		addLink(url);
		
	}
	private void addLink(final String url) {
//		Image link = new Image("images/link.png");
		AbsolutePanel absolutePanel = new AbsolutePanel();
		Image link = new Image("http://www.google.com/s2/favicons?domain="+url);
		link.setPixelSize(160, 160);
		link.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			
				String httpUrl;
				
				if(!url.substring(0, Math.min(url.length(), 7)).equals("http://")){
					httpUrl = "http://" + url;
				}
				else{
					httpUrl = url;
				}
				Window.Location.assign(httpUrl);

			}

		});

		absolutePanel.add(link); //TODO: shouldn't add plussign eventually!!
		
		initWidget(absolutePanel);
	}
	
	public void addLinkToDatabase(String link, int userId){
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");
			}
			@Override
			public void onSuccess(String result) {
				System.out.println("success");				
				
			}
			
		};
		parent.projectSvc.addWidget(userId, link, position,"link", callback);
		
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
