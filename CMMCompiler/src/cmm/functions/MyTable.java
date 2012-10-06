package cmm.functions;

import java.util.Vector;

import javax.swing.JTable;


import sun.security.jgss.TokenTracker;

public class MyTable{
	private JTable jTable;
	private Vector<VarObject> vos=new Vector<VarObject>();
	private Object[][] data=new Object[100][7];
	private Object[] colNames= { "变量","类型","值","层","是数组","数组大小","数组元素"};
	private int p=-1;
	
	public MyTable(){
		
	}
	
	public void addRow(VarObject vo){
		vos.add(vo);
		p++;
		if(p>=100) return ;
		String [] data=new String[7];
		data[0]=vo.getVarString();
		data[1]=vo.getTypeString();
		data[2]=vo.getValueString();
		data[3]=""+vo.getLayer();
		data[4]=vo.isArray()?"true":"false";
		data[5]=""+vo.getArraySize();
		data[6]="";
		for(int i=0;i<vo.getArraySize();i++){
			data[6]+=vo.getArrayElement(i)+",";
		}
		
		this.data[p]=data;
	}
	
	
	public JTable getTable(){
		jTable=new JTable(data,colNames);
		return jTable;
	}

	public Vector<VarObject> getVos() {
		return vos;
	}

	public void setVos(Vector<VarObject> vos) {
		this.vos = vos;
	}
	
	public void removeAll(){
		p=-1;
		vos.removeAllElements();
		data=new String[100][7];
	}
	
}
