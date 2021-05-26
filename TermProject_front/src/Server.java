import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
	static String building_number;
	static Double xposition;
	static ArrayList<Socket> list = new ArrayList<Socket>(); // ���� Ȯ�ο�
	static Socket socket = null;
	
	public Server(Socket socket) {
		this.socket = socket; // ���� socket�� �Ҵ�
		list.add(socket); // ������ list�� �߰�
	}
	public void run() {
		try {
        		// ���� Ȯ�ο�
			System.out.println("���� : " + socket.getInetAddress() 
            						+ " IP�� Ŭ���̾�Ʈ�� ����Ǿ����ϴ�");
			
			// InputStream - Ŭ���̾�Ʈ���� ���� �޼��� �б�
			InputStream input = socket.getInputStream(); // �� ó�� �̸� �Է��ϰ�
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			// OutputStream - �������� Ŭ���̾�Ʈ�� �޼��� ������
			OutputStream out = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(out, true);
			
			// Ŭ���̾�Ʈ���� ����Ǿ��ٴ� �޼��� ������
			writer.println("Connect success");
		
			String rName = null;
			String category = null;
			String building = null;
			
			Database DB = new Database();
		
					
			String WhatToDo = reader.readLine(); // get input from GUI
			
			if(WhatToDo.equals("insertNew")) { // when users are give DB some new information.
				writer.println("insert"); 
				
				writer.println("rName : "); // writer Lines can be Deleted or be a GUI interface. 3 Questions.
				rName = reader.readLine(); // get input from GUI
				writer.println("Category : ");
				category = reader.readLine(); // get input..
				writer.println("building : ");
				building = reader.readLine(); // get input..
				
				if(DB.setRoom(rName, category, building)) { // return 1 if success to insert.
					writer.println("insert success");
				}
			}
			else if(WhatToDo.equals("PathFind")) {
				writer.println("PathFind"); 
				// TODO GUI 
			}
             
		} catch (Exception e) {
		    e.printStackTrace(); // ����ó��
		}    		
    }

}

