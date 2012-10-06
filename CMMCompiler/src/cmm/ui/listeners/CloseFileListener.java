package cmm.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cmm.functions.Files;
import cmm.ui.MainFrame;

public class CloseFileListener implements ActionListener{

	public void actionPerformed(ActionEvent arg0) {
		new Files().closeFile();
	}
	
}
