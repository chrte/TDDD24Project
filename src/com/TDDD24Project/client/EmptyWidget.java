package com.TDDD24Project.client;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public class EmptyWidget extends SuperWidget{


	@UiField
	DivElement content;
	@UiField
	DivElement header;
	

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
		final RadioButton linkWidget = new RadioButton("widgetType", "Link");
		final RadioButton rssWidget = new RadioButton("widgetType", "RSS-feed");
		linkWidget.setChecked(true);
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
				if(linkWidget.getValue()){
					parent.addWidget(index, url);
				}
				else{
					parent.addRssWidget(index, url);
				}
				
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
		popUpPanelContents.add(linkWidget);
		popUpPanelContents.add(rssWidget);
		popUpPanelContents.add(holder);
		popUpPanelContents.add(holder2);

		chooseWidget.setWidget(popUpPanelContents);
		chooseWidget.setGlassEnabled(true);
		chooseWidget.center();
	}

}
