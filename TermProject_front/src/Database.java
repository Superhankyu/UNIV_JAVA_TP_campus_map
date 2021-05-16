import java.util.List;
import java.util.ArrayList;

public class Database {
	// 모든 room 정보를 Database에서 가져와서 List로 형성
	public List<Room> getRooms(){
		List<Room> rooms = new ArrayList<>();
		return rooms;
	}
	
	// room 정보를 Database에 저장
	public boolean setRoom(String rName, String category, Building building) {
		
		return true;
	}
}
