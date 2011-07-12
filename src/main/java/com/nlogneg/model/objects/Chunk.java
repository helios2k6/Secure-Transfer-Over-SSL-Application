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

import java.io.Serializable;
import java.util.zip.CRC32;

import com.nlogneg.model.exceptions.ChunkTooLargeException;

public class Chunk implements Comparable<Chunk>, Serializable{
	private static final long serialVersionUID = -3442521503842839274L;

	public static final long CHUNK_SIZE_IN_BYTES = 4096; //4 kilobytes. That's right.
	
	private byte[] data;
	
	private long length;
	
	private long checksum;
	
	private final long uniquePosition;
	
	public Chunk(byte[] data, long uniquePosition) throws ChunkTooLargeException{
		if(data.length < CHUNK_SIZE_IN_BYTES){
			int i = 0;
			for(byte b : data){
				data[i] = new Byte(b);
				i++;
			}
			CRC32 crc32 = new CRC32();
			crc32.update(data);
			
			checksum = crc32.getValue();
			
			this.length = data.length;
			
			this.uniquePosition = uniquePosition;
			
			crc32 = null;
		}else{
			throw new ChunkTooLargeException();
		}
	}

	public byte[] getBytes(){
		byte[] freshByteArray = new byte[data.length];
		int i = 0;
		for(byte b : data){
			freshByteArray[i] = new Byte(b);
			i++;
		}
		
		return freshByteArray;
	}
	
	public long getChecksum(){
		return checksum;
	}
	
	public long getLength(){
		return length;
	}

	public long getUniquePosition(){
		return uniquePosition;
	}
	
	@Override
	public int compareTo(Chunk o) {
		return (int)(uniquePosition - o.getUniquePosition());
	}
}
