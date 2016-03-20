package com.jee.client.useradmin;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

public abstract class RightSideComponent extends Panel{
	private static final long serialVersionUID = 1655462157592081611L;

	public void changeContent(Component content){
		this.setContent(content);
	}
	protected abstract void init();
}
