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
	static ArrayList<Socket> list = new ArrayList<Socket>(); // 유저 확인용
	Socket socket = null;
	
	public Server_thread(Socket socket) {
		this.socket = socket; // 유저 socket을 할당
		list.add(socket); // 유저를 list에 추가
	}
	public void run() {
		try {
        		// 연결 확인용
			System.out.println("서버 : " + socket.getInetAddress() 
            						+ " IP의 클라이언트와 연결되었습니다");
			
			// InputStream - 클라이언트에서 보낸 메세지 읽기
//			InputStream input = socket.getInputStream(); // 맨 처음 이름 입력하게
//			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			// OutputStream - 서버에서 클라이언트로 메세지 보내기
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
		    e.printStackTrace(); // 예외처리
		}    		
    }

}

