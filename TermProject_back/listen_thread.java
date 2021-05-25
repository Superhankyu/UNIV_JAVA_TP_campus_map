



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class listen_thread extends Thread { // 서버에서 보낸 메세지 읽는 Thread
	Socket socket = null;

	public listen_thread(Socket socket) { // 생성자
		this.socket = socket; // 받아온 Socket Parameter를 해당 클래스 Socket에 넣기
	}
	
	public void run() {
		try {
			// InputStream - Server에서 보낸 메세지를 클라이언트로 가져옴
           		// socket의 InputStream 정보를 InputStream in에 넣은 뒤
			InputStream input = socket.getInputStream();
          		// BufferedReader에 위 InputStream을 담아 사용
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			System.out.println(reader.readLine()); 
			String WhatToDo = reader.readLine();
			
			if(WhatToDo.equals("insert")) { // if user wants insert new information.
				for(int i = 0; i<3; i++) {
					System.out.println(reader.readLine()); // 3 questions at Server.java run();
				}	
				System.out.println(reader.readLine());
			}
			else if(WhatToDo.equals("PathFind")) { // if user wants path
				// TODO GUI With Server.java run();
				
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
