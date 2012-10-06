package cmm.ui.components;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import cmm.functions.MyTable;

public class VarsTable {
	private JFrame frame=new JFrame("±‰¡ø±Ì");
	private MyTable table=new MyTable();
	private JScrollPane jScrollPane=null;
	public VarsTable(){
		jScrollPane = new JScrollPane(table.getTable());
		frame.setAlwaysOnTop(true);
		frame.add(jScrollPane);
		frame.pack();
		frame.setLocation(0, 0);
	}
	
	public void refresh(MyTable table){
		frame.remove(jScrollPane);
		this.table=table;
		jScrollPane = new JScrollPane(table.getTable());
		frame.add(jScrollPane);
		frame.pack();
	}
	public void setVisible(boolean b){
		frame.setVisible(true);
	}
}
