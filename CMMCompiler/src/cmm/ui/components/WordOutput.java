package cmm.ui.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class WordOutput extends JTextArea{
	
	public WordOutput(){
		this.setText("");
		this.setBackground(new Color(220,220,220));
		this.setBorder(BorderFactory.createEtchedBorder());
	}
}
