import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class MyFrame extends JFrame {
	JMenu menu;
	JMenuBar menuBar;
	JMenuItem fPathItem;
	JMenuItem fBuildItem;
	JMenuItem ExitItem;
	
	MyFrame() {
		//setLayout(null);
		CreateMenu();
		
		setTitle("Campus Map");
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("name: ");
		JTextField textField = new JTextField(20);
		JButton button = new JButton("send");
		
		JLabel label_img = new JLabel("campus");
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
	
	class TestListenr implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == ExitItem) { //[3 메뉴_EXIT] 클릭 시 끄기
				System.exit(1);
			}
			else if(event.getSource() == fPathItem) {
				
			}
			else if(event.getSource() == fBuildItem) {
				
			}
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
