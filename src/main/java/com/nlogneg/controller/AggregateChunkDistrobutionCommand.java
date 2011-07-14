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
