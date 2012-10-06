package componets;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import sun.security.krb5.Config;




//确认 对话框,仅供提醒用 不提供任何操作
public class ConfirmDialog{
	
	public ConfirmDialog(String message,JFrame f){
		
		Dialog dialog=new Dialog(f);
		dialog.setTitle("五子棋提醒");
		dialog.setModal(true);
		dialog.setLayout(new GridLayout(2,1));
		dialog.setLocation(400, 250);
		
		
		JLabel lb1=new JLabel(message);
		dialog.add(lb1);
		
		JButton bt1=new JButton("确认");
		bt1.addActionListener(new ConfirmActionListener(dialog));
		dialog.add(bt1);
		dialog.pack();
		dialog.setResizable(false);
		dialog.setVisible(true);
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
