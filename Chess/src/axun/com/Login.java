package axun.com;
import userInterface.com.*;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import componets.ConfirmDialog;

import axun.com.Chess.bt1Listener;


public class Login {
	//GUI
	private LoginUI lg=new LoginUI();
	
	//账号密码
	private String userId="阿训";
	private String password="123456";
	
	private boolean isLogin=false;
	public String serverIp="localhost";	//服务器地址
	public int serverPort=6666;	//服务器端口
	Socket s=null;
	Socket chats=null;
	
	public Login(){
		lg.exitbt.addActionListener(new ExitbtListener());
		lg.loginbt.addActionListener(new LoginbtListener());
		lg.jbtRegister.addActionListener(new JbtRegisterListener());
	
	}
	
	public void login(String userId,String password,String serverIp){
		this.userId=userId;
		this.password=password;
		this.serverIp=serverIp;
		//连接服务器
		LoginServer ls=new LoginServer(serverIp,serverPort,userId,password);
		if(ls.getResult()==0){
			s=ls.getSocket();
			chats=ls.getChatSocket();
			isLogin=true;
			lg.j.dispose();	//连接完成就返回
			new GameCenter(s,chats,getServerIp(),userId);
		}else if(ls.getResult()==2){
			new ConfirmDialog("登录失败！用户名或密码错误！",lg.j);
		}else if(ls.getResult()==1){
			new ConfirmDialog("登录失败！无法连接服务器!请确保服务器的地址是正确的！",lg.j);
		}
		
	}
	
	public boolean isLogin(){
		return isLogin;
	}
	
	
	
	public String getServerIp(){
		return serverIp;
	}
	
	public int getServerPort(){
		return serverPort;
	}
	
	public static void main(String[] args) {
		Login lg=new Login();
		
	}
	
	//////////////////////////////////////////////////
	//一下是监听
	///////////////////////////////////////////////////
	
	/**
	 *退出按钮监听
	 */
	class ExitbtListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			
		}
		
	}
	
	/**
	 *登陆按钮监听
	 */
	class LoginbtListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			login(lg.userIdtf.getText(),lg.passwordtf.getText(),lg.serverIptf.getText());
			
		}
		
	}
	
	//注册按钮监听
	class JbtRegisterListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			new Registe(serverIp);
		}
		
	}
}

//登陆

class LoginServer {
	private String ip="localhost";
	private int port=2222;
	private DataOutputStream dos=null;
	private DataInputStream dis=null;
	private Socket s=null;
	private Socket chats=null;
	private boolean result=false;
	private int flag=2;		//0成功，1，无法连接服务器，2 用户名或密码错误
	
	public LoginServer(String ips,int p,String userId,String password){
		
		ip=ips;
		port=p;
		flag=2;
		//while(true){
			try{
				//创建两个链接，其中一个专门用作聊天
				s=new Socket(ip,port);
				chats=new Socket(ip,6667);
				OutputStream os=s.getOutputStream();
				InputStream is=s.getInputStream();
				
				dos=new DataOutputStream(os);
				dis=new DataInputStream(is);
				
				//发送用户名和密码
				System.out.println("发送用户名和密码……");
				dos.writeUTF(userId);
				dos.writeUTF(password);
				if(dis.readBoolean()){
					flag=0;
				}
				else flag=2;
			}catch(Exception e){
				System.out.println("与服务器连接失败！请填写正确的服务器ip！");
				flag=1;
				
			}
			
		//}
		
	
	}
	
	public Socket getSocket(){
		return s;
	}
	
	public Socket getChatSocket(){
		return chats;
	}
	
	public int getResult(){
		return flag;
	}
}