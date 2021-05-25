import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	// ��� room ������ Database���� �����ͼ� List�� ����
	// rNamge, category building �Է½� �׿� �´� rooms return.
	public List<Room> getRooms(String rName, String category, String building){
		
		List<Room> rooms = new ArrayList<>();
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaPro", // This state can changed by Host's IP or DB_name, password, port ...
					"root", "1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rset;
			String sql;
			
			sql = "select * from Room where rName = '" +rName+ "' and category = '"+category+"' and building = '"+building+"'"; // TODO FOR DETAILS
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) { 
				Room temp = new Room();
				temp.rName = rset.getString(rName);
				temp.category = rset.getString(category);
				temp.building = rset.getString(building); // cannot apply type "building" should change this.
				
				rooms.add(temp);
			}
		}
		catch(SQLException sqle) {
			System.out.println("SQLException "+ sqle);
		}
		catch(Exception e) {
			System.out.println("Exception "+ e);
		}

		return rooms;
	}
	
	// room ������ Database�� ����
	public boolean setRoom(String rName, String category, String building) {  // set Room table in DB when there are new input from users.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaPro",
					"root", "1234");
			
			Statement stmt = conn.createStatement();
			String sql;	
			
			sql = "insert into Room values('"+rName+"', '"+category+"', '"+building+"')"; // TODO FOR DETAILS
			
			int updated = stmt.executeUpdate(sql); // return number of updated queries.
			if(updated >= 1) {
				stmt.close();
				conn.close();
				return true;
			}
			else { // cannot update.
				stmt.close();
				conn.close();
				return false;
			}			
		}
		catch(SQLException sqle) {
			System.out.println("SQLException "+ sqle);
		}
		catch(Exception e) {
			System.out.println("Exception "+ e);
		}
		return false;
	}
}
