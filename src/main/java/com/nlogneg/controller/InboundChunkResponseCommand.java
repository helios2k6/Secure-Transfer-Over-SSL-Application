package com.nlogneg.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.CRC32;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogn.SecureFacade;
import com.nlogneg.model.InboundFileProxy;
import com.nlogneg.model.objects.Chunk;
import com.nlogneg.model.objects.ChunkRequest;
import com.nlogneg.model.objects.ChunkResponse;
import com.nlogneg.model.objects.Message;
import com.nlogneg.model.objects.MessageType;

public class InboundChunkResponseCommand extends SimpleCommand{
	
	private static Logger logger = Logger.getLogger(InboundChunkResponseCommand.class);
	
	public void execute(INotification notification){
		Message message = (Message)notification.getBody();
		
		if(message.getMessageType() == MessageType.RESPONSE_CHUNK){
			ChunkResponse response = (ChunkResponse)message.getPayload();
			Set<Chunk> chunks = response.getChunks();
			
			Set<Long> badChunks = new HashSet<Long>();
			
			SecureFacade facade = SecureFacade.getInstance();
			
			InboundFileProxy proxy = (InboundFileProxy)facade.retrieveProxy(InboundFileProxy.NAME_PREFIX + response.getFilePath());
			
			for(Chunk c : chunks){
				CRC32 calculatedChecksum = new CRC32();
				calculatedChecksum.update(c.getBytes());
				
				if(calculatedChecksum.getValue() == c.getChecksum()){
					try {
						proxy.writeChunk(c);
						
						//TODO: Send notification of progress using progress object or something
					} catch (IOException e) {
						logger.error("Could not write chunk", e);
					}
				}else{
					//Checksums do not match
					badChunks.add(c.getUniquePosition());
				}
			}
			
			if(badChunks.size() > 0){
				ChunkRequest request = new ChunkRequest(response.getFilePath(), badChunks);
				sendNotification(SecureFacade.NEED_TO_SEND_CHUNK_REQUEST_NOTIFICATION, request);
			}
			
		}else{
			logger.error("Got the wrong type of message");
		}
	}
}
