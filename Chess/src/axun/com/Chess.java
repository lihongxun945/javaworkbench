package axun.com;


import Compute.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;


import javax.swing.*;
import componets.*;





public class Chess extends JPanel{
	
	

	
	//GUI
	public JFrame f=new JFrame("五子棋");
	private JPanel pright=new JPanel();
	private JPanel pleft=new JPanel();
	private JPanel p1=new JPanel();
	private JPanel p2=new JPanel();
	private JPanel p3=new JPanel();
	private JButton startbt=new JButton("开始游戏");
	private JButton restartbt=new JButton("重新开始");
	private JButton setbt=new JButton("设置");
	private JButton undobt=new JButton("悔棋");
	private JButton exitbt=new JButton("退出游戏");
	private JButton hhbt=new JButton("双人对战");
	private JButton netbt=new JButton("建立主机");
	private JButton netbt2=new JButton("加入游戏");
	private JButton hcbt=new JButton("普通电脑");
	
	private JButton hcbt2=new JButton("疯狂电脑");
	private JButton endbt=new JButton("结束此局");
	private JLabel lb1=new JLabel("走棋记录：");
	private JLabel timelb=new JLabel("00:00:00");			//计时标签
	private JTextArea tr1=new JTextArea();
	private JLabel myIdlb=new JLabel("我是");
	private JLabel rivalIdlb=new JLabel("对手");
	
	//聊天用的
	private JTextArea chatbox=new JTextArea();
	private JTextArea chatinputbox=new JTextArea();
	private JButton chatbt=new JButton("发送");
	
	
//	*********************增加的 ************************
//	取 屏幕  宽度 高度
	private int scWidth=Toolkit.getDefaultToolkit().getScreenSize().width;
	private int scHeight=Toolkit.getDefaultToolkit().getScreenSize().height;

	// JPanel jpMain 
	JPanel jpMain ;
	
	JScrollPane jspchatinputbox , jspchatbox , jsptr1;
	
	ImageIcon icon1,icon2,icon3,icon4;			// 玩家头像, 棋子
	JLabel jlPlayer1,jlPlayer2,jlPlayer1Disk,jlPlayer2Disk; 		// 显示玩家头像，所执棋子
	
	JLabel jlNormal, jlHappy,jlAngry,jlSad, jlGameStart, jlEasyPC,jlCrazyPC,jlExit ,jlRegret;
//**************************************************************
	
	
	//聊天用的线程
	
	
	//简单类型的变量
	
	//状态记录
	private boolean isStart=false;		//是否已经开始游戏
	public boolean lock=false;				//是否锁定棋盘，锁定之后不能下子
	private int computer=0;						//电脑等级，0表示双人对战，123分别表示难度
	private boolean net=false;						//net表示是否是网络对战
	private boolean undo=true;				//是否允许悔棋
	private boolean wait=false;				//是否正在等待对方下棋
	private int mode=2;						//是否已经选择了一种模式
	int state=0;				//当前状态
	String  rivalId=null;
	String myId=null;
	int compControl=0;			//控制按钮的enable属性
	public int undoNumber=100;			//控制悔棋的次数
	
	
	private String chessboardMessage=null;	//在棋盘上打印消息
	int[][] five;							//一个5*5的小棋盘
	private int last_x=-1,last_y=-1;

	private int[][] x;
	private int[][] record;	//走棋记录
	private int step;		//
	private int player;
	private int time;		//计时 ，表示毫秒数
	
	private TimeRunner timeR=new TimeRunner();	//
	private Thread timeThread=new Thread(timeR);	//计时用的线程
	int i=0;
	
	//网络用的
	
	boolean server=false;	  //是否是主机
	String ip="localhost";				//ip地址
	final int port=12377;				//端口号
	final int port2=12388;				//聊天用的端口号
	private final int ON_LINE=6661;	//发送给服务器，表示自己在线
	private final int ON_GAME=6662;	//发送给服务器表明自己正在游戏中
	private final int ON_WAIT=6662;	//发送给服务器表明自己建立主机，正在等待
	
	NetTrans netTrans=null;
	NetChat netChat=null;
	Thread netTransThread=null;
	Thread netChatThread=null;
	
	
	
	//电脑AI用的
	private AutoPlay ap=new AutoPlay();
	
	//常量
	private final int NET_BEGIN=300;	//表示开始游戏
	private final int NET_END=301;	//表示结束游戏
	
	//零时变量
	Point point=null;
	TextArea inputTextArea=null;
	private String inputString=null;		//暂存inputdialog中的输入
	

	
	
