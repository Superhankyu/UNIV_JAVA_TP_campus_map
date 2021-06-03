

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WritingThread extends Thread { // if there are keyborad input.
	Socket socket = null;
	Scanner scanner = new Scanner(System.in); // 채팅용 Scanner
	
	public WritingThread(Socket socket) { // 생성자
    		// 받아온 Socket Parameter를 해당 클래스 Socket에 넣기
		this.socket = socket; 
	}
	
	public void run() { // GUI writes.
		try {
			// OutputStream - 클라이언트에서 Server로 메세지 발송 
            // socket의 OutputStream 정보를 OutputStream out에 넣은 뒤
			OutputStream out = socket.getOutputStream();
            
			// PrintWriter에 위 OutputStream을 담아 사용
			PrintWriter writer = new PrintWriter(out, true);
			
			writer.println(scanner.nextLine()); // insertNew or PathFind
			for(int i = 0; i<3; i++) {
				writer.println(scanner.nextLine());
			}
			
			//while(true) { // Get information form GUI and send to server in this while loop until user click Quit or Fin 한번 에 입력을 다 받아서 한번에 정보를 보내줘야 할듯.
				// writer.println(mouseEvents()....); // FROM MOUSE or KEYBOARD input
			//}
			
		} catch (Exception e) {
			e.printStackTrace(); // 예외처리
		}
		
		
	}
}
