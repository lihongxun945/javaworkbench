package cmm.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cmm.functions.Files;
import cmm.staticValues.Values;
import cmm.ui.components.ChooseFileWindow;
import cmm.ui.components.SaveFileWindow;

public class SaveFileListener implements ActionListener{
	
	public void actionPerformed(ActionEvent arg0) {
		new Files().saveFile();
	}
	
}
