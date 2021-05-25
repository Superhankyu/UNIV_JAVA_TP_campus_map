import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

class MyFrame extends JFrame {
	JMenu menu;
	
	//메뉴바 변수
	JMenuBar menuBar;
	JMenuItem fPathItem;
	JMenuItem fBuildItem;
	JMenuItem ExitItem;
	
	//맵 이미지 변수
	JLabel label_img;
	
	//마우스 및 키보드 input 변수
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
		
		label_img = new JLabel("campus");
		ImageIcon icon = new ImageIcon("자과캠.jpg");
		Image img = icon.getImage();
		Image changeimg = img.getScaledInstance(600, 500, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeimg);
		label_img.setIcon(changeIcon);
		//label_img.setIcon(new ImageIcon("campus.jpg"));
		
		panel.add(label);
		panel.add(textField);
		panel.add(button);
		panel.add(label_img);
		// Content Pane: Space for attaching component or container linked to frame
		Container c = getContentPane(); 
		c.add(panel); // add panel in frame 
		
		pack(); // to combine
		
		panel.setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		setVisible(true);
	}
	
	private void CreateMenu() { //메뉴바 만들기 [1 메뉴_find path]: 현재 위치(가장 가까운 노드) --> 도착 위치 노드  [2 메뉴_find building]:현재 위치(가장 가까운 노드) --> 도착 건물 [3 메뉴_EXIT]
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
	
	class MouseMotionAdapter implements MouseListener, MouseMotionListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(myPos.x == 0) { //내 위치를 받은 적이 없으면 마우스 입력은 myPos
				myPos.x = e.getX();
				myPos.y = e.getY();
			}
			else { //내 위치 입력 받았었으면 마우스 입력은 targetPos
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
	
	class TestListenr implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == ExitItem) { //[3 메뉴_EXIT] 클릭 시 끄기
				System.exit(1);
			}
			else if(event.getSource() == fPathItem) {
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				FindPath();
			}
			else if(event.getSource() == fBuildItem) {
				FindBuilding();
			}
		}
		
		void FindPath() {
			
		}
		
		void FindBuilding() {
			
		}
	}
	
	// PathFinder 이용
	// 자신의 위치를 구할 때는 findClosestVertex로 제일 가까운 Vertex
	// Mouse input target은 건물만 지정 가능, 건물 정보는 getAllBuildingInfos으로 가져옴
	// Mouse input target으로 길찾기 : findShortestPath(Vertex source, Building target)
	// Category input target으로 길찾기 : findShortestPath(Vertex source, String category)
	// ----------------------------------------------------------------------------------------
	// 화면에 + 버튼 눌러서 사용자 입력 창 띄우기
	// 사용자가 submit 하면 Database class에 setRoom(String rName, String category, Building building) 사용
}

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrame mf = new MyFrame();
	}

}
