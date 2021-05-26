import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

class MyFrame extends JFrame {
	
	//variables for menu bar
	JMenuBar menuBar;
	JMenu menu1;
	JMenu menu2;
	JMenu menu3;
	JMenuItem fPathItem;
	JMenuItem fBuildItem;
	JMenuItem ExitItem;
	
	//variable campus_img
	JLabel label_img;
	
	//variable userPosition & targetPosition
	Position myPos = new Position(0, 0);
	Position targetPos = new Position(0, 0);
	
	MyFrame() {
		//setLayout(null);
		CreateMenu();
		
		setTitle("Campus Map");
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("name: ");
		JTextField textField = new JTextField(20);
		JButton button = new JButton("send");
		
		label_img = new JLabel();

		ImageIcon icon = new ImageIcon("campus.jpg");
		Image img = icon.getImage();
		Image changeimg = img.getScaledInstance(600, 480, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeimg);
		label_img.setIcon(changeIcon);
		
		panel.setBackground(Color.WHITE);
		panel.add(label);
		panel.add(textField);
		panel.add(button);
		panel.add(label_img);
		// Content Pane: Space for attaching component or container linked to frame
		Container c = getContentPane(); 
		c.add(panel); // add panel in frame 
		
		pack(); // to combine
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		setVisible(true);
	}

	//Making MenuBar
	private void CreateMenu() {
		menuBar = new JMenuBar();
		//[menu1_find path]
		menu1 = new JMenu("Find Path");
		menuBar.add(menu1);
		//[menu2_find building]
		menu2 = new JMenu("Find Building");
		menuBar.add(menu2);
		//[menu3_EXIT]
		menu3 = new JMenu("EXIT");
		menuBar.add(menu3);
		
		
		fPathItem = new JMenuItem("Find Path");
		fBuildItem = new JMenuItem("Find Building");
		ExitItem = new JMenuItem("EXIT");
		menu.add(fPathItem);
		menu.add(fBuildItem);
		menu.add(ExitItem);
		menuBar.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		TestListenr listener = new TestListenr();
		ExitItem.addActionListener(listener);
		fPathItem.addActionListener(listener);
		fBuildItem.addActionListener(listener);
		
		setJMenuBar(menuBar);
	}
	
