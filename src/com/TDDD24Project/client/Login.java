package com.TDDD24Project.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Form google tutorial
 * @author google
 *
 */
public class Login extends Composite {
	private TextBox textBoxUsername;
	private TextBox textBoxPassword;
	LoginManager parent;

	public Login(final LoginManager parent) {
		this.parent=parent;
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);

		Label lblLoginToYour = new Label("Sign in to your account");
		lblLoginToYour.setStyleName("gwt-Label-Login");
		verticalPanel.add(lblLoginToYour);

		FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setWidth("345px");

		Label lblUsername = new Label("Username:");
		lblUsername.setStyleName("gwt-Label-Login");
		flexTable.setWidget(0, 0, lblUsername);

		textBoxUsername = new TextBox();
		flexTable.setWidget(0, 1, textBoxUsername);

		Label lblPassword = new Label("Password:");
		lblPassword.setStyleName("gwt-Label-Login");
		flexTable.setWidget(1, 0, lblPassword);

		textBoxPassword = new PasswordTextBox();
		flexTable.setWidget(1, 1, textBoxPassword);

		Button btnSignIn = new Button("Sign In");
		btnSignIn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doOnClick();
			}
		});
		textBoxPassword.addKeyPressHandler(new KeyPressHandler(){

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode()==KeyCodes.KEY_ENTER){
					doOnClick();
				}				
			}			
		});
		flexTable.setWidget(3, 1, btnSignIn);
	}

	private void doOnClick(){

		if (textBoxUsername.getText().length() == 0
				|| textBoxPassword.getText().length() == 0) {
			Window.alert("Username or password is empty."); 
		}
		else {
			ProjectServiceAsync projectSvc = GWT.create(ProjectService.class);
			AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {
				public void onFailure(Throwable caught) {
					System.out.println("failure");
				}
				@Override
				public void onSuccess(Integer result) {
					if(result == 0){
						Window.alert("Failed to log in");
					}
					else{
						
						parent.userLoggedIn(result);
					}
				}
			};

			projectSvc.authUser(textBoxUsername.getText(), textBoxPassword.getText(), callback);
		}	
	}
}
