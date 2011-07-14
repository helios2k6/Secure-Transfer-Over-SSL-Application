package com.nlogneg.view;

import javax.swing.JTable;

import com.nlogneg.model.FileTableModel;
import com.nlogneg.model.objects.ShareFile;

public class FileTable extends JTable {

	private static final long serialVersionUID = -2658540155130880919L;

	public FileTable(FileTableModel model){
		super(model);
		init();
	}
	
	private void init(){
		setDefaultRenderer(ShareFile.class, new FileTableShareFileCellRenderer());
	}
	
}