	//筌띾뜆�뒭占쎈뮞嚥∽옙 Position 占쎌뿯占쎌젾 獄쏆룆�뮉 占쎈맙占쎈땾
	class MouseMotionAdapter implements MouseListener, MouseMotionListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(myPos.x == 0) { //占쎄땀 占쎌맄燁살꼶占쏙옙 獄쏆룇占� 占쎌읅占쎌뵠 占쎈씨占쎌몵筌롳옙 筌띾뜆�뒭占쎈뮞 占쎌뿯占쎌젾占쏙옙 myPos
				myPos.x = e.getX();
				myPos.y = e.getY();
			}
			else { //占쎄땀 占쎌맄燁삼옙 占쎌뿯占쎌젾 獄쏆룇釉�占쎈�占쎌몵筌롳옙 筌띾뜆�뒭占쎈뮞 占쎌뿯占쎌젾占쏙옙 targetPos
				targetPos.x = e.getX();
				targetPos.y = e.getY();
			}
			//System.out.println(e.getX());
		}
		@Override
	    public void mouseEntered(MouseEvent e) {
	    }
		@Override
	    public void mousePressed(MouseEvent e) {      
	    }
		@Override
	    public void mouseReleased(MouseEvent e) {      
	    }
		@Override
		public void mouseDragged(MouseEvent e) {    
	    }
		@Override
		public void mouseExited(MouseEvent e) {      
	    }
		@Override
        public void mouseMoved(MouseEvent e) {
        }
	}
	/*3揶쏉옙筌욑옙 筌롫뗀�뤀 揶쏄낫而� function 占쎈땾占쎈뻬
	1. Exit 筌롫뗀�뤀: 占쎄돌揶쏉옙疫뀐옙
	2. Find Path: 筌띾뜆�뒭占쎈뮞 占쎌뿯占쎌젾 2甕곤옙(占쎌겱占쎌맄燁삼옙, 占쏙옙野껓옙 占쎌맄燁삼옙) 占쎌뜎 path 筌≪뼐由�
	3. Find Building: 筌띾뜆�뒭占쎈뮞 占쎌뿯占쎌젾 1甕곤옙(占쎌겱占쎌맄燁삼옙), 占쎄텕癰귣�諭� 占쎌뿯占쎌젾 1甕곤옙(占쎌벥占쎈즲) 占쎌뜎 path 筌≪뼐由�
	*/
	class TestListenr implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == ExitItem) {
				System.exit(1);
			}
			else if(event.getSource() == fPathItem) {
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				//TODO: targetPos占쎈뮉 揶쏉옙占쎌삢 揶쏉옙繹먮슣�뒲 占쎈걗占쎈굡嚥∽옙 癰귨옙野껓옙
				FindPath();
			}
			else if(event.getSource() == fBuildItem) {
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				FindBuilding();
			}
		}
		void FindPath() {
			//TODO : 1)占쎌겱占쎌맄燁삼옙, 占쏙옙野껋옕�맄燁살꼷肉됵옙苑� 揶쏉옙占쎌삢 揶쏉옙繹먮슣�뒲 占쎈걗占쎈굡 筌≪뼐由� 2)findShortestPath()
		}
		
		void FindBuilding() {
			//TODO : 1)占쎌겱占쎌맄燁살꼷肉됵옙苑� 揶쏉옙占쎌삢 揶쏉옙繹먮슣�뒲 占쎈걗占쎈굡 筌≪뼐由� 2)findShortestPath()
		}
	}
	
	// PathFinder 占쎌뵠占쎌뒠
	// 占쎌쁽占쎈뻿占쎌벥 占쎌맄燁살꼶占쏙옙 �뤃�뗫막 占쎈르占쎈뮉 findClosestVertex嚥∽옙 占쎌젫占쎌뵬 揶쏉옙繹먮슣�뒲 Vertex
	// Mouse input target占쏙옙 椰꾨�窺筌랃옙 筌욑옙占쎌젟 揶쏉옙占쎈뮟, 椰꾨�窺 占쎌젟癰귣��뮉 getAllBuildingInfos占쎌몵嚥∽옙 揶쏉옙占쎌죬占쎌긾
	// Mouse input target占쎌몵嚥∽옙 疫뀀챷媛쇗묾占� : findShortestPath(Vertex source, Building target)
	// Category input target占쎌몵嚥∽옙 疫뀀챷媛쇗묾占� : findShortestPath(Vertex source, String category)
	// ----------------------------------------------------------------------------------------
	// 占쎌넅筌롫똻肉� + 甕곌쑵�뱣 占쎈땭占쎌쑎占쎄퐣 占쎄텢占쎌뒠占쎌쁽 占쎌뿯占쎌젾 筌∽옙 占쎌뱽占쎌뒭疫뀐옙
	// 占쎄텢占쎌뒠占쎌쁽揶쏉옙 submit 占쎈릭筌롳옙 Database class占쎈퓠 setRoom(String rName, String category, Building building) 占쎄텢占쎌뒠
}

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrame mf = new MyFrame();
		/*
		try {
			int socketPort = 1234; // �냼耳� �룷�듃 �꽕�젙
            ServerSocket serverSocket = new ServerSocket(socketPort); // �꽌踰� �냼耳� 留뚮뱾湲�
            // �꽌踰� �삤�뵂 �솗�씤�슜
            System.out.println("socket : " + socketPort + "�쑝濡� �꽌踰꾧� �뿴�졇�뒿�땲�떎");
	
            // �냼耳� �꽌踰꾧� 醫낅즺�맆 �븣源뚯� 臾댄븳猷⑦봽 �쑀�� �닔留뚰겮 �뒪�젅�뱶媛� �깮�꽦�씠 �맖.
            while(true) {
                Socket socketUser = serverSocket.accept(); // �꽌踰꾩뿉 �겢�씪�씠�뼵�듃 �젒�냽 �떆
                // Thread �븞�뿉 �겢�씪�씠�뼵�듃 �젙蹂대�� �떞�븘以�
                //Thread thd = new Server(socketUser);
                //thd.start(); // Thread �떆�옉
            }                 
        
	} catch (IOException e) {
		e.printStackTrace(); // �삁�쇅泥섎━
	}*/
	}

}
