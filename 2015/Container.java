
public class Container {
	public static int idCount = 0;
	public int value;
	public int id;
	public Container(int v) {
		value = v;
		id = idCount;
		idCount++;
	}
	
	public Container(Container c) {
		id = c.id;
		value = c.value;
	}
	
	public boolean equals(Container c) {
		return id == c.id;
	}
	
	public String toString() {
		return "" + value;
	}
}
