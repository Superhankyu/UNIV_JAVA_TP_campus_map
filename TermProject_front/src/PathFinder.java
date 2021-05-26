import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.lang.Math;

import org.json.JSONArray;
import org.json.JSONObject;

public class PathFinder {
	private static List<Vertex> vertexs = new ArrayList<Vertex>();
	private static List<Building> buildings = new ArrayList<Building>();
	private static boolean defaultDataSet = false;
	private static List<Room> rooms = new ArrayList<Room>();

	public PathFinder(){
		try {
			if(!defaultDataSet){
				// vertex read
				Path path = Paths.get("skku_vertex_info.json");
				String data = Files.readString(path);				
				JSONArray jArray = new JSONArray(data);
				var leng = jArray.length();
				for(int i = 0; i<leng; i++) {
					JSONObject jObject = jArray.getJSONObject(i);
					String name = jObject.getString("name");
					int x = jObject.getInt("x");
					int y = jObject.getInt("y");
					vertexs.add(new Vertex(name, new Position(x, y)));
				}

				// add edges
				path = Paths.get("skku_edge_info.json");
				data = Files.readString(path);				
				jArray = new JSONArray(data);
				leng = jArray.length();
				for(int i = 0; i<leng; i++) {
					JSONObject jObject = jArray.getJSONObject(i);
					String name = jObject.getString("name");
					String adj = jObject.getString("adj");
					int adj_dis = jObject.getInt("adj_dis");

					Vertex startVt = findVertex(name);
					Vertex endVt = findVertex(adj);
					startVt.addNeighbour(new Edge(adj_dis, startVt, endVt));

					startVt = findVertex(adj);
					endVt = findVertex(name);
					startVt.addNeighbour(new Edge(adj_dis, startVt, endVt));
				}

				// add buildings
				path = Paths.get("skku_building_info.json");
				data = Files.readString(path);				
				jArray = new JSONArray(data);
				leng = jArray.length();
				for(int i = 0; i<leng; i++) {
					JSONObject jObject = jArray.getJSONObject(i);
					String bName = jObject.getString("bName");
					int openTime = jObject.getInt("openTime");
					int closeTime = jObject.getInt("closeTime");
					Vertex vt = findVertex(jObject.getString("vt"));

					buildings.add(new Building(bName, openTime, closeTime, vt));
				}

				updateRooms();
				defaultDataSet = true;
				
				//For Test
//				List<Vertex> testPath = this.findShortestPath(this.findClosestVertex(100, 100), this.findClosestBuilding(300, 300));
//				for(Vertex vt : testPath) {
//					System.out.println(vt.toString());
//				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Update room infos from db
	public List<Room> updateRooms(){
		try {
			Path path = Paths.get("skku_room_info.json");
			String data = Files.readString(path);				
			JSONArray jArray = new JSONArray(data);
			var leng = jArray.length();
			for(int i = 0; i<leng; i++) {
				JSONObject jObject = jArray.getJSONObject(i);
				String rName = jObject.getString("rName");
				String category = jObject.getString("category");
				String building = jObject.getString("building");
				
				rooms.add(new Room(rName, category, building));
			}
			
			return rooms;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// When finding user position
	public Vertex findClosestVertex(int x, int y) {
		Position pos = new Position(x, y);
		Vertex minVt = null;
		double min = Double.MAX_VALUE;
		for(Vertex vt : vertexs) {
			var cmpDist = distance(pos, vt.pos);
			if(min > cmpDist) {
				min = cmpDist;
				minVt = vt;
			}
		}
		
		return minVt;
	}
	
	// When finding target building
	public Building findClosestBuilding(int x, int y) {
		Position pos = new Position(x, y);
		Building minbd = null;
		double min = Double.MAX_VALUE;
		for(Building bd : buildings) {
			var cmpDist = distance(pos, bd.vt.pos);
			if(min > cmpDist) {
				min = cmpDist;
				minbd = bd;
			}
		}
		
		return minbd;
	}
	
	private double distance(Position a, Position b) {
		double xabs = Math.abs(a.x - b.x);    
	    double yabs = Math.abs(a.y - b.y);
	    
	    return Math.hypot(xabs, yabs);
	}
	
	// When finding shortest path to building
	public List<Vertex> findShortestPath(Vertex source, Building target) {
		this.computeShortestPaths(source);
		List<Vertex> path = this.getShortestPathTo(target.vt);
		return path;
	}
	
	// When finding shortest path to room
	public List<Vertex> findShortestPath(Vertex source, String category) {
		List<Vertex> targets = new ArrayList<Vertex>();
		for(Room room : rooms) {
			if(room.category.equals(category)) {
				Building bd = findBuilding(room.building);
				targets.add(bd.vt);
			}
		}
		
		this.computeShortestPaths(source);
		
		Vertex minVt = null;
		var min = Double.MAX_VALUE;
		for(Vertex target : targets) {
			if(min > target.getDistance()) {
				min = target.getDistance();
				minVt = target;
			}
		}
		
		List<Vertex> path = this.getShortestPathTo(minVt);
		
		return path;
	}
	
	// when initializing
	public List<Building> getAllBuildingInfos(){
		return buildings;
	}

	public Building findBuilding(String bName){
		for(Building bd : buildings){
			if(bd.bName.equals(bName))
				return bd;
		}

		return null;
	}

	public Vertex findVertex(String vName){
		for(Vertex vt : vertexs){
			if(vt.getName().equals(vName))
				return vt;
		}

		return null;
	}
	
	private void resetVertexs() {
		for(Vertex vt : vertexs) {
			vt.setVisited(false);
			vt.setPredecessor(null);
			vt.setDistance(Double.MAX_VALUE);
		}
	}
	
	private void computeShortestPaths(Vertex sourceVertex){
		resetVertexs();
		
        sourceVertex.setDistance(0);
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(sourceVertex);
        sourceVertex.setVisited(true);
 
        while( !priorityQueue.isEmpty() ){
            // Getting the minimum distance vertex from priority queue
            Vertex actualVertex = priorityQueue.poll();
 
            for(Edge edge : actualVertex.getAdjacenciesList()){
 
                Vertex v = edge.getTargetVertex();
                if(!v.isVisited())
                {
                    double newDistance = actualVertex.getDistance() + edge.getWeight();
 
                    if( newDistance < v.getDistance() ){
                        priorityQueue.remove(v);
                        v.setDistance(newDistance);
                        v.setPredecessor(actualVertex);
                        priorityQueue.add(v);
                    }
                }
            }
            actualVertex.setVisited(true);
        }
    }
 
    private List<Vertex> getShortestPathTo(Vertex targetVertex){
        List<Vertex> path = new ArrayList<>();
 
        for(Vertex vertex=targetVertex;vertex!=null;vertex=vertex.getPredecessor()){
            path.add(vertex);
        }
 
        Collections.reverse(path);
        return path;
    }
}
