package Compute;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import axun.com.*;

public class MyComputer {
	
         
	private  int[][] chessboard=new int[25][25];          //棋盘 

	private  int[][] five=new int[5][5];                  //5*5小棋盘 
	private int last1,last2;                    //记录上一步  
	private int[][] comresults=new int[25][25];             //存储分数
	private int[][] humresults=new int[25][25];
	private  int[][] virtualresults=new int[25][25];          //虚拟分数，可以用来打草稿 
	private int level=1;
 
 
	private int max_com,max_hum,max_com_i,max_com_j,max_hum_i,max_hum_j;           //存储电脑和玩家的最高分数 和相应的坐标 
                    //反回主菜单； 
	
	Chess chess=null;
	
	public MyComputer(int[][] cb,int player,int level,Chess chess){
		//初始化
		this.chess=chess;
		this.level=level;
		for(int i=0;i<25;i++)
			for(int j=0;j<25;j++){
				chessboard[i][j]=3;	//3表示边界
				comresults[i][j]=0;
				humresults[i][j]=0;
			}
		
		 for(int i=0;i<5;i++)
             for(int j=0;j<5;j++)
                    {five[i][j]=0;}
     
     last1=-1;last2=-1;       //-1表示还未开始 
     max_com=0;max_hum=0;max_com_i=0;
     max_com_j=0;max_hum_i=0;max_hum_j=0;
     
     
     
     for(int i=0;i<15;i++)
    	 for(int j=0;j<15;j++){
    			chessboard[i+5][j+5]=cb[i][j];
    			//if(cb[i][j]!=0) System.out.println("存在棋子："+cb[i][j]+"("+i+","+j+")");
    		
    	 }
     
     
     if(player==2) {			//交换棋子
    	 for(int i=5;i<20;i++)
        	 for(int j=5;j<20;j++){
        		 if(chessboard[i][j]==0) continue;
        			if(chessboard[i][j]==1) chessboard[i][j]=2;
        			else chessboard[i][j]=1;
        		
        	 }
     }
     if(count(1)==0) computerSpeak(0);			//电脑讲话
	}
	

	void getfive(int i,int j){
	     for(int m=0;m<5;m++)
	         for(int n=0;n<5;n++)
	                 five[m][n]=chessboard[m+i][n+j];
	}
	
	void resetVirtualResults() {
		for(int i=0;i<25;i++)
			for(int j=0;j<25;j++){
				virtualresults[i][j]=0;
			}
	}

	
	//判断是否有人赢 注意此处！！！！！！！
	int ifwin(){
	    for(int i=5;i<=15;i++)
	        for(int j=5;j<=15;j++){
	              getfive(i,j);
	              for(int m=0;m<5;m++){
	                      if(five[m][0]==1&&five[m][1]==1&&five[m][2]==1&&five[m][3]==1&&five[m][4]==1)
	                          return (1);
	                      if(five[0][m]==1&&five[1][m]==1&&five[2][m]==1&&five[3][m]==1&&five[4][m]==1)
	                          return (1);}
	              if(five[0][0]==1&&five[1][1]==1&&five[2][2]==1&&five[3][3]==1&&five[4][4]==1)
	                   return (1);
	              if(five[0][4]==1&&five[1][3]==1&&five[2][2]==1&&five[3][1]==1&&five[4][0]==1)
	                   return (1);
	              }
	              
	    for(int i=5;i<=15;i++)
	        for(int j=5;j<=15;j++){
	              getfive(i,j);
	              for(int m=0;m<5;m++){
	                      if(five[m][0]==2&&five[m][1]==2&&five[m][2]==2&&five[m][3]==2&&five[m][4]==2)
	                          return 2;
	                      if(five[0][m]==2&&five[1][m]==2&&five[2][m]==2&&five[3][m]==2&&five[4][m]==2)
	                          return 2;}
	              if(five[0][0]==2&&five[1][1]==2&&five[2][2]==2&&five[3][3]==2&&five[4][4]==2)
	                   return 2;
	              if(five[0][4]==2&&five[1][3]==2&&five[2][2]==2&&five[3][1]==2&&five[4][0]==2)
	                   return 2;
	              }
	    
	    return 0;
	}
	
	public Point getPoint(){
		Point p;
		if(last1==0&&last2==0){
			if(chessboard[12][12]==0)
			p=new Point(7,7);
			else 
				p=new Point(8,8);
		}else{
			p=new Point(last1-5,last2-5);			//注意减去
		}
		return p;
	}
	
	//数棋子
	int count(int p){
	    int num=0;
	     for(int i=5;i<20;i++)
	         for(int j=5;j<20;j++){
	                 if(chessboard[i][j]==p) num++;
	                
	                 }
	     return num;
	}

	
	//电脑思考 
	public void think(){
	     
	     
	     //首先判断是否可以下一个子就立即赢或输,即成五 
	     for(int i=5;i<20;i++){
	         for(int j=5;j<20;j++){
	                 if(chessboard[i][j]!=0) continue;       //找空位 
	                  chessboard[i][j]=1;
	                  if(ifwin()==1) {last1=i;last2=j;return;}                  //电脑赢 
	                  chessboard[i][j]=0;                      //没用的位置不下子 
	                  }
	         computerSpeak(1);			//电脑讲话
	         }
	         
	     for(int i=5;i<20;i++){
	         for(int j=5;j<20;j++){
	                 if(chessboard[i][j]!=0) continue;       //找空位 
	                  chessboard[i][j]=2;
	                  if(ifwin()==2) {chessboard[i][j]=1;last1=i;last2=j;return;}                  //阻止玩家赢 
	                  chessboard[i][j]=0;                      //没用的位置不下子 
	                  }
	         computerSpeak(2);			//电脑讲话
	         }    
	         

	         
	         
	      //电脑走的第一步，跟子，避开边界 
	     if(last1==-1){
	    	 last1=8;last2=8;
	     }
	   getresults(); 
	     
	            
	        
	  
	}   



	public void getresults(){
	     
	          for(int i=5;i<20;i++)
	            for(int j=5;j<20;j++){
	                    comresults[i][j]=0;
	                    humresults[i][j]=0;}                //每次先置零 
	       max_com=0;max_hum=0;max_com_i=0;
	       max_com_j=0;max_hum_i=0;max_hum_j=0;
	       
	       
	       //开始打分 
	       r_four();
	       r_three();
	       r_two();
	       
	       //分别筛选电脑和玩家的最高分，并保存其坐标 
	       for(int i=5;i<20;i++)
	            for(int j=5;j<20;j++){
	                    if(comresults[i][j]>max_com){
	                                                 max_com=comresults[i][j];
	                                                 max_com_i=i;
	                                                 max_com_j=j;
	                                                 }
	                    if(humresults[i][j]>max_hum){
	                                                 max_hum=humresults[i][j];
	                                                 max_hum_i=i;
	                                                 max_hum_j=j;
	                                                 }
	                    } 
	                    
	       //分析两个最高分，决定攻还是防
	       
	       if(max_com>=100) {                                      //电脑能成四 
	                       chessboard[max_com_i][max_com_j]=1; 
	                       last1=max_com_i;last2=max_com_j;   
	                       computerSpeak(3);
	                       return;
	                       }        
	       if(max_hum>=100) {                                      //阻止玩家四 
	                       chessboard[max_hum_i][max_hum_j]=1;
	                       last1=max_hum_i;last2=max_hum_j;   
	                       computerSpeak(4);
	                       return;
	                       }
	       
	       if(max_com>=18) {                                      //电脑能成双三 （或更多） 
	                       chessboard[max_com_i][max_com_j]=1; 
	                       last1=max_com_i;last2=max_com_j;   
	                       computerSpeak(5);
	                       return;
	                       }        
	       if(max_hum>=18) {                                      //阻止玩家双三 （或更多） 
	                       chessboard[max_hum_i][max_hum_j]=1;
	                       last1=max_hum_i;last2=max_hum_j;    
	                       computerSpeak(6);
	                       return;
	                       }
	       
	       //此处多层思考
	       if(level==2){				//2级的电脑才能
		       if(count(1)>=3){
		    	   if(deepThink(1)) {
		    		   computerSpeak(8);
		    		   return;
		    	   }
		    	   if(deepThink(2)) {
		    		   computerSpeak(8);
		    		   return;
		    	   }
		    	   if(deepThink_2(1)) {
		    		   computerSpeak(8);
		    		   return;
		    	   }
		    	   if(deepThink_2(2)) {
		    		computerSpeak(8);
		    	    return;
		    	   }
		       }
	       }
	       if(max_com==17||max_com==16) {                                      //另一种不能取胜双三
	                       chessboard[max_com_i][max_com_j]=1;
	                       last1=max_com_i;last2=max_com_j;      
	                       computerSpeak(9);
	                       return;
	                       }        
	       if(max_hum==17||max_hum==16) {                                      
	                       chessboard[max_hum_i][max_hum_j]=1;
	                       last1=max_hum_i;last2=max_hum_j;   
	                       computerSpeak(10);
	                       return;
	                       }
	                       
	                       
	       if(max_com<16&&max_com>=10) {                                      //3,3+1 ,3+2，单4 
	                       chessboard[max_com_i][max_com_j]=1;
	                       last1=max_com_i;last2=max_com_j;     
	                       computerSpeak(11);
	                       return;
	                       }        
	       if(max_hum<16&&max_hum>=10) {                                      
	                       chessboard[max_hum_i][max_hum_j]=1; 
	                       last1=max_hum_i;last2=max_hum_j;    
	                       computerSpeak(12);
	                       return;
	                       }                                      

	       //其他情况不管玩家，电脑走最好的
	       chessboard[max_com_i][max_com_j]=1;
	       last1=max_com_i;last2=max_com_j; 
	       computerSpeak(13);
	       return;
	} 
//****************************************  电脑讲话 ********************************************8
	//电脑会根据当前局势讲出一些有意义的话
	//这个是说话的借口
	private void speak(String s){
		chess.printMessage(s);
	}
	
