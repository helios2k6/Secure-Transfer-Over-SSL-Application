package com.nlogneg.view;

import org.puremvc.java.multicore.patterns.mediator.Mediator;

public class FileTableMediator extends Mediator{

	private static final String[] LOCAL_SHARE_FILE_INTERESTS = {};

	private static final String[] PEER_SHARE_FILE_INTERESTS = {};

	private FileTableMediatorEnum type;

	public FileTableMediator(String name, Object view, FileTableMediatorEnum type){
		super(name, view);
		this.type = type;
	}

	public FileTableMediatorEnum getType(){
		return type;
	}
	
	//TODO: Finish this up
	@Override
	public String[] listNotificationInterests(){
		String[] result = null;
		switch(type){
		case LOCAL_SHARE_FILE_TABLE_MEDIATOR:
			result = LOCAL_SHARE_FILE_INTERESTS;
			break;
		case PEER_SHARE_FILE_TABLE_MEDIATOR:
			result = PEER_SHARE_FILE_INTERESTS;
		}
		return result;
	}
	
	//TODO: Make "handleNotification()" method so you can do stuff with this
	
}
