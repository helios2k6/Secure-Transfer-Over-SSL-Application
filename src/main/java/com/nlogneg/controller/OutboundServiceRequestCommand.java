package com.nlogneg.controller;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogn.SecureFacade;
import com.nlogneg.model.OutboundFileProxy;
import com.nlogneg.model.objects.Chunk;
import com.nlogneg.model.objects.ChunkRequest;

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
				
				
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}
}
