package cmm.functions;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTextArea;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;


import cmm.staticValues.Values;
import cmm.ui.MainFrame;
import cmm.ui.components.InputFrame;
import cmm.ui.components.VarsTable;

/**
 * 语义分析
 * 
 * @author axun
 */
public class SyntaxParse {
	private DefaultTreeModel treeModel = null;
	private ArrayList<VarObject> vos = new ArrayList<VarObject>();
	private String outputString = ""; // 输出结果
	private String debugOutputString = "";	
	private JTextArea textArea = null; // 输出对象
	private MyTable table=new MyTable();
	private VarsTable varsTable=new VarsTable();
	private int type=1;	//临时记住类型
	private int layer=0;	//临时记住层次
	private double returnVar=0;	//临时记住返回值
	

	public SyntaxParse(DefaultTreeModel treeModel) {
		this.treeModel = treeModel;
	}

	public void start() {
		program((TreeNode) treeModel.getRoot());
	}

	private void program(TreeNode node) {
		Enumeration<TreeNode> e = node.children();
		if (node.getChildCount() > 0) {
			while (e.hasMoreElements()) {
				TreeNode n = e.nextElement();
				state(n);
			}
		}
	}

	/**
	 * 语句，包括语句块
	 * 
	 * @param node
	 */
	private void state(TreeNode node) {
		if (node.toString().equalsIgnoreCase("intState")) { // int语句
			intState(node);
		}
		if (node.toString().equalsIgnoreCase("realState")) { // int语句
			realState(node);
		}
		if (node.toString().equalsIgnoreCase("returnState")) { // int语句
			returnState(node);
		}
		if (node.toString().equalsIgnoreCase("writeState")) {
			writeState(node);
		}
		if (node.toString().equalsIgnoreCase("writesState")) {
			writesState(node);
		}
		if (node.toString().equalsIgnoreCase("readState")) {
			readState(node);
		}
		if (node.toString().equalsIgnoreCase("ifState")) {
			ifState(node);
		}
		if (node.toString().equalsIgnoreCase("whileState")) {
			whileState(node);
		}
		if (node.toString().equalsIgnoreCase("assignState")) {
			assignState(node);
		}
		if (node.toString().equalsIgnoreCase("functionDefine")) {
			functionDefine(node);
		}
		if (node.toString().equalsIgnoreCase("block")) {
			block(node);
		}
	}

	/**
	 * 语句块
	 */
	private void block(TreeNode node) {
		layer++;
		Enumeration<TreeNode> e = node.children();
		if (node.getChildCount() > 0) {
			while (e.hasMoreElements()) {
				TreeNode n = e.nextElement();
				state(n);
				if (node.toString().equalsIgnoreCase("returnState")) { // return语句，结束block
					
					layer--;
					deleteVars(layer);
					return;
				}
				
			}
		}
		layer--;
		//块 结束，销毁局部变量
		deleteVars(layer);
	}

	/**
	 * int语句
	 * 
	 * @param node
	 */
	private void intState(TreeNode node) {
		if (node.getChildCount() == 1) { // 只有一个子节点,没有赋值
			if(node.getChildAt(0).toString().equalsIgnoreCase("array")){
				VarObject vo=new VarObject();
				vo.setType(1);
				vo.setLayer(layer);
				createArray(node.getChildAt(0),vo);
			}else{
				VarObject vo=new VarObject();
				vo.setType(1);
				vo.setVarString(node.getChildAt(0).toString());
				vo.setLayer(layer);
				addVar(vo);
			}
		} else if (node.getChildCount() == 2) {
			if(node.getChildAt(0).toString().equalsIgnoreCase("array")){
				VarObject vo=new VarObject();
				vo.setType(1);
				vo.setLayer(layer);
				createArray(node.getChildAt(0),vo);
			}
			VarObject vo=new VarObject();
			vo.setType(1);
			vo.setVarString(node.getChildAt(0).toString());
			vo.setLayer(layer);
			assignVar(node,vo);
		}
	}

