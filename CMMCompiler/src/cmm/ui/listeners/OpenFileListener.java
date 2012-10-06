package cmm.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import cmm.functions.Files;
import cmm.staticValues.Values;
import cmm.ui.components.ChooseFileWindow;

public class OpenFileListener implements ActionListener{
	
	public void actionPerformed(ActionEvent arg0) {
		ChooseFileWindow chooseFileWindow=new ChooseFileWindow(JFileChooser.FILES_ONLY);
		if(chooseFileWindow.ifChosed()){
			new Files().openFile(chooseFileWindow.getFile());
		}
	}
	
}
