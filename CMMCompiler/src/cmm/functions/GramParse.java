package cmm.functions;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.HeaderTokenizer.Token;

import sun.reflect.generics.tree.Tree;

/*
 * 语法分析
 */
/**
 * 备忘：使用异常来做是不是简单点？
 */
public class GramParse {
	private CMMToken[] cmmTokens = new CMMToken[2000];
	ArrayList<CMMToken> errorTokens=new ArrayList<CMMToken>();
	private int p = 0;
	private int size = 0; // token 的个数

	private Stack stack = new Stack(); // 用来检查变量的作用域的

	private String outputString = "";
	private String debugOutputString="";
	private String inString = ""; // 输入
	private boolean hasError = false;
	private boolean hasReturn=false;	//临时记住，有没有return语句

	// 语法树
	JTree tree;
	DefaultTreeModel treeModel;
	DefaultMutableTreeNode root;
	private int nodeNum = 0; // 节点序号，命名用的
	
	
	/**
	 * 先调用词法分析，获得token
	 * 
	 * @param in
	 */
	public GramParse(String in, CMMToken[] cmmTokens, int size) {
		this.cmmTokens = cmmTokens;
		this.size = size;
		this.inString = in;
		initialize();
	}
	
	public void initialize() {
		outputLine("调用词法分析……");
		outputLine("单词个数：" + size);
		p = -1;
		outputLine("初始化语法树……");

		root = new DefaultMutableTreeNode("program");
		treeModel = new DefaultTreeModel(root);
		tree = new JTree(treeModel);
		tree.setToggleClickCount(1); // 单击以展开节点
	}

	/**
	 * 开始
	 */
	public void start() {
		program();
	}

	/**
	 * 程序，由语句组成
	 */
	public void program() {
		while (true) {
			state(root);
			if (p >= size - 1)
				break; // 结束了
		}
		outputLine("语法分析结束……");
	}

	/**
	 * 语句，由语句或者语句块组成
	 */
	public void state(DefaultMutableTreeNode node) {
		if (moveP() == false) {
			return;
		}
		CMMToken cmmToken = getToken();
		if (cmmToken.getToken().equals("int")
				|| cmmToken.getToken().equals("real")) {
			backP();
			var_define_state(node);
		}else if (cmmToken.getToken().equals("string")) {
			backP();
			string_state(node);
		}  
		else if (cmmToken.getToken().equals("void")) {
			backP();
			void_state(node);
		} else if (cmmToken.getToken().equals("return")) {
			backP();
			return_state(node);
		} else if (cmmToken.getToken().equals("if")) {
			backP();
			if_state(node);
		} else if (cmmToken.getToken().equals("while")) {
			backP();
			while_state(node);
		} else if (cmmToken.getTypeString().equals("IDENT")) {
			backP();
			assign_state(node);
		} else if (cmmToken.getToken().equals("write")) {
			backP();
			write_state(node);
		} else if (cmmToken.getToken().equals("writes")) {
			backP();
			writes_state(node);
		} else if (cmmToken.getToken().equals("read")) {
			backP();
			read_state(node);
		} else if (cmmToken.getToken().equals(";")) { // 空语句
			return;
		} else if (cmmToken.getToken().equals("{")) { // 语句块，等价于语句
			backP();
			block(node);
		} else {
			return;
		}

	}

	/**
	 * 语句块,不包含return语句
	 * 
	 */
	public void block(DefaultMutableTreeNode node) {
		if (moveP() == false) {
			return;
		}
		if (!getToken().getToken().equals("{")) {
			error(getToken(), "应该是'{'");
		}
		if (moveP() == false) {
			return;
		}
		if (getToken().getToken().equals("}")) { // 空block
			return;
		}
		backP();
		pushVar(getToken(), 0, false, 0,-1,null,null); // 把大括号压入栈；
		DefaultMutableTreeNode blockNode = new DefaultMutableTreeNode("block");
		// blockNode.add(new DefaultMutableTreeNode("{")); //没有必要加
		node.add(blockNode);
		state(blockNode);
		// 找匹配的右括号
		while (true) {
			if (moveP() == false) {
				error(getToken(), "错误：缺少'}'");
				return;
			}
			CMMToken cmmToken1 = getToken();
			if (cmmToken1.getToken().equals("}")) {
				popVar(); // 碰到} ，就弹出，直到{
				// blockNode.add(new DefaultMutableTreeNode("}"));
				return;
			}
			backP(); // 注意指针向前移一位
			state(blockNode); // 没有结束，继续匹配语句

		}
		
		
	}

	
	/**
	 * return 语句
	 */
	private void return_state(DefaultMutableTreeNode node1){
		if (moveP() == false) {
			error(getToken() ,"缺少return");
			return;
		}
		if (!getToken().getToken().equals("return")) {
			error(getToken(), "应该是'return'");
		}
		DefaultMutableTreeNode node=new DefaultMutableTreeNode("returnState");
		node1.add(node);
		
		if(expression(node)==false){
			System.out.println(getToken().getToken());
			if (!getToken().getToken().equals(";")) {
				error(getToken(), "应该是';'");
				return;
			}
			return;
			
		}
		if (moveP() == false) {
			error(getToken(),"缺少';'");
			return;
		}
		if (!getToken().getToken().equals(";")) {
			error(getToken(), "应该是';'");
			return;
		}
		hasReturn=true;
		return;
	}

