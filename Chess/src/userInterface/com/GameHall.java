package userInterface.com;

import javax.swing.*;

import axun.com.*;

import java.awt.*;

import javax.swing.border.LineBorder;
import javax.swing.event.*;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class GameHall extends JFrame {
	
//	**************************************		需要的数据区		**********************
//	jpMain
	JPanel jpMain =new JpMain();
	
//	玩家列表 
	JLabel [] jlUserList= new JLabel [20];
	public ImageIcon [] userHeadImage = new ImageIcon [30];  //头像
	public String [] userNameString = new String[20];		//名字
	public int [] userState = new int [20]; //玩家状态  8881 在线空闲， 8882 建立主机，等待， 8883 游戏;
	int number=0;
//	游戏大厅， 同玩家列表
	public String [] userIP =new String [30];
	JScrollPane jspRoom;
	JPanel jpRoom ;
	JPanel [] jpGame = new JPanel[30];
	JButton [] jlHost = new JButton[30];
	JLabel [] jlTable = new JLabel[30];
	JButton [] jlChallenge = new JButton[30];
	public JLabel [] jlUserName=new JLabel[30];
	
	public JButton enterHost[]=new JButton[20];
	
	
//	按钮
	public JButton  jbtNewGame,jbtChangeInfo, jbtHelp, jbtExit ,jbtVSPC;
	public JCheckBox jcbMusic;
	JLabel jlNewGame,jlVSPC, jlChangeInfo,jlHelp,jlExit,jlMusic;
	
//	个人信息
	JPanel jpMyInfo;
	JLabel jlMyHead, jlMyinfo;
	JLabel jlID;
	JLabel jlMyName;
	public ImageIcon myHead = new ImageIcon("image/headImage/1.png");
	public String myID="123" , myName="kian";
	public int myWin=9, myLose=9, myGrade=3;
	
//	输入框
	JScrollPane jspInput;
	public JTextArea jtaInput;
	public String inputMessage;
	
//	对话框
	JScrollPane jspDialog;
	public JTextArea jtaDialog ;

//	*************************************	需要的数据区		**************************
	
//	*************************************	其他成员			**************************
//	JSP userList
	JScrollPane jspUserList ;
	List jpUserList = new List();
	
	

//	取 屏幕宽度 高度
	private int scWidth=Toolkit.getDefaultToolkit().getScreenSize().width;
	private int scHeight=Toolkit.getDefaultToolkit().getScreenSize().height;
	private Color brown = new Color(142,78,30);


//	标题栏LOGO
	Image titleImage=new ImageIcon("image/logo.png").getImage();
	

	
//	************************************	其他成员			******************************
	
//	构造方法
	public GameHall(){
//		Frame 设置大小布局，title 
		this.setBounds((scWidth-900)/2,(scHeight-600)/3, 900, 600);
		this.setIconImage(titleImage);
		this.setResizable(false);
		this.setTitle("欢乐五子棋 -- 游戏大厅");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//this.setResizable(false);
		
//		jpMain 设置大小，布局
		jpMain.setBounds(0, 0, 900, 600);
		jpMain.setLayout(null);

		
//		用户列表  userList
		//jpUserList.setLayout(new GridLayout(30,0));
		//jpUserList.setOpaque(false);
		/*for(int i=0;i<18;i++){
			jlUserList[i] = new JLabel("玩家"+i,new ImageIcon("image/headImage/1small.png"),JLabel.CENTER);
//			jlUserList[i]=new JLabel(userHeadImage[1]);
			jpUserList.add(jlUserList[i].toString());
		}*/
		jspUserList = new JScrollPane(jpUserList);
		jspUserList.setBounds(20, 80, 140, 350);
		jspUserList.setOpaque(false);
		jspUserList.setBorder(null);
		jspUserList.getViewport().setOpaque(false);

		
		
		
		
//		个人信息
		jpMyInfo = new JPanel();
		jpMyInfo.setBounds(590, 13, 290, 130);
		jpMyInfo.setLayout(null);
		jpMyInfo.setOpaque(false);
		
//		头像
		jlMyHead = new JLabel(myHead);
		jlMyHead.setBounds(0, 0, 128, 128);
//		信息，包含ID,昵称，胜负，等级
		JLabel jlMyInfo = new JLabel();
		jlMyInfo.setLayout(new GridLayout(5,1,2,3));
		jlMyInfo.setBounds(130, 10, 155, 110);
//		ID
		jlID = new JLabel("ID:"+myID);
		jlID.setFont(new Font("微软雅黑",1,20));
		jlID.setForeground(Color.BLACK);	
//		昵称     设置字体，颜色
		jlMyName = new JLabel("昵称："+myName);
		jlMyName.setFont(new Font("微软雅黑",1,20));
		jlMyName.setForeground(Color.BLACK);	
//		胜局     设置字体，颜色
		JLabel jlMyWin  = new JLabel("胜局："+myWin);
		jlMyWin.setFont(new Font("微软雅黑",1,20));
		jlMyWin.setForeground(Color.BLACK);		
//		负句  设置字体，颜色
		JLabel jlMyLose = new JLabel("负局："+myLose);
		jlMyLose.setFont(new Font("微软雅黑",1,20));
		jlMyLose.setForeground(Color.BLACK);
//		等级 设置字体， 颜色
		JLabel jlMyGrade = new JLabel("等级："+myGrade);
		jlMyGrade.setFont(new Font("微软雅黑",1,20));
		jlMyGrade.setForeground(Color.BLACK);
		
		
//		jlMyInfo加入各部件，ID，昵称，胜负，等级
		jlMyInfo.add(jlID);
		jlMyInfo.add(jlMyName);
		jlMyInfo.add(jlMyWin);
		jlMyInfo.add(jlMyLose);
		jlMyInfo.add(jlMyGrade);
		
//		jpMyInfo加入各部件，头像，信息
		jpMyInfo.add(jlMyHead);
		jpMyInfo.add(jlMyInfo);
		
//		输入框
		jtaInput = new JTextArea();
		jtaInput.setOpaque(false);
		jtaInput.setLineWrap(true);							//自动换行
		//jtaInput.addKeyListener(new InputListener());		//加入监听，回车发送信息
		
		jspInput = new JScrollPane(jtaInput);
		jspInput.setBounds(650, 500, 200, 50);
		jspInput.setOpaque(false);
		jspInput.getViewport().setOpaque(false);
		jspInput.setBorder(null);
		
//		对话框 dialog
		jtaDialog = new JTextArea();
		jtaDialog.setLineWrap(true);
		jtaDialog.setOpaque(false);
		jtaDialog.setEditable(false);
		jspDialog =new JScrollPane(jtaDialog);
		jspDialog.setBounds(600,175,270,280);
		jspDialog.setOpaque(false);
		jspDialog.getViewport().setOpaque(false);
		jspDialog.setBorder(null);
		
//		游戏厅  jspRoom  包含 jpRoom,  jpRoom 包含若干个jpGame 
		
		jpRoom = new JPanel();
//		jpRoom.setBounds(180, 80, 400, 1400);
		jpRoom.setPreferredSize(new Dimension(380, 1000));
//		jpRoom.setBackground(Color.WHITE);
		jpRoom.setOpaque(false);
		jpRoom.setLayout(null);
		jspRoom = new JScrollPane(jpRoom);				
		jspRoom.setBounds(165, 80, 410, 400);
		jspRoom.setOpaque(false);
		jspRoom.getViewport().setOpaque(false);
		jspRoom.setBorder(new LineBorder(Color.WHITE,3));

		
		
		
//		设置各个game, 双方头像，棋桌， 这里是假设
		for(int i=0;i<20;i++){
			jpGame[i]= new JPanel();
			jpGame[i].setBounds(10+(i%2)*190, 20+(i/2)*80, 200, 80);			//设置位置
			jpGame[i].setLayout(null);
			jpGame[i].setOpaque(false);
			
			
			jlHost[i]= new JButton(new ImageIcon("image/headImage/1small.png"));			//建主玩家的头像，button
			jlHost[i].setBounds(0, 0, 50, 50);
			jlHost[i].setBorder(null);
			jlHost[i].setOpaque(false);
			jlHost[i].setContentAreaFilled(false);
			jlHost[i].addActionListener(new HostLinstener());
			
			jlTable [i] = new JLabel(new ImageIcon("image/tablesmall.png"));				//棋桌图片， label
			jlTable[i].setBounds(60, 0, 50, 50);
			jlTable[i].setBorder(null);
			jlTable[i].setOpaque(false);
			
			
			enterHost[i]=new JButton(new ImageIcon("image/chair.png"));
			enterHost[i].setBounds(120, 0, 50, 50);
			enterHost[i].setOpaque(false);
			enterHost[i].setContentAreaFilled(false);
//			enterHost[i].setBorder(null);
//			enterHost[i].setEnabled(false);
			
//			jlChallenge[i] = new JButton(new ImageIcon("image/headImage/2small.png"));		//对手头像，人或电脑或空闲
//			jlChallenge[i].setBounds(120, 0, 50, 50);
//			jlChallenge[i].setBorder(null);
//			jlChallenge[i].setOpaque(false);
//			jlChallenge[i].setContentAreaFilled(false);
			//jlChallenge[i].addActionListener(new ChallengeListener());
			
			jlUserName[i] = new JLabel("没有主机");
			jlUserName[i].setBounds(10, 50, 100, 20);
			
			
			jpGame[i].add(jlHost[i]);
			jpGame[i].add(jlTable[i]);
			jpGame[i].add(enterHost[i]);
			jpGame[i].add(jlUserName[i]);
			
			jpRoom.add(jpGame[i]);
		
			
			
//				jpGame[i]= new JPanel();
//				
//				enterHost[i]=new JButton("没有主机！");
//				enterHost[i].setEnabled(false);
//				jpGame[i].add(enterHost[i]);
//				
//				jpRoom.add(jpGame[i]);
			
		}
		
//		几个按钮
//		新建游戏 jbtNewGame
		jbtNewGame = new JButton (new ImageIcon("image/newgame.gif"));
		jbtNewGame.setBounds(175,  490, 50,50);
		jbtNewGame.setOpaque(false);
		jbtNewGame.setBorder(null);
		jbtNewGame.setContentAreaFilled(false);
		//jbtNewGame.addActionListener(new NewGameListener());
		jbtNewGame.setToolTipText("点击我，新建游戏");
		
//		新建游戏标签
		jlNewGame = new JLabel("新建游戏",JLabel.CENTER);
		jlNewGame.setFont(new Font("微软雅黑",1,14));
		jlNewGame.setBounds(180, 540, 60, 30);
		
		
		
//		打电脑
		jbtVSPC = new JButton(new ImageIcon("image/vspc.gif"));
		jbtVSPC.setBounds(245, 490, 50, 50);
		jbtVSPC.setOpaque(false);
		jbtVSPC.setBorder(null);
		jbtVSPC.setContentAreaFilled(false);
		
//		打电脑标签
		jlVSPC = new JLabel("打电脑",JLabel.CENTER);
		jlVSPC.setFont(new Font("微软雅黑",1,14));
		jlVSPC.setBounds(245, 540, 60, 30);
		
		
//		修改个人信息，jbtChangeInfo
		jbtChangeInfo = new JButton (new ImageIcon("image/changeinfo.gif"));
		jbtChangeInfo.setBounds(315	, 490, 50,50);
		jbtChangeInfo.setOpaque(false);
		jbtChangeInfo.setBorder(null);
		jbtChangeInfo.setContentAreaFilled(false);
		//jbtChangeInfo.addActionListener(new ChangeInfoListener());
		jbtChangeInfo.setToolTipText("点击我，修改个人信息");
		
//		修改个人信息标签
		jlChangeInfo = new JLabel("修改信息",JLabel.CENTER);
		jlChangeInfo.setBounds(310, 540, 60, 30);
		jlChangeInfo.setFont(new Font("微软雅黑",1,14));
		
//		帮助信息，jbtHelp
		jbtHelp = new JButton (new ImageIcon("image/help.gif"));
		jbtHelp.setBounds(380,  490, 50,50);
		jbtHelp.setOpaque(false);
		jbtHelp.setBorder(null);
		jbtHelp.setContentAreaFilled(false);
		jbtHelp.addActionListener(new HelpListener());
		jbtHelp.setToolTipText("点击我，查看帮助信息");
		
//		帮助信息标签
		jlHelp = new JLabel("帮助信息",JLabel.CENTER);
		jlHelp.setBounds(375, 540, 60, 30);
		jlHelp.setFont(new Font("微软雅黑",1,14));
		
//		退出，jbtExit
		jbtExit = new JButton (new ImageIcon("image/exit.gif"));
		jbtExit.setBounds(450,  490, 50,50);
		jbtExit.setOpaque(false);
		jbtExit.setBorder(null);
		jbtExit.setContentAreaFilled(false);
		jbtExit.addActionListener(new ExitListener());
		jbtExit.setToolTipText("不玩了,退出游戏");
		
//		退出标签
		jlExit = new JLabel("退    出",JLabel.LEFT);
		jlExit.setBounds(455, 540, 60, 30);
		jlExit.setFont(new Font("微软雅黑",1,14));
		
//		音乐开关， jcbMusic
//		jcbMusic = new JCheckBox("音乐开关", new ImageIcon("image/music.gif"), false);
		jcbMusic = new JCheckBox(new ImageIcon("image/music.gif"));
		jcbMusic.setBounds(520, 490, 100, 50);
		jcbMusic.setBorder(new LineBorder(Color.WHITE,1));
		jcbMusic.setOpaque(false);
		jcbMusic.addActionListener(new MusicListener());

//		音乐标签
		jlMusic = new JLabel("背景音乐",JLabel.CENTER);
		jlMusic.setBounds(505, 540, 100, 30);
		jlMusic.setFont(new Font("微软雅黑",1,14));
		
		
//		jpMain 添加各个部件  用户列表，个人信息,对话框，输入框， 游戏厅
		jpMain.add(jspUserList);
		jpMain.add(jpMyInfo);
		jpMain.add(jspInput);
		jpMain.add(jspDialog);
		jpMain.add(jspRoom);
		jpMain.add(jbtNewGame);
		jpMain.add(jbtChangeInfo);
		jpMain.add(jbtHelp);
		jpMain.add(jbtExit);
		jpMain.add(jcbMusic);
		jpMain.add(jbtVSPC);
		jpMain.add(jlNewGame);
		jpMain.add(jlVSPC);
		jpMain.add(jlHelp);
		jpMain.add(jlChangeInfo);
		jpMain.add(jlExit);
		jpMain.add(jlMusic);
		
		this.add(jpMain);
		this.setVisible(true);
		
		
		
		//*****************************************************************
		jbtChangeInfo.addActionListener(new JbtChangeInfoListener());
		jbtHelp.addActionListener(new JbtHelpListener());
	}
	
	
	
//	测试方法
	public static void main(String[] args) {
		
		GameHall gh=new GameHall();
		
		
	}
	
//	********************************		监听			******************************
//	游戏室内，棋桌左边（建主玩家）头像的监听
	public class HostLinstener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			JOptionPane.showMessageDialog(null, "主机信息");
		}
	}
	
