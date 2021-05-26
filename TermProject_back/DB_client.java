

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class DB_client {
	public static void main(String[] args) {
		try {
			Socket socket = null;
            		// ���� ������ ����
			socket = new Socket("192.168.219.100", 1234); // TEST -> LOCALHOST
			System.out.println("������ ���� ����!"); // ���� Ȯ�ο�
			
            // �������� ���� �޼��� �д� Thread
			listen_thread t1 = new listen_thread(socket);
			WritingThread t2 = new WritingThread(socket); // GUI Thread start.

			t1.start(); // ListeningThread Start
			t2.start(); // WritingThread Start
            
		} catch (IOException e) {
			e.printStackTrace(); // ����ó��
		}
	}
}
