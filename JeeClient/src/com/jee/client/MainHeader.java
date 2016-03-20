package com.jee.client;

import java.util.Locale;

import com.jee.client.login.LoginForm;
import com.jee.client.style.MainStyle;
import com.jee.model.userAdmin.JeeUser;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

public class MainHeader extends CustomComponent {
	
	private HorizontalLayout layout;
	private Image logoImage;
	private Button logout;
	private ComboBox language;
	private JeeUser userInstance;
	private JeeclientUI current;
	
//	private Logger log = Logger.getLogger(MainHeader.class);
	private String LANG_DISPLAY_NAME = "LANG_DISPLAY_NAME";
	private String LANG_LOCALE_VALUE = "LANG_LOCALE_VALUE";
	private Locale[] supportedLocales = {Locale.ENGLISH, Locale.FRENCH};
	public MainHeader(){
		init();
	}
	
	private void init() {
		
		logoImage = new Image(null, new ThemeResource(MainStyle.ICON_LOGO));
		logoImage.setHeight("50px");
		current = JeeclientUI.getCurrent();
		String localizedName = current.getText("labels.welcome");
		String wlcmString = localizedName + " <b>" + (userInstance.getFirstName()!=null?userInstance.getFirstName():userInstance.getUserName() )
				 +"&nbsp;"+ (userInstance.getLastName()!=null?userInstance.getLastName():userInstance.getUserName() )+ "</b>";
		Label welcomeLable =new Label(wlcmString, ContentMode.HTML);
		welcomeLable.addStyleName(MainStyle.MAIN_HEADER_LABEL);
		language = initLanguageCombo();
		logout = new Button(current.getText("buttons.logout"), new Button.ClickListener() {

	        @Override
	        public void buttonClick(ClickEvent event) {

	            // "Logout" the user
	        	VaadinService.getCurrentRequest().getWrappedSession().setAttribute(JeeclientUI.USER_NAME_ATTRIBUTE_NAME, null);

	            // Refresh this view, should redirect to login view
	            getUI().getNavigator().navigateTo(LoginForm.NAME);
	        }
	    });
		language.setVisible(false);
		logout.setIcon(new ThemeResource(MainStyle.ICON_LOGOUT));
		HorizontalLayout leftLayout = new HorizontalLayout(logoImage , welcomeLable);
		HorizontalLayout rightLayout = new HorizontalLayout(language, logout);
		rightLayout.setSpacing(true);
		layout = new HorizontalLayout(leftLayout,rightLayout);
//		layout.setExpandRatio(logoImage, 0.1f);
//		layout.setExpandRatio(welcomeLable, 0.6f);
//		layout.setComponentAlignment(language, Alignment.MIDDLE_RIGHT);
//		layout.setComponentAlignment(welcomeLable, Alignment.MIDDLE_LEFT);
		layout.setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);
		layout.setSpacing(true);
		layout.setMargin(new MarginInfo(false, true, false, true));
		layout.setSizeFull();
		this.setStyleName(MainStyle.MAIN_HEADER);
		this.setSizeFull();
		setCompositionRoot(layout);
	}

	private ComboBox initLanguageCombo() {
		final ComboBox combo = new ComboBox();
		combo.setImmediate(true);
		combo.addContainerProperty(LANG_DISPLAY_NAME , String.class, "");
		combo.addContainerProperty(LANG_LOCALE_VALUE , Locale.class, null);
		for(Locale locale : supportedLocales){
			combo.addItem(locale.getDisplayLanguage());
			combo.getContainerProperty(locale.getDisplayLanguage(), LANG_DISPLAY_NAME).setValue(locale.getDisplayLanguage());
			combo.getContainerProperty(locale.getDisplayLanguage(), LANG_LOCALE_VALUE).setValue(locale);
		}
		combo.setItemCaptionPropertyId(LANG_DISPLAY_NAME);
		combo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		Locale currentLocale = current.getCurrentLocale();
		if(currentLocale==null)
			combo.setValue(combo.getItemIds().iterator().next());
		else
			combo.setValue(currentLocale.getDisplayLanguage());
		combo.setNullSelectionAllowed(false);
		combo.addStyleName(MainStyle.CUSTOM_COMBO);
		combo.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
					current.changeLocale((Locale) combo.getItem(event.getProperty().getValue()).getItemProperty(LANG_LOCALE_VALUE).getValue());
			}
		});
		return combo;
	}

	public MainHeader(JeeUser user){
		this.userInstance = user;
		init();
	}

}
