package cmm.functions;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class VarObject {
	private String varString=null;	//变量名
	private int type=0;	//1，整型，2，浮点型,3，字符串，10,void函数，11整型函数，12浮点型函数
	private String valueString=null;
	private int layer=0;	//层
	private boolean array=false;	//是否是数组
	private int arraySize=-1;	//数组大小
	private ArrayList<Double> arrayElementsList=null;	//数组元素
	private int argNum=0;		//函数参数个数
	private Vector<VarObject> argListVector=null;	//参数列表
	private DefaultMutableTreeNode node=null;	//函数在语法树上的位置
	private TreeNode treeNode=null;	//函数在语法树上的位置
	private int pointer=0;	//指针指向的变量的地址
	public VarObject(){
		
	}
	
	public VarObject(VarObject vo){
		this.varString=vo.getVarString();
		this.type=vo.getType();
		this.valueString=vo.getValueString();
		this.layer=vo.getLayer();
		this.array=vo.isArray();
		this.arraySize=vo.getArraySize();
		this.arrayElementsList=vo.getArrayElementsList();
		this.argNum=vo.getArgNum();
		this.argListVector=vo.getArgListVector();
		this.node=vo.getNode();
		this.treeNode=vo.getTreeNode();
	}
	
	public String getVarString() {
		return varString;
	}
	public void setVarString(String varString) {
		this.varString = varString;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
		
	}
	public String getValueString() {
		return valueString;
	}
	public void setValueString(String valueString) {
		this.valueString = valueString;
	}
	public boolean isArray() {
		return array;
	}
	public void setArray(boolean array) {
		this.array = array;
	}

	public int getArraySize() {
		return arraySize;
	}
	public void setArraySize(int arraySize) {
		this.arraySize = arraySize;
		if(array){
			arrayElementsList=new ArrayList<Double>();
			for(int i=0;i<arraySize;i++){
				arrayElementsList.add(0.0);
			}
		}
	}
	
	public String getTypeString(){
		switch(type){
		case(1): return "INT";
		case(2): return "REAL";
		
		case 10:return "VOID_FUNCTION";
		case(11): return "INT_FUNCTION";
		case 12: 	return "REAL_FUNCTION";
		default : return "ERROR";
		}
	}
	
	/**
	 * 对数组元素操作
	 * 
	 */
	public boolean setArrayElement(double value,int index){
		if(index>=arraySize) return false;	//数组越界
		arrayElementsList.set(index, value);
		return true;
	}
	public double getArrayElement(int index){
		if(index>=arraySize) return 0;	//数组越界
		else return arrayElementsList.get(index);
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public int getArgNum() {
		return argNum;
	}
	public void setArgNum(int argNum) {
		this.argNum = argNum;
	}

	public DefaultMutableTreeNode getNode() {
		return node;
	}
	public void setNode(DefaultMutableTreeNode node) {
		this.node = node;
	}
	public TreeNode getTreeNode() {
		return treeNode;
	}
	public void setTreeNode(TreeNode treeNode) {
		this.treeNode = treeNode;
	}
	public Vector<VarObject> getArgListVector() {
		return argListVector;
	}
	public void setArgListVector(Vector<VarObject> argListVector) {
		this.argListVector = argListVector;
	}

	public ArrayList<Double> getArrayElementsList() {
		return arrayElementsList;
	}

	public void setArrayElementsList(ArrayList<Double> arrayElementsList) {
		this.arrayElementsList = arrayElementsList;
	}
}
