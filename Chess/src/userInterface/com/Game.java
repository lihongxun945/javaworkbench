package userInterface.com;

import javax.swing.*;

import java.awt.*;

import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Game extends JFrame{

//	屏幕长宽，颜色棕色
	private int scWidth=Toolkit.getDefaultToolkit().getScreenSize().width;
	private int scHeight=Toolkit.getDefaultToolkit().getScreenSize().height;
	private Color brown = new Color(142,78,30);
	
//	主界面
	JPanel jpMain = new JpMain();
	
//		五个JButton 开始游戏，重新开始，退出游戏，人机大战，悔棋
	JButton jbtStart,jbtRestart,jbtExit,jbtVsPC,jbtRegret;
	
//		三个JScrollPane, 下棋记录&对话&输入, 分别包含JTextArea jpRecord,jpDialg，jpInput
	JScrollPane jspRecord, jspDialog, jspInput;
	public JTextArea jtaRecord,jtaDialog,jtaInput;
	
	public String inputMessage;					// 输入框 的信息
	
	
//		一个子Panel 显示提示信息 和 时间
	JPanel jpInformation, jpTime;
	
//		四个JLabel 用于显示玩家头像和棋子颜色
	JLabel jlPlayer1,jlPlayer2,jlPlayer1Disk,jlPlayer2Disk;
	
//	构造方法
	public Game(){
		this.setBounds((scWidth-800)/2, (scHeight-600)/2, 800,600);
		this.setTitle("^悠嘻欢乐五子棋^ 游戏");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		
		jpMain.setBounds(0, 0, 800, 600);
		jpMain.setLayout(null);
		
//		开始游戏 按钮  jbtStart
		jbtStart = new JButton ("开始游戏");
		jbtStart.setBounds(30, 40, 45, 45);
		jbtStart.setBorder(new LineBorder(Color.BLACK,3));
		jbtStart.addActionListener(new StartListener());
		
		
//		人机大战 按钮 jbtVsPC
		jbtVsPC = new JButton("人机大战");
		jbtVsPC.setBounds(30, 125, 45, 45);
		jbtVsPC.setBorder(new LineBorder(Color.BLACK,3));
		jbtVsPC.addActionListener(new VsPCListener());
		
		
//		悔棋 按钮 jbtRegret
		jbtRegret = new JButton("悔棋");
		jbtRegret.setBounds(30, 210, 45, 45);
		jbtRegret.setBorder(new LineBorder(Color.BLACK,3));
		jbtRegret.addActionListener(new RegretListener());
	
//		重新开始 按钮 jbtRestart
		jbtRestart = new JButton("重新开始");
		jbtRestart.setBounds(30, 295, 45, 45);
		jbtRestart.setBorder(new LineBorder(Color.BLACK,3));
		jbtRestart.addActionListener(new RestartListener());
		
//		退出游戏 按钮 JbtExit
		jbtExit = new JButton("退出游戏");
		jbtExit.setBounds(30, 380, 45, 45);
		jbtExit.setBorder(new LineBorder(Color.BLACK,3));
		jbtExit.addActionListener(new ExitListener());
		
//		JscrollPane 下棋记录， 包含JTextArea jtaRecord
		jtaRecord = new JTextArea();
		jtaRecord.setLineWrap(true);
		jtaRecord.setOpaque(false);
		jtaRecord.setEditable(false);
		jspRecord = new JScrollPane(jtaRecord);
		jspRecord.setBounds(560,20,220,100);
		jspRecord.setBorder(new LineBorder(Color.BLACK,3));
		jspRecord.setOpaque(false);
		jspRecord.getViewport().setOpaque(false);
		
//		JScrollPane 对话框，包含 JTextArea jtaDialog
		jtaDialog = new JTextArea();
		jtaDialog.setLineWrap(true);
		jtaDialog.setOpaque(false);
		jtaDialog.setEditable(false);
		jspDialog = new JScrollPane(jtaDialog);
		jspDialog.setBounds(560, 140, 220, 300);
		jspDialog.setBorder(new LineBorder(Color.BLACK,3));
		jspDialog.setOpaque(false);
		jspDialog.getViewport().setOpaque(false);
		
//		JTextArea 输入框  包含JTextArea jtaInput
		jtaInput = new JTextArea();
		jtaInput.setLineWrap(true);
		jtaInput.setOpaque(false);
		jtaInput.addKeyListener(new InputListener());
		jspInput = new JScrollPane(jtaInput);
		jspInput.setBounds(560, 460, 220, 80);
		jspInput.setBorder(new LineBorder(Color.BLACK,3));
		jspInput.setOpaque(false);
		jspInput.getViewport().setOpaque(false);
		
//		玩家1头像，固定在左方显示，是建立游戏的玩家  icon1代表玩家1头像图片，具体路径根据实际判断
		ImageIcon icon1 = new ImageIcon("image/headImage/1small.png");
		jlPlayer1 = new JLabel(icon1);
		jlPlayer1.setBounds(200, 500, 45, 45);
		
//		玩家2头像，固定在右边显示，是加入游戏的玩家或者电脑   icon2代表玩家2头像图片，具体路径根据实际判断
		ImageIcon icon2 = new ImageIcon("image/headImage/3small.png");
		jlPlayer2 = new JLabel(icon2);
		jlPlayer2.setBounds(400, 500, 45, 45);
		
//		玩家1棋盅，颜色根据实际情况判断， icon3代表玩家1棋子，具体路径根据实际判断
		ImageIcon icon3 = new ImageIcon("image/whiteDisk.gif");
		jlPlayer1Disk = new JLabel(icon3);
		jlPlayer1Disk.setBounds(250, 500, 45, 45);
		
//		玩家2棋盅，颜色根据实际情况判断， icon4代表玩家2棋子，具体路径根据实际判断
		ImageIcon icon4 = new ImageIcon("image/blackDisk.gif");
		jlPlayer2Disk = new JLabel(icon4);
		jlPlayer2Disk.setBounds(350, 500, 45, 45);		
		
//		添加各个部件
		this.add(jbtStart);
		this.add(jbtVsPC);
		this.add(jbtRegret);
		this.add(jbtRestart);
		this.add(jbtExit);
		this.add(jspRecord);
		this.add(jspDialog);
		this.add(jspInput);
		this.add(jlPlayer1);
		this.add(jlPlayer2);
		this.add(jlPlayer1Disk);
		this.add(jlPlayer2Disk);
		
		this.add(jpMain);
		
		this.setVisible(true);
	}
	
	
//	开始 按钮 jbtStart 监听
	public class StartListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			JOptionPane.showMessageDialog(null, "开始游戏");
		}
	}
	
