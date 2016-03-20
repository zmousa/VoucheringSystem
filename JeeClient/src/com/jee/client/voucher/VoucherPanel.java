package com.jee.client.voucher;

import com.jee.client.JeeclientUI;
import com.jee.client.style.MainStyle;
import com.jee.client.useradmin.RightSideComponent;
import com.jee.model.voucher.Voucher;
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

public class VoucherPanel extends RightSideComponent{
	private static final long serialVersionUID = -3296862001446015439L;
	private Voucher voucher;
    private VerticalLayout layout;
    private Table dataTable;
	private Label lblCode;
	private Label lblCategory;
	private Label lblValue;
    
    public VoucherPanel(Voucher voucher)
    {
        this.voucher = voucher;
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
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();
        setSizeFull();
        
        this.setContent(layout);
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
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
        Label name = new Label("  " + voucher.getCode());
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
        content.setCaption(JeeclientUI.getCurrent().getText("userAdmin.user.voucher.details")); 
        VerticalLayout vLayout = new VerticalLayout();
        HorizontalLayout hLayout = new HorizontalLayout();
        GridLayout grid = new GridLayout(2, 4);
        
        // Code
        Label temp = new Label(JeeclientUI.getCurrent().getText("userAdmin.user.voucher.code") + ":"); 
        temp.setStyleName(ChameleonTheme.LABEL_H4);
        grid.addComponent(temp);
        grid.setComponentAlignment(temp, Alignment.MIDDLE_CENTER);
        lblCode = new Label(voucher.getCode());
        grid.addComponent(lblCode);
        
        // Category
        temp = new Label(JeeclientUI.getCurrent().getText("userAdmin.user.voucher.category") + ":"); 
        temp.setStyleName(ChameleonTheme.LABEL_H4);
        grid.addComponent(temp);
        grid.setComponentAlignment(temp, Alignment.MIDDLE_CENTER);
        lblCategory = new Label(voucher.getCategory() != null? voucher.getCategory().getId() : "");
        grid.addComponent(lblCategory);
        
        // Value
        temp = new Label(JeeclientUI.getCurrent().getText("userAdmin.user.voucher.value") + ":"); 
        temp.setStyleName(ChameleonTheme.LABEL_H4);
        grid.addComponent(temp);
        grid.setComponentAlignment(temp, Alignment.MIDDLE_CENTER);
        lblValue = new Label(voucher.getValue() + "");
        grid.addComponent(lblValue);
        
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
}
