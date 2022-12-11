import java.util.*;

public class Packet {
	ArrayList<Packet> packets = new ArrayList<Packet>();
	long value;
	long operator;
	
	public void eval() {
		for(int i = 0; i < packets.size(); i++) {
			packets.get(i).eval();
		}
		
		if(operator == 0) {
			long v = 0;
			for(int i = 0; i < packets.size(); i++) {
				v += packets.get(i).value;
			}
			value = v;
		} else if(operator == 1) {
			long v = 1;
			for(int i = 0; i < packets.size(); i++) {
				v *= packets.get(i).value;
			}
			value = v;
		} else if(operator == 2) {
			long v = Long.MAX_VALUE;
			for(int i = 0; i < packets.size(); i++) {
				if(packets.get(i).value < v) {
					v = packets.get(i).value;
				}
			}
			value = v;
		} else if(operator == 3) {
			long v = 0;
			for(int i = 0; i < packets.size(); i++) {
				if(packets.get(i).value > v) {
					v = packets.get(i).value;
				}
			}
			value = v;
		} else if(operator == 5) {
			if(packets.get(0).value > packets.get(1).value) {
				value = 1;
			} else {
				value = 0;
			}
		} else if(operator == 6) {
			if(packets.get(0).value < packets.get(1).value) {
				value = 1;
			} else {
				value = 0;
			}
		} else if(operator == 7) {
			if(packets.get(0).value == packets.get(1).value) {
				value = 1;
			} else {
				value = 0;
			}
		}
		
	}
}
