package cmm.ui;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cmm.functions.GramParse;
import cmm.ui.listeners.CloseFileListener;
import cmm.ui.listeners.CreateFileListener;
import cmm.ui.listeners.GramParseListener;
import cmm.ui.listeners.OpenFileListener;
import cmm.ui.listeners.SaveFileListener;
import cmm.ui.listeners.SyntaxParseListener;
import cmm.ui.listeners.WordAnalyseListener;

public class MenuBar extends JMenuBar{
	private JMenu menu1=new JMenu("文件");
	private JMenu menu2=new JMenu("编辑");
	private JMenu menu3=new JMenu("代码");
	private JMenu menu4=new JMenu("搜索");
	private JMenu menu5=new JMenu("运行");
	private JMenu menu6=new JMenu("窗口");
	private JMenu menu7=new JMenu("帮助");
	
	private JMenu menu1_1=new JMenu("新建");
	private JMenuItem menu1_1_1=new JMenuItem("txt文件");
	private JMenuItem menu1_2=new JMenuItem("打开文件");
	private JMenuItem menu1_3=new JMenuItem("保存");
	private JMenuItem menu1_4=new JMenuItem("另存为");
	private JMenuItem menu1_5=new JMenuItem("关闭文件");
	private JMenuItem menu1_6=new JMenuItem("退出程序");
	
	private JMenuItem menu5_1=new JMenuItem("词法分析");
	private JMenuItem menu5_2=new JMenuItem("语法分析");
	private JMenuItem menu5_3=new JMenuItem("语义分析");
	
	public MenuBar(){
		this.add(menu1);
		this.add(menu2);
		this.add(menu3);
		this.add(menu4);
		this.add(menu5);
		this.add(menu6);
		this.add(menu7);
		
		menu1.add(menu1_1);
		menu1_1.add(menu1_1_1);
		menu1.add(menu1_2);
		menu1.add(menu1_3);
		menu1.add(menu1_4);
		menu1.add(menu1_5);
		menu1.add(menu1_6);
		
		menu5.add(menu5_1);
		menu5.add(menu5_2);
		menu5.add(menu5_3);
		
		//添加按钮监听
		menu1_1_1.addActionListener(new CreateFileListener());
		menu1_2.addActionListener(new OpenFileListener());
		menu1_3.addActionListener(new SaveFileListener());
		menu1_5.addActionListener(new CloseFileListener());
		menu5_1.addActionListener(new WordAnalyseListener());
		menu5_2.addActionListener(new GramParseListener());
		menu5_3.addActionListener(new SyntaxParseListener());
		
	}
	
}
