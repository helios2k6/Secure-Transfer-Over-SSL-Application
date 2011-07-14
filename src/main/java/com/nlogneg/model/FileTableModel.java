package com.nlogneg.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.nlogneg.model.objects.ShareFile;

public class FileTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 265236836353118319L;

	private static final int COLUMN_COUNT = 2;

	private static final String[] COLUMN_NAMES = {"File", "Size"};
	
	private List<ShareFile> shareFiles;
	
	public FileTableModel(List<ShareFile> shareFiles){
		this.shareFiles = shareFiles;
	}
	
	@Override
	public String getColumnName(int columnIndex){
		return COLUMN_NAMES[columnIndex];
	}
	
	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public int getRowCount() {
		return shareFiles.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		ShareFile f = shareFiles.get(rowIndex);
		if(colIndex == 0){
			return f;
		}
		return f.getFileSize();
	}

    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
}