	public Chess(){
		
		
//		*********************************************************************************
//		f.setLayout(new BorderLayout());
//		f.setSize(800,600);
//		
//		f.setLocation(300, 160);
//		f.setResizable(false);			//不可改变大小
//		
//		f.add(pleft,"West");
//		pleft.setLayout(new GridLayout(10,1));
//		//pleft.add(netbt);
//		//pleft.add(netbt2);
//		//pleft.add(hhbt);
//		pleft.add(hcbt);
//		pleft.add(setbt);
//		//pleft.add(exitbt);
//		pleft.add(myIdlb);
//		pleft.add(rivalIdlb);
//		
//		hhbt.addActionListener(new hhbtListener());				
//		hcbt.addActionListener(new hcbtListener());			//人机大战 	OK
//		netbt.addActionListener(new netbtListener());
//		netbt2.addActionListener(new netbt2Listener());
//		
//		
//		this.addMouseListener(new mousel());	//注册监听
//		setSize(500,500);
//		f.add(this,"Center");
//		this.setBackground(Color.green);
//		
//		startbt.addActionListener(new StartbtListener());		// 开始， OK
//		
//		restartbt.addActionListener(new RestartbtListener());	//重新开始。 OK
//		
//		undobt.addActionListener(new UndobtListener());			// 悔棋 。 OK
//		endbt.addActionListener(new EndbtListener());			//退出  OK
//		
//		pright.setLayout(new GridLayout(3,1));
//		pright.setBackground(Color.yellow);
//		f.add(pright,"East");
//		pright.add(p1);
//		pright.add(p2);
//		pright.add(p3);
//		
//		tr1.setEnabled(true);
//		tr1.setBackground(Color.LIGHT_GRAY);
//		tr1.setRows(10);
//		tr1.setColumns(20);
//		tr1.setEditable(false);
//		
//		p1.setLayout(new GridLayout(6,1,0,10));
//		p1.add(startbt);
//		p1.add(restartbt);
//		p1.add(undobt);
//		p1.add(endbt);
//		
//		
//		p1.add(timelb);
//		p1.add(lb1);
//		p2.add(tr1);
//		
//		chatbox.setRows(10);
//		chatbox.setColumns(20);
//		
//		chatinputbox.setRows(1);
//		chatinputbox.setColumns(10);
//		chatbox.setEditable(false);
//		p3.setLayout(new GridLayout(3,1));
//		p3.add(chatbox);
//		p3.add(chatinputbox);
//		p3.add(chatbt);
////		chatbt.addActionListener(new chatbtListener());		// 发送 按钮   改为input 输入框的 keyListener  OK
//		
//		
//		f.setVisible(true);			//注意最后添加这个
//		
		
		//**********************************界面*********************************************
		
		f.setBounds((scWidth-800)/2, (scHeight-600)/3, 800,600);
		f.setResizable(false);
		f.setIconImage(new ImageIcon("image/logo.png").getImage());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("悠嘻欢乐五子棋");
		
		jpMain = new JpMain() ;
		jpMain.setBounds(0, 0, 800, 600);
		jpMain.setLayout(null);
		
//		开始游戏按钮
		startbt = new JButton (new ImageIcon("image/startgame.gif"));
		startbt.setBounds(30, 70, 45, 45);
//		startbt.setBorder(new LineBorder(Color.BLACK,3));
		startbt.setOpaque(false);
		startbt.setContentAreaFilled(false);
		startbt.setBorder(null);
		startbt.addActionListener(new StartbtListener());
		
		
//		开始游戏标签
		jlGameStart = new JLabel("开始游戏");
		jlGameStart.setBounds(20, 115, 100, 40);
		jlGameStart.setFont(new Font("微软雅黑",1,14));
		jlGameStart.setOpaque(false);
		
		
//		人机大战 按钮 jbtVsPC
		hcbt = new JButton(new ImageIcon("image/easyPC.gif"));
		hcbt.setOpaque(false);
		hcbt.setContentAreaFilled(false);
		hcbt.setBorder(null);
		hcbt.setBounds(30, 165, 45, 45);
//		hcbt.setBorder(new LineBorder(Color.BLACK,3));
		hcbt.addActionListener(new hcbtListener());
		
//		简单电脑标签
		jlEasyPC = new JLabel("简单电脑");
		jlEasyPC.setBounds(20, 210, 100, 40);
		jlEasyPC.setFont(new Font("微软雅黑",1,14));
		jlEasyPC.setOpaque(false);
		
//		疯狂电脑 
		hcbt2 = new JButton(new ImageIcon("image/crazyPC.gif"));
		hcbt2.setBounds(30, 250, 45, 45);
		hcbt2.setOpaque(false);
		hcbt2.setContentAreaFilled(false);
		hcbt2.setBorder(null);
//		hcbt2.setBorder(new LineBorder(Color.BLACK,3));
		hcbt2.addActionListener(new hcbt2Listener());
		
//		疯狂电脑 标签
		jlCrazyPC = new JLabel("疯狂电脑");
		jlCrazyPC.setBounds(20, 295, 100, 40);
		jlCrazyPC.setFont(new Font("微软雅黑",1,14));
		jlCrazyPC.setOpaque(false);
		
//		悔棋 按钮 jbtRegret
		undobt = new JButton(new ImageIcon("image/regret.gif"));
		undobt.setBounds(30, 345, 45, 45);
		undobt.setBorder(null);
		undobt.setOpaque(false);
		undobt.setContentAreaFilled(false);
//		undobt.setBorder(new LineBorder(Color.BLACK,3));
		undobt.addActionListener(new UndobtListener());
		
//		悔棋标签
		jlRegret = new JLabel("悔棋（3）");
		jlRegret.setBounds(20, 390, 100, 40);
		jlRegret.setFont(new Font("微软雅黑",1,14));
		jlRegret.setOpaque(false);
		
		
		
//		
////		重新开始 按钮 jbtRestart
//		restartbt = new JButton("重新开始");
//		restartbt.setBounds(30, 295, 45, 45);
//		restartbt.setBorder(new LineBorder(Color.BLACK,3));
//		restartbt.addActionListener(new RestartbtListener());
		
//		退出游戏 按钮 JbtExit
		endbt = new JButton(new ImageIcon("image/exit.gif"));
		endbt.setBounds(30, 430, 45, 45);
		endbt.setOpaque(false);
		endbt.setContentAreaFilled(false);
		endbt.setBorder(null);
//		endbt.setBorder(new LineBorder(Color.BLACK,3));
		endbt.addActionListener(new EndbtListener());
		
//		退出游戏标签
		jlExit = new JLabel("退出游戏");
		jlExit.setBounds(20, 475, 100, 40);
		jlExit.setFont(new Font("微软雅黑",1,14));
		jlExit.setOpaque(false);
		
//		JscrollPane 下棋记录， 包含JTextArea jtaRecord

		tr1.setLineWrap(true);
		tr1.setOpaque(false);
		tr1.setFont(new Font("微软雅黑",1,16));
//		tr1.setEditable(false);
		jsptr1 = new JScrollPane(tr1);
		jsptr1.setBounds(570,20,200,100);
//		jsptr1.setBorder(new LineBorder(Color.BLACK,3));
		jsptr1.setBorder(null);
		jsptr1.setOpaque(false);
		jsptr1.getViewport().setOpaque(false);
		
//		JScrollPane 对话框，包含 JTextArea jtaDialog
		
		chatbox.setLineWrap(true);
		chatbox.setOpaque(false);
		chatbox.setFont(new Font("微软雅黑",1,16));
		chatbox.setEditable(false);
		jspchatbox = new JScrollPane(chatbox);
		jspchatbox.setBounds(570, 140, 200, 300);
//		jspchatbox.setBorder(new LineBorder(Color.BLACK,3));
		jspchatbox.setBorder(null);
		jspchatbox.setOpaque(false);
		jspchatbox.getViewport().setOpaque(false);	
		
//		JTextArea 输入框  包含JTextArea jtaInput
		
		chatinputbox.setLineWrap(true);
		chatinputbox.setOpaque(false);
		chatinputbox.setFont(new Font("微软雅黑",1,16));
		chatinputbox.addKeyListener(new InputListener());
		jspchatinputbox = new JScrollPane(chatinputbox);
		jspchatinputbox.setBounds(570, 460, 200, 78);
//		jspchatinputbox.setBorder(new LineBorder(Color.WHITE,3));
		jspchatinputbox.setBorder(null);
		jspchatinputbox.setOpaque(false);
		jspchatinputbox.getViewport().setOpaque(false);
		
//		玩家1头像，固定在左方显示，是建立游戏的玩家  icon1代表玩家1头像图片，具体路径根据实际判断
		icon1 = new ImageIcon("image/headImage/1small.png");
		jlPlayer1 = new JLabel(icon1);
		jlPlayer1.setBounds(200, 500, 45, 45);
		
//		玩家2头像，固定在右边显示，是加入游戏的玩家或者电脑   icon2代表玩家2头像图片，具体路径根据实际判断
		icon2 = new ImageIcon("image/headImage/3small.png");
		jlPlayer2 = new JLabel(icon2);
		jlPlayer2.setBounds(400, 500, 45, 45);
		
//		玩家1棋盅，颜色根据实际情况判断， icon3代表玩家1棋子，具体路径根据实际判断
		icon3 = new ImageIcon("image/whiteDisk.gif");
		jlPlayer1Disk = new JLabel(icon3);
		jlPlayer1Disk.setBounds(250, 500, 45, 45);
		
//		玩家2棋盅，颜色根据实际情况判断， icon4代表玩家2棋子，具体路径根据实际判断
		icon4 = new ImageIcon("image/blackDisk.gif");
		jlPlayer2Disk = new JLabel(icon4);
		jlPlayer2Disk.setBounds(350, 500, 45, 45);		
		
//		棋盘
		this.addMouseListener(new mousel());	//注册监听
//		setSize(500,500);
//		f.add(this,"Center");
		this.setBounds(90,35,470,470);
	
		
//		this.setBackground(Color.green);
		
		
//		时间
		timelb.setBounds(298, 510, 100, 30);
//		timelb.setBorder(new LineBorder(Color.BLACK,3));
		
		jlNormal = new JLabel(new ImageIcon("image/normal.gif"));
		jlNormal.setBounds(220, 10, 50, 50);
		jlNormal.setOpaque(false);
//		jlNormal.setVisible(false);
		
		jlHappy = new JLabel(new ImageIcon("image/happy.gif"));
		jlHappy.setBounds(220, 10, 50, 50);
		jlHappy.setOpaque(false);
		jlHappy.setVisible(false);
		
		jlAngry = new JLabel(new ImageIcon("image/angry.gif"));
		jlAngry.setBounds(220, 10, 50, 50);
		jlAngry.setOpaque(false);
		jlAngry.setVisible(false);
		
		jlSad = new JLabel(new ImageIcon("image/sad.gif"));
		jlSad.setBounds(220, 10, 50, 50);
		jlSad.setOpaque(false);
		jlSad.setVisible(false);
		
		jpMain.add(startbt);
		jpMain.add(hcbt);
		jpMain.add(undobt);
//		jpMain.add(restartbt);
		jpMain.add(hcbt2);
		jpMain.add(endbt);
		jpMain.add(jsptr1);
		jpMain.add(jspchatbox);
		jpMain.add(jspchatinputbox);
		jpMain.add(jlPlayer1);
		jpMain.add(jlPlayer2);
		jpMain.add(jlPlayer1Disk);
		jpMain.add(jlPlayer2Disk);
		jpMain.add(this);
		jpMain.add(timelb);
		jpMain.add(jlGameStart);
		jpMain.add(jlEasyPC);
		jpMain.add(jlCrazyPC);
		jpMain.add(jlRegret);
		jpMain.add(jlExit);
		jpMain.add(jlNormal);
		jpMain.add(jlHappy);
		jpMain.add(jlSad);
		jpMain.add(jlAngry);
		
		
		f.add(jpMain);
		
		jpMain.addMouseListener(new XYListener());
		f.setVisible(true);
		//********************************************************************************
		
		reset();
	}
	
