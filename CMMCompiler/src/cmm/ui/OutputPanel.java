package cmm.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import cmm.staticValues.Values;
import cmm.ui.components.Console;
import cmm.ui.components.WordOutput;

public class OutputPanel extends JPanel{
	private JTabbedPane jTabbedPane=new JTabbedPane();
	
	public WordOutput wordOutput=new WordOutput();
	public WordOutput gramParseOutput=new WordOutput();
	public Console console=new Console();
	public Console debug=new Console();
	private JScrollPane jScrollPane1=new JScrollPane(wordOutput);
	private JScrollPane jScrollPane2=new JScrollPane(console);
	private JScrollPane jScrollPane3=new JScrollPane(gramParseOutput);
	private JScrollPane jScrollPane4=new JScrollPane(debug);
	public OutputPanel(){
		//this.setBackground(Color.BLUE);
		this.setLayout(new BorderLayout());
		this.add(jTabbedPane,BorderLayout.CENTER);
		//jTabbedPane.setPreferredSize(new Dimension(Values.getMainFrameWidth()-200-20,200));
		//jScrollPane1.add();
		//为了最大化，加一个borderlayout
		JPanel jPanel1=new JPanel();
		jPanel1.setLayout(new BorderLayout());
		jPanel1.add(jScrollPane1,BorderLayout.CENTER);
		
		JPanel jPanel2=new JPanel();
		jPanel2.setLayout(new BorderLayout());
		jPanel2.add(jScrollPane2,BorderLayout.CENTER);
		
		JPanel jPanel3=new JPanel();
		jPanel3.setLayout(new BorderLayout());
		jPanel3.add(jScrollPane3,BorderLayout.CENTER);
		
		JPanel jPanel4=new JPanel();
		jPanel4.setLayout(new BorderLayout());
		jPanel4.add(jScrollPane4,BorderLayout.CENTER);
		
		jTabbedPane.addTab("词法分析",jPanel1);
		jTabbedPane.addTab("语法分析",jPanel3);
		jTabbedPane.addTab("Console",jPanel2);
		jTabbedPane.addTab("Debug",jPanel4);
		
	}
	
	public void setSize(){
		jTabbedPane.setPreferredSize(new Dimension(Values.getMainFrameWidth(),200));
	}
	
	public void wordOutput(String s){
		this.wordOutput.setText(s);
	}
	
	public void gramOutput(String s){
		this.gramParseOutput.setText(s);
	}
	
	public void consoleOutput(String s){
		this.console.setText(s);
	}
	
	public void DebugOutput(String s){
		this.debug.setText(s);
	}
	
	/**
	 * 选中某一个选项卡
	 * @param index
	 */
	public boolean select(int index){
		if(index<0||index>=jTabbedPane.getTabCount()) return false;
		else{
			jTabbedPane.setSelectedIndex(index);
			return true;
		}
	}
	
}
