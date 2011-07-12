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
