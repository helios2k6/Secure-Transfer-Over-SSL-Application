package com.nlogneg.model;

import java.io.File;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.SecureFacade;
import com.nlogneg.model.objects.FileList;
import com.nlogneg.model.objects.ShareFile;

public class FileListProxy extends Proxy{
	public FileListProxy(){
		super("File List Proxy");
	}

	public FileList getFileList(File folder){
		ShareFolderProxy proxy = (ShareFolderProxy) SecureFacade.getInstance().retrieveProxy("Share Folder Proxy");
		String relativePath = proxy.getRelativePathFromAbsolutePath(folder.getAbsolutePath());
		FileList fileList = new FileList(relativePath);
		if(folder.isDirectory()){
			File[] files = folder.listFiles();
			for(File f : files){
				ShareFile sfile = ShareFile.getShareFile(f);
				if(sfile != null){
					fileList.addFileToList(sfile);
				}else{
					return null;
				}
			}
		}

		return fileList;
	}
}
