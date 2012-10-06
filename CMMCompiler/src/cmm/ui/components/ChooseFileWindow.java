package cmm.ui.components;


/**
 * 打开文件
 */
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;


public class ChooseFileWindow {
	private File file=null;
	private boolean chosed=false;
	
	public ChooseFileWindow(int type){
		JFileChooser chooser=new JFileChooser(); 
		chooser.setFileSelectionMode(type); 
		
		
		CMMFileFilter filter=new CMMFileFilter();
		chooser.setFileFilter(filter);
		int result=chooser.showOpenDialog(null); 
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