	class XYListener extends MouseAdapter {
		public void mousePressed (MouseEvent e){
			System.out.println(e.getX()+"           +              "+e.getY());
		}
	}
	
//	JpMain 的class,  重写paint 方法， 构造背景
	class JpMain extends JPanel {
		public JpMain(){
			super();
		}
		public void paintComponent (Graphics g){
			g.drawImage(new ImageIcon("image/游戏背景测试2.jpg").getImage(), 0, 0, 800, 600, this);
		}
	}
	
	
	public void reset(){
		
		
		
		player=1;				//1表示黑棋
		x=new int[15][15];		//记录棋子
		
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){
				x[i][j]=0;		//初始化，0表示无子,1表示黑子，2表示白子
				
				
			}
		}
		
		record=new int[255][3];
		for(int i=0;i<255;i++)
			for(int j=0;j<3;j++){
				record[i][j]=-1;	//-1表示还没下子
			}
		
		//5*5 小棋盘初始化
		five=new int[5][5];
		for(int i=0;i<5;i++)
			for(int j=0;j<5;j++){
				five[i][j]=0;
			}
		
		step=0;		
		tr1.setText("尚未开始");
		time=57600;
		chessboardMessage="null";	//null表示没有消息
		net=false;
		server=true;
		ip="localhost";
		
		
		lock=true;
		
		//状态记录
		isStart=false;		//是否已经开始游戏
		computer=0;						//电脑等级，0表示双人对战，123分别表示难度
		net=false;						//net表示是否是网络对战
		undo=true;				//是否允许悔棋
		wait=false;				//是否正在等待对方下棋
		mode=0;						//是否已经选择了一种模式
		state=8881;
		
		//selectMode(4);
		stateChange();
	}
	
	public void createNetCon(){//创建线程
		netTransThread=new Thread(new NetTransThread(this));
		netChatThread=new Thread(new NetChatThread(this));
		
		
	}
	
	//走棋记录
	public void showstep(){
		tr1.setText("");//先清空
		if(step==0) {
			tr1.setText("请下子！");
			return;
		}
		String p=null;
		//装入走棋步骤
		for(int i=0;i<step;i++){
			if(record[i][2]==1) p="黑方";
			else p="白方";
			tr1.append("\n"+i+":"+p+"("+record[i][0]+","+record[i][1]+")");
		}
	}
	
	//悔棋,注意一次悔棋两步
	public void undo(){
		if(undoNumber<=0) {
			undobt.setEnabled(false);	//不可用
			return;		//悔棋次数
		}
		
		if(mode==1||mode==2){
			sendNetStep(302);		//发送悔棋请求
			new ConfirmDialog("悔棋请求已发送，等待对方同意！",f);
			return;
		}
		
		if(step==0) {
			undobt.setEnabled(false);
			return;
		}
		x[record[step-1][0]][record[step-1][1]]=0;
		step--;
		if(step==0) {
			undobt.setEnabled(false);
			return;
		}
		x[record[step-1][0]][record[step-1][1]]=0;
		step--;
		
		undoNumber--;
		repaint();
	}
	
	//网络上收到对手的同意悔棋请求
	public void netUndo(){
		if(step==0) {
			undobt.setEnabled(false);
			return;
		}
		x[record[step-1][0]][record[step-1][1]]=0;
		step--;
		if(step==0) {
			undobt.setEnabled(false);
			return;
		}
		x[record[step-1][0]][record[step-1][1]]=0;
		undoNumber--;
		step--;
		repaint();
	}
	
	//
	public void netUndoServer(){
		if(step==0) {
			undobt.setEnabled(false);
			return;
		}
		x[record[step-1][0]][record[step-1][1]]=0;
		step--;
		if(step==0) {
			undobt.setEnabled(false);
			return;
		}
		x[record[step-1][0]][record[step-1][1]]=0;
		step--;
		repaint();
	}
	
	//收到对手悔棋请求
	public void netUndoRequest(){
		//监听
		class ConfirmActionListener implements ActionListener{
			private Dialog d=null;
			public ConfirmActionListener(Dialog d){
				this.d=d;
			}

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sendNetStep(303);				//303表示同意
				netUndoServer();
				d.dispose();
			}
			
		}
		class CancelActionListener implements ActionListener{
			private Dialog d=null;
			public CancelActionListener(Dialog d){
				this.d=d;
			}

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sendNetStep(304);				//303表示同意
				d.dispose();
			}
			
		}
		
		Dialog dialog=new Dialog(f);
		dialog.setTitle("五子棋提醒");
		dialog.setModal(true);
		dialog.setLayout(new GridLayout(3,1));
		dialog.setLocation(400, 250);
		
		
		JLabel lb1=new JLabel("对方请求悔棋，是否同意？");
		dialog.add(lb1);
		
		JButton bt1=new JButton("同意");
		bt1.addActionListener(new ConfirmActionListener(dialog));
		JButton bt2=new JButton("不同意");
		bt2.addActionListener(new CancelActionListener(dialog));
		dialog.add(bt1);
		dialog.add(bt2);
		dialog.pack();
		dialog.setResizable(false);
		dialog.setVisible(true);
		
		
		
		
		
		
	}
	
	
	
	//清除棋子
	public void clear(){
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){
				x[i][j]=0;		//初始化，0表示无子,1表示黑子，2表示白子
			}
		}
		step=0;
		player=1;
	}
	
	//锁定棋盘
	public void lockChessboard(){
		lock=true;
	}
	//解锁棋盘
	public void unlockChessboard(){
		lock=false;
	}
	
	public void paint(Graphics g){
//		this.removeAll();
//		this.clear();
//		g.clearRect(0, 0, 500, 500);
		this.setOpaque(false);
		makeGUI(g);
		draw_circle(g);
		showstep();
		printMessage(g);
		stateChange();
	}
	
	
	//控制棋盘界面的各个控件的状态，是否能够点击
	public void stateChange(){
		mode = 4;	//单机测试代码
		endbt.setEnabled(true);		//任何时候都是可以退出游戏的
		jlRegret.setText("悔棋（"+undoNumber+"）");
		if(undoNumber<=0){
			undobt.setEnabled(false);
		}
		else{
			undobt.setEnabled(true);
		}
		if(mode==0){					//尚未选择模式
			startbt.setEnabled(true);
			restartbt.setEnabled(false);
			undobt.setEnabled(false);
			//endbt.setEnabled(false);
			
			
			return;
		}
		//建立主机
		if(mode==1){
			hcbt.setEnabled(false);		//双人对战不能加电脑
			hcbt2.setEnabled(false);
			if(compControl==11){
				startbt.setEnabled(false);
				undobt.setEnabled(false);
				//exitbt.setEnabled(true);
				
			}
			if(compControl==12){
				startbt.setEnabled(true);
				undobt.setEnabled(false);
				//exitbt.setEnabled(true);
				
			}
			if(compControl==13){
				startbt.setEnabled(false);
				undobt.setEnabled(true);
				//exitbt.setEnabled(true);
				
			}
		}
		
		if(mode==2){
			hcbt.setEnabled(false);		//双人对战不能加电脑
			hcbt2.setEnabled(false);
			startbt.setEnabled(false);	//客户端只能等待主机开始
			//exitbt.setEnabled(true);
			if(compControl==21){
				undobt.setEnabled(false);
				
			}
			if(compControl==22){
				undobt.setEnabled(true);
				
			}
			if(compControl==23){
				undobt.setEnabled(false);
				
			}
		}
		//人机对战
		if(mode==4){
			if(compControl==41){
				startbt.setEnabled(false);
				hcbt.setEnabled(true);		
				hcbt2.setEnabled(true);
				undobt.setEnabled(false);
			}
			if(compControl==42){
				startbt.setEnabled(true);
				hcbt.setEnabled(false);		
				hcbt2.setEnabled(false);
				undobt.setEnabled(false);
			}
			if(compControl==43){
				startbt.setEnabled(false);
				hcbt.setEnabled(false);		
				hcbt2.setEnabled(false);
				undobt.setEnabled(true);
			}
		}
		
		
		//尚未开始游戏
		/*if(isStart==false) {
			startbt.setEnabled(true);
			restartbt.setEnabled(false);
			undobt.setEnabled(false);
			endbt.setEnabled(true);
			if(mode==2) startbt.setEnabled(false);		//加入对方游戏，只能等对方开始，自己不能开始
			return;
		}
		else {
			startbt.setEnabled(false);
			endbt.setEnabled(true);
		}
		
		if(undo) undobt.setEnabled(true);
		else undobt.setEnabled(false);
		
		*/
	}
	
	//开始游戏,未开始之前，棋盘是锁定的
	public void Start(){
		unlockChessboard();
	}
	
	//画棋子
	public void draw_circle(Graphics g){
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){	
				
				if(x[i][j]==1){	//黑子
					
					//g.setColor(Color.black);
					//g.fillOval((j*30+30)-13, (i*30+30)-13, 26, 26);//根据坐标计算出像素点，画出棋子
					g.drawImage(new ImageIcon("image/black.gif").getImage(), j*30+15, i*30+15, 28, 28, this);
				}
				else if(x[i][j]==2){	//白子
					//g.setColor(Color.white);
					//g.fillOval((j*30+30)-13, (i*30+30)-13, 26, 26);//根据坐标计算出像素点
					g.drawImage(new ImageIcon("image/white.gif").getImage(), j*30+15, i*30+15, 28, 28, this);
				}
				//最后一个子  特殊标记
				if(x[i][j]!=0&&last_x==i&&last_y==j){
					g.setColor(Color.red);
					g.drawRect((j*30+30)-15, (i*30+30)-15, 30, 30);
					
				}
			}
		}
	}
	
	//画棋盘
	public void makeGUI(Graphics g){
//		for(int i=30;i<=450;i+=30){
//			g.drawLine(i, 30, i, 450);
//			g.drawLine(30,i,450,i);
//		}
//		for(int i=0;i<15;i++){
//			g.drawString(i+"",25+i*30, 20);
//			g.drawString(i+"",10,35+i*30);
//		}
//		
//		g.drawImage(new ImageIcon("image/棋盘底稿 15X15 2.jpg .jpg").getImage(), 0, 0, 470, 470, this);
	}
	
	//判断是否胜利,简单的判断 还不完善
	public int win(){		//0表示没有人赢，1表示黑子赢，2表示白子赢
		int m=0;
		int n=0;
		
		//构造一个5*5的小棋盘,在小棋盘内部判断是否win
		for(int i=0;i<11;i++){
			for(int j=0;j<11;j++){
				
				 for(m=0;m<5;m++)
			         for(n=0;n<5;n++)
			                 five[m][n]=x[m+i][n+j];
				 
				 for(m=0;m<5;m++){
                     if(five[m][0]==1&&five[m][1]==1&&five[m][2]==1&&five[m][3]==1&&five[m][4]==1)
                         return (1);
                     if(five[0][m]==1&&five[1][m]==1&&five[2][m]==1&&five[3][m]==1&&five[4][m]==1)
                         return (1);}
		             if(five[0][0]==1&&five[1][1]==1&&five[2][2]==1&&five[3][3]==1&&five[4][4]==1)
		                  return (1);
		             if(five[0][4]==1&&five[1][3]==1&&five[2][2]==1&&five[3][1]==1&&five[4][0]==1)
		                  return (1);
				
				
		
				for(m=0;m<5;m++){
		            if(five[m][0]==2&&five[m][1]==2&&five[m][2]==2&&five[m][3]==2&&five[m][4]==2)
		                return 2;
		            if(five[0][m]==2&&five[1][m]==2&&five[2][m]==2&&five[3][m]==2&&five[4][m]==2)
		                return 2;}
				    if(five[0][0]==2&&five[1][1]==2&&five[2][2]==2&&five[3][3]==2&&five[4][4]==2)
				         return 2;
				    if(five[0][4]==2&&five[1][3]==2&&five[2][2]==2&&five[3][1]==2&&five[4][0]==2)
				         return 2;
				
	}
}
		
		return 0;
	}
	
	//胜利对话框
	public void ShowWinDialog(){
		
		Dialog dialog=new Dialog(f);
		dialog.setTitle("胜利！");
		dialog.setModal(true);
		dialog.setLayout(new FlowLayout());
		dialog.setLocation(400, 250);
	
	
		if(win()==1){
			JLabel lb1=new JLabel("恭喜黑方获胜！");
			dialog.add(lb1);
		}
		else {
			JLabel lb1=new JLabel("恭喜白方获胜！");
			dialog.add(lb1);
		}
		JButton bt1=new JButton("确认");
		bt1.addActionListener(new bt1Listener(dialog));
		dialog.add(bt1);
		dialog.pack();
		dialog.setVisible(true);
		
		}
	
	//input dialog
	public void inputDialog(String title,String message){
		Dialog dialog=new Dialog(f);
		dialog.setTitle(title);
		dialog.setModal(true);
		dialog.setLayout(new GridLayout(4,1));
		dialog.setLocation(400, 250);
		
		
		inputTextArea=new TextArea("localhost");
		
		JLabel lb1=new JLabel(message);

		JButton bt1=new JButton("确认");
		JButton bt2=new JButton("取消");
		JPanel pp=new JPanel();
		
		
		bt1.addActionListener(new inputDialogBt1Listener(dialog));
		bt2.addActionListener(new inputDialogBt2Listener(dialog));
		pp.setLayout(new GridLayout(1,4));
		
		dialog.add(lb1);
		dialog.add(inputTextArea);
		
		dialog.add(pp);
		pp.add(new JPanel());
		pp.add(bt1);
		pp.add(new JPanel());
		pp.add(bt2);
		
		
		dialog.setSize(300,200);
		
		dialog.setVisible(true);
		}
	
	//设置电脑等级
	public void setComputer(int i){
		computer=i;
	}
	
	public void setNet(boolean i){
		net=i;
	}
	
	//改变表情
	public void changeExpression (int x){
		switch(i){
			case (1): jlNormal.setVisible(false);		//happy
					  jlSad.setVisible(false);
					  jlAngry.setVisible(false);
					  jlHappy.setVisible(true);
					  repaint();
					  break;
					  
			case (2):jlNormal.setVisible(false);		//sad
			  		 jlSad.setVisible(true);
			  		 jlAngry.setVisible(false);
			  		jlHappy.setVisible(false); 
			  		repaint();
			  		 break;
			case (3):jlNormal.setVisible(false);		//angry
			  		 jlSad.setVisible(false);
			  		 jlAngry.setVisible(true);
			  		 jlHappy.setVisible(false); 
			  		repaint();
			  		 break;
			case (4):jlNormal.setVisible(true);		//normal
			  		 jlSad.setVisible(false);
			  		 jlAngry.setVisible(false);
			  		 jlHappy.setVisible(false);
			  		repaint();
			  		 break; 		 
		}
	}
	
	//转换玩家
	public void switchplayer(){
		if(player==1) player=2;
		else player=1;	//转换玩家
	}
	
	
	public void compute(){
		
		Compute cm=new Compute(x,player,computer,this);
		//repaint();
	
	}
	
	public void computeComplete(Point p){
		
		addChessman(p.x,p.y);
		switchplayer();
		lock=false;
	}
	
	
	public void addChessman(int i,int j){
		System.out.println("("+i+","+j+")");
		last_x=i;last_y=j;
		x[i][j]=player;
		record(i,j,player);
		repaint();
		
		//每下一个子 就判断胜负判断胜负
		if(win()!=0) {
			ShowWinDialog();
		}
	}
	
	//记录
	public void record(int i,int j,int player){
		record[step][0]=i;
		record[step][1]=j;
		record[step][2]=player;
		step++;
	}
	
	//开始网络连接
	public void netStart(){
		

		netTransThread.start();
		netChatThread.start();
		
		
	}
	
	
	
	
	//发送自己下子坐标
	public boolean sendNetStep(int p){
		return netTrans.send(p);
	}
	
	
	public void printMessage(Graphics g){
		
		if(chessboardMessage!="null") {
			g.setColor(Color.orange);
			g.setFont(new Font("微软雅黑",1,16));
			g.drawString(chessboardMessage, 200, 14);
		}
		return;
	}
	
	//在棋盘上打印消息
	public void printMessage(String m){
		chessboardMessage=m;
		repaint();
	}
	
	//清除棋盘上的消息
	public void clearMessage(){
		chessboardMessage ="null";
		repaint();
	}
	
	//point 和int 互相转换
	public Point inttoPoint(int p){
		point.setpoint((int)(p/15),p%15);
		return point;
	}
	public int pointtoInt(Point p){
		return p.x*15+p.y;
	}
	
	//计时函数,开始计时
	public void timeStart(){
		time=57600;
		timeR.turnOn();
		if(timeThread.isAlive()) {}
		else{
			timeThread.start();
			}
	}
	//重新开始计时
	public  void timeRestart(){
		time=57600;
	}
	
	//结束计时
	public void timeEnd(){
		timeR.shutdown();
	}
	
	//聊天，显示接收到的对方消息
	public void addChatbox(String m){
		chatbox.append(m);
	}
	//聊天 发送消息
	public void sendMessage(String m){
		netChat.sendChat(m);
	}
	
	//清空聊天输入窗口
	public void clearChatInputbox(){
		chatinputbox.setText("");
	}
	
	//网络对战时 终止游戏
	public void netEnd(){
		endGame();
		if(server) compControl=14;
		else compControl=23;
		stateChange();
		printMessage("对方已经结束游戏！");
	}
	
	//网络对战时 开始游戏
	public void netBegin(){
		beginGame();
		printMessage("对方已经开始游戏！");
		if(server){
			compControl=13;
		}else {
			compControl=22;
		}
			stateChange();
		lockChessboard();
	}
	
	//游戏结束
	public void endGame(){
		lockChessboard();
		timeEnd();
		clear();
		
		isStart=false;
		stateChange();
		printMessage("游戏已经结束！");
	}
	//游戏开始
	public void beginGame(){
		unlockChessboard();
		timeStart();
		clear();
		if(mode==4){
			compControl=43;
		}
		if(mode==1){
			compControl=13;
		}
		
		isStart=true;
		stateChange();
		undoNumber=100;			//每次可悔棋3次
		printMessage("游戏已经开始！");
	}
	
	//游戏重新开始
	public void restartGame(){
		isStart=true;
		timeRestart();
		
		clear();
		unlockChessboard();
		
		stateChange();
	}
	
	//选择模式
	public void selectMode(int m){
		if(m==1){
			mode=1;
			state=8882;
			server=true;
			undo=false;
			compControl=11;
			isStart=false;
			
			setNet(true);
			createNetCon();
			netStart();
			printMessage("建立主机，等待对方连接……");
			
			lockChessboard();	
			stateChange();
		}
		else if(m==2){
			mode=2;
			server=false;
			isStart=false;
			undo=false;
			compControl=21;
			setNet(true);
			
			printMessage("正在连接主机……");
			lockChessboard();	
			//inputDialog("加入游戏", "请输入主机ip地址：");
			createNetCon();
			netStart();	
			stateChange();
		}
		else if(m==3){
			mode=3;
			isStart=false;
			setComputer(0);
			setNet(false);
			lockChessboard();
			stateChange();
		}
		else if(m==4){
			mode=4;
			compControl=41;
			
			GameCenter.changeState(8884);
			isStart=false;
			setComputer(1);
			setNet(false);
			lockChessboard();
			stateChange();
		}
	}
	
	public void setIp(String ip){
		this.ip=ip;
	}
	
	public void changeMyId(String myId){
		this.myId=myId;
		myIdlb.setText("我是："+myId);
	}
	
	public void changeRivalId(String Id){
		this.rivalId=Id;
		rivalIdlb.setText("对手："+Id);
	}
	
	//main
	public static void main(String[] args) {
		new Chess();
		
	}
	

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////电脑AI///////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////

	
	
	
	
	
	
	
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////以下是监听接口类///////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
	
