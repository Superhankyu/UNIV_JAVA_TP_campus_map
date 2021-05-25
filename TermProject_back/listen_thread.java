



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class listen_thread extends Thread { // �������� ���� �޼��� �д� Thread
	Socket socket = null;

	public listen_thread(Socket socket) { // ������
		this.socket = socket; // �޾ƿ� Socket Parameter�� �ش� Ŭ���� Socket�� �ֱ�
	}
	
	public void run() {
		try {
			// InputStream - Server���� ���� �޼����� Ŭ���̾�Ʈ�� ������
           		// socket�� InputStream ������ InputStream in�� ���� ��
			InputStream input = socket.getInputStream();
          		// BufferedReader�� �� InputStream�� ��� ���
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
