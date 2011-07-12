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
package com.nlogneg.model.objects;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public class Message implements Serializable, Comparable<Message>{
	private static final long serialVersionUID = -1724486368119119326L;
	private static final AtomicLong MESSAGE_NUMBER_GENERATOR = new AtomicLong(0);
	
	private final MessageType messageType;
	private final long messageNumber;	
	
	private Serializable payload;
	
	public Message(MessageType messageType, Serializable payload){
		this.messageType = messageType;
		this.payload = payload;
		this.messageNumber = MESSAGE_NUMBER_GENERATOR.getAndIncrement();		
	}
	
	public MessageType getMessageType(){
		return messageType;
	}
	
	public long getMessageNumber(){
		return messageNumber;
	}
	
	public Serializable getPayload(){
		return payload;
	}

	@Override
	public int compareTo(Message arg0) {
		return (int)(messageNumber - arg0.getMessageNumber());
	}
}
