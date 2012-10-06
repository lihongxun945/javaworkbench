package cmm.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cmm.functions.Files;

public class CreateFileListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		new Files().createFile();
	}

}
