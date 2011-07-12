/*
 * Copyright (c) 2011 Andrew Johnson <hpmamv@gmail.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in 
 * the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the 
 * Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
