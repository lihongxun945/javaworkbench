package cmm.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cmm.staticValues.Values;
import cmm.ui.components.ChooseFileWindow;
import cmm.ui.components.Explorer;

public class ExplorerPanel extends JPanel{
	private Explorer explorer = new Explorer(new File(Values.getDefaultWorkBench()));
	private JButton button =new JButton("更改目录");
	
	public ExplorerPanel(){
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());
		this.add(explorer,BorderLayout.CENTER);
		
		JPanel panel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		panel.add(new JLabel("资源管理器"));
		//panel.add(button);
		this.add(panel ,BorderLayout.NORTH);
		button.addActionListener(new ButtonListener());
	}
	
	
	private class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ChooseFileWindow chooseFileWindow=new ChooseFileWindow(JFileChooser.DIRECTORIES_ONLY);
			if(chooseFileWindow.ifChosed()){
				Values.setDefaultWorkBench(chooseFileWindow.getFile().getAbsolutePath());
				ExplorerPanel.this.remove(explorer);
				explorer=new Explorer(new File(Values.getDefaultWorkBench()));
				ExplorerPanel.this.add(explorer,BorderLayout.CENTER);
				ExplorerPanel.this.repaint();
			}
		}
	}
	
	
}
