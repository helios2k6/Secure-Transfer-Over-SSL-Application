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

import java.io.File;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

import com.nlogneg.SecureFacade;
import com.nlogneg.model.ShareFolderProxy;

public class ShareFile implements Serializable{

	private static final long serialVersionUID = 3321436022515211294L;
	
	private File file;
	
	private Icon icon;
	
	private ShareFile(File file){
		this.file = file;
		this.icon = FileSystemView.getFileSystemView().getSystemIcon(this.file);
	}
	
	public File getFile(){
		return file;
	}
	
	public Icon getIcon(){
		return icon;
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
	
	public long getFileSize(){
		return file.length();
	}
	
	public String getName(){
		return file.getName();
	}
	
}
