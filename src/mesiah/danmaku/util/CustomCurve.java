package mesiah.danmaku.util;

public class CustomCurve {
	protected String id;
	protected String[][] points;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public CustomCurve(String id) {
		this.id = id;
		points = new String[4][2];
	}
	
	public void addPoint(String[] p, int index) {
		points[index] = p;
	}
	
	public String[][] getPoints() {
		return points;
	}
	
	public int size() {
		return points.length;
	}
	
	public String[] getPoint(int index) {
		return points[index];
	}
	
	public String toString() {
		String s = "";
		s += "CustomCurve ID: " + id + "\n";
		s += "Point 1: " + points[0][0] + ", " + points[0][1] + "\n";
		s += "Point 2: " + points[1][0] + ", " + points[1][1] + "\n";
		s += "Point 3: " + points[2][0] + ", " + points[2][1] + "\n";
		s += "Point 4: " + points[3][0] + ", " + points[3][1];
		return s;
	}

}
