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
