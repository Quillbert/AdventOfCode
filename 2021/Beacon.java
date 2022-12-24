import java.util.*;

public class Beacon {
	public int[] place = new int[3];
	
	public Beacon(int x, int y, int z) {
		place = new int[3];
		place[0] = x;
		place[1] = y;
		place[2] = z;
	}
	
	public Beacon transform(int x, int y, int z) {
		return new Beacon(place[0]+x, place[1]+y, place[2]+z);
	}
	
	public Beacon rotate(int value) {
		int[] negatives = new int[3];
		switch(value%8) {
		case 0:
			negatives[0] = 1;
			negatives[1] = 1;
			negatives[2] = 1;
			break;
		case 1:
			negatives[0] = -1;
			negatives[1] = 1;
			negatives[2] = 1;
			break;
		case 2:
			negatives[0] = 1;
			negatives[1] = -1;
			negatives[2] = 1;
			break;
		case 3:
			negatives[0] = 1;
			negatives[1] = 1;
			negatives[2] = -1;
			break;
		case 4:
			negatives[0] = -1;
			negatives[1] = -1;
			negatives[2] = 1;
			break;
		case 5:
			negatives[0] = -1;
			negatives[1] = 1;
			negatives[2] = -1;
			break;
		case 6:
			negatives[0] = 1;
			negatives[1] = -1;
			negatives[2] = -1;
			break;
		case 7:
			negatives[0] = -1;
			negatives[1] = -1;
			negatives[2] = -1;
			break;
		}
		
		int[] values = new int[3];
		switch(value/8) {
		case 0:
			values[0] = place[0];
			values[1] = place[1];
			values[2] = place[2];
			break;
		case 1:
			values[0] = place[0];
			values[1] = place[2];
			values[2] = place[1];
			break;
		case 2:
			values[0] = place[1];
			values[1] = place[0];
			values[2] = place[2];
			break;
		case 3:
			values[0] = place[1];
			values[1] = place[2];
			values[2] = place[0];
			break;
		case 4:
			values[0] = place[2];
			values[1] = place[0];
			values[2] = place[1];
			break;
		case 5:
			values[0] = place[2];
			values[1] = place[1];
			values[2] = place[0];
			break;
		}
		
		return new Beacon(values[0]*negatives[0], values[1]*negatives[1], values[2]*negatives[2]);
	}
	
	public String toString() {
		return "[" + place[0] + "," + place[1] + "," + place[2] + "]";
	}
	
	public boolean equals(Beacon o) {
		return o.place[0] == place[0] && o.place[1] == place[1] && o.place[2] == place[2];
	}
}
