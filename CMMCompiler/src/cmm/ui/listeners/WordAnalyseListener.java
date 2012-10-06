package cmm.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cmm.functions.WordParse;
import cmm.functions.WordParse;
import cmm.ui.MainFrame;

public class WordAnalyseListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		WordParse wordAnalyse=new WordParse(MainFrame.textPane.getInputString());
		wordAnalyse.start();
		MainFrame.outputPanel.wordOutput(wordAnalyse.getOutputString());
		MainFrame.outputPanel.select(0);
	}
	
}
