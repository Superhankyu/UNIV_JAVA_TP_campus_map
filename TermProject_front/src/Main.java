import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class MyFrame extends JFrame {	
	MyFrame() {
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
		
		setSize(800,600);
		setVisible(true);
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
