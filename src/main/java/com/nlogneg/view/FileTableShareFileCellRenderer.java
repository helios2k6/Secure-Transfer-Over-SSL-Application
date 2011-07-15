package com.nlogneg.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.nlogneg.model.objects.ShareFile;

public class FileTableShareFileCellRenderer extends JLabel implements TableCellRenderer{

	private static final long serialVersionUID = 5044788067276853377L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object shareFile,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if(shareFile instanceof ShareFile){
			//Rendering sharefile
			ShareFile f = (ShareFile)shareFile;
			//Set the icon for this guy
			setIcon(f.getIcon());
			setText(f.getName());
			if(isSelected){
				//Set background
				Color color = new Color(197, 222, 252, 128);
				setBackground(color);
			}
		}

		return this;
	}


}
