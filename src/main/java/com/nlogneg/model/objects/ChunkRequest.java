package com.nlogneg.model.objects;

import java.io.Serializable;
import java.util.Set;

public class ChunkRequest implements Serializable{

	private static final long serialVersionUID = -1096830276679029171L;

	private String relativePath;
	private Set<Long> chunkRequests;
	
	public ChunkRequest(String relativePath, Set<Long> chunkRequests){
		this.relativePath = relativePath;
		this.chunkRequests = chunkRequests;
	}
	
	public String getRelativePath(){
		return relativePath;
	}
	
	public Set<Long> getChunkRequests(){
		return chunkRequests;
	}
}