	/**
	 * real语句
	 * 
	 * @param node
	 */
	private void realState(TreeNode node) {
		if (node.getChildCount() == 1) { // 只有一个子节点,没有赋值
			if(node.getChildAt(0).toString().equalsIgnoreCase("array")){
				VarObject vo=new VarObject();
				vo.setType(2);
				vo.setLayer(layer);
				createArray(node.getChildAt(0),vo);
			}else{
				VarObject vo=new VarObject();
				vo.setType(2);
				vo.setVarString(node.getChildAt(0).toString());
				vo.setLayer(layer);
				addVar(vo);
			}
		} else if (node.getChildCount() == 2) {
			if(node.getChildAt(0).toString().equalsIgnoreCase("array")){
				VarObject vo=new VarObject();
				vo.setType(2);
				vo.setLayer(layer);
				createArray(node.getChildAt(0),vo);
			}
			VarObject vo=new VarObject();
			vo.setType(2);
			vo.setVarString(node.getChildAt(0).toString());
			vo.setLayer(layer);
			assignVar(node,vo);
		}
	}
	
	/**
	 * return 语句
	 */
	private void returnState(TreeNode node){
		returnVar=expression(node.getChildAt(0));
	}

	/**
	 * if语句
	 */
	private void ifState(TreeNode node) {
		boolean bool = bool(node.getChildAt(0));
		if (bool){
			if(node.getChildCount() >= 2)	//可能是空if语句
				state(node.getChildAt(1));
		}
			
		else {
			if (node.getChildCount() >= 3)
				state(node.getChildAt(2));
		}
	}

	/**
	 * while语句
	 */
	public void whileState(TreeNode node) {
		if(node.getChildCount()>1) loop(node.getChildAt(0), node.getChildAt(1));	//可能循环体为空
	}
	/*
	 * 用户while循环
	 */
	private void loop(TreeNode bool,TreeNode block){
		if (bool(bool)) {
			state(block);
			loop(bool, block);
		}
		else return;
	}

	/**
	 * read语句
	 */
	private void readState(TreeNode node) {
		assignVar(node);
	}

	/**
	 * write语句
	 */
	private void writeState(TreeNode node) {
		double result=expression(node.getChildAt(0));
		if(type==1){
			output("" + (int)(result));
		}else{
			output(""+result);
		}
	}
	
	/**
	 * writes语句，写字符串
	 */
	private void writesState(TreeNode node) {
		String  result=stringExpression(node.getChildAt(0));
		output(result);
	}
	private String stringExpression(TreeNode node){
		return node.getChildAt(0).toString().replaceAll("\"", "");	//去掉双引号
	}

