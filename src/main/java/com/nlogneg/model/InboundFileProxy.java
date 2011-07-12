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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.CRC32;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.model.exceptions.ChunkTooLargeException;
import com.nlogneg.model.objects.Chunk;
import com.nlogneg.model.objects.FileManifest;

public class InboundFileProxy extends Proxy{
	
	private static Logger logger = Logger.getLogger(InboundFileProxy.class);
	
	public static final String NAME_PREFIX = "Inbound File Proxy - ";
	
	private RandomAccessFile file;
	
	private FileManifest manifest;
	
	public InboundFileProxy(FileManifest manifest) throws FileNotFoundException{
		super(NAME_PREFIX + manifest.getShareFile().getRelativePath());
		this.file = new RandomAccessFile(manifest.getShareFile().getFile(), "rw");
		this.manifest = manifest;
	}

	public void writeChunk(Chunk c) throws IOException{
		FileChannel fc = file.getChannel();
		
		fc.position(c.getUniquePosition());
		
		ByteBuffer bb = ByteBuffer.wrap(c.getBytes());
		
		fc.write(bb);
	}
	
	public Chunk readChunk(long index) throws IOException, ChunkTooLargeException{
		FileChannel fc = file.getChannel();
		
		fc.position(index);
		
		ByteBuffer bb = ByteBuffer.allocate((int)Chunk.CHUNK_SIZE_IN_BYTES);
		
		int amountRead = fc.read(bb);
		
		if(amountRead != -1){
			Chunk chunk = new Chunk(bb.array(), index);
			return chunk;
		}
		
		return null;
	}
	
	public Set<Long> getSetOfNeededChunks() throws IOException{
		Set<Long> result = new HashSet<Long>();
		
		long numberOfChunks = manifest.getNumberOfChunks();
		
		for(long index = 0; index < numberOfChunks; index++){
			try {
				Chunk chunk = readChunk(index);
				if(chunk == null){
					result.add(index);
				}else{
					CRC32 calculatedChecksum = new CRC32();
					long reportedChecksum = chunk.getChecksum();
					calculatedChecksum.update(chunk.getBytes());
					
					if(reportedChecksum != calculatedChecksum.getValue()){
						result.add(index);
					}
				}
			} catch (ChunkTooLargeException e) {
				logger.error("", e);
			}
		}
		
		return result;
	}
	
	public long getFileSize() throws IOException{
		return file.length();
	}
	
	public void closeFile() throws IOException{
		file.close();
	}
	
	public FileManifest getManifest(){
		return manifest;
	}
	
}
