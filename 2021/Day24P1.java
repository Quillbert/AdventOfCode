import java.math.BigInteger;
import java.util.*;

// Gave up, went to reddit, done by hand

public class Day24P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);

		String input = "02";

		ArrayList<String> lines = new ArrayList<String>();

		String str = s.nextLine().trim();
		while(str.length() > 0) {
			lines.add(str);
			str = s.nextLine().trim();
		}

		long[] out = run(lines, input);

		System.out.println(Arrays.toString(out));

		lines = new ArrayList<String>();

		str = s.nextLine().trim();
		while(str.length() > 0) {
			lines.add(str);
			str = s.nextLine().trim();
		}

		out = run(lines, "2", out);

		System.out.println(Arrays.toString(out));



	}

	public static long[] run(ArrayList<String> lines, String input) {
		long regs[] = new long[4];


		for(int i = 0; i < lines.size(); i++) {
			String[] parts = lines.get(i).split(" ");
			if(parts[0].equals("inp")) {
				regs[regNum(parts[1].charAt(0))] = Long.parseLong(input.substring(0, 1));
				input = input.substring(1);
				continue;
			}
			int regNum1 = regNum(parts[1].charAt(0));
			long num2 = 0;
			if(Character.isLetter(parts[2].charAt(0))) {
				num2 = regs[regNum(parts[2].charAt(0))];
			} else {
				num2 = Long.parseLong(parts[2]);
			}
			if(parts[0].equals("add")) {
				regs[regNum1] += num2;
			} else if(parts[0].equals("mul")) {
				regs[regNum1] *= num2;
			} else if(parts[0].equals("div")) {
				regs[regNum1] /= num2;
			} else if(parts[0].equals("mod")) {
				regs[regNum1] %= num2;
			} else if(parts[0].equals("eql")) {
				regs[regNum1] = regs[regNum1] == num2 ? 1 : 0;
			}
		}


		return regs;
	}

	public static long[] run(ArrayList<String> lines, String input, long[] regs) {

		for(int i = 0; i < lines.size(); i++) {
			String[] parts = lines.get(i).split(" ");
			if(parts[0].equals("inp")) {
				regs[regNum(parts[1].charAt(0))] = Long.parseLong(input.substring(0, 1));
				input = input.substring(1);
				continue;
			}
			int regNum1 = regNum(parts[1].charAt(0));
			long num2 = 0;
			if(Character.isLetter(parts[2].charAt(0))) {
				num2 = regs[regNum(parts[2].charAt(0))];
			} else {
				num2 = Long.parseLong(parts[2]);
			}
			if(parts[0].equals("add")) {
				regs[regNum1] += num2;
			} else if(parts[0].equals("mul")) {
				regs[regNum1] *= num2;
			} else if(parts[0].equals("div")) {
				regs[regNum1] /= num2;
			} else if(parts[0].equals("mod")) {
				regs[regNum1] %= num2;
			} else if(parts[0].equals("eql")) {
				regs[regNum1] = regs[regNum1] == num2 ? 1 : 0;
			}
		}


		return regs;
	}

	public static int regNum(char c) {
		return (int)c-(int)'w';
	}

}
