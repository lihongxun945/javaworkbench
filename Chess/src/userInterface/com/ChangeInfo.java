package userInterface.com;

import javax.swing.*;
import java.awt.*;

import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class ChangeInfo  extends JFrame {
	
	private int scWidth=Toolkit.getDefaultToolkit().getScreenSize().width;
	private int scHeight=Toolkit.getDefaultToolkit().getScreenSize().height;
	private Color brown = new Color(142,78,30);
	
	
	JPanel jpMain ;

//	四个label， 修改昵称，修改密码， 确认密码，修改头像；
	JLabel jlName,jlPassWord,jlPassWordConfirm,jlHeadImage;
	
//	JTextField, 输入昵称
	JTextField jtfName;
	
//	JPasswordField 输入密码，输入密码（确认），
	JPasswordField jpfPassword,jpfPasswordConfirm;
	
//	一个选择框 选择头像
	JComboBox jcbHeadImage;
	
//	两个功能按钮， 确认 & 取消
	JButton jbtOK,jbtCancel;
	JLabel jlOK, jlCancel;
	
//	构造方法
	public ChangeInfo () {
//		设置Frame 的大小，title, 
		this.setBounds((scWidth-400)/2,(scHeight-500)/3, 400, 480);
		this.setTitle("修改个人资料");
		this.setIconImage(new ImageIcon("image/LOGO.png").getImage());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
//		jpMain 初始化
		jpMain = new JpMain();
		
		jpMain.setLayout(null);
		jpMain.setBounds(0, 0, 400, 500);
		
//		jpMain的各部件
//		JLabel  jlName  修改昵称
		jlName =  new JLabel ("修改昵称",JLabel.RIGHT);
		jlName.setFont(new Font("微软雅黑",1,24));
		jlName.setBounds(30,30,130,40);
//		jlName.setBorder(new LineBorder(Color.BLACK,3));
		
		
//		JLabel jlPassWord  修改密码
		jlPassWord = new JLabel ("修改密码",JLabel.RIGHT);
		jlPassWord.setFont(new Font("微软雅黑",1,24));
		jlPassWord.setBounds(30,90,130,40);
//		jlPassWord.setBorder(new LineBorder(Color.BLACK,3));
		
//		JLabel jlPassWordConfirm 确认密码
		jlPassWordConfirm = new JLabel ("确认密码",JLabel.RIGHT);
		jlPassWordConfirm.setFont(new Font("微软雅黑",1,24));
		jlPassWordConfirm.setBounds(30,150,130,40);
//		jlPassWordConfirm.setBorder(new LineBorder(Color.BLACK,3));
		
//		JLabel jlHeadImage 修改头像
		jlHeadImage = new JLabel ("修改头像",JLabel.RIGHT);
		jlHeadImage.setFont(new Font("微软雅黑",1,24));
		jlHeadImage.setBounds(30,250,130,40);
//		jlHeadImage.setBorder(new LineBorder(Color.BLACK,3));
		
//		JTextFied jtfName
		jtfName = new JTextField();
		jtfName.setFont(new Font("微软雅黑",1,16));
		jtfName.setBounds(180, 30, 170, 40);
		jtfName.setOpaque(false);
		jtfName.setBorder(new LineBorder(Color.BLACK,3));
		
//		JPasswordField jpfPassWord
		jpfPassword = new JPasswordField();
		jpfPassword.setFont(new Font("微软雅黑",1,16));
		jpfPassword.setOpaque(false);
		jpfPassword.setEchoChar('*');
		jpfPassword.setBounds(180,90,170,40);
		jpfPassword.setBorder(new LineBorder(Color.BLACK,3));
		
//		JPasswordField jpfPasswordConfirm
		jpfPasswordConfirm = new JPasswordField();
		jpfPasswordConfirm.setFont(new Font("微软雅黑",1,16));
		jpfPasswordConfirm.setOpaque(false);
		jpfPasswordConfirm.setEchoChar('*');
		jpfPasswordConfirm.setBounds(180,150,170,40);
		jpfPasswordConfirm.setBorder(new LineBorder(Color.BLACK,3));
		
//		头像，用数组存储, 相应的用String数组存储他们的路径
		ImageIcon [] headImage =new ImageIcon [22];
		String [] headImagePath = new String[22];
		for(int i=0;i<22;i++){
			headImagePath [i]="image/headImage/"+i+".png";
			headImage[i]= new ImageIcon(headImagePath[i]);
		}
//		JComboBox jcbHeadImage
		jcbHeadImage = new JComboBox(headImage);
		jcbHeadImage.setBounds(180, 210, 170, 128);
//		jcbHeadImage.setOpaque(false);
		jcbHeadImage.setBorder(null);
		
		jbtOK = new JButton(new ImageIcon("image/ok.gif"));
		jbtOK.setBounds(120, 355, 50, 50);
		jbtOK.setOpaque(false);
		jbtOK.setContentAreaFilled(false);
//		jbtOK.addActionListener(new OKListener());
		jbtOK.setBorder(null);
		
		jbtCancel = new JButton(new ImageIcon("image/Cancel.gif"));
		jbtCancel.setBounds(230, 355, 50, 50);
		jbtCancel.addActionListener(new CancelListener());
		jbtCancel.setOpaque(false);
		jbtCancel.setContentAreaFilled(false);
//		jbtCancel(new OKListener());
		jbtCancel.setBorder(null);

		jlOK = new JLabel("确定");
		jlOK.setFont(new Font("微软雅黑",1,16));
		jlOK.setBounds(130, 405, 50, 30);
		
		jlCancel = new JLabel("取消");
		jlCancel.setFont(new Font("微软雅黑",1,16));
		jlCancel.setBounds(235, 405, 50, 30);
		
		
//		jpMain 添加各部件
		jpMain.add(jlName);
		jpMain.add(jlPassWord);
		jpMain.add(jlPassWordConfirm);
		jpMain.add(jlHeadImage);
		jpMain.add(jtfName);
		jpMain.add(jpfPassword);
		jpMain.add(jpfPasswordConfirm);
		jpMain.add(jcbHeadImage);
		jpMain.add(jbtOK);
		jpMain.add(jbtCancel);
		jpMain.add(jlOK);
		jpMain.add(jlCancel);
		
		this.add(jpMain);
		this.setVisible(true);
		
	}


	
//	OK 按钮 监听
	public class OKListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null, "修改成功");
		}
	}
	
//	Cancel 按钮 监听
	public class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	}
	
//	主面板 jpMain 
	class JpMain extends JPanel {
		public JpMain(){
			super();
		}
//		重写paint方法， 加 背景
		public void paintComponent (Graphics g){
			g.drawImage(new ImageIcon("image/changeinfobackground.jpg").getImage(), 0, 0	, 400, 480, this);
		}
	}
	
	
	
	
	
	
	
//	测试方法
	public static void main(String [] args){
		new ChangeInfo();
	}
}
