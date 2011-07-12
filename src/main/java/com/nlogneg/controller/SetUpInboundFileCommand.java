package com.nlogneg.controller;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogn.SecureFacade;
import com.nlogneg.model.InboundFileProxy;
import com.nlogneg.model.objects.FileManifest;
import com.nlogneg.model.objects.Message;
import com.nlogneg.model.objects.MessageType;

/**
 * This command will set up an inbound File that is being downloaded
 * from a peer. This simple command is meant to be executed the moment
 * we get the File Manifest
 * @author ajohnson
 *
 */
public class SetUpInboundFileCommand extends SimpleCommand{
	
	private static Logger logger = Logger.getLogger(SetUpInboundFileCommand.class);
	
	@Override
	public void execute(INotification notification){
		Message message = (Message)notification.getBody();
		//Sanity check
		if(message.getMessageType() == MessageType.RESPONSE_FILE_MANIFEST){
			//Set up file to download
			FileManifest manifest = (FileManifest)message.getPayload();
			try {
				SecureFacade facade = SecureFacade.getInstance();

				logger.info("Attempting to download the file " + manifest.getShareFile().getRelativePath());
				InboundFileProxy proxy = new InboundFileProxy(manifest);
				
				facade.registerProxy(proxy);
				
				facade.sendNotification(SecureFacade.SETUP_INBOUND_FILE_PROXY_NOTIFICATION, proxy.getProxyName());
			} catch (FileNotFoundException e) {
				logger.error("", e);
			}
		}
	}
}
