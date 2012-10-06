package cmm.functions;


public class WordParse {
	private String in="";
	private String temp="";
	private int type=0;
	private String outputString="";
	private String[] inLine=new String[1000];		//分行存储
	private int lineCount=0;						//输入了多少行
	private int linePointer=0;						//行号
	private int charIndex=0;						//第几个字符，不算\r
	private String currentLine="";					//当前行的内容
	private int p=0;								//当前行的第几个字符
	private CMMToken[] cmmTokens=new CMMToken[2000];	//正确的字符
	private CMMToken[] errorCMMTokens=new CMMToken[1000];	//错误的字符
	private int errorPointer=0;	//错误字符的存储指针
	private int tokenPointer=0;
	private boolean hasError=false;	//有没有错误
	
	public WordParse(String in){
		this.in=in;
		for(int i=0;i<100;i++){
		}
	}
	
	//开始分析
	public void start(){
		tokenPointer=0;
		errorPointer=0;
		intoLines();	//逐行拆开
		linePointer=0;
		while(linePointer<lineCount){
			outputLine("第"+(linePointer+1)+"行:");
			LineAnalyse();
			linePointer++;
		}
	}
	
	//开始分析一行
	private void LineAnalyse(){
		currentLine=inLine[linePointer];
		p=-1;	//尚未开始
		
		TokenAnalyse();
	}
	
	//开始分析一个token
	private void TokenAnalyse(){
		temp="";
		char c=' ';
		if(moveP()==false) return;
		c=getChar();
		if(isNum(c)) {
			intTest();
		}else if(c=='"') {
			stringTest();
		}
		else if(isLetter(c)||c=='_') {
			identTest();
		}else if(c=='>'||c=='<'||c=='=') {
			doubleSCharTest();
		}else if(isSpecialChar(c)) {
			singleSCharTest();
		}else if(isSpace(c)){
			TokenAnalyse();
		}
		else{
			errorOutput(c);	//总是把每一个错误作为下一个字符的开始来报错
		}
		TokenAnalyse();	//总是自己循环
		
	}
	
	//整型
	private void intTest(){
		charIndex=p;
		char c=' ';
		if(moveP()==false) {
			intEnd();
			return;
		}
		c=getChar();
		if(c=='.'){
			realTest();
			return;
		}
		else if(isSpace(c)){
			intEnd();
			return;
		}else if(isSpecialChar(c)){
			backP();
			intEnd();
			return;
		}else if(isNum(c)){
			intTest();
			return;
		}
		else {
			backP();
			intEnd();
			return;
		}
		
	}
	
	private void intEnd(){
		type=1;
		saveToken();
		return;
	}
	
	//实型
	private void realTest(){
		charIndex=p;
		char c=' ';
		if(moveP()==false) {
			realEnd();
			return;
		}
		c=getChar();
		if(isSpace(c)){
			realEnd();
			return;
		}else if(isSpecialChar(c)){
			backP();
			realEnd();
			return;
		}else if(isNum(c)){
			realTest();
			return;
		}
		else {
			backP();
			realEnd();
			return;
		}
		
	}
	
	private void realEnd(){
		type=2;
		saveToken();
		return;
	}
	
	//标识符
	private void identTest(){
		charIndex=p;
		char c=' ';
		if(moveP()==false) {
			identEnd();
			return;
		}
		c=getChar();
		if(isSpace(c)){
			identEnd();
			return;
		}else if(isSpecialChar(c)){
			backP();
			identEnd();
			return;
		}else if(isNum(c)||isLetter(c)||c=='_'){
			identTest();
			return;
		}
		else {
			backP();
			identEnd();
			return;
		}
	}
	
	private void identEnd(){
		keywordTest();
	}
	
	//字符串
	private void stringTest(){	
			type= 7;
			
			boolean end=false;
			int i;
			for(i=p+1;i<currentLine.length();i++){
				if(currentLine.charAt(i)=='"'){
					end=true;
					break;
				}
			}
			if(end){
				temp+=currentLine.substring(p+1,i+1);
				p=i;
				charIndex=p;
				saveToken();
				return;	//只有一行
			}else{
				temp+=currentLine.substring(p+1);
				saveToken();
			}
			
			
	}
	
	//单个特殊字符
	private void singleSCharTest(){
		charIndex=p;
		if(getCurrentChar()=='/'){	//注释
			char c=' ';
			if(moveP()==false) {
				identEnd();
				return;
			}
			
			c=getChar();
			if(c=='/'){
				singleLineComment();
				return;
			}else if(c=='*'){
				mutiLineComment();
				return;
			}
			else{
				backP();
			}
		}

		singleSCharEnd();
		return;
	}
	private void singleSCharEnd(){
		type=4;
		saveToken();
		return;
	}
	
	//双特殊字符
	private void doubleSCharTest(){
		charIndex=p+1;		//注意此处不同
		char c=' ';
		if(moveP()==false) {
			doubleSCharEnd();
			return;
		}
		c=getChar();
		if(c=='='){
			doubleSCharEnd();
			return;
		}else if(temp.equals("<")&&c=='>'){
			doubleSCharEnd();
			return;
		}
		else{
			backP();
			singleSCharEnd();
			return;
		}
	}
	private void doubleSCharEnd(){
		type=4;
		saveToken();
		return;
	}
	
	//关键字
	private void keywordTest(){
		if(isKeyWord(temp)){
			keywordEnd();
			return;
		}
		else{
			type=3;
			saveToken();
			return;
		}
	}
	private void keywordEnd(){
		type=5;
		saveToken();
		return;
	}
	