//棋盘监听
class mousel implements MouseListener{
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		if(lock) return;//棋盘被锁定 忽略点击
		
		
		int xx=e.getY();
		int yy=e.getX();
		
		//修正鼠标点击位置，总是找到其最近交叉点
		aa:for(int i=30;i<=450;i+=30){
			for(int j=30;j<=450;j+=30){
				if(Math.abs(xx-i)<=15&&Math.abs(yy-j)<=15){
					xx=i;
					yy=j;
					break aa;
				}
					
			}
		}
		int stepX=(xx-30)/30;int stepY=(yy-30)/30;
		System.out.println("点击："+stepX+","+stepY);
			if(x[stepX][stepY]!=0) return; //此位置已经有子，忽略此次点击
			
			if(net==true){
				sendNetStep(stepX*15+stepY);		//在下子之前发送坐标，不然下子同时会判断胜负，会阻塞发送坐标
			}
				addChessman((xx-30)/30,(yy-30)/30);	//根据像素点计算出坐标
			
			repaint();
			
			switchplayer();			//双人对战
			
			//单机游戏
			if(net==false){
				if(computer!=0)	{						//人机对战
					compute();
					//然后返回，等待电脑计算结束	注意此处没有switchplayer
					return;
				}
				}
			//网络对战
			else{
				lockChessboard();
				Point p=null;
				
				printMessage("等待对方下子……");
				
			}
			
