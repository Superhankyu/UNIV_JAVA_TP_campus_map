import java.util.List;
import java.util.ArrayList;

public class PathFinder {
	// �ڽ� ��ġ ���콺�� ã�� ��,
	public Vertex findClosestVertex(int x, int y) {
		return new Vertex("test", new Position(x, y));
	}
	
	// target�� ���콺�� ã�� ��, �ǹ� ��������
	public List<Vertex> findShortestPath(Vertex source, Building target) {
		List<Vertex> path = new ArrayList<>();
		return path;
	}
	
	// target�� category�� ã�� ��,
	public List<Vertex> findShortestPath(Vertex source, String category) {
		List<Vertex> path = new ArrayList<>();
		return path;
	}
	
	public Room findClosestRoom(Vertex source, String category) {
		return new Room();
	}
	
	// ó���� �ǹ� ������ ���� �����ͼ� set �صд�.
	public List<Building> getAllBuildingInfos(){
		List<Building> buildings = new ArrayList<>();
		return buildings;
	}
}
