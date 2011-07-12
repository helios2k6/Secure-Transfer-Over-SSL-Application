package com.nlogneg.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogn.SecureFacade;
import com.nlogneg.model.InboundFileProxy;
import com.nlogneg.model.objects.ChunkRequest;

public class AggregateChunkDistrobutionCommand extends SimpleCommand{

	private static Logger logger = Logger.getLogger(AggregateChunkDistrobutionCommand.class);
	
	public void execute(INotification notification){
		String proxyName = (String)notification.getBody();
		
		SecureFacade facade = SecureFacade.getInstance();
		
		InboundFileProxy proxy = (InboundFileProxy)facade.retrieveProxy(proxyName);
		
		try {
			Set<Long> chunks = proxy.getSetOfNeededChunks();
			Set<Set<Long>> subChunks = new HashSet<Set<Long>>();
			int index = 0;
			
			Set<Long> subSet = new HashSet<Long>();
			for(Long l : chunks){
				if(index < 5){
					subSet.add(l);
					index++;
				}else{
					subChunks.add(subSet);
					subSet = new HashSet<Long>();
					subSet.add(l);
					index = 1;
				}
			}
			
			for(Set<Long> s : subChunks){
				ChunkRequest request = new ChunkRequest(proxy.getManifest().getShareFile().getRelativePath(), s);
				
				sendNotification(SecureFacade.NEED_TO_SEND_CHUNK_REQUEST_NOTIFICATION, request);
			}
		} catch (IOException e) {
			logger.error("", e);
		}
		
	}
}
