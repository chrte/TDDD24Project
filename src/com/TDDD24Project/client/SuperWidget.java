package com.TDDD24Project.client;

import static com.google.gwt.query.client.GQuery.$;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent.BeforeDragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent.DragStopEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public abstract class SuperWidget extends DraggableWidget<Widget> {
	interface SuperUiBinder extends UiBinder<Widget, Portlet> {


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
	protected static DraggablePositionHandler HANDLER = new DraggablePositionHandler();
	protected static SuperUiBinder uiBinder = GWT.create(SuperUiBinder.class);
	protected ProjectServiceAsync projectSvc = GWT.create(ProjectService.class);

	@UiField
	DivElement content;
	@UiField
	DivElement header;
	int userId;
	MainPage parent;
	int position;
	int column;
	int row;
	int widgetId; //TODO, how to do with this?
	String url;
	Boolean isBeingDragged=false;
	
	

	protected void setup() {
		// opacity of the portlet during the drag
		setDraggingOpacity(new Float(0.8));
		// zIndex of the portlet during the drag
		setDraggingZIndex(1000);
		// add position handler
		addBeforeDragHandler(HANDLER);
		addDragStopHandler(HANDLER);

	}
	protected void getColumnAndRow(){
		AsyncCallback<int[]> callback = new AsyncCallback<int[]>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");
			}
			@Override
			public void onSuccess(int[] result) {
				column=result[0];
				row=result[1];
			}
			
		};
		
		
		
		projectSvc.getWidgetColumnAndRow(widgetId, callback);
		
	}
}

