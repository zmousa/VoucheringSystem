package com.jee.client.voucher;

import com.jee.client.JeeclientUI;
import com.jee.client.style.MainStyle;
import com.jee.client.useradmin.RightSideComponent;
import com.jee.model.voucher.Distribution;
import com.jee.model.voucher.DistributionDetail;
import com.vaadin.data.Container;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ChameleonTheme;

public class DistributionPanel extends RightSideComponent{
	private static final long serialVersionUID = -3296862001446015439L;
	private Distribution distribution;
    private VerticalLayout layout;
    private Table dataTable;
	private Label lblId;
	private Label lblCreateDate;
	private Table detailsTable;
    
    public DistributionPanel(Distribution distribution)
    {
        this.distribution = distribution;
        init();
    }
    
	@Override
	protected void init() {
		layout = new VerticalLayout();
        Component compo = initHeader();
        layout.addComponent(compo);
        layout.setExpandRatio(compo, 5f);
        compo = initDetails();
        layout.addComponent(compo);
        layout.setExpandRatio(compo, 35f);
        compo = initMembers();
        layout.addComponent(compo);
        layout.setExpandRatio(compo, 55f);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();
        setSizeFull();
        
        this.setContent(layout);
	}

	public Distribution getDistribution() {
		return distribution;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	public void setLayout(VerticalLayout layout) {
		this.layout = layout;
	}

	public Table getMembersTable() {
		return dataTable;
	}

	public void setMembersTable(Table membersTable) {
		this.dataTable = membersTable;
	}
	
	private Component initHeader()
    {
        HorizontalLayout hLayout = new HorizontalLayout();
        Image image = new Image(null, new ThemeResource(MainStyle.ICON_USER));
        hLayout.addComponent(image);
        Label name = new Label("  " + distribution.getId());
        name.setStyleName(ChameleonTheme.LABEL_H2);
        hLayout.addComponent(name);
        hLayout.setComponentAlignment(name, Alignment.MIDDLE_LEFT);
        hLayout.setComponentAlignment(image, Alignment.MIDDLE_LEFT);
        
        GridLayout grid = new GridLayout(2, 1);
        grid.addComponent(hLayout);
        grid.setComponentAlignment(hLayout, Alignment.MIDDLE_LEFT);

        grid.setColumnExpandRatio(0, 50f);
        grid.setColumnExpandRatio(1, 50f);
        grid.setWidth("100%");
        
        return grid;
    }

    private Component initDetails()
    {
        Panel content = new Panel();
        content.setCaption(JeeclientUI.getCurrent().getText("userAdmin.distribution.details")); 
        VerticalLayout vLayout = new VerticalLayout();
        HorizontalLayout hLayout = new HorizontalLayout();
        GridLayout grid = new GridLayout(2, 4);
        
        Label temp = new Label(JeeclientUI.getCurrent().getText("userAdmin.distribution.id") + ":"); 
        temp.setStyleName(ChameleonTheme.LABEL_H4);
        grid.addComponent(temp);
        grid.setComponentAlignment(temp, Alignment.MIDDLE_CENTER);
        lblId = new Label(distribution.getId());
        grid.addComponent(lblId);
        
        temp = new Label(JeeclientUI.getCurrent().getText("userAdmin.distribution.createdate") + ":"); 
        temp.setStyleName(ChameleonTheme.LABEL_H4);
        grid.addComponent(temp);
        grid.setComponentAlignment(temp, Alignment.MIDDLE_CENTER);
        lblCreateDate = new Label(distribution.getCreateDate() != null? distribution.getCreateDate().toString() : " ");
        grid.addComponent(lblCreateDate);
        
        grid.setComponentAlignment(temp, Alignment.MIDDLE_CENTER);
        grid.setColumnExpandRatio(0, 20f);
        grid.setColumnExpandRatio(1, 40f);
        grid.setWidth("100%");
        hLayout.addComponent(grid);
        hLayout.setExpandRatio(grid, 75f);
        VerticalLayout vLayout2 = new VerticalLayout();
        vLayout2.setSpacing(true);
        
        hLayout.addComponent(vLayout2);
        hLayout.setExpandRatio(vLayout2, 25f);
        hLayout.setWidth("50%"); 
        vLayout.addComponent(hLayout);
        vLayout.setMargin(true);
        vLayout.setExpandRatio(hLayout, 90f);
        vLayout.setSizeFull();
        content.setSizeFull();
        content.setContent(vLayout);
        return content;
    }
    
    private Component initMembers()
    {
        final Panel content = new Panel();
        VerticalLayout vLayout = new VerticalLayout();
        HorizontalLayout hLayout = new HorizontalLayout();
        content.setCaption(JeeclientUI.getCurrent().getText("userAdmin.user.admin.distribution.details"));
        hLayout.setSizeFull();
        // add header layout to main layout
        vLayout.addComponent(hLayout);
        vLayout.setExpandRatio(hLayout, 10f);
        // the Table
        detailsTable = new Table();
        buildDataSource(detailsTable);
        detailsTable.setWidth("100%"); 
        detailsTable.setHeight("99%"); 

        vLayout.addComponent(detailsTable);
        vLayout.setExpandRatio(detailsTable, 80f);

        vLayout.setSizeFull();
        content.setSizeFull();
        content.setContent(vLayout);
        return content;
    }
    
    Container buildDataSource(Table detailsTable)
    {
    	detailsTable.addContainerProperty(JeeclientUI.getCurrent().getText("userAdmin.user.admin.distribution.category"), String.class, null);
    	detailsTable.addContainerProperty(JeeclientUI.getCurrent().getText("userAdmin.user.admin.distribution.amount"), String.class, null);

        if (this.getDistribution().getDistributionDetails() != null)
        {
            for (DistributionDetail disDetail : this.getDistribution().getDistributionDetails())
            {
            	Object obj[] = { disDetail.getCategory().getId(), disDetail.getAmount().toString() };
                detailsTable.addItem(obj, disDetail.getId());
            }
        }
        return null;
    }
}