			undobt.setEnabled(true);
			
		repaint();
		
		
		
		
	}

	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
	//对话框监听
	class bt1Listener implements ActionListener{
		private Dialog g=null;
		bt1Listener(Dialog g){
			this.g=g;
			
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			g.setVisible(false); //关闭对话框
			endGame();
			repaint();	//别忘了重画
		}
		
	}
	
	
	//开始按钮 监听
	class StartbtListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			beginGame();
			stateChange();
			
			timeStart();	//开始计时
			if(mode==1) sendNetStep(NET_BEGIN);
			if(net==false&&computer!=0){
				//compute();			//电脑先走
				//switchplayer();
			}
			
			repaint();
		}
		
	}
	
	//重新开始按钮 监听
	class RestartbtListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			
			
			time=57600;//重新计时
			repaint();
		}
		
	}
	
	
	
	//悔棋按钮监听
	class UndobtListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			undo();
			repaint();
			stateChange();
		}
		
	}
	
	//结束此局  按钮监听
	class EndbtListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			endGame();
			try{
				sendNetStep(NET_END);
			}catch(Exception ecp){}
			timeEnd();		//停止计时；
			timelb.setText("00:00:00");
			repaint();
			stateChange();
			
			GameCenter.changeState(8881);
			//释放socket
			try{
				netChat.s.close();
				netTrans.s.close();
			}catch(Exception ecp){}
			
			
			
			f.dispose();
		}
		
	}
	
	//计时用的线程
	class TimeRunner implements Runnable{
		private boolean flag=true;
		public void shutdown(){
			flag=false;
		}
		public void turnOn(){
			flag=true;
		}
		
		public void run() {
			while(true){
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){}
				time++;
				if(flag==true){
					timelb.setText(""+(new Time(time*1000).toString()));
				}
				else{
					timelb.setText("00:00:00");
				}
				
			}
		}
		
	}
	
	
	//人机对战 简单电脑按钮监听
	class hcbtListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			compControl=42;
			computer=1;
			stateChange();
		}
		
	}
	
	//人机对战，疯狂电脑按钮
	class hcbt2Listener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			compControl=42;
			computer=2;
			stateChange();
		}
		
	}
	
	//双人对战 按钮监听
	class hhbtListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			selectMode(3);
		}
		
	}
	
	//建立主机 按钮监听
	class netbtListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			selectMode(1);
				
			
		}
		
	}
	
	//加入游戏 按钮监听
	class netbt2Listener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			
			
			
			selectMode(2);
			
		}
		
	}
	
	//输入对话框的 监听按钮
	class inputDialogBt1Listener implements ActionListener{
		private Dialog g=null;
		inputDialogBt1Listener(Dialog g){
			this.g=g;
			
		}
		public void actionPerformed(ActionEvent e) {
			ip=inputTextArea.getText();
			
			g.setVisible(false);
		}
		
	}
	
	class inputDialogBt2Listener implements ActionListener{
		private Dialog g=null;
		inputDialogBt2Listener(Dialog g){
			this.g=g;
			
		}
		public void actionPerformed(ActionEvent e) {
			
				g.setVisible(false);
			
		}
		
	}
	
	//加入游戏对话框 按钮监听
