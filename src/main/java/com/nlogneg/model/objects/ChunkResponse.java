package com.nlogneg.model.objects;

import java.io.Serializable;
import java.util.Set;

public class ChunkResponse implements Serializable{
	private static final long serialVersionUID = 8090214173921429925L;
	
	private Set<Chunk> chunks;
	private String relativePath;
	
	public ChunkResponse(Set<Chunk> chunks, String relativePath){
		this.chunks = chunks;
		this.relativePath = relativePath;
	}

	public Set<Chunk> getChunks() {
		return chunks;
	}

	public void setChunks(Set<Chunk> chunks) {
		this.chunks = chunks;
	}

	public String getFilePath() {
		return relativePath;
	}

	public void setFilePath(String filePath) {
		this.relativePath = filePath;
	}
}
