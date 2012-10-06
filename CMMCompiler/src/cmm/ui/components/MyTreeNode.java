package cmm.ui.components;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

/**
     * 自定义节点类，保存对应文件
     * @author axun
     *
     */
    public class MyTreeNode extends DefaultMutableTreeNode{
		private File file=null;
    	
    	public MyTreeNode(Object object){
    		super(object);
    	}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}
    	
    	
    }