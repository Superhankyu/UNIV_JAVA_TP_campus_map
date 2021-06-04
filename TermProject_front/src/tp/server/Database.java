package tp.server;

import tp.common.*;

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
	// 모든 room 정보를 Database에서 가져와서 List로 형성
//	public List<Room> getRooms(){
//		
//		List<Room> rooms = new ArrayList<>();
//	
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaPro", // This state can changed by Host's IP or DB_name, password, port ...
//					"root", "1234");
//			
//			Statement stmt = conn.createStatement();
//			ResultSet rset;
//			String sql;
//			
//			sql = "select * from room;"; // TODO FOR DETAILS
//			rset = stmt.executeQuery(sql);
//			
//			while(rset.next()) { 
//				Room temp = new Room();
//				temp.rName = rset.getString("rName");
//				temp.category = rset.getString("category");
//				temp.building = rset.getString("building"); // cannot apply type "building" should change this.
//				
//				rooms.add(temp);
//			}
//		}
//		catch(SQLException sqle) {
//			System.out.println("SQLException "+ sqle);
//		}
//		catch(Exception e) {
//			System.out.println("Exception "+ e);
//		}
//
//		return rooms;
//	}
	
	public List<String> getRooms(){
		
		List<String> datas = new ArrayList<>();
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaPro", // This state can changed by Host's IP or DB_name, password, port ...
					"root", "1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rset;
			String sql;
			
			sql = "select * from room;"; // TODO FOR DETAILS
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) { 
//				Room temp = new Room();
//				temp.rName = rset.getString("rName");
//				temp.category = rset.getString("category");
//				temp.building = rset.getString("building"); // cannot apply type "building" should change this.
				
				datas.add(
						rset.getString("rName") + ":" +
						rset.getString("category") + ":" +
						rset.getString("building")
						);
			}
		}
		catch(SQLException sqle) {
			System.out.println("SQLException "+ sqle);
		}
		catch(Exception e) {
			System.out.println("Exception "+ e);
		}

		return datas;
	}
	
	// rNamge, category building 입력시 그에 맞는 rooms return.
	public List<Room> getRooms(String rName, String category, String building){
		
		List<Room> rooms = new ArrayList<>();
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaPro", // This state can changed by Host's IP or DB_name, password, port ...
					"root", "1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rset;
			String sql;
			
			sql = "select * from room where rName = '" +rName+ "' and category = '"+category+"' and building = '"+building+"'"; // TODO FOR DETAILS
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
	
	// room 정보를 Database에 저장
	public boolean setRoom(String rName, String category, String building) {  // set Room table in DB when there are new input from users.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaPro",
					"root", "1234");
			
			Statement stmt = conn.createStatement();
			String sql;	
			
			sql = "insert into room (" + 
					"`rName`,\r\n" + 
					"`category`,\r\n" + 
					"`building`)values('"+rName+"', '"+category+"', '"+building+"')"; // TODO FOR DETAILS
			
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
	
	public boolean eraseRoom(String rName, String category, String building) {  // erase data.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaPro",
					"root", "1234");
			
			Statement stmt = conn.createStatement();
			String sql;	
			
			sql = "delete from room where rName = '" +rName+ "' and category = '"+category+"' and building = '"+building+"' "; // TODO FOR DETAILS
			
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
