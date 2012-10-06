package axun.com;
import userInterface.com.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import userInterface.com.GameHall;

import com.sun.media.sound.Toolkit;

public class GameCenter extends JFrame{
	

	
	//********************************************************************************************
	GameHall gh=new GameHall();			//游戏大厅界面
	
	private String[] clientInfo;
	private String[] clientSocket;
	private int[] clientState;
	private int clientNumber=0;
	int hostNumber=0;
	
	private String serverIp="localhost";	//服务器地址
	private final int serverPort=6666;	//服务器端口
	static int state=8881;			//当前状态
	
	private DataOutputStream dos=null;
	private DataInputStream dis=null;
	Socket s=null;
	Socket chats=null;
	
	
	String rivalIp="localhost";	//对手ip
	String rivalName="null";
	private String myId="";
	
	
	
	public GameCenter(Socket s,Socket chats,String serverIp,String myId){
		
		/*gh.exitbtListener(new ExitbtListener());
		gh.enterhostbtListener(new EnterHostbtListener());
		gh.clientltListener(new ClientltListener());
		gh.chatbtListener(new ChatbtListener());
		gh.createHostbtListener(new CreateHostbtListener());
		*/
		gh.jbtNewGameListener(new CreateHostbtListener());
		gh.inputListener(new JtaInputListener());
		gh.jbtVSPC.addActionListener(new pcbtListener());
		
		
		
		this.serverIp=serverIp;
		this.s=s;
		this.chats=chats;
		
		changeMyId(myId);
		//启动线程
		new Thread(new ServerCom(s,serverIp,serverPort,this)).start();
		new Thread(new ChatListener(this)).start();
		
		clientInfo=new String[20];
		clientSocket=new String[20];
		clientState=new int[20];
		
	}
	
	public void clearclientlt(){
		gh.clearClient();
		clientNumber=0;
		hostNumber=0;
	}
	
	public void addclient(String name,String socket,int state){
		gh.addClient(name, socket, state);
		clientInfo[clientNumber]=name;
		clientSocket[clientNumber]=socket;
		clientState[clientNumber]=state;
		ghEnterHostListener(clientNumber);
		clientNumber++;
		
		
	}
	
	void ghEnterHostListener(int i){
		if(clientState[i]==8882){

			gh.enterHost[hostNumber].removeAll();
			gh.enterHost[hostNumber].addActionListener(new GhEnterHostListener(i));
			gh.enterHost[hostNumber].setText(clientInfo[i]+","+clientSocket[i]);
			gh.enterHost[hostNumber].setEnabled(true);
			gh.jlUserName[hostNumber].setText(clientInfo[i]);
			hostNumber++;
		}
		
	}
	
	public void endAddClient(){
		gh.endAddClient();
	}
	
	public int getState(){
		
		return state;
	}
	
	//改变玩家的在线状态
	public static void changeState(int i){
		state=i;
	}
	
	public void changeMyId(String id){
		myId=id;
		gh.setID(id);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////以下是监听
	////////////////////////////////////////////////////////////////////////////
	/**
	 *退出按钮监听
	 */
	class ExitbtListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			
		}
		
	}
	
	/**
	 * 创建主机 按钮监听
	 */
	
	class CreateHostbtListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			state=8882;
			Chess chess=new Chess();
			chess.selectMode(1);
			
			chess.changeMyId(myId);
			
		}
		
	}
	/**
	 * 加入游戏 按钮监听
	 *
	 */
	
	class EnterHostbtListener implements ActionListener{
		
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			Chess chess=new Chess();
			chess.setIp(rivalIp);
			chess.selectMode(2);
			state=8883;
			chess.changeMyId(myId);
		}
		
	}
	
	/*gh 加入主机按钮监听
	 * 
	 */
	class GhEnterHostListener implements ActionListener{
		private int number=0;
		public GhEnterHostListener(int num){
			number=num;
		}

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			Chess chess=new Chess();
			chess.setIp(clientSocket[number]);
			chess.selectMode(2);
			state=8883;
			chess.changeMyId(myId);
		}
		
	}
	/*
	 * gh 空主机按钮
	 */
		class EmptyHostListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Chess chess=new Chess();
			chess.setIp(rivalIp);
			chess.selectMode(4);
			state=8884;
			chess.changeMyId(myId);
		}
			
		}
		
	/*
	 * 人机对战 按钮监听
	 * 
	 */
		
		class pcbtListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Chess chess=new Chess();
			chess.setIp(rivalIp);
			chess.selectMode(4);
			state=8884;
			chess.changeMyId(myId);
		}
			
		}
	
	/**
	 * 用户列表 监听
	 *
	 */
	
	class ClientltListener implements ItemListener{

		public void itemStateChanged(ItemEvent arg0) {
			// TODO Auto-generated method stub
			/*rivalIp=clientSocket[gh.clientlt.getSelectedIndex()];
			rivalName=clientInfo[gh.clientlt.getSelectedIndex()];
			gh.infolb.setText("对方ip:"+rivalIp);
			*/
		}
	}
	/**
	 * 发送消息 监听
	 */
	class JtaInputListener extends KeyAdapter{
		private DataOutputStream dos=null;
		
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyChar()==KeyEvent.VK_ENTER){
					try{
						dos=new DataOutputStream(chats.getOutputStream());
						dos.writeUTF(gh.jtaInput.getText());
						gh.jtaInput.setText("");
						gh.jtaInput.append("\b");
					}catch(Exception ep){
						gh.jtaDialog.append("failed:"+e.toString());
					}
			}
		}

		
		
	}
	
	
}

