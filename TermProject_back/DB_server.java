



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
	static ArrayList<Socket> list = new ArrayList<Socket>(); // 유저 확인용
	static Socket socket = null;
	
	public DB_server(Socket socket) {
		this.socket = socket; // 유저 socket을 할당
		list.add(socket); // 유저를 list에 추가
	}
	public void run() {
		try {
        		// 연결 확인용
			System.out.println("서버 : " + socket.getInetAddress() 
            						+ " IP의 클라이언트와 연결되었습니다");
			
			// InputStream - 클라이언트에서 보낸 메세지 읽기
			InputStream input = socket.getInputStream(); // 맨 처음 이름 입력하게
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			// OutputStream - 서버에서 클라이언트로 메세지 보내기
			OutputStream out = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(out, true);
			
			// 클라이언트에게 연결되었다는 메세지 보내기
			writer.println("서버에 연결되었습니다! 건물 번호, X값, Y값 입력해주세요.");
			
			String bulid_num = null;
			String x = null;
			String y = null;
			String readValue; // Client에서 보낸 값 저장
			
			writer.println("건물번호 : ");
			bulid_num = reader.readLine();
			writer.println("X값 : ");
			x = reader.readLine();
			writer.println("Y값 : ");
			y = reader.readLine();
			
			if(DB_conn(bulid_num, x, y) == 1) {
				writer.println("there are exist building");
			}
			else {
				writer.println("no building or error");
			}
			
			String name = null; // 클라이언트 이름 설정용
			boolean identify = false;
			
            		// 클라이언트가 메세지 입력시마다 수행
			while((readValue = reader.readLine()) != null ) {
				if(!identify) { // 연결 후 한번만 노출
					name = readValue; // 이름 할당
					identify = true;
					writer.println(name + "님이 접속하셨습니다.");
					continue;
				}
				
                		// list 안에 클라이언트 정보가 담겨있음
				for(int i = 0; i<list.size(); i++) { 
					out = list.get(i).getOutputStream();
					writer = new PrintWriter(out, true);
                    			// *모든 클라이언트에게 메세지 발송
					writer.println(name + " : " + readValue); 
				}
			}
		} catch (Exception e) {
		    e.printStackTrace(); // 예외처리
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
                  int socketPort = 1234; // 소켓 포트 설정용
                  ServerSocket serverSocket = new ServerSocket(socketPort); // 서버 소켓 만들기
                  // 서버 오픈 확인용
                  System.out.println("socket : " + socketPort + "으로 서버가 열렸습니다");
		
                  // 소켓 서버가 종료될 때까지 무한루프 유저 수만큼 스레드가 생성이 됨.
                  while(true) {
                      Socket socketUser = serverSocket.accept(); // 서버에 클라이언트 접속 시
                      // Thread 안에 클라이언트 정보를 담아줌
                      Thread thd = new DB_server(socketUser);
                      thd.start(); // Thread 시작
                  }                 
        
	} catch (IOException e) {
		e.printStackTrace(); // 예외처리
	}

}
}
