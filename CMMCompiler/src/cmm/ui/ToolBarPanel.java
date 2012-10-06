package cmm.ui;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cmm.ui.components.MyButton;
import cmm.ui.listeners.CloseFileListener;
import cmm.ui.listeners.CreateFileListener;
import cmm.ui.listeners.OpenFileListener;
import cmm.ui.listeners.SaveFileListener;
import cmm.ui.listeners.SyntaxParseListener;


public class ToolBarPanel extends JPanel{
	MyButton jButton0=new MyButton();
	MyButton jButton1=new MyButton();
	MyButton jButton2=new MyButton();
	MyButton jButton3=new MyButton();
	MyButton jButton4=new MyButton();
	MyButton jButton5=new MyButton();
	MyButton jButton6=new MyButton();
	MyButton jButton7=new MyButton();
	MyButton jButton8=new MyButton();
	MyButton jButton9=new MyButton();
	MyButton jButton10=new MyButton();
	MyButton jButton11=new MyButton();
	MyButton jButton12=new MyButton();
	MyButton jButton13=new MyButton();
	MyButton jButton14=new MyButton();
	
	public JLabel jLabel1=new JLabel("100%");	//为了方便修改
	
	public ToolBarPanel(){
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.add(jButton0);
		this.add(jButton1);
		this.add(jButton2);
		this.add(jButton3);
		//this.add(jButton4);
		//this.add(jLabel1);
		//this.add(jButton5);
		this.add(jButton6);
//		this.add(jButton7);
//		this.add(jButton8);
//		this.add(jButton9);
//		this.add(jButton10);
//		this.add(jButton11);
//		this.add(jButton12);
//		this.add(jButton13);
//		this.add(jButton14);
		
		jButton0.setPreferredSize(new Dimension(30,30));
		jButton1.setPreferredSize(new Dimension(30,30));
		jButton2.setPreferredSize(new Dimension(30,30));
		jButton3.setPreferredSize(new Dimension(30,30));
		jButton4.setPreferredSize(new Dimension(30,30));
		jButton5.setPreferredSize(new Dimension(30,30));
		jButton6.setPreferredSize(new Dimension(30,30));
		jButton7.setPreferredSize(new Dimension(30,30));
		
		
		
		//设置图片
		ImageIcon imageIcon00=new ImageIcon(ToolBarPanel.class.getResource("/images/createFile.png"));
		ImageIcon imageIcon10=new ImageIcon(ToolBarPanel.class.getResource("/images/open.png"));
		ImageIcon imageIcon20=new ImageIcon(ToolBarPanel.class.getResource("/images/save.png"));
		ImageIcon imageIcon30=new ImageIcon(ToolBarPanel.class.getResource("/images/close.png"));
		ImageIcon imageIcon40=new ImageIcon(ToolBarPanel.class.getResource("/images/fangda.jpg"));
		ImageIcon imageIcon50=new ImageIcon(ToolBarPanel.class.getResource("/images/suoxiao.jpg"));
		ImageIcon imageIcon60=new ImageIcon(ToolBarPanel.class.getResource("/images/run.png"));
		
		
		jButton0.setEnterIcon(imageIcon00);
		jButton1.setEnterIcon(imageIcon10);
		jButton2.setEnterIcon(imageIcon20);
		jButton3.setEnterIcon(imageIcon30);
		jButton4.setEnterIcon(imageIcon40);
		jButton5.setEnterIcon(imageIcon50);
		jButton6.setEnterIcon(imageIcon60);
		
		ImageIcon imageIcon01=new ImageIcon(ToolBarPanel.class.getResource("/images/createFile2.png"));
		ImageIcon imageIcon11=new ImageIcon(ToolBarPanel.class.getResource("/images/open2.png"));
		ImageIcon imageIcon21=new ImageIcon(ToolBarPanel.class.getResource("/images/save2.png"));
		ImageIcon imageIcon31=new ImageIcon(ToolBarPanel.class.getResource("/images/close2.png"));
		ImageIcon imageIcon41=new ImageIcon(ToolBarPanel.class.getResource("/images/fangda.jpg"));
		ImageIcon imageIcon51=new ImageIcon(ToolBarPanel.class.getResource("/images/suoxiao.jpg"));
		ImageIcon imageIcon61=new ImageIcon(ToolBarPanel.class.getResource("/images/run2.png"));
		
		
		jButton0.setExitIcon(imageIcon01);
		jButton1.setExitIcon(imageIcon11);
		jButton2.setExitIcon(imageIcon21);
		jButton3.setExitIcon(imageIcon31);
		jButton4.setExitIcon(imageIcon41);
		jButton5.setExitIcon(imageIcon51);
		jButton6.setExitIcon(imageIcon61);

		
		
		jButton0.setContentAreaFilled(false);
		jButton1.setContentAreaFilled(false);
		jButton2.setContentAreaFilled(false);
		jButton3.setContentAreaFilled(false);
		jButton4.setContentAreaFilled(false);
		jButton5.setContentAreaFilled(false);
		jButton6.setContentAreaFilled(false);
		
		jButton0.setBorderPainted(false);
		jButton1.setBorderPainted(false);
		jButton2.setBorderPainted(false);
		jButton3.setBorderPainted(false);
		jButton4.setBorderPainted(false);
		jButton5.setBorderPainted(false);
		jButton6.setBorderPainted(false);
		
		jButton0.setToolTipText("新建文件");
		jButton1.setToolTipText("打开文件");
		jButton2.setToolTipText("保存文件");
		jButton3.setToolTipText("关闭文件");
		jButton4.setToolTipText("放大字体");
		jButton5.setToolTipText("缩小字体");
		jButton6.setToolTipText("运行");
		
		
		//添加监听
		jButton0.addActionListener(new CreateFileListener());
		jButton1.addActionListener(new OpenFileListener());
		jButton2.addActionListener(new SaveFileListener());
		jButton3.addActionListener(new CloseFileListener());
		jButton6.addActionListener(new SyntaxParseListener());
		
	}
}
