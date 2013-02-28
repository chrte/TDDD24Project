package com.TDDD24Project.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;

/**
 * Class for the user widget, i.e display name etc....
 * @author Christian
 *
 */
public class UserWidget extends Composite {

	private int userId;
	private String userName;

	/**
	 * Constructor, initiating the userWidget, for example fetching the username
	 * @param userId The userId
	 */
	public UserWidget(int userId){
		this.userId=userId;
		getUserDataFromDb();

	}
	private void getUserDataFromDb(){
		ProjectServiceAsync projectSvc = GWT.create(ProjectService.class);
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");
			}
			@Override
			public void onSuccess(String result) {
				userName=result;
			}

		};
		projectSvc.getUserData(userId, callback);

	}
}

