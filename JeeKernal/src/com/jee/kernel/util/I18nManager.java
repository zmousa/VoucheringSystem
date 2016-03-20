package com.jee.kernel.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

public class I18nManager implements ApplicationContextAware {
	
	private MessageSource messages;
	private Locale locale = Locale.ENGLISH;
	
	private static I18nManager instance = null;
	
	public I18nManager(MessageSource messages, Locale local) {
		super();
		this.messages = messages;
		this.locale = local;
	}
	
	public I18nManager() {
		
	}
	
	public void setMessages(MessageSource _messages) {
		this.messages = _messages;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext _ac) throws BeansException {
		instance = _ac.getBean("i18nManager", I18nManager.class);
	}
	
	public static String getText(String _key) {
		// default value is the same key
		
		if(instance != null)
			return instance.messages.getMessage(_key, null, _key, instance.locale);
		
		return null;
	}
	
	public static String getTextParam(String _key, Object... params) {
		try
		{
			return MessageFormat.format(getText(_key), params);
		}
		catch(MissingResourceException e)
		{
			return '!' + _key + '!';
		}
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public static Locale getInstanceLocale() {
		return instance.locale; 
	}
}
