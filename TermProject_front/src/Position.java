
public class Position {
	int x, y;
	
	Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "(" + x + ", "  + y + ")";
		return str;
	}
}
