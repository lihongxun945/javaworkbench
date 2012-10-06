package Compute;
import axun.com.*;

/*
 * 当电脑开始计算式，锁定棋盘，计算完成后，调用chess中的computeComplete函数；
 * 
 * 
 */
public class Compute {
	Chess chess=null;
	int[][] chessboard=null;
	int comLevel=1;
	int player=1;

	public Compute(int[][] chessboard,int player,int comLevel,Chess c){
		this.chess=c;
		this.chessboard=chessboard;
		this.comLevel=comLevel;
		this.player=player;
		
		chess.lockChessboard();
		//chess.printMessage("电脑正在计算……");
		Thread comThread=new Thread(new ComputeThread(chess,this));
		comThread.start();
		
		
	}
}

/*
 * 电脑计算用的单独线程
 */
class ComputeThread implements Runnable{
	private Chess chess=null;
	private Compute compute=null;
	
	public ComputeThread(Chess chess,Compute com){
		this.chess=chess;
		this.compute=com;
	}

	public void run() {
		// TODO Auto-generated method stub
		
		MyComputer computer=new MyComputer(compute.chessboard,compute.player,compute.comLevel,chess);
		computer.think();
		Point point=computer.getPoint();
		
		chess.computeComplete(point);
		
		
		//简单电脑
		if(compute.comLevel==1){
			
		}
	}
	
	
}