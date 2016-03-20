package com.jee.client;

import com.jee.client.panel.UploadDataPanel;
import com.jee.client.panel.MainPanel;
import com.jee.client.style.MainStyle;
import com.jee.client.useradmin.VoucheringUnitPanel;
import com.jee.model.common.MainModule;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;


@SuppressWarnings("serial")
public class MainBody extends CustomComponent implements SelectedTabChangeListener {
	private JeeclientUI currentUi;
	private VerticalLayout layout;
	private TabSheet tabSheet;
	public MainBody(){
		currentUi = JeeclientUI.getCurrent();
		tabSheet = new TabSheet();
		tabSheet.addSelectedTabChangeListener(this);
		for(MainModule module: MainModule.values()){
			switch(module){
			case VoucherAdministration:
			{
				if(currentUi.getLoggedInUser()!=null /*&& currentUi.getUserAdminManager().getSecurityService().isUserHasPermission(currentUi.getLoggedInUser().getId(), currentUi.getLoggedInUser().getUserName(), JeePermissionEnum.userAdministrationModuleView)*/){
					Tab tab = tabSheet.addTab(new VoucheringUnitPanel(), currentUi.getText(module.getI18nKey()));
					tab.setIcon(new ThemeResource(MainStyle.ICON_USER_ADMIN));
				}
				break;
			}
			case UploadModule:
			{
				if(currentUi.getLoggedInUser() !=null /*&& currentUi.getUserAdminManager().getSecurityService().isUserHasPermission(currentUi.getLoggedInUser().getId(), currentUi.getLoggedInUser().getUserName(), JeePermissionEnum.administrationModuleView)*/){
					Tab tab = tabSheet.addTab(new UploadDataPanel(), currentUi.getText(module.getI18nKey()));
					tab.setIcon(new ThemeResource(MainStyle.ICON_ADMINISTRATION));
				}
				break;
			}
			default:
				break;
			}
		}
		tabSheet.setSizeFull();
		layout = new VerticalLayout();
		layout.addComponent(tabSheet);
		layout.setSpacing(true);
		layout.setSizeFull();
		this.setSizeFull();
		this.setCompositionRoot(layout);
	}
	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		
		MainPanel panel = (MainPanel) event.getTabSheet().getSelectedTab();
		currentUi.setSelectedPanel(panel);
		panel.onLoad();
		
	}
}
