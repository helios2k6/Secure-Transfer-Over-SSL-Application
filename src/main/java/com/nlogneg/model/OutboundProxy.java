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
