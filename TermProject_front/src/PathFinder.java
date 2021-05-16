import java.util.List;
import java.util.ArrayList;

public class PathFinder {
	// 자신 위치 마우스로 찾을 때,
	public Vertex findClosestVertex(int x, int y) {
		return new Vertex("test", new Position(x, y));
	}
	
	// target을 마우스로 찾을 때, 건물 기준으로
	public List<Vertex> findShortestPath(Vertex source, Building target) {
		List<Vertex> path = new ArrayList<>();
		return path;
	}
	
	// target을 category로 찾을 때,
	public List<Vertex> findShortestPath(Vertex source, String category) {
		List<Vertex> path = new ArrayList<>();
		return path;
	}
	
	public Room findClosestRoom(Vertex source, String category) {
		return new Room();
	}
	
	// 처음에 건물 정보를 전부 가져와서 set 해둔다.
	public List<Building> getAllBuildingInfos(){
		List<Building> buildings = new ArrayList<>();
		return buildings;
	}
}
