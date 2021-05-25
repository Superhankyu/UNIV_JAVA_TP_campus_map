



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.sql.*;
import java.util.Scanner;
import java.io.*;



public class DB_server extends Thread {
	static String building_number;
	static Double xposition;
	static ArrayList<Socket> list = new ArrayList<Socket>(); // ���� Ȯ�ο�
	static Socket socket = null;
	
	public DB_server(Socket socket) {
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
			writer.println("������ ����Ǿ����ϴ�! �ǹ� ��ȣ, X��, Y�� �Է����ּ���.");
			
			String bulid_num = null;
			String x = null;
			String y = null;
			String readValue; // Client���� ���� �� ����
			
			writer.println("�ǹ���ȣ : ");
			bulid_num = reader.readLine();
			writer.println("X�� : ");
			x = reader.readLine();
			writer.println("Y�� : ");
			y = reader.readLine();
			
			if(DB_conn(bulid_num, x, y) == 1) {
				writer.println("there are exist building");
			}
			else {
				writer.println("no building or error");
			}
			
			String name = null; // Ŭ���̾�Ʈ �̸� ������
			boolean identify = false;
			
            		// Ŭ���̾�Ʈ�� �޼��� �Է½ø��� ����
			while((readValue = reader.readLine()) != null ) {
				if(!identify) { // ���� �� �ѹ��� ����
					name = readValue; // �̸� �Ҵ�
					identify = true;
					writer.println(name + "���� �����ϼ̽��ϴ�.");
					continue;
				}
				
                		// list �ȿ� Ŭ���̾�Ʈ ������ �������
				for(int i = 0; i<list.size(); i++) { 
					out = list.get(i).getOutputStream();
					writer = new PrintWriter(out, true);
                    			// *��� Ŭ���̾�Ʈ���� �޼��� �߼�
					writer.println(name + " : " + readValue); 
				}
			}
		} catch (Exception e) {
		    e.printStackTrace(); // ����ó��
		}    		
    }
	public int DB_conn(String b_num, String x, String y){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaPro",
					"root", "1234");
			
		
			Statement stmt = conn.createStatement();
			ResultSet rset;
			String sql;
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			building_number = b_num;
			xposition =  Double.parseDouble(x);
			
			sql = "select * from cross_node where name = '" +building_number+ "' and xposition = '"+xposition+"'";
			rset = stmt.executeQuery(sql);
				
			if(rset.next()== true) {
				stmt.close();
				conn.close();
				return 1;
			}
			else {
				stmt.close();
				conn.close();
				return 0;
			}
			
			
		}
		catch(SQLException sqle) {
			System.out.println("SQLException "+ sqle);
		}
		catch(Exception e) {
			System.out.println("Exception "+ e);
		}
		return -1;
	}
	public static void main(String[] args) {
		try {
                  int socketPort = 1234; // ���� ��Ʈ ������
                  ServerSocket serverSocket = new ServerSocket(socketPort); // ���� ���� �����
                  // ���� ���� Ȯ�ο�
                  System.out.println("socket : " + socketPort + "���� ������ ���Ƚ��ϴ�");
		
                  // ���� ������ ����� ������ ���ѷ��� ���� ����ŭ �����尡 ������ ��.
                  while(true) {
                      Socket socketUser = serverSocket.accept(); // ������ Ŭ���̾�Ʈ ���� ��
                      // Thread �ȿ� Ŭ���̾�Ʈ ������ �����
                      Thread thd = new DB_server(socketUser);
                      thd.start(); // Thread ����
                  }                 
        
	} catch (IOException e) {
		e.printStackTrace(); // ����ó��
	}

}
}