//	class chatbtListener implements ActionListener{
//
//		public void actionPerformed(ActionEvent e) {
//				sendMessage(chatinputbox.getText());
//			
//		}
//		
//	}

	
	// 输入框 监听
	class InputListener implements KeyListener {

		
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyChar()==KeyEvent.VK_ENTER){	
				sendMessage(chatinputbox.getText());
			}
		}

		
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		
	}

}


//***************************************************************************
//****************************以下是网络通信用的类和线程*********************************
class NetTransThread implements Runnable{
	private Chess c=null;
	public NetTransThread(Chess c){
		this.c=c;
	}
	public void run() {
		// TODO Auto-generated method stub
		c.netTrans=new NetTrans(c.ip,c.port,c);
		c.netTrans.startNet(c.server);
		c.netTrans.receiveStart();
	}
	
}

class NetChatThread implements Runnable{
	private Chess c=null;
	public NetChatThread(Chess c){
		this.c=c;
	}
	public void run() {
		// TODO Auto-generated method stub
		c.netChat=new NetChat(c.ip,c.port2,c);
		c.netChat.startNet(c.server);
		c.netChat.receiveStart();
	}
	
}





//网络通信 类
class NetTrans {
	
	//一下是 网络通信用的变量
	DataOutputStream dos=null;
	BufferedReader br=null;
	DataInputStream dis=null;
	String string=null;
	String ip="localhost";
	int port=12345;
	Chess c=null;
	Socket s=null;
	
