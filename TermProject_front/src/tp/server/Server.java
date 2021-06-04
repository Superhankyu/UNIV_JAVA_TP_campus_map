package tp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		try {
			int socketPort = 1234; // 소켓 포트 설정
            ServerSocket serverSocket = new ServerSocket(socketPort); // 서버 소켓 만들기
            // 서버 오픈 확인용
            System.out.println("socket : " + socketPort + "으로 서버가 열렸습니다");
	
            // 소켓 서버가 종료될 때까지 무한루프 유저 수만큼 스레드가 생성이 됨.
            while(true) {
                Socket socketUser = serverSocket.accept(); // 서버에 클라이언트 접속 시
                // Thread 안에 클라이언트 정보를 담아줌
                Server_thread sthd = new Server_thread(socketUser);
                Thread thd = new Thread(sthd);
                thd.start(); // Thread 시작
            }                 
        
		} catch (IOException e) {
			e.printStackTrace(); // 예외처리
		}
	}
}
