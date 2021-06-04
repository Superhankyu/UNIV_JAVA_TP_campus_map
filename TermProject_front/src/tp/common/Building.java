package tp.common;

public class Building {
	public String bName;
	public int openTime;
	public int closeTime;
	public Vertex vt;

	public Building(String bName, int openTime, int closeTime, Vertex vt){
		this.bName = bName;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.vt = vt;
	}
}
