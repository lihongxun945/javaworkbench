package cmm.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.TabableView;

import cmm.functions.Files;
import cmm.staticValues.Values;
import cmm.ui.components.MyButton;

public class TextPane extends JPanel{
	static JTabbedPane jTabbedPane=new JTabbedPane();
	static Vector<EditTextPanel> editTextPanelVector=new Vector<EditTextPanel>();
	private static int index=0;	//当前选定的文件
	private static int lastIndex=0;	//上次选中的文件
	
	public TextPane(){
		this.setLayout(new BorderLayout());
		this.add(jTabbedPane);
		jTabbedPane.addChangeListener(new   ChangeListener(){
		      public   void   stateChanged(ChangeEvent   e){
		        JTabbedPane   tabbedPane   =   (JTabbedPane)e.getSource();
		        lastIndex=index;
		        index  =   tabbedPane.getSelectedIndex();
		        Values.selectFile(index);
		       // System.out.println("select:"+index);
		      }
		}); 
		
	}
	
	/**
	 * 添加
	 */
	public static void add(File file){
		
		
		EditTextPanel editTextPanel=new EditTextPanel();
		editTextPanel.setText(Values.getFileContent());
		editTextPanelVector.add(editTextPanel);

		jTabbedPane.addTab(null,editTextPanel);
		lastIndex=index;
		index=jTabbedPane.getTabCount()-1;
		
		
		
		//构造关闭按钮
		JPanel panel=new JPanel();	
		panel.setOpaque(false);
		String fileName=null;
		if(file==null){
			fileName="新建文件";
		}else{
			fileName=file.getName();
		}
		JLabel label=new JLabel(fileName);
		MyButton closeTabButton=new MyButton();
		closeTabButton.setOpaque(false);
		panel.add(label);
		panel.add(closeTabButton);
		closeTabButton.addActionListener(new CloseTabButtonListener(panel));
		ImageIcon imageIcon1=new ImageIcon(ToolBarPanel.class.getResource("/images/closeTab.png"));
		ImageIcon imageIcon2=new ImageIcon(ToolBarPanel.class.getResource("/images/closeTab2.png"));
		closeTabButton.setContentAreaFilled(false);
		closeTabButton.setBorderPainted(false);
		closeTabButton.setEnterIcon(imageIcon1);
		closeTabButton.setExitIcon(imageIcon2);
		closeTabButton.setToolTipText("关闭选项卡");
		closeTabButton.setPreferredSize(new Dimension(16,16));
		jTabbedPane.setTabComponentAt(index, panel);
		
		//选中最新添加的那一个
		jTabbedPane.setSelectedIndex(index);
		//System.out.println("select:"+index);
	}
	
	/**
	 * 删除
	 */
	public static void delete(int index){
		editTextPanelVector.remove(index);
		jTabbedPane.remove(index);
		//System.out.println("remove:"+index);
		
	}
	//删除现在选中的；
	public static void deleteNow(){
		editTextPanelVector.remove(index);
		jTabbedPane.remove(index);
		//System.out.println("remove:"+index);
		
	}

	public static int getIndex() {
		return index;
	}
	public static int getLatsIndex(){
		return lastIndex;
	}
	
	/**
	 * 选中一个选项卡
	 */
	public static void selectTab(int index){
		jTabbedPane.setSelectedIndex(index);
	}
	/**
	 * 获得当前选中文件的内容
	 * @return
	 */
	public String getInputString(){
		return editTextPanelVector.get(index).getInputString();
	}
	
}
/**
	 * 关闭按钮
	 */
	class CloseTabButtonListener implements ActionListener{
		JPanel panel=null;
		public CloseTabButtonListener(JPanel panel){
			this.panel=panel;
		}
		public void actionPerformed(ActionEvent e) {
			int index=TextPane.jTabbedPane.indexOfTabComponent(panel);
			Values.deleteFile(index);
			MainFrame.textPane.delete(index);
		}
		
	}

	
	