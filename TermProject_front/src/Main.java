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
	
	// PathFinder �̿�
	// �ڽ��� ��ġ�� ���� ���� findClosestVertex�� ���� ����� Vertex
	// Mouse input target�� �ǹ��� ���� ����, �ǹ� ������ getAllBuildingInfos���� ������
	// Mouse input target���� ��ã�� : findShortestPath(Vertex source, Building target)
	// Category input target���� ��ã�� : findShortestPath(Vertex source, String category)
	// ----------------------------------------------------------------------------------------
	// ȭ�鿡 + ��ư ������ ����� �Է� â ����
	// ����ڰ� submit �ϸ� Database class�� setRoom(String rName, String category, Building building) ���
}

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrame mf = new MyFrame();
	}

}
