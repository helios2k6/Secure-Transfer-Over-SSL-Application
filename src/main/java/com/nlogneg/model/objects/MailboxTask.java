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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.log4j.Logger;

import com.nlogneg.SecureFacade;

public class MailboxTask implements Runnable{

	private static Logger logger = Logger.getLogger(MailboxTask.class);

	private volatile boolean keepGoing;
	private BlockingQueue<Message> mailbox; //Other threads can add messages
	private Map<Long, Message> uncheckedMessages; //We put messages in this Set when we don't expect them

	private Thread thisThread; //Set in the run method

	private long lastServicedMessage;

	private MailboxTask() {
		mailbox = new PriorityBlockingQueue<Message>(); //Required to enforce mailbox retrieval ordering
		uncheckedMessages = new HashMap<Long, Message>();
		keepGoing = true;
		lastServicedMessage = 0;
	}

	private static class MailboxTaskHolder {
		private static final MailboxTask INSTANCE = new MailboxTask();
	}

	public static MailboxTask getInstance() {
		return MailboxTaskHolder.INSTANCE;
	}

	public void addMessage(Message message){
		mailbox.offer(message);
	}

	private void checkUncheckedMessages(){
		Set<Long> keyring = uncheckedMessages.keySet();
		for(long l : keyring){
			if(l == lastServicedMessage + 1){
				processMessage(uncheckedMessages.get(l));
				return;
			}
		}
	}

	private void processMessage(Message message){
		MessageType type = message.getMessageType();
		SecureFacade facade = SecureFacade.getInstance();
		
		switch(type){
		case REQUEST_FILE_LIST:
			facade.sendNotification(SecureFacade.FILE_LIST_REQUEST_NOTIFICATION, message);
			break;
		case REQUEST_FILE_MANIFEST:
			facade.sendNotification(SecureFacade.FILE_MANIFEST_REQUEST_NOTIFICATION, message);
			break;
		case REQUEST_CHUNK:
			facade.sendNotification(SecureFacade.CHUNK_REQUEST_RECEIVED_NOTIFICATION, message);
			break;
		case RESPONSE_FILE_LIST:
			facade.sendNotification(SecureFacade.FILE_LIST_RESPONSE_NOTIFICATION, message);
			break;
		case RESPONSE_FILE_MANIFEST:
			facade.sendNotification(SecureFacade.FILE_MANIFEST_RESPONSE_NOTIFICATION, message);
			break;
		case RESPONSE_CHUNK:
			facade.sendNotification(SecureFacade.CHUNK_RESPONSE_RECEIVED_NOTIFICATION, message);
			break;
		default:
			//got something weird
			facade.sendNotification(SecureFacade.UNKNOWN_MESSAGE_RECEIVED_NOTIFICATION);
		}
	}

	private void serviceMailbox() throws InterruptedException{
		Message currentMessage = mailbox.take();
		long currentMessageNumber = currentMessage.getMessageNumber();

		//Very strict rules on what messages can be serviced.
		if(lastServicedMessage + 1 != currentMessageNumber){
			processMessage(currentMessage);
			lastServicedMessage++;
		}else{
			uncheckedMessages.put(currentMessageNumber, currentMessage);
			checkUncheckedMessages();
		}
	}

	public void shutdown(){
		logger.info("Mailbox Task shutting down");
		thisThread.interrupt();
		keepGoing = false;
	}

	@Override
	public void run() {
		thisThread = Thread.currentThread();
		while(keepGoing){
			try {
				serviceMailbox();
			} catch (InterruptedException e) {
				logger.error("", e);
				keepGoing = false; //Shutdown this task
			}
		}
	}

}
