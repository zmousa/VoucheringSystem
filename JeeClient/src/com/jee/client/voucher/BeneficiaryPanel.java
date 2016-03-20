package com.jee.client.voucher;

import java.util.List;

import com.jee.client.JeeclientUI;
import com.jee.client.style.MainStyle;
import com.jee.client.useradmin.RightSideComponent;
import com.jee.model.voucher.Beneficiary;
import com.jee.model.voucher.Participation;
import com.jee.model.voucher.ParticipationDetail;
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

public class BeneficiaryPanel extends RightSideComponent{
	private static final long serialVersionUID = -3296862001446015439L;
	private Beneficiary beneficiary;
    private VerticalLayout layout;
    private Table dataTable;
    private Table distTable;
    private Table particTable;
	private Label lblId;
	private Label lblName;
	private Label lblGender;
	private Label lblAge;
    
    public BeneficiaryPanel(Beneficiary beneficiary)
    {
        this.beneficiary = beneficiary;
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
        layout.setExpandRatio(compo, 25f);
        
        HorizontalLayout hLayout = new HorizontalLayout();
        compo = initDistribution();
        hLayout.addComponent(compo);
        hLayout.setExpandRatio(compo, 50f);
        compo = initParticipation();
        hLayout.addComponent(compo);
        hLayout.setExpandRatio(compo, 50f);
        hLayout.setWidth("100%");
        layout.addComponent(hLayout);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();
        setSizeFull();
        
        this.setContent(layout);
	}

	public Beneficiary getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
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
        Label Id = new Label("  " + beneficiary.getId());
        Id.setStyleName(ChameleonTheme.LABEL_H2);
        hLayout.addComponent(Id);
        hLayout.setComponentAlignment(Id, Alignment.MIDDLE_LEFT);
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
        content.setCaption(JeeclientUI.getCurrent().getText("userAdmin.user.beneficiary.details")); 
        VerticalLayout vLayout = new VerticalLayout();
        HorizontalLayout hLayout = new HorizontalLayout();
        GridLayout grid = new GridLayout(2, 4);
        
        // Id
        Label temp = new Label(JeeclientUI.getCurrent().getText("userAdmin.user.beneficiary.id") + ":"); 
        temp.setStyleName(ChameleonTheme.LABEL_H4);
        grid.addComponent(temp);
        grid.setComponentAlignment(temp, Alignment.MIDDLE_CENTER);
        lblId = new Label(beneficiary.getId());
        grid.addComponent(lblId);
        
        // Name
        temp = new Label(JeeclientUI.getCurrent().getText("userAdmin.user.beneficiary.name") + ":"); 
        temp.setStyleName(ChameleonTheme.LABEL_H4);
        grid.addComponent(temp);
        grid.setComponentAlignment(temp, Alignment.MIDDLE_CENTER);
        lblName = new Label(beneficiary.getName());
        grid.addComponent(lblName);
        
        // Gender
        temp = new Label(JeeclientUI.getCurrent().getText("userAdmin.user.beneficiary.gender") + ":"); 
        temp.setStyleName(ChameleonTheme.LABEL_H4);
        grid.addComponent(temp);
        grid.setComponentAlignment(temp, Alignment.MIDDLE_CENTER);
        lblGender = new Label(beneficiary.getGender());
        grid.addComponent(lblGender);
        
        // Age
        temp = new Label(JeeclientUI.getCurrent().getText("userAdmin.user.beneficiary.age") + ":"); 
        temp.setStyleName(ChameleonTheme.LABEL_H4);
        grid.addComponent(temp);
        grid.setComponentAlignment(temp, Alignment.MIDDLE_CENTER);
        lblAge = new Label(beneficiary.getAge() + "");
        grid.addComponent(lblAge);
        
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
    
    
    private Component initParticipation() {
    	final Panel content = new Panel();
        VerticalLayout vLayout = new VerticalLayout();
        HorizontalLayout hLayout = new HorizontalLayout();
        content.setCaption(JeeclientUI.getCurrent().getText("userAdmin.user.admin.beneficiary.distribution"));
        hLayout.setSizeFull();
        // add header layout to main layout
        vLayout.addComponent(hLayout);
        vLayout.setExpandRatio(hLayout, 10f);
        // the Table
        distTable = new Table();
        buildDistDataSource(distTable);
        distTable.setWidth("100%"); 
        distTable.setHeight("99%"); 

        vLayout.addComponent(distTable);
        vLayout.setExpandRatio(distTable, 40f);

        vLayout.setSizeFull();
        content.setSizeFull();
        content.setContent(vLayout);
        return content;
	}

	private void buildDistDataSource(Table distTable) {
		distTable.addContainerProperty(JeeclientUI.getCurrent().getText("userAdmin.user.admin.beneficiary.id"), String.class, null);

        if (this.getBeneficiary().getParticipations() != null)
        {
            for (Participation participation : this.getBeneficiary().getParticipations())
            {
            	Object obj[] = { participation.getDistribution().getId() };
            	distTable.addItem(obj, distTable.getId());
            }
        }
	}

	private Component initDistribution() {
		final Panel content = new Panel();
        VerticalLayout vLayout = new VerticalLayout();
        HorizontalLayout hLayout = new HorizontalLayout();
        content.setCaption(JeeclientUI.getCurrent().getText("userAdmin.user.admin.beneficiary.participation"));
        hLayout.setSizeFull();
        // add header layout to main layout
        vLayout.addComponent(hLayout);
        vLayout.setExpandRatio(hLayout, 10f);
        
        // Permission Table
        particTable = new Table();
        buildParticDataSource(particTable);
        particTable.setWidth("100%"); 
        particTable.setHeight("99%"); 

        vLayout.addComponent(particTable);
        vLayout.setExpandRatio(particTable, 40f);

        vLayout.setSizeFull();
        content.setSizeFull();
        content.setContent(vLayout);
        return content;
	}

	private void buildParticDataSource(Table particTable) {
		particTable.addContainerProperty(JeeclientUI.getCurrent().getText("userAdmin.user.admin.beneficiary.category"), String.class, null);
		particTable.addContainerProperty(JeeclientUI.getCurrent().getText("userAdmin.user.admin.beneficiary.amount"), String.class, null);
		List<ParticipationDetail> participationDetails = null;
		
        if (this.getBeneficiary().getParticipations() != null)
        {
            for (Participation participation : this.getBeneficiary().getParticipations())
            {
            	participationDetails = JeeclientUI.getCurrent().getUserAdminManager().getParticipationDetails(participation);
            	for (ParticipationDetail participationDetail : participationDetails){
	            	Object obj[] = { participationDetail.getCategory().getId(), participationDetail.getAmount().toString() };
	            	particTable.addItem(obj, particTable.getId());
            	}
            }
        }
	}
}
