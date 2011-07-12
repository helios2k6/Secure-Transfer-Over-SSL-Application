package com.nlogneg.controller;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogn.SecureFacade;
import com.nlogneg.model.objects.ProgramConfiguration;

public class SetEnviroVariablesCommand extends SimpleCommand {
	
	private static Logger logger = Logger.getLogger(SetEnviroVariablesCommand.class);
	
	public static final String KEYSTORE_PROP = "javax.net.ssl.keyStore";
	public static final String TRUSTSTORE_PROP = "javax.net.ssl.trustStore";

	public static final String KEYSTORE_PASS = "javax.net.ssl.keyStorePassword";
	public static final String TRUSTSTORE_PASS = "javax.net.ssl.trustStorePassword";
	
	private void setProperty(String key, String value){
		logger.info("Setting " + key + " to: " + value);
		System.setProperty(key, value);
	}
	
	public void execute(INotification notification){
		ProgramConfiguration pc = (ProgramConfiguration)notification.getBody();
		
		setProperty(KEYSTORE_PROP, pc.getKeystoreFile().getAbsolutePath());
		setProperty(KEYSTORE_PASS, pc.getKeystorePassword());
		
		setProperty(TRUSTSTORE_PROP, pc.getTrustStoreFile().getAbsolutePath());
		setProperty(TRUSTSTORE_PASS, pc.getTrustStorePassword());
		
		SecureFacade.getInstance().sendNotification(SecureFacade.ENVIRONMENTAL_VARIABLES_SETUP_NOTIFICATION);
	}
}
