package cmm.staticValues;

import java.io.File;
import java.util.Vector;


import cmm.functions.Files;
import cmm.ui.MainFrame;

public class Values {
	private static int MainFrameWidth=1000;
	private static int MainFrameHeight=700;
	private static File file=null;	//当前操作的文件
	private static Vector<File> files=new Vector<File>();	//所有已经打开的文件
	private static String fileContentString="";
	private static String defaultWorkBench="/Users/lihongxun/Documents";	//默认工作目录
	private static double min=0.000001;	
	
	public static int getMainFrameWidth() {
		return MainFrameWidth;
	}
	public static void setMainFrameWidth(int mainFrameWidth) {
		MainFrameWidth = mainFrameWidth;
	}
	public static int getMainFrameHeight() {
		return MainFrameHeight;
	}
	public static void setMainFrameHeight(int mainFrameHeight) {
		MainFrameHeight = mainFrameHeight;
	}
	public static File getFile() {
		return file;
	}
	public static void addFile(File f) {
		if(file==null){	//表示新建文件
			file = f;
			files.add(f);
			MainFrame.textPane.add(f);
			return ;
		}
		/**
		 * 对于已经打开的文件，直接定位到此文件，不再打开一次
		 */
		if(files.size()>0){
			for(int i=0;i<files.size();i++){
				if(new Files().compareFile(files.get(i),f)){
					Values.selectFile(i);
					MainFrame.textPane.selectTab(i);
					return;
				}
			}
		}
		file = f;
		files.add(f);
		MainFrame.textPane.add(file);
		
	}
	public static String getFileContent(){
		return fileContentString;
	}
	public static void setFileContent(String file){
		fileContentString=file;
	}
	public static void deleteFile(int index){
		files.remove(index);
	}
	/**
	 * 选择某一个文件为当前文件
	 * @param index
	 */
	public static void selectFile(int index){
		if(index==-1){	//全部关闭了
			file=null;
			fileContentString=null;
			return;
		}
		file=files.get(index);
		fileContentString=new Files().getFileContent(file);
	}
	
	public static Vector<File> getFiles() {
		return files;
	}
	public static String getDefaultWorkBench() {
		return defaultWorkBench;
	}
	public static void setDefaultWorkBench(String defaultWorkBench) {
		Values.defaultWorkBench = defaultWorkBench;
	}
	public static double getMin() {
		return min;
	}
	public static void setMin(double min) {
		Values.min = min;
	}
	
}
