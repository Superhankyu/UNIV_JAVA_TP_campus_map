

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WritingThread extends Thread { // if there are keyborad input.
	Socket socket = null;
	Scanner scanner = new Scanner(System.in); // ä�ÿ� Scanner
	
	public WritingThread(Socket socket) { // ������
    		// �޾ƿ� Socket Parameter�� �ش� Ŭ���� Socket�� �ֱ�
		this.socket = socket; 
	}
	
	public void run() { // GUI writes.
		try {
			// OutputStream - Ŭ���̾�Ʈ���� Server�� �޼��� �߼� 
            // socket�� OutputStream ������ OutputStream out�� ���� ��
			OutputStream out = socket.getOutputStream();
            
			// PrintWriter�� �� OutputStream�� ��� ���
			PrintWriter writer = new PrintWriter(out, true);
			
			writer.println(scanner.nextLine()); // insertNew or PathFind
			for(int i = 0; i<3; i++) {
				writer.println(scanner.nextLine());
			}
			
			//while(true) { // Get information form GUI and send to server in this while loop until user click Quit or Fin �ѹ� �� �Է��� �� �޾Ƽ� �ѹ��� ������ ������� �ҵ�.
				// writer.println(mouseEvents()....); // FROM MOUSE or KEYBOARD input
			//}
			
		} catch (Exception e) {
			e.printStackTrace(); // ����ó��
		}
		
		
	}
}
