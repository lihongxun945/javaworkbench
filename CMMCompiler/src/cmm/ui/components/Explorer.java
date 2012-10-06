package cmm.ui.components;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import cmm.ui.listeners.ExplorerListener;
/**
 * 资源管理器,部分参考了网上的代码
 * @author axun
 *
 */
public class Explorer extends JPanel {
    public Explorer(File dir) throws HeadlessException {
        JTree tree=new JTree(buildTreeModel(dir));
        tree.setCellRenderer(new FileTreeRenderer());
        //this.setPreferredSize(new Dimension(200,100));
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(tree),BorderLayout.CENTER);
        //tree.setToggleClickCount(2);
        tree.addMouseListener(new ExplorerListener(tree));
    }

    private TreeModel buildTreeModel(File dir){
        MyTreeNode root = new MyTreeNode(dir);
        walkthrough(dir,root);
        return new DefaultTreeModel(root);
    }

    private static void walkthrough(File f,MyTreeNode node){
        for (File file : f.listFiles()) {
        	
            MyTreeNode n = new MyTreeNode(file);
            node.add(n);
            if (!file.isDirectory()){
               n.setFile(file); 
            }else{
            	walkthrough(file, n);
            }
        }
    }

    private class FileTreeRenderer extends DefaultTreeCellRenderer  {
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            JLabel cmp = (JLabel)super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            if (value instanceof MyTreeNode) {
                MyTreeNode n = (MyTreeNode)value;
                Object obj = n.getUserObject();
                if (obj instanceof File) {
                    File f = (File)obj;
                    cmp.setText(f.getName());
                    cmp.setForeground(f.isDirectory()?Color.BLUE:Color.BLACK);
                }
            }
            return cmp;
        }
    }
    
  
}
  