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
import javax.swing.JTextField;


import cmm.client.CMMClient;
import cmm.ui.MainFrame;

import sun.security.krb5.Config;




//确认 对话框,仅供提醒用 不提供任何操作
public class InputFrame{
	private static String inputString="";
	private Dialog dialog=null;
	JTextField jTextField=null;
	private int type=1;
	public InputFrame(int type){	//1 int 2 real
		this.type=type;
		dialog=new Dialog(CMMClient.mainFrame);
		dialog.setTitle("输入");
		dialog.setModal(true);
		dialog.setLayout(new GridLayout(3,1));
		
		JLabel jLabel=new JLabel();
		if(type==1){
			jLabel.setText("请输入一个整型数：");
		}
		else if(type==2){
			jLabel.setText("请输入一个浮点型数：");
		}
		dialog.add(jLabel);
		jTextField=new JTextField();
		dialog.add(jTextField);
		
		JButton bt1=new JButton("确认");
		JButton bt2=new JButton("取消");
		bt1.addActionListener(new ConfirmActionListener());
		bt2.addActionListener(new CancelActionListener());
		JPanel temPanel=new JPanel();
		temPanel.add(bt1);
		//temPanel.add(bt2); //不能不输入
		dialog.add(temPanel);
		dialog.pack();
		dialog.setResizable(false);
		dialog.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - dialog.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - dialog.getHeight()) / 2);
		
		dialog.setVisible(true);
		
	}

	public static String getInputString() {
		return inputString;
	}
	public static void setInputString(String input) {
		inputString = input;
	}

class ConfirmActionListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//**检查输入类型
		String string=jTextField.getText();
		try{
			if(type==1){
				Integer.parseInt(string);
			}else if(type==2){
				Double.parseDouble(string);
			}
		}catch (Exception ex) {
			new ConfirmDialog("错误！请输入一个"+(type==1?"整数":"实数"));
			return ;
		}
		setInputString(string);
		dialog.dispose();
	}
	
}

class CancelActionListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		dialog.dispose();
	}
	
}
}
