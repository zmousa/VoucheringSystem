package com.jee.client;

import com.jee.client.style.MainStyle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class MainFooter extends CustomComponent {
	
	HorizontalLayout layout;
	public MainFooter(){
		layout = new HorizontalLayout();
		
		layout.setSizeFull();
		this.setSizeFull();
		this.setStyleName(MainStyle.MAIN_FOOTER);
		this.setCompositionRoot(layout);
	}

}