//与服务器通信，判断用户是否在线

class ServerCom implements Runnable{
	private String ip=null;
	private int port;
	private DataOutputStream dos=null;
	private DataInputStream dis=null;
	private Socket s=null;
	
	private GameCenter gc=null;
	
	public ServerCom(Socket s,String ips,int serverport,GameCenter gc){
		ip=ips;
		port=serverport;
		this.s=s;
		this.gc=gc;
		try{
			this.dis=new DataInputStream(s.getInputStream());
			this.dos=new DataOutputStream(s.getOutputStream());
		}catch(Exception e){}
		
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
		while(true){
			try{
				int r=dis.readInt();
				//是否在线
				if(r==7771){
					System.out.println("收到服务器信息："+7771);
					dos.writeInt(gc.state);
					//if(gc.getState()==8883) dos.writeUTF(gc.rivalName);
				}
				//获取在线用户列表
				if(r==7772){
					System.out.println("收到服务器信息："+7772);
					dos.writeInt(6661);
					System.out.println("发送服务器信息："+6662);
					System.out.println("开始接受在线用户列表……");
					String clientInfo=null;
					String clientSocketString=null;
					int state;
					int cn=0;
					gc.clearclientlt();		//先清空列表
					
					while(true){
						clientInfo="未命名";
						clientSocketString="null";
						if((clientInfo=dis.readUTF()).equals("END")) break;  //读到END表示结束
						if((clientSocketString=dis.readUTF()).equals("END")) break;
						state=dis.readInt();
						System.out.println("收到用户信息：姓名："+clientInfo+"IP地址："+clientSocketString);
						gc.addclient(clientInfo, clientSocketString,state);
						cn++;
					}
					System.out.println("用户列表接受完毕！共有在线用户"+cn+"人！");
					gc.endAddClient();
				}
				
				//获取正在游戏的双方id
				if(r==7773){
					System.out.println("接收对战双方列表……");
					dos.writeInt(6662);
					String rivalName1=null;
					String rivalName2=null;
					while(true){
						rivalName1=dis.readUTF();
						rivalName2=dis.readUTF();
						if(rivalName1.equalsIgnoreCase("END")) break;
						if(rivalName2.equalsIgnoreCase("END")) break;
						//gc.gh.chatta.append(rivalName1+"和"+rivalName2+"开战了！");
					}
				}
				
			}catch(Exception e){
				System.out.println("与服务器连接异常！10秒钟后将重新连接……");
				try{
					Thread.sleep(10000);
					
				}catch(Exception ecp){}
			}
			
		}
	}
	
}

/**
 * 监听服务器发送的消息
 */
class ChatListener implements Runnable{
	private DataInputStream dis=null;
	private GameCenter gc=null;
	
	public ChatListener(GameCenter gc){
		this.gc=gc;
	}

	public void run() {
		// TODO Auto-generated method stub
		String read=null;
		while(true){
			try{
				dis=new DataInputStream(gc.chats.getInputStream());
				read=dis.readUTF();
				System.out.println(read);
				gc.gh.jtaDialog.append(read+"\n");
			}catch(Exception e){
				
			}
		}
	}
	
}
 
