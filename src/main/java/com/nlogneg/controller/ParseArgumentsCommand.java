package com.nlogneg.controller;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.SecureFacade;
import com.nlogneg.model.objects.ConnectionConfiguration;

@Deprecated
public class ParseArgumentsCommand extends SimpleCommand{
	private static Logger logger = Logger.getLogger(ParseArgumentsCommand.class);
	
	private static final String USER_ATTRIBUTE = "user";
	private static final String KEYSTORE_ATTRIBUTE = "keystore";
	private static final String TRUSTSTORE_ATTRIBUTE = "truststore";
	
	private static final String COMMON_NAME_ATTRIBUTE = "common-name";
	private static final String LOCATION_ATTRIBUTE = "location";
	private static final String PASSWORD_ATTRIBUTE = "password";
	
	@Override
	public void execute(INotification notification){
		String configFileLocation = (String)notification.getBody();
		try{
			SAXBuilder sax = new SAXBuilder();
			Document doc = sax.build(new File(configFileLocation));
			Element root = doc.getRootElement();
			
			Element user = root.getChild(USER_ATTRIBUTE);
			Element keystore = user.getChild(KEYSTORE_ATTRIBUTE);
			Element truststore = user.getChild(TRUSTSTORE_ATTRIBUTE);
			
			String commonName = user.getChildText(COMMON_NAME_ATTRIBUTE);
			
			String locationOfKeystore = keystore.getChildText(LOCATION_ATTRIBUTE);
			String passwordOfKeystore = keystore.getChildText(PASSWORD_ATTRIBUTE);
			
			String locationOfTrustStore = truststore.getChildText(LOCATION_ATTRIBUTE);
			String passwordOfTrustStore = truststore.getChildText(PASSWORD_ATTRIBUTE);
			
			ConnectionConfiguration pc = new ConnectionConfiguration(commonName, 
					new File(locationOfKeystore), 
					passwordOfKeystore, 
					new File(locationOfTrustStore), 
					passwordOfTrustStore);
			
			SecureFacade facade = SecureFacade.getInstance();
			
			facade.sendNotification(SecureFacade.PROGRAM_CONFIGURATION_PARSED_NOTIFICATION, pc);
		}catch(IOException e){
			logger.error("", e);
		} catch (JDOMException e) {
			logger.error("Couldn't build SAX object", e);
		}
	}

}
