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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EmptyWidget extends DraggableWidget<Widget> {
	interface EmptyWidgetUiBinder extends UiBinder<Widget, Portlet> {

		
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
private static EmptyWidgetUiBinder uiBinder = GWT.create(EmptyWidgetUiBinder.class);

@UiField
DivElement content;
@UiField
DivElement header;
	int userId;
	MainPage parent;
	int position;
	public EmptyWidget(final MainPage parent, int userId, final int position, String url){
//		 initWidget(uiBinder.createAndBindUi(this));
		
		 setup();
		this.parent=parent;
		this.position = position;
		this.userId=userId;
		Image plusSign = new Image("images/plus_sign.png");
		plusSign.setPixelSize(160, 160);
		plusSign.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {

				chooseNewWidget(parent.positionToIndex(position));

			}

		});

		initWidget(plusSign);

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
			parent.addWidget(index, url);
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