package cmm.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cmm.functions.GramParse;
import cmm.functions.WordParse;
import cmm.ui.MainFrame;

public class GramParseListener implements ActionListener{

	public void actionPerformed(ActionEvent arg0) {
		WordParse wordParse=new WordParse(MainFrame.textPane.getInputString());
		wordParse.start();
		if(wordParse.hasError()){	//发生错误
			MainFrame.outputPanel.gramOutput(wordParse.getOutputString()+"\n语法分析无法进行");
			MainFrame.outputPanel.select(1);
			return;
		}
		
		GramParse gramParse=new GramParse(MainFrame.textPane.getInputString(),wordParse.getCmmTokens(),wordParse.getTokenAmount());
		gramParse.start();
		gramParse.showGramTree();
		MainFrame.outputPanel.gramOutput(gramParse.getOutputString());
		MainFrame.outputPanel.select(1);
	}

}
