import java.util.*;

public class Day16P2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String in  = s.nextLine().trim();
		
		String value = "";
		
		for(int i = 0; i < in.length(); i++) {
			int piece = Integer.parseInt(in.substring(i, i+1), 16);
			String binary = Integer.toBinaryString(piece);
			while(binary.length() < 4) {
				binary = "0" + binary;
			}
			value += binary;
		}
		
		Packet root = new Packet();
		
		sum(value, false, 1, false, root);
		
		root.packets.get(0).eval();
		
		System.out.println(root.packets.get(0).value);
		
	}
	
	public static long[] sum(String value, boolean sub, int num, boolean type2, Packet root) {
		long[] out = new long[2];
		Packet newPack = new Packet();
		if(value.length() == 0) {
			return out;
		}
		
		if(num == 0 && type2) {
			return out;
		}
		
		int bitsUsed = 0;
		
		int version = Integer.parseInt(value.substring(bitsUsed, bitsUsed+3), 2);
		bitsUsed += 3;
		
		int type = Integer.parseInt(value.substring(bitsUsed, bitsUsed+3), 2);
		bitsUsed += 3;
		
		newPack.operator = type;
		
		if(type == 4) {
			String v = "";
			while(true) {
				String block = value.substring(bitsUsed, bitsUsed+5);
				bitsUsed += 5;
				
				v += block.substring(1);
				
				if(block.charAt(0) == '0') break;
			}
			newPack.value = Long.parseLong(v, 2);
		} else {
			char lengthId = value.charAt(bitsUsed);
			bitsUsed++;
			
			if(lengthId == '0') {
				int length = Integer.parseInt(value.substring(bitsUsed, bitsUsed+15), 2);
				bitsUsed += 15;
				
				long[] val = sum(value.substring(bitsUsed, bitsUsed+length), true, 1, false, newPack);
				version += val[0];
				bitsUsed += length;
			} else {
				int length = Integer.parseInt(value.substring(bitsUsed, bitsUsed+11), 2);
				bitsUsed += 11;
				long[] val = sum(value.substring(bitsUsed), true, length, true, newPack);
				version += val[0];
				bitsUsed += val[1];
			}
		}
		
		root.packets.add(newPack);
		
		if(!sub) {
			int remove = (int)Math.ceil(bitsUsed/8.0)*8;
			long[] val = sum(value.substring(remove), sub, 1, type2, root);
			version+= val[0];
			out[0] = version;
			out[1] = bitsUsed;
			return out;
		} else {
			long[] val = sum(value.substring(bitsUsed), sub, num-1, type2, root);
			version += val[0];
			out[0] = version;
			out[1] = bitsUsed+val[1];
			return out;
		}
	}
	
}