package com.nlogneg.model;

import java.io.File;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

public class ShareFolderProxy extends Proxy{
	private File rootfolder;
	
	public ShareFolderProxy(String folder){
		super("Share Folder Proxy");
		this.rootfolder = new File(folder);
	}
	
	public String getRootPath(){
		return rootfolder.getAbsolutePath();
	}
	
	public String getAbsolutePathFromRelativePath(String relativePath){
		String path = getRootPath() + relativePath;
		
		return path;
	}
	
	public String getRelativePathFromAbsolutePath(String absolutePath){
		String path = absolutePath.replace(getRootPath(), "");
		
		return path;
	}
	
	public boolean isFileInShareDirectory(File file){
		String absolutePath = getRootPath();
		
		if(absolutePath.contains(file.getAbsolutePath())){
			return true;
		}
		
		return false;
	}
}