//	人机大战 jbtVsPC 监听
	public class VsPCListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			JOptionPane.showMessageDialog(null, "人机大战");
		}
	}
	
//	悔棋 jbtRegret 监听
	public class RegretListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			JOptionPane.showMessageDialog(null, "悔棋");
		}
	}
	
//	重新开始 jbtRestart 监听
	public class RestartListener implements ActionListener {
		public void actionPerformed (ActionEvent E){
			JOptionPane.showMessageDialog(null,"重新开始");
		}
	}
	 
//	退出游戏 jbtExit 监听
	public class ExitListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			JOptionPane.showMessageDialog(null, "退出");
		}
	}
	
//	输入 jtaInput 监听
	public class InputListener extends KeyAdapter {
			
			
			public void keyPressed (KeyEvent e){
				if(e.getKeyChar()==KeyEvent.VK_ENTER){		//如果输入了 回车
				JTextArea jtaf=(JTextArea)e.getSource();
				inputMessage = jtaf.getText();				// 输入框的信息  赋值 给  inputMessage
				jtaf.setText("");							// 输入框 清空
				
				
				}
		}
	}
	
	
	
//	JpMain
	class JpMain extends JPanel {
		public JpMain(){
			super();
		}
		
//		重写jpMain的paint方法，添加背景,标记棋盘
		public void paintComponent (Graphics g){
			g.drawImage(new ImageIcon("image/background.jpg").getImage(), 0, 0, 800, 600, this);
			g.drawLine(100, 40, 100, 490);
			g.drawLine(100,	40, 550, 40);
			g.drawLine(100, 490, 550, 490);
			g.drawLine(550, 40, 550, 490);
		}
	}
	
//	测试方法
	public static void main(String[] args) {
		new Game();
	}
}
