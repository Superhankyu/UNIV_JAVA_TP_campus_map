import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

import org.json.JSONArray;
import org.json.JSONObject;

class MyFrame extends JFrame {
	JPanel panel;
	PathFinder PathFinder = new PathFinder();
	Database Database = new Database();
	static int isfin = 0;
	static int wait = 1;
	//variables for function
	boolean FINDPATH = false;
	boolean FINDBUILD = false;
	boolean DATABASE = false;
	RoundedButton start;
	String dbrName;
	String dbCategory;
	String dbbName;
	RoundedButton sendButton;
	
	//variables for menu bar
	JMenuBar menuBar;
	JMenu menu1;
	JMenuItem newPath;
	JMenu menu2;
	JMenuItem rBathroom;
	JMenuItem rRestaurant;
	JMenuItem rFeLounge;
	JMenuItem rMaLounge;
	JMenuItem convStore;
	JMenuItem cafe;
	JMenuItem disToil;
	JMenu menu3;
	JMenuItem addData;
	JMenuItem eraseData;
	JMenu menu4;
	
	JMenuItem startNew;
	JMenuItem exit;
	
	//variable campus_img
	JLabel label_img;
	
	//variable userPosition & targetPosition & PathPosition
	Position myPos = new Position(0, 0);
	List<Building> buildings = PathFinder.getAllBuildingInfos();
	String category;
	List<Vertex> path = new ArrayList<Vertex>();
	
	//variable for drawing path line
	int[] xPoints;
	int[] yPoints;
	
	//variable for popups
	PopupFactory pf = PopupFactory.getSharedInstance();
	JLabel curlocLabel;
	Popup curlocPopup;
	JLabel tarlocLabel;
	Popup tarlocPopup;
	Popup startPopup;
	ArrayList<Popup> popupList = new ArrayList<Popup>(); //target building button popup list
	ArrayList<JButton> buttonList = new ArrayList<JButton>();
	JPanel dbPanel;
	Popup dbPopup;
	int exit_ = 0;
	
	//variable for design
	Color buttonC=new Color(136, 133, 164); //background color of button
	List<Line2D> lineList = new ArrayList<Line2D>();
	
	MyFrame() {
		//setLayout(null);
		CreateMenu();
		
		setTitle("Campus Map");
		
		panel = new JPanel();
		//JTextField textField = new JTextField(20);
		
		label_img = new JLabel();

		ImageIcon icon = new ImageIcon("campus.jpg");
		Image img = icon.getImage();
		Image changeimg = img.getScaledInstance(600, 480, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeimg);
		label_img.setIcon(changeIcon);
		
		panel.setBackground(Color.WHITE);
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
		menuBar.add(Box.createRigidArea(new Dimension(10, 37)));
		menuBar.setBorder(BorderFactory.createLineBorder(Color.black));
		menuBar.setBackground(Color.ORANGE);
		
		//[menu1_find path]
		menu1 = new JMenu("Find Path");
		menuBar.add(menu1);
		newPath = new JMenuItem("find new path");
		menu1.add(newPath);
		
		//[menu2_find building]
		menu2 = new JMenu("Find Building");
		menuBar.add(menu2);
		rBathroom = new JMenuItem("bathroom");
		rRestaurant = new JMenuItem("restaurant");
		rFeLounge = new JMenuItem("female student lounge");
		rMaLounge = new JMenuItem("male student lounge");
		convStore = new JMenuItem("convernience store");
		cafe = new JMenuItem("cafe");
		disToil = new JMenuItem("disabled toilets");		
		menu2.add(rBathroom);
		menu2.add(rRestaurant);
		menu2.add(rFeLounge);
		menu2.add(rMaLounge);
		menu2.add(convStore);
		menu2.add(cafe);
		menu2.add(disToil);
		
		//[menu3_Database]
		menu3 = new JMenu("SKKU Map Database");
		menuBar.add(menu3);
		addData = new JMenuItem("add new amenities");
		eraseData = new JMenuItem("erase missing amenities");
		menu3.add(addData);
		menu3.add(eraseData);
		
		//[menu4_EXIT]
		menu4 = new JMenu("Start new map or Exit");
		menuBar.add(menu4);
		exit = new JMenuItem("EXIT");
		startNew = new JMenuItem("Start new map");
		menu4.add(startNew);
		menu4.add(exit);
		
		TestListenr listener = new TestListenr();
		newPath.addActionListener(listener);
		rBathroom.addActionListener(listener);
		rRestaurant.addActionListener(listener);
		rFeLounge.addActionListener(listener);
		rMaLounge.addActionListener(listener);
		convStore.addActionListener(listener);
		cafe.addActionListener(listener);
		disToil.addActionListener(listener);
		addData.addActionListener(listener);
		eraseData.addActionListener(listener);
		exit.addActionListener(listener);
		startNew.addActionListener(listener);
		
		setJMenuBar(menuBar);
	}
	
