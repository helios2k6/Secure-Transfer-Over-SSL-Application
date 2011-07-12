package com.nlogneg.controller;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogn.SecureFacade;
import com.nlogneg.model.objects.MailboxTask;
import com.nlogneg.model.objects.Message;

public class MessageBrokerCommand extends SimpleCommand{
	
	@Override
	public void execute(INotification notification){
		String type = notification.getType();
		if(type.equals(SecureFacade.MESSAGE_RECEIVED_NOTIFICATION)){
			MailboxTask.getInstance().addMessage((Message)notification.getBody());
		}
	}
	
}
