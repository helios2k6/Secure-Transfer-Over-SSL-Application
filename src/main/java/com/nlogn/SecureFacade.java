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
