package com.nlogneg.model.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6708262632633769693L;
	private String directoryPath;
	private ArrayList<String> fileList;
	
	public FileList(String directoryPath){
		this.directoryPath = directoryPath;
		this.fileList = new ArrayList<String>();
	}
	
	public String getPath(){
		return directoryPath;
	}
	
	public List<String> getFileList(){
		return fileList;
	}
	
	public void addFileToList(String file){
		fileList.add(file);
	}
}