	/**
	 * 变量定义&赋值语句
	 */
	private void var_define_state(DefaultMutableTreeNode node) {
		if (moveP() == false) {
			return;
		}
		if (getToken().getToken().equals("int")) {
			backP();
			int_define_state(node);
		} else if (getToken().getToken().equals("real")) {
			backP();
			real_define_state(node);
		} else {
			error(getToken(),"应该是int或者real");
		}
	}

	/**
	 * 变量定义&赋值语句,int
	 */
	private void int_define_state(DefaultMutableTreeNode node1) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("intState");
		node1.add(node);
		if (moveP() == false) {
			return;
		}
		if (!getToken().getToken().equals("int")) {
			error(getToken(), "应为int");
			return;
		}

		if (moveP() == false) {
			error(getToken(),"缺少标识符");
			return;
		}
		CMMToken cmmToken = getToken();
		if (!(cmmToken.getTypeString().equals("IDENT"))) {
			error(getToken(), "应该是'标识符'");
			return;
		}

		// 添加节点
		DefaultMutableTreeNode identNode=new DefaultMutableTreeNode(getToken().getToken());
		CMMToken identToken=getToken();
		
		useIdent(node,identNode,identToken);

		if (moveP() == false) {
			error(getToken(), "缺少';'");
			return;
		}
		CMMToken cmmToken1 = getToken();
		// 是不是函数定义
		if (getToken().getToken().equals("(")) {

			backP();

			node1.remove(node);
			DefaultMutableTreeNode functionNode = new DefaultMutableTreeNode(
					"functionDefine");
			node1.add(functionNode);
			functionDefine(functionNode, cmmToken,1);
			return;
		}
		//是不是数组
		if(getToken().getToken().equals("[")){
			backP();
			node.remove(identNode);
			DefaultMutableTreeNode arrayNode = new DefaultMutableTreeNode(
					"array");
			node.add(arrayNode);
			createArray(arrayNode, cmmToken);
			return;
		}
		pushVar(cmmToken, 1, false, -1,-1,null,null);
		if (getToken().getToken().equals(";")) {

			return;

		}
		
		if (!(getToken().getToken().equals("="))) {
			error(getToken(), "应该是'='");
			return;
		}

