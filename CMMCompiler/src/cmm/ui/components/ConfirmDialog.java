package cmm.ui.components;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import cmm.client.*;
import cmm.ui.MainFrame;

import sun.security.krb5.Config;




//确认 对话框,仅供提醒用 不提供任何操作
public class ConfirmDialog{
	public ConfirmDialog(String message,JFrame f){
		
		Dialog dialog=new Dialog(f);
		dialog.setTitle("提醒");
		dialog.setModal(true);
		dialog.setLayout(new GridLayout(2,1));
		
		
		JTextArea jTextArea=new JTextArea(message);
		jTextArea.setEditable(false);
		dialog.add(jTextArea);
		
		JButton bt1=new JButton("确认");
		bt1.addActionListener(new ConfirmActionListener(dialog));
		JPanel temPanel=new JPanel();
		temPanel.add(bt1);
		dialog.add(temPanel);
		dialog.pack();
		dialog.setResizable(false);
		dialog.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - dialog.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - dialog.getHeight()) / 2);
		
		dialog.setVisible(true);
		
	}
	public ConfirmDialog(String s){
		this(s,CMMClient.mainFrame);
	}
}

class ConfirmActionListener implements ActionListener{
	private Dialog d=null;
	public ConfirmActionListener(Dialog d){
		this.d=d;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		d.dispose();
	}
	
}
