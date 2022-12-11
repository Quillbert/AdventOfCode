import java.util.*;
import java.math.*;

public class Day16P1 {

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

		int versionSum = sum(value, false, 1, false)[0];

		System.out.println(versionSum);

	}

	public static int[] sum(String value, boolean sub, int num, boolean type2) {
		int[] out = new int[2];
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
		
		
		
		if(type == 4) {
			while(true) {
				String block = value.substring(bitsUsed, bitsUsed+5);
				bitsUsed += 5;
				
				if(block.charAt(0) == '0') break;
			}
		} else {
			char lengthId = value.charAt(bitsUsed);
			bitsUsed++;
			
			if(lengthId == '0') {
				int length = Integer.parseInt(value.substring(bitsUsed, bitsUsed+15), 2);
				bitsUsed += 15;
				
				int[] val = sum(value.substring(bitsUsed, bitsUsed+length), true, 1, false);
				version += val[0];
				bitsUsed += length;
			} else {
				int length = Integer.parseInt(value.substring(bitsUsed, bitsUsed+11), 2);
				bitsUsed += 11;
				int[] val = sum(value.substring(bitsUsed), true, length, true);
				version += val[0];
				bitsUsed += val[1];
			}
		}
		
		if(!sub) {
			int remove = (int)Math.ceil(bitsUsed/8.0)*8;
			int[] val = sum(value.substring(remove), sub, 1, type2);
			version+= val[0];
			out[0] = version;
			out[1] = bitsUsed;
			return out;
		} else {
			int[] val = sum(value.substring(bitsUsed), sub, num-1, type2);
			version += val[0];
			out[0] = version;
			out[1] = bitsUsed+val[1];
			return out;
		}
	}

}