	public NetTrans(String ip,int port,Chess c){
		this.ip=ip;
		this.port=port;
		this.c=c;
	}
	
	public void set(String ip,int port){
		this.ip=ip;
		this.port=port;
	}
	
	public boolean startNet(boolean server) {
		InputStream in=null;
		OutputStream out=null;
		string=null;
		
		
		try{
			if(server==false){
				System.out.println("正在连接主机……"+ip+port);
				
				s=new Socket(ip,port);
				out=s.getOutputStream();
				in=s.getInputStream();
				dis=new DataInputStream(in);
				dos=new DataOutputStream(out);
				
				System.out.println("连接成功！");
				//System.out.println("接收对手姓名");
				//c.changeRivalId(dis.readUTF());	//接收对手姓名
				//System.out.println("发送自己姓名");
				//dos.writeUTF(c.myId);			//发送自己姓名
				c.printMessage("连接成功，等待对方开始游戏……");
				c.lock=true;
				c.state=8883;
			}else {
				ServerSocket ss=new ServerSocket(port);
				System.out.println("建立主机："+ip+":"+port+"等待客户端连接……");
				
				s=ss.accept();
				out=s.getOutputStream();
				in=s.getInputStream();
				dis=new DataInputStream(in);
				dos=new DataOutputStream(out);
				
				//System.out.println("发送自己姓名");
				//dos.writeUTF(c.myId);			//发送自己姓名
				//System.out.println("接收对手姓名");
				//c.changeRivalId(dis.readUTF());	//接收对手姓名
				
				c.printMessage("客户端连接成功，请开始游戏！");
				c.sendMessage(c.myId+"加入了游戏");
				c.compControl=12;
				c.stateChange();
				GameCenter.changeState(8883);
				System.out.println("客户端连接成功！");
				c.state=8883;
			}
			
			
			
		}catch(Exception e){
			c.clearMessage();
			c.printMessage("联网错误！请检查网络设置！");
			System.out.println("联网错误！"+e);
			return false;
		}
		
		
		
		return true;
	}
	