//	游戏室内，棋桌右边（对战玩家 或者电脑 或者 空闲）头像的监听
	public void challengeListener() {
		
	}
	
//	输入框 jtaInput 的监听：  按回车发送信息
	public void inputListener(KeyAdapter k) {
		
		jtaInput.addKeyListener(k);
	}
	
//	按钮 新建游戏 jbtNewGame 的监听： 点击新建游戏
	public void jbtNewGameListener(ActionListener listener){
		
		jbtNewGame.addActionListener(listener);
	}

//	按钮 修改个人信息 监听 jbtChangeInfo
	public class ChangeInfoListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			
		}
	}
//	按钮 帮助 监听 jbtHelp
	
	public class HelpListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			
		}
	}
//	按钮 退出 监听 jbtExit
	public class ExitListener implements ActionListener {
		public void actionPerformed (ActionEvent e ){
			System.exit(0);
		}
	}
	
//	选择按钮 音乐开关监听 jcbMusic
	public class MusicListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			JOptionPane.showMessageDialog(null, "音乐关闭");
		}
	}
	
//	******************************			监听			********************************
	
	
	
	
	
//	****************************** 			方法			*******************************
	//传递用户列表
	public void addClient(String name,String ip,int state){
		this.userNameString[number]=name;
		this.userIP[number]=ip;
		this.userState[number]=state;
		
		jlUserList[number]=new JLabel("1211111111");
		jlUserList[number].setText(name+"("+state+")");
		if(state==8881){
			jpUserList.add(name+"(在线)");
		}
		if(state==8882){
			jpUserList.add(name+"(主机)");
		}
		if(state==8883){
			jpUserList.add(name+"(正在游戏)");
		}
		if(state==8884){
			jpUserList.add(name+"(人机对战)");
		}
		
		jpGame[number]=new JPanel();
		jlChallenge[number]=new JButton("加入主机");
		//jlUserName[number].setText(name);
		
		/*if(state==8882){
			jtaDialog.append("主机："+name+ip+state+"\n");
			
			
			//添加主机
			jpGame[number]= new JPanel();
			jpGame[number].setBounds(10+(number%2)*180, 20+(number/2)*50, 200, 80);			//设置位置
			jpGame[number].setLayout(null);
			
			
			jlHost[number]= new JButton(new ImageIcon("image/headImage/1small.png"));			//建主玩家的头像，button
			jlHost[number].setBounds(0, 0, 50, 50);
			jlHost[number].setBorder(null);
			jlHost[number].setOpaque(false);
			jlHost[number].setContentAreaFilled(false);
			jlHost[number].addActionListener(new HostLinstener());
			
			jlTable [number] = new JLabel(new ImageIcon("image/tablesmall.png"));				//棋桌图片， label
			jlTable[number].setBounds(70, 0, 50, 50);
			jlTable[number].setBorder(null);
			jlTable[number].setOpaque(false);
			
			
			jlChallenge[number] = new JButton(new ImageIcon("image/headImage/2small.png"));		//对手头像，人或电脑或空闲
			jlChallenge[number].setBorder(null);
			jlChallenge[number].setBounds(140, 0, 50, 50);
			jlChallenge[number].setOpaque(false);
			jlChallenge[number].setContentAreaFilled(false);
			//jlChallenge[i].addActionListener(new ChallengeListener());
			
			jlUserName[number] = new JLabel(name);
			jlUserName[number].setBounds(20,55,50,25);
			
			
			jpGame[number].add(jlHost[number]);
			jpGame[number].add(jlTable[number]);
			jpGame[number].add(jlChallenge[number]);
			jpGame[number].add(jlUserName[number]);
			
			jpRoom.add(jpGame[number]);
			this.repaint();
			
			
			
		}
		*/
		//jtaDialog.append("收到用户："+name+ip+state+"\n");
		
		
		number++;
	}
	
	//用户列表传递结束
	public void endAddClient(){
		//初始化游戏大厅，全是空桌子
		
		/*for(int i=0;i<18-number;i++){
		jpGame[i]= new JPanel();
		jpGame[i].setBounds(10+(i%2)*180, 20+(i/2)*50, 200, 50);			//设置位置
		
		
		jlHost[i]= new JButton(new ImageIcon("image/headImage/1small.png"));			//建主玩家的头像，button
		jlHost[i].setBorder(null);
		jlHost[i].setOpaque(false);
		jlHost[i].setContentAreaFilled(false);
		jlHost[i].addActionListener(new HostLinstener());
		
		jlTable [i] = new JLabel(new ImageIcon("image/tablesmall.png"));				//棋桌图片， label
		jlTable[i].setBorder(null);
		jlTable[i].setOpaque(false);
		
		
		jlChallenge[i] = new JButton(new ImageIcon("image/headImage/2small.png"));		//对手头像，人或电脑或空闲
		jlChallenge[i].setBorder(null);
		jlChallenge[i].setOpaque(false);
		jlChallenge[i].setContentAreaFilled(false);
		//jlChallenge[i].addActionListener(new ChallengeListener());
		
		jpGame[i].add(jlHost[i]);
		jpGame[i].add(jlTable[i]);
		jpGame[i].add(jlChallenge[i]);
		
		jpRoom.add(jpGame[i]);
		repaint();
	}*/
	}
	
	//清楚用户列表
	public void clearClient(){
		number=0;
		jpUserList.removeAll();
		for(int i=0;i<20;i++){
			jlUserList[i]=null;
		}
		//jtaDialog.append("清空左边列表！\n");
		//jtaDialog.append("清空游戏大厅列表！\n");
		//jpRoom.removeAll();
		for(int i=0;i<20;i++){
			//enterHost[i].setText("没有主机");
			jlUserName[i].setText("没有主机");
			//enterHost[i].
			enterHost[i].setEnabled(false);
			enterHost[i].addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
		
	}
	
	
	
//	初始化用户列表的头像数组，全部清除
	public void clearUserHeadImage() {
		
	}
	
//	添加玩家列表的头像数组
	public void addUserHeadImage(ImageIcon arr[]){
		
	}
	
//	修改个人信息 的ID
	public void setID(String newID) {
		this.myID = newID;
		jlID.setText("ID:"+newID);
	}
	
//  修改个人信息 的昵称
	public void setName(String newName) {
		this.myName = newName;
		jlMyName.setText("Name:"+newName);
	}
	
//	胜局加一
	public void winGame (){
		this.myWin ++;
	}
	
//	负局加一
	public void loseGame (){
		this.myLose --;
	}
	
//	升级
	public void gradeUp(){
		this.myGrade++; 
	}
	
//	降级
	public void gradeDown(){
		this.myGrade--;
	}
	//***************************监听*******************************************
	class JbtChangeInfoListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			new ChangeInfo();
		}
		
	}
	
	class JbtHelpListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			new Help();
		}
		
	}
	
//	*****************************			方法 		********************************
}


//	jpMain
class JpMain extends JPanel	{
	
	public JpMain (){
		super();
	}
	
//	*****************  主面板 背景  ************************
//	jpMain的重写paint方法， 画出背景图
	public void paintComponent(Graphics g){
		g.drawImage(new ImageIcon("image/background4.jpg").getImage(), 0, 0, 900,580, this);
	}
//	*****************************************************
	
}


