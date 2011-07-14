package com.nlogneg.model;

import java.util.List;

import javax.swing.JTable;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.SecureFacade;
import com.nlogneg.model.objects.ShareFile;
import com.nlogneg.view.FileTable;

public class FileTableProxy extends Proxy{
	
	private FileTable table;
	
	public FileTableProxy(List<ShareFile> shareFiles){
		super("File Table Proxy");
		table = new FileTable(new FileTableModel(shareFiles));
	}
	
	public JTable getTable(){
		return table;
	}
	
	public void updateTable(List<ShareFile> shareFiles){
		table.setModel(new FileTableModel(shareFiles));
		
		//Send notification
		sendNotification(SecureFacade.LOCAL_SHARE_FOLDER_FILE_TABLE_UPDATED_NOTIFICATION);
	}
}
