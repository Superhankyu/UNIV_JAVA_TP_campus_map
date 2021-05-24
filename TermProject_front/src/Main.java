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
	
	private void CreateMenu() { //�޴��� ����� [1 �޴�_find path]: ���� ��ġ(���� ����� ���) --> ���� ��ġ ���  [2 �޴�_find building]:���� ��ġ(���� ����� ���) --> ���� �ǹ� [3 �޴�_EXIT]
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
			if(event.getSource() == ExitItem) { //[3 �޴�_EXIT] Ŭ�� �� ����
				System.exit(1);
			}
			else if(event.getSource() == fPathItem) {
				
			}
			else if(event.getSource() == fBuildItem) {
				
			}
		}
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
