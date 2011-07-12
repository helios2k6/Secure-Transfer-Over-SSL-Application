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
