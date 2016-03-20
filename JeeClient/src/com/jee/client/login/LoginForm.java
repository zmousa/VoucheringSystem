package com.jee.client.login;


import java.io.File;

import com.jee.client.JeeclientUI;
import com.jee.client.MainView;
import com.jee.client.style.MainStyle;
import com.jee.client.style.MessageKeys;
import com.jee.model.userAdmin.JeeUser;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class LoginForm extends CustomComponent implements View,
		Button.ClickListener {

	public static final String NAME = "login";

	private final TextField userField;

	private final PasswordField passwordField;

	private final Button loginButton;
	
	private Label errorMsg;
	
	private CssLayout loginPanel;
	
	private JeeclientUI currentUi;
	public LoginForm() {
		setSizeFull();
		currentUi = JeeclientUI.getCurrent();
		userField = new TextField(currentUi.getText("labels.user"));
		userField.setHeight("24px");
		userField.setRequiredError(userField.getCaption() + currentUi.getText(MessageKeys.REQUIRED_FIELD));
		userField.setRequired(true);
		userField.setInvalidAllowed(false);

		// Create the password input field
		passwordField = new PasswordField(currentUi.getText("labels.password"));
		passwordField.setRequired(true);
		passwordField.setRequiredError(passwordField.getCaption() + currentUi.getText(MessageKeys.REQUIRED_FIELD));
		passwordField.setValue("");
		passwordField.setNullRepresentation("");

		// Create login button
		loginButton = new Button(currentUi.getText("buttons.login"), this);
		loginButton.setClickShortcut(KeyCode.ENTER, null);
		
		errorMsg = new Label("", ContentMode.HTML);
		errorMsg.setVisible(false);
		// Add both to a panel
		HorizontalLayout fields = new HorizontalLayout(userField, passwordField, loginButton);
		fields.setComponentAlignment(loginButton, Alignment.BOTTOM_CENTER);
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();
		
		
		loginPanel = new CssLayout();
		loginPanel.addStyleName(MainStyle.LOGIN_VIEW_PANEL);
		
		HorizontalLayout logoLayout = new HorizontalLayout();
		logoLayout.setSpacing(true);
		logoLayout.setMargin(true);
		Image logoImage = new Image(null, new ThemeResource( MainStyle.ICON_LOGO));
		logoImage.setHeight("150px");
		logoImage.setWidth("150px");
		logoLayout.addComponent(logoImage);
		logoLayout.setComponentAlignment(logoImage, Alignment.BOTTOM_RIGHT);
		logoLayout.setExpandRatio(logoImage, 0.5f);
		
		String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();

		
		File imagefile =new File(basepath +
                MainStyle.ICON_COMPANY_LOGO);
		if(imagefile.exists()){
			//Image as a file resource
			FileResource resource;
			resource = new FileResource(imagefile );
			//Show the image in the application
			Image companyLogo = new Image(null, resource);
			
			companyLogo.setHeight("150px");
			companyLogo.setWidth("150px");
			logoLayout.addComponent(companyLogo);
			logoLayout.setExpandRatio(companyLogo, 0.5f);
			logoLayout.setComponentAlignment(companyLogo, Alignment.BOTTOM_LEFT);
		}else
			logoLayout.setComponentAlignment(logoImage, Alignment.BOTTOM_CENTER);
		
		loginPanel.addComponent(logoLayout);
		
		VerticalLayout labels = new VerticalLayout();
		labels.setWidth("100%");
		labels.setMargin(true);
		labels.addStyleName(MainStyle.STYLE_LABELS);

		loginPanel.addComponent(labels);

		Label welcome = new Label(currentUi.getText(MessageKeys.WELCOME_MSG));
		welcome.setSizeUndefined();
		welcome.addStyleName("h4");
		labels.addComponent(welcome);
		labels.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

		Label title = new Label(currentUi.getText(MessageKeys.APP_TITLE));
//		title.setSizeUndefined();
		title.addStyleName("h2");
		title.addStyleName("light");
		labels.addComponent(title);
		labels.setComponentAlignment(title, Alignment.MIDDLE_LEFT);

		loginPanel.addComponent(fields);
		loginPanel.addComponent(errorMsg);
		
		
		// The view root layout
		Label label = new Label("<br><br><br><br><br><br>", ContentMode.HTML);
		VerticalLayout loginLayout = new VerticalLayout(label, loginPanel);
		
//		loginLayout.setExpandRatio(label, 0.01f);
		loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
//		loginLayout.setExpandRatio(loginPanel, 0.09f);
		loginLayout.addStyleName(MainStyle.LOGIN_LAYOUT);
		VerticalLayout mainL = new VerticalLayout(loginLayout);
//		mainL.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
		mainL.setSizeFull();
		mainL.addStyleName(MainStyle.LOGIN_VIEW_LAYOUT);
		setCompositionRoot(mainL);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// focus the username field when user arrives to the login view
		userField.focus();
	}

	//
	// Validator for validating the passwords
	//
	@SuppressWarnings("unused")
	private static final class PasswordValidator extends
			AbstractValidator<String> {

		public PasswordValidator() {
			super("The password provided is not valid");
		}

		@Override
		protected boolean isValidValue(String value) {
			//
			// Password must be at least 8 characters long and contain at least
			// one number
			//
			if (value != null
					&& (value.length() < 8 || !value.matches(".*\\d.*"))) {
				return false;
			}
			return true;
		}

		@Override
		public Class<String> getType() {
			return String.class;
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {

		//
		// Validate the fields using the navigator. By using validors for the
		// fields we reduce the amount of queries we have to use to the database
		// for wrongly entered passwords
		//
		if (!userField.isValid() || !passwordField.isValid()) {
			return;
		}

		String username = userField.getValue();
		String password = this.passwordField.getValue();

		//
		// Validate username and password with database here. For examples sake
		// I use a dummy username and password.
		//
		
		JeeUser authUser = currentUi.getUserAdminManager().login(username, password);
		if (authUser!=null) {
			// Store the current user in the service session
			VaadinService.getCurrentRequest().getWrappedSession().setAttribute(JeeclientUI.USER_NAME_ATTRIBUTE_NAME, authUser);
			currentUi.setLoggedInUser(authUser);
			// Navigate to main view
			currentUi.getNavigator().navigateTo(MainView.NAME);

		} else {
			errorMsg.setValue("<font color=RED>"+currentUi.getText("labels.invalid.username.or.password")+"</font>");
			errorMsg.setVisible(true);
			// Wrong password clear the password field and refocuses it
			this.passwordField.setValue(null);
			this.passwordField.focus();
		}
	}
}