	//单行注释
	private void singleLineComment(){
		charIndex=inLine[linePointer].length()-1;	//注意这个和其他的不一样
		temp+=currentLine.substring(p+1);
		p=currentLine.length();	//指针指向末尾
		type=6;
		saveToken();
		return;
	}
	
	//多行注释
	private void  mutiLineComment(){
		
		type= 6;

		if(currentLine.indexOf("*/")!=-1)	{
			temp+=inLine[linePointer].substring(p+1,currentLine.indexOf("*/")+2);
			p=currentLine.indexOf("*/");
			charIndex=p+1;
			saveToken();
			return;	//只有一行
		}else{
			temp+=inLine[linePointer].substring(p+1);
		}
		while(true){
			p=0;
			linePointer++;
			if(linePointer>=lineCount){
				outputLine("错误：注释未结束");
				
				return;
			}
			currentLine=inLine[linePointer];
			if(currentLine.indexOf("*/")!=-1){
				temp+=currentLine.substring(0,currentLine.indexOf("*/")+2);
				p=currentLine.indexOf("*/")+1;
				charIndex=p;
				saveToken();
				return;
			}else {
				temp+=currentLine;
				continue;
			}
			
		}
		
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//存储token
	private void saveToken(){
		for(int i=0;i<linePointer;i++){
			charIndex+=inLine[i].length();
		}
		
		charIndex=charIndex-temp.length()+1;
		
		cmmTokens[tokenPointer]=new CMMToken(temp,type,linePointer+1,p+1,charIndex+1+linePointer);
		outputLine(cmmTokens[tokenPointer].getToken()+"  :  "+cmmTokens[tokenPointer].getTypeString()
				+"("+cmmTokens[tokenPointer].getCharIndex()+")");

		tokenPointer++;
	}
	
	//输出错误
	private void errorOutput(char c){
		charIndex=p;
		hasError=true;
		
		outputLine("错误字符：   '"+c+"'    位置：第"+(linePointer+1)+"行，"+p+"列");

		for(int i=0;i<linePointer;i++){
			charIndex+=inLine[i].length();
		}

		charIndex=charIndex-temp.length()+1;
		CMMToken errToken=new CMMToken(currentLine.charAt(p)+"",0,linePointer+1,p+1,charIndex+1);
		errToken.setMessageString("错误字符：   '"+c+"'    位置：第"+(linePointer+1)+"行，"+p+"列");
		errorCMMTokens[errorPointer]=errToken;
		errorPointer++;
	}
	
	//把输入字符串按行拆开
	private void intoLines(){
		inLine=in.split("\n");
		lineCount=inLine.length;

		outputLine("总共："+lineCount+"行");
	}
	//指针向下移动一位
	private boolean moveP(){
		p++;
		
		if(p>=currentLine.length()) return false;
		else {
			return true;
		}
	}
	//获取一个字符 同时存储,不存储空格
	private char getChar(){
		if(!isSpace(currentLine.charAt(p))){
			temp+=currentLine.charAt(p);
		}
		return currentLine.charAt(p);
	}
	//获取当前字符
	private char getCurrentChar(){
		return currentLine.charAt(p);
	}
	
	//指针返回
	private boolean backP(){
		p--;

		if(p<0) {
			return false;
		}
		else {
			/*
			 * !!!!!!!!!!!
			 * 注意此处，不能直接temp减去最后一个，因为有可能本来要加的是一个空格，结果被略过去了，然后把空格前面的字符给删了
			 */
			if(!isSpace(currentLine.charAt(p+1))){
				temp=temp.substring(0, temp.length()-1);
				
			}return true;
		}
	}
	
	
	//输出,可以在此方便的重定向输出
	public void outputLine(String s){
		outputString+=(s+"\n");
	}
	
	public void output(String s){
		outputString+=(s);
	}
	
	//获取输出
	public String getOutputString(){
		return outputString;
	}
	
	//是否是数字
	public boolean isNum(char c){
		if(c>=48&&c<=57) return true;
		else return false;
	}
	
	//是否是字母	a-z 97-122 ,A-Z 65-90
	public boolean isLetter(char c){
		if((c>=97&&c<=122)||(c>=65&&c<=90)) return true;
		else return false;
	}
	
	//是否是空白符
	public boolean isSpace(char c){
		if(c=='\n'||c==' '||c=='\t'||c=='\r') return true;
		else return false;
	}
	
	//是否是特殊字符
	private boolean isSpecialChar(char c){
		if(c==';'||c=='{'||c=='}'||c=='['||c==']'||c=='('||c==')'||c=='>'||c=='<'||c=='='||c=='+'||c=='-'||c=='*'||c=='/'||c==',')
			return true;
		else return false;
	}
	
	//是否是标识符
	private boolean isKeyWord(String s){
		if(s.equals("if")||s.equals("else")||s.equals("while")||s.equals("int")||s.equals("real")||s.equals("string")||s.equals("read")||s.equals("write")||s.equals("writes")||s.equals("void")||s.equals("return")) {
			return true;
		}
		else {
			return false;
		}
	}

	public CMMToken[] getCmmTokens() {
		return cmmTokens;
	}
	
	public int getTokenAmount(){
		return tokenPointer;
	}

	public CMMToken[] getErrorCMMTokens() {
		return errorCMMTokens;
	}
	public int getErrorTokenAmount(){
		return errorPointer;
	}
	
	public boolean hasError(){
		return hasError;
	}
}
