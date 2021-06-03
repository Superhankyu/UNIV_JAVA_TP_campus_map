



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client_thread extends Thread { // �������� ���� �޼��� �д� Thread
	Socket socket = null;

	public Client_thread(Socket socket) { // ������
		this.socket = socket; // �޾ƿ� Socket Parameter�� �ش� Ŭ���� Socket�� �ֱ�
	}
	
	public void run() {
		try {
			// InputStream - Server���� ���� �޼����� Ŭ���̾�Ʈ�� ������
           		// socket�� InputStream ������ InputStream in�� ���� ��
			InputStream input = socket.getInputStream();
          		// BufferedReader�� �� InputStream�� ��� ���
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			//System.out.println(reader.readLine()); 
			
			
			while(true) {
				
				MyFrame.wait = 1;
				MyFrame mf = new MyFrame();
				
				while(MyFrame.wait == 1) {
					System.out.print("");
				}
				
				if(MyFrame.isfin == 1) {
					break;
				}
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
