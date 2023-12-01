import java.util.*;

public class Day10P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);

		String input = s.nextLine().trim();

		for(int t = 0; t < 50; t++) {

			int count = 0;

			char current = '\0';

			String out = "";

			for(int i = 0; i < input.length(); i++) {
				if(input.charAt(i) != current) {
					if(count > 0) {
						out += count + "" + current;
					}
					count = 1;
					current = input.charAt(i);
				} else {
					count++;
				}
			}

			out += count + "" + current;

			input = out;
			
			System.out.println(t);
		}

		System.out.println(input.length());
	}

}
