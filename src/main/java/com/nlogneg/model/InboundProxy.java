package com.nlogneg.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogn.SecureFacade;
import com.nlogneg.model.objects.Message;

/**
 * This class is meant to act as the "inbound" ssl-stream for our
 * program. This will set up a socket and draw information from it.
 * @author ajohnson
 *
 */
public class InboundProxy extends Proxy implements Runnable{

	private static Logger logger = Logger.getLogger(InboundProxy.class);

	public static final int DEFAULT_INBOUND_PORT = 1054;

	private ServerSocketFactory ssocketFactory;
	private SSLServerSocket ssocket;
	private SSLSocket socket;

	private volatile boolean keepReading;
	
	private volatile boolean readyForRead;

	private GZIPInputStream inputStream;

	public InboundProxy() throws IOException {
		super("Inbound Proxy");
		ssocketFactory = SSLServerSocketFactory.getDefault();
		ssocket = (SSLServerSocket)ssocketFactory.createServerSocket(DEFAULT_INBOUND_PORT);

		readyForRead = false;
		keepReading = true;

		ssocket.setNeedClientAuth(true);
	}

	@Override
	public void run() {
		try {
			socket = (SSLSocket)ssocket.accept();
			inputStream = new GZIPInputStream(socket.getInputStream());
			readyForRead = true;
			
			while(keepReading){
				try {
					ObjectInputStream ois = new ObjectInputStream(inputStream);
					Message msg = (Message)ois.readObject();
					sendNotification(SecureFacade.MESSAGE_RECEIVED_NOTIFICATION, msg);
				} catch (ClassNotFoundException e) {
					logger.error("Unknown data sent", e);
				}
				
			}
		} catch (IOException e) {
			logger.error("", e);
		} 
	}

	public boolean isReadyForRead(){
		return readyForRead;
	}
	
	public void shutdown() throws IOException{
		keepReading = false;
		inputStream.close();
	}

}
