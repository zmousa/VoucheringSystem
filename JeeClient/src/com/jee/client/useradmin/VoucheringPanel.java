package com.jee.client.useradmin;

import com.jee.client.panel.MainPanel;
import com.jee.model.common.ToolBarEventEnum;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

public class VoucheringPanel extends MainPanel {
	private static final long serialVersionUID = -7222068439181540915L;
	private VerticalLayout vLayout;
	private HorizontalLayout hLayout; 
	private HorizontalSplitPanel hSpliter;
	private LeftSideComponent leftSide;
	private RightSideComponent rightSide;
	private ToolBarEventEnum eventType;
	
	public VoucheringPanel(ToolBarEventEnum eventType) {
		super();
		this.eventType = eventType;
	}
	
	@Override
	protected void initUi() {
		leftSide = new LeftSideComponent();
		hSpliter = new HorizontalSplitPanel();
		hSpliter.setFirstComponent(leftSide);
		hSpliter.setSecondComponent(rightSide);
		hSpliter.setImmediate(true);
		hSpliter.setSplitPosition(13, Unit.PERCENTAGE); 
		hSpliter.setSizeFull();
		hLayout = new HorizontalLayout();
		hLayout.addComponent(hSpliter);
		hLayout.setSizeFull();
		vLayout = new VerticalLayout();
		vLayout.addComponent(hLayout);
		vLayout.setExpandRatio(hLayout, 100f);
		vLayout.setSizeFull();
		this.setContent(vLayout);
		this.setSizeFull(); 
	}
	
	public void fillData() {
		this.getLeftSide().setEventType(this.eventType);
		this.getLeftSide().fillTable(this.eventType, null);
	}

	@Override
	public void onLoad() {
		
	}
	
	public LeftSideComponent getLeftSide() {
		return leftSide;
	}

	public void setLeftSide(LeftSideComponent leftSide) {
		this.leftSide = leftSide;
	}

	public RightSideComponent getRightSide() {
		return rightSide;
	}

	public void setRightSide(RightSideComponent rightSide) {
		this.rightSide = rightSide;
		hSpliter.setSecondComponent(rightSide);
	}

	public ToolBarEventEnum getEventType() {
		return eventType;
	}

	public void setEventType(ToolBarEventEnum eventType) {
		this.eventType = eventType;
	}

}