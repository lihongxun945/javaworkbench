package cmm.ui.components;


/**
 * 保存文件
 */
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import cmm.staticValues.Values;


public class SaveFileWindow {
	private File file=null;
	private boolean chosed=false;
	
	public SaveFileWindow(){
		JFileChooser chooser=new JFileChooser(Values.getDefaultWorkBench()); 
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
		
		CMMFileFilter filter=new CMMFileFilter();
		chooser.setFileFilter(filter);
		int result=chooser.showSaveDialog(null); 
		if(result==JFileChooser.APPROVE_OPTION){ 
			chosed=true;
			file=chooser.getSelectedFile();
			System.out.println(file);
			
		}
	}
	//是否打开了文件
	public boolean ifChosed(){
		return chosed;
	}
	
	//返回选择的文件
	public File getFile(){
		return file;
	}

}