		if (expression(node) == false) {
			error(getToken(),"表达式错误！");
		} else if (moveP() == false) {
			error(getToken(), "错误：缺少';'");
			backP();
			return;
		}
		if (getToken().getToken().equals(";")) {

			return; // 正确的定义&赋值语句
		} else {
			error(getToken(), "应该是';'");
		}

	}

	/**
	 * 变量定义&赋值语句 real
	 */
	private void real_define_state(DefaultMutableTreeNode node1) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("realState");
		node1.add(node);
		if (moveP() == false) {
			return;
		} else {
			if (!getToken().getToken().equals("real")) {
				error(getToken(), "应为int");
				return;
			}
		}

		if (moveP() == false) {
			error(getToken(),"缺少标识符！");
			return;
		}
		CMMToken cmmToken = getToken();
		if (!(cmmToken.getTypeString().equals("IDENT"))) {

			error(getToken(),"缺少标识符！");
			return;
		}

		// 添加节点
		DefaultMutableTreeNode identNode=new DefaultMutableTreeNode(getToken().getToken());
		CMMToken identToken=getToken();
		useIdent(node,identNode,identToken);
		
		if (moveP() == false) {
			pushVar(cmmToken, 2, false, 0,-1,null,null);
			error(getToken(),"标识符错误！");
			return;
		}
		CMMToken cmmToken1 = getToken();
		// 是不是函数定义
		if (getToken().getToken().equals("(")) {

			backP();
			// 识别数组
			node1.remove(node);
			DefaultMutableTreeNode functionNode = new DefaultMutableTreeNode(
					"functionDefine");
			node1.add(functionNode);
			functionDefine(functionNode, cmmToken,2);
			return;
		}
		// 是不是数组
		if (getToken().getToken().equals("[")) {
			backP();
			// 识别数组
			node.remove(identNode);
			DefaultMutableTreeNode arrayNode = new DefaultMutableTreeNode(
					"array");
			node.add(arrayNode);
			createArray(arrayNode, cmmToken);
			return;
		}
		pushVar(cmmToken, 2, false, -1,-1,null,null);

		if (cmmToken1.getToken().equals(";")) {
			return;
		}
		if (!(cmmToken1.getToken().equals("="))) {
			error(getToken(),"应该是'='");
			return;
		}

		if (expression(node) == false) {
			error(getToken(),"表达式错误！");
		} else if (moveP() == false) {
			error(getToken(), "错误：缺少';'");
			backP();
		} else {
			if (getToken().getToken().equals(";")) {
				// DefaultMutableTreeNode fhNode = new
				// DefaultMutableTreeNode(
				// getToken().getToken());
				// node.add(fhNode);
				return; // 正确的定义&赋值语句
			} else {
				error(getToken(), "应该是';'");
			}
		}

	}
	
	private void string_state(DefaultMutableTreeNode node1){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("stringState");
		node1.add(node);
		if (moveP() == false) {
			return;
		} else {
			if (!getToken().getToken().equals("string")) {
				error(getToken(), "应为string");
				return;
			}
		}

		if (moveP() == false) {
			error(getToken(),"缺少标识符！");
			return;
		}
		CMMToken cmmToken = getToken();
		if (!(cmmToken.getTypeString().equals("IDENT"))) {

			error(getToken(),"缺少标识符！");
			return;
		}

		// 添加节点
		DefaultMutableTreeNode identNode=new DefaultMutableTreeNode(getToken().getToken());
		CMMToken identToken=getToken();
		useIdent(node,identNode,identToken);
		
		if (moveP() == false) {
			pushVar(cmmToken, 7, false, 0,-1,null,null);
			error(getToken(),"标识符错误！");
			return;
		}
		CMMToken cmmToken1 = getToken();
		pushVar(cmmToken, 7, false, -1,-1,null,null);

		if (cmmToken1.getToken().equals(";")) {
			return;
		}
		if (!(cmmToken1.getToken().equals("="))) {
			error(getToken(),"应该是'='");
			return;
		}

		if (string(node) == false) {
			error(getToken(),"字符串错误！");
		} else if (moveP() == false) {
			error(getToken(), "错误：缺少';'");
			backP();
		} else {
			if (getToken().getToken().equals(";")) {
				return; // 正确的定义&赋值语句
			} else {
				error(getToken(), "应该是';'");
			}
		}
	}
	
	/**
	 * 此函数只做预处理，然后调用functionDefine（）
	 * @param node1
	 */
	private void void_state(DefaultMutableTreeNode node1){
		if (moveP() == false) {
			return;
		}
		if (!getToken().getToken().equals("void")) {
			error(getToken(), "应为void");
			return;
		}

		if (moveP() == false) {
			return;
		}
		if (!(getToken().getTypeString().equalsIgnoreCase("IDENT"))) {

			error(getToken(), "应该为一个标识符");
		}
		DefaultMutableTreeNode functionNode = new DefaultMutableTreeNode(
		"functionDefine");
		node1.add(functionNode);
		functionDefine(functionNode, getToken(),0);
	}

	/**
	 * write 语句，写一个表达式
	 */
	private void write_state(DefaultMutableTreeNode node1) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("writeState");
		node1.add(node);
		if (moveP() == false) {
			return;
		}
		if (!getToken().getToken().equals("write")) {
			error(getToken(), "应为write");
			return;
		}

		if (moveP() == false) {
			return;
		}
		if (!(getToken().getToken().equals("("))) {

			error(getToken(), "应该为'('");
		}
		expression(node);
		if (moveP() == false) {
			error(getToken(), "应该为')'");
			return;
		}
		if (!(getToken().getToken().equals(")"))) {
			error(getToken(), "应该为')'");
			return;
		}

		if (moveP() == false) {
			error(getToken(), "错误：缺少';'");
			backP();
			return;
		}
		if (getToken().getToken().equals(";")) {
			return; // 正确的定义&赋值语句
		} else {
			error(getToken(), "应该是';'");
		}

		return;

	}
	
	private void writes_state(DefaultMutableTreeNode node1) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("writesState");
		node1.add(node);
		if (moveP() == false) {
			return;
		}
		if (!getToken().getToken().equals("writes")) {
			error(getToken(), "应为writes");
			return;
		}

		if (moveP() == false) {
			return;
		}
		if (!(getToken().getToken().equals("("))) {

			error(getToken(), "应该为'('");
		}
		stringExpression(node);
		if (moveP() == false) {
			error(getToken(), "应该为')'");
			return;
		}
		if (!(getToken().getToken().equals(")"))) {
			error(getToken(), "应该为')'");
			return;
		}

		if (moveP() == false) {
			error(getToken(), "错误：缺少';'");
			backP();
			return;
		}
		if (getToken().getToken().equals(";")) {
			return; // 正确的定义&赋值语句
		} else {
			error(getToken(), "应该是';'");
		}

		return;

	}
	private void stringExpression(DefaultMutableTreeNode node1){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("stringExpression");
		node1.add(node);
		if (moveP() == false) {
			return;
		}
		if (getToken().getTypeString().equalsIgnoreCase("STRING")) {
			DefaultMutableTreeNode stringNode = new DefaultMutableTreeNode(getToken().getToken());
			node.add(stringNode);
			return;
		}
//		if (getToken().getTypeString().equalsIgnoreCase("STRING")) {
//			DefaultMutableTreeNode stringNode = new DefaultMutableTreeNode(getToken().getToken());
//			node1.add(stringNode);
//			return;
//		}
	}

	/**
	 * read 语句,只能读一个变量
	 */
	private void read_state(DefaultMutableTreeNode node1) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("readState");
		node1.add(node);
		if (moveP() == false) {
			return;
		}
		if (!getToken().getToken().equals("read")) {
			error(getToken(), "应为read");
			return;
		}
		if (moveP() == false) {
			return;
		}
		if (!(getToken().getToken().equals("("))) {
			error(getToken(), "应该为'('");
		}

		if (moveP() == false) {
			error(getToken(), "应该为一个变量");
			return;
		}
		if (!getToken().getTypeString().equalsIgnoreCase("IDENT")) {
			error(getToken(), "应该是一个标识符");
		}
		DefaultMutableTreeNode identNode=new DefaultMutableTreeNode(getToken().getToken());
		CMMToken identToken=getToken();
		useIdent(node,identNode,identToken);
		if (moveP() == false) {
			useVar(identToken, false);
			return;
		}
		CMMToken cmmToken1 = getToken();
		// 是不是数组
		if (getToken().getToken().equals("[")) {
			useVar(identToken, true); // 使用变量时总是要调用此方法，以检测变量是否合法
			backP();
			// 识别数组
			node.remove(identNode);
			DefaultMutableTreeNode arrayNode = new DefaultMutableTreeNode(
					"array");
			node.add(arrayNode);
			arrayNode.add(identNode);
			useArray(arrayNode, getToken());
		} else {
			useVar(identToken, false); // 使用变量时总是要调用此方法，以检测变量是否合法
			backP();
		}
		if (moveP() == false) {
			return;
		}
		if (!(getToken().getToken().equals(")"))) {
			error(getToken(), "应该为')'");
			return;
		}

		if (moveP() == false) {
			error(getToken(), "错误：缺少';'");
			backP();
		} else {
			if (getToken().getToken().equals(";")) {

				return; // 正确的定义&赋值语句
			} else {
				error(getToken(), "应该是';'");
			}
		}
		return;

	}

	/**
	 * if 语句
	 * 
	 * @param s
	 */
	private void if_state(DefaultMutableTreeNode node1) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("ifState");
		node1.add(node);
		if (moveP() == false) {
			return;
		}
		if (!getToken().getToken().equals("if")) {
			error(getToken(),"应该是if");
			return;
		}

		if (moveP() == false) {
			error(getToken(),"应该是'('");
			return;
		}
		if (!(getToken().getToken().equals("("))) {
			error(getToken(), "应该为：'('");
			return;
		}

		if (bool(node) == false) {
			error(getToken(), "应该为一个bool表达式");
		} else if (moveP() == false) {
			error(getToken(), "应该为')'");
			return;
		}
		if (!(getToken().getToken().equals(")"))) {
			error(getToken(), "应该为')'");
			return;
		}

		state(node);
		if (moveP() == false) {
			return;
		}
		if (getToken().getToken().equals("else")) {
			state(node);
			return;
		}
		backP();
		return;

	}

	/**
	 * while 语句，结构相似 if语句
	 * 
	 * @param s
	 */
	private void while_state(DefaultMutableTreeNode node1) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("whileState");
		node1.add(node);
		if (moveP() == false) {
			return;
		}
		if (!getToken().getToken().equals("while")) {
			error(getToken(),"应该是while");
			return;
		}

		if (moveP() == false) {
			error(getToken(),"应该是'('");
			return;
		}
		if (!(getToken().getToken().equals("("))) {
			error(getToken(), "应该为：'('");
			return;
		}

		if (bool(node) == false) {
			error(getToken(), "应该为一个bool表达式");
		} else if (moveP() == false) {
			error(getToken(), "应该为')'");
			return;
		}
		if (!(getToken().getToken().equals(")"))) {
			error(getToken(), "应该为')'");
			return;
		}

		state(node);

		return;

	}

	/**
	 * 简单的赋值语句
	 */
	private void assign_state(DefaultMutableTreeNode node1) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("assignState");
		node1.add(node);
		if (moveP() == false) {
			return;
		}
		if (!getToken().getTypeString().equalsIgnoreCase("IDENT")) {
			error(getToken(),"应该是标识符");
			return;
		}
		DefaultMutableTreeNode identNode=new DefaultMutableTreeNode(getToken().getToken());
		CMMToken identToken=getToken();
		useIdent(node, identNode,identToken);

		if (moveP() == false) {
			error(getToken(),"缺少';'");
			return;
		}
		CMMToken cmmToken1 = getToken();
		// 是不是数组
		if (getToken().getToken().equals("[")) {
			useVar(identToken, true);
			backP();
			// 识别数组
			node.remove(identNode);
			DefaultMutableTreeNode arrayNode = new DefaultMutableTreeNode(
					"array");
			node.add(arrayNode);
			arrayNode.add(identNode);
			useArray(arrayNode, getToken());
		} else {
			useVar(identToken, false);
			backP();
		}

		if (moveP() == false) {
			return;
		}
		if(getToken().getToken().equals(";")){
			return;
		}
		
		if (!(getToken().getToken().equals("="))) {
			error(getToken(),"应该是'='");
			return;
		}
		if (moveP() == false) {
			error(getToken(),"缺少表达式");
			return;
		}
		backP();
		expression(node);
		if (moveP() == false) {
			error(getToken(), "错误：缺少';'");
			return;
		} else if (getToken().getToken().equals(";")) {

			return; // 正确的赋值语句
		} else {
			error(getToken(), "错误：缺少';'");
			return;
		}

	}
	
	
	/**
	 * 函数定义,第三个参数是返回类型 0,void,1，int，2 real
	 * 注意此处的变量作用域标志不太一样
	 * {参数block}
	 */
	private void functionDefine(DefaultMutableTreeNode node1,CMMToken cmmToken,int type ){
		DefaultMutableTreeNode node2=null;
		if(type==0){
			node2=new DefaultMutableTreeNode("void");
		}else if(type==1){
			node2=new DefaultMutableTreeNode("int");
		}else if(type==2){
			node2=new DefaultMutableTreeNode("real");
		}
		node1.add(node2);
		DefaultMutableTreeNode node=new DefaultMutableTreeNode(cmmToken.getToken());	//函数名称
		
		node2.add(node);
		if(moveP()==false){
			error(getToken(),"应该是'('");
			return;
		}
		if(!getToken().getToken().equals("(")){
			error(getToken(),"应该是'('");
			return;
		}
		
		
		if(moveP()==false){
			error(getToken(),"应该是')'");
			return;
		}
		//无参数
		if(getToken().getToken().equals(")")){
			pushVar(cmmToken, 10+type, false, -1,0,null,node2);
			hasReturn=false;
			block(node2);
			if(hasReturn==true&&type==0) error(getToken(), "不能包含带参数的return语句");
			if(hasReturn==false&&type!=0) error(getToken(), "缺少return语句");
			hasReturn=false;
			return;
		}
		
		
		//带参数的函数定义
		if(getToken().getToken().equals("int")||getToken().getToken().equals("real")){
			backP();
			
			args(node,cmmToken,type);
			
			hasReturn=false;
			block(node2);
			if(hasReturn==true&&type==0) error(getToken(), "不能包含带参数的return语句");
			if(hasReturn==false&&type!=0) error(getToken(), "缺少return语句");
			hasReturn=false;
			popVar();
			return;
		}
		
		
		
	}
	
	/**
	 * 函数参数
	 */
	private void args(DefaultMutableTreeNode node,CMMToken cmmToken,int type){
		int argNum=0;
		Vector<VarObject> argList=new Vector<VarObject>();
		Vector<CMMToken> varTokenVector=new Vector<CMMToken>();
		
		while(true){
			
			if(moveP()==false){
				return;
			}
			//INT类型
			if(getToken().getToken().equals("int")){
				if(moveP()==false){
					error(getToken(),"应该是一个标识符！");
					return;
				}
				if(!(getToken().getTypeString().equalsIgnoreCase("IDENT"))){
					error(getToken(),"应该是一个标识符！");
					return;
				}
				DefaultMutableTreeNode intNode=new DefaultMutableTreeNode("int");
				node.add(intNode);
				DefaultMutableTreeNode identNode=new DefaultMutableTreeNode(getToken().getToken());
				intNode.add(identNode);
				VarObject vo=new VarObject();
				vo.setType(1);
				vo.setVarString(getToken().getToken());
				argList.add(vo);
				argNum++;
				//
			
				varTokenVector.add(getToken());
				
			}
			
			//real
			if(getToken().getToken().equals("real")){
				if(moveP()==false){
					error(getToken(),"应该是一个标识符！");
					return;
				}
				if(!(getToken().getTypeString().equalsIgnoreCase("IDENT"))){
					error(getToken(),"应该是一个标识符！");
					return;
				}
				DefaultMutableTreeNode realNode=new DefaultMutableTreeNode("real");
				node.add(realNode);
				DefaultMutableTreeNode identNode=new DefaultMutableTreeNode(getToken().getToken());
				realNode.add(identNode);
				VarObject vo=new VarObject();
				vo.setType(2);
				vo.setVarString(getToken().getToken());
				argList.add(vo);
				argNum++;
				//
				CMMToken token=new CMMToken();
				token.setToken(vo.getVarString());
				token.setType(2);
				pushVar(token, 2, false, -1, 0, null, null);
			}
			
			if(moveP()==false){
				error(getToken(),"应该是')'");
				return;
			}
			if(getToken().getToken().equals(",")) continue;
			if(getToken().getToken().equals(")")) break;
			
			break;
		}
		
		pushVar(cmmToken, 10+type, false, -1, argNum, argList, node);
		//构造一个大括号，把变量和函数体一起放进去
		CMMToken braceToken=new CMMToken();
		braceToken.setToken("{");
		braceToken.setType(4);
		
		pushVar(braceToken, 4, false, -1, 0, null, null);
		for(int i=0;i<varTokenVector.size();i++){
			pushVar(varTokenVector.get(i), 1, false, -1, 0, null, null);
		}
	}
	/**
	 * 布尔表达式，不是单独的语句
	 */
	private boolean bool(DefaultMutableTreeNode node1) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("boolExpr");
		node1.add(node);
		if (expression(node) == false)
			return false;
		if (moveP() == false) {
			return false;
		} if (getToken().getToken().equals(">")) {
			DefaultMutableTreeNode dyNode = new DefaultMutableTreeNode(
					getToken().getToken());
			node.add(dyNode);
			return expression(node);
		} else if (getToken().getToken().equals("<")) {
			DefaultMutableTreeNode dyNode = new DefaultMutableTreeNode(
					getToken().getToken());
			node.add(dyNode);
			return expression(node);
		} else if (getToken().getToken().equals(">=")) {
			DefaultMutableTreeNode dyNode = new DefaultMutableTreeNode(
					getToken().getToken());
			node.add(dyNode);
			return expression(node);
		} else if (getToken().getToken().equals("<=")) {
			DefaultMutableTreeNode dyNode = new DefaultMutableTreeNode(
					getToken().getToken());
			node.add(dyNode);
			return expression(node);
		} else if (getToken().getToken().equals("==")) {
			DefaultMutableTreeNode dyNode = new DefaultMutableTreeNode(
					getToken().getToken());
			node.add(dyNode);
			return expression(node);
		} else if (getToken().getToken().equals("<>")) {
			DefaultMutableTreeNode dyNode = new DefaultMutableTreeNode(
					getToken().getToken());
			node.add(dyNode);
			return expression(node);
		} else
			return false;
	}
	private boolean string(DefaultMutableTreeNode node1){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("string");
		node1.add(node);
		if(moveP()==false){
			error(getToken());
			return false;
		}
		if(!(getToken().getType()==7)){
			error(getToken());
			return false;
		}
		DefaultMutableTreeNode stringNode = new DefaultMutableTreeNode(getToken().getToken());
		node.add(stringNode);
		return true;
	}
	/**
	 * 算术表达式,不是单独的语句 expression=term[+|-term]*
	 * 
	 * @param s
	 */
	public boolean expression(DefaultMutableTreeNode node1) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("expression");
		node1.add(node);
		int count = 0;
		DefaultMutableTreeNode termNode = new DefaultMutableTreeNode("term");
		node.add(termNode);

		if (term(termNode))
			count++;
		while (true) {
			if (moveP() == false) {
				return count > 0;
			} else if (getToken().getToken().equals("+")
					|| getToken().getToken().equals("-")) {
				DefaultMutableTreeNode fNode = new DefaultMutableTreeNode(
						getToken().getToken());
				node.add(fNode);
				DefaultMutableTreeNode term2Node = new DefaultMutableTreeNode(
						"term");
				node.add(term2Node);
				if (term(term2Node) == false) {
					return count > 0;
				} else {
					count++;
					continue;
				}
			} else {
				backP();
				return count > 0;
			}
		}
	}

	/**
	 * 项 term=unary[*|/unary]*
	 * 
	 * @param s
	 */
	public boolean term(DefaultMutableTreeNode node) {
		int count = 0;
		DefaultMutableTreeNode unaryNode = new DefaultMutableTreeNode("unary");
		node.add(unaryNode);
		if (unary(unaryNode))
			count++;
		while (true) {

			if (moveP() == false) {
				return count > 0;
			} else if (getToken().getToken().equals("*")
					|| getToken().getToken().equals("/")) {
				DefaultMutableTreeNode fNode = new DefaultMutableTreeNode(
						getToken().getToken());
				node.add(fNode);
				DefaultMutableTreeNode unary2Node = new DefaultMutableTreeNode(
						"unary");
				node.add(unary2Node);
				if (unary(unary2Node)) {
					count++;
					continue;
				}

			} else {
				backP();
				return count > 0;
			}
		}
	}

	/**
	 * 因子 unary=[-]element
	 * 
	 * @param s
	 */
	public boolean unary(DefaultMutableTreeNode node) {
		int count = 0;
		while (true) {

			if (moveP() == false) {
				return count > 0;
			} else if (getToken().getToken().equals("-")) {
				DefaultMutableTreeNode fNode = new DefaultMutableTreeNode(
						getToken().getToken());
				node.add(fNode);
				if (element(node))
					count++;
				return count > 0;
			} else {
				backP();
				if (element(node))
					count++;
				return count > 0;
			}
		}
	}

	/**
	 * 元素，int，real，ident，（表达式） element=int/real/ident/(expression)
	 * 
	 * @param s
	 */
	public boolean element(DefaultMutableTreeNode node) {
		int count = 0;
		if (moveP() == false) {

			return false;
		} 
		if (getToken().getTypeString().equalsIgnoreCase("INTERGER")) {
			count++;
			DefaultMutableTreeNode fNode = new DefaultMutableTreeNode(
					getToken().getToken());
			node.add(fNode);
			return true;
		} else if (getToken().getTypeString().equalsIgnoreCase("REAL")) {
			count++;
			DefaultMutableTreeNode fNode = new DefaultMutableTreeNode(
					getToken().getToken());
			node.add(fNode);
			return true;
		} else if (getToken().getTypeString().equalsIgnoreCase("IDENT")) {
			CMMToken idenToken = getToken();
			count++;
			DefaultMutableTreeNode identNode=new DefaultMutableTreeNode(getToken().getToken());
			CMMToken identToken=getToken();
			useIdent(node,identNode,identToken);
			if (moveP() == false) {
				useVar(idenToken, false);
				return true;
			} else {
				CMMToken cmmToken1 = getToken();
				// 是不是数组
				if (getToken().getToken().equals("[")) {
					useVar(idenToken, true);
					backP();
					// 识别数组
					node.remove(identNode);
					DefaultMutableTreeNode arrayNode = new DefaultMutableTreeNode(
							"array");
					node.add(arrayNode);
					arrayNode.add(identNode);
					useArray(arrayNode, getToken());
					return true;
				} else {
					useVar(idenToken, false);
					backP();
					return true;
				}
			}
		} else if (getToken().getToken().equals("(")) {
			expression(node);
			// 找匹配的 ）
			if (moveP() == false) {
				error(getToken(), "缺少 )");
				return true;
			} else if (getToken().getToken().equals(")")) {

				return true;
			} else {
				error(getToken(), "错误：缺少 )");
				return true;
			}

		} else {
			return false;
		}
	}

	// 数组相关的，识别片段，创建数组
	private void createArray(DefaultMutableTreeNode node, CMMToken token) {
		if (moveP() == false) {
			error(getToken(), "应该是'['");
			return;
		}
		if (!(getToken().getToken().equals("["))) {
			error(getToken(), "应该是'['");
			return;
		}
		if (moveP() == false) {
			error(getToken(), "缺少一个整型常量");
			return;
		}
		if (!(getToken().getTypeString().equalsIgnoreCase("INTERGER"))) {
			error(getToken(), "应该是一个整型常量");
			return;
		}
		pushVar(token, 1, true, Integer.parseInt(getToken().getToken()),-1,null,null);
		if (moveP() == false) {
			error(getToken(), "缺少一个']'");
			return;
		}
		if (!(getToken().getToken().equals("]"))) {

			error(getToken(), "应该是一个']'");
			return;
		}
		DefaultMutableTreeNode aNode = new DefaultMutableTreeNode(token
				.getToken());
		DefaultMutableTreeNode bNode = new DefaultMutableTreeNode(
				cmmTokens[p - 1].getToken());
		node.add(aNode);
		node.add(bNode);

		if (moveP() == false) {
			error(getToken(), "缺少一个';'");
			return;
		} else if (getToken().getToken().equals(";")) {

			return;
		} else {
			error(getToken(), "应该是：';'");
		}

	}
	
	/**
	 * 使用标识符，包含函数调用
	 */
	public void useIdent(DefaultMutableTreeNode node,DefaultMutableTreeNode identNode,CMMToken identToken){

		node.add(identNode);
		VarObject vo=null;
		for(int i=0;i<stack.size();i++){
			if(stack.get(i).getVarString().equals(identToken.getToken())) vo=stack.get(i);
		}
		if(vo==null) return;
		if(vo.getType()<10) return;
		
		if(vo.getType()>=10){	//函数调用
			if(moveP()==false){
				error(getToken(), "缺少'('");
				return;
			}
			if(!getToken().getToken().equals("(")){
				error(getToken(), "应该是'('");
				return;
			}
			argConvey(identNode, vo);
			if(moveP()==false){
				error(getToken(), "缺少')'");
				return;
			}
			if(!getToken().getToken().equals(")")){
				error(getToken(), "应该是')'");
				return;
			}
		}
	}
	
	/**
	 * 传递参数部分
	 * @param node
	 * @param token
	 */
	private void argConvey(DefaultMutableTreeNode node,VarObject vo){
		
		int argNum=vo.getArgNum();
		int count=1;//计数
		Vector<VarObject> argList=vo.getArgListVector();
		if(argNum<=0) return;
		for(int i=0;i<argList.size();i++){
			expression(node);
			if(moveP()==false){
				error(getToken(), "缺少')'");
				return;
			}
			if(getToken().getToken().equals(",")) {
				count++;
				continue;
			}
			if(getToken().getToken().equals(")")){
				if(count<argNum){	//参数少于指定个数，报错
					error(getToken(),"参数个数不足！");
				}
				backP();
				
				return;
			}
		}
	}

	// 使用数组
	private void useArray(DefaultMutableTreeNode node, CMMToken token) {
		if (moveP() == false) {
			error(getToken(), "应该是'['");
			return;
		}
		if (!(getToken().getToken().equals("["))) {
			error(getToken(), "应该是'['");
			return;
		}
		if (moveP() == false) {
			error(getToken(), "缺少一个表达式");
			return;
		}
		backP();
		if (!(expression(node))) {

			error(getToken(), "应该是一个整型常量");
			return;
		}
		if (moveP() == false) {
			error(getToken(), "缺少一个']'");
			return;
		} else if (getToken().getToken().equals("]")) {
			// node.add(new DefaultMutableTreeNode("]"));
			return;
		} else {
			error(getToken(), "应该是一个']'");
			return;
		}

	}

	/**
	 * 变量的检查
	 * 
	 * @param token
	 * @param message
	 */
	/**
	 * 添加一个变量,0,不是，1，整型，2，实型
	 */
	public void pushVar(CMMToken token, int type, boolean isArray, int arraySize,int argNum,Vector<VarObject> argList,DefaultMutableTreeNode node) {
		VarObject vo = new VarObject();
		vo.setVarString(token.getToken());
		vo.setType(type);
		vo.setArray(isArray);
		vo.setArraySize(arraySize);
		vo.setArgNum(argNum);
		vo.setArgListVector(argList);
		vo.setNode(node);
		if (stack.hasVo(vo)) {
			error(token, "变量定义重复！");
		} else {
			stack.push(vo);
		}
	}

	/**
	 * 使用一个变量
	 * 
	 * @param token
	 * @param message
	 */
	public void useVar(CMMToken token, boolean isArray) {
		VarObject vo = new VarObject();
		vo.setVarString(token.getToken());
		vo.setArray(isArray);
		if (stack.hasVo(vo)) {
			return;
		} else {
			error(token, "未声明的变量！");
		}
	}

	/**
	 * 弹出变量，直到{,变量的作用域结束
	 * 
	 * @param token
	 * @param message
	 */
	public void popVar() {
		stack.pop();
	}

	/**
	 * 弹出一个变量
	 * 
	 */
	public void popOne() {
		stack.popOne();
	}

	/**
	 * 显示语法树
	 * 
	 * @param token
	 * @param message
	 */
	public void showGramTree() {
		JFrame frame = new JFrame("语法树");
		JScrollPane jScrollPane = new JScrollPane(tree);
		frame.add(jScrollPane);
		frame.setSize(800, 600);
		frame.setVisible(true);
		//		
	}

	//获得语法树
	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}

	private String getNodeName() {
		nodeNum++;
		return "node" + nodeNum;

	}

	// 报错1
	private void error(CMMToken token, String message) {
		hasError = true;
		outputLine("错误：'" + token.getToken() + "'，第" + token.getX() + "行,第"
				+ token.getY() + "列");
		outputLine("	" + message);
		token.setMessageString("错误：'" + token.getToken() + "'，第" + token.getX() + "行,第"
				+ token.getY() + "列"+"。 "+message);
		token.setType(0);
		errorTokens.add(token);
	}

	// 报错2
	private void error(CMMToken token) {
		hasError = true;
		outputLine("错误：'" + token.getToken() + "'，第" + token.getX() + "行,第"
				+ token.getY() + "列");
		token.setMessageString("错误：'" + token.getToken() + "'，第" + token.getX() + "行,第"
				+ token.getY() + "列");
		token.setType(0);
		errorTokens.add(token);
	}

	// 指针移动，自动跳过注释
	private boolean moveP() {
		if (p >= size - 1)
			return false;
		else {
			p++;
			while (true) {
				if (getToken().getTypeString().equalsIgnoreCase("COMMENT")) {
					if (p < size - 1) {
						p++;
						continue;
					} else
						break;
				} else
					break;
			}
			return true;
		}
	}
	//注意，这个可以一直返回到-1
	private boolean backP() {
		if (p <0)	
			return false;
		else {
			p--;
			while (true) {
				if (p>=0&&getToken().getTypeString().equalsIgnoreCase("COMMENT")) {
					if (p >=0) {
						p--;
						continue;
					} else
						break;
				} else
					break;
			}
			return true;
		}
	}

	//返回当前指针出的token
	private CMMToken getToken() {
		return cmmTokens[p];
	}

	//输出,可以在此方便的重定向输出
	public void outputLine(String s) {
		outputString += (s + "\n");
	}

	public void output(String s) {
		outputString += (s);
	}

	public String getOutputString() {
		return outputString;
	}

	public boolean hasError() {
		return hasError;
	}

	public ArrayList<CMMToken> getErrorTokens() {
		return errorTokens;
	}
}