	private ArrayList <JButton> CreateBuildPopup(){
		for(int i = 0; i < buildings.size(); i++) {
			ImageIcon icon = new ImageIcon("place.png");
			Image img = icon.getImage();
			Image changeimg = img.getScaledInstance(20, 25, Image.SCALE_SMOOTH);
			ImageIcon changeIcon = new ImageIcon(changeimg);
			JButton button_place = new JButton(changeIcon);
			button_place.setSize(10, 10);
			button_place.setBorderPainted(false);
			button_place.setBorder(null);
			//button.setFocusable(false);
			button_place.setMargin(new Insets(0, 0, 0, 0));
			button_place.setContentAreaFilled(false);
			ImageIcon icon2 = new ImageIcon("chooseplace.png");
			Image img2 = icon2.getImage();
			Image changeimg2 = img2.getScaledInstance(20, 25, Image.SCALE_SMOOTH);
			ImageIcon changeIcon2 = new ImageIcon(changeimg2);
			button_place.setRolloverIcon(changeIcon2);
			button_place.setPressedIcon(changeIcon2);
			button_place.setDisabledIcon(changeIcon2);
			Popup buildPopup = pf.getPopup(label_img, button_place, buildings.get(i).vt.pos.x + 80, buildings.get(i).vt.pos.y + 40);
			popupList.add(buildPopup);
			buttonList.add(button_place);
			buildPopup.show();
		}
		return buttonList;
	}
	