	public void receiveStart(){
		Listen l=new Listen(c,dis);
		Thread t=new Thread(l);
		
		
		t.start();
		
	}
	
	public boolean send(int p){
		try{
			dos.writeInt(p);
		}catch(Exception e){
			System.out.println("发送数据发生错误！"+e);
			return false;
		}
		System.out.println("发送坐标："+p);
		return true;
	}
	

	
}

//监听线程，接收棋子坐标
class Listen implements Runnable{
	
	private Chess c=null;
	private DataInputStream dis=null;
	private int p=0;
	
	Listen(Chess c,DataInputStream dis){
		this.c=c;
		this.dis=dis;
	}
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("监听线程启动，等待接收坐标……");
		try{
			while(true){
				
					p=dis.readInt();
					System.out.println("收到坐标："+p);
				
					//是否表示特殊意义
				if(p==300){
					c.netBegin();
					continue;
				}
				else if(p==301){
					c.netEnd();
					continue;
				}
				else if(p==302){		//悔棋代号
					c.netUndoRequest();
					continue;
				}
				else if(p==303){
					new ConfirmDialog("对方同意了您的悔棋请求！",c.f);
					c.netUndo();
					continue;
				}
				else if(p==304){
					new ConfirmDialog("对方不同意您的悔棋请求！",c.f);
					continue;
				}
				
					c.addChessman((int)(p/15), p%15);
					c.switchplayer();
					c.repaint();
					c.unlockChessboard();
					c.clearMessage();
			}
		}catch(Exception e){
			c.printMessage("网络连接已断开！对方已经退出游戏！");
			c.sendMessage("网络连接已经断开！对方已经退出了游戏！");
			c.lockChessboard();
		}
	}
}




//发送和接收聊天信息
class NetChat {
	
	//一下是 网络通信用的变量
	DataOutputStream dos=null;
	BufferedReader br=null;
	DataInputStream dis=null;
	String string=null;
	String ip="localhost";
	int port=12345;
	Chess c=null;
	Socket s=null;
	
	public NetChat(String ip,int port,Chess c){
		this.ip=ip;
		this.port=port;
		this.c=c;
	}
	
	public void set(String ip,int port){
		this.ip=ip;
		this.port=port;
	}
	
	public boolean startNet(boolean server) {
		InputStream in=null;
		OutputStream out=null;
		string=null;
		
		
		try{
			if(server==false){
				System.out.println("聊天线程正在连接主机……");
				s=new Socket(ip,port);
				System.out.println("连接成功！");
				
			}else {
				ServerSocket ss=new ServerSocket(port);
				System.out.println("聊天线程建立主机："+ip+":"+port+"等待客户端连接……");
				s=ss.accept();
				
				System.out.println("客户端连接成功！");
			}
			
			
			out=s.getOutputStream();
			in=s.getInputStream();
		}catch(Exception e){
			c.clearMessage();
			c.printMessage("联网错误！请检查网络设置！");
			System.out.println("联网错误！"+e);
			return false;
		}
		
		dis=new DataInputStream(in);
		dos=new DataOutputStream(out);
		
		return true;
	}
	
	public void receiveStart(){
		
		ChatListen cl=new ChatListen(c,dis);
		Thread ct=new Thread(cl);
		
		
		ct.start();
	}
	
	public boolean sendChat(String m){
		try{
			dos.writeUTF(m);
		}catch(Exception e){
			System.out.println("发送消息发生错误！"+e);
			c.addChatbox("网络连接错误！消息发送失败！\n");
			return false;
		}
		System.out.println("发送聊天信息："+m);
		c.addChatbox("我说："+"\n"+"   "+m+"\n");
		c.clearChatInputbox();
		return true;
	}
	
}

//监听线程 接收聊天消息
class ChatListen implements Runnable{
	
	private Chess c=null;
	private DataInputStream dis=null;
	private String m=null;
	
	ChatListen(Chess c,DataInputStream dis){
		this.c=c;
		this.dis=dis;
	}
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("聊天监听线程启动，等待接收消息……");
		try{
			while(true){
				
				m=dis.readUTF();
				System.out.println("收到消息："+m);
				c.addChatbox("他说："+"\n"+"   "+m+"\n");
			}
		}catch(Exception e){
			System.out.println("联网错误！"+e);
		}
	}
}


