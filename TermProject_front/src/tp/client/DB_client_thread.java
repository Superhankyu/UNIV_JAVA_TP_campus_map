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

public class DB_client_thread implements Runnable { // �������� ���� �޼��� �д� Thread
	private Socket socket = null;
	private String db_action = null;
	private Room room = null;
	private String rName = null;
	private volatile List<Room> return_rooms = new ArrayList<Room>();
	private volatile boolean return_bool;
	
	public DB_client_thread(Socket socket, String db_action) { // ������
		this.socket = socket; // �޾ƿ� Socket Parameter�� �ش� Ŭ���� Socket�� �ֱ�
		this.db_action = db_action;
	}

	public DB_client_thread(Socket socket, String db_action, Room room) { // ������
		this.socket = socket; // �޾ƿ� Socket Parameter�� �ش� Ŭ���� Socket�� �ֱ�
		this.db_action = db_action;
		this.room = room;
	}
	
	public DB_client_thread(Socket socket, String db_action, String rName) { // ������
		this.socket = socket; // �޾ƿ� Socket Parameter�� �ش� Ŭ���� Socket�� �ֱ�
		this.db_action = db_action;
		this.rName = rName;
	}
	
	public List<Room> getRooms(){
		while(return_rooms.size() == 0) {}
		
		return return_rooms;
	}
	
	public void run() {
		try {
//			InputStream - Server���� ���� �޼����� Ŭ���̾�Ʈ�� ������
//			socket�� InputStream ������ InputStream in�� ���� ��
//			BufferedReader�� �� InputStream�� ��� ���
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