	//电脑讲话
	public void computerSpeak(int i){
		//产生一个随机数，随即选择一句话
		Random rd=new Random();
		//System.out.println(""+rd.nextInt(10));
		
		int rand=rd.nextInt(10);
		
		if(count(1)==30){
			
			speak("小样，竟然能挺到现在都没死！");return;
		}
		if(count(1)==50){
			speak("这么多棋子看得我是眼花缭乱！");return;
		}
		if(count(1)==60){
			speak("我都已经没有耐心了！");return;
		}
		
		//随机乱喷
		if(rand<5) {
			computerSpeak_2();	//乱喷
			return;
		}
		
		
		//开局讲话
		if(i==0){
			
			rand=rd.nextInt(20);
			chess.changeExpression(2);
			switch(rand){
				//case(0):speak("");break;
				//case(1):speak("");break;
				//case(2):speak("");break;
				//case(3):speak("");break;
				//case(4):speak("");break;
				//case(5):speak("");break;
				//case(6):speak("");break;
				//case(7):speak("");break;
				//case(8):speak("");break;
				//case(9):speak("");break;
				
				default:speak("你知道独孤求败是什么感觉吗？");break;
			}
			
		}
		else if(i==1){
			rand=rd.nextInt(20);
			switch(rand){
				//case(0):speak("");break;
				//case(1):speak("");break;
				//case(2):speak("");break;
				//case(3):speak("");break;
				//case(4):speak("");break;
				//case(5):speak("");break;
				//case(6):speak("");break;
				//case(7):speak("");break;
				//case(8):speak("");break;
				//case(9):speak("");break;
				default:speak("胜利来的是如此轻松");break;
			}
		}
		else if(i==2){
			rand=rd.nextInt(20);
			switch(rand){
			//case(0):speak("");break;
			//case(1):speak("");break;
			//case(2):speak("");break;
			//case(3):speak("");break;
			//case(4):speak("");break;
			//case(5):speak("");break;
			//case(6):speak("");break;
			//case(7):speak("");break;
			//case(8):speak("");break;
				case(9):speak("我头晕眼花了……");break;
				default:speak("我们和局把……");break;
			}
		}
		else if(i==3){
			rand=rd.nextInt(20);
			switch(rand){
			//case(0):speak("");break;
				//case(1):speak("");break;
			////case(2):speak("");break;
			//case(3):speak("");break;
			//case(4):speak("");break;
			//case(5):speak("");break;
			//case(6):speak("");break;
			//case(7):speak("");break;
				case(8):speak("简直是轻松没花样！");break;
				case(9):speak("简直像踩死一只蚂蚁一样简单");break;
				default:speak("小样，你还嫩呢！");break;
			}
		}
		else if(i==4){
			rand=rd.nextInt(20);
			switch(rand){
			//case(0):speak("");break;
			//case(1):speak("");break;
			//case(2):speak("");break;
			//case(3):speak("");break;
			//case(4):speak("");break;
			//case(5):speak("");break;
			//case(6):speak("");break;
			//case(7):speak("");break;
			//case(8):speak("");break;
				case(9):speak("其实我一直在用脚趾头下棋");break;
				default:speak("你是不是以为你会赢？");break;
			}
		}
		else if(i==5){
			rand=rd.nextInt(20);
			switch(rand){
			//case(0):speak("");break;
			//case(1):speak("");break;
			//case(2):speak("");break;
			//case(3):speak("");break;
			//case(4):speak("");break;
			//case(5):speak("");break;
			//case(6):speak("");break;
			//case(7):speak("");break;
				case(8):speak("我猜你已经绝望了~~");break;
				case(9):speak("告诉你一个小秘密……&*%￥%…#￥…………？");break;
				default:speak("其实双三不是最厉害的……");break;
			}
		}
		else if(i==6){
			rand=rd.nextInt(20);
			switch(rand){
			//case(0):speak("");break;
			//case(1):speak("");break;
			//case(2):speak("");break;
			//case(3):speak("");break;
			//case(4):speak("");break;
			//case(5):speak("");break;
			//case(6):speak("");break;
			//case(7):speak("");break;
				case(8):speak("你肯定在作弊！");break;
				case(9):speak("我真的要生气啦！");break;
				default:speak("这个是怎么回事呢？…………");break;
			}
		}
		else if(i==7){
			rand=rd.nextInt(20);
			switch(rand){
			//case(0):speak("");break;
			//case(1):speak("");break;
			//case(2):speak("");break;
			//case(3):speak("");break;
			//case(4):speak("");break;
			//case(5):speak("");break;
			//case(6):speak("");break;
			//case(7):speak("");break;
			//case(8):speak("");break;
				case(9):speak("你猜这一局谁会赢？");break;
				default:speak("这只是一个开始……");break;
			}
		}
		else if(i==8){
			rand=rd.nextInt(20);
			switch(rand){
			//case(0):speak("");break;
			//case(1):speak("");break;
			//case(2):speak("");break;
			//case(3):speak("");break;
			//case(4):speak("");break;
			//case(5):speak("");break;
			//case(6):speak("");break;
			//case(7):speak("");break;
				case(8):speak("我能思考到第100步你信吗？");break;
				case(9):speak("猴哥不发威你当我是病猫？");break;
				default:speak("我正在思考……请不要讲话……");break;
			}
		}
		else if(i==9){
			rand=rd.nextInt(20);
			switch(rand){
			//case(0):speak("");break;
			//case(1):speak("");break;
			//case(2):speak("");break;
			//case(3):speak("");break;
			//case(4):speak("");break;
			//case(5):speak("");break;
			//case(6):speak("");break;
			//case(7):speak("");break;
			//case(8):speak("");break;
				case(9):speak("难道这样也行？");break;
				default:speak("其实是我看错了……");break;
			}
		}
		else if(i==10){
			rand=rd.nextInt(20);
			switch(rand){
			//case(0):speak("");break;
			//case(1):speak("");break;
			//case(2):speak("");break;
			//case(3):speak("");break;
			//case(4):speak("");break;
			//case(5):speak("");break;
			//case(6):speak("");break;
			//case(7):speak("");break;
				case(8):speak("这样搞是不对滴！~~");break;
				case(9):speak("这样也想赢？哥们你也太天真了！");break;
				default:speak("你这个玩意是假的！~");break;
			}
		}
		else if(i==11){
			rand=rd.nextInt(20);
			switch(rand){
			//case(0):speak("");break;
			//case(1):speak("");break;
			//case(2):speak("");break;
			//case(3):speak("");break;
			//case(4):speak("");break;
			//case(5):speak("");break;
			//case(6):speak("");break;
			//case(7):speak("");break;
				case(8):speak("怕了吧");break;
				case(9):speak("有时候我也会瞎蒙一次~~");break;
				default:speak("我是不是已经赢了？");break;
			}
		}
		else if(i==12){
			rand=rd.nextInt(20);
			switch(rand){
			//case(0):speak("");break;
			//case(1):speak("");break;
			//case(2):speak("");break;
			//case(3):speak("");break;
			//case(4):speak("");break;
			//case(5):speak("");break;
			//case(6):speak("");break;
			//case(7):speak("");break;
				//case(8):speak("");break;
				case(9):speak("哎~~你这智商真的没治了~~");break;
				default:speak("你是瞎猜的吧？");break;
			}
		}
		else if(i==13){
			rand=rd.nextInt(20);
			switch(rand){
			//case(0):speak("");break;
			//case(1):speak("");break;
			//case(2):speak("");break;
			//case(3):speak("");break;
			//case(4):speak("");break;
			//case(5):speak("");break;
			//case(6):speak("");break;
			//case(7):speak("");break;
				case(8):speak("猴哥很纠结……");break;
				case(9):speak("what a Fucking day!");break;
				default:speak("真让人蛋疼！！……");break;
			}
		}
		
		
		
	}
	
	//这里是乱喷的话
	void computerSpeak_2(){
		Random rd=new Random();
		
		int rand=rd.nextInt(30);
		chess.changeExpression(2);
		switch(rand){
			case(0):speak("你竟然连我的大名都没听说过！鄙视你！");break;
			case(1):speak("你们人类都是我们猴子进化失败的产物");break;
			case(3):speak("等会，我去上个厕所……");break;
			case(4):speak("你不就是上次败给我的那个sb吗？");break;
			case(5):speak("我猜你现在一定已经焦头烂额了！");break;
			case(6):speak("猴子才是世界上最聪明的动物！");break;
			case(7):speak("哼哼哈嘿……快使用双节棍！");break;
			case(8):speak("傻逼了吧~~~~~~~~");break;
			case(9):speak("五子棋这种弱智的玩意~~");break;
			case(10):speak("今天天气真好~");break;
			case(11):speak("我也以用尾巴跟你下棋");break;
			//case(12):speak("");break;
			//case(13):speak("");break;
			//case(14):speak("");break;
			//case(15):speak("");break;
			//case(16):speak("");break;
			case(17):speak("不要勾引我哦~~");break;
			case(18):speak("你的话很冷耶");break;
			case(19):speak("拜托别想我了~！~~");break;
			case(20):speak("去哪个妞家呢？…");break;
			case(21):speak("采花大盗驾到！");break;
			case(23):speak("我是超级大帅哥");break;
			case(24):speak("瞧，猴哥耍太极……");break;
			case(25):speak("宝贝来照一张");break;
			case(26):speak("将泡妞进行到底！");break;
			case(27):speak("天马流星拳！！！");break;
			case(28):speak("眼保健操开始……");break;
			case(29):speak("人家要抱抱嘛~~");break;
			default:speak("我也喜欢看喜洋洋……");break;
	}
	}
	
	//下子，下完子之后会触发一次重新打分
	void put(int i,int j, int p) {
		chessboard[i][j]=p;
		reMark(i, j);
	}
	//取消下子，会触发一次重新打分
	void unput(int i,int j) {
		chessboard[i][j]=0;
		reMark(i, j);
	}
	
	//////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	//以下是多层思考
	boolean deepThink(int player){
		
		for(int i=5;i<20;i++)
			for(int j=5;j<20;j++){
				if(chessboard[i][j]!=0) continue;
				
				if((neighbourCheck(i, j, 1)||neighbourCheck(i, j, 2))==false) continue;
				
				if(player==1){
				//判断电脑自己的
				put(i, j, 1);
			       
		
			     
			       for(int p=5;p<20;p++)
						for(int q=5;q<20;q++){
							
							//堵住任何一个漏洞还有一个 则必胜
							if(comresults[p][q]>=18) {
								put(p, q, 2);
								for(int a=5;a<20;a++)
									for(int b=5;b<20;b++){
										if(comresults[a][b]>=18){
											 	last1=i;last2=j;
									    	   System.out.println("第二层 攻:("+(i-5)+","+(j-5)+")");
									    	   printBoard();
									    	   return true;
										}
									}
								//注意还原
								unput(p, q);
								
							}
						}
			       
			      //注意还原假设的走棋
			       unput(i, j);
				}
				
			       
			    if(player==2)  { 
			       //组织玩家的
			       put(i, j, 2);
				     
				       
				       for(int p=5;p<20;p++)
							for(int q=5;q<20;q++){
								
								
								//堵住任何一个漏洞还有一个 则必胜
								if(humresults[p][q]>=18) {
									put(p, q, 1);
									for(int a=5;a<20;a++)
										for(int b=5;b<20;b++){
											if(humresults[a][b]>=18){
												   last1=i;last2=j;
										    	   System.out.println("第二层 守:("+(i-5)+","+(j-5)+")");
										    	   printBoard();
										    	   return true;
											}
										}
									//注意还原
									unput(p,q);;
									
								}
							}
				      
				      
				      //注意还原假设的走棋
				       unput(i, j);
				       
				
			}
			}
		System.out.println("第二层 无结果");
		return false;
	}
	void printBoard() {
//		for(int i=5;i<20;i++) {
//			System.out.println(Arrays.toString(comresults[i]));
//		}
//		for(int i=5;i<20;i++) {
//			System.out.println(Arrays.toString(humresults[i]));
//		}
	}
	
