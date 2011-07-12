package com.nlogneg.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogn.SecureFacade;
import com.nlogneg.model.OutboundFileProxy;
import com.nlogneg.model.OutboundProxy;
import com.nlogneg.model.objects.Chunk;
import com.nlogneg.model.objects.ChunkRequest;
import com.nlogneg.model.objects.ChunkResponse;
import com.nlogneg.model.objects.Message;
import com.nlogneg.model.objects.MessageType;

public class OutboundServiceRequestCommand extends SimpleCommand{
	private static Logger logger = Logger.getLogger(OutboundServiceRequestCommand.class);
	public void execute(INotification notification){
		ChunkRequest request = (ChunkRequest)notification.getBody();

		SecureFacade facade = SecureFacade.getInstance();

		OutboundFileProxy proxy = (OutboundFileProxy)facade.retrieveProxy(OutboundFileProxy.OUTBOUND_FILE_PROXY_PREFIX + 
				request.getRelativePath());

		Set<Long> chunkRequestIDs = request.getChunkRequests();

		Set<Chunk> chunkResponse = new HashSet<Chunk>();
		
		for(Long l : chunkRequestIDs){
			try {
				Chunk c = proxy.getChunk(l);
				
				chunkResponse.add(c);
			} catch (Exception e) {
				logger.error("Couldn't get chunk", e);
			}
		}
		
		ChunkResponse response = new ChunkResponse(chunkResponse, request.getRelativePath());
		
		OutboundProxy outboundProxy = (OutboundProxy)facade.retrieveProxy("Outbound Proxy");
		Message message = new Message(MessageType.RESPONSE_CHUNK, response);

		try {
			outboundProxy.sendMessage(message);
		} catch (IOException e) {
			logger.error("Could not send message", e);
		}
	}
}
