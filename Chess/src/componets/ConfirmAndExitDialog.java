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




//确认 对话框,提醒用 点击确认后退出
public class ConfirmAndExitDialog{
	
	public ConfirmAndExitDialog(String message,JFrame f){
		
		Dialog dialog=new Dialog(f);
		dialog.setTitle("五子棋提醒");
		dialog.setModal(true);
		dialog.setLayout(new GridLayout(2,1));
		dialog.setLocation(400, 250);
		
		
		JLabel lb1=new JLabel(message);
		dialog.add(lb1);
		
		JButton bt1=new JButton("确认");
		bt1.addActionListener(new ConfirmAndExitActionListener(dialog,f));
		dialog.add(bt1);
		dialog.pack();
		dialog.setResizable(false);
		dialog.setVisible(true);
	}
	
}

class ConfirmAndExitActionListener implements ActionListener{
	private Dialog d=null;
	private JFrame f=null;
	public ConfirmAndExitActionListener(Dialog d,JFrame f){
		this.d=d;
		this.f=f;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		d.dispose();
		f.dispose();
	}
	
}
