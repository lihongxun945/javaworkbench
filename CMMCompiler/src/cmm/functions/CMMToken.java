package cmm.functions;
/*
 * 词法分析
 */
public class CMMToken {
	private String tokenString="null";
	private int type=0;	//0 错误，1 整型常量，2 实数常量 3 标识符,4 特殊字符 5,关键字,6 注释，7，字符串
	private int x=0;	//行号，从1开始
	private int y=0;	//列号，从1开始
	private int charIndex=0;	//
	private String messageString="无";
	
	public CMMToken(){
		
	}
	
	public CMMToken(String tokenString,int type,int x,int y,int charIndex){
		this.tokenString=tokenString;
		this.type=type;
		this.x=x;
		this.y=y;
		this.charIndex=charIndex;
	}

	public String getToken() {
		return tokenString;
	}

	public void setToken(String token) {
		this.tokenString = token;
	}

	public int getType() {
		return type;
	}
	
	public String getTypeString() {
		switch(type){
		case 0:return "ERROR";
		case 1:return "INTERGER";
		case 2:return "REAL";
		case 3:return "IDENT";
		case 4:return "SPECIALCHAR";
		case 5:return "KEYWORD";
		case 6:return "COMMENT";
		case 7:return "STRING";
		default:return "EORRO";
		}
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCharIndex() {
		return charIndex;
	}

	public void setCharIndex(int charIndex) {
		this.charIndex = charIndex;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}
}
