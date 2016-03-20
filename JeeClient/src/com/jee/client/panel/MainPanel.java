package com.jee.client.panel;

import com.jee.api.VoucheringManager;
import com.jee.client.JeeclientUI;
import com.jee.model.userAdmin.JeeUser;
import com.vaadin.ui.Panel;

public abstract class MainPanel extends Panel {
	private static final long serialVersionUID = 5366487964545376472L;
	protected JeeUser logedInUser;
	protected JeeclientUI currentUi;
	protected VoucheringManager userAdminManager;
	
	public MainPanel(){
		super();
		currentUi = JeeclientUI.getCurrent();
		logedInUser = currentUi.getLoggedInUser();
		userAdminManager = currentUi.getUserAdminManager();
		initUi();
	}

	protected abstract void initUi();

	public abstract void onLoad();

}
