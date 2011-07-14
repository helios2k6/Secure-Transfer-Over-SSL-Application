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

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.SecureFacade;
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