	/**
	 * 赋值语句
	 */
	private void assignState(TreeNode node) {
		assignVar(node);
	}
	/**
	 * 函数定义语句,保存函数名，参数列表等信息
	 */
	private void functionDefine(TreeNode node){
		TreeNode node1=node.getChildAt(0);
		int functionType=0;
		if(node1.toString().equals("void")){
			functionType=0;
		}
		if(node1.toString().equals("int")){
			functionType=1;
		}
		if(node1.toString().equals("real")){
			functionType=2;
		}
		//参数
		int childNum=node1.getChildAt(0).getChildCount();
		Vector<VarObject> vector=new Vector<VarObject>();
		for(int i=0;i<childNum;i++){
			VarObject var=new VarObject();
			if(node1.getChildAt(0).getChildAt(i).toString().equalsIgnoreCase("int")) var.setType(1);
			if(node1.getChildAt(0).getChildAt(i).toString().equalsIgnoreCase("real")) var.setType(2);
			var.setVarString(node1.getChildAt(0).getChildAt(i).getChildAt(0).toString());
			vector.add(var);
		}
		
		VarObject vo=new VarObject();
		vo.setType(10+functionType);
		vo.setVarString(node1.getChildAt(0).toString());
		vo.setLayer(layer);
		vo.setTreeNode(node1);
		vo.setArgNum(childNum);
		vo.setArgListVector(vector);
		addVar(vo);
	}
	/**
	 * 计算布尔表达式的值
	 */
	private boolean bool(TreeNode node) {
		boolean result = false;
		double result1 = expression(node.getChildAt(0));
		double result2 = expression(node.getChildAt(2));
		String string = node.getChildAt(1).toString();
		int action = 1; // 1== 2> 3< 4>= 5<=
		if (string.equalsIgnoreCase("=="))
			action = 1;
		if (string.equalsIgnoreCase(">"))
			action = 2;
		if (string.equalsIgnoreCase("<"))
			action = 3;
		if (string.equalsIgnoreCase(">="))
			action = 4;
		if (string.equalsIgnoreCase("<="))
			action = 5;
		if (string.equalsIgnoreCase("<>"))
			action = 6;
		
		/**
		 * 注意，因为计算表达式总是double'类型，所以判断==和<> 时要注意
		 */
		switch (action) {
		case 1:
			if (Math.abs(result1 - result2)<=Values.getMin())
				result = true;
			else
				result = false;
			break;
		case 2:
			if (result1 > result2)
				result = true;
			else
				result = false;
			break;
		case 3:
			if (result1 < result2)
				result = true;
			else
				result = false;
			break;
		case 4:
			if (result1 >= result2)
				result = true;
			else
				result = false;
			break;
		case 5:
			if (result1 <= result2)
				result = true;
			else
				result = false;
			break;
		case 6:
			if (result1 -result2>=Values.getMin())
				result = true;
			else
				result = false;
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * 计算表达式的值
	 * 每次先记住类型type=1；如果发现double，则type=2
	 * @param s
	 */
	private double expression(TreeNode node) {
		type=1;
		double result = 0;
		Enumeration<TreeNode> e = node.children();
		int action = 1; // 0 没有，1+，2- 默认是+
		while (e.hasMoreElements()) {
			TreeNode n = e.nextElement();
			if (n.toString().equalsIgnoreCase("term")) {
				switch (action) {
				case 1:
					result += term(n);
					break;
				case 2:
					result -= term(n);
					break;
				}
			}
			if (e.hasMoreElements()) {
				TreeNode n1 = e.nextElement();
				if (n1.toString().equalsIgnoreCase("+")) {
					action = 1;
				} else if (n1.toString().equalsIgnoreCase("-")) {
					action = 2;
				}
			} else
				break;
		}
		// outputln("expression:"+result);
		return result;
	}

	private double term(TreeNode node) {
		double result = 1;
		Enumeration<TreeNode> e = node.children();
		int action = 1; // 0 没有，1*，2/ 默认是*
		while (e.hasMoreElements()) {
			TreeNode n = e.nextElement();
			if (n.toString().equalsIgnoreCase("unary")) {
				switch (action) {
				case 1:
					result *= unary(n);
					break;
				case 2:
					double unaryResult=unary(n);
					if(Math.abs(unaryResult)<0.0000001) {
						error("除数不能为零！");
						return 0;
					}
					result /= unaryResult;
					break;
				}
			}
			if (e.hasMoreElements()) {
				TreeNode n1 = e.nextElement();
				if (n1.toString().equalsIgnoreCase("*")) {
					action = 1;
				} else if (n1.toString().equalsIgnoreCase("/")) {
					action = 2;
				}
			} else
				break;
		}
		// outputln("term:"+result);
		return result;
	}

	private double unary(TreeNode node) {
		TreeNode n = node.getChildAt(0);
		String string = n.toString();
		if (string.equalsIgnoreCase("expression"))
			return expression(n);
		else if (string.equalsIgnoreCase("array"))
			return useArray(n);
		else {
			boolean hasVar = false;
			for (int i = 0; i < n.toString().length(); i++) {
				if ((string.charAt(i) >= 65 && string.charAt(i) <= 90)
						|| (string.charAt(i) >= 97 && string.charAt(i) <= 122)) {
					hasVar = true;
				}
			}
			if (hasVar) {
				double r= useVar(n);
				debugOutputln("使用变量："+n+"="+r);
				return r;
			} else{
				for(int i=0;i<string.length();i++) if(string.charAt(i)=='.'){
					type=2;	//浮点型
					;
				}
				return Double.parseDouble(string);
			}
				
		}
	}
	
	/**
	 * 函数调用
	 * @param vo
	 * @return
	 */
	private double callFunction(VarObject vo,TreeNode node){
		debugOutput("调用函数："+node.toString()+",层："+layer+",参数：");
		//无参函数
		if(vo.getArgNum()<=0){
			if(vo.getTreeNode().getChildCount()>1) block(vo.getTreeNode().getChildAt(1));
			if(vo.getType()==12) type=2;
			return returnVar;
		}
		//有参数的函数，
		else if(vo.getArgNum()>0){
			layer++;
			//先根据参数创建变量
			Vector<VarObject> vars=vo.getArgListVector();
			for(int i=0;i<vars.size();i++){
				VarObject var=new VarObject(vars.get(i));
				var.setLayer(layer);
				layer--;	/*
					注意上面的一个变量现在还不能用，因为现在新建一个变量x，在使用时，就会把原函数中的x漏掉，因为新建的x比原函数中的优先级高*/
				var.setValueString(""+expression(node.getChildAt(i)));
				if(var.getType()==1&&type==2){
					warning("把real类型赋值给int类型，可能会损失精度！");
				}
				layer++;
				addVar(var);
				debugOutput(var.getVarString()+"("+var.getValueString()+")"+",");
			}
			debugOutputln("");
			if(vo.getTreeNode().getChildCount()>1) block(vo.getTreeNode().getChildAt(1));
			if(vo.getType()==12) type=2;
			layer--;
			deleteVars(layer);	//销毁参数
			return returnVar;
		}
		debugOutputln("函数调用失败！返回0");
		return 0;
	}

	/**
	 * 使用变量，包含函数调用
	 * 
	 * @param s
	 */
	private double useVar(TreeNode node) {
		String s = node.toString();
		//先找当前层的变量，找不到的话就找上一层的，最后找上上层的
		for(int t=0;t<=layer;t++)
		for (int i = vos.size()-1; i >=0; i--) {	//倒着找，提高效率
			
			if (s.equals(vos.get(i).getVarString())&&(vos.get(i).getLayer()==(layer-t))) {	
				debugOutputln(s+",层："+vos.get(i).getLayer()+",当前层："+layer);
				if (vos.get(i).isArray() == false){
					if(vos.get(i).getType()==2) type=2;
					if(vos.get(i).getType()==10){
						error(vos.get(i).getVarString()+"是void类型！");
						return 0;
					}else
					if(vos.get(i).getType()>=10){
						
						return callFunction(vos.get(i),node);
					}else
					if(vos.get(i).getValueString()==null){
						error("变量'"+vos.get(i).getVarString()+"'未初始化");
					}
					else return Double.parseDouble(vos.get(i).getValueString());
				}
			}
		}
		
		if (s.equals("array")) {
			return useArray(node);
		}
		debugOutputln("计算失败！返回0");
		return 0;
	}

	/**
	 * 变量赋值
	 * 
	 * @param s
	 */
	private void assignVar(TreeNode node,VarObject vo) {
		String s = node.getChildAt(0).toString();
		String v = null;
		double result;
			
			if (node.getChildCount() > 1) {
				result=expression(node.getChildAt(1));
				//double赋值给int 警告
				if(vo.getType()==1&&type==2){
					warning("把real类型赋值给int类型，可能会损失精度！");
				}
			} else {
				result=Double.parseDouble(new InputFrame(vo.getType()).getInputString());
			}
		
		if(!s.equalsIgnoreCase("array")){
			vo.setValueString("" + result);
			addVar(vo);
		}else{
			assignArray(node.getChildAt(0), v);
		}
		debugOutputln("变量赋值："+vo.getVarString()+",层："+vo.getLayer()+",值:"+vo.getValueString());
	}
	
	/**
	 * 变量赋值，包含void函数调用
	 * @param node
	 */
	private void assignVar(TreeNode node) {
		String s = node.getChildAt(0).toString();
		String v = null;
		
		//函数调用
		for (int i = 0; i < vos.size(); i++) {
			if (s.equals(vos.get(i).getVarString())) {
				if (vos.get(i).isArray() == false){
					type=vos.get(i).getType(); 
					
					if(vos.get(i).getType()==10){
						callFunction(vos.get(i),node.getChildAt(0));
						return;
					}
				}
			}
		}
		
		if (node.getChildCount() > 1) {
			v = "" + expression(node.getChildAt(1));
		} else {
			v = new InputFrame(type).getInputString();
		}
		
		if(!s.equalsIgnoreCase("array")){
		for(int t=0;t<=layer;t++){
			for (int i = vos.size()-1; i >=0; i--) {
				if (s.equals(vos.get(i).getVarString())&&(vos.get(i).getLayer()==(layer-t))) {
					vos.get(i).setValueString(""+v);
					if(vos.get(i).getType()==1&type==2){
						warning("把real类型赋值给int类型，可能会损失精度！");
					}
					refreshVarsTable();
					return;
				}
			}
		}
		}else{
			assignArray(node.getChildAt(0), v);
		}
	}
	
	/**
	 * 创建数组，数组自动初始化为0
	 */
	private void createArray(TreeNode node ,VarObject vo){
		vo.setVarString(node.getChildAt(0).toString());
		vo.setArray(true);
		vo.setArraySize(Integer.parseInt(node.getChildAt(1).toString()));
		addVar(vo);
	}

	/**
	 * 使用数组
	 * 
	 * @param s
	 */
	private double useArray(TreeNode node) {
		String name = node.getChildAt(0).toString();
		int index = (int) expression(node.getChildAt(1));
		for (int i = 0; i < vos.size(); i++) {
			if (name.equals(vos.get(i).getVarString())) {
				if(vos.get(i).getType()==1&&type==2){
					error("数组下表不是整数！");
				}
				if (index >= vos.get(i).getArraySize()) {
					error("数组越界！");
				}
				type=vos.get(i).getType();
				return vos.get(i).getArrayElement(index);
			}
		}
		return 0;
	}

	public void assignArray(TreeNode node, String v) {

		String name = node.getChildAt(0).toString();
		int index = (int) expression(node.getChildAt(1));
		
		for (int i = 0; i < vos.size(); i++) {
			if (name.equals(vos.get(i).getVarString())) {
				if (index >= vos.get(i).getArraySize()) {
					error("数组越界！");
				}
				if(vos.get(i).getType()==1&&type==2){
					error("数组下表不是整数！");
				}
				vos.get(i).setArrayElement(Double.parseDouble(v), index);
			}
		}
		refreshVarsTable();
	}
	/**
	 * 创建变量
	 */
	private void addVar(VarObject vo){
		vos.add(vo);
		debugOutputln("创建变量："+vo.getVarString()+",层："+vo.getLayer()+",值:"+vo.getValueString());
		printStack();
		refreshVarsTable();
	}
	/**
	 * 销毁变量
	 * @param s
	 */
	private void deleteVars(int layer){
		debugOutput("销毁变量：");
		for (int i = 0; i < vos.size(); i++) {
			if (vos.get(i).getLayer()>layer) {
				debugOutput(vos.get(i).getVarString()+",层："+vos.get(i).getLayer()+",值:"+vos.get(i).getValueString()+";");
				vos.remove(i);
				i--;	//注意此处，不然无法完全删除，因为销毁完成后，后面的会往前进一个
			}
		}
		debugOutputln("");
		printStack();
		refreshVarsTable();
	}
	
	
	public void warning(String s){
		outputln("警告：" + s);
	}
	public void error(String s) {
		outputln("错误：" + s);
	}

	private void output(String s) {
		outputString += s;
		textArea.setText(outputString);
	}

	private void outputln(String s) {
		output(s + "\r\n");
	}

	/**
	 * 设置输出对象
	 */
	public void setOutputObject(JTextArea textArea) {
		this.textArea = textArea;
	}
	/**
	 * 控制台输出
	 * @param s
	 */
	private void debugOutput(String s){
		debugOutputString+=s;
		MainFrame.outputPanel.DebugOutput(debugOutputString);
	}
	private void debugOutputln(String s){
		debugOutputString+=s+"\n";
		MainFrame.outputPanel.DebugOutput(debugOutputString);
	}
	private void printStack(){
//		debugOutput("变量表：");
//		for (int i = 0; i < vos.size(); i++) {
//			debugOutput("("+vos.get(i).getVarString()+","+vos.get(i).getValueString()+","+vos.get(i).getLayer()+")");
//		}
//		debugOutputln("");
	}
	
	/**
	 * 获得输出结果
	 * @return
	 */
	public String getOutputString() {
		return outputString;
	}
	/**
	 * 显示变量表
	 */
	public void showTable() {
		varsTable.setVisible(true);
	}
	
	public void refreshVarsTable(){
		//显示出来
		table.removeAll();
		for(int i=0;i<vos.size();i++){
			table.addRow(vos.get(i));
		}
		varsTable.refresh(table);
	}
}
