package cmm.client;

import cmm.functions.Files;
import cmm.ui.MainFrame;

public class CMMClient {
	public static MainFrame mainFrame;
	public static void main(String[] args) {
		mainFrame=new MainFrame();
		new Files().createFile();	//先创建一个文件
	}
}
