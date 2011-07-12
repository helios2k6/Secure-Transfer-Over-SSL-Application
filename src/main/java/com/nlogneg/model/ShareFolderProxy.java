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
