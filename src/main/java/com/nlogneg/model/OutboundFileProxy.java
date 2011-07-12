package com.nlogneg.model;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.zip.CRC32;

import org.apache.log4j.Logger;
import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.model.objects.Chunk;
import com.nlogneg.model.objects.FileManifest;
import com.nlogneg.model.objects.ShareFile;

/**
 * This class is meant to be a proxy for COMPLETE files, not files
 * that are in limbo
 * @author ajohnson
 *
 */
public class OutboundFileProxy extends Proxy{

	private static Logger logger = Logger.getLogger(OutboundFileProxy.class);

	private RandomAccessFile file;
	
	private ShareFile shareFile;
	
	private FileManifest manifest;

	private Chunk[] buffer;
	private long nextBufferIndex;

	private static final int BUFFER_SIZE = 2000; //Buffer 8 megabytes at a time
	
	public static final String OUTBOUND_FILE_PROXY_PREFIX = "Outbound File Proxy - ";

	public OutboundFileProxy(ShareFile file) throws IOException{
		super(OUTBOUND_FILE_PROXY_PREFIX + file.getRelativePath());

		this.shareFile = file;
		this.file = new RandomAccessFile(file.getFile(), "r");
		this.buffer = new Chunk[BUFFER_SIZE];
		
		this.nextBufferIndex = -1;

		createManifest();
		
		logger.info("Initialized file proxy for " + file);
	}
	public Chunk getChunk(long chunkIndex) throws Exception{
		if(chunkIndex < nextBufferIndex && chunkIndex >= (nextBufferIndex - BUFFER_SIZE)){
			//We have the chunk in buffer
			int localIndex = BUFFER_SIZE - (int)(nextBufferIndex - chunkIndex);
			return buffer[localIndex];
		}else{
			//We don't have the chunk in buffer. Bias buffer for forward reading
			hydrateBuffer(chunkIndex);
			//Recursively return this chunk
			return getChunk(chunkIndex);
		}
	}

	private void hydrateBuffer(long chunkIndex) throws Exception{
		FileChannel channel = file.getChannel();
		try{
			channel.position(chunkIndex * Chunk.CHUNK_SIZE_IN_BYTES);

			long iterations = 0;

			for(int index = 0; index < BUFFER_SIZE; index++){
				ByteBuffer localBuffer = ByteBuffer.allocateDirect((int)Chunk.CHUNK_SIZE_IN_BYTES);
				int amountRead = channel.read(localBuffer);

				if(amountRead == -1){
					break;
				}
				iterations++;

				Chunk chunk = new Chunk(localBuffer.array(), chunkIndex + index);
				buffer[index] = chunk;
			}

			nextBufferIndex = chunkIndex + iterations;
		}catch(Exception e){
			nextBufferIndex = Long.MIN_VALUE;
			throw e;
		}
	}

	public long getFileSize() throws IOException{
		return file.length();
	}

	public long getNumberOfChunks() throws IOException{
		long fileSize = getFileSize();

		long decimal = fileSize / Chunk.CHUNK_SIZE_IN_BYTES;
		long mod = fileSize % Chunk.CHUNK_SIZE_IN_BYTES;

		if(mod == 0){
			return decimal;
		}
		return decimal + 1;
	}

	public void shutdown() throws IOException{
		file.close();
	}
	
	public FileManifest getManifest(){
		return manifest;
	}
	
	private void createManifest() throws IOException{
		FileChannel fc = file.getChannel();
		
		long size = getFileSize();
		
		HashMap<Long, CRC32> chunkMap = new HashMap<Long, CRC32>();
		
		for(long index = 0; index < size; index += Chunk.CHUNK_SIZE_IN_BYTES){
			CRC32 checksum = new CRC32();
			
			ByteBuffer bb = ByteBuffer.allocate((int)Chunk.CHUNK_SIZE_IN_BYTES);
			
			int amountRead = fc.read(bb);
			
			if(amountRead < 0){
				break;
			}
			
			checksum.update(bb.array());
			
			chunkMap.put(new Long(index), checksum);
		}
		
		manifest = new FileManifest(shareFile, chunkMap);
	}

}