package com.nlogneg.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.SecureFacade;

public class ParsePropertiesCommand extends SimpleCommand{
	
	private static Logger logger = Logger
			.getLogger(ParsePropertiesCommand.class);
	
	public static final String PROPERTIES_FILE_LOCATION = "config.properies"; 
	
	@Override
	public void execute(INotification notification){
		try {
			File file = new File(PROPERTIES_FILE_LOCATION);
			FileReader reader = new FileReader(file);
			
			Properties prop = new Properties();
			
			prop.load(reader);
			
			reader.close();
			
			sendNotification(SecureFacade.PROGRAM_PROPERTIES_PARSED_NOTIFICATION, prop);
		} catch (FileNotFoundException e) {
			logger.error("Could not read properties file", e);
			sendNotification(SecureFacade.PROGRAM_PROPERTIES_COULD_NOT_BE_PARSED_NOTIFICATION);
		} catch (IOException e) {
			logger.error("Could not load properties file into reader", e);
			sendNotification(SecureFacade.PROGRAM_PROPERTIES_COULD_NOT_BE_PARSED_NOTIFICATION);
		}
	}
	
}
