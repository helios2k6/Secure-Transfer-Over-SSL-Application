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

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.SecureFacade;
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
