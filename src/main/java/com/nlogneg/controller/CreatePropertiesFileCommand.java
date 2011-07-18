package com.nlogneg.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public class CreatePropertiesFileCommand extends SimpleCommand{
	
	private static Logger logger = Logger
			.getLogger(CreatePropertiesFileCommand.class);
	
	@Override
	public void execute(INotification notification){
		File propFile = new File(ParsePropertiesCommand.PROPERTIES_FILE_LOCATION);
		
		Properties props = (Properties) notification.getBody();
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(propFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			PrintWriter printWriter = new PrintWriter(bufferedWriter);
			
			props.store(printWriter, "Configuration for the app");
			
			printWriter.close();
		} catch (IOException e) {
			logger.error("Could not set up config.properties output file", e);
		}
	}
}
