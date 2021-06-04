package tp.client;

//import tp.server.Database;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	static int exit = 0;
	public static void main(String[] args) {
		try {
			Socket socket = null;
            		// 家南 辑滚俊 立加
			socket = new Socket("localhost", 1234); // TEST -> LOCALHOST
			System.out.println("辑滚俊 立加 己傍! - " + socket.toString()); // 立加 犬牢侩
			
			OutputStream os = socket.getOutputStream ();
			DataOutputStream dos = new DataOutputStream (os);
		
			while(true) {
				MyFrame.wait = 1;
				MyFrame mf = new MyFrame(socket);
				
				while(MyFrame.wait == 1) {
					System.out.print("");
				}
				
				if(MyFrame.isfin == 1) {
					break;
				}
			}
			/*while(true) {
				MyFrame.wait = 1;
				MyFrame mf = new MyFrame();
				
				while(MyFrame.wait == 1) {
					System.out.print("");
						
				}
				
				if(MyFrame.isfin == 1) {
					break;
				}
			}*/
		
			dos.writeUTF("ready");
			dos.writeUTF("finish");
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace(); // 抗寇贸府
		}
	}
}
