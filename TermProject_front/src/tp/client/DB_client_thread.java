package tp.client;

import tp.common.Room;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

public class DB_client_thread implements Runnable { // 서버에서 보낸 메세지 읽는 Thread
	private Socket socket = null;
	private String db_action = null;
	private Room room = null;
	private String rName = null;
	private volatile List<Room> return_rooms = new ArrayList<Room>();
	private volatile boolean return_bool;
	
	public DB_client_thread(Socket socket, String db_action) { // 생성자
		this.socket = socket; // 받아온 Socket Parameter를 해당 클래스 Socket에 넣기
		this.db_action = db_action;
	}

	public DB_client_thread(Socket socket, String db_action, Room room) { // 생성자
		this.socket = socket; // 받아온 Socket Parameter를 해당 클래스 Socket에 넣기
		this.db_action = db_action;
		this.room = room;
	}
	
	public DB_client_thread(Socket socket, String db_action, String rName) { // 생성자
		this.socket = socket; // 받아온 Socket Parameter를 해당 클래스 Socket에 넣기
		this.db_action = db_action;
		this.rName = rName;
	}
	
	public List<Room> getRooms(){
		while(return_rooms.size() == 0) {}
		
		return return_rooms;
	}
	
	public void run() {
		try {
//			InputStream - Server에서 보낸 메세지를 클라이언트로 가져옴
//			socket의 InputStream 정보를 InputStream in에 넣은 뒤
//			BufferedReader에 위 InputStream을 담아 사용
//			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//			System.out.println(reader.readLine());
			
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream (is);
			OutputStream os = socket.getOutputStream ();
			DataOutputStream dos = new DataOutputStream (os);
			
			if(db_action.equals("update")) {
				dos.writeUTF("ready");
				dos.writeUTF("update");
				
				while(!dis.readUTF().equals("update ready")) {}
				
				var data = "";
				while(true) {
					data = dis.readUTF();
					if(data.equals("done"))
						break;
					
					return_rooms.add(new Room(data.split(":")[0],data.split(":")[1],data.split(":")[2]));
				}
			}
			else if(db_action.equals("set")) {
				dos.writeUTF("ready");
				dos.writeUTF("set");
				dos.writeUTF(
						room.rName + ":" +
						room.category + ":" +
						room.building
				);
			}
			else if(db_action.equals("erase")) {
				dos.writeUTF("ready");
				dos.writeUTF("erase");
				dos.writeUTF(
						room.rName + ":" +
						room.category + ":" +
						room.building
				);
			}
			
			
//			while(true) {
//				
//				MyFrame.wait = 1;
//				MyFrame mf = new MyFrame();
//				
//				while(MyFrame.wait == 1) {
//					System.out.print("");
//				}
//				
//				if(MyFrame.isfin == 1) {
//					break;
//				}
//			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