	//思考到下两步
	/*
	 * 
	 */
	boolean deepThink_2(int p){
		
		System.out.println("第三层思考……");
		
		int a=0,b=0;
		boolean flag=false;
		for(int i=5;i<20;i++)
			for(int j=5;j<20;j++){
				flag=false;			//初始化，一开始没有假设对方的下棋
				if(chessboard[i][j]!=0) continue;
				if((neighbourCheck(i, j, 1)||neighbourCheck(i, j, 2))==false) continue;

				if(p==1){
					//判断电脑自己的
					if(neighbourCheck(i, j, 1)==false) continue;
					//自己假设下一个子
					put(i, j, 1);
					//简单的假设，如果有一个高分的位置，对方会堵住
					int best=0;
					int best_x=0,best_y=0;
					for(a=5;a<20;a++)
						for(b=5;b<20;b++){
							if(comresults[a][b]>=best) {
								best_x=a;best_y=b;
								best=comresults[a][b];
								
							}
						}
					put(best_x,best_y,2);		//假设下子，注意还原
					a=best_x;b=best_y;
					flag=true;			//表明已经假设下子
						

					
					
					if(deepThink(1)){
						last1=i;last2=j;
						System.out.println("第三层：攻：("+(i-5)+","+(j-5)+")");
						return true;
					}
					 unput(i, j);//注意还原假设的走棋
					 if(flag) unput(a, b);
				}
			       
			     if(p==2){  
			       //阻止玩家的
			    	 if(neighbourCheck(i, j, 2)==false) continue; 
			      put(i, j, 2);
			       
			    //简单的假设，如果有一个高分的位置，对方会堵住
					int best=0;
					int best_x=0,best_y=0;
					for(a=5;a<20;a++)
						for(b=5;b<20;b++){
							if(comresults[a][b]>=best) {
								best_x=a;best_y=b;
								best=comresults[a][b];
								
							}
						}
					put(best_x,best_y,1);		//假设下子，注意还原
					a=best_x;b=best_y;
					flag=true;			//表明已经假设下子
						

					
					
					if(deepThink(2)){
						last1=i;last2=j;
						System.out.println("第三层：守：("+(i-5)+","+(j-5)+")");
						return true;
					}
			       unput(i, j);//注意还原假设的走棋
			       if(flag) unput(a, b);
			     } 
				       
				
		
				
			}
		System.out.println("第三层无结果");
		return false;
	}

	//下了某个子之后，只对它周围的棋子重新打分
	void reMark(int i, int j){
		 //开始打分 
	       for(int p=i-3;p<i+3;p++){
	    	   if(p<5 || p>20) continue;
	            for(int q=j-3;q<j+3;q++){
	               if(q<5 || q>20) continue;
	               //因为打分算法是追加分数，所以任何时候重新打分一定要清空
	               comresults[p][q]=0;
                   humresults[p][q]=0;
	               r_four(p, q);
	 		       r_three(p, q);
	 		       r_two(p, q);
	            }                //每次先置零
	       }
		       
	}
	//全部重新打分
	void reMark(){
		 //开始打分 
	       for(int p=5;p<20;p++)
	            for(int q=5;q<20;q++){
	                    comresults[p][q]=0;
	                    humresults[p][q]=0;}                //每次先置零 
		       r_four();
		       r_three();
		       r_two();
	}
	
	
	
