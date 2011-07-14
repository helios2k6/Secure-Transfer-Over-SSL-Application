package com.nlogneg.view;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.nlogneg.SecureFacade;

public class MainWindowMediator extends Mediator{
	
	private MainWindow mainWindow;
	
	private static final String[] NOTIFICATION_INTERESTS = {
		SecureFacade.LOCAL_SHARE_FOLDER_FILE_TABLE_UPDATED_NOTIFICATION
	};
	
	public MainWindowMediator(MainWindow mainWindow){
		super("Main Window Mediator", mainWindow);
		this.mainWindow = mainWindow;
	}
	
	@Override
	public String[] listNotificationInterests(){
		return NOTIFICATION_INTERESTS;
	}
	
	@Override
	public void handleNotification(INotification notification){
		String n = notification.getName();
		
		if(n.equals(SecureFacade.LOCAL_SHARE_FOLDER_FILE_TABLE_UPDATED_NOTIFICATION)){
			handleLocalShareFolderFileTableUpdated(notification);
		}
	}
	
	private void handleLocalShareFolderFileTableUpdated(INotification notification){
		FileTable table = (FileTable) notification.getBody();
		mainWindow.drawToLocalShareFolderPane(table);
	}
}
