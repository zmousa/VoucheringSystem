package com.jee.client.useradmin;

import com.jee.client.panel.MainPanel;
import com.jee.model.common.ToolBarEventEnum;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;

public class VoucheringUnitPanel extends MainPanel implements SelectedTabChangeListener {
	private static final long serialVersionUID = -4584825802681172213L;
	private TabSheet innerTabSheet;
	private VoucheringPanel selectedTab;

	@Override
	protected void initUi() {
		initInnerTabSheet();
		this.setContent(innerTabSheet);
		this.setSizeFull(); 
	}

	@Override
	public void onLoad() {
		selectedTab.fillData();
	}

	private void initInnerTabSheet() {
		innerTabSheet = new TabSheet();
		innerTabSheet.addSelectedTabChangeListener(this);

		VoucheringPanel categoryTab = new VoucheringPanel(ToolBarEventEnum.Category);
		innerTabSheet.addTab(categoryTab, currentUi.getText("category.module.name"));
		
		VoucheringPanel voucherTab = new VoucheringPanel(ToolBarEventEnum.Voucher);
		innerTabSheet.addTab(voucherTab, currentUi.getText("vouchers.module.name"));
		
		VoucheringPanel beneficiaryTab = new VoucheringPanel(ToolBarEventEnum.Beneficiary);
		innerTabSheet.addTab(beneficiaryTab, currentUi.getText("beneficiary.module.name"));
		
		VoucheringPanel distributionTab = new VoucheringPanel(ToolBarEventEnum.Distribution);
		innerTabSheet.addTab(distributionTab, currentUi.getText("distribution.module.name"));
		
		
		innerTabSheet.setStyleName("custom-tab");
		innerTabSheet.setSizeFull();
	}
	
	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		selectedTab = (VoucheringPanel) event.getTabSheet().getSelectedTab();
		if (currentUi.getSelectedPanel() instanceof VoucheringUnitPanel)
			selectedTab.fillData();
	}

	public VoucheringPanel getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(VoucheringPanel selectedTab) {
		this.selectedTab = selectedTab;
	}
}
