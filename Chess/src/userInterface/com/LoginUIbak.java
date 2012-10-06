package userInterface.com;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JPanel;


public class LoginUIbak {
	//GUI
	public Frame j=new Frame("Îå×ÓÆåµÇÂ½");
	public JButton exitbt=new JButton("ÍË³ö");
	public JButton loginbt=new JButton("µÇÂ½");
	public TextField userIdtf=new TextField("");
	public TextField passwordtf=new TextField("");
	public TextField serverIptf=new TextField("localhost");

	
	
	
	public LoginUIbak(){
		j.setSize(400, 300);
		j.setLayout(new BorderLayout());
		j.add(exitbt,"West");
		j.add(loginbt,"East");
		
		JPanel p1=new JPanel();
		
		j.add(p1,"Center");
		p1.setLayout(new GridLayout(4,1));
		
		p1.add(userIdtf);
		p1.add(passwordtf);
		p1.add(serverIptf);
		
		
		j.setVisible(true);
	}
}
