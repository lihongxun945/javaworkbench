package userInterface.com;

import javax.swing.*;
import java.awt.*;

import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LogOn	extends JFrame {

//	屏幕长宽，颜色棕色
	private int scWidth=Toolkit.getDefaultToolkit().getScreenSize().width;
	private int scHeight=Toolkit.getDefaultToolkit().getScreenSize().height;
	private Color brown = new Color(142,78,30);
	
//	主界面
	private JPanel jpMain;
	

//	三个JLabel, 用户ID，密码，服务器IP
	JLabel jlID,jlPassword,jlServerIp;
	
//	两个JTextField， 用户ID，服务器IP
	JTextField jtfID,jtfSeverIp;
	
//	一个JPasswordField, 密码
	JPasswordField jpfPassword;
	
//	三个JButton， 登录，注册，退出
	JButton jbtLogon,jbtRegister,jbtExit;
	
//	三个JLabel   登录， 注册， 退出
	JLabel jlLog, jlRegister, jlExit;
	
	
	public LogOn(){
		setBounds((scWidth-400)/2,(scHeight-300)/2,400,300);
		setTitle("^悠嘻欢乐五子棋^  登录");
		setIconImage(new ImageIcon("image/LOGO.PNG").getImage());
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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
		JTextField jtfID = new JTextField();
		jtfID.setBounds(150, 30, 160, 30);
		jtfID.setBorder(new LineBorder(Color.BLACK,2));
		jtfID.setOpaque(false);
		jtfID.setFont(new Font("微软雅黑",1,18));
		jtfID.setForeground(Color.BLACK);
		
//		JPasswordField password
		JPasswordField jpfPassword = new JPasswordField();
		jpfPassword.setBounds(150, 70, 160, 30);
		jpfPassword.setBorder(new LineBorder(Color.BLACK,2));
		jpfPassword.setOpaque(false);
		jpfPassword.setFont(new Font("微软雅黑",1,18));
		jpfPassword.setEchoChar('*');
		
//		JTextField ServerIp
		jtfSeverIp=new JTextField();
		jtfSeverIp.setBorder(new LineBorder(Color.BLACK,2));
		jtfSeverIp.setOpaque(false);
		jtfSeverIp.setBounds(150, 110, 160, 30);
		jtfSeverIp.setFont(new Font("微软雅黑",1,18));
		
//		JButton logon
		jbtLogon=new JButton(new ImageIcon("image/log.png"));
		jbtLogon.setBounds(90, 170, 50, 50);
		jbtLogon.setOpaque(false);
		jbtLogon.setContentAreaFilled(false);
		jbtLogon.setBorder(null);
		jbtLogon.addActionListener(new LogonListener());
		
//		JButton register
		jbtRegister = new JButton(new ImageIcon("image/register.gif"));
		jbtRegister.setBounds(170, 170	,50, 50);
		jbtRegister.setOpaque(false);
		jbtRegister.setContentAreaFilled(false);
		jbtRegister.setBorder(null);
		jbtRegister.addActionListener(new RegisterListener());
		
//		JButton exit
		jbtExit = new JButton(new ImageIcon("image/exit.gif"));
		jbtExit.setOpaque(false);
		jbtExit.setContentAreaFilled(false);
		jbtExit.setBorder(null);
		jbtExit.setBounds(250, 170, 50, 50);
		jbtExit.addActionListener(new ExitListener());
		
//		JLabel 
		jlLog = new JLabel("登录");
		jlLog.setFont(new Font("微软雅黑",1,14));
		jlLog.setBounds(105, 220, 100, 30);
		
		jlRegister = new JLabel("注册");
		jlRegister.setFont(new Font("微软雅黑",1,14));
		jlRegister.setBounds(180, 220, 100, 30);
		
		jlExit = new JLabel("退出");
		jlExit.setFont(new Font("微软雅黑",1,14));
		jlExit.setBounds(260, 220, 100, 30);
		
		jpMain.add(jlID);
		jpMain.add(jlPassword);
		jpMain.add(jlServerIp);
		jpMain.add(jtfID);
		jpMain.add(jpfPassword);
		jpMain.add(jtfSeverIp);
		jpMain.add(jbtLogon);
		jpMain.add(jbtRegister);
		jpMain.add(jbtExit);
		jpMain.add(jlLog);
		jpMain.add(jlRegister);
		jpMain.add(jlExit);
		
		add(jpMain);
		
		setVisible(true);
	}
	


	
//	登录 jbtLogon监听
	public class LogonListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			new GameHall();
			dispose();
		}
	}
	
//	注册jbtRegister监听
	public class RegisterListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			new Register();
		}
	}
//	退出 jbtExit监听
	public class ExitListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			System.exit(0);
		}
	}
	
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
	
	
	
//	测试方法
	public static void main(String [] args){
		LogOn g=new LogOn();
		
	}
}
