package com.jee.client;

import com.jee.model.userAdmin.JeeUser;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;


	public class MainView extends CustomComponent implements View {

	    public static final String NAME = "";

	    private MainHeader header;
	    private MainBody body;
	    private MainFooter footer;

		private VerticalLayout mainLayout;

	    public MainView() {
	        
	    }

	    @Override
	    public void enter(ViewChangeEvent event) {
    		JeeUser username = JeeclientUI.getCurrent().getLoggedInUser();
    		header = new MainHeader(username);
    		body = new MainBody();
    		footer = new MainFooter();
    		mainLayout = new VerticalLayout(header, body, footer);
    		mainLayout.setExpandRatio(header, 8f);
    		mainLayout.setExpandRatio(body, 88f);
    		mainLayout.setExpandRatio(footer, 4f);
    		mainLayout.setSizeFull();
    		this.setSizeFull();
    		setCompositionRoot(mainLayout);
//	        // Get the user name from the session
//	        String username = String.valueOf(getSession().getAttribute("user"));
	    }
	}
