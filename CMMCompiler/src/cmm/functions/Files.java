package cmm.functions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cmm.staticValues.Values;
import cmm.ui.MainFrame;
import cmm.ui.components.ConfirmDialog;
import cmm.ui.components.SaveFileWindow;

public class Files {
	FileInputStream fin = null;
	FileOutputStream fout=null;
	BufferedInputStream bin=null;
	BufferedOutputStream bout=null;
	String fileContent="";
	File file=null;
	//打开文件
	public void openFile(File file){
		Values.setFileContent(new Files().getFileContent(file));
		Values.addFile(file);
		
	}
	
	/**关闭当前选中的文件
	 * 
	 */
	public void closeFile(){
		int index=MainFrame.textPane.getIndex();
		Values.deleteFile(index);
		MainFrame.textPane.delete(index);
	}
	
	/**
	 * 可以读取文件内容
	 * @param file
	 * @return
	 */
	public String getFileContent(File file){
		this.file=file;
		if(file==null) return null;
		int b;
		try{
			fin=new FileInputStream(file);
		}catch(FileNotFoundException e){
			System.out.println("File not found!");
		}
		bin=new BufferedInputStream(fin);
		try{
			while((b=bin.read())!=-1){
				fileContent+=((char)b);
			}
		}catch(IOException e){
			System.out.println("file read Error!");
			
		}
		try{
			bin.close();
		}catch(IOException e){
			System.out.println("file close error!");
		}
		return fileContent;
	}
	
	/**
	 * 比较两个文件是否相同
	 * @param file1
	 * @param file2
	 * @return
	 */
	public boolean compareFile(File file1,File file2){
		if(file1==null||file2==null)return false;
		if(!file1.getName().equals(file2.getName())) return false;	//文件名不同，返回false
		FileInputStream fis1=null ;
		FileInputStream fis2 =null;
		try {
			fis1 = new FileInputStream(file1);
			fis2 = new FileInputStream(file2);
			
			int len1 = fis1.available();
			int len2 = fis2.available();
			
			if (len1 == len2) {//长度相同，则比较具体内容
				//建立两个字节缓冲区
				byte[] data1 = new byte[len1];
				byte[] data2 = new byte[len2];
				
				//分别将两个文件的内容读入缓冲区
				fis1.read(data1);
				fis2.read(data2);
				
				//依次比较文件中的每一个字节
				for (int i=0; i<len1; i++) {
					//只要有一个字节不同，两个文件就不一样
					if (data1[i] != data2[i]) {
						//System.out.println("文件内容不一样");
						return false;
					}
				}
				
				//System.out.println("两个文件完全相同");
				return true;
			} else {
				//长度不一样，文件肯定不同
				return false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {//关闭文件流，防止内存泄漏
			if (fis1 != null) {
				try {
					fis1.close();
				} catch (IOException e) {
					//忽略
					e.printStackTrace();
				}
			}
			if (fis2 != null) {
				try {
					fis2.close();
				} catch (IOException e) {
					//忽略
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	
	//保存文件
	public void saveFile(){
		//保存内容
		String string=MainFrame.textPane.getInputString();
		boolean ifnull=(Values.getFile()==null);
		File newFile=null;
		if(ifnull){	//如果不是已经打开了文件
			SaveFileWindow saveFileWindow=new SaveFileWindow();
			if(saveFileWindow.ifChosed()){
				newFile=new File(saveFileWindow.getFile().getAbsolutePath());
				
			}else{
				return;
			}
		}
		if(ifnull) this.file=newFile;
		else this.file=Values.getFile();
		System.out.println(file.getAbsolutePath());
		int b;
		try{
			fout=new FileOutputStream(file.getAbsolutePath());
		}catch(FileNotFoundException e){
			System.out.println("File not found!");
			e.printStackTrace();
		}
		
		bout=new BufferedOutputStream(fout);
		try{
			for(int i=0;i<string.length();i++){
				bout.write((int)string.charAt(i));
			}
		}catch(IOException e){
			System.out.println("file write Error!");
		}
		try{
			bout.close();
		}catch(IOException e){
			System.out.println("file close error!");
		}
		
		//如果是新建的文件，关闭以前的，打开现在的
		if(ifnull){
			MainFrame.textPane.deleteNow();
			openFile(file);
		}
		if(!ifnull){
			new ConfirmDialog("保存成功！");
		}
	}
	
	/**
	 * 创建新文件,新文件是null
	 */
	public void createFile(){
		Values.addFile(null);
	}
}
