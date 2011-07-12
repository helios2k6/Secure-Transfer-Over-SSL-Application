package com.nlogneg.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogn.SecureFacade;
import com.nlogneg.model.OutboundProxy;
import com.nlogneg.model.objects.ChunkRequest;
import com.nlogneg.model.objects.Message;
import com.nlogneg.model.objects.MessageType;

public class SendChunkRequestCommand extends SimpleCommand{
	
	private static Logger logger = Logger.getLogger(SendChunkRequestCommand.class);
	
	public void execute(INotification notification){
		OutboundProxy proxy = (OutboundProxy)SecureFacade.getInstance().retrieveProxy("Outbound Proxy");
		
		ChunkRequest request = (ChunkRequest)notification.getBody();
		
		Message message = new Message(MessageType.REQUEST_CHUNK, request);
		
		try {
			proxy.sendMessage(message);
		} catch (IOException e) {
			logger.error("", e);
		}
		
	}
}
