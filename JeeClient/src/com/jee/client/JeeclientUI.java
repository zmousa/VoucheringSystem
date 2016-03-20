package com.jee.client;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jee.api.VoucheringManager;
import com.jee.client.login.LoginForm;
import com.jee.client.panel.MainPanel;
import com.jee.kernel.util.WebUtil;
import com.jee.model.userAdmin.JeeUser;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Widgetset("com.jee.widgetset.JeeclientWidgetset")
@Theme("jeeclient")
public class JeeclientUI extends UI {
	public static final String USER_NAME_ATTRIBUTE_NAME = "userInstance";
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(JeeclientUI.class);
	private ApplicationContext applicationContext;
	private ReloadableResourceBundleMessageSource messageSource;
	private VoucheringManager userAdminManager;
	private Locale currentLocale;
	private MainPanel selectedPanel;
	
	private JeeUser loggedInUser;
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = JeeclientUI.class)
	public static class Servlet extends VaadinServlet {
	}

	public static JeeclientUI getCurrent() {
		return (JeeclientUI) UI.getCurrent();
	}
	
	@Override
	protected void init(VaadinRequest request) {
		new Navigator(this, this);
		getNavigator().addView(LoginForm.NAME, LoginForm.class);
		getNavigator().addView(MainView.NAME, MainView.class);
		getNavigator().addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				// Check if a user has logged in
				boolean isLoggedIn = VaadinService.getCurrentRequest().getWrappedSession().getAttribute(USER_NAME_ATTRIBUTE_NAME)!= null;// getSession().getAttribute(USER_NAME_ATTRIBUTE_NAME) != null;
				boolean isLoginView = event.getNewView() instanceof LoginForm;

				if (!isLoggedIn && !isLoginView) {
					// Redirect to login view always if a user has not yet
					// logged in
					getNavigator().navigateTo(LoginForm.NAME);
					return false;

				} else if (isLoggedIn && isLoginView) {
					// If someone tries to access to login view while logged in,
					// then cancel
					return false;
				}

				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {

			}
		});

		WrappedSession session = request.getWrappedSession();
		HttpSession httpSession = ((WrappedHttpSession) session).getHttpSession();
		ServletContext servletContext = httpSession.getServletContext();
		applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		messageSource = (ReloadableResourceBundleMessageSource) applicationContext.getBean("resource");
		userAdminManager = (VoucheringManager) applicationContext.getBean("userAdminManager");
		WebUtil.absolutePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		this.getLoadingIndicatorConfiguration().setFirstDelay(500); 
		this.getLoadingIndicatorConfiguration().setSecondDelay(30000); 
		this.getLoadingIndicatorConfiguration().setThirdDelay(80000);
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}	

	public void changeLocale(Locale newLocale) {
		this.currentLocale = newLocale;
		this.getNavigator().navigateTo(MainView.NAME);
	}
	
	public String getText(String key){
		String localizedName;
		try {
			localizedName = messageSource.getMessage(key, null, currentLocale);
		} catch (NoSuchMessageException e) {
			
			return key;
		}
		return localizedName;
	}
	
	public String getTextWithParam(String key, Object... params){
		String localizedName;
		try {
			localizedName = messageSource.getMessage(key, params, currentLocale);
		} catch (NoSuchMessageException e) {
			
			return key;
		}
		return localizedName;
	}
	
	public Locale getCurrentLocale(){
		if(currentLocale!=null)
			return currentLocale;
		if(Locale.getDefault()!=null){
			currentLocale =  Locale.getDefault();
			return currentLocale;
		}
		return new Locale("en", "EN");
	}

	public VoucheringManager getUserAdminManager() {
		return userAdminManager;
	}

	public void setUserAdminManager(VoucheringManager userAdminManager) {
		this.userAdminManager = userAdminManager;
	}

	public JeeUser getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(JeeUser loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public void showInvalidLicenseMsg() {
		Notification.show(getText("license.module.invalid.license"), Type.WARNING_MESSAGE);
		
	}

	public MainPanel getSelectedPanel() {
		return selectedPanel;
	}

	public void setSelectedPanel(MainPanel selectedPanel) {
		this.selectedPanel = selectedPanel;
	}

	public ReloadableResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
}