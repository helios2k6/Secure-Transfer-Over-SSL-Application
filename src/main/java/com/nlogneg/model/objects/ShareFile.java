package com.nlogneg.model.objects;

import java.io.File;
import java.io.Serializable;

import com.nlogn.SecureFacade;
import com.nlogneg.model.ShareFolderProxy;

public class ShareFile implements Serializable{

	private static final long serialVersionUID = 3321436022515211294L;
	
	private File file;
	
	private ShareFile(File file){
		this.file = file;
	}
	
	public File getFile(){
		return file;
	}
	
	public String getRelativePath(){
		ShareFolderProxy proxy = (ShareFolderProxy)SecureFacade.getInstance().retrieveProxy("Share Folder Proxy");
		return proxy.getRelativePathFromAbsolutePath(file.getAbsolutePath());
	}
	
	public String getAbsolutePath(){
		return file.getAbsolutePath();
	}
	
	public static ShareFile getShareFile(File file){
		ShareFolderProxy proxy = (ShareFolderProxy)SecureFacade.getInstance().retrieveProxy("Share Folder Proxy");
		
		if(proxy.isFileInShareDirectory(file)){
			return new ShareFile(file);
		}
		
		return null;
	}
	
	public static ShareFile getShareFileFromRelativePath(String relativePath){
		ShareFolderProxy proxy = (ShareFolderProxy)SecureFacade.getInstance().retrieveProxy("Share Folder Proxy");
		File file = new File(relativePath);
		
		if(proxy.isFileInShareDirectory(file)){
			return new ShareFile(file);
		}
		
		return null;
	}
}