	class RoundedButton extends JButton {
		private static final long serialVersionUID = -6534565712701872577L;
		public RoundedButton() { super(); decorate(); } 
	      public RoundedButton(String text) { super(text); decorate(); } 
	      public RoundedButton(Action action) { super(action); decorate(); } 
	      public RoundedButton(Icon icon) { super(icon); decorate(); } 
	      public RoundedButton(String text, Icon icon) { super(text, icon); decorate(); } 
	      protected void decorate() { setBorderPainted(false); setOpaque(false); }
	      @Override 
	      protected void paintComponent(Graphics g) {
	         Color o=new Color(0, 0, 0); //word color of button
	         int width = getWidth(); 
	         int height = getHeight();
	         Graphics2D graphics = (Graphics2D) g; 
	         graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
	         if (getModel().isArmed()) { graphics.setColor(buttonC.darker()); } 
	         else if (getModel().isRollover()) { graphics.setColor(buttonC.brighter()); } 
	         else { graphics.setColor(buttonC); } 
	         graphics.fillRoundRect(0, 0, width, height, 10, 10); 
	         FontMetrics fontMetrics = graphics.getFontMetrics(); 
	         Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); 
	         int textX = (width - stringBounds.width) / 2; 
	         int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent(); 
	         graphics.setColor(o); 
	         graphics.setFont(getFont()); 
	         graphics.drawString(getText(), textX, textY); 
	         graphics.dispose(); 
	         super.paintComponent(g); 
	     }
	}
	
	//get Position information from Mouse Input
	class MouseMotionAdapter implements MouseListener, MouseMotionListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(myPos.x == 0 && FINDPATH == true) {
				myPos.x = e.getX();
				myPos.y = e.getY();
				curlocPopup.hide();
				
				tarlocLabel = new JLabel("Click Your Target Location", JLabel.CENTER);				
				tarlocLabel.setOpaque(true);
				tarlocLabel.setPreferredSize(new Dimension(190, 70));
				EtchedBorder etborder = new EtchedBorder(EtchedBorder.RAISED);
				tarlocLabel.setBorder(etborder);
				tarlocPopup= pf.getPopup(label_img, tarlocLabel, 30, 80);

				tarlocPopup.show();
				buttonList = CreateBuildPopup();
				for(int i = 0; i< buttonList.size(); i++) {
					int index = i;
					buttonList.get(i).addActionListener(new ActionListener() {
						@Override
				        public void actionPerformed(ActionEvent e) {
							tarlocPopup.hide();
							for (int j = 0; j < popupList.size(); j++) {
								popupList.get(j).hide();
							}
				            findPath(index);
				        }
					});
				}
			}
			else if(myPos.x == 0 && FINDBUILD == true) {
				myPos.x = e.getX();
				myPos.y = e.getY();
				curlocPopup.hide();
				start = new RoundedButton("find closest " + category +"!");
				startPopup = pf.getPopup(label_img, start, myPos.x, myPos.y);
				startPopup.show();
				start.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						startPopup.hide();
						findBuilding();
					}
				});
			}
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
	/*3 menu Action Event
	1. Find Path: 1)get user pos from mouse input 2)get target pos from mouse input 3)show shortest path
	2. Find Building: 1)get room category from menu input 2)get user pos from mouse input 3)show shortest path
	3. SKKU Map Database: 1)choose one of the menu(add or erase) 2)pop up new interface 3)room category & building with keyboard input 4)when wrong bat or build -->error message
	*/
	class TestListenr implements ActionListener{
		JTextField textField1;
		JTextField textField2;
		JTextField textField3;
		public void getFieldText() {
			dbrName = textField1.getText();
			dbCategory = textField2.getText();
			dbbName = textField3.getText();
		}
		public void actionPerformed(ActionEvent event) {
			path = new ArrayList<Vertex>();
			if(event.getSource() == newPath) {
				myPos.x = 0;
				myPos.y = 0;
				FINDPATH = true;
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				curlocLabel = new JLabel("Click Your Current Location",JLabel.CENTER);
				curlocLabel.setOpaque(true);
				curlocLabel.setPreferredSize(new Dimension(190, 70));
				EtchedBorder etborder = new EtchedBorder(EtchedBorder.RAISED);
				curlocLabel.setBorder(etborder);
				curlocPopup= pf.getPopup(label_img, curlocLabel, 30, 80);
						
				curlocPopup.show();
			}
			else if(event.getSource() == rBathroom) {
				myPos.x = 0;
				myPos.y = 0;
				FINDBUILD = true;
				category = "bathroom";
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				curlocLabel = new JLabel("Click Your Current Location",JLabel.CENTER);
				curlocLabel.setOpaque(true);
				curlocLabel.setPreferredSize(new Dimension(190, 70));
				EtchedBorder etborder = new EtchedBorder(EtchedBorder.RAISED);
				curlocLabel.setBorder(etborder);
				curlocPopup= pf.getPopup(label_img, curlocLabel, 30, 80);
						
				curlocPopup.show();
			}
			else if(event.getSource() == rRestaurant) {
				myPos.x = 0;
				myPos.y = 0;
				FINDBUILD = true;
				category = "restaurant";
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				curlocLabel = new JLabel("Click Your Current Location",JLabel.CENTER);
				curlocLabel.setOpaque(true);
				curlocLabel.setPreferredSize(new Dimension(190, 70));
				EtchedBorder etborder = new EtchedBorder(EtchedBorder.RAISED);
				curlocLabel.setBorder(etborder);
				curlocPopup= pf.getPopup(label_img, curlocLabel, 30, 80);
						
				curlocPopup.show();
			}
			else if(event.getSource() == rFeLounge) {
				myPos.x = 0;
				myPos.y = 0;
				FINDBUILD = true;
				category = "femaleStudentLounge";
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				curlocLabel = new JLabel("Click Your Current Location",JLabel.CENTER);
				curlocLabel.setOpaque(true);
				curlocLabel.setPreferredSize(new Dimension(190, 70));
				EtchedBorder etborder = new EtchedBorder(EtchedBorder.RAISED);
				curlocLabel.setBorder(etborder);
				curlocPopup= pf.getPopup(label_img, curlocLabel, 30, 80);
						
				curlocPopup.show();
			}
			else if(event.getSource() == rMaLounge) {
				myPos.x = 0;
				myPos.y = 0;
				FINDBUILD = true;
				category = "maleStudentLounge";
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				curlocLabel = new JLabel("Click Your Current Location",JLabel.CENTER);
				curlocLabel.setOpaque(true);
				curlocLabel.setPreferredSize(new Dimension(190, 70));
				EtchedBorder etborder = new EtchedBorder(EtchedBorder.RAISED);
				curlocLabel.setBorder(etborder);
				curlocPopup= pf.getPopup(label_img, curlocLabel, 30, 80);
						
				curlocPopup.show();
			}
			else if(event.getSource() == convStore) {
				myPos.x = 0;
				myPos.y = 0;
				FINDBUILD = true;
				category = "convenienceStore";
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				curlocLabel = new JLabel("Click Your Current Location",JLabel.CENTER);
				curlocLabel.setOpaque(true);
				curlocLabel.setPreferredSize(new Dimension(190, 70));
				EtchedBorder etborder = new EtchedBorder(EtchedBorder.RAISED);
				curlocLabel.setBorder(etborder);
				curlocPopup= pf.getPopup(label_img, curlocLabel, 30, 80);
						
				curlocPopup.show();
			}
			else if(event.getSource() == cafe) {
				myPos.x = 0;
				myPos.y = 0;
				FINDBUILD = true;
				category = "cafe";
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				curlocLabel = new JLabel("Click Your Current Location",JLabel.CENTER);
				curlocLabel.setOpaque(true);
				curlocLabel.setPreferredSize(new Dimension(190, 70));
				EtchedBorder etborder = new EtchedBorder(EtchedBorder.RAISED);
				curlocLabel.setBorder(etborder);
				curlocPopup= pf.getPopup(label_img, curlocLabel, 30, 80);
						
				curlocPopup.show();
			}
			else if(event.getSource() == disToil) {
				myPos.x = 0;
				myPos.y = 0;
				FINDBUILD = true;
				category = "disabledToilets";
				label_img.addMouseListener(new MouseMotionAdapter());
				label_img.addMouseMotionListener(new MouseMotionAdapter());
				curlocLabel = new JLabel("Click Your Current Location",JLabel.CENTER);
				curlocLabel.setOpaque(true);
				curlocLabel.setPreferredSize(new Dimension(190, 70));
				EtchedBorder etborder = new EtchedBorder(EtchedBorder.RAISED);
				curlocLabel.setBorder(etborder);
				curlocPopup= pf.getPopup(label_img, curlocLabel, 30, 80);
						
				curlocPopup.show();
			}
			else if(event.getSource() == addData) {
				DATABASE = true;
				dbPanel = new JPanel();
				dbPanel.setPreferredSize(new Dimension(300, 130));
				dbPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				dbPanel.setBorder(BorderFactory.createLineBorder(Color.black));

				String title = "Add new Building";
				Border b = BorderFactory.createTitledBorder(title);
				dbPanel.setBorder(b);
				JLabel rNameLabel = new JLabel("Roomname: ");
				textField1 = new JTextField(18);
				JLabel catLabel = new JLabel("Category:     ");
				textField2 = new JTextField(18);
				JLabel buildLabel = new JLabel("Building:       ");
				textField3 = new JTextField(18);
				sendButton = new RoundedButton("SEND");
				
				dbPanel.add(rNameLabel);
				dbPanel.add(textField1);
				dbPanel.add(catLabel);
				dbPanel.add(textField2);
				dbPanel.add(buildLabel);
				dbPanel.add(textField3);
				dbPanel.add(sendButton);
				
				dbPopup = pf.getPopup(label_img, dbPanel, 30, 80);
				dbPopup.show();
				DATABASE = false;
				
				sendButton.addActionListener(new ActionListener() {
					@Override
			        public void actionPerformed(ActionEvent e) {
						getFieldText();
						Database.setRoom(dbrName, dbCategory, dbbName);
						dbPopup.hide();
			        }
				});
			}
			else if(event.getSource() == eraseData) {
				DATABASE = true;
				dbPanel = new JPanel();
				dbPanel.setPreferredSize(new Dimension(300, 130));
				dbPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				dbPanel.setBorder(BorderFactory.createLineBorder(Color.black));

				String title = "Erase Building";
				Border b = BorderFactory.createTitledBorder(title);
				dbPanel.setBorder(b);
				JLabel rNameLabel = new JLabel("Roomname: ");
				textField1 = new JTextField(18);
				JLabel catLabel = new JLabel("Category:     ");
				textField2 = new JTextField(18);
				JLabel buildLabel = new JLabel("Building:       ");
				textField3 = new JTextField(18);
				sendButton = new RoundedButton("SEND");
				
				dbPanel.add(rNameLabel);
				dbPanel.add(textField1);
				dbPanel.add(catLabel);
				dbPanel.add(textField2);
				dbPanel.add(buildLabel);
				dbPanel.add(textField3);
				dbPanel.add(sendButton);
				
				dbPopup = pf.getPopup(label_img, dbPanel, 30, 80);
				dbPopup.show();
				DATABASE = false;
				
				sendButton.addActionListener(new ActionListener() {
					@Override
			        public void actionPerformed(ActionEvent e) {
						getFieldText();
						Database.eraseRoom(dbrName, dbCategory, dbbName);
						dbPopup.hide();
			        }
				});
			}
			else if(event.getSource() == startNew) { //TODO: clear All painting on the Frame
				isfin = 0;
				wait = 0;
				dispose();
			}
			else if(event.getSource() == exit) {
				isfin = 1;
				wait = 0;
				dispose();
			}
		}
	}
	
	private void findPath(int buildIndex) {
		Vertex myVer = PathFinder.findClosestVertex(myPos.x, myPos.y);
		Building tarBuild = buildings.get(buildIndex);
		path = PathFinder.findShortestPath(myVer, tarBuild);
		xPoints= new int[path.size()];
		yPoints= new int[path.size()];
		for(int i = 0; i < path.size(); i++) {
			xPoints[i] = path.get(i).pos.x + 90;
			yPoints[i] = path.get(i).pos.y + 80;
		}
	}
	
	private void findBuilding() {
		Vertex myVer = PathFinder.findClosestVertex(myPos.x, myPos.y);
		path = PathFinder.findShortestPath(myVer, category);
		xPoints= new int[path.size()];
		yPoints= new int[path.size()];
		for(int i = 0; i < path.size(); i++) {
			xPoints[i] = path.get(i).pos.x + 90;
			yPoints[i] = path.get(i).pos.y + 80;
		}
		ImageIcon icon = new ImageIcon("chooseplace.png");
		Image img = icon.getImage();
		Image changeimg = img.getScaledInstance(20, 25, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeimg);
		JButton button_place = new JButton(changeIcon);
		button_place.setSize(10, 10);
		button_place.setBorderPainted(false);
		button_place.setBorder(null);
		button_place.setBackground(getBackground());
		//button.setFocusable(false);
		button_place.setMargin(new Insets(0, 0, 0, 0));
		button_place.setContentAreaFilled(false);
		ImageIcon icon2 = new ImageIcon("chooseplace.png");
		Image img2 = icon2.getImage();
		Image changeimg2 = img2.getScaledInstance(20, 25, Image.SCALE_SMOOTH);
		ImageIcon changeIcon2 = new ImageIcon(changeimg2);
		button_place.setRolloverIcon(changeIcon2);
		button_place.setPressedIcon(changeIcon2);
		button_place.setDisabledIcon(changeIcon2);
		Popup buildPopup = pf.getPopup(label_img, button_place, xPoints[path.size()-1]-10, yPoints[path.size()-1]-40);
		buildPopup.show();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(path.size() != 0) {
			Graphics2D g2 = (Graphics2D) g;
			for(int i = 0; i < path.size()-1; i++) {
				Stroke stroke = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
				g2.setStroke(stroke);
				g2.setColor(Color.ORANGE);
				Line2D line = new Line2D.Float(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]);
				lineList.add(line);
				g2.draw(line);
			}
		}
	}
	
	// PathFinder
	// findClosestVertex  Vertex
	// Mouse input target,  getAllBuildingInfos
	// Mouse input target: findShortestPath(Vertex source, Building target)
	// Category input target: findShortestPath(Vertex source, String category)
	// ----------------------------------------------------------------------------------------
	// submit Database class setRoom(String rName, String category, Building building) 
}

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//MyFrame mf = new MyFrame();
		
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
