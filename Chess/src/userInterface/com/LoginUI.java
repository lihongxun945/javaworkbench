package userInterface.com;

import javax.swing.*;
import java.awt.*;

import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginUI {
	public JFrame j=new JFrame("五子棋登陆");

//	屏幕长宽，颜色棕色
	
	private int scWidth=Toolkit.getDefaultToolkit().getScreenSize().width;
	private int scHeight=Toolkit.getDefaultToolkit().getScreenSize().height;
	private Color brown = new Color(142,78,30);
	
//	主界面
	JPanel jpMain;
	

//	三个JLabel, 用户ID，密码，服务器IP
	JLabel jlID,jlPassword,jlServerIp;
	
//	两个JTextField， 用户ID，服务器IP
	public JTextField userIdtf,serverIptf;
	
//	一个JPasswordField, 密码
	public JPasswordField passwordtf;
	
//	三个JButton， 登录，注册，退出
	public JButton loginbt,jbtRegister,exitbt;
	
	JLabel jlLog,jlRegister,jlExit;
	
	
	public LoginUI(){
		j.setBounds((scWidth-400)/2,(scHeight-300)/2,400,300);
		j.setTitle("^悠嘻欢乐五子棋^  登录");
		j.setResizable(false);
		j.setIconImage(new ImageIcon("image/logo.png").getImage());
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//j.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
//		jpMain初始化
		jpMain= new JpMain();
		jpMain.setBounds(0, 0, 400, 300);
		jpMain.setLayout(null);
		
//		jpMain 各部件

//		JLabel ID
		jlID=new JLabel("账号：",JLabel.RIGHT);
		jlID.setBounds(10, 30, 130, 30);
//		jlID.setBorder(new LineBorder(Color.BLACK,3));
		jlID.setFont(new Font("微软雅黑",1,18));
		jlID.setForeground(Color.BLACK);
		
//		JLabel Password
		jlPassword = new JLabel("密码：",JLabel.RIGHT);
		jlPassword.setBounds(10, 70, 130, 30);
//		jlPassword.setBorder(new LineBorder(Color.BLACK,3));
		jlPassword.setFont(new Font("微软雅黑",1,18));
		jlPassword.setForeground(Color.BLACK);
		
//		JLabel ServerIP
		jlServerIp=new JLabel("服务器IP：",JLabel.RIGHT);
		jlServerIp.setBounds(10, 110, 130, 30);
//		jlServerIp.setBorder(new LineBorder(Color.BLACK,3));
		jlServerIp.setFont(new Font("微软雅黑",1,18));
		jlServerIp.setForeground(Color.BLACK);
		
//		JTextField ID
		userIdtf = new JTextField();
		userIdtf.setBounds(150, 30, 160, 30);
		userIdtf.setFont(new Font("微软雅黑",1,18));
		userIdtf.setForeground(Color.BLACK);
		userIdtf.setOpaque(false);
		userIdtf.setBorder(new LineBorder(Color.BLACK,2));
		
//		JPasswordField password
		passwordtf = new JPasswordField();
		passwordtf.setBounds(150, 70, 160, 30);
		passwordtf.setFont(new Font("微软雅黑",1,18));
		passwordtf.setEchoChar('*');
		passwordtf.setOpaque(false);
		passwordtf.setBorder(new LineBorder(Color.BLACK,2));
		
//		JTextField ServerIp
		serverIptf=new JTextField("localhost");
		serverIptf.setBounds(150, 110, 160, 30);
		serverIptf.setFont(new Font("微软雅黑",1,18));
		serverIptf.setOpaque(false);
		serverIptf.setBorder(new LineBorder(Color.BLACK,2));
		
//		JButton logon
		loginbt = new JButton(new ImageIcon("image/log.png"));
		loginbt.setOpaque(false);
		loginbt.setContentAreaFilled(false);
		loginbt.setBounds(90, 160, 50, 50);
		loginbt.setBorder(null);
//		jbtLogon.addActionListener(new LogonListener());
		
//		JButton register
		jbtRegister = new JButton(new ImageIcon("image/register.gif"));
		jbtRegister.setBounds(180, 160	,50, 50);
		jbtRegister.setOpaque(false);
		jbtRegister.setContentAreaFilled(false);
		jbtRegister.setBorder(null);
//		jbtRegister.addActionListener(new RegisterListener());
		
//		JButton exit
		exitbt = new JButton(new ImageIcon("image/exit.gif"));
		exitbt.setBounds(270, 160, 50, 50);
		exitbt.setOpaque(false);
		exitbt.setContentAreaFilled(false);
		exitbt.setBorder(null);
//		exitbt.addActionListener(new ExitListener());
		
		jlLog = new JLabel("登录",JLabel.CENTER);
		jlLog.setFont(new Font("微软雅黑",1,16));
		jlLog.setBounds(90, 210, 50, 30);

		jlRegister = new JLabel("注册新用户",JLabel.LEFT);
		jlRegister.setFont(new Font("微软雅黑",1,16));
		jlRegister.setBounds(170, 210, 100, 30);

		jlExit = new JLabel("取消",JLabel.RIGHT);
		jlExit.setFont(new Font("微软雅黑",1,16));
		jlExit.setBounds(260, 210, 50, 30);
		
		
		jpMain.add(jlID);
		jpMain.add(jlPassword);
		jpMain.add(jlServerIp);
		jpMain.add(userIdtf);
		jpMain.add(passwordtf);
		jpMain.add(serverIptf);
		jpMain.add(loginbt);
		jpMain.add(jbtRegister);
		jpMain.add(exitbt);
		jpMain.add(jlLog);
		jpMain.add(jlRegister);
		jpMain.add(jlExit);
		
		j.add(jpMain);
		
		j.setVisible(true);
		
		//**********************************监听******************************
		
	}
	 
	


//	
////	登录 jbtLogon监听
//	public class LogonListener implements ActionListener {
//		public void actionPerformed (ActionEvent e){
//			new GameHall();
//			dispose();
//		}
//	}
//	
////	注册jbtRegister监听
//	public class RegisterListener implements ActionListener {
//		public void actionPerformed (ActionEvent e){
//			new Register();
//		}
//	}
////	退出 jbtExit监听
//	public class ExitListener implements ActionListener {
//		public void actionPerformed (ActionEvent e){
//			System.exit(0);
//		}
//	}
//	
//	主界面 jpMain 的class
	class JpMain extends JPanel {	
		public JpMain (){
			super();
		}
//		JpMain 重写paint 方法， 添加背景
		public void paintComponent(Graphics g){
			g.drawImage(new ImageIcon("image/logbackground.jpg").getImage(), 0, 0, 400, 300, this);
		}
	}
	
	
//	
////	测试方法
//	public static void main(String [] args){
//		LogOn g=new LogOn();
//		
//	}
}
