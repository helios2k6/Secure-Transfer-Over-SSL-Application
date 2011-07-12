package com.nlogneg.model.exceptions;

public class ChunkTooLargeException extends Exception {

	private static final long serialVersionUID = 5209525967410712253L;
	
	public ChunkTooLargeException(){
		super("Cannot construct Chunk object. The byte array is too large");
	}
}
