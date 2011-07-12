package com.nlogn;

import java.util.UUID;

import org.puremvc.java.multicore.patterns.facade.Facade;

public class SecureFacade extends Facade{
	
	//Custom Notification Strings
	public static final String MESSAGE_RECEIVED_NOTIFICATION = "MESSAGE_RECEIVED_NOTIFICATION";
	
	public static final String CHUNK_REQUEST_RECEIVED_NOTIFICATION = "CHUNK_REQUEST_RECEIVED_NOTIFICATION";
	public static final String CHUNK_RESPONSE_RECEIVED_NOTIFICATION = "CHUNK_RESPONSE_RECEIVED_NOTIFICATION";
	
	public static final String FILE_MANIFEST_REQUEST_NOTIFICATION = "FILE_MANIFEST_REQUEST_NOTIFICATION";
	public static final String FILE_MANIFEST_RESPONSE_NOTIFICATION = "FILE_MANIFEST_RESPONSE_NOTIFICATION";
	
	public static final String FILE_LIST_REQUEST_NOTIFICATION = "FILE_LIST_REQUEST_NOTIFICATION";
	public static final String FILE_LIST_RESPONSE_NOTIFICATION = "FILE_LIST_RESPONSE_NOTIFICATION";
	
	public static final String UNKNOWN_MESSAGE_RECEIVED_NOTIFICATION = "UNKNOWN_MESSAGE_RECEIVED_NOTIFICATION";
	
	public static final String SETUP_INBOUND_FILE_PROXY_NOTIFICATION = "SETUP_INBOUND_FILE_PROXY_NOTIFICATION";
	
	public static final String NEED_TO_SEND_CHUNK_REQUEST_NOTIFICATION = "NEED_TO_SEND_CHUNK_REQUEST_NOTIFICATION";
	
	public static final String SUCCESSFULLY_RECEIVED_CHUNK_NOTIFICATION = "SUCCESSFULLY_RECEIVED_CHUNK_NOTIFICATION";
	
	public static final String OUTBOUND_FILE_PROXY_SETUP_NOTIFICATION = "CHUNK_REQUEST_READY_TO_BE_SERVICED_NOTIFICATION";
	
	protected SecureFacade(String key) {
		super(key);
	}
	
	private static class SecureFacadeHolder {
		private static final SecureFacade INSTANCE = new SecureFacade(UUID.randomUUID().toString());
	}

	public static SecureFacade getInstance() {
		return SecureFacadeHolder.INSTANCE;
	}
}
