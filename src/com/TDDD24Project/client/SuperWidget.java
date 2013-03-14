package com.TDDD24Project.client;

import static com.google.gwt.query.client.GQuery.$;


import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent;
import gwtquery.plugins.draggable.client.events.BeforeDragStartEvent.BeforeDragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent.DragStopEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
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
	String url;
	Boolean isBeingDragged=false;
	AbsolutePanel superPanel; 

	String getWidgetType(){
		return "";
	}


	protected void setup() {
		superPanel=new AbsolutePanel();

		// opacity of the portlet during the drag
		setDraggingOpacity(new Float(0.8));
		// zIndex of the portlet during the drag
		setDraggingZIndex(1000);
		// add position handler
		addBeforeDragHandler(HANDLER);
		addDragStopHandler(HANDLER);
		initWidget(superPanel);

	}
	protected void addButtons(){
		Button editButton = new Button("editButton");
		Button removeButton = new Button("X");
		removeButton.addStyleName("removeButton");
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(editButton);
		buttonPanel.add(removeButton);
		superPanel.add(buttonPanel);
		editButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {				

				editWidget(parent.positionToIndex(position), position);
			}	

		});

		removeButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {			

				removeWidgetPopUp(parent.positionToIndex(position), position);
			}


		});

	}


	private void removeWidgetPopUp(final int index, final int position) {
		final PopupPanel reallyRemove = new PopupPanel(false);
		reallyRemove.setStyleName("demo-popup");
		reallyRemove.setPixelSize(200,100);
		VerticalPanel popUpPanelContents = new VerticalPanel();
		reallyRemove.setTitle("Remove?");
		HTML message = new HTML("Are you sure you would like to remove this widget?");
		message.setStyleName("demo-PopUpPanel-message");
		
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");		
	

		yesButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				removeWidget(index,  position);	
				parent.addEmptyWidget(index);
				reallyRemove.hide();
			}
		});
		
		noButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				reallyRemove.hide();				
			}


		});
		
		SimplePanel holder = new SimplePanel();
		SimplePanel holder2 = new SimplePanel();
		holder.add(yesButton);
		holder2.add(noButton);
		holder.setStyleName("demo-PopUpPanel-footer");
		popUpPanelContents.add(message);
		popUpPanelContents.add(holder);
		popUpPanelContents.add(holder2);
		
		reallyRemove.setWidget(popUpPanelContents);
		reallyRemove.setGlassEnabled(true);
		reallyRemove.center();
	}	

	
	private void removeWidget(int index, int position) {
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");				
			}

			@Override
			public void onSuccess(String result) {
				System.out.println("Success");
			}				

		};

		projectSvc.removeWidget(userId, position, callback);
		
		
	}

	private void editWidget(final int index, final int widgetPosition) {
		final PopupPanel chooseWidget = new PopupPanel(false);	
		chooseWidget.setStyleName("demo-popup");
		chooseWidget.setPixelSize(200,100);
		VerticalPanel popUpPanelContents = new VerticalPanel();
		chooseWidget.setTitle("Add Widget");
		final TextBox widgetLink = new TextBox();
		widgetLink.setText(url);

		HTML message = new HTML("Insert link here:");
		message.setStyleName("demo-PopUpPanel-message");
		final RadioButton linkWidget = new RadioButton("widgetType", "Link");
		final RadioButton rssWidget = new RadioButton("widgetType", "RSS-feed");



		if(getWidgetType().equals("Link")){
			linkWidget.setValue(true);
		}
		else if(getWidgetType().equals("RSS")){
			rssWidget.setValue(true);
		}
		else{
			//Should never get here!
			System.out.println("Something's wrong");
			linkWidget.setValue(true);
		}

		Button editButton = new Button("Edit");
		editButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				String widgetData = widgetLink.getText();
				String widgetType;

				if(linkWidget.getValue()){
					System.out.println(index);
					widgetType = "link";		
					parent.addLinkWidgetAlreadyInDatabase(index,widgetData);

				}
				else{
					widgetType = "RSS";
					parent.addRSSWidgetAlreadyInDatabase(index,widgetData);

				}
				editWidgetInDatabase(widgetData, widgetPosition, widgetType);
				chooseWidget.hide();				
			}

		});
		Button cancelButton = new Button("Cancel");

		cancelButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				chooseWidget.hide();				
			}


		});
		SimplePanel holder = new SimplePanel();
		SimplePanel holder2 = new SimplePanel();
		holder.add(editButton);
		holder2.add(cancelButton);
		holder.setStyleName("demo-PopUpPanel-footer");
		popUpPanelContents.add(message);
		popUpPanelContents.add(widgetLink);
		popUpPanelContents.add(linkWidget);
		popUpPanelContents.add(rssWidget);
		popUpPanelContents.add(holder);
		popUpPanelContents.add(holder2);

		chooseWidget.setWidget(popUpPanelContents);
		chooseWidget.setGlassEnabled(true);
		chooseWidget.center();
	}


	public void editWidgetInDatabase(String widgetData, int widgetPosition, String widgetType){
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");				
			}

			@Override
			public void onSuccess(String result) {
				System.out.println("Success");
			}				

		};

		projectSvc.editWidget(userId, widgetData, widgetPosition, widgetType, callback);


	}

}

