package cmm.ui.components;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import cmm.functions.CMMToken;

/**
 * 代码自动补全
 * @author axun
 *
 */
public class Complement {
	private JFrame frame=new JFrame("自动补全");
	private List list=new List();
	private CMMToken[] tokens=new CMMToken[10];
	private int num=0;
	
	public Complement(){
		frame.setSize(100,200);
		frame.setAlwaysOnTop(true);
		JScrollPane jScrollPane=new JScrollPane(list);
		frame.setLayout(new BorderLayout());
		
		frame.add(list,BorderLayout.CENTER);
		
		frame.setVisible(true);
		frame.setLocation(300, 200);
		
	}
	
	public void print(){
		list.removeAll();

		for(int i=0;i<num;i++){
			if(tokens[i].getType()==3){
				//不能重复
				boolean repeat=false;
				for(int j=0;j<num;j++){
					if((j!=i)&&(tokens[j].getToken().equals(tokens[i].getToken()))) repeat=true;
				}
				if(repeat) continue;
				System.out.println("complement:"+tokens[i].getToken());
				list.add(tokens[i].getToken());
			}
		}
		frame.repaint();
	}
	
	public void setTokens(CMMToken[] tokens,int n){
		this.tokens=tokens;
		num=n;
		print();
	}

}
