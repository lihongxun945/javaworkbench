package userInterface.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;
import javax.swing.event.*;

public class Help extends JFrame{
	
	private int scWidth=Toolkit.getDefaultToolkit().getScreenSize().width;
	private int scHeight=Toolkit.getDefaultToolkit().getScreenSize().height;
	private Color brown = new Color(142,78,30);
	
	
	JPanel jpMain;
	
	JRadioButton jrbRules ,jrbAbout , jrbReturn;
	
	JLabel jlRules, jlAbout;
	
//	返回按钮
	JButton backToHall;
	
	public Help() {
		setBounds((scWidth-500)/2,(scHeight-500)/3,500,400);
		setLayout(null);
		setResizable(false);
		setIconImage(new ImageIcon("image/logo.png").getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
//		jpMain
		jpMain=new jpMainJPanel();
		jpMain.setBounds(0,0,500,400);
		jpMain.setLayout(null);
		
		
		jrbRules = new JRadioButton("游戏规则", new ImageIcon("image/rules.png"), true);
		jrbRules.setBounds(50, 300, 120, 50);
		jrbRules.setOpaque(false);
		jrbRules.setBorder(null);
		jrbRules.setContentAreaFilled(false);
		jrbRules.addActionListener(new RulesListener());
		
		jrbAbout = new JRadioButton ("关于",new ImageIcon("image/headImage/2small.png"),true);
		jrbAbout.setBounds(200, 300, 120, 50);
		jrbAbout.setOpaque(false);
		jrbAbout.setBorder(null);
		jrbAbout.setContentAreaFilled(false);
		jrbAbout.addActionListener(new AboutListener());
		
		jrbReturn = new JRadioButton("返回",new ImageIcon("image/return.png"),true);
		jrbReturn.setBounds(320, 300, 120, 50);
		jrbReturn.setOpaque(false);
		jrbReturn.setBorder(null);
		jrbReturn.setContentAreaFilled(false);
		jrbReturn.addActionListener(new ReturnListener());
		
		jlRules = new JLabel(new ImageIcon("image/helprules.jpg"));
		jlRules.setBounds(0, 0, 500, 400);
//		jlRules.setBorder(new LineBorder(Color.BLACK,3));
		
		
		
		jlAbout = new JLabel(new ImageIcon("image/helpabout.jpg"));
		jlAbout.setBounds(0, 0, 500, 400);
//		jlAbout.setBorder(new LineBorder(Color.BLUE,3));
		
		jpMain.add(jrbRules);
		jpMain.add(jrbAbout);
		jpMain.add(jrbReturn);
		jpMain.add(jlRules);
		jpMain.add(jlAbout);
		
		
		add(jpMain);
		
	
		
		setVisible(true);
	}
	
	class RulesListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			jlRules.setVisible(true);
			jlAbout.setVisible(false);
		}
	}
	
	class AboutListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			jlRules.setVisible(false);
			jlAbout.setVisible(true);
		}
	}
	
//	确定按钮监听  退出窗口
	class ReturnListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	}
	
	
	
	public static void main(String [] args){
		new Help();
	}
		
}

class jpMainJPanel extends JPanel {
		public jpMainJPanel (){
			super();
		}
		
//		public void paintComponent (Graphics g){
//			g.drawImage(new ImageIcon("image/helpbackground.jpg").getImage(), 0, 0, 521, 577, this);
//			setFont(new Font("fixedsys",14,17));			
//		}

}
