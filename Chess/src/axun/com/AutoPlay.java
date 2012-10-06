package axun.com;

public class AutoPlay{
    int x,y;
    int shape[][][]=new int[15][15][5];
    int chesspad[][]=new int[15][15];
    int color=1;
    int max_x=0;
    int max_y=0;
    int max=0;
    
    public void setAutoPlay(int[][] chess,int color){
    	for(int i=0;i<15;i++)
    		for(int j=0;j<15;j++){
    			chesspad[i][j]=chess[i][j];
    		}
    
    	this.color=color;
    }
    
    void autoPlay(int chesspad[][],int a,int b){
         int randomNumber=(int)(Math.random()*8)+1;
         switch(randomNumber){
            case(1):
                 if(a-1>0 && b-1>0&&chesspad[a-1][b-1]==0)
                 {x=a-1;y=b-1;break;}
                 else if(a-2>0 && b-2>0&&chesspad[a-2][b-2]==0)
                 {x=a-2;y=b-2;break;} 
                 else{}
                 
                  
            case(2):
                 if(a-1>0 &&chesspad[a-1][b]==0)
                 {x=a-1;y=b;break;}
                 else if(a-2>0&&chesspad[a-2][b]==0)
                 {x=a-2;y=b;break;}
                 else{}
            case(3):
                 if(a-1>0 && b+1<=15&&chesspad[a-1][b+1]==0)
                 {x=a-1;y=b+1;break;}
                 else if(a-2>0 &&b+2<=15&&chesspad[a-2][b+2]==0)
                 {x=a-2;y=b+2;break;}
                 else{}
            case(4):
                 if(b+1<=15&&chesspad[a][b+1]==0)
                 {x=a;y=b+1;break;}
                 else if( b+2<=15&&chesspad[a][b+2]==0 )
                 {x=a;y=b+2;break;}
                 else{}
            case(5):
                 if(a+1<=15 && b+1<=15&& chesspad[a+1][b+1]==0 )
                 {x=a+1;y=b+1;break;}
                 else if(a+2<=15 && b+2<=15 && chesspad[a+2][b+2]==0)
                 {x=a+2;y=b+2;break;} 
                 else{}
            case(6):
                 if(a+1<=15&&chesspad[a+1][b]==0)
                 {x=a+1;y=b;break;}
                 else if(a+2<=15&&chesspad[a+2][b]==0)
                 {x=a+2;y=b;break;}
                 else{}
            case(7):
                 if( a+1<=15 && b-1>0&&chesspad[a+1][b-1]==0)
                 {x=a+1;y=b-1;break;}
                 else if( a+2<=15 && b-2>0&&chesspad[a+2][b-2]==0)
                 {x=a+2;y=b-2;break;}
                 else{}
            case(8):
                 if(b-1>0&& chesspad[a][b-1]==0)
                 {x=a;y=b-1;break;}
                 else  if(b-2>0&& chesspad[a][b-2]==0)
                 {x=a;y=b-2;break;}
                 else{}
           }
     }
public Point think(){
	int max1=1;
	int max1_x=1;
	int max1_y=1;
	int max2=1;
	int max2_x=1;
	int max2_y=1;
	Point p=new Point(1,1);
	
	scan(1);
	evaluate();
	max1=max;
	max1_x=max_x;
	max1_y=max_y;
	
	scan(2);
	evaluate();
	max2=max;
	max2_x=max_x;
	max2_y=max_y;
	
	if(max1>=max2){
		p.x=max1_x;
		p.y=max1_y;
	}else{
		p.x=max2_x;
		p.y=max2_y;
	}
	
	return p;
}


public void evaluate(){
   	int i=1,j=1;
       
   	for(i=0;i<=14;i++)
   		for(j=0;j<=14;j++){
   			if(chesspad[i][j]!=0) continue;
   			switch(shape[i][j][0]) {
                   case 5:
                       shape[i][j][4]=200;
                       break;
                   case 4:
                       switch(shape[i][j][1]){
                        	case 4:
                            	shape[i][j][4]=150+shape[i][j][2]+shape[i][j][3];   
                              	break;    
                        	case 3:
                             	shape[i][j][4]=100+shape[i][j][2]+shape[i][j][3];
                            	break;
                        	default:
                              	shape[i][j][4]=50+shape[i][j][2]+shape[i][j][3];
                             
                        }
                        break;
                   case 3:
                     	switch(shape[i][j][1]){
                         	case 3:
                            	shape[i][j][4]=75+shape[i][j][2]+shape[i][j][3];
                                    break;              
                        	default:
                              	shape[i][j][4]=20+shape[i][j][2]+shape[i][j][3];
                          }
                          break; 
                  case 2:
                        shape[i][j][4]=10+shape[i][j][1]+shape[i][j][2]+shape[i][j][3];
                        break; 
                  case 1:
                        shape[i][j][4]=shape[i][j][0]+shape[i][j][1]+shape[i][j][2]+shape[i][j][3];
                  default : shape[i][j][4]=0;                      
                 }   
            }       
    
   	int x=1,y=1;
   	max=0;
   	for(x=0;x<=14;x++)
   		for(y=0;y<=14;y++)
   			if(max<shape[x][y][4]){   
   				max=shape[x][y][4];  
   				max_x=x;max_y=y;
   			}     
    }


void scan(int colour){//查看八方向上相邻同色棋子个数
	int i,j;
	
   for(i=1;i<=14;i++){
   	for(j=1;j<=14;j++){
   		if(chesspad[i][j]==0){
             	int m=i,n=j;
           	while(n-1>0&&chesspad[m][--n]==colour){//向上
                 	shape[i][j][0]++;
            	}
              	n=j;
            	while(n+1<=14&&chesspad[m][++n]==colour){//向下
                 	shape[i][j][0]++;
              	}          
            	n=j;
             	while(m-1>0&&chesspad[--m][n]==colour){//向左
                 	shape[i][j][1]++;
             	}
              	m=i;
              	while(m+1<=14&&chesspad[++m][n]==colour){//向右
              		shape[i][j][1]++;
             	}	
                	m=i;
              	while(m-1>0&&n+1<=14&&chesspad[--m][++n]==colour){//左下
                  	shape[i][j][2]++;
            	}
     		   	m=i;n=j; 
     		  	while(m+1<=14&&n-1>0&&chesspad[++m][--n]==colour){//右上
                  	shape[i][j][2]++;
              	}
     		          
             	m=i;n=j; 
                             
           	while(m-1>=0&&n-1>0&&chesspad[--m][--n]==colour){//左上
                 	shape[i][j][3]++;
            	}
     		   	m=i;n=j; 
     			while(m+1<=14&&n+1<=14&&chesspad[++m][++n]==colour){//右下
                 	shape[i][j][3]++;
             	}
   		} 
   	}
		}     
} 
}
