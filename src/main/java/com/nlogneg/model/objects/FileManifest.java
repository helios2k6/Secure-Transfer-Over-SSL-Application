package com.nlogneg.model.objects;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.zip.CRC32;

public class FileManifest implements Serializable{
	
	private static final long serialVersionUID = -3442455607218814617L;
	private ShareFile shareFile;
	
	private Map<Long, CRC32> chunkMap;
	
	public FileManifest(ShareFile shareFile, Map<Long, CRC32> chunkMap){
		this.shareFile = shareFile;
		this.chunkMap = chunkMap;
	}

	public ShareFile getShareFile() {
		return shareFile;
	}

	public Map<Long, CRC32> getChunkMap() {
		return chunkMap;
	}

	public long getFileSize(){		
		return ((long)getNumberOfChunks()) * Chunk.CHUNK_SIZE_IN_BYTES;
	}
	
	public int getNumberOfChunks(){
		Set<Long> keyring = chunkMap.keySet();
		return keyring.size();
	}
}
