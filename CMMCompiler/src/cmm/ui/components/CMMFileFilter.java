package cmm.ui.components;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class CMMFileFilter extends FileFilter{

	@Override
	public boolean accept(File f) {
		String path=f.getPath().toLowerCase();
		if(f.isDirectory()) return true;	//
		if(path.endsWith("txt")) return true;
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "txt";
	}
	
}
