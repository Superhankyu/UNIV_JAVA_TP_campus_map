package tp.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server_thread implements Runnable {
	static String building_number;
	static Double xposition;
	static ArrayList<Socket> list = new ArrayList<Socket>(); // ���� Ȯ�ο�
	Socket socket = null;
	
	public Server_thread(Socket socket) {
		this.socket = socket; // ���� socket�� �Ҵ�
		list.add(socket); // ������ list�� �߰�
	}
	public void run() {
		try {
        		// ���� Ȯ�ο�
			System.out.println("���� : " + socket.getInetAddress() 
            						+ " IP�� Ŭ���̾�Ʈ�� ����Ǿ����ϴ�");
			
			// InputStream - Ŭ���̾�Ʈ���� ���� �޼��� �б�
//			InputStream input = socket.getInputStream(); // �� ó�� �̸� �Է��ϰ�
//			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			// OutputStream - �������� Ŭ���̾�Ʈ�� �޼��� ������
//			OutputStream out = socket.getOutputStream();
//			PrintWriter writer = new PrintWriter(out, true);
			
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream (is);
			OutputStream os = socket.getOutputStream ();
			DataOutputStream dos = new DataOutputStream (os);
			
			Database db = new Database();
			
			while(true) {
				while(!dis.readUTF().equals("ready")) {}

				var action = dis.readUTF();
				if(action.equals("update")) {
					dos.writeUTF("update ready");
					List<String> room_datas = db.getRooms();
					for(String data : room_datas) {
						dos.writeUTF(data);
					}
				}
				else if(action.equals("set")) {
					var data = dis.readUTF();
					db.setRoom(data.split(":")[0], data.split(":")[1], data.split(":")[2]);
				}
				else if(action.equals("erase")) {
					var data = dis.readUTF();
					db.eraseRoom(data.split(":")[0], data.split(":")[1], data.split(":")[2]);
				}
				else if(action.equals("finish")) {
					break;
				}
				
				dos.writeUTF("done");
			}
		
			//String WhatToDo = "hasd";	
			// String WhatToDo = reader.readLine(); // get input from GUI
			
			/*if(WhatToDo.equals("insertNew")) { // when users are give DB some new information.
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
			}*/
             
		} catch (Exception e) {
		    e.printStackTrace(); // ����ó��
		}    		
    }

}

