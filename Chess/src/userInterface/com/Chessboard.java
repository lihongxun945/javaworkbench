package userInterface.com;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.java.swing.plaf.windows.resources.windows;

import axun.com.*;

public class Chessboard extends Canvas{
	//GUI
	public JFrame f=new JFrame("五子棋");
	public JPanel pright=new JPanel();
	public JPanel pleft=new JPanel();
	public JPanel p1=new JPanel();
	public JPanel p2=new JPanel();
	public JPanel p3=new JPanel();
	public JButton startbt=new JButton("开始游戏");
	public JButton restartbt=new JButton("重新开始");
	public JButton setbt=new JButton("设置");
	public JButton undobt=new JButton("悔棋");
	public JButton exitbt=new JButton("退出游戏");
	public JButton hhbt=new JButton("双人对战");
	public JButton netbt=new JButton("建立主机");
	public JButton netbt2=new JButton("加入游戏");
	public JButton hcbt=new JButton("人机对战");
	public JButton endbt=new JButton("结束此局");
	public JLabel lb1=new JLabel("走棋记录：");
	public JLabel timelb=new JLabel("00:00:00");			//计时标签
	public TextArea tr1=new TextArea();
	public JLabel myIdlb=new JLabel("我是");
	public JLabel rivalIdlb=new JLabel("对手");
	
	//聊天用的
	public TextArea chatbox=new TextArea("");
	public TextArea chatinputbox=new TextArea("");
	public JButton chatbt=new JButton("发送");
	
	
	//数据


	private boolean isStart=false;		//是否已经开始游戏
	public boolean lock=false;				//是否锁定棋盘，锁定之后不能下子
	private int computer=0;						//电脑等级，0表示双人对战，123分别表示难度
	private boolean net=false;						//net表示是否是网络对战
	private boolean undo=true;				//是否允许悔棋
	private boolean wait=false;				//是否正在等待对方下棋
	private int mode=0;						//是否已经选择了一种模式
	int state=0;				//当前状态
	String  rivalId=null;
	String myId=null;
	
	private int[][] x;
	private int[][] record;	//走棋记录
	private int step;		//
	private int player;
	private int time;		//计时 ，表示毫秒数
	
	public Chessboard(){
		f.setLayout(new BorderLayout());
		f.setSize(800,600);
		
		f.setLocation(300, 160);
		f.setResizable(false);			//不可改变大小
		
		f.add(pleft,"West");
		pleft.setLayout(new GridLayout(10,1));
		//pleft.add(netbt);
		//pleft.add(netbt2);
		//pleft.add(hhbt);
		pleft.add(hcbt);
		pleft.add(setbt);
		//pleft.add(exitbt);
		pleft.add(myIdlb);
		pleft.add(rivalIdlb);
		
		
		
		
		
		setSize(500,500);
		f.add(this,"Center");
		this.setBackground(Color.green);
		
		
		
		pright.setLayout(new GridLayout(3,1));
		pright.setBackground(Color.yellow);
		f.add(pright,"East");
		pright.add(p1);
		pright.add(p2);
		pright.add(p3);
		
		tr1.setEnabled(true);
		tr1.setBackground(Color.LIGHT_GRAY);
		tr1.setRows(10);
		tr1.setColumns(20);
		tr1.setEditable(false);
		
		p1.setLayout(new GridLayout(6,1,0,10));
		p1.add(startbt);
		p1.add(restartbt);
		p1.add(undobt);
		p1.add(endbt);
		
		
		p1.add(timelb);
		p1.add(lb1);
		p2.add(tr1);
		
		chatbox.setRows(10);
		chatbox.setColumns(20);
		
		chatinputbox.setRows(1);
		chatinputbox.setColumns(10);
		chatbox.setEditable(false);
		p3.setLayout(new GridLayout(3,1));
		p3.add(chatbox);
		p3.add(chatinputbox);
		p3.add(chatbt);
		
		
		
		f.setVisible(true);			//注意最后添加这个
	}
	
	public void paint(Graphics g){
		makeGUI(g);
		draw_circle(g,x);
		
	}
	
	//画棋子
	public void draw_circle(Graphics g,int[][]x){
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){	
				
				if(x[i][j]==1){	//黑子
					
					g.setColor(Color.black);
					g.fillOval((j*30+30)-13, (i*30+30)-13, 26, 26);//根据坐标计算出像素点，画出棋子
				}
				else if(x[i][j]==2){	//白子
					g.setColor(Color.white);
					g.fillOval((j*30+30)-13, (i*30+30)-13, 26, 26);//根据坐标计算出像素点
				}
			}
		}
	}
	
	//画棋盘
	public void makeGUI(Graphics g){
		for(int i=30;i<=450;i+=30){
			g.drawLine(i, 30, i, 450);
			g.drawLine(30,i,450,i);
		}
		for(int i=0;i<15;i++){
			g.drawString(i+"",25+i*30, 20);
			g.drawString(i+"",10,35+i*30);
		}
	}
	
	
	//锁定棋盘
	public void lockChessboard(){
		lock=true;
	}
	//解锁棋盘
	public void unlockChessboard(){
		lock=false;
	}
	
	//胜利对话框
	public void ShowWinDialog(int winner){
		Dialog dialog=new Dialog(f);
		dialog.setTitle("胜利！");
		dialog.setModal(true);
		dialog.setLayout(new FlowLayout());
		dialog.setLocation(400, 250);
	
	
		if(winner==1){
			JLabel lb1=new JLabel("恭喜黑方获胜！");
			dialog.add(lb1);
		}
		else {
			JLabel lb1=new JLabel("恭喜白方获胜！");
			dialog.add(lb1);
		}
		JButton bt1=new JButton("确认");
		dialog.add(bt1);
		dialog.pack();
		dialog.setVisible(true);
		
		}
	
	public void addChessman(int i,int j,int player){
		System.out.println("("+i+","+j+")");
		x[i][j]=player;
		record(i,j,player);
		
	}
	
	public void record(int i,int j,int player){
		record[step][0]=i;
		record[step][1]=j;
		record[step][2]=player;
		step++;
	}
	

	
	
}
