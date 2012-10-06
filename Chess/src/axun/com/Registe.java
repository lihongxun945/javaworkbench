package axun.com;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import componets.ConfirmAndExitDialog;
import componets.ConfirmDialog;


import userInterface.com.*;

public class Registe {
	private Register rg=new Register();
	private String ip="localhost";
	private int port=6666;
	
	public Registe(String ip){
		rg.jbtOK.addActionListener(new JbtOKListener());
		this.ip=ip;
	}
	
	//注册
	public void registe(){
		int result=0;
		Socket s=null;
		try{
			if(!(rg.jpfPassword.getText().equals(rg.jpfPasswordConfirm.getText()))){
				new ConfirmDialog("注册失败！两次密码输入不一致！",rg);
				return;
			}
			
			System.out.print(ip+","+port);
			s=new Socket(ip,port);

			OutputStream os=s.getOutputStream();
			InputStream is=s.getInputStream();
			
			DataOutputStream dos=new DataOutputStream(os);
			DataInputStream dis=new DataInputStream(is);
			
			//发送用户名和密码
			System.out.println("正在注册……");
			dos.writeUTF("Registe");
			//System.out.println("发送用户名……"+rg.jtfID.getText());
			dos.writeUTF(rg.jtfID.getText());
			//System.out.println("发送密码……"+rg.jpfPassword.getText());
			dos.writeUTF(rg.jpfPassword.getText());
			//System.out.println("发送昵称……"+rg.jtfName.getText());
			dos.writeUTF(rg.jtfName.getText());

			result=dis.readInt();
		}catch(Exception e){
			System.out.println("与服务器连接失败！请填写正确的服务器ip！");
			result=2;
			
		}
		if(result==0) {
			System.out.println("注册成功！");
			new ConfirmAndExitDialog("恭喜！注册成功！",rg);		//对话框提醒
			
			//break;
		
		}
		else if(result==1){
			System.out.println("注册失败！");
			new ConfirmDialog("注册失败！该用户名已存在！",rg);
		}else if(result==2){
			System.out.println("注册失败！");
			new ConfirmDialog("注册失败！无法连接服务器！",rg);
		}
		
		
	}
	
	//注册按钮监听
	class JbtOKListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			registe();
		}

		
	}
}
