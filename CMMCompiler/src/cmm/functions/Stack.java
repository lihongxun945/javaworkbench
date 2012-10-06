package cmm.functions;

import javax.print.attribute.standard.OutputDeviceAssigned;

/*
 * 实现堆栈功能
 */
public class Stack {
	private int size=0;	//堆栈大小
	private VarObject[] stack=null;	//堆栈，用数组实现，存储的是cmmtoken
	private int p=-1;	//堆栈指针,-1表示没有
	
	public Stack(){
		this.size=100;	//默认100
		initialize();
	}
	
	public Stack(int size){
		this.size=size;
		initialize();
	}
	
	//初始化
	private void initialize(){
		this.stack=new VarObject[size];
		p=-1;
	}
	
	//弹出，直到{
	public void pop(){
		if(p==-1) return;
		else{
			while(true){
				if(stack[p].getVarString().equals("{")) {
					p--;
					break;
				}
				p--;
				if(p<0) break;
			}
		}
	}
	//仅弹出一个
	public void popOne(){
		if(p==-1) return;
		p--;
	}
	
	//压入
	public boolean push(VarObject vo){
		if(p>=size) return false;
		p++;
		stack[p]=vo;
		return true;
	}
	
	//检测当前stack中是否有某个vo,不检查{
	public boolean hasVo(VarObject vo){
		for(int i=0;i<=p;i++){
			if(stack[i].getVarString().equals("{")) continue;
			if(vo.isArray()){
				if(stack[i].getVarString().equals(vo.getVarString())&&stack[i].isArray()) return true;
			}else{
				if(stack[i].getVarString().equals(vo.getVarString())) return true;
			}
		}
		return false;
	}
	
	//
	public VarObject get(int index){
		if(index>p) return null;
		return stack[index];
	}
	
	public int size(){
		return p+1;
	}
}
