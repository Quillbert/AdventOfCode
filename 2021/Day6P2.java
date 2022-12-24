import java.math.BigInteger;
import java.util.*;

public class Day6P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		String[] parts = str.split(",");
		BigInteger[] values = new BigInteger[9];
		for(int i = 0; i < values.length; i++) {
			values[i] = new BigInteger("0");
		}
		for(int i = 0; i < parts.length; i++) {
			values[Integer.parseInt(parts[i])] = values[Integer.parseInt(parts[i])].add(new BigInteger("1"));
		}
		for(int i = 0; i < 256; i++) {
			BigInteger temp = values[0];
			for(int j = 0; j < values.length-1; j++) {
				values[j] = values[j+1];
			}
			values[8] = temp;
			values[6] = values[6].add(temp);
		}
		BigInteger sum = new BigInteger("0");
		for(int i = 0; i < values.length; i++) {
			sum = sum.add(values[i]);
		}
		System.out.println(sum);
	}

}
