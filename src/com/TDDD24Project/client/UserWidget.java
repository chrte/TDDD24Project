package com.TDDD24Project.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * Class for the user widget, i.e display name etc....
 * @author Christian
 *
 */
public class UserWidget extends Composite {

	private int userId;
	private String userName;
	private Label userLabel;
	private String userImageSrc;
	private Image userImage;

	/**
	 * Constructor, initiating the userWidget, for example fetching the username
	 * @param userId The userId
	 */
	public UserWidget(int userId){
		this.userId=userId;
		initiateGraphic();
		getUserDataFromDb();
		

	}
	private void initiateGraphic() {
		final AbsolutePanel content = new AbsolutePanel();
		content.setStyleName("userWidget");
		userLabel = new Label();
		userLabel.setPixelSize(40, 40);
		userLabel.setStyleName("userName");
		content.add(userLabel);
		userImage = new Image();
		userImage.setPixelSize(75, 75);
		content.add(userImage);
		Button changeImageButton = new Button("ChangeImage");
		changeImageButton.addClickHandler( new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				UploadImage uploadImage = new UploadImage();
				content.add(uploadImage); //TODO: TEST				
			}
			
		});
		content.add(changeImageButton);
		initWidget(content);
		
	}
	private void getUserDataFromDb(){
		ProjectServiceAsync projectSvc = GWT.create(ProjectService.class);
		AsyncCallback<String[]> callback = new AsyncCallback<String[]>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure in the userWidget");
			}
			@Override
			public void onSuccess(String[] result) {
				userName=result[0];
				userImageSrc= result[1];
				updateGraphic();
				
			}
			

		};
		projectSvc.getUserData(userId, callback);

	}
	
	/**
	 * Method for updating the graphic when result is returned from the server
	 */
	private void updateGraphic() {
		userLabel.setText(userName);
		userImage.setUrl(userImageSrc);
		
	}
}

