package com.nlogneg.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogn.SecureFacade;
import com.nlogneg.model.OutboundFileProxy;
import com.nlogneg.model.objects.ChunkRequest;
import com.nlogneg.model.objects.Message;
import com.nlogneg.model.objects.MessageType;
import com.nlogneg.model.objects.ShareFile;

public class SetUpOutboundFileProxy extends SimpleCommand{
	
	private static Logger logger = Logger.getLogger(SetUpOutboundFileProxy.class);
	
	public void execute(INotification notification){
		Message message = (Message)notification.getBody();
		
		if(message.getMessageType() == MessageType.REQUEST_CHUNK){
			SecureFacade facade = SecureFacade.getInstance();
			ChunkRequest request = (ChunkRequest)message.getPayload();
			
			String proxyName = OutboundFileProxy.OUTBOUND_FILE_PROXY_PREFIX + request.getRelativePath();
			
			if(facade.hasProxy(proxyName)){
				//Proxy Present
				sendNotification(SecureFacade.OUTBOUND_FILE_PROXY_SETUP_NOTIFICATION, request);
			}else{
				//No proxy present
				try {
					
					OutboundFileProxy proxy = new OutboundFileProxy(ShareFile.getShareFileFromRelativePath(request.getRelativePath()));
					facade.registerProxy(proxy);
					
					sendNotification(SecureFacade.OUTBOUND_FILE_PROXY_SETUP_NOTIFICATION, request);
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}else{
			logger.error("Got unknown message type");
		}
	}
}
