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
	JMenu menu;
	
	//硫붾돱諛� 蹂��닔
	JMenuBar menuBar;
	JMenuItem fPathItem;
	JMenuItem fBuildItem;
	JMenuItem ExitItem;
	
	//留� �씠誘몄� 蹂��닔
	JLabel label_img;
	
	//留덉슦�뒪 諛� �궎蹂대뱶 input 蹂��닔
	Position myPos = new Position(0, 0); // �쁽�옱 �궗�슜�옄 �쐞移�
	Position targetPos = new Position(0, 0); // �룄李⑺븷 �옣�냼(�끂�뱶�뿬�빞�븿) �쐞移�
	
	MyFrame() {
		//setLayout(null);
		CreateMenu();
		
		setTitle("Campus Map");
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("name: ");
		JTextField textField = new JTextField(20);
		JButton button = new JButton("send");
		
		label_img = new JLabel("campus");
		label_img.setIcon(new ImageIcon("campus.jpg"));
		
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

	//硫붾돱諛� 留뚮뱾湲�
	private void CreateMenu() { //[1 硫붾돱_find path]: �쁽�옱 �쐞移�(媛��옣 媛�源뚯슫 �끂�뱶) --> �룄李� �쐞移� �끂�뱶  [2 硫붾돱_find building]:�쁽�옱 �쐞移�(媛��옣 媛�源뚯슫 �끂�뱶) --> �룄李� 嫄대Ъ [3 硫붾돱_EXIT]
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		menuBar.add(menu);
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
	
	//留덉슦�뒪濡� Position �엯�젰 諛쏅뒗 �븿�닔
	class MouseMotionAdapter implements MouseListener, MouseMotionListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(myPos.x == 0) { //�궡 �쐞移섎�� 諛쏆� �쟻�씠 �뾾�쑝硫� 留덉슦�뒪 �엯�젰�� myPos
				myPos.x = e.getX();
				myPos.y = e.getY();
			}
			else { //�궡 �쐞移� �엯�젰 諛쏆븯�뿀�쑝硫� 留덉슦�뒪 �엯�젰�� targetPos
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
	/*3媛�吏� 硫붾돱 媛곴컖 function �닔�뻾
	1. Exit 硫붾돱: �굹媛�湲�
	2. Find Path: 留덉슦�뒪 �엯�젰 2踰�(�쁽�쐞移�, ��寃� �쐞移�) �썑 path 李얘린
	3. Find Building: 留덉슦�뒪 �엯�젰 1踰�(�쁽�쐞移�), �궎蹂대뱶 �엯�젰 1踰�(�쓽�룄) �썑 path 李얘린
	*/
	class TestListenr implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == ExitItem) {
				System.exit(1);
			}
			else if(event.getSource() == fPathItem) {
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				//TODO: targetPos�뒗 媛��옣 媛�源뚯슫 �끂�뱶濡� 蹂�寃�
				FindPath();
			}
			else if(event.getSource() == fBuildItem) {
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				FindBuilding();
			}
		}
		void FindPath() {
			//TODO : 1)�쁽�쐞移�, ��寃잛쐞移섏뿉�꽌 媛��옣 媛�源뚯슫 �끂�뱶 李얘린 2)findShortestPath()
		}
		
		void FindBuilding() {
			//TODO : 1)�쁽�쐞移섏뿉�꽌 媛��옣 媛�源뚯슫 �끂�뱶 李얘린 2)findShortestPath()
		}
	}
	
	// PathFinder �씠�슜
	// �옄�떊�쓽 �쐞移섎�� 援ы븷 �븣�뒗 findClosestVertex濡� �젣�씪 媛�源뚯슫 Vertex
	// Mouse input target�� 嫄대Ъ留� 吏��젙 媛��뒫, 嫄대Ъ �젙蹂대뒗 getAllBuildingInfos�쑝濡� 媛��졇�샂
	// Mouse input target�쑝濡� 湲몄갼湲� : findShortestPath(Vertex source, Building target)
	// Category input target�쑝濡� 湲몄갼湲� : findShortestPath(Vertex source, String category)
	// ----------------------------------------------------------------------------------------
	// �솕硫댁뿉 + 踰꾪듉 �닃�윭�꽌 �궗�슜�옄 �엯�젰 李� �쓣�슦湲�
	// �궗�슜�옄媛� submit �븯硫� Database class�뿉 setRoom(String rName, String category, Building building) �궗�슜
}

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// MyFrame mf = new MyFrame();
		
		try {
			int socketPort = 1234; // 소켓 포트 설정
            ServerSocket serverSocket = new ServerSocket(socketPort); // 서버 소켓 만들기
            // 서버 오픈 확인용
            System.out.println("socket : " + socketPort + "으로 서버가 열렸습니다");
	
            // 소켓 서버가 종료될 때까지 무한루프 유저 수만큼 스레드가 생성이 됨.
            while(true) {
                Socket socketUser = serverSocket.accept(); // 서버에 클라이언트 접속 시
                // Thread 안에 클라이언트 정보를 담아줌
                Thread thd = new Server(socketUser);
                thd.start(); // Thread 시작
            }                 
        
	} catch (IOException e) {
		e.printStackTrace(); // 예외처리
	}
	}

}
