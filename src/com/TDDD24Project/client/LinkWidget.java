package com.TDDD24Project.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

public class LinkWidget extends Composite {

	int userId;
	MainPage parent;
	int position;
	public LinkWidget(MainPage parent, int userId, int position, String url){
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
		parent.projectSvc.addWidget(userId, link, position, callback);
		
	}
}
