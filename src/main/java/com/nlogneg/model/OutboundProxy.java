package com.nlogneg.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.zip.GZIPOutputStream;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.model.objects.Message;

public class OutboundProxy extends Proxy{
	
	private SocketFactory socketFactory;
    private SSLSocket socket;
    
    private GZIPOutputStream outputStream;
	
	public OutboundProxy(String hostName) throws UnknownHostException, IOException {
		super("Outbound Proxy");

		socketFactory = SSLSocketFactory.getDefault();
		socket = (SSLSocket) socketFactory.createSocket(hostName, InboundProxy.DEFAULT_INBOUND_PORT);
		
		socket.setKeepAlive(true);
		
		outputStream = new GZIPOutputStream(socket.getOutputStream());
	}
	
	public void sendMessage(Message msg) throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(outputStream);
		oos.writeObject(msg);
		oos.close();
	}
	
	public void shutdown() throws IOException{
		outputStream.close();
	}

}