	//检查一个点三格周围有没有同色的子
	boolean neighbourCheck(int i,int j,int p){
		int length = 2;
		for(int x=i-length;x<i+length;x++) {
			for(int y=j-length;y<j+length;y++) {
				if(chessboard[x][y]==p) return true;
			}
		}
		return false;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	//以下全是打分的算法
	void r_four(int i, int j){

		if(chessboard[i][j]!=0) return;
        
        //第一种情况    xooo@x
        
        if(chessboard[i][j+1]==0&&chessboard[i][j-1]==1&&chessboard[i][j-2]==1&&chessboard[i][j-3]==1&&chessboard[i][j-4]==0) comresults[i][j]+=100;
        if(chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==1&&chessboard[i-4][j-4]==0) comresults[i][j]+=100; 
        if(chessboard[i+1][j]==0&&chessboard[i-1][j]==1&&chessboard[i-2][j]==1&&chessboard[i-3][j]==1&&chessboard[i-4][j]==0) comresults[i][j]+=100;
        if(chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==1&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==1&&chessboard[i-4][j+4]==0) comresults[i][j]+=100;
        if(chessboard[i][j-1]==0&&chessboard[i][j+1]==1&&chessboard[i][j+2]==1&&chessboard[i][j+3]==1&&chessboard[i][j+4]==0) comresults[i][j]+=100;
        if(chessboard[i-1][j-1]==0&&chessboard[i+1][j+1]==1&&chessboard[i+2][j+2]==1&&chessboard[i+3][j+3]==1&&chessboard[i+4][j+4]==0) comresults[i][j]+=100;
        if(chessboard[i-1][j]==0&&chessboard[i+1][j]==1&&chessboard[i+2][j]==1&&chessboard[i+3][j]==1&&chessboard[i+4][j]==0) comresults[i][j]+=100;
        if(chessboard[i-1][j+1]==0&&chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==1&&chessboard[i+4][j-4]==0) comresults[i][j]+=100;
        
        if(chessboard[i][j+1]==0&&chessboard[i][j-1]==2&&chessboard[i][j-2]==2&&chessboard[i][j-3]==2&&chessboard[i][j-4]==0) humresults[i][j]+=100;
        if(chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==2&&chessboard[i-3][j-3]==2&&chessboard[i-4][j-4]==0) humresults[i][j]+=100; 
        if(chessboard[i+1][j]==0&&chessboard[i-1][j]==2&&chessboard[i-2][j]==2&&chessboard[i-3][j]==2&&chessboard[i-4][j]==0) humresults[i][j]+=100;
        if(chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==2&&chessboard[i-2][j+2]==2&&chessboard[i-3][j+3]==2&&chessboard[i-4][j+4]==0) humresults[i][j]+=100;
        if(chessboard[i][j-1]==0&&chessboard[i][j+1]==2&&chessboard[i][j+2]==2&&chessboard[i][j+3]==2&&chessboard[i][j+4]==0) humresults[i][j]+=100;
        if(chessboard[i-1][j-1]==0&&chessboard[i+1][j+1]==2&&chessboard[i+2][j+2]==2&&chessboard[i+3][j+3]==2&&chessboard[i+4][j+4]==0) humresults[i][j]+=100;
        if(chessboard[i-1][j]==0&&chessboard[i+1][j]==2&&chessboard[i+2][j]==2&&chessboard[i+3][j]==2&&chessboard[i+4][j]==0) humresults[i][j]+=100;
        if(chessboard[i-1][j+1]==0&&chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==2&&chessboard[i+3][j-3]==2&&chessboard[i+4][j-4]==0) humresults[i][j]+=100;
        
        
        //第二种情况   xoo@ox
        
        if(chessboard[i][j+2]==0&&chessboard[i][j+1]==1&&chessboard[i][j-1]==1&&chessboard[i][j-2]==1&&chessboard[i][j-3]==0) comresults[i][j]+=100;
        if(chessboard[i+2][j+2]==0&&chessboard[i+1][j+1]==1&&chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==0) comresults[i][j]+=100; 
        if(chessboard[i+2][j]==0&&chessboard[i+1][j]==1&&chessboard[i-1][j]==1&&chessboard[i-2][j]==1&&chessboard[i-3][j]==0) comresults[i][j]+=100;
        if(chessboard[i+2][j-2]==0&&chessboard[i+1][j-1]==1&&chessboard[i-1][j+1]==1&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==0) comresults[i][j]+=100;
        if(chessboard[i][j-2]==0&&chessboard[i][j-1]==1&&chessboard[i][j+1]==1&&chessboard[i][j+2]==1&&chessboard[i][j+3]==0) comresults[i][j]+=100;
        if(chessboard[i-2][j-2]==0&&chessboard[i-1][j-1]==1&&chessboard[i+1][j+1]==1&&chessboard[i+2][j+2]==1&&chessboard[i+3][j+3]==0) comresults[i][j]+=100;
        if(chessboard[i-2][j]==0&&chessboard[i-1][j]==1&&chessboard[i+1][j]==1&&chessboard[i+2][j]==1&&chessboard[i+3][j]==0) comresults[i][j]+=100;
        if(chessboard[i-2][j+2]==0&&chessboard[i-1][j+1]==1&&chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==0) comresults[i][j]+=100;
        
        if(chessboard[i][j+2]==0&&chessboard[i][j+1]==2&&chessboard[i][j-1]==2&&chessboard[i][j-2]==2&&chessboard[i][j-3]==0) humresults[i][j]+=100;
        if(chessboard[i+2][j+2]==0&&chessboard[i+1][j+1]==2&&chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==2&&chessboard[i-3][j-3]==0) humresults[i][j]+=100; 
        if(chessboard[i+2][j]==0&&chessboard[i+1][j]==2&&chessboard[i-1][j]==2&&chessboard[i-2][j]==2&&chessboard[i-3][j]==0) humresults[i][j]+=100;
        if(chessboard[i+2][j-2]==0&&chessboard[i+1][j-1]==2&&chessboard[i-1][j+1]==2&&chessboard[i-2][j+2]==2&&chessboard[i-3][j+3]==0) humresults[i][j]+=100;
        if(chessboard[i][j-2]==0&&chessboard[i][j-1]==2&&chessboard[i][j+1]==2&&chessboard[i][j+2]==2&&chessboard[i][j+3]==0) humresults[i][j]+=100;
        if(chessboard[i-2][j-2]==0&&chessboard[i-1][j-1]==2&&chessboard[i+1][j+1]==2&&chessboard[i+2][j+2]==2&&chessboard[i+3][j+3]==0) humresults[i][j]+=100;
        if(chessboard[i-2][j]==0&&chessboard[i-1][j]==2&&chessboard[i+1][j]==2&&chessboard[i+2][j]==2&&chessboard[i+3][j]==0) humresults[i][j]+=100;
        if(chessboard[i-2][j+2]==0&&chessboard[i-1][j+1]==2&&chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==2&&chessboard[i+3][j-3]==0) humresults[i][j]+=100;
        
        
        
        
        
        //第三种情况     *ooo@x 
        
        if(chessboard[i][j+1]==0&&chessboard[i][j-1]==1&&chessboard[i][j-2]==1&&chessboard[i][j-3]==1&&chessboard[i][j-4]==2) comresults[i][j]+=11;
        if(chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==1&&chessboard[i-4][j-4]==2) comresults[i][j]+=11;
        if(chessboard[i+1][j]==0&&chessboard[i-1][j]==1&&chessboard[i-2][j]==1&&chessboard[i-3][j]==1&&chessboard[i-4][j]==2) comresults[i][j]+=11;
        if(chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==1&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==1&&chessboard[i-4][j+4]==2) comresults[i][j]+=11;
        if(chessboard[i][j-1]==0&&chessboard[i][j+1]==1&&chessboard[i][j+2]==1&&chessboard[i][j+3]==1&&chessboard[i][j+4]==2) comresults[i][j]+=11;
        if(chessboard[i-1][j-1]==0&&chessboard[i+1][j+1]==1&&chessboard[i+1][j+2]==1&&chessboard[i+3][j+3]==1&&chessboard[i+4][j+4]==2) comresults[i][j]+=11;
        if(chessboard[i-1][j]==0&&chessboard[i+1][j]==1&&chessboard[i+2][j]==1&&chessboard[i+3][j]==1&&chessboard[i+4][j]==2) comresults[i][j]+=11;
        if(chessboard[i-1][j+1]==0&&chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==1&&chessboard[i+4][j-4]==2) comresults[i][j]+=11;
        
        if(chessboard[i][j+1]==0&&chessboard[i][j-1]==2&&chessboard[i][j-2]==2&&chessboard[i][j-3]==2&&chessboard[i][j-4]==1) humresults[i][j]+=11;
        if(chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==2&&chessboard[i-3][j-3]==2&&chessboard[i-4][j-4]==1) humresults[i][j]+=11;
        if(chessboard[i+1][j]==0&&chessboard[i-1][j]==2&&chessboard[i-2][j]==2&&chessboard[i-3][j]==2&&chessboard[i-4][j]==1) humresults[i][j]+=11;
        if(chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==2&&chessboard[i-2][j+2]==2&&chessboard[i-3][j+3]==2&&chessboard[i-4][j+4]==1) humresults[i][j]+=11;
        if(chessboard[i][j-1]==0&&chessboard[i][j+1]==2&&chessboard[i][j+2]==2&&chessboard[i][j+3]==2&&chessboard[i][j+4]==1) humresults[i][j]+=11;
        if(chessboard[i-1][j-1]==0&&chessboard[i+1][j+1]==2&&chessboard[i+1][j+2]==2&&chessboard[i+3][j+3]==2&&chessboard[i+4][j+4]==1) humresults[i][j]+=11;
        if(chessboard[i-1][j]==0&&chessboard[i+1][j]==2&&chessboard[i+2][j]==2&&chessboard[i+3][j]==2&&chessboard[i+4][j]==1) humresults[i][j]+=11;
        if(chessboard[i-1][j+1]==0&&chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==2&&chessboard[i+3][j-3]==2&&chessboard[i+4][j-4]==1) humresults[i][j]+=11;
        
        //第四种情况   *oo@ox
        
        if(chessboard[i][j+2]==0&&chessboard[i][j+1]==1&&chessboard[i][j-1]==1&&chessboard[i][j-2]==1&&chessboard[i][j-3]==2) comresults[i][j]+=11;
        if(chessboard[i+2][j+2]==0&&chessboard[i+1][j+1]==1&&chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==2) comresults[i][j]+=11; 
        if(chessboard[i+2][j]==0&&chessboard[i+1][j]==1&&chessboard[i-1][j]==1&&chessboard[i-2][j]==1&&chessboard[i-3][j]==2) comresults[i][j]+=11;
        if(chessboard[i+2][j-2]==0&&chessboard[i+1][j-1]==1&&chessboard[i-1][j+1]==1&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==2) comresults[i][j]+=11;
        if(chessboard[i][j-2]==0&&chessboard[i][j-1]==1&&chessboard[i][j+1]==1&&chessboard[i][j+2]==1&&chessboard[i][j+3]==2) comresults[i][j]+=11;
        if(chessboard[i-2][j-2]==0&&chessboard[i-1][j-1]==1&&chessboard[i+1][j+1]==1&&chessboard[i+2][j+2]==1&&chessboard[i+3][j+3]==2) comresults[i][j]+=11;
        if(chessboard[i-2][j]==0&&chessboard[i-1][j]==1&&chessboard[i+1][j]==1&&chessboard[i+2][j]==1&&chessboard[i+3][j]==2) comresults[i][j]+=11;
        if(chessboard[i-2][j+2]==0&&chessboard[i-1][j+1]==1&&chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==2) comresults[i][j]+=11;
        
        if(chessboard[i][j+2]==0&&chessboard[i][j+1]==2&&chessboard[i][j-1]==2&&chessboard[i][j-2]==2&&chessboard[i][j-3]==1) humresults[i][j]+=11;
        if(chessboard[i+2][j+2]==0&&chessboard[i+1][j+1]==2&&chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==2&&chessboard[i-3][j-3]==1) humresults[i][j]+=11; 
        if(chessboard[i+2][j]==0&&chessboard[i+1][j]==2&&chessboard[i-1][j]==2&&chessboard[i-2][j]==2&&chessboard[i-3][j]==1) humresults[i][j]+=11;
        if(chessboard[i+2][j-2]==0&&chessboard[i+1][j-1]==2&&chessboard[i-1][j+1]==2&&chessboard[i-2][j+2]==2&&chessboard[i-3][j+3]==1) humresults[i][j]+=11;
        if(chessboard[i][j-2]==0&&chessboard[i][j-1]==2&&chessboard[i][j+1]==2&&chessboard[i][j+2]==2&&chessboard[i][j+3]==1) humresults[i][j]+=11;
        if(chessboard[i-2][j-2]==0&&chessboard[i-1][j-1]==2&&chessboard[i+1][j+1]==2&&chessboard[i+2][j+2]==2&&chessboard[i+3][j+3]==1) humresults[i][j]+=11;
        if(chessboard[i-2][j]==0&&chessboard[i-1][j]==2&&chessboard[i+1][j]==2&&chessboard[i+2][j]==2&&chessboard[i+3][j]==1) humresults[i][j]+=11;
        if(chessboard[i-2][j+2]==0&&chessboard[i-1][j+1]==2&&chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==2&&chessboard[i+3][j-3]==1) humresults[i][j]+=11;
        
        //第五种情况   *o@oox
        
        if(chessboard[i][j+2]==2&&chessboard[i][j+1]==1&&chessboard[i][j-1]==1&&chessboard[i][j-2]==1&&chessboard[i][j-3]==0) comresults[i][j]+=11;
        if(chessboard[i+2][j+2]==2&&chessboard[i+1][j+1]==1&&chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==0) comresults[i][j]+=11; 
        if(chessboard[i+2][j]==2&&chessboard[i+1][j]==1&&chessboard[i-1][j]==1&&chessboard[i-2][j]==1&&chessboard[i-3][j]==0) comresults[i][j]+=11;
        if(chessboard[i+2][j-2]==2&&chessboard[i+1][j-1]==1&&chessboard[i-1][j+1]==1&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==0) comresults[i][j]+=11;
        if(chessboard[i][j-2]==2&&chessboard[i][j-1]==1&&chessboard[i][j+1]==1&&chessboard[i][j+2]==1&&chessboard[i][j+3]==0) comresults[i][j]+=11;
        if(chessboard[i-2][j-2]==2&&chessboard[i-1][j-1]==1&&chessboard[i+1][j+1]==1&&chessboard[i+2][j+2]==1&&chessboard[i+3][j+3]==0) comresults[i][j]+=11;
        if(chessboard[i-2][j]==2&&chessboard[i-1][j]==1&&chessboard[i+1][j]==1&&chessboard[i+2][j]==1&&chessboard[i+3][j]==0) comresults[i][j]+=11;
        if(chessboard[i-2][j+2]==2&&chessboard[i-1][j+1]==1&&chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==0) comresults[i][j]+=11;
        
        if(chessboard[i][j+2]==1&&chessboard[i][j+1]==2&&chessboard[i][j-1]==2&&chessboard[i][j-2]==2&&chessboard[i][j-3]==0) humresults[i][j]+=11;
        if(chessboard[i+2][j+2]==1&&chessboard[i+1][j+1]==2&&chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==2&&chessboard[i-3][j-3]==0) humresults[i][j]+=11; 
        if(chessboard[i+2][j]==1&&chessboard[i+1][j]==2&&chessboard[i-1][j]==2&&chessboard[i-2][j]==2&&chessboard[i-3][j]==0) humresults[i][j]+=11;
        if(chessboard[i+2][j-2]==1&&chessboard[i+1][j-1]==2&&chessboard[i-1][j+1]==2&&chessboard[i-2][j+2]==2&&chessboard[i-3][j+3]==0) humresults[i][j]+=11;
        if(chessboard[i][j-2]==1&&chessboard[i][j-1]==2&&chessboard[i][j+1]==2&&chessboard[i][j+2]==2&&chessboard[i][j+3]==0) humresults[i][j]+=11;
        if(chessboard[i-2][j-2]==1&&chessboard[i-1][j-1]==2&&chessboard[i+1][j+1]==2&&chessboard[i+2][j+2]==2&&chessboard[i+3][j+3]==0) humresults[i][j]+=11;
        if(chessboard[i-2][j]==1&&chessboard[i-1][j]==2&&chessboard[i+1][j]==2&&chessboard[i+2][j]==2&&chessboard[i+3][j]==0) humresults[i][j]+=11;
        if(chessboard[i-2][j+2]==1&&chessboard[i-1][j+1]==2&&chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==2&&chessboard[i+3][j-3]==0) humresults[i][j]+=11;
        
        //第六种情况  *@ooox
        
        if(chessboard[i][j+1]==2&&chessboard[i][j-1]==1&&chessboard[i][j-2]==1&&chessboard[i][j-3]==1&&chessboard[i][j-4]==0) comresults[i][j]+=11;
        if(chessboard[i+1][j+1]==2&&chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==1&&chessboard[i-4][j-4]==0) comresults[i][j]+=11;
        if(chessboard[i+1][j]==2&&chessboard[i-1][j]==1&&chessboard[i-2][j]==1&&chessboard[i-3][j]==1&&chessboard[i-4][j]==0) comresults[i][j]+=11;
        if(chessboard[i+1][j-1]==2&&chessboard[i-1][j+1]==1&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==1&&chessboard[i-4][j+4]==0) comresults[i][j]+=11;
        if(chessboard[i][j-1]==2&&chessboard[i][j+1]==1&&chessboard[i][j+2]==1&&chessboard[i][j+3]==1&&chessboard[i][j+4]==0) comresults[i][j]+=11;
        if(chessboard[i-1][j-1]==2&&chessboard[i+1][j+1]==1&&chessboard[i+1][j+2]==1&&chessboard[i+3][j+3]==1&&chessboard[i+4][j+4]==0) comresults[i][j]+=11;
        if(chessboard[i-1][j]==2&&chessboard[i+1][j]==1&&chessboard[i+2][j]==1&&chessboard[i+3][j]==1&&chessboard[i+4][j]==0) comresults[i][j]+=11;
        if(chessboard[i-1][j+1]==2&&chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==1&&chessboard[i+4][j-4]==0) comresults[i][j]+=11;
        
        if(chessboard[i][j+1]==1&&chessboard[i][j-1]==2&&chessboard[i][j-2]==2&&chessboard[i][j-3]==2&&chessboard[i][j-4]==0) humresults[i][j]+=11;
        if(chessboard[i+1][j+1]==1&&chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==2&&chessboard[i-3][j-3]==2&&chessboard[i-4][j-4]==0) humresults[i][j]+=11;
        if(chessboard[i+1][j]==1&&chessboard[i-1][j]==2&&chessboard[i-2][j]==2&&chessboard[i-3][j]==2&&chessboard[i-4][j]==0) humresults[i][j]+=11;
        if(chessboard[i+1][j-1]==1&&chessboard[i-1][j+1]==2&&chessboard[i-2][j+2]==2&&chessboard[i-3][j+3]==2&&chessboard[i-4][j+4]==0) humresults[i][j]+=11;
        if(chessboard[i][j-1]==1&&chessboard[i][j+1]==2&&chessboard[i][j+2]==2&&chessboard[i][j+3]==2&&chessboard[i][j+4]==0) humresults[i][j]+=11;
        if(chessboard[i-1][j-1]==1&&chessboard[i+1][j+1]==2&&chessboard[i+1][j+2]==2&&chessboard[i+3][j+3]==2&&chessboard[i+4][j+4]==0) humresults[i][j]+=11;
        if(chessboard[i-1][j]==1&&chessboard[i+1][j]==2&&chessboard[i+2][j]==2&&chessboard[i+3][j]==2&&chessboard[i+4][j]==0) humresults[i][j]+=11;
        if(chessboard[i-1][j+1]==1&&chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==2&&chessboard[i+3][j-3]==2&&chessboard[i+4][j-4]==0) humresults[i][j]+=11;  
        
        //第七种情况   @xooo*  
        
        if(chessboard[i][j+1]==0&&chessboard[i][j+2]==1&&chessboard[i][j+3]==1&&chessboard[i][j+4]==1) comresults[i][j]+=9;
        if(chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==1&&chessboard[i+3][j+3]==1&&chessboard[i+4][j+4]==1) comresults[i][j]+=9;
        if(chessboard[i+1][j]==0&&chessboard[i+2][j]==1&&chessboard[i+3][j]==1&&chessboard[i+4][j]==1) comresults[i][j]+=9;
        if(chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==1&&chessboard[i+4][j-4]==1) comresults[i][j]+=9;
        if(chessboard[i][j-1]==0&&chessboard[i][j-2]==1&&chessboard[i][j-3]==1&&chessboard[i][j-4]==1) comresults[i][j]+=9;
        if(chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==1&&chessboard[i-4][j-4]==1) comresults[i][j]+=9;
        if(chessboard[i-1][j]==0&&chessboard[i-2][j]==1&&chessboard[i-3][j]==1&&chessboard[i-4][j]==1) comresults[i][j]+=9;
        if(chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==1&&chessboard[i-4][j+4]==1) comresults[i][j]+=9;
        
        if(chessboard[i][j+1]==0&&chessboard[i][j+2]==2&&chessboard[i][j+3]==2&&chessboard[i][j+4]==2) humresults[i][j]+=9;
        if(chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==2&&chessboard[i+3][j+3]==2&&chessboard[i+4][j+4]==2) humresults[i][j]+=9;
        if(chessboard[i+1][j]==0&&chessboard[i+2][j]==2&&chessboard[i+3][j]==2&&chessboard[i+4][j]==2) humresults[i][j]+=9;
        if(chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==2&&chessboard[i+3][j-3]==2&&chessboard[i+4][j-4]==2) humresults[i][j]+=9;
        if(chessboard[i][j-1]==0&&chessboard[i][j-2]==2&&chessboard[i][j-3]==2&&chessboard[i][j-4]==2) humresults[i][j]+=9;
        if(chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==2&&chessboard[i-3][j-3]==2&&chessboard[i-4][j-4]==2) humresults[i][j]+=9;
        if(chessboard[i-1][j]==0&&chessboard[i-2][j]==2&&chessboard[i-3][j]==2&&chessboard[i-4][j]==2) humresults[i][j]+=9;
        if(chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==2&&chessboard[i-3][j+3]==2&&chessboard[i-4][j+4]==2) humresults[i][j]+=9;
        
        //第八种情况  ox@oo*     *是为了不和活三重复加分 
        
        if(chessboard[i][j+3]==2&&chessboard[i][j+2]==1&&chessboard[i][j+1]==1&&chessboard[i][j-1]==0&&chessboard[i][j-2]==1) comresults[i][j]+=9;
        if(chessboard[i+3][j+3]==2&&chessboard[i+2][j+2]==1&&chessboard[i+1][j+1]==1&&chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==1) comresults[i][j]+=9; 
        if(chessboard[i+3][j]==2&&chessboard[i+2][j]==1&&chessboard[i+1][j]==1&&chessboard[i-1][j]==0&&chessboard[i-2][j]==1) comresults[i][j]+=9;
        if(chessboard[i+3][j-3]==2&&chessboard[i+2][j-2]==1&&chessboard[i+1][j-1]==1&&chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==1) comresults[i][j]+=9;
        if(chessboard[i][j-3]==2&&chessboard[i][j-2]==1&&chessboard[i][j-1]==1&&chessboard[i][j+1]==0&&chessboard[i][j+2]==1) comresults[i][j]+=9;
        if(chessboard[i-3][j-3]==2&&chessboard[i-2][j-2]==1&&chessboard[i-1][j-1]==1&&chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==12) comresults[i][j]+=9;
        if(chessboard[i-3][j]==2&&chessboard[i-2][j]==1&&chessboard[i-1][j]==1&&chessboard[i+1][j]==0&&chessboard[i+2][j]==1) comresults[i][j]+=9;
        if(chessboard[i-3][j+3]==2&&chessboard[i-2][j+2]==1&&chessboard[i-1][j+1]==1&&chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==1) comresults[i][j]+=9;
        
        if(chessboard[i][j+3]==1&&chessboard[i][j+2]==2&&chessboard[i][j+1]==2&&chessboard[i][j-1]==0&&chessboard[i][j-2]==2) humresults[i][j]+=9;
        if(chessboard[i+3][j+3]==1&&chessboard[i+2][j+2]==2&&chessboard[i+1][j+1]==2&&chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==2) humresults[i][j]+=9; 
        if(chessboard[i+3][j]==1&&chessboard[i+2][j]==2&&chessboard[i+1][j]==2&&chessboard[i-1][j]==0&&chessboard[i-2][j]==2) humresults[i][j]+=9;
        if(chessboard[i+3][j-3]==1&&chessboard[i+2][j-2]==2&&chessboard[i+1][j-1]==2&&chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==2) humresults[i][j]+=9;
        if(chessboard[i][j-3]==1&&chessboard[i][j-2]==2&&chessboard[i][j-1]==2&&chessboard[i][j+1]==0&&chessboard[i][j+2]==2) humresults[i][j]+=9;
        if(chessboard[i-3][j-3]==1&&chessboard[i-2][j-2]==2&&chessboard[i-1][j-1]==2&&chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==2) humresults[i][j]+=9;
        if(chessboard[i-3][j]==1&&chessboard[i-2][j]==2&&chessboard[i-1][j]==2&&chessboard[i+1][j]==0&&chessboard[i+2][j]==2) humresults[i][j]+=9;
        if(chessboard[i-3][j+3]==1&&chessboard[i-2][j+2]==2&&chessboard[i-1][j+1]==2&&chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==2) humresults[i][j]+=9; 
        
        //第九种情况   oxo@o*
        
        if(chessboard[i][j+2]==2&&chessboard[i][j+1]==1&&chessboard[i][j-1]==1&&chessboard[i][j-2]==0&&chessboard[i][j-3]==1) comresults[i][j]+=9;
        if(chessboard[i+2][j+2]==2&&chessboard[i+1][j+1]==1&&chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==0&&chessboard[i-3][j-3]==1) comresults[i][j]+=9;
        if(chessboard[i+2][j]==2&&chessboard[i+1][j]==1&&chessboard[i-1][j]==1&&chessboard[i-2][j]==0&&chessboard[i-3][j]==1) comresults[i][j]+=9;
        if(chessboard[i+2][j-2]==2&&chessboard[i+1][j-1]==1&&chessboard[i-1][j+1]==1&&chessboard[i-2][j+2]==0&&chessboard[i-3][j+3]==1) comresults[i][j]+=9;
        if(chessboard[i][j-2]==2&&chessboard[i][j-1]==1&&chessboard[i][j+1]==1&&chessboard[i][j+2]==0&&chessboard[i][j+3]==1) comresults[i][j]+=9;
        if(chessboard[i-2][j-2]==2&&chessboard[i-1][j-1]==1&&chessboard[i+1][j+1]==1&&chessboard[i+2][j+2]==0&&chessboard[i+3][j+3]==1) comresults[i][j]+=9;
        if(chessboard[i-2][j]==2&&chessboard[i-1][j]==1&&chessboard[i+1][j]==1&&chessboard[i+2][j]==0&&chessboard[i+3][j]==1) comresults[i][j]+=9;
        if(chessboard[i-2][j+2]==2&&chessboard[i-1][j+1]==1&&chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==0&&chessboard[i+3][j-3]==1) comresults[i][j]+=9;
        
        if(chessboard[i][j+2]==2&&chessboard[i][j+1]==2&&chessboard[i][j-1]==2&&chessboard[i][j-2]==0&&chessboard[i][j-3]==2) humresults[i][j]+=9;
        if(chessboard[i+2][j+2]==2&&chessboard[i+1][j+1]==2&&chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==0&&chessboard[i-3][j-3]==2) humresults[i][j]+=9;
        if(chessboard[i+2][j]==2&&chessboard[i+1][j]==2&&chessboard[i-1][j]==2&&chessboard[i-2][j]==0&&chessboard[i-3][j]==2) humresults[i][j]+=9;
        if(chessboard[i+2][j-2]==2&&chessboard[i+1][j-1]==2&&chessboard[i-1][j+1]==2&&chessboard[i-2][j+2]==0&&chessboard[i-3][j+3]==2) humresults[i][j]+=9;
        if(chessboard[i][j-2]==2&&chessboard[i][j-1]==2&&chessboard[i][j+1]==2&&chessboard[i][j+2]==0&&chessboard[i][j+3]==2) humresults[i][j]+=9;
        if(chessboard[i-2][j-2]==2&&chessboard[i-1][j-1]==2&&chessboard[i+1][j+1]==2&&chessboard[i+2][j+2]==0&&chessboard[i+3][j+3]==2) humresults[i][j]+=9;
        if(chessboard[i-2][j]==2&&chessboard[i-1][j]==2&&chessboard[i+1][j]==2&&chessboard[i+2][j]==0&&chessboard[i+3][j]==2) humresults[i][j]+=9;
        if(chessboard[i-2][j+2]==2&&chessboard[i-1][j+1]==2&&chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==0&&chessboard[i+3][j-3]==2) humresults[i][j]+=9; 
        
        //第十种情况   oxoo@* 
        
        if(chessboard[i][j+1]==2&&chessboard[i][j-1]==1&&chessboard[i][j-2]==1&&chessboard[i][j-3]==0&&chessboard[i][j-4]==1) comresults[i][j]+=9;
        if(chessboard[i+1][j+1]==2&&chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==0&&chessboard[i-4][j-4]==1) comresults[i][j]+=9;
        if(chessboard[i+1][j]==2&&chessboard[i-1][j]==1&&chessboard[i-2][j]==1&&chessboard[i-3][j]==0&&chessboard[i-4][j]==1) comresults[i][j]+=9;
        if(chessboard[i+1][j-1]==2&&chessboard[i-1][j+1]==1&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==0&&chessboard[i-4][j+4]==1) comresults[i][j]+=9;
        if(chessboard[i][j-1]==2&&chessboard[i][j+1]==1&&chessboard[i][j+2]==1&&chessboard[i][j+3]==0&&chessboard[i][j+4]==1) comresults[i][j]+=9;
        if(chessboard[i-1][j-1]==2&&chessboard[i+1][j+1]==1&&chessboard[i+1][j+2]==1&&chessboard[i+3][j+3]==0&&chessboard[i+4][j+4]==1) comresults[i][j]+=9;
        if(chessboard[i-1][j]==2&&chessboard[i+1][j]==1&&chessboard[i+2][j]==1&&chessboard[i+3][j]==0&&chessboard[i+4][j]==1) comresults[i][j]+=9;
        if(chessboard[i-1][j+1]==2&&chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==0&&chessboard[i+4][j-4]==1) comresults[i][j]+=9;
        
        if(chessboard[i][j+1]==1&&chessboard[i][j-1]==2&&chessboard[i][j-2]==2&&chessboard[i][j-3]==0&&chessboard[i][j-4]==2) humresults[i][j]+=9;
        if(chessboard[i+1][j+1]==1&&chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==2&&chessboard[i-3][j-3]==0&&chessboard[i-4][j-4]==2) humresults[i][j]+=9;
        if(chessboard[i+1][j]==1&&chessboard[i-1][j]==2&&chessboard[i-2][j]==2&&chessboard[i-3][j]==0&&chessboard[i-4][j]==2) humresults[i][j]+=9;
        if(chessboard[i+1][j-1]==1&&chessboard[i-1][j+1]==2&&chessboard[i-2][j+2]==2&&chessboard[i-3][j+3]==0&&chessboard[i-4][j+4]==2) humresults[i][j]+=9;
        if(chessboard[i][j-1]==1&&chessboard[i][j+1]==2&&chessboard[i][j+2]==2&&chessboard[i][j+3]==0&&chessboard[i][j+4]==2) humresults[i][j]+=9;
        if(chessboard[i-1][j-1]==1&&chessboard[i+1][j+1]==2&&chessboard[i+1][j+2]==2&&chessboard[i+3][j+3]==0&&chessboard[i+4][j+4]==2) humresults[i][j]+=9;
        if(chessboard[i-1][j]==1&&chessboard[i+1][j]==2&&chessboard[i+2][j]==2&&chessboard[i+3][j]==0&&chessboard[i+4][j]==2) humresults[i][j]+=9;
        if(chessboard[i-1][j+1]==1&&chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==2&&chessboard[i+3][j-3]==0&&chessboard[i+4][j-4]==2) humresults[i][j]+=9; 
        
        //第十一种情况   @oxoo
        
        if(chessboard[i][j+1]==1&&chessboard[i][j+2]==0&&chessboard[i][j+3]==1&&chessboard[i][j+4]==1) comresults[i][j]+=9;
        if(chessboard[i+1][j+1]==1&&chessboard[i+2][j+2]==0&&chessboard[i+3][j+3]==1&&chessboard[i+4][j+4]==1) comresults[i][j]+=9;
        if(chessboard[i+1][j]==1&&chessboard[i+2][j]==0&&chessboard[i+3][j]==1&&chessboard[i+4][j]==1) comresults[i][j]+=9;
        if(chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==0&&chessboard[i+3][j-3]==1&&chessboard[i+4][j-4]==1) comresults[i][j]+=9;
        if(chessboard[i][j-1]==1&&chessboard[i][j-2]==0&&chessboard[i][j-3]==1&&chessboard[i][j-4]==1) comresults[i][j]+=9;
        if(chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==0&&chessboard[i-3][j-3]==1&&chessboard[i-4][j-4]==1) comresults[i][j]+=9;
        if(chessboard[i-1][j]==1&&chessboard[i-2][j]==0&&chessboard[i-3][j]==1&&chessboard[i-4][j]==1) comresults[i][j]+=9;
        if(chessboard[i-1][j+1]==1&&chessboard[i-2][j+2]==0&&chessboard[i-3][j+3]==1&&chessboard[i-4][j+4]==1) comresults[i][j]+=9;
        
        if(chessboard[i][j+1]==2&&chessboard[i][j+2]==0&&chessboard[i][j+3]==2&&chessboard[i][j+4]==2) humresults[i][j]+=9;
        if(chessboard[i+1][j+1]==2&&chessboard[i+2][j+2]==0&&chessboard[i+3][j+3]==2&&chessboard[i+4][j+4]==2) humresults[i][j]+=9;
        if(chessboard[i+1][j]==2&&chessboard[i+2][j]==0&&chessboard[i+3][j]==2&&chessboard[i+4][j]==2) humresults[i][j]+=9;
        if(chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==0&&chessboard[i+3][j-3]==2&&chessboard[i+4][j-4]==2) humresults[i][j]+=9;
        if(chessboard[i][j-1]==2&&chessboard[i][j-2]==0&&chessboard[i][j-3]==2&&chessboard[i][j-4]==2) humresults[i][j]+=9;
        if(chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==0&&chessboard[i-3][j-3]==2&&chessboard[i-4][j-4]==2) humresults[i][j]+=9;
        if(chessboard[i-1][j]==2&&chessboard[i-2][j]==0&&chessboard[i-3][j]==2&&chessboard[i-4][j]==2) humresults[i][j]+=9;
        if(chessboard[i-1][j+1]==2&&chessboard[i-2][j+2]==0&&chessboard[i-3][j+3]==2&&chessboard[i-4][j+4]==2) humresults[i][j]+=9;
        
        //第十二种情况   o@xoo 
        
        if(chessboard[i][j-1]==1&&chessboard[i][j+1]==0&&chessboard[i][j+2]==0&&chessboard[i][j+3]==1) comresults[i][j]+=9;
        if(chessboard[i-1][j-1]==1&&chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==0&&chessboard[i+3][j+3]==1) comresults[i][j]+=9;
        if(chessboard[i-1][j]==1&&chessboard[i+1][j]==0&&chessboard[i+2][j]==0&&chessboard[i+3][j]==1) comresults[i][j]+=9;
        if(chessboard[i-1][j+1]==1&&chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==0&&chessboard[i+3][j-3]==1) comresults[i][j]+=9;
        if(chessboard[i][j+1]==1&&chessboard[i][j-1]==0&&chessboard[i][j-2]==0&&chessboard[i][j-3]==1) comresults[i][j]+=9;
        if(chessboard[i+1][j+1]==1&&chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==0&&chessboard[i-3][j-3]==1) comresults[i][j]+=9;
        if(chessboard[i+1][j]==1&&chessboard[i-1][j]==0&&chessboard[i-2][j]==0&&chessboard[i-3][j]==1) comresults[i][j]+=9;
        if(chessboard[i+1][j-1]==1&&chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==0&&chessboard[i-3][j+3]==1) comresults[i][j]+=9;
        
        if(chessboard[i][j-1]==2&&chessboard[i][j+1]==0&&chessboard[i][j+2]==0&&chessboard[i][j+3]==1) humresults[i][j]+=9;
        if(chessboard[i-1][j-1]==2&&chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==0&&chessboard[i+3][j+3]==1) humresults[i][j]+=9;
        if(chessboard[i-1][j]==2&&chessboard[i+1][j]==0&&chessboard[i+2][j]==0&&chessboard[i+3][j]==1) humresults[i][j]+=9;
        if(chessboard[i-1][j+1]==2&&chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==0&&chessboard[i+3][j-3]==1) humresults[i][j]+=9;
        if(chessboard[i][j+1]==2&&chessboard[i][j-1]==0&&chessboard[i][j-2]==0&&chessboard[i][j-3]==1) humresults[i][j]+=9;
        if(chessboard[i+1][j+1]==2&&chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==0&&chessboard[i-3][j-3]==1) humresults[i][j]+=9;
        if(chessboard[i+1][j]==2&&chessboard[i-1][j]==0&&chessboard[i-2][j]==0&&chessboard[i-3][j]==1) humresults[i][j]+=9;
        if(chessboard[i+1][j-1]==2&&chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==0&&chessboard[i-3][j+3]==1) humresults[i][j]+=9;
        

	}
	void r_four(){
	    
	     for(int i=5;i<20;i++)
	         for(int j=5;j<20;j++){
	     		r_four(i,j);
	                 
	     } 
	     
	}
	     
	void r_three(int i, int j){
		if(chessboard[i][j]!=0) return;
		//第一种情况    x@oox
        
        if(chessboard[i][j-1]==0&&chessboard[i][j+1]==1&&chessboard[i][j+2]==1&&chessboard[i][j+3]==0) comresults[i][j]+=10;
        if(chessboard[i-1][j-1]==0&&chessboard[i+1][j+1]==1&&chessboard[i+2][j+2]==1&&chessboard[i+3][j+3]==0) comresults[i][j]+=10;
        if(chessboard[i-1][j]==0&&chessboard[i+1][j]==1&&chessboard[i+2][j]==1&&chessboard[i+3][j]==0) comresults[i][j]+=10;
        if(chessboard[i-1][j+1]==0&&chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==0) comresults[i][j]+=10;
        if(chessboard[i][j+1]==0&&chessboard[i][j-1]==1&&chessboard[i][j-2]==1&&chessboard[i][j-3]==0) comresults[i][j]+=10;
        if(chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==0) comresults[i][j]+=10;
        if(chessboard[i+1][j]==0&&chessboard[i-1][j]==1&&chessboard[i-2][j]==1&&chessboard[i-3][j]==0) comresults[i][j]+=10;
        if(chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==1&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==0) comresults[i][j]+=10;
        
        if(chessboard[i][j-1]==0&&chessboard[i][j+1]==2&&chessboard[i][j+2]==2&&chessboard[i][j+3]==0) humresults[i][j]+=10;
        if(chessboard[i-1][j-1]==0&&chessboard[i+1][j+1]==2&&chessboard[i+2][j+2]==2&&chessboard[i+3][j+3]==0) humresults[i][j]+=10;
        if(chessboard[i-1][j]==0&&chessboard[i+1][j]==2&&chessboard[i+2][j]==2&&chessboard[i+3][j]==0) humresults[i][j]+=10;
        if(chessboard[i-1][j+1]==0&&chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==2&&chessboard[i+3][j-3]==0) humresults[i][j]+=10;
        if(chessboard[i][j+1]==0&&chessboard[i][j-1]==2&&chessboard[i][j-2]==2&&chessboard[i][j-3]==0) humresults[i][j]+=10;
        if(chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==2&&chessboard[i-3][j-3]==0) humresults[i][j]+=10;
        if(chessboard[i+1][j]==0&&chessboard[i-1][j]==2&&chessboard[i-2][j]==2&&chessboard[i-3][j]==0) humresults[i][j]+=10;
        if(chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==2&&chessboard[i-2][j+2]==2&&chessboard[i-3][j+3]==0) humresults[i][j]+=10;
        
        
        //第二种情况   xo@ox
        if(chessboard[i][j+1]==1&&chessboard[i][j-1]==1&&chessboard[i][j-2]==0&&chessboard[i][j+2]==0) comresults[i][j]+=10; 
        if(chessboard[i-1][j]==1&&chessboard[i+1][j]==1&&chessboard[i-2][j]==0&&chessboard[i+2][j]==0) comresults[i][j]+=10;
        if(chessboard[i+1][j+1]==1&&chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==0&&chessboard[i+2][j+2]==0) comresults[i][j]+=10;
        if(chessboard[i-1][j+1]==1&&chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==0&&chessboard[i+2][j-2]==0) comresults[i][j]+=10;
        
        if(chessboard[i][j+1]==2&&chessboard[i][j-1]==2&&chessboard[i][j-2]==0&&chessboard[i][j+2]==0) humresults[i][j]+=10; 
        if(chessboard[i-1][j]==2&&chessboard[i+1][j]==2&&chessboard[i-2][j]==0&&chessboard[i+2][j]==0) humresults[i][j]+=10;
        if(chessboard[i+1][j+1]==2&&chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==0&&chessboard[i+2][j+2]==0) humresults[i][j]+=10;
        if(chessboard[i-1][j+1]==2&&chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==0&&chessboard[i+2][j-2]==0) humresults[i][j]+=10;
        
        //第三种情况  x@xoox 
        
        if(chessboard[i][j-1]==0&&chessboard[i][j+1]==0&&chessboard[i][j+2]==1&&chessboard[i][j+3]==1&&chessboard[i][j+4]==0) comresults[i][j]+=9;
        if(chessboard[i-1][j-1]==0&&chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==1&&chessboard[i+3][j+3]==1&&chessboard[i+4][j+4]==0) comresults[i][j]+=9;
        if(chessboard[i-1][j]==0&&chessboard[i+1][j]==0&&chessboard[i+2][j]==1&&chessboard[i+3][j]==1&&chessboard[i+4][j]==0) comresults[i][j]+=9;
        if(chessboard[i-1][j+1]==0&&chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==1&&chessboard[i+4][j-4]==0) comresults[i][j]+=9;
        if(chessboard[i][j+1]==0&&chessboard[i][j-1]==0&&chessboard[i][j-2]==1&&chessboard[i][j-3]==1&&chessboard[i][j-4]==0) comresults[i][j]+=9;
        if(chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==1&&chessboard[i-4][j-4]==0) comresults[i][j]+=9;
        if(chessboard[i+1][j]==0&&chessboard[i-1][j]==0&&chessboard[i-2][j]==1&&chessboard[i-3][j]==1&&chessboard[i-4][j]==0) comresults[i][j]+=9;
        if(chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==1&&chessboard[i-4][j+4]==0) comresults[i][j]+=9;
        
        if(chessboard[i][j-1]==0&&chessboard[i][j+1]==0&&chessboard[i][j+2]==2&&chessboard[i][j+3]==2&&chessboard[i][j+4]==0) humresults[i][j]+=9;
        if(chessboard[i-1][j-1]==0&&chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==2&&chessboard[i+3][j+3]==2&&chessboard[i+4][j+4]==0) humresults[i][j]+=9;
        if(chessboard[i-1][j]==0&&chessboard[i+1][j]==0&&chessboard[i+2][j]==2&&chessboard[i+3][j]==2&&chessboard[i+4][j]==0) humresults[i][j]+=9;
        if(chessboard[i-1][j+1]==0&&chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==2&&chessboard[i+3][j-3]==2&&chessboard[i+4][j-4]==0) humresults[i][j]+=9;
        if(chessboard[i][j+1]==0&&chessboard[i][j-1]==0&&chessboard[i][j-2]==2&&chessboard[i][j-3]==2&&chessboard[i][j-4]==0) humresults[i][j]+=9;
        if(chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==2&&chessboard[i-3][j-3]==2&&chessboard[i-4][j-4]==0) humresults[i][j]+=9;
        if(chessboard[i+1][j]==0&&chessboard[i-1][j]==0&&chessboard[i-2][j]==2&&chessboard[i-3][j]==2&&chessboard[i-4][j]==0) humresults[i][j]+=9;
        if(chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==2&&chessboard[i-3][j+3]==2&&chessboard[i-4][j+4]==0) humresults[i][j]+=9;
        
        //第四种情况    xox@ox
        
        if(chessboard[i][j+2]==0&&chessboard[i][j+1]==1&&chessboard[i][j-1]==0&&chessboard[i][j-2]==1&&chessboard[i][j-3]==0) comresults[i][j]+=9;
        if(chessboard[i+2][j+2]==0&&chessboard[i+1][j+1]==1&&chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==0) comresults[i][j]+=9;
        if(chessboard[i+2][j]==0&&chessboard[i+1][j]==1&&chessboard[i-1][j]==0&&chessboard[i-2][j]==1&&chessboard[i-3][j]==0) comresults[i][j]+=9;
        if(chessboard[i+2][j-2]==0&&chessboard[i+1][j-1]==1&&chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==0) comresults[i][j]+=9;
        if(chessboard[i][j-2]==0&&chessboard[i][j-1]==1&&chessboard[i][j+1]==0&&chessboard[i][j+2]==1&&chessboard[i][j+3]==0) comresults[i][j]+=9;
        if(chessboard[i-2][j-2]==0&&chessboard[i-1][j-1]==1&&chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==1&&chessboard[i+3][j+3]==0) comresults[i][j]+=9;
        if(chessboard[i-2][j]==0&&chessboard[i-1][j]==1&&chessboard[i+1][j]==0&&chessboard[i+2][j]==1&&chessboard[i+3][j]==0) comresults[i][j]+=9;
        if(chessboard[i-2][j+2]==0&&chessboard[i-1][j+1]==1&&chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==0) comresults[i][j]+=9;
        
        if(chessboard[i][j+2]==0&&chessboard[i][j+1]==2&&chessboard[i][j-1]==0&&chessboard[i][j-2]==2&&chessboard[i][j-3]==0) humresults[i][j]+=9;
        if(chessboard[i+2][j+2]==0&&chessboard[i+1][j+1]==2&&chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==2&&chessboard[i-3][j-3]==0) humresults[i][j]+=9;
        if(chessboard[i+2][j]==0&&chessboard[i+1][j]==2&&chessboard[i-1][j]==0&&chessboard[i-2][j]==2&&chessboard[i-3][j]==0) humresults[i][j]+=9;
        if(chessboard[i+2][j-2]==0&&chessboard[i+1][j-1]==2&&chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==2&&chessboard[i-3][j+3]==0) humresults[i][j]+=9;
        if(chessboard[i][j-2]==0&&chessboard[i][j-1]==2&&chessboard[i][j+1]==0&&chessboard[i][j+2]==2&&chessboard[i][j+3]==0) humresults[i][j]+=9;
        if(chessboard[i-2][j-2]==0&&chessboard[i-1][j-1]==2&&chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==2&&chessboard[i+3][j+3]==0) humresults[i][j]+=9;
        if(chessboard[i-2][j]==0&&chessboard[i-1][j]==2&&chessboard[i+1][j]==0&&chessboard[i+2][j]==2&&chessboard[i+3][j]==0) humresults[i][j]+=9;
        if(chessboard[i-2][j+2]==0&&chessboard[i-1][j+1]==2&&chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==2&&chessboard[i+3][j-3]==0) humresults[i][j]+=9;
        
        //第五种情况     xoxo@x
        
        if(chessboard[i][j+1]==0&&chessboard[i][j-1]==1&&chessboard[i][j-2]==0&&chessboard[i][j-3]==1&&chessboard[i][j-4]==0) comresults[i][j]+=9;
        if(chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==0&&chessboard[i-3][j-3]==1&&chessboard[i-4][j-4]==0) comresults[i][j]+=9;
        if(chessboard[i+1][j]==0&&chessboard[i-1][j]==1&&chessboard[i-2][j]==0&&chessboard[i-3][j]==1&&chessboard[i-4][j]==0) comresults[i][j]+=9; 
        if(chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==1&&chessboard[i-2][j+2]==0&&chessboard[i-3][j+3]==1&&chessboard[i-4][j+4]==0) comresults[i][j]+=9;
        if(chessboard[i][j-1]==0&&chessboard[i][j+1]==1&&chessboard[i][j+2]==0&&chessboard[i][j+3]==1&&chessboard[i][j+4]==0) comresults[i][j]+=9;
        if(chessboard[i-1][j-1]==0&&chessboard[i+1][j+1]==1&&chessboard[i+2][j+2]==0&&chessboard[i+3][j+3]==1&&chessboard[i+4][j+4]==0) comresults[i][j]+=9;
        if(chessboard[i-1][j]==0&&chessboard[i+1][j]==1&&chessboard[i+2][j]==0&&chessboard[i+3][j]==1&&chessboard[i+4][j]==0) comresults[i][j]+=9;
        if(chessboard[i-1][j+1]==0&&chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==0&&chessboard[i+3][j-3]==1&&chessboard[i+4][j-4]==0) comresults[i][j]+=9;
        
        if(chessboard[i][j+1]==0&&chessboard[i][j-1]==2&&chessboard[i][j-2]==0&&chessboard[i][j-3]==2&&chessboard[i][j-4]==0) humresults[i][j]+=9;
        if(chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==0&&chessboard[i-3][j-3]==2&&chessboard[i-4][j-4]==0) humresults[i][j]+=9;
        if(chessboard[i+1][j]==0&&chessboard[i-1][j]==2&&chessboard[i-2][j]==0&&chessboard[i-3][j]==2&&chessboard[i-4][j]==0) humresults[i][j]+=9; 
        if(chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==2&&chessboard[i-2][j+2]==0&&chessboard[i-3][j+3]==2&&chessboard[i-4][j+4]==0) humresults[i][j]+=9;
        if(chessboard[i][j-1]==0&&chessboard[i][j+1]==2&&chessboard[i][j+2]==0&&chessboard[i][j+3]==2&&chessboard[i][j+4]==0) humresults[i][j]+=9;
        if(chessboard[i-1][j-1]==0&&chessboard[i+1][j+1]==2&&chessboard[i+2][j+2]==0&&chessboard[i+3][j+3]==2&&chessboard[i+4][j+4]==0) humresults[i][j]+=9;
        if(chessboard[i-1][j]==0&&chessboard[i+1][j]==2&&chessboard[i+2][j]==0&&chessboard[i+3][j]==2&&chessboard[i+4][j]==0) humresults[i][j]+=9;
        if(chessboard[i-1][j+1]==0&&chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==0&&chessboard[i+3][j-3]==2&&chessboard[i+4][j-4]==0) humresults[i][j]+=9;
        
        //第六种情况    x@xoxox
        
        if(chessboard[i][j-1]==0&&chessboard[i][j+1]==0&&chessboard[i][j+2]==1&&chessboard[i][j+3]==0&&chessboard[i][j+4]==1&&chessboard[i][j+5]==0) comresults[i][j]+=8; 
        if(chessboard[i-1][j-1]==0&&chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==1&&chessboard[i+3][j+3]==0&&chessboard[i+4][j+4]==1&&chessboard[i+5][j+5]==0) comresults[i][j]+=8;
        if(chessboard[i-1][j]==0&&chessboard[i+1][j]==0&&chessboard[i+2][j]==1&&chessboard[i+3][j]==0&&chessboard[i+4][j]==1&&chessboard[i+5][j]==0) comresults[i][j]+=8;
        if(chessboard[i-1][j+1]==0&&chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==0&&chessboard[i+4][j-4]==1&&chessboard[i+5][j-5]==0) comresults[i][j]+=8;
        if(chessboard[i][j+1]==0&&chessboard[i][j-1]==0&&chessboard[i][j-2]==1&&chessboard[i][j-3]==0&&chessboard[i][j-4]==1&&chessboard[i][j-5]==0) comresults[i][j]+=8;
        if(chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==0&&chessboard[i-4][j-4]==1&&chessboard[i-5][j-5]==0) comresults[i][j]+=8;
        if(chessboard[i+1][j]==0&&chessboard[i-1][j]==0&&chessboard[i-2][j]==1&&chessboard[i-3][j]==0&&chessboard[i-4][j]==1&&chessboard[i-5][j]==0) comresults[i][j]+=8;
        if(chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==0&&chessboard[i-4][j+4]==1&&chessboard[i-5][j+5]==0) comresults[i][j]+=8;
        
        if(chessboard[i][j-1]==0&&chessboard[i][j+1]==0&&chessboard[i][j+2]==2&&chessboard[i][j+3]==0&&chessboard[i][j+4]==2&&chessboard[i][j+5]==0) humresults[i][j]+=8; 
        if(chessboard[i-1][j-1]==0&&chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==2&&chessboard[i+3][j+3]==0&&chessboard[i+4][j+4]==2&&chessboard[i+5][j+5]==0) humresults[i][j]+=8;
        if(chessboard[i-1][j]==0&&chessboard[i+1][j]==0&&chessboard[i+2][j]==2&&chessboard[i+3][j]==0&&chessboard[i+4][j]==2&&chessboard[i+5][j]==0) humresults[i][j]+=8;
        if(chessboard[i-1][j+1]==0&&chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==2&&chessboard[i+3][j-3]==0&&chessboard[i+4][j-4]==2&&chessboard[i+5][j-5]==0) humresults[i][j]+=8;
        if(chessboard[i][j+1]==0&&chessboard[i][j-1]==0&&chessboard[i][j-2]==2&&chessboard[i][j-3]==0&&chessboard[i][j-4]==2&&chessboard[i][j-5]==0) humresults[i][j]+=8;
        if(chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==2&&chessboard[i-3][j-3]==0&&chessboard[i-4][j-4]==2&&chessboard[i-5][j-5]==0) humresults[i][j]+=8;
        if(chessboard[i+1][j]==0&&chessboard[i-1][j]==0&&chessboard[i-2][j]==2&&chessboard[i-3][j]==0&&chessboard[i-4][j]==2&&chessboard[i-5][j]==0) humresults[i][j]+=8;
        if(chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==2&&chessboard[i-3][j+3]==0&&chessboard[i-4][j+4]==2&&chessboard[i-5][j+5]==0) humresults[i][j]+=8;
        
        //第八种情况   xox@xox
        
        //这个似乎不必写，先放着          
	}
	void r_three(){
	    
		 
	     for(int i=5;i<20;i++)
	         for(int j=5;j<20;j++){
	                
	                 r_three(i,j);
	                 
	                 }    
	     
	    
	}


	void r_two(int i, int j) {
		if(chessboard[i][j]!=0) return;
        //第一种情况  xx@oxx
        if(chessboard[i][j-2]==0&&chessboard[i][j-1]==0&&chessboard[i][j+1]==1&&chessboard[i][j+2]==0&&chessboard[i][j+3]==0) comresults[i][j]+=2;
        if(chessboard[i-2][j-2]==0&&chessboard[i-2][j-1]==0&&chessboard[i+1][j+1]==1&&chessboard[i+2][j+2]==0&&chessboard[i+3][j+3]==0) comresults[i][j]+=2;
        if(chessboard[i-2][j]==0&&chessboard[i-1][j]==0&&chessboard[i+1][j]==1&&chessboard[i+2][j]==0&&chessboard[i+3][j]==0) comresults[i][j]+=2;
        if(chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==0&&chessboard[i+1][j-1]==1&&chessboard[i+2][j-2]==0&&chessboard[i+3][j-3]==0) comresults[i][j]+=2;
        if(chessboard[i][j+2]==0&&chessboard[i][j+1]==0&&chessboard[i][j-1]==1&&chessboard[i][j-2]==0&&chessboard[i][j-3]==0) comresults[i][j]+=2;
        if(chessboard[i+2][j+2]==0&&chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==1&&chessboard[i-2][j-2]==0&&chessboard[i-3][j-3]==0) comresults[i][j]+=2;
        if(chessboard[i+2][j]==0&&chessboard[i+1][j]==0&&chessboard[i-1][j]==1&&chessboard[i-2][j]==0&&chessboard[i-3][j]==0) comresults[i][j]+=2;
        if(chessboard[i+2][j-2]==0&&chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==1&&chessboard[i-2][j+2]==0&&chessboard[i-3][j+3]==0) comresults[i][j]+=2;
        
        if(chessboard[i][j-2]==0&&chessboard[i][j-1]==0&&chessboard[i][j+1]==2&&chessboard[i][j+2]==0&&chessboard[i][j+3]==0) humresults[i][j]+=2;
        if(chessboard[i-2][j-2]==0&&chessboard[i-2][j-1]==0&&chessboard[i+1][j+1]==2&&chessboard[i+2][j+2]==0&&chessboard[i+3][j+3]==0) humresults[i][j]+=2;
        if(chessboard[i-2][j]==0&&chessboard[i-1][j]==0&&chessboard[i+1][j]==2&&chessboard[i+2][j]==0&&chessboard[i+3][j]==0) humresults[i][j]+=2;
        if(chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==0&&chessboard[i+1][j-1]==2&&chessboard[i+2][j-2]==0&&chessboard[i+3][j-3]==0) humresults[i][j]+=2;
        if(chessboard[i][j+2]==0&&chessboard[i][j+1]==0&&chessboard[i][j-1]==2&&chessboard[i][j-2]==0&&chessboard[i][j-3]==0) humresults[i][j]+=2;
        if(chessboard[i+2][j+2]==0&&chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==2&&chessboard[i-2][j-2]==0&&chessboard[i-3][j-3]==0) humresults[i][j]+=2;
        if(chessboard[i+2][j]==0&&chessboard[i+1][j]==0&&chessboard[i-1][j]==2&&chessboard[i-2][j]==0&&chessboard[i-3][j]==0) humresults[i][j]+=2;
        if(chessboard[i+2][j-2]==0&&chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==2&&chessboard[i-2][j+2]==0&&chessboard[i-3][j+3]==0) humresults[i][j]+=2; 
        
        //第二种情况   xx@xox 
        
        if(chessboard[i][j-2]==0&&chessboard[i][j-1]==0&&chessboard[i][j+1]==0&&chessboard[i][j+2]==1&&chessboard[i][j+3]==0) comresults[i][j]+=1;
        if(chessboard[i-2][j-2]==0&&chessboard[i-2][j-1]==0&&chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==1&&chessboard[i+3][j+3]==0) comresults[i][j]+=1;
        if(chessboard[i-2][j]==0&&chessboard[i-1][j]==0&&chessboard[i+1][j]==0&&chessboard[i+2][j]==1&&chessboard[i+3][j]==0) comresults[i][j]+=1;
        if(chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==0&&chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==0) comresults[i][j]+=1;
        if(chessboard[i][j+2]==0&&chessboard[i][j+1]==0&&chessboard[i][j-1]==0&&chessboard[i][j-2]==1&&chessboard[i][j-3]==0) comresults[i][j]+=1;
        if(chessboard[i+2][j+2]==0&&chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==0) comresults[i][j]+=1;
        if(chessboard[i+2][j]==0&&chessboard[i+1][j]==0&&chessboard[i-1][j]==0&&chessboard[i-2][j]==1&&chessboard[i-3][j]==0) comresults[i][j]+=1;
        if(chessboard[i+2][j-2]==0&&chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==0) comresults[i][j]+=1;
        
        if(chessboard[i][j-2]==0&&chessboard[i][j-1]==0&&chessboard[i][j+1]==0&&chessboard[i][j+2]==1&&chessboard[i][j+3]==0) humresults[i][j]+=1;
        if(chessboard[i-2][j-2]==0&&chessboard[i-2][j-1]==0&&chessboard[i+1][j+1]==0&&chessboard[i+2][j+2]==1&&chessboard[i+3][j+3]==0) humresults[i][j]+=1;
        if(chessboard[i-2][j]==0&&chessboard[i-1][j]==0&&chessboard[i+1][j]==0&&chessboard[i+2][j]==1&&chessboard[i+3][j]==0) humresults[i][j]+=1;
        if(chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==0&&chessboard[i+1][j-1]==0&&chessboard[i+2][j-2]==1&&chessboard[i+3][j-3]==0) humresults[i][j]+=1;
        if(chessboard[i][j+2]==0&&chessboard[i][j+1]==0&&chessboard[i][j-1]==0&&chessboard[i][j-2]==1&&chessboard[i][j-3]==0) humresults[i][j]+=1;
        if(chessboard[i+2][j+2]==0&&chessboard[i+1][j+1]==0&&chessboard[i-1][j-1]==0&&chessboard[i-2][j-2]==1&&chessboard[i-3][j-3]==0) humresults[i][j]+=1;
        if(chessboard[i+2][j]==0&&chessboard[i+1][j]==0&&chessboard[i-1][j]==0&&chessboard[i-2][j]==1&&chessboard[i-3][j]==0) humresults[i][j]+=1;
        if(chessboard[i+2][j-2]==0&&chessboard[i+1][j-1]==0&&chessboard[i-1][j+1]==0&&chessboard[i-2][j+2]==1&&chessboard[i-3][j+3]==0) humresults[i][j]+=1;
        
	}

	void r_two(){
		 
	     
	     for(int i=5;i<20;i++)
	            for(int j=5;j<20;j++){
	                    r_two(i,j);
	                    
	                    }
	     
	}
	     